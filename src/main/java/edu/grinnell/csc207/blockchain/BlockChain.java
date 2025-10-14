package edu.grinnell.csc207.blockchain;
import java.security.NoSuchAlgorithmException;


/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {

    private static class Node {
        Block block;
        Node next;
        Node(Block b) { 
            this.block = b; 
        }
    }

    private Node first;
    private Node last;


    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, initial, null);
        first = new Node(firstBlock);
        last = first;
    }

  
    public Block mine(int amount) throws NoSuchAlgorithmException {
        int nextNum = last.block.getNum() + 1;
        return new Block(nextNum, amount, last.block.getHash());
    }


    public int getSize() {
        return last.block.getNum() + 1;
    }


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


    public Hash getHash() {
        return last.block.getHash();
    }


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
