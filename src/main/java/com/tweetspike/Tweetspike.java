package com.tweetspike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.policy.ClientPolicy;

import java.util.Scanner;


//**
public class Tweetspike {
    private AerospikeClient tweetspikeClient;
    private User user = null;
    Scanner input = new Scanner(System.in);
    UserService us = new UserService(tweetspikeClient, input);
    TweetspikeService ts = new TweetspikeService(tweetspikeClient, input);

    public Tweetspike(String hostIP, int hostPort)
    {
        ClientPolicy cPolicy = new ClientPolicy();
        cPolicy.timeout = 500;
        tweetspikeClient = new AerospikeClient(cPolicy, hostIP, hostPort);
    }

    public void menu()
    {
        try {
            System.out.println("INFO: Connecting to Aerospike cluster...");

            // Establish connection to Aerospike server

            if (tweetspikeClient == null || !tweetspikeClient.isConnected()) {
                System.out.print("\nERROR: Connection to Aerospike cluster failed! Please check the server settings and try again!");
                //console.readLine();
            } else {
                System.out.print("\nINFO: Connection to Aerospike cluster succeeded!\n");

                if (user != null) {
                    System.out.print("[1]: Write a tweet");
                    System.out.print("[2]: View a user profile");
                    System.out.print("[3]: View a users tweets");
                    System.out.print("\nSelect 1-3 and hit enter: ");

                    int choice = input.nextInt();
                    input.nextLine();

                    switch (choice) {
                        case 1:
                            ts.createTweet(user);
                            break;
                        case 2:
                            us.getUser(input.nextLine());
                            break;
                        case 3:
                            ts.batchGetUserTweets(input.nextLine());
                            break;
                        default:
                            break;
                    }
                } else if(user == null) {
                    System.out.print("[1]: Sign in");
                    System.out.print("[2]: Create a user account");
                    System.out.print("[3]: View a user profile");
                    System.out.print("[4]: view a users tweets");
                    System.out.print("\nSelect 1-4 and hit enter: ");

                    int choice = input.nextInt();
                    input.nextLine();

                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            us.getUser(input.nextLine());
                            break;
                        case 4:
                            ts.batchGetUserTweets(input.nextLine());
                            break;
                        default:
                            break;
                    }
                }


                // UtilityService util = new UtilityService(client);
                // Present options
//                System.out.print("\nWhat would you like to do:\n");
//                System.out.print("1> Create a user account\n");
//                System.out.print("2> Create a tweet\n");
//                System.out.print("3> Read a user record\n");
//                System.out.print("4> Batch read tweets for a user\n");
//                System.out.print("5> Scan all tweets for all users\n");
//                System.out.print("6> Record UDF -- update user password\n");
//                System.out.print("7> Query tweets by username and users by tweet count range\n");
//                System.out.print("8> Stream UDF -- aggregation based on tweet count by region\n");
//                System.out.print("0> Exit\n");
//                System.out.print("\nSelect 0-8 and hit enter: ");
//
//                int feature = input.nextInt();
//                input.nextLine();
//
//                if (feature != 0) {
//                    switch (feature) {
//                        case 1:
//                            System.out.print("\n********** Your Selection: Create User **********\n");
//                            us.createUser();
//                            break;
//                        case 2:
//                            System.out.print("\n********** Your Selection: Create a Tweet **********\n");
//                            ts.createTweet();
//                            break;
//                        case 3:
//                            System.out.print("\n********** Your Selection: Read A User Record **********\n");
//                            us.getUser();
//                            break;
//                        case 4:
//                            System.out.print("\n********** Your Selection: Batch Read Tweets For A User **********\n");
//                            ts.batchGetUserTweets();
//                            break;
//                        case 5:
//                            System.out.print("\n********** Your Selection: Scan All Tweets For All Users **********\n");
//                            // ts.scanAllTweetsForAllUsers();
//                            break;
//                        case 6:
//                            System.out.print("\n********** Your Selection: Record UDF -- Update User Password **********\n");
//                            // us.updatePasswordUsingUDF();
//                            break;
//                        case 7:
//                            System.out.print("\n********** Your Selection: Query Tweets By Username And Users By Tweet Count Range **********\n");
//                            // ts.queryTweets();
//                            break;
//                        case 8:
//                            System.out.print("\n********** Your Selection: Stream UDF -- Aggregation Based on Tweet Count By Region **********\n");
//                            // us.aggregateUsersByTweetCountByRegion();
//                            break;
//                        case 12:
//                            System.out.print("\n********** Create Users **********\n");
//                            // us.createUsers();
//                            break;
//                        case 23:
//                            System.out.print("\n********** Create Tweets **********\n");
//                            // ts.createTweets();
//                            break;
//                        default:
//                            break;
//                    }
//                }
            }
        } catch (AerospikeException e) {
            System.out.print("AerospikeException - Message: " + e.getMessage() + "\n");
            System.out.print("AerospikeException - StackTrace: " + e + "\n");
        } catch (Exception e) {
            System.out.print("Exception - Message: " + e.getMessage() + "\n");
            System.out.print("Exception - StackTrace: " + e + "\n");
        } finally {
            if (tweetspikeClient != null && tweetspikeClient.isConnected()) {
                tweetspikeClient.close(); // Close Aerospike server connection
            }
            System.out.print("\n\nINFO: Press any key to exit...\n");
            //console.readLine();
        }
    }

    protected void finalize() throws Throwable {
        if (this.tweetspikeClient != null){
            this.tweetspikeClient.close();
        }
    }
}
