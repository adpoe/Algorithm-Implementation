
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.math.BigInteger;
import java.security.MessageDigest;

public class HashExVerify {
    static  BigInteger hashValue;
    static byte[] digest;
    public static void main(byte[] data) {

        // lazily catch all exceptions...
        try {

            // create class instance to create SHA-256 hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // process the file
            md.update(data);
            // generate a hash of the file
            digest = md.digest();

            // convert the bite string to a printable hex representation
            // note that conversion to biginteger will remove any leading 0s in the bytes of the array!
            String result = new BigInteger(1, digest).toString(16);
            hashValue = new BigInteger(1, digest);

            // print the hex representation
            //System.out.println("___hash__EX___ string=" + result);
            //System.out.println("hash_EX___ Big Int=" + hashValue);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
