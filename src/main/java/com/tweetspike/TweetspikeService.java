package com.tweetspike;

import com.aerospike.client.*;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;

import java.time.Instant;
import java.util.Scanner;

public class TweetspikeService {
    private AerospikeClient client;
    private Scanner input;

    public TweetspikeService(AerospikeClient client, Scanner input)
    {
        this.client = client;
        this.input = input;
    }

    public void createTweet(User user) throws AerospikeException, InterruptedException {

        System.out.print("\n********** Create Tweet **********\n");
        Record userRecord = null;
        Key userKey = null;
        Key tweetKey = null;

        // Check if username exists
        userKey = new Key("test", "users", user.getUsername());
        userRecord = client.get(null, userKey);
        if (userRecord != null) {
            int nextTweetCount = userRecord.getInt("tweetcount") + 1;
            // Get tweet
            String tweet;
            System.out.print("Enter tweet for " + user.getUsername() + ":");
            tweet = input.nextLine();

            // Write record
            WritePolicy wPolicy = new WritePolicy();
            wPolicy.recordExistsAction = RecordExistsAction.UPDATE;

            // Create timestamp to store along with the tweet so we can
            // query, index and report on it
            long timestamp = Instant.now().getEpochSecond();

            tweetKey = new Key("test", "tweets", user.getUsername() + ":" + nextTweetCount);
            Bin bTweet = new Bin("tweet", tweet);
            Bin bTimestamp = new Bin("timestamp", timestamp);
            Bin bLastTweeted = new Bin("lasttweeted", timestamp);
            Bin bUsername = new Bin("username", user.getUsername());
            Bin bNextTweetCount = new Bin("tweetcount", nextTweetCount);

            client.put(wPolicy, tweetKey, bTweet, bTimestamp, bUsername);
            System.out.print("\nINFO: Tweet record created!\n");

            // Update tweet count and last tweet'd timestamp in the user
            // record
            client.put(wPolicy, userKey, bLastTweeted, bNextTweetCount);
            user.setTweetCount(nextTweetCount);
            user.setLastTweeted(timestamp);
        } else {
           System.out.print("ERROR: User record not found!\n");
        }
    }

    public void batchGetUserTweets(String username) throws AerospikeException {
        Record userRecord = null;
        Key userKey = null;

        if (username != null && username.length() > 0) {
            // Check if username exists
            userKey = new Key("test", "users", username);
            userRecord = client.get(null, userKey);
            if (userRecord != null) {
                // Get how many tweets the user has
                int tweetCount = userRecord.getInt("tweetcount");

                // Create an array of keys so we can initiate batch read
                // operation
                Key[] keys = new Key[tweetCount];
                for (int i = 0; i < keys.length; i++) {
                    keys[i] = new Key("test", "tweets",(username + ":" + (i + 1)));
                }

                System.out.println("\n************ " + username + "'s Tweets ************");

                BatchPolicy policy = new BatchPolicy();
                // Initiate batch read operation
                if (keys.length > 0){
                    Record[] records = client.get(policy, keys);
                    for (int j = 0; j < records.length; j++) {
                        System.out.print(records[j].getValue("tweet").toString() + "\n");
                    }
                }
            }
        } else {
            System.out.print("ERROR: User record not found!\n");
        }
    }
}
