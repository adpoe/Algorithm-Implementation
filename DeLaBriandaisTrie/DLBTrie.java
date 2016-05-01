import java.io.*;
import java.io.IOException;
import java.io.FileReader;

/**
 * @Author Anthony Poerio - (adp59@pitt.edu)
 * University of Pittsburgh, Spring 2016
 * CS1501 - Algorithm Implementation
 * DLBTrie Class
 */
public class DLBTrie
{
    private DLBNode currentNode;
    private DLBNode root;            // Reference to head of the list
    private String[] dictArray;      // An array of strings which we have read from our inFile

    ////////////////////////
    //// CONSTRUCTORS //////
    ////////////////////////

    /**
     * No args constructor for initializing a DLB Trie
     */
    DLBTrie() {
        this.root=new DLBNode();
        this.dictArray=null;

    }

    ///////////////////////////
    ////// BRUTE FORCE ////////
    ///////////////////////////

    /**
     * Brute force algorithm to generate all 5-character permutations
     * of a string given these parameters:
     *    1. 1-3 chars are letters
     *    2. 1-2 chars are numbers
     *    3. 1-2 chars are symbols
     *    4. Password doesn't match dictionary words or their permutations with substitutions:
     *         - 7 => t
     *         - 4 => a
     *         - 0 =  o
     *         - 3 => e
     *         - 1 => i
     *         - $ => $
     * Write it all to a file, static, within the current directory.
     * @return A reference to the FILE where everything is written.
     */
    public File bruteForce()
    {

        PrintWriter outputFile = null;
        char[] dataSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m',// First 13 chars
                'n', 'o','p', 'q','r','s','t','u','v','w','x','y','z', '1', // End set of all valid characters
                '2','3','4','5','6','7','8','9','0', // End set of all valid numbers
                '@','!','$','^','_','*' }; // End data set
        int letterCount = 0;
        int symbolCount = 0;
        int numberCount = 0;

        // Open file for writing
        try {
            outputFile = new PrintWriter("good_passwords.txt");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        // Use a counting based system to get all permutations within our data set
        int tenThousands = 0;
        int thousands = 0;
        int hundreds = 0;
        int tens = 0;
        int ones = 0;


        int counter = 0;
        while (true)
        {

            // value of our incrementing number in 'ones' place
            ones=counter;

            // Handle reset
            if (counter==42) {
                counter = -1;
            }

            // Chain of number incrementations
            if (ones==42) {
                tens++;

                ones=0;
            }

            if (tens==42) {
                hundreds++;

                tens=0;
                ones=0;
            }

            if (hundreds==42) {
                thousands++;

                hundreds=0;
                tens=0;
                ones=0;
            }

            if (thousands==42) {
                tenThousands++;

                thousands=0;
                hundreds=0;
                tens=0;
                ones=0;
            }

            // Determines how many letters are in the password
            if(tenThousands < 26)letterCount++;
            if(thousands < 26)letterCount++;
            if(hundreds < 26)letterCount++;
            if(tens < 26)letterCount++;
            if(ones < 26)letterCount++;

            // Determines how many numbers are in the password
            if((tenThousands >= 26) && (tenThousands <= 35))numberCount++;
            if((thousands >= 26) && (thousands <= 35))numberCount++;
            if((hundreds >= 26) && (hundreds <= 35))numberCount++;
            if((tens >= 26) && (tens <= 35))numberCount++;
            if((ones >= 26) && (ones <= 35))numberCount++;

            // Determines how many symbols are in the password
            if((tenThousands >= 36) && (tenThousands <= 41))symbolCount++;
            if((thousands >= 36) && (thousands <= 41))symbolCount++;
            if((hundreds >= 36) && (hundreds <= 41))symbolCount++;
            if((tens >= 36) && (tens <= 41))symbolCount++;
            if((ones >= 36) && (ones <= 41))symbolCount++;

            // Once we overflow, we're done!
            if(tenThousands == 42) break;

            // If the password meets the character, symbol, and number requirements
            if( ( (letterCount >= 1) && (letterCount <= 3) ) && ( (numberCount >= 1) && (numberCount <= 2) ) && ( (symbolCount >= 1) && (symbolCount <= 2) ) )
            {

                // set output value in a literal
                char [] output = {dataSet[tenThousands], dataSet[thousands], dataSet[hundreds],dataSet[tens],dataSet[ones]};

                // Build a string from each character value within that literal, in order
                String passwordPermutation = "";
                for (int print = 0; print < 5; print++)
                {

                    passwordPermutation = passwordPermutation + output[print];
                    //System.out.print(output[print]);
                }


                // And if the string is not a match for any dictionary words or their permutations
                if (!isDictionaryMatch(passwordPermutation))
                {
                    outputFile.println(passwordPermutation);
                    //System.out.println(stringBuild);
                }

            }

            // reset counters for next loop
            numberCount = 0;
            letterCount = 0;
            symbolCount = 0;


            // increment number indefinitely
            counter++;
        }
        outputFile.close();
        System.out.println("Passwords Generated...");

        return null;
    }

