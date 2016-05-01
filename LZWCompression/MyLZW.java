//import java.io.PrintWriter;
/******************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 ******************************************************************************/


/**
 *  The <tt>LZW</tt> class provides static methods for compressing
 *  and expanding a binary input using LZW compression over the 8-bit extended
 *  ASCII alphabet with 12-bit codewords.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/55compress">Section 5.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class MyLZW {
    private static final int R = 256;  // number of input chars
    // L and LW are no longer FINAL, they are now variables.
    private static int W = 9;         // codeword width, starts at 9, will go up to 16
    private static int L = (int) Math.pow(2, W);     // number of codewords = 2^W
    private static char mode = 'n';


    // Do not instantiate.
    private MyLZW() { }


    //////////////////////////////
    ////// COMPRESS METHODS //////
    //////////////////////////////

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses
     * them using LZW compression with 12-bit codewords; and writes the results
     * to standard output.
     */
    public static void compressMonTest() {
        //try {
        //    PrintWriter writer = new PrintWriter("cmpr_codes.txt", "UTF-8");


        boolean monitorMode = false;
        double numBitsRead = 0.0;
        double numBitsCompressed = 0.0;
        double compressRatio = 0.0;
        // New ratio
        double oldCompressRatio = 0.0;
        int counter = 0;

        int i=0;

        String input = BinaryStdIn.readString();  // Input is each individual bitString
        // This is the entire file
        // Write the value of our mode
        BinaryStdOut.write((int)mode, W);



        TST<Integer> st = new TST<Integer>();
        // This for-loop inserts the base 256 ASCII table to a Trie
        // The For Loop fills the codebook for as many as are available W-length bit code word


        for (i = 0; i < R; i++)
        {
            st.put("" + (char) i, i);   // once past 256, no longer have value in the codebook
        }
        int code = R+1;  // R is codeword for EOF
        // first time we touch code, it is 257


        // Keeps eating the file and putting it a trie, for as long as have bytes left to consume
        while (input.length() > 0) { // input is the full file, as a bitstream

            if (code==L && W < 16) {
                W++;
                L = (int) Math.pow(2,W);
               // System.err.println("W="+W);
                //BinaryStdOut.write(R, W);  // Tells us how big to expand
            }

            // figure out compression ratio, guarding against division by zero
            if (numBitsCompressed != 0.0) compressRatio = (numBitsRead/numBitsCompressed);

            if (code == (Math.pow(2,16)) && !monitorMode) {
               // System.err.println("ENTERED MONITOR MODE");
                monitorMode = true;
                oldCompressRatio = compressRatio;
            }

            //System.err.println("OLD CR="+oldCompressRatio);
            //System.err.println("NEW CR="+compressRatio);
            //System.err.println("COMPOSITE CR="+oldCompressRatio /compressRatio);
            // Check if we need to completely reset our codebook
            if (monitorMode && (oldCompressRatio/compressRatio > 1.1) )
            {
                 // System.err.println("BEFORE RESET, CODE IS: "+code);
                 // System.err.println("Resetting codebook");
                    st = new TST<Integer>();
                    for (i = 0; i <= R; i++)
                    {
                        st.put("" + (char) i, i);   // once past 256, no longer have value in the codebook
                        //codeCounter++;
                    }
                    W = 9;
                    L = (int) Math.pow(2,W);
                    code = R+1;  // R is codeword for EOF
                    i+=1;
                    monitorMode = false;
                    numBitsRead = 0;
                    compressRatio = 0;

                    oldCompressRatio = 0;
                 //   System.err.println("CODE AFTER RESETTING CODEBOOK IS: "+code);
                //System.err.println("AFTER RESETTING, COUNT IS:"+counter);
            }




            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            numBitsRead+=(8 * s.length());
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            numBitsCompressed += W;
            // Need W, because it tells us how many bits we're writing
            int t = s.length();      // t = length of longest prefix
            // Now, we know how much to reduce to reduce the input by
            if (t < input.length() && code < L) {
                st.put(input.substring(0, t + 1), code++); // adds the longest prefix and next byte to the
                             // Add s to symbol table.
          //      writer.println("("+counter+","+code+")");

            }

            // symbol table
            // Cuts the last substring out of the input, keeps t,
            // even though we add t+1 to the TST
            input = input.substring(t);            // Scan past s in input.

            counter++;
        }
        BinaryStdOut.write(R, W);  // Tells us how big to expand, last thing you write is always 256
        BinaryStdOut.close();
       // writer.close();
       // } catch (Exception e) {
       //     e.printStackTrace();
       // }

    }



    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses
     * them using LZW compression with 12-bit codewords; and writes the results
     * to standard output.
     */
    public static void compress() {
        String input = BinaryStdIn.readString();  // Input is each individual bitString
        // This is the entire file
        // Write the value of our mode
        BinaryStdOut.write((int)mode, W);

        TST<Integer> st = new TST<Integer>();
        // This for-loop inserts the base 256 ASCII table to a Trie
        // The For Loop fills the codebook for as many as are available W-length bit code word


        for (int i = 0; i < R; i++)
        {
            st.put("" + (char) i, i);   // once past 256, no longer have value in the codebook
        }
        int code = R+1;  // R is codeword for EOF
        // first time we touch code, it is 257


        // Keeps eating the file and putting it a trie, for as long as have bytes left to consume
        while (input.length() > 0) { // input is the full file, as a bitstream
            if (code==L && W < 16) {
                W++;
                L = (int) Math.pow(2,W);
                //BinaryStdOut.write(R, W);  // Tells us how big to expand
            }
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            // Need W, because it tells us how many bits we're writing
            int t = s.length();      // t = length of longest prefix
            // Now, we know how much to reduce to reduce the input by
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++); // adds the longest prefix and next byte to the
            // symbol table
            // Cuts the last substring out of the input, keeps t,
            // even though we add t+1 to the TST
            input = input.substring(t);            // Scan past s in input.

        }
        BinaryStdOut.write(R, W);  // Tells us how big to expand, last thing you write is always 256
        BinaryStdOut.close();
    }


    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses
     * them using LZW compression with 12-bit codewords; and writes the results
     * to standard output.
     */
    public static void compressReset() {
        String input = BinaryStdIn.readString();  // Input is each individual bitString
        // This is the entire file
        // Write the value of our mode
        BinaryStdOut.write((int)mode, W);
        int i = 0;
        TST<Integer> st = new TST<Integer>();
        // This for-loop inserts the base 256 ASCII table to a Trie
        // The For Loop fills the codebook for as many as are available W-length bit code word
        for (i = 0; i < R; i++)
        {
            st.put("" + (char) i, i);   // once past 256, no longer have value in the codebook
        }
        int code = R+1;  // R is codeword for EOF
        i+=1;

        // first time we touch code, it is 257


        // Keeps eating the file and putting it a trie, for as long as have bytes left to consume
        while (input.length() > 0) { // input is the full file, as a bitstream
            // check if we need to increase the size of our codebook
            if (code==L && W < 16) {
                W++;
                //System.err.println("W=" + W);
                L = (int) Math.pow(2,W);
            }
            // Check if we need to completely reset our codebook
            if (code == (int) Math.pow(2,16)) {
                st = new TST<Integer>();
                W = 9;
                L = (int) Math.pow(2,W);
                for (i = 0; i <= R; i++)
                {
                    st.put("" + (char) i, i);   // once past 256, no longer have value in the codebook
                    //codeCounter++;
                }
                code = R+1;  // R is codeword for EOF
                i+=1;
                //System.err.println("CODE AFTER RESETTING CODEBOOK IS: "+code);
            }

            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            // Need W, because it tells us how many bits we're writing
            int t = s.length();      // t = length of longest prefix
            // Now, we know how much to reduce to reduce the input by
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++); // adds the longest prefix and next byte to the
            // symbol table
            // Cuts the last substring out of the input, keeps t,
            // even though we add t+1 to the TST
            input = input.substring(t);            // Scan past s in input.

        }
        BinaryStdOut.write(R, W);  // Tells us how big to expand, last thing you write is always 256
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of 8-bit bytes from standard input; compresses
     * them using LZW compression with 12-bit codewords; and writes the results
     * to standard output.
     */
    public static void compressMonitor() {
        String input = BinaryStdIn.readString();  // Input is each individual bitString
        // This is the entire file
        boolean monitorMode = false;
        double numBitsRead = 0.0;
        double numBitsCompressed = 0.0;
        double compressRatio = 0.0;
        // New ratio
        double oldCompressRatio = 0.0;


        // Write the value of our mode
        BinaryStdOut.write((int)mode, W);
        int i = 0;
        TST<Integer> st = new TST<Integer>();
        // This for-loop inserts the base 256 ASCII table to a Trie
        // The For Loop fills the codebook for as many as are available W-length bit code word
        for (i = 0; i < R; i++)
        {
            st.put("" + (char) i, i);   // once past 256, no longer have value in the codebook
        }
        int code = R+1;  // R is codeword for EOF
        i+=1; // first time we touch code, it is 257

        // Keeps eating the file and putting it a trie, for as long as have bytes left to consume
        while (input.length() > 0) { // input is the full file, as a bitstream
            // check if we need to increase the size of our codebook
            if (code==L && W < 16) {
                W++;
                //System.err.println("W=" + W);
                L = (int) Math.pow(2,W);
            }
            // figure out compression ratio, guarding against division by zero
            if (numBitsCompressed != 0.0) compressRatio = numBitsRead / numBitsCompressed;


            // Check if we need to completely reset our codebook
            if (code == (int) Math.pow(2,16) || monitorMode == true) {
                if ( (oldCompressRatio / compressRatio) > 1.1 )
                {
                    //System.err.println("Resetting codebook");
                    st = new TST<Integer>();
                    for (i = 0; i <= R; i++)
                    {
                        st.put("" + (char) i, i);   // once past 256, no longer have value in the codebook
                        //codeCounter++;
                    }
                    W = 9;
                    L = (int) Math.pow(2,W);
                    code = R+1;  // R is codeword for EOF
                    i+=1;
                    monitorMode = false;
                    numBitsRead = 0;
                    compressRatio = 0;
                    //System.err.println("CODE AFTER RESETTING CODEBOOK IS: "+code);
                } else {
                    oldCompressRatio = compressRatio;
                    monitorMode = true;
                }
            }

            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            numBitsRead+=(8 * s.length());
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            numBitsCompressed += W;
            // Need W, because it tells us how many bits we're writing
            int t = s.length();      // t = length of longest prefix
            // Now, we know how much to reduce to reduce the input by
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++); // adds the longest prefix and next byte to the
            // symbol table
            // Cuts the last substring out of the input, keeps t,
            // even though we add t+1 to the TST
            input = input.substring(t);            // Scan past s in input.

        }
        BinaryStdOut.write(R, W);  // Tells us how big to expand, last thing you write is always 256
        BinaryStdOut.close();
    }


    //////////////////////////////
    /////// EXPAND METHODS ///////
    //////////////////////////////
    /**
     * Compress with reset
     */
    public static void monitorExpandTest() {
        //try {
           // PrintWriter writer = new PrintWriter("exp_codes.txt", "UTF-8");

            //System.err.println("IN MONITOR EXPAND");
        String[] st = new String[(int)Math.pow(2,16)]; // Get a string array with our codebook
        int i; // next available codeword value
        int counter = 0;

        // initialize symbol table with all 1-character strings
        // Puts all of ASCII table into the codebook
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        // puts a blank at arr[256] in the array, and it's
        st[i++] = "";                         // (unused) lookahead for EOF, --OR-- end of array of bit size
        // And i is 257, so our next codeword will be 257
        // SO WE SET CODEWORD=First ASCII table character that isn't a special case
        int codeword = BinaryStdIn.readInt(W); // okay, we're reading in 9 bits
        // if (codeword == R) return;             // expanded message is empty string
        // we'll need to change this so we can tell if we need to expand the bit range
        // if we get here, that means that we're at end of file
        String val = st[codeword];

        // CREATE MONITOR EXPAND VARIABLES
        boolean monitorMode = false;
        double numBitsRead = 0.0;
        double numBitsCompressed = 0.0;
        double compressRatio = 0.0;
        // Old ratio
        double oldCompressRatio = 0.0;

        // Expand
        while (true) {

            // Check if we need to completely reset our codebook
            if (numBitsCompressed  != 0.0) compressRatio = numBitsRead/numBitsCompressed;

            if (i == (Math.pow(2,16)-1) && !monitorMode) {
                monitorMode = true;
                oldCompressRatio = compressRatio;
            }

            // WRITE OUT OUR VALUE
           // BinaryStdOut.write(val);
            // figure out compression ratio, guarding against division by zero
            //System.err.println("OLD CR="+oldCompressRatio);
            //System.err.println("NEW CR="+compressRatio);
            //System.err.println("COMPOSITE CR="+oldCompressRatio /compressRatio);


            // Check if we need to RESET codebook entirely
            if ( monitorMode && (oldCompressRatio/compressRatio > 1.1)) {
                //System.err.println("BEFORE RESET, CODE IS: "+i);
                //System.err.println("DO I GET INTO MONITOR MODE SECTION?");
                //System.err.println("Compression ratio too high. Resetting codebook.");
                //System.err.println("DO I GET HERE?");
                st = new String[(int)Math.pow(2,16)];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;

                // puts a blank at arr[256] in the array, and it's
                W = 9;
                L = (int) Math.pow(2,W);
                // Puts all of ASCII table into the codebook
                st[i++] = "";                         // (unused) lookahead for EOF, --OR-- end of array of bit size
                // And i is 257, so our next codeword will be 257
                //codeword = 258;
                codeword = BinaryStdIn.readInt(W);
                // SO WE SET CODEWORD=First ASCII table character that isn't a special case
                //codeword = BinaryStdIn.readInt(W); // okay, we're reading in 9 bits
                //if (codeword == R) return;
                val = st[codeword];
                monitorMode = false;
                numBitsRead = 0;
                numBitsCompressed = 0;
                compressRatio = 0;

                //System.err.println("CODEWORD AFTER RESET="+codeword);
                //System.err.println("COUNTER="+counter);
                //System.err.println("i="+i);
                //System.err.println("val="+val);
            }

            //if (counter>65279) System.err.println("CODEWORD: "+codeword);

            // Check if we need to increase the size of our codebook
            if (i== (L-1) && W<16) {
                W++;
                L = (int) Math.pow(2,W);
                //System.err.println("W="+W);
            }

            BinaryStdOut.write(val);

            // Need to adapt to know that we're done with x=amount of bits, and need to start the next level
            //BinaryStdOut.write(val);  // write current string val
            codeword = BinaryStdIn.readInt(W); // read a codeword from the input
            numBitsCompressed += W;
            //System.err.println("CODEWORD"+codeword);
            if (codeword == R) break;

            String s = st[codeword]; // s=the value associated with x in the symbol table
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            // take old value, add in first char of the new prefix we took in
            if (i < L) {
                st[i++] = val + s.charAt(0); // builds the symbol table with new prefixes
                //writer.println("("+val+","+i+")");
            }
            val = s; // set current value to s
            numBitsRead += (8 * val.length());

            counter++;
        }
        BinaryStdOut.close();
        //    writer.close();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

    }



    /**
     * Compress with reset
     */
    public static void monitorExpand() {
        //System.err.println("IN MONITOR EXPAND");
        String[] st = new String[(int)Math.pow(2,16)]; // Get a string array with our codebook
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        // Puts all of ASCII table into the codebook
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        // puts a blank at arr[256] in the array, and it's
        st[i++] = "";                         // (unused) lookahead for EOF, --OR-- end of array of bit size
        // And i is 257, so our next codeword will be 257
        // SO WE SET CODEWORD=First ASCII table character that isn't a special case
        int codeword = BinaryStdIn.readInt(W); // okay, we're reading in 9 bits
        // if (codeword == R) return;             // expanded message is empty string
        // we'll need to change this so we can tell if we need to expand the bit range
        // if we get here, that means that we're at end of file
        String val = st[codeword];

        // CREATE MONITOR EXPAND VARIABLES
        boolean monitorMode = false;
        double numBitsRead = 0.0;
        double numBitsCompressed = 0.0;
        double compressRatio = 0.0;
        // Old ratio
        double oldCompressRatio = 0.0;

        // Expand
        while (true) {
            // Check if we need to completely reset our codebook
            if (numBitsCompressed !=0) compressRatio = numBitsRead/compressRatio;


            // Check if we need to increase the size of our codebook
            if (i>= (L-1) && W<16) {
                W++;
                L = (int) Math.pow(2,W);
                //System.err.println("W="+W);
            }

            // WRITE OUT OUR VALUE
            BinaryStdOut.write(val);

            // Check if we need to RESET codebook entirely
            if (i == (int) (Math.pow(2,16)) || monitorMode == true ) {
                //System.err.println("DO I GET INTO MONITOR MODE SECTION?");


                if ( (oldCompressRatio/compressRatio) > 1.1 )
                {
                    //System.err.println("Compression ratio too high. Resetting codebook.");
                    st = new String[(int)Math.pow(2,16)];
                    for (i = 0; i < R; i++)
                        st[i] = "" + (char) i;
                    // puts a blank at arr[256] in the array, and it's
                    W = 9;
                    L = (int) Math.pow(2,W);
                    // Puts all of ASCII table into the codebook
                    st[i++] = "";                         // (unused) lookahead for EOF, --OR-- end of array of bit size
                    // And i is 257, so our next codeword will be 257
                    codeword = BinaryStdIn.readInt(W);
                    val = st[codeword];
                    monitorMode = false;
                    numBitsRead = 0;
                    numBitsCompressed = 0;
                }

                else
                {
                    oldCompressRatio = compressRatio;
                    monitorMode = true;
                }
            }


            // Need to adapt to know that we're done with x=amount of bits, and need to start the next level
            //BinaryStdOut.write(val);  // write current string val
            codeword = BinaryStdIn.readInt(W); // read a codeword from the input
            numBitsCompressed += W;
            if (codeword == R) break;
            String s = st[codeword]; // s=the value associated with x in the symbol table
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            // take old value, add in first char of the new prefix we took in
            if (i < L) st[i++] = val + s.charAt(0); // builds the symbol table with new prefixes
            val = s; // set current value to s
            numBitsRead += (8 * val.length());
        }
        BinaryStdOut.close();
    }

    /**
     * Compress with reset
     */
    public static void resetExpand() {
        String[] st = new String[(int)Math.pow(2,16)]; // Get a string array with our codebook
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        // Puts all of ASCII table into the codebook
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        // puts a blank at arr[256] in the array, and it's
        st[i++] = "";                         // (unused) lookahead for EOF, --OR-- end of array of bit size
        // And i is 257, so our next codeword will be 257
        // SO WE SET CODEWORD=First ASCII table character that isn't a special case
        int codeword = BinaryStdIn.readInt(W); // okay, we're reading in 9 bits
        if (codeword == R) return;             // expanded message is empty string

        // we'll need to change this so we can tell if we need to expand the bit range
        // if we get here, that means that we're at end of file
        String val = st[codeword];

        // Expand
        while (true) {
            // Check if we need to completely reset our codebook
            if (i == (int) (Math.pow(2,16) - 1) ) {
                //System.err.println("DO I GET HERE?");
                st = new String[(int)Math.pow(2,16)];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                // puts a blank at arr[256] in the array, and it's
                W = 9;
                L = (int) Math.pow(2,W);
                // Puts all of ASCII table into the codebook
                st[i++] = "";                         // (unused) lookahead for EOF, --OR-- end of array of bit size
                // And i is 257, so our next codeword will be 257
                codeword = BinaryStdIn.readInt(W);
                // SO WE SET CODEWORD=First ASCII table character that isn't a special case
                //codeword = BinaryStdIn.readInt(W); // okay, we're reading in 9 bits
                //if (codeword == R) return;
                val = st[codeword];
                //System.err.println("CODEWORD IS: "+codeword);
                //System.err.println("VAL IS: " + val);
            }

            // Check if we need to increase the size of our codebook
            if (i== (L-1) && W<=16) {
                W++;
                //System.err.println("W="+W);
                L = (int) Math.pow(2,W);
            }
            // Need to adapt to know that we're done with x=amount of bits, and need to start the next level
            BinaryStdOut.write(val);  // write current string val
            codeword = BinaryStdIn.readInt(W); // read a codeword from the input
            if (codeword == R) break;
            String s = st[codeword]; // s=the value associated with x in the symbol table
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            // take old value, add in first char of the new prefix we took in
            if (i < L) st[i++] = val + s.charAt(0); // builds the symbol table with new prefixes
            val = s; // set current value to s
        }
        BinaryStdOut.close();
    }

    /**
     * Route the expansion
     */
    public static void routeExpand() {

        // CHECK WHICH MODE WE'RE IN
        int codeword = BinaryStdIn.readInt(W); // okay, we're reading in 9 bits
        //System.err.println("CODEWORD: "+ codeword);
        if (codeword == R) return; // expanded message is empty string
        else if (codeword == 'r')
        {
            // if i=l-z, and w==16, reset codebook. symbol table == new string
            resetExpand();
        } // send to reset mode
        else if (codeword == 'm')
        {
            monitorExpandTest();
        }
        // send to monitor mode
        else if (codeword == 'n') {
            expand();// continue on to normal mode
        }
        else expand();
    }
    /**
     * Reads a sequence of bit encoded using LZW compression with
     * 12-bit codewords from standard input; expands them; and writes
     * the results to standard output.
     */
    public static void expand() {
        String[] st = new String[(int)Math.pow(2,16)]; // Get a string array with our codebook
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        // Puts all of ASCII table into the codebook
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        // puts a blank at arr[256] in the array, and it's
        st[i++] = "";                         // (unused) lookahead for EOF, --OR-- end of array of bit size
        // And i is 257, so our next codeword will be 257
        // SO WE SET CODEWORD=First ASCII table character that isn't a special case
        int codeword = BinaryStdIn.readInt(W); // okay, we're reading in 9 bits
        if (codeword == R) return;             // expanded message is empty string

        // we'll need to change this so we can tell if we need to expand the bit range
        // if we get here, that means that we're at end of file
        String val = st[codeword];

        while (true) {
            if (i== (L-1) && W<16) {
                W++;
                L = (int) Math.pow(2,W);
            }
            // Need to adapt to know that we're done with x=amount of bits, and need to start the next level
            BinaryStdOut.write(val);  // write current string val
            codeword = BinaryStdIn.readInt(W); // read a codeword from the input
            if (codeword == R) break;
            String s = st[codeword]; // s=the value associated with x in the symbol table
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            // take old value, add in first char of the new prefix we took in
            if (i < L) st[i++] = val + s.charAt(0); // builds the symbol table with new prefixes
            val = s; // set current value to s
        }
        BinaryStdOut.close();
    }


    //////////////////////////////
    ////// MAIN ENTRY POINT //////
    //////////////////////////////
    /**
     * Sample client that calls <tt>compress()</tt> if the command-line
     * argument is "-" an <tt>expand()</tt> if it is "+".
     */
    public static void main(String[] args) {

        if (args[0].equals("-")) {
            mode = args[1].charAt(0); // store which mode we are in
            if (args[1].equals("n")) compress();
            else if (args[1].equals("r")) compressReset();
            else if (args[1].equals("m")) compressMonTest();
        }
        else if (args[0].equals("+")) routeExpand();
        else throw new IllegalArgumentException("Illegal command line argument");
        // tells us:  args[0] = +/-
        //            args[1] = mode, n/r/m

        //for (String arg : args ) System.err.println("ARG=" + arg);

    }

}
