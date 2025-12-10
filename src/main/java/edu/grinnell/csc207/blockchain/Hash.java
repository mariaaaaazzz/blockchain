package edu.grinnell.csc207.blockchain;
import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private final byte[] data;

  
   /**
     * Constructs a Hash storing a copy of the given bytes.
     *
     * @param data the hash bytes
     */
    public Hash(byte[] data) {
        this.data = Arrays.copyOf(data, data.length);
    }


    /**
     * Returns a copy of the bytes.
     *
     * @return a new byte[] containing the hash
     */
    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }


    /**
     * returns true if this hash meets the criteria for validity
     *
     * @return true if valid; false otherwise
     */
    public boolean isValid() {
        return data.length >= 3 && data[0] == 0 && data[1] == 0 && data[2] == 0;
    }



    /**
     * Returns the hexadecimal string
     *
     * @return hex string of the hash bytes
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            int u = Byte.toUnsignedInt(b);
            sb.append(String.format("%02x", u));
        }
        return sb.toString();
    }


    /**
     * Checks if the hash structurally equal to the argument
     * @param other another object
     * @return true if this hash is structurally equal to the argument.
     */
    public boolean equals(Object other) {
        if (!(other instanceof Hash)) {
            return false;
        }
        Hash o = (Hash) other;
        return Arrays.equals(this.data, o.data);
    }

    /**
     * Hash code for data.
     *
     * @return hash code
     */
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}