    ////////////////////////////
    ////// READ DICTIONARY /////
    ////////////////////////////

    /**
     * Given a source file path, read all data (separated by newLines) and place
     * each data entry into an array of Strings
     * @param filePath the path of the file to open and read
     * @return an array of strings, containing the data we've read.
     */
    public String[] readDictionary(String filePath)
    {

        // Setup values needed for file reading
        String[] dictValues = new String[500];
        File file = new File(filePath);
        BufferedReader fileReader = null;
        int i = 0;
        int arrayLength = 0;

        // Try to read data
        try
        {
            fileReader = new BufferedReader(new FileReader(file));
            String nextEntry = null;

            while ((nextEntry = fileReader.readLine()) != null) {


                if (nextEntry.length() <= 5)
                {
                    dictValues[i] = nextEntry.toLowerCase();
                    i++;
                    arrayLength++;
                }
            }
            // Catch exceptions
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                // Try to close the file, if we're at end
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        // Copy values into final array of correct size
        String[] finalValues = new String[arrayLength]; // Create an array of the correct final size
        finalValues = java.util.Arrays.copyOf(dictValues,arrayLength);


        // Print each item and insert into the DLB Trie
        for (int j=0; j<arrayLength; j++) {
            // System.out.println(finalValues[j]);
            this.insertPassword(finalValues[j].toLowerCase());
        }
        this.setDictArray(finalValues);

        return finalValues;

    }

    ////////////////////////
    //// PERMUTATIONS //////
    ////////////////////////
    /**
     * Create permutations of all items in the dictionary.txt file, using these substitutions:
     *   - 7 => t
     *   - 4 => a
     *   - 0 =  o
     *   - 3 => e
     *   - 1 => i
     *   - $ => $
     * Then, write all to text file.
     * @param myDictionary An array of all dictionary words, obtained from readDictionary()
     */
    void dictionaryPermutations(String[] myDictionary)
    {
        // output array
        String[] outputPerms;

        // open outputfile
        PrintWriter outputFile = null;
        try
        {
            outputFile = new PrintWriter("my_dictionary.txt");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        // possible variables
        // named djanky because.. well, this is djanky.
        char[] djanky = {'\0','\0'};
        char v[] = djanky;
        char w[] = djanky;
        char x[] = djanky;
        char y[] = djanky;
        char z[] = djanky;

        char[][] substitutions = {
                {'i','1'},{'l','1'},{'a','4'},
                {'t','7'},{'e','3'},{'s','$'},{'o','0'}
        };  // Literal array with all substitutions

        // index through dict array
        for (int word=0; word<dictArray.length; word++)
        {

            // check all words
            // transform to char array so we can index into it
            char[] wordChars = dictArray[word].toCharArray();


            // check all characters
            for (int character = 0; character < wordChars.length; character++) {

                // check all substitutions
                for (int sub=0; sub<substitutions.length; sub++)
                {
                    // assign a substitutions array if valid
                    if (wordChars[character] == substitutions[sub][0])
                    {
                        //System.out.println("wordChars[k]=" + wordChars[character]);
                        //System.out.println("substitutions[k][0]=" + substitutions[sub][0]);
                        //System.out.println("FOUND A MATCH!");
                        if (character == 0) v = new char[]{substitutions[sub][0], substitutions[sub][1]};
                        if (character == 1) w = new char[]{substitutions[sub][0], substitutions[sub][1]};
                        if (character == 2) x = new char[]{substitutions[sub][0], substitutions[sub][1]};
                        if (character == 3) y = new char[]{substitutions[sub][0], substitutions[sub][1]};
                        if (character == 4) z = new char[]{substitutions[sub][0], substitutions[sub][1]};
                    }
                }

                // else we just use the letter itself as a literal array
                // with the same value for both positions
                if (v == djanky && dictArray[word].length() >= 1 && character == 0) {
                    v = new char[]{wordChars[character], wordChars[character]};
                }
                if (w == djanky && dictArray[word].length() >= 2 && character == 1) {
                    w = new char[]{wordChars[character], wordChars[character]};
                }
                if (x == djanky && dictArray[word].length() >= 3 && character == 2) {
                    x = new char[]{wordChars[character], wordChars[character]};
                }
                if (y == djanky && dictArray[word].length() >= 4 && character == 3) {
                    y = new char[]{wordChars[character], wordChars[character]};
                }
                if (z == djanky && dictArray[word].length() >= 5 && character == 4) {
                    z = new char[]{wordChars[character], wordChars[character]};
                }

            }

            // Create literal and use natural # system to get all permutations
            int counter = 0;
            int a = 0;
            int b = 0;
            int c = 0;
            int d = 0;
            int e = 0;
            while (true) {
                // increment counter
                e = counter;

                // Perform arithmetic operations
                // 0th position
                if (e == 2) {
                    // carry
                    d++;
                    // cascade zeros
                    e = 0;
                    counter = 0;
                }

                // 1st position
                if (d == 2) {
                    // carry
                    c++;
                    // cascade zeros
                    d = 0;
                    e = 0;
                }

                // 2nd position
                if (c == 2) {
                    // carry
                    b++;
                    // cascade zeros
                    c = 0;
                    d = 0;
                    e = 0;
                }

                // 3rd position
                if (b == 2) {
                    // carry
                    a++;
                    // cascade zeros
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                }

                // 4th position
                if (a == 2) {
                    // carry
                    a++;

                    // cascade zeros
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                }

                ////// break when we get overflow ///////
                if (a > 1) break;
                ////////////// end //////////////////////


                char[] outputPermutation = {v[a], w[b], x[c], y[d], z[e]};


                String printString = new String(outputPermutation);
                outputFile.println(printString);
                // System.out.println(printString);

                counter++;

            }
            // reset char permutation array
            v = djanky;
            w = djanky;
            x = djanky;
            y = djanky;
            z = djanky;
        }
        outputFile.close();
    }


    /////////////////////
    ///// SEARCHING /////
    /////////////////////
    /**
     * Find the prefix of a string within an initialized try
     * @param searchString String whose prefix we will search for within the trie
     * @return 0 if prefix is NOT found in trie
     *         1 if prefix is found, but word NOT valid
     *         2 if word is found, but prefix NOT valid
     *         3 if word is found AND it is a valid prefix
     */
    public int hasPrefix(String searchString)
    {
        // Init return code
        int returnCode = 0;
        boolean stringFound = true;

        // Only search if string is < 5 chars
        if (searchString.length() > 5) {
            stringFound = false;
        }

        // Store the end character
        char endChar = searchString.charAt(searchString.length() - 1);
        // Store the current character
        char currentChar = searchString.charAt(0);
        // Get a reference to the current node we're looking at
        DLBNode currentNode;


        // And we're off -- start iterating through
        this.currentNode = null; //
        // Make sure trie exists
        if (root.getChild() == null) {
            stringFound = false;
        }

        // If we find the string up until current char, keep iterating
        if(stringFound) {
            this.currentNode = root.getChild();
            // Check this level
            while (this.currentNode.next != null) {
                // at each node, check for a match, and break out of while loop if found; otherwise keep on iterating
                if (this.currentNode.data == currentChar) break;
                this.currentNode = this.currentNode.next;
            }

            // Confirm we have correct node, and if not, return false
            if((this.currentNode.next == null) && (this.currentNode.data != currentChar)) stringFound = false;
        }

        // Kloodgy, but it works....
        // Next, keep looking for subsequent characters in the prefix
        if (stringFound) {
            // Increment character we're on
            for(int characterPlace=1; characterPlace<searchString.length(); characterPlace++) {
                currentChar = searchString.charAt(characterPlace);
                // If current node is correct, but no child -> false
                if (this.currentNode.getChild() == null && stringFound) {
                    stringFound = false;

                // Otherwise, ensure we have the right node, and break if we do.
                } else if (stringFound) {
                    this.currentNode = this.currentNode.getChild();
                    while (this.currentNode.next != null) {
                        if (this.currentNode.data == currentChar) break;
                        // Else
                        this.currentNode = this.currentNode.next;
                    } //end-while
                    // Confirm we've found the right node
                    if (this.currentNode.next == null && this.currentNode.data != currentChar) stringFound = false;
                } // end inner:  if and else-if
            } // end-for
        } // end outer-if

        // If word is valid, and has a child -> it is a prefix also: Return 3
        if (    endChar == this.currentNode.getData() &&
                this.currentNode.isFlag() &&
                this.currentNode.getChild() != null) {
            returnCode = 3;
        }
        // If prefix found, but word not valid -> Return 1
        else if (endChar == this.currentNode.getData() &&
                !this.currentNode.isFlag() &&
                 this.currentNode.getChild() != null) {
            returnCode = 1;
        }
        // If word is valid, but it isn't a prefix -> Return 2
        else if(endChar == this.currentNode.getData() &&
                this.currentNode.isFlag() &&
                this.currentNode.getChild() == null) {
            returnCode = 2;
        }
        // If prefix not found at all -> Return 0
        else {
            returnCode = 0;
            return returnCode;
        }

        // Finally, return our value
        return returnCode;
    }

    /**
     * Find the longest prefix given a password as input
     * REQUIRES:: an initialized try with valid passwords in it, for us to search
     * @param password a password to search for the longest prefix of
     * @return A string, containing the longest prefix we could find
     */
    public String getLongestPrefix(String password)
    {
        // Use a StringBuilder to more easily create our prefix string as we search at each level
        StringBuilder prefixString = new StringBuilder();
        // Start by getting our first character
        char currentCharacter = password.charAt(0);


        // Get one of our returnValues from searchPrefix, so we know what we're working with
        int returnCode;
        returnCode = hasPrefix("" + currentCharacter);

        // If return code is 1 or 3, we have a valid prefix. So add it to our prefixString
        if(returnCode == 1 || returnCode == 3) prefixString.append(currentCharacter);
        // Otherwise, we don't have a valid prefix at all. So just return "".
        else return prefixString.toString();

        // Continue adding prefixes and searching
        for(int characterPosition = 1; characterPosition < password.length(); characterPosition++) {
            currentCharacter=password.charAt(characterPosition);
            prefixString.append(currentCharacter);
            returnCode= hasPrefix(prefixString.toString());
            // until we reach a prefix which is NOT valid return Code == (1 or 3)
            if(returnCode != 1 && returnCode != 3) break;
        }

        // Once we reach an invalid prefix within the loop above, delete last char and return the final string
        if(returnCode != 1 && returnCode != 3) {
            prefixString.deleteCharAt(prefixString.length() - 1);
            return prefixString.toString();
        }
        return prefixString.toString();
    }


    /**
     * Checks if a given password matches anything in our dictionary
     * @param password password string to search
     * @return true if a match is found, false if not
     */
    public boolean isDictionaryMatch(String password)
    {
        boolean matchFound = false;
        password = mapSubstitutionsToPlainText(password);

        // Search dictionary word by word, looking for a match against whatever string we pass in.
        for(int word = 0;word < dictArray.length; word++) {
            if(password.length() > dictArray[word].length()) {
                matchFound = password.contains(dictArray[word]); // calling contains might be slow...

                /////// if MATCH at any point, RETURN TRUE ////////////
                if(matchFound)return true; // end-if
                ///////////////////return//////////////////////////////

            } // end-outer if
        } // end-for

        // If we've made it this far, there weren't ANY matches -> RETURN FALSE
        return false;
    }

    /**
     * Given a password permutation created by bruteforce(), map all possible substitutions
     * back to letters. We'll use the output of this to feed our "isDictionaryMatch() function",
     * so we can check for matches more easily
     * @param passwordPermutation A password permutation (5 char string) to map back to pain text
     * @return Same password permutation, but with substitutions made
     */
    public String mapSubstitutionsToPlainText(String passwordPermutation)
    {
        // Make our password a character array,
        // and initialize our output string (passwordPermutation) to ""
        // We'll build it letter by letter
        char[] passwordAsCharacterList = passwordPermutation.toCharArray();
        passwordPermutation = "";

        for(int currentElement = 0; currentElement < passwordAsCharacterList.length; currentElement++) {
            if(passwordAsCharacterList[currentElement] == '7') passwordAsCharacterList[currentElement] = 't';
            else if(passwordAsCharacterList[currentElement] == '3') passwordAsCharacterList[currentElement] = 'e';
            else if(passwordAsCharacterList[currentElement] == '4') passwordAsCharacterList[currentElement] = 'a';
            else if(passwordAsCharacterList[currentElement] == '1') passwordAsCharacterList[currentElement] = 'i';
            else if(passwordAsCharacterList[currentElement] == '$') passwordAsCharacterList[currentElement] = 's';
            else if(passwordAsCharacterList[currentElement] == '0') passwordAsCharacterList[currentElement] = 'o';

            // Build up our mapped permutation by one more item on each iteration
            passwordPermutation += passwordAsCharacterList[currentElement];
        }

        // Finally, return what we've built
        return passwordPermutation;
    }



    ///////////////////////////
    ///// UTILITY METHODS /////
    ///////////////////////////

    /**
     * Insert a given word into an initialized DLB Trie
     * @param word the word to insert into our try. Must be a string.
     */
    public void insertPassword(String word) {
        // For each word, start at the head of the DLB
        currentNode = getRoot();

        // Check for each char, because each char is a new level on the tree
        for (int i=0; i<word.length(); i++)
        {
            // set myChar to be the current character at the String index we're working on
            char myChar = word.charAt(i);
            // Make a new new with the current character
            // DLBNode newNode = new DLBNode(myChar);

            // if the current node is null, we initialize the next level by creating
            // a new child node, which is also a linked list
            if (currentNode.getChild() == null) {
                currentNode.setChild(new DLBNode(myChar));
                currentNode.getChild().setParent(currentNode);  // set current node to be parent
                currentNode = currentNode.getChild();
                // else there MUST be an already established list at this level
                // and either our character is in this list, or it is not
                // so, we need to search for it at the current level, and create it,
                // if it doesn't exist yet
            } else {
                // Move down one level, because know child list is not null
                currentNode.getChild().setParent(currentNode);  // set current node to be parent
                currentNode = currentNode.getChild();
                // move laterally along the list at this level,
                // until we find a match for our data, OR we reach a null

                // if our first child node matches the char, we're done
                if (currentNode.getData() != myChar) {

                    // else we find node that does equal our data at this level
                    // or we find null
                    // OLD while ( (currentNode.getNext().getData() != myChar))
                    {
                        boolean valueNotPlaced = true;
                        while (currentNode.getNext() != null && valueNotPlaced) {

                            // make current node next as long as we can
                            currentNode = currentNode.getNext();

                            if (currentNode.getData() == myChar)
                            {
                                // we're done
                                valueNotPlaced = false;
                            } // else, go back to the while loop and keep iterating

                        }  // END WHILE
                        // if we make it this far, then we've reached a NULL
                        // SO, we want to make current.next a new node with our input value
                        // THEN, iterate to that node

                        /// Not needed?
                        if (currentNode.getData() != myChar) {
                            currentNode.setNext(new DLBNode(myChar));
                            currentNode.getNext().setPrev(currentNode);  // set current node to be prev
                            currentNode = currentNode.getNext();
                        } // END IF

                    } // END IF

                } // END ELSE

            } // END FOR

            // At end of for loop, set boolean flag to TRUE, because we've completed a full word
            currentNode.setFlag(true);
        }
    }
    /**
     * Search for a given string in an initialized DLB Trie
     * @param lostString string to search for
     * @return true if the string is found; false if not.
     */
    public boolean hasPassword(String lostString) {

        currentNode = null;
        // String searchPath = "";
        char searchChar = '\0';
        boolean found = false;

        // As long as rootnode is initialized get a reference to it
        if (getRoot() != null) {
            currentNode = getRoot();
        } else {
            return false;
        }

        // If we have an initialized trie, BEGIN SEARCH
        for (int i=0; i<lostString.length(); i++) {

            // make searchChar the current character we're looking at
            searchChar = lostString.charAt(i);

            // Move down one level, and check if that level is initialized
            if (currentNode == null) {
                // if not initialized at this level at all, search is null
                return false;
            } else {
                // Move laterally along the current level until a match is found
                // or null is reached
                if (currentNode.getChild() != null) {
                    currentNode = currentNode.getChild();
                } else {
                    return false;
                }

                boolean notNull = true;
                while (notNull && currentNode.getData() != searchChar) {
                    // Make current node the next on at current level
                    currentNode = currentNode.getNext();

                    // Check if current node is null, and if yes, break loop
                    if (currentNode == null) {
                        notNull = false;
                    }  // end-if
                } // end-while
            } // end-else
        } // end-for

        // Once we get this far, we need to see if the flag is true or false
        // because we're at the end of the word
        if (currentNode == null) {
            return false;
        } else {
            found = currentNode.isFlag();
        }

        return found;
    }

    /**
     * Helper method - remove last item in a string. Used mostly in findColletion(),
     * as we search through the Trie, building and de-accumulating strings
     * @param givenString String we want to remove last element from
     * @return The same string, with only the last element removed
     */
    public String deAccumulate(String givenString)
    {
        char[] stringAsCharArray = givenString.toCharArray();
        givenString = "";
        for(int i = 0; i < stringAsCharArray.length-1 ;i++) givenString += stringAsCharArray[i];
        return givenString;
    }

    /**
     * Delete a given password from the Trie.
     * @param password a password to search for and delete from our Tri.
     */
    public boolean deletePassword(String password) {
        // code goes here
        char lastChar = password.charAt(password.length()-1);
        currentNode = null;
        boolean success = false;
        boolean deleteFinished = false;

        // The string exists, attempt to delete it
        if (hasPassword(password)) {
            // find the "end of the string" character

            // use search, and get a reference to EOS character

            // String searchPath = "";
            char searchChar = '\0';
            boolean found = false;

            // As long as rootnode is initialized get a reference to it
            if (getRoot() != null) {
                currentNode = getRoot();
            } else {
                return false;
            }

            // If we have an initialized trie, BEGIN SEARCH
            for (int i=0; i<password.length(); i++) {

                // make searchChar the current character we're looking at
                searchChar = password.charAt(i);

                // Move down one level, and check if that level is initialized
                if (currentNode == null) {
                    // if not initialized at this level at all, search is null
                    return false;
                } else {
                    // Move laterally along the current level until a match isfound
                    // or null is reached
                    currentNode = currentNode.getChild();
                    boolean notNull = true;
                    while (notNull && currentNode.getData() != searchChar) {
                        // Make current node the next on at current level
                        currentNode = currentNode.getNext();

                        // Check if current node is null, and if yes, break loop
                        if (currentNode == null) {
                            notNull = false;
                        }  // end-if
                    } // end-while
                } // end-else
            } // end-for

            // Once we get this far, we need to see if the flag is true or false
            // because we're at the end of the word
            if (currentNode == null) {
                return false;
            } else {
                found = currentNode.isFlag();
            }

            // Coming out of this loop, we have a reference to the end of string node, in "currentNode"
            // System.out.println("CONFIRM WE HAVE A REFERENCE TO LAST NODE: " + currentNode.getData());
            // then backtrack, removing nodes until a node with a sibling is found

            // CASE 1:  No Children, No Siblings
            if (currentNode.getChild() == null && currentNode.getNext() == null)
            {
                // If current node has no siblings, delete it

                // We have no children and no siblings, but a valid word, so delete it
                if (currentNode.isFlag()) {
                    currentNode.setFlag(false);
                    // System.out.println("CASE 1 DELETE");
                    success = true;
                }


                // Now, delete all unused nodes until we get to one with a sibling, or which is being used
                while (currentNode != null && !currentNode.isFlag() && currentNode.getNext() == null && !deleteFinished ) {
                    // System.out.println("CASE 1 DELETE");
                    // System.out.println("DATA VALUE FOR CURRENT NODE: " + currentNode.getData() +" "+ currentNode.getParent());
                    if (currentNode.getParent() == null) { // if no parent
                        // then we're in the middle of a sibling list, and the node we're at is no longer used
                        DLBNode oldLink = currentNode.getPrev();
                        DLBNode newLink = currentNode.getNext();
                        oldLink.setNext(newLink);
                        if (newLink != null) { // If next is NOT null
                            newLink.setPrev(oldLink);
                        }
                        currentNode.setNext(null);
                        currentNode.setPrev(null);
                        deleteFinished = true;
                    } else { // if it does have a parent
                        currentNode = currentNode.getParent();   // Move up onelevel

                        //System.out.println("DATA VALUE FOR CURRENT NODE: " + currentNode.getData());
                        currentNode.getChild().setParent(null);  // Set "parent" of the node one level down to null
                        currentNode.setChild(null);  // Set "child" reference to node one level down to null, removing it
                    }
                    currentNode = currentNode.getParent();
                }


            }

            // CASE 2:  No Children, Has Siblings
            if (currentNode != null && currentNode.getChild() == null && currentNode.getNext() != null) {

                if (currentNode.getParent() != null) {
                    currentNode = currentNode.getParent();
                    DLBNode newChild = currentNode.getChild().getNext();
                    newChild.setParent(currentNode);
                    currentNode.getChild().setParent(null);
                    currentNode.setChild(newChild);
                    currentNode.getChild().setPrev(null);
                    // System.out.println("CASE 2 DELETE");
                    success = true;
                }
                else if  (currentNode.getPrev() != null)
                {
                    // if no parent, but it has a previous
                    // then link PREVIOUS and NEXT NODE
                    // Make currentNode's references ull
                    DLBNode prevNode = currentNode.getPrev();
                    DLBNode nextNode = currentNode.getNext();
                    prevNode.setNext(nextNode);
                    nextNode.setPrev(prevNode);
                    currentNode.setNext(null);
                    currentNode.setPrev(null);

                    success = true;
                }
                else
                {
                    // System.out.println("CASE 2 Delete with NO previous and No parent!");
                    success = false;
                }

            }

            // CASE 3:  Has Children, No Siblings
            if (currentNode != null && currentNode.getChild() != null && currentNode.getNext() == null)
            {
                currentNode.setFlag(false);
                // System.out.println("CASE 3 DELETE");
                success = true;
            }

            // CASE 4: Has Children, Has Siblings
            if (currentNode != null && currentNode.getChild() != null && currentNode.getNext() != null && !deleteFinished)
            {
                currentNode.setFlag(false);
                // System.out.println("CASE 4 DELETE");
                success = true;
            }
            //      in this case, the node is still removed, but the deletion is done
            //      determining if the node has a sibling is not always trivial,
            //      nor is keeping track of the pointers

            //success = true;
        }
        // else we cant delete anything
        return success;
    }


    /**
     * Public access method - allows user to easily get a collection of valid passwords, when given an invalid
     * Requires an initialized DLB Trie, filled with valid passwords, to search within
     * @param passwordGiven User supplied password, which is invalid.
     * @return A collection of valid passwords.
     */
    public String[] returnCollection(String passwordGiven)
    {
        String[] validPasswords = new String[10];
        String[] myCollection = findCollection(passwordGiven, validPasswords, root, 0, false);
        return myCollection;
    }

    /**
     * The method where the magic happens. Finds a collection of valid passwords, when given an invalid one.
     * This method is recursive, so we have a few variables that are passed back into itself, so we can
     * keep track of what's going on.
     * @param passwordGiven The initial password user gave us, or password found in last iteration
     * @param validPasswords The list of valid passwords built so far
     * @param currentNode The current node we're on
     * @param currentLevel The current tree-(or trie)-level we're on
     * @param prefixFound Have we reached the prefix we were initially given?
     * @return A collection of 10 valid passwords, an array of Strings (String[])
     */
    private String[] findCollection(String passwordGiven, String[] validPasswords, DLBNode currentNode,
                                    int currentLevel, boolean prefixFound)
    {
        // If rootNode, special case
        if(currentNode == root) {
            if(passwordGiven.equals("")) prefixFound = true;
            validPasswords = findCollection(passwordGiven, validPasswords,
                    currentNode.getChild(), currentLevel, prefixFound);
        }
        // Else we keep looking for passwords
        else {
            if (prefixFound) {
                passwordGiven += currentNode.data;
                // Check how many items are in our collection, find next open spot, if it exists
                if (passwordGiven.length() == 5) {
                    int collectionItem = 0;
                    while(validPasswords[collectionItem] != null) {
                        collectionItem++;
                        if(collectionItem == 9) break;
                    } //end-while

                    boolean addedAPassword = true;

                    // Make sure we're not adding duplicates
                    for (int elemChecked=0; elemChecked<collectionItem; elemChecked++) {
                        if(validPasswords[elemChecked].equals(passwordGiven))addedAPassword = false;
                    } // end-for

                    // If currentNode is NOT a valid password, don't add it.
                    if(!currentNode.isFlag()) addedAPassword = false;

                    // If we've already addy a password,
                    if(addedAPassword) validPasswords[collectionItem] = passwordGiven;

                    // If collection is full, return it.
                    if(collectionItem == 9) return validPasswords;
                } // end-middle-if
            } // end-outer-if

            String tempString = "";
            // If root is null, return null
            if(root == null) return null;

            if(currentLevel == passwordGiven.length()) prefixFound = true;

            // If our original prefix was NOT reached yet
            if(!prefixFound) {
                // If we have a next node to move to
                if(currentNode.next != null) {
                    while(currentNode.getData() != passwordGiven.charAt(currentLevel)) {
                        // If we don't have a next, return
                        if(currentNode.getNext() == null) return validPasswords; // end if -- (4)

                        // if we do have a next, move to it, for as long as we can.
                        currentNode = currentNode.getNext();
                    } // end-while

                    // If we can move down a level, do it.
                    if(currentNode.getChild() != null) {
                        currentLevel++;
                        validPasswords = findCollection(passwordGiven, validPasswords,
                                                        currentNode.getChild(), currentLevel, prefixFound);
                        currentLevel--;
                    } // end-if -- (3)
                } // end-if -- (2)
            } //end-if -- (1)

            // For as long as we can move down a level on current path, do it.
            if(currentNode.getChild() != null && currentLevel != 5) {
                currentLevel++;
                validPasswords = findCollection(passwordGiven, validPasswords,
                                                currentNode.getChild(), currentLevel, prefixFound);
                currentLevel--;
                deAccumulate(passwordGiven);
            } // end-if

            if(currentNode.getNext() != null) {
                currentLevel++;
                validPasswords = findCollection(passwordGiven, validPasswords,
                        currentNode.getNext(), currentLevel, prefixFound);
                deAccumulate(passwordGiven);
            } // end-if
        } //end-else

        int collectionItemLocation = 0;
        if(currentNode == root) {
            while(validPasswords[collectionItemLocation] != null) {
                collectionItemLocation++;
                if(collectionItemLocation == 9) break; // end-if
            } // end-while
            if(collectionItemLocation < 9) validPasswords = findCollection(deAccumulate(passwordGiven),
                                                                           validPasswords, this.getRoot(), 0, false);
        } // end-if

        // Return whichever passwords we've found so far....
        return validPasswords;
    }


    ////////////////////////////////
    ///// GETTERS AND SETTERS //////
    ////////////////////////////////

    public DLBNode getRoot() {
        return root;
    }

    public String[] getDictArray() {
        return dictArray;
    }

    public void setDictArray(String[] dictArray) {
        this.dictArray = dictArray;
    }

}
