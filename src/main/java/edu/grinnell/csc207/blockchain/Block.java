package edu.grinnell.csc207.blockchain;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {
    private final int num;
    private final int amount;
    private final Hash prevHash;
    private final int nonce;
    private final Hash hash;

    /**
     * Constructs and mines a block by searching nonce starting at 0.
     *
     * @param num      block number
     * @param amount   transaction amount
     * @param prevHash hash of previous block 
     * @throws NoSuchAlgorithmException if SHA-256 is unavailable
     */
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

            if (h.isValid()) {
                break;
            }
            n++;
        }

        nonce = n;
        hash = h;
    }


    /**
     * Constructs a block using a given nonce.
     *
     * @param num      block number
     * @param amount   transaction amount
     * @param prevHash previous block hash
     * @param nonce    nonce to use
     * @throws NoSuchAlgorithmException if SHA-256 is unavailable
     */
    public Block(int num, int amount, Hash prevHash, int nonce)
        throws NoSuchAlgorithmException {
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

    /**
     * @return the block number
     */
    public int getNum() { 
        return num; 
    }

    /**
     * @return the transaction amount in this block
     */
    public int getAmount() { 
        return amount; 
    }

    /**
     * @return the mined nonce
     */
    public int getNonce() { 
        return nonce; 
    }

    /**
     * @return the previous block's hash
     */
    public Hash getPrevHash() { 
        return prevHash; 
    }

    /**
     * @return this block's hash
     */
    public Hash getHash() { 
        return hash; 
    }


    /**
     * @return summary of the block in string
     */
    public String toString() {
        return "Block " + num
            + " (Amount: " + amount
            + ", Nonce: " + nonce
            + ", prevHash: " + String.valueOf(prevHash)
            + ", hash: " + hash.toString() + ")";
    }


    /**
     * Converts an int to a 4 byte array.
     *
     * @param x the int
     * @return 4 bytes representing x
     */
    private static byte[] intToBytes(int x) {
        return ByteBuffer.allocate(4).putInt(x).array();
    }
}