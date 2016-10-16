package com.tweetspike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;

import java.util.Scanner;

public class UserService {
    private AerospikeClient client;
    private Scanner input;

    public UserService(AerospikeClient client, Scanner input)
    {
        this.client = client;
        this.input = input;
    }

    public void createUser() throws AerospikeException
    {
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
}
