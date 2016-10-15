package com.tweetSpike;


public class AerospikeApplication
{
    String hostIP = "192.168.0.2";
    int hostPort = 3000;
    TweetSpike tweetSpike = new TweetSpike(hostIP, hostPort);

    public static void main( String[] args )
    {
       AerospikeApplication app = new AerospikeApplication();
       app.run();
    }

    public void run()
    {
        tweetSpike = new TweetSpike(hostIP, hostPort);
        tweetSpike.menu();
    }


}
