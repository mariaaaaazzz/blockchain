package edu.grinnell.csc207.blockchain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {
    // Hash: 
    @Test
    @DisplayName("Placeholder Test")
    public void placeholderTest() {
        assertEquals(2, 1 + 1);
    }


    @Test
    public void isValid() {
        byte[] bytes = new byte[32];
        bytes[0] = 0; 
        bytes[1] = 0; 
        bytes[2] = 1;
        Hash hash = new Hash(bytes);
        assertFalse(hash.isValid());
    }

    @Test
    public void equalsCompare() {
        byte[] a = new byte[] {1,2,3};
        byte[] b = new byte[] {1,2,3};
        assertTrue(new Hash(a).equals(new Hash(b)));
    }



    // Block:
    @Test
    public void toString_contains() throws Exception {
        Block g = new Block(0, 300, null);
        String s = g.toString();
        assertTrue(s.contains("Block 0"));
        assertTrue(s.contains("Amount: 300"));
        assertTrue(s.contains("Nonce: "));
        assertTrue(s.contains("hash: "));
    }

    // BlockChain:
    @Test
    public void construct_getSize() throws Exception {
        BlockChain bc = new BlockChain(300);
        assertEquals(1, bc.getSize());
        assertNotNull(bc.getHash());
    }
}
