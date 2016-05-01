
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.math.*;
import java.util.Random;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by tony on 4/14/16.
 */
public class MyKeyGen {
    static BigInteger P, Q, E, D, N, PHI_N;

    public static void generateKeyPair() {
        // Create new random number generator
        Random rnd = new Random();
        // define the bit-length
        int bitLength = 512;

        // Steps:
        // i.   To generate a keypair
        //     a.  Pick P and Q to be random primes of an appropriate size to generate a 1024 bit key
        // Pick P
        P = new BigInteger(bitLength, 1, rnd);
        // Pick Q
        Q = new BigInteger(bitLength, 1, rnd);
        //     b.  Generate N as P * Q
        N = P.multiply(Q);
        //     c.  Generate PHI(N) as (P-1) * (Q-1)
        PHI_N = ( P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE)) );

        //     d.  Pick E such that 1 < E < PHI(N) and GCD(E, PHI(N)) = 1
        //             (E must NOT divide PHI(N) evenly)
        boolean E_IsValid = false;
        E = new BigInteger("65573");
        // ensures we have picked E appropriately
        while (!E_IsValid) {
            if (E.gcd(PHI_N).equals(BigInteger.ONE)) {
                E_IsValid = true;
            } else {
                E = new BigInteger(bitLength, 1, rnd);
            } // end-if
        } // end-while

        //     e.  Pick D such that D = E^-1 mod PHI(N)
        //
        D = E.modInverse(PHI_N);
        // ii.   After generating E, D, N:
        //     a.  Save E and N to pubkey.rsa
        try {
            // create a new file with an ObjectOutputStream
            FileOutputStream out = new FileOutputStream("pubkey.rsa");
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // write something in the file
            oout.writeObject(E);
            oout.writeObject(N);

            // close the stream
            oout.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //     b.  Save D and N to privkey.rsa
        try {
            // create a new file with an ObjectOutputStream
            FileOutputStream out = new FileOutputStream("privkey.rsa");
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // write something in the file
            oout.writeObject(D);
            oout.writeObject(N);

            // close the stream
            oout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generateKeyPair();
    }

}
