package com.tweetspike;

import com.aerospike.client.*;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;

import java.util.Scanner;

public class UserService {
    private AerospikeClient client;
    private Scanner input;

    public UserService(AerospikeClient client, Scanner input) {
        this.client = client;
        this.input = input;
    }

    public void createUser() throws AerospikeException {
        System.out.print("\n********** Create User **********\n");

        String username;
        String password;
        String gender;
        String region;

        // Get username
        System.out.print("Enter username: ");
        username = input.nextLine();

        if (username != null && username.length() > 0) {
            // Get password
            System.out.print("Enter password for " + username + ":");
            password = input.nextLine();

            // Get gender
            System.out.print("Select gender (f or m) for " + username + ":");
            gender = input.nextLine().substring(0, 1);

            // Get region
            System.out.print("Select region (north, south, east or west) for "
                    + username + ":");
            region = input.nextLine().substring(0, 1);


            // Write record
            WritePolicy wPolicy = new WritePolicy();
            wPolicy.recordExistsAction = RecordExistsAction.UPDATE;

            Key key = new Key("test", "users", username);
            Bin binUsername = new Bin("username", username);
            Bin binPassword = new Bin("password", password);
            Bin binGender = new Bin("gender", gender);
            Bin binRegion = new Bin("region", region);
            Bin binLastTweeted = new Bin("lasttweeted", 0);
            Bin binTweetCount = new Bin("tweetcount", 0);

            client.put(wPolicy, key, binUsername, binPassword, binGender, binRegion, binLastTweeted, binTweetCount);

            System.out.print("\nINFO: User record created!");
        }
    }

    public void getUser() throws AerospikeException {
        Record userRecord = null;
        Key userKey = null;

        // Get username
        String username;
        System.out.print("\nEnter username:");
        username = input.nextLine();

        if (username != null && username.length() > 0) {
            // Check if username exists
            userKey = new Key("test", "users", username);
            userRecord = client.get(null, userKey);
            if (userRecord != null) {
                System.out.print("\nINFO: User record read successfully! Here are the details:\n");
                System.out.print("Username:   " + userRecord.getValue("username") + "\n");
                System.out.print("Password:   " + userRecord.getValue("password") + "\n");
                System.out.print("Gender:     " + userRecord.getValue("gender") + "\n");
                System.out.print("Region:     " + userRecord.getValue("region") + "\n");
                System.out.print("Last tweet  " + userRecord.getValue("lasttweeted") + "\n");
                System.out.print("Tweet count: " + userRecord.getValue("tweetcount") + "\n");
            } else {
                System.out.print("ERROR: User record not found!\n");
            }
        } else {
            System.out.print("ERROR: User record not found!\n");
        }
    }

    public void batchGetUserTweets() throws AerospikeException {
        Record userRecord = null;
        Key userKey = null;

        // Get username
        String username;
        System.out.print("\nEnter username:");
        username = input.nextLine();

        if (username != null && username.length() > 0) {
            // Check if username exists
            userKey = new Key("test", "users", username);
            userRecord = client.get(null, userKey);
            if (userRecord != null) {
                // Get how many tweets the user has
                int tweetCount = (Integer) userRecord.getValue("tweetcount");

                // Create an array of keys so we can initiate batch read
                // operation
                Key[] keys = new Key[tweetCount];
                for (int i = 0; i < keys.length; i++) {
                    keys[i] = new Key("test", "tweets",(username + ":" + (i + 1)));
                }

                System.out.print("\nHere's " + username + "'s tweet(s):\n");

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
