package com.example.blockchain_app;


public class Block {
    public final int index;
    public final long timestamp;
    public final String action;     // "REGISTER", "SET_LOST", "GENESIS"
    public final String imeiHash;   // your SHA-256 hex (64 chars)
    public final String ownerName;  // only used for REGISTER
    public final boolean isLost;    // used for SET_LOST
    public final String prevHash;
    public final String hash;

    private Block(int index, long timestamp, String action,
                  String imeiHash, String ownerName, boolean isLost,
                  String prevHash, String hash) {
        this.index = index;
        this.timestamp = timestamp;
        this.action = action;
        this.imeiHash = imeiHash;
        this.ownerName = ownerName;
        this.isLost = isLost;
        this.prevHash = prevHash;
        this.hash = hash;
    }

    public static Block genesis() {
        long now = System.currentTimeMillis();
        Block temp = new Block(0, now, "GENESIS", "", "", false, "0", "");
        String h = temp.computeHash();
        return new Block(0, now, "GENESIS", "", "", false, "0", h);
    }

    public static Block create(int index, String action, String imeiHash,
                               String ownerName, boolean isLost, String prevHash) {
        long now = System.currentTimeMillis();
        Block temp = new Block(index, now, action, imeiHash, ownerName, isLost, prevHash, "");
        String h = temp.computeHash();
        return new Block(index, now, action, imeiHash, ownerName, isLost, prevHash, h);
    }

    public String computeHash() {
        String data = index + "|" + timestamp + "|" + action + "|" + imeiHash + "|" +
                ownerName + "|" + isLost + "|" + prevHash;
        return HashUtil.sha256(data);
    }



}
