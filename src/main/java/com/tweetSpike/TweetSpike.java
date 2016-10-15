package com.tweetSpike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;

//**
public class TweetSpike {
    private AerospikeClient tweetSpikeClient;

    public TweetSpike(String hostIP, int hostPort)
    {
        ClientPolicy cPolicy = new ClientPolicy();
        cPolicy.timeout = 500;
        tweetSpikeClient = new AerospikeClient(cPolicy, hostIP, hostPort);
    }

    //Maybe pass parameters
    protected void finalize() throws Throwable {
        if (this.tweetSpikeClient != null){
            this.tweetSpikeClient.close();
        }
    }
}
