import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author Anthony Poerio - (adp59@pitt.edu)
 * University of Pittsburgh, Spring 2016
 * CS1501 - Algorithm Implementation
 *
 * REQUIRED:  "dictionary.txt", must be a .txt file which contains a list of strings, separated my newLines.
 *             Each file should be a dictionary word which CANNOT be included in a valid password.
 *
 * USAGE:  javac pw_check.java -g
 *      --> To generate:
 *             1) "my_dictionary.txt", which contains all malformed substrings of words in "dictionary.txt"
 *             2) "good_passwords.txt", which contains all possible
 *
 *         javac pw_check.java
 *      --> Reads in "my_dictionary.txt" and "good_passwords.txt", and stores them in a DLB Trie.
 *          Then prompts the user for a password and tells her if it is valid, or invalid.
 *          If the password is invalid, the program will recommend 10 similar passwords,
 *          based on the longest valid prefix of a word which is in our Trie. If the password is valid,
 *          congratulate the user.
 */

public class pw_check
{
    public static void main(String[] args)
    {
        String userInput = "none";
        
        // GET USER INPUT FROM CMD LINE
        if (args.length > 0) { 
                userInput = args[0];
        }

        if (userInput.contains("-g")) {
            // If user specifies "-g", we generate the output files: "dictionary.txt" and "my_dictionary.txt"
            System.out.println("Generating every possible password using __BRUTE_FORCE__ !");
            System.out.println("......please wait.....");
            System.out.println("NOTE: on author's machine this took about 1min, 30 seconds.");


            DLBTrie dictionaryTry = new DLBTrie(); // Create a new Trie data structure
            dictionaryTry.readDictionary("dictionary.txt"); // Now we have a trie that has all dictionary words
            dictionaryTry.bruteForce();  // Create all possible permutations, prune, and write to file "dictionary.txt"

            // Create all malformed substrings, and write to "my_dictionary.txt"
            dictionaryTry.dictionaryPermutations(dictionaryTry.getDictArray());
            System.out.println("Passwords successfully created!");
            System.out.println("Please check the current directory for:");
            System.out.println();
            System.out.println("\t'good_passwords.txt'");
            System.out.println("\t'my_dictionary.txt'");

        } else if (userInput.equals("none")) {
            // If user does NOT specify any arguments, we want to check for valid passwords

            // Create a new Trie instance
            DLBTrie passwordsTry = new DLBTrie();

            // insert all words in a Trie "my_dictionary.java"
            String currentPasswordPermutation;

            // insert all words in a Trie "good_passwords.java"
            System.out.println("WELCOME:  This program will let you check if your password is valid, " +
                    "based on the following rules: \n" +
                    "\tMust be 5 characters long." +
                    "\n\t1-3 characters must be letters." +
                    "\n\t1-2 characters must be numbers."  +
                    "\n\t1-2 characters must be numbers symbols." +
                    "\n\tCannot contain any substrings of the 500 most used dictionary words,\n" +
                    "\t    including certain substitutions which this program will check.");
            System.out.println();
            System.out.println("------------------------------------------------------");
            System.out.println("PROGRAM LOADING PASSWORD DB INTO DE-LA BRIANDAIS TRIE ");
            System.out.println("       please wait. will take a few moments.          ");
            System.out.println("------------------------------------------------------");

            try {
                BufferedReader br = new BufferedReader(new FileReader("good_passwords.txt"));
                while ((currentPasswordPermutation = br.readLine()) != null) {
                    passwordsTry.insertPassword(currentPasswordPermutation);
                }
                br.close(); // close the buffered reader once we're done.
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            System.out.println();
            System.out.println("All data loaded successfully!!");
            System.out.println();

            // Open a scanner to keyboard and get user's input
            Scanner kb = new Scanner(System.in);
            boolean passwordValid = false;

            // Ask for password until a valid one is provided
            while(!passwordValid) {
                System.out.print("\nEnter a password to check for validity: ");
                String userPassword = kb.nextLine();

                if (userPassword.length()<1) {
                    System.out.println("Password must not be empty.");
                } else if (passwordsTry.hasPassword(userPassword)) {
                    System.out.println("Congratulations! You Have picked a valid password!");
                    passwordValid = true;
                } else {
                    // do this while the password is invalid
                    userPassword = passwordsTry.getLongestPrefix(userPassword);
                    String[] validPasswords = passwordsTry.returnCollection(userPassword);
                    System.out.println("Invalid password. Please enter a valid password. \n" +
                            "The following passwords are valid & share a common prefix with yours: " + userPassword);
                    for (String item : validPasswords) System.out.println(item);  // for-each loop
                    System.out.println("Please try again.");
                } // end-if-else (inner)

            } // end while

            // close scanner
            kb.close();
        } else {
            // For any other input (not -g or no args), give usage info.
            System.out.println("usage:  \"javac pw_check.java -g\" to generate data files. (\"dictionary.txt\"" +"must be in the same directory as\"pw_check.java\"");
            System.out.println("\"javac pw_check.java \" to check if your password is valid.");
        }  // end control-flow

    }

}
