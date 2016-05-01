import java.io.*;
import java.math.BigInteger;


/**
 * Class used to very signatures.
 *
 * Takes in two command line args, and a flag to specify whether it should:
 *    1.  SIGN  (s)
 *    2.  VERIFY (v)
 */
public class MySign {
    static String operationSelection;
    static BigInteger D, N, E;
    static String[] filePath = new String[10];
    static BigInteger fileHash;
    static BigInteger decryptedHash;
    static int fileLengthInBytes;

    public static void sign(){
        //System.out.println("SIGNING FILE");
        //System.out.println("==============");
        // program used for signing
        // i. if called to sign (e.g. - "java mySign s myFile.txt") your program should:
        //      a.  Generate a SHA-256 hash of the contents of the provided file (e.g. "myfile.txt")
                HashEx.main(filePath);
        //      b.  "decrypt" this hash value using the private key stored in privkey.rsa
        //           (i.e., raise it to the Dth power mod N)
        //            - Program should exit and display an error IF privkey.rsa is not found in current directory
                fileHash = HashEx.hashValue;
                // System.out.println("FILE HASH ON SIGN: " + fileHash);
                decryptedHash = fileHash.modPow(D, N);
                // System.out.println("DECRYPTED HASH ON SIGN: " + decryptedHash);
                fileLengthInBytes = HashEx.digest.length;
        //      c.  Write out a signed version of the file (e.g., "myfile.txt.signed) that contains:
        //            - The contents of the original file
        //            - The "decrypted" hash of the the original file
                try {
                    File inputFile = new File(filePath[0]);

                    // Write our file to a new file with signed and print each object
                    FileOutputStream out = new FileOutputStream(filePath[0] + ".signed");
                    // write our data first
                    ObjectOutputStream oout = new ObjectOutputStream(out);
                    // write something in the file
                    oout.writeObject(HashEx.data);
                    oout.writeObject(decryptedHash);

                    // close the stream
                    oout.close();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    public static void verify(){
        //System.out.println("VERIFYING FILE");
        //System.out.println("==============");
        // program used for verifying
        byte[] fileToVerify;
        BigInteger decryptedHashToCheck;
        BigInteger verifiedFileHash;
        BigInteger reEncryptedHash;

        // ii.  If called to verify (e.g., "java MySign v myfile.txt.signed") your program should:
        //      a.   Read the contents of the original file
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(filePath[0]));
            // read and print what we wrote before
            fileToVerify = (byte[]) ois.readObject();
            decryptedHashToCheck = (BigInteger) ois.readObject();
            //System.out.println("DECRYPTED HASH TO CHECK=   "+decryptedHashToCheck);
            ois.close();

            // write the file object as an intermediary file object
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream("checkFile.bin"));
                    oos.writeObject(fileToVerify);
            oos.close();


            // read back our contents, this time we ONLY have the file,
            // this means, we can hash it without having to worry about
            // the signature throwing off our verification
            ObjectInputStream checkStream =
                    new ObjectInputStream(new FileInputStream("checkFile.bin"));
                    filePath[0] = "checkFile.bin";
            //      b.   Generate a SHA-256 hash of the contents of the original file
            HashExVerify.main(fileToVerify);
            verifiedFileHash = HashExVerify.hashValue;
            //System.out.println("VERIFIED FILE HASH=   "+verifiedFileHash);
            checkStream.close();

            // delete our intermediary file
            File binFile = new File("checkFile.bin");
            binFile.delete();

            //      c.   Read the "decrypted" hash of the original file
            //   --> stored in "decryptedHashToCheck"
            //      d.  "encrypt" this value with the contents of pubkey.rsa (i.e. - raise it to the Eth power mod N)
            //            - Program should exit and display error if pubkey.rsa is not found in the current directory
            reEncryptedHash = decryptedHashToCheck.modPow(E, N);
            // System.out.println("RE-ENCRYPTED HASH=   "+reEncryptedHash);

            //      e.   Compare these two hash values (the one newly generated and the one that was just "encrypted")
            //           and print out to console whether or not the signature is valid (i.e. - whether or not the
            //           values are the same).
            boolean verificationResult = reEncryptedHash.equals(verifiedFileHash);
            System.out.println("SIGNATURE TEST RESULT: ");
            System.out.println("==============");
            System.out.println("The test for verification returned:  " + verificationResult);
            if (verificationResult) {
                System.out.println("\t SIGNATURE IS VALID");
                System.out.println("\t This file matches the one that was signed. It's okay to use. Proceed.");
            } else {
                System.out.println("\t SIGNATURE IS **NOT** VALID");
                System.out.println("\t THIS FILE MAY HAVE BEEN TAMPERED WITH.");
                System.out.println("\t Be careful.");
            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("ERROR: "+ filePath[0] +" was not found in the current directory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readKeys() {
        // create an ObjectInputStream for the file we created before
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream("privkey.rsa"));

            // read and print what we wrote before
            D = (BigInteger) ois.readObject();
            //System.out.println("" + (BigInteger) ois.readObject());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("ERROR:  privkey.rsa NOT found in the current directory.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream("pubkey.rsa"));

            // read and print what we wrote before
            E = (BigInteger) ois.readObject();
            // System.out.println("" + (BigInteger) ois.readObject());
            // System.out.println("D Objects are equal="+E.equals((BigInteger) ois.readObject()));
            // System.out.println();
            // System.out.println();
            N = (BigInteger) ois.readObject();
            //System.out.println("" + (BigInteger) ois.readObject());
            //System.out.println("N Objects are equal="+N.equals((BigInteger) ois.readObject()));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("ERROR:  pubkey.rsa NOT found in the current directory.");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        //System.out.println("E=  "+E);
        //System.out.println("D=  "+D);
        //System.out.println("N=  "+N);
    }


    /**
     * Main class.
     * Takes in cmd line args
     * @param args
     */
    public static void main(String[] args) {
        // check if user passed in (s) or (v)
        operationSelection = args[0];

        // get path for the file we are checking
        filePath[0] = args[1];

        // read in our key values
        readKeys();

        // check if file is signed
        boolean fileIsSigned = filePath[0].contains("signed");

        if (!fileIsSigned && operationSelection.contains("s")) {
            MySign.sign();
        } else if(!fileIsSigned && operationSelection.contains("v")) {
            System.out.println("ERROR:  File passed in has not yet been signed.");
            System.out.println("\t Please sign the file before trying to verify.");
        } else if(operationSelection.contains("v")) {
            MySign.verify();
        } else {
            System.out.println("To sign a file: ");
            System.out.println("----------------");
            System.out.println("\tUsage: \"java mySign s myFile.txt\" ");
            System.out.println();
            System.out.println("To verify a file: ");
            System.out.println("-------------------");
            System.out.println("\tUsage: \"java mySign v myFile.txt.signed\" ");
        }



    }


}
