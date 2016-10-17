package com.tweetspike;

import com.aerospike.client.*;
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

    public void createTweet() throws AerospikeException, InterruptedException {

        System.out.print("\n********** Create Tweet **********\n");

        Record userRecord = null;
        Key userKey = null;
        Key tweetKey = null;

        // Get username
        String username;
        System.out.print("\nEnter username:");
        username = input.nextLine();

        if (username != null && username.length() > 0) {
            // Check if username exists
            userKey = new Key("test", "users", username);
            userRecord = client.get(null, userKey);
            if (userRecord != null) {
                int nextTweetCount = userRecord.getInt("tweetcount") + 1;
                //nextTweetCount++;
                System.out.println(nextTweetCount);
                // Get tweet
                String tweet;
                System.out.print("Enter tweet for " + username + ":");
                tweet = input.nextLine();

                // Write record
                WritePolicy wPolicy = new WritePolicy();
                wPolicy.recordExistsAction = RecordExistsAction.UPDATE;

                // Create timestamp to store along with the tweet so we can
                // query, index and report on it
                long timestamp = Instant.now().getEpochSecond();

                tweetKey = new Key("test", "tweets", username + ":" + nextTweetCount);
                Bin bTweet = new Bin("tweet", tweet);
                Bin bTimestamp = new Bin("timestamp", timestamp);
                Bin bLastTweeted = new Bin("lasttweeted", timestamp);
                Bin bUsername = new Bin("username", username);
                Bin bNextTweetCount = new Bin("tweetcount", nextTweetCount);

                client.put(wPolicy, tweetKey, bTweet, bTimestamp, bUsername);
                System.out.print("\nINFO: Tweet record created!\n");

                // Update tweet count and last tweet'd timestamp in the user
                // record
                client.put(wPolicy, userKey, bLastTweeted, bNextTweetCount);
            } else {
               System.out.print("ERROR: User record not found!\n");
            }
        }
    } //createTweet
}
