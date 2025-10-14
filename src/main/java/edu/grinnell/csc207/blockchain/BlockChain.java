package edu.grinnell.csc207.blockchain;
import java.security.NoSuchAlgorithmException;


/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {

    /** Simple singly linked list node that wraps a Block. 
    */
    private static class Node {
        Block block;
        Node next;
        Node(Block b) { 
            this.block = b; 
        }
    }

    private Node first;
    private Node last;



    /**
     * Creates a chain with a single mined initial block holding the initial amount.
     *
     * @param initial the initial amount for Alice in the first block
     * @throws NoSuchAlgorithmException if SHA-256 is unavailable
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, initial, null);
        first = new Node(firstBlock);
        last = first;
    }

  
    /**
     * Mines a candidate block for the given transaction,
     * linked to the current last block.
     *
     * @param amount the transaction amount for the new block
     * @return a mined Block
     * @throws NoSuchAlgorithmException if SHA-256 is unavailable
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        int nextNum = last.block.getNum() + 1;
        return new Block(nextNum, amount, last.block.getHash());
    }

    /**
     * @return size of the chain
     */
    public int getSize() {
        return last.block.getNum() + 1;
    }


    /**
     * Appends a block to the end of the chain if it's valid
     *
     * @param block the block to append
     * @throws IllegalArgumentException if the block is not valid
     */
    public void append(Block block) {
        if (block.getNum() != last.block.getNum() + 1) {
            throw new IllegalArgumentException("Invalid block number.");
        }

        if (!last.block.getHash().equals(block.getPrevHash())) {
            throw new IllegalArgumentException("Invalid prevHash.");
        }

        if (!block.getHash().isValid()) {
            throw new IllegalArgumentException("Invalid block hash.");
        }


        int alice = 0;
        Node cur = first;
        while (cur != null) {
            alice += cur.block.getAmount();
            if (alice < 0) {
                throw new IllegalArgumentException("Alice would go negative.");
            }
            cur = cur.next;
        }
        alice += block.getAmount();
        if (alice < 0) {
            throw new IllegalArgumentException("Alice would go negative.");
        }


        Node n = new Node(block);
        last.next = n;
        last = n;
    }


    /**
     * Removes the last block if there is more than one block.
     *
     * @return true if a block was removed; false otherwise.
     */
    public boolean removeLast() {
        if (first == last) {
            return false;
        }
        Node cur = first;
        while (cur.next != last) {
            cur = cur.next;
        }
        cur.next = null;
        last = cur;
        return true;
    }


    /**
     * Returns the hash of the last block.
     *
     * @return the last block's hash
     */
    public Hash getHash() {
        return last.block.getHash();
    }


    /**
     * @return true if the chain is valid; false otherwise
     */
    public boolean isValidBlockChain() {
        if (first == null) return false;

        int alice = 0;
        Node prev = null;
        Node cur = first;
        int expected = 0;

        while (cur != null) {
            Block b = cur.block;


            if (b.getNum() != expected) {
                return false;
            }

            if (prev == null) {
                if (b.getPrevHash() != null) {
                    return false;
                }
            } else {
                if (!prev.block.getHash().equals(b.getPrevHash())) {
                    return false;
                }
            }

            if (!b.getHash().isValid()) {
                return false;
            }

            alice += b.getAmount();
            if (alice < 0) {
                return false;
            }

            prev = cur;
            cur = cur.next;
            expected++;
        }
        return true;
    }

    /**
     * Prints the balances: "Alice: X, Bob: Y".
     * Bob = initial - Alice.
     */
    public void printBalances() {
        int alice = 0;
        Node cur = first;
        while (cur != null) {
            alice += cur.block.getAmount();
            cur = cur.next;
        }
        int initial = first.block.getAmount();
        int bob = initial - alice;
        System.out.println("Alice: " + alice + ", Bob: " + bob);
    }


    /**
     * @return string representation of the chain
     */
    public String toString() {
        String s = "";
        Node cur = first;
        while (cur != null) {
            s += cur.block.toString();
            cur = cur.next;
        }
        return s.toString().trim();
    }
}
