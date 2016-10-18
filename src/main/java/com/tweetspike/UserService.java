package com.tweetspike;

import com.aerospike.client.*;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import utilityMethods.PasswordHash;

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
            String hashedPassword = PasswordHash.genHash(password, PasswordHash.genSalt());
            
            // Get gender
            System.out.print("Select gender (f or m) for " + username + ":");
            gender = input.nextLine().substring(0, 1);

            // Get region
            System.out.print("Select region (north, south, east or west) for " + username + ":");
            region = input.nextLine().substring(0, 1);


            // Write record
            WritePolicy wPolicy = new WritePolicy();
            wPolicy.recordExistsAction = RecordExistsAction.UPDATE;

            Key key = new Key("test", "users", username);
            Bin binUsername = new Bin("username", username);
            Bin binPassword = new Bin("password", hashedPassword);
            Bin binGender = new Bin("gender", gender);
            Bin binRegion = new Bin("region", region);
            Bin binLastTweeted = new Bin("lasttweeted", 0);
            Bin binTweetCount = new Bin("tweetcount", 0);

            client.put(wPolicy, key, binUsername, binPassword, binGender, binRegion, binLastTweeted, binTweetCount);

            System.out.print("\nINFO: User record created!");
        }
    }

    public void printUser(String username) throws AerospikeException {
        Record userRecord = null;
        Key userKey = null;

        userKey = new Key("test", "users", username);
        userRecord = client.get(null, userKey);
        if (userRecord != null) {
            System.out.print("\nINFO: User record read successfully! Here are the details:\n");
            System.out.print("Username:   " + userRecord.getValue("username") + "\n");
            System.out.print("Gender:     " + userRecord.getValue("gender") + "\n");
            System.out.print("Region:     " + userRecord.getValue("region") + "\n");
            System.out.print("Last tweet  " + userRecord.getValue("lasttweeted") + "\n");
            System.out.print("Tweet count: " + userRecord.getValue("tweetcount") + "\n");
        } else {
            System.out.print("ERROR: User record not found!\n");
        }
    }

    public User signIn(String username, String password) {
        Record userRecord = null;
        Key userKey = null;
        User user = new User();

        userKey = new Key("test", "users", username);
        userRecord = client.get(null,userKey);
        if(userRecord !=null)
        {
            if(PasswordHash.checkPassword(password, userRecord.getString("password"))) {
                user.setUsername(userRecord.getString("username"));
                user.setGender(userRecord.getString("gender"));
                user.setRegion(userRecord.getString("region"));
                user.setLastTweeted(userRecord.getLong("lasttweeted"));
                user.setTweetCount(userRecord.getInt("tweetcount"));
                System.out.print("good");
                return user;
            } else {
                System.out.println("bad");
                return null;
            }
        } else {
            return null;
        }
    }



}
