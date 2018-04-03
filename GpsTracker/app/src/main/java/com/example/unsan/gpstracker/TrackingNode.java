package com.example.unsan.gpstracker;

/**
 * Created by Unsan on 27/3/18.
 */

public class TrackingNode {
    String node;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public TrackingNode(String node, Tracking tracking) {
        this.node = node;
        this.tracking = tracking;
    }

    public Tracking getTracking() {
        return tracking;
    }

    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }

    Tracking tracking;

}
