package edu.grinnell.csc207.blockchain;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;

/**
 * A single block of a blockchain.
 */
public class Block {
    private final int num;
    private final int amount;
    private final Hash prevHash;
    private final int nonce;
    private final Hash hash;

    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;

        MessageDigest md;
        int n = 0;
        Hash h;

        while (true) {
            md = MessageDigest.getInstance("SHA-256");
            md.update(intToBytes(num));
            md.update(intToBytes(amount));

            if (prevHash != null) {
                md.update(prevHash.getData());
            }
            
            md.update(intToBytes(n));
            h = new Hash(md.digest());

            if (h.isValid()){
                break;
            }
            n++;
        }

        nonce = n;
        hash = h;
    }

    public Block(int num, int amount, Hash prevHash, int nonce) throws NoSuchAlgorithmException {
        this.num = num;
        this.amount = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(intToBytes(num));
        md.update(intToBytes(amount));
        if (prevHash != null) {
            md.update(prevHash.getData());
        }
        md.update(intToBytes(nonce));

        this.hash = new Hash(md.digest());
    }

    public int getNum() { 
        return num; 
    }

    public int getAmount() { 
        return amount; 
    }
    public int getNonce() { 
        return nonce; 
    }
    public Hash getPrevHash() { 
        return prevHash; 
    }
    public Hash getHash() { 
        return hash; 
    }

    public String toString() {
        return "Block " + num +
        " (Amount: " + amount +
        ", Nonce: " + nonce +
        ", prevHash: " + String.valueOf(prevHash) +
        ", hash: " + hash.toString() + ")";
    }

    private static byte[] intToBytes(int x) {
        return ByteBuffer.allocate(4).putInt(x).array();
    }
}