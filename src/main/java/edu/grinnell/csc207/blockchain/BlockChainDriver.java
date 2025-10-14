package edu.grinnell.csc207.blockchain;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;


/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {
   
    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        int initial = Integer.parseInt(args[0]);
        BlockChain chain = new BlockChain(initial);
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println(chain.toString());
            System.out.print("Command? ");
            String command = in.next();

            if (command.equals("help")) {
                System.out.println("Valid commands:");
                System.out.println("    mine: discovers the nonce for a given transaction");
                System.out.println("    append: appends a new block onto the end of the chain");
                System.out.println("    remove: removes the last block from the end of the chain");
                System.out.println("    check: checks that the block chain is valid");
                System.out.println("    report: reports the balances of Alice and Bob");
                System.out.println("    help: prints this list of commands");
                System.out.println("    quit: quits the program");

            } else if (command.equals("mine")) {
                System.out.print("Amount transferred? ");
                int amt = in.nextInt();
                Block candidate = chain.mine(amt);
                System.out.println("amount = " + amt + ", nonce = " + candidate.getNonce());

            } else if (command.equals("append")) {
                System.out.print("Amount transferred? ");
                int amt = in.nextInt();
                System.out.print("Nonce? ");
                int nonce = in.nextInt();

                int nextNum = chain.getSize();
                Hash prev = chain.getHash();
                Block block = new Block(nextNum, amt, prev, nonce);
                chain.append(block);

            } else if (command.equals("remove")) {
                chain.removeLast();

            } else if (command.equals("check")) {
                System.out.println(chain.isValidBlockChain() ? "Chain is valid!" : "Chain is invalid!");

            } else if (command.equals("report")) {
                chain.printBalances();

            } else if (command.equals("quit")) {
                break;

            } else {
                System.out.println("Invalid command. Type 'help' for options.");
            }
            in.nextLine();
        }
        in.close();
    }
}
