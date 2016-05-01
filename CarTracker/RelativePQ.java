import java.util.Arrays;

/**
 * Two IndexMinPQ's connected into a single data structure backed by an array which
 * lets us index into and understand what's going on in each
 *
 * Methodology:  Generally --> get a reference to an existing object using retrieve() with its
 *             VIN, then use other methods to add, update, etc.
 *
 * This PQ Structure is tailored for our problem specifically, but it could be generalized
 *             by making the string index by int, instead of STRING.
 *             Chose string so that we could index by VIN.
 *
 * Could Use a hash table, symbol table to get fast index for removes... but then need to update
 * the whole thing every time... not worth it. Better to take the hit on update, expected it
 * will be used less frequently than the adds.
 */
public class RelativePQ   {
    int maxVals = 100;
    // Create an index PQ for Price
    IndexMinPQ priceQueue;
    // Create an index PQ for Mileage
    IndexMinPQ mileageQueue;
    // Collection to index into both PQ's efficiently
    String[] keyIndex;    // index by VIN
    Car[] priceValues;    // Values to the price heap
    int[] priceHeap;      // Keys of the price heap
    Car[] mileageValues;  // Values to the mileage heap
    int[] mileageHeap;    // Keys of the mileage heap
    int count;            // number of values we have, total

    ////////////////////
    /// CONSTRUCTORS ///
    ////////////////////

    /**
     * Default maximum values=100, but this can be changed
     */
    @SuppressWarnings({"unchecked"})
    RelativePQ() {
        priceQueue = new IndexMinPQ(maxVals);
        mileageQueue = new IndexMinPQ(maxVals);
        keyIndex = new String[maxVals];
        priceHeap = new int[maxVals];
        mileageHeap = new int[maxVals];
        count = 0;
    }

    /**
     * Constructor for when the user wants to define his own max
     * @param userDefinedMax
     */
    RelativePQ(int userDefinedMax) {
        if (userDefinedMax < 0 || userDefinedMax > 999999999) {
            System.out.println("That max is invalid (too large or too small)");
            System.out.println("Relative PQ initialized with maxValue=100");
            userDefinedMax = 100;
        }

        maxVals = userDefinedMax;
        priceQueue = new IndexMinPQ(userDefinedMax);
        mileageQueue = new IndexMinPQ(userDefinedMax);
        keyIndex = new String[userDefinedMax];
        priceHeap = new int[userDefinedMax];
        mileageHeap = new int[userDefinedMax];
        count = 0;
    }



    //////////////////
    /// PQ METHODS ///
    //////////////////
    /**
     * Return true if the vin we passed in is already used
     * O(n), but necessary to ensure that everything doesn't break down
     * May be possible to make this faster by hashing... but not sure if it's worth it
     * @param checkVin Vin to check
     * @return true if the VIN is already used; false if it's free.
     */
    public boolean containsVin(String checkVin) {
        boolean vinUsed = false;

        // first, simply check if our queues are empty.
        if (keyIndex[0] == null) return false;

        // if they aren't empty, and we get this far, check all the values
        for (int i=0; i<count; i++ ) {
            if (keyIndex[i].equalsIgnoreCase(checkVin)) vinUsed = true;
        }
        return vinUsed;
    }

    public Car getLowestPrice() {
        if (count == 0) {
            System.out.println("No cars in lowest price PQ. A null value has been returned.");
            return null;
        }
        return priceQueue.minKey();
    }

    public Car getLowestMileage() {
        if (count == 0) {
            System.out.println("No cars in lowest mileage PQ. A null value has been returned.");
            return null;
        }
        return mileageQueue.minKey();
    }

    /**
     * Strategy:  Use the iterator method in Each PQ to build an array of values ONLY that
     * match the make and model we are looking for.
     *            Then, sort on that value.
     *            Then, return min of the sorted array.
     *
     *            ^^ This way is less maybe, but maybe not that much less... and too complicated.
     *
     *    OR:     Is it faster to just make a copy of the PQ, and then remove from that copy
     *    until we have a value that matches our make and model?
     *    Think this is better for us.  <<<<
     * @param make
     * @param model
     * @return
     */
    public Car getLowestPriceByMakeAndModel(String make, String model) {
        // Make a copy of our PQ so we can get values faster
        IndexMinPQ priceQueueCopy = new IndexMinPQ(maxVals);
        Car[] priceValuesCopy = Arrays.copyOf(priceValues, maxVals);  // Copy priceValuesArray
        int[] priceHeapCopy = Arrays.copyOf(priceHeap, maxVals); // Copy values of priceheap]
        int[] qpCopy = new int[maxVals];
        if (maxVals < 0) throw new IllegalArgumentException();
        priceQueueCopy.setMaxN(maxVals);
        for (int i = 0; i <= maxVals-1; i++)
            qpCopy[i] = -1;
        priceQueueCopy.setCars(priceValuesCopy);
        priceQueueCopy.setPq(priceHeapCopy);
        priceQueueCopy.setQp(qpCopy);
        priceQueueCopy.setMaxN(maxVals);
        priceQueueCopy.setN(count);
        int copyCount = count;

        // first, make sure we have values to check
        if (copyCount == 0) return null;

        // Check minKey
        Car minKey;
        do {
            minKey = priceQueueCopy.minKey();
            // If the make and model match our Min Key, return it!
            if (  minKey.getMake().equals(make)  &&
                    minKey.getModel().equals(model)   ) {
                return minKey;
            }
            // Else, delete it from our copy, and keep going
            else {
                priceQueueCopy.delMin();
                copyCount--;
            }
        } while (copyCount > 0 );


        // If we get this far, no make and model match. Return null.
        System.out.println("No make and model match that criteria.");
        return null;

    }

    /**
     * Strategy:  Use the iterator method in Each PQ to build an array of values ONLY that
     * match the make and model we are looking for.
     *            Then, sort on that value.
     *            Then, return min of the sorted array.
     *
     *    OR:     Is it faster to just make a copy of the PQ, and then remove from that copy
     *    until we have a value that matches our make and model?
     *    Think this is better for us.   <<<<
     * @param make
     * @param model
     * @return
     */
    public Car getLowestMileageByMakeAndModel(String make, String model) {
        // Make a copy of our PQ so we can get values faster
        IndexMinPQ mileageQueueCopy = new IndexMinPQ(maxVals);
        Car[] mileageValuesCopy = Arrays.copyOf(mileageValues, maxVals);  // Copy mileageValuesArray
        int[] mileageHeapCopy = Arrays.copyOf(mileageHeap, maxVals); // Copy values of milageeHeap
        int[] qpCopy = new int[maxVals];
        if (maxVals < 0) throw new IllegalArgumentException();
        mileageQueueCopy.setMaxN(maxVals);
        for (int i = 0; i <= maxVals-1; i++)
            qpCopy[i] = -1;
        mileageQueueCopy.setCars(mileageValuesCopy);
        mileageQueueCopy.setPq(mileageHeapCopy);
        mileageQueueCopy.setQp(qpCopy);
        mileageQueueCopy.setMaxN(maxVals);
        mileageQueueCopy.setN(count);
        int copyCount = count;

        // first, make sure we have values to check
        if (copyCount == 0) return null;

        // Check minKey
        Car minKey;
        do {
            minKey = mileageQueueCopy.minKey();
            // If the make and model match our Min Key, return it!
            if (  minKey.getMake().equals(make)  &&
                    minKey.getModel().equals(model)   ) {
                return minKey;
            }
            // Else, delete it from our copy, and keep going
            else {
                mileageQueueCopy.delMin();
                copyCount--;
            }
        } while (copyCount > 0 );


        // If we get this far, no make and model match. Return null.
        System.out.println("No make and model match that criteria.");
        return null;

    }


    ///////////////////////
    /// UTILITY METHODS ///
    ///////////////////////


    /**
     * Add a fully formed key to both Queues, keep its index in the array
     * @param newValue
     */
    public void add(Car newValue) {
        // before add, make a copy, and set compare on price to false, then put in the mileagePQ

        if (newValue.getVin() == null) {
            System.out.println("Value passed in was NULL. Please check if VIN was valid.");
            return;
        }

        // Add priceCar
        Car priceCar = new Car();
        priceCar.setVin(newValue.getVin());
        priceCar.setColor(newValue.getColor());
        priceCar.setMileage(newValue.getMileage());
        priceCar.setPrice(newValue.getPrice());
        priceCar.setMake(newValue.getMake());
        priceCar.setModel(newValue.getModel());
        priceCar.compareOnMiles = false;
        priceCar.compareOnPrice = true;
        priceQueue.insert(count, priceCar);

        // Add mileageCar
        Car mileAgeCar = new Car();
        mileAgeCar.setVin(newValue.getVin());
        mileAgeCar.setColor(newValue.getColor());
        mileAgeCar.setMileage(newValue.getMileage());
        mileAgeCar.setPrice(newValue.getPrice());
        mileAgeCar.setMake(newValue.getMake());
        mileAgeCar.setModel(newValue.getModel());
        mileAgeCar.compareOnPrice = false;
        mileAgeCar.compareOnMiles = true;
        mileageQueue.insert(count, mileAgeCar);

        // Add to our own indirection array
        keyIndex[count] = newValue.getVin();

        // Get indices of items in both Queues
        // replicate Price PQueue Data
        priceHeap = priceQueue.getPq();
        priceValues = priceQueue.getCars();

        // replicate Mileage PQueue Data
        mileageHeap = mileageQueue.getPq();
        mileageValues = mileageQueue.getCars();

        // increment count
        count++;
    }


    /**
     * Update the value in both PQ's and the Index array, for a given key, must be fully formed
     * Decided to do these separate, because each list needs its own Swim methods, and to
     * re-order itself separately
     * @param updatedValue
     * @return
     */
    public void update(Car updatedValue, boolean updatePrice, boolean updateMileage, boolean updateColor) {
        // if the VIN is invalid, don't update at all
        if (!this.containsVin(updatedValue.getVin())) {
            System.out.println("VIN was invalid. Could not update");
            return;
        }

        // First, update the price list.... to do so, iterate and get the one we want.
        // For all the cars in our list,
        int updatedHeapValue = 0; // starts out at 0, in cause it wasn't initialized
        for (int i=0; i<count; i++) {
            Car myCar = priceValues[i];
            // If their VIN's match, via the .equals method
            if (myCar.equals(updatedValue)) {
                // Then update the car that matched
                // it takes on only the RELEVANT attributes of the updated car
                if (updatePrice) myCar.setPrice(updatedValue.getPrice());
                if (updateMileage) myCar.setMileage(updatedValue.getMileage());
                if (updateColor) myCar.setColor(updatedValue.getColor());
            } // end-if
        } // end-for

        // THEN, we need to access the actual value in the heap, so we can operate on it
        int index = 0;
        while (true) {
            // Index into the price, use that to get the Car Object, and check if
            // The VIN's match; if they do:
            if (priceValues[priceHeap[index]].getVin().equals(updatedValue.getVin())) {
                updatedHeapValue = priceHeap[index];
                break;
            } // end-if

            if (index > maxVals) {
                break;
            } // end-if
            index++;
        } // end-while
        // Perform the sink and swim
        priceQueue.sink(index);
        priceQueue.swim(index);

        // Then, update the mileage list
        updatedHeapValue = 0; // starts out at 1, in cause it wasn't initialized
        for (int i=0; i<count; i++) {
            Car myCar = mileageValues[i];
            // If their VIN's match, via the .equals method
            if (myCar.equals(updatedValue)) {
                // Then update the car that matched
                // it takes on only the RELEVANT attributes of the updated car
                if (updatePrice) myCar.setPrice(updatedValue.getPrice());
                if (updateMileage) myCar.setMileage(updatedValue.getMileage());
                if (updateColor) myCar.setColor(updatedValue.getColor());
                // THEN, we need to access the actual value in the heap, so we can operate on it
            } // end-if
        } // end-for
        index = 0;

        while (true) {
            // Index into the price, use that to get the Car Object, and check if
            // The VIN's match; if they do:
            if (mileageValues[mileageHeap[index]].getVin().equals(updatedValue.getVin())) {
                updatedHeapValue =  mileageHeap[index];
                break;
            } // end-if

            if (index > maxVals) {
                break;
            } // end-if
            index++;
        } // end-while
        // Perform the sink and swim
        mileageQueue.sink(index);
        mileageQueue.swim(index);

    }

    /**
     * Remove a Key from the data structure, given its vin
     * @param vin
     * @return
     */
    public boolean remove(String vin) {
        // To remove.. set the value being considered to zero. Re-heap. And then delete min =)
        // Need to do it to both structures though

        // First, if the VIN is invalid, don't remove at all
        if (!this.containsVin(vin)) {
            System.out.println("VIN was invalid. Could not update");
            return false;
        }

        // We can remove by just re-using the update method. Update price and miles and set them both to zero.
        // Then, when we get back, do a delMinKey
        // All done within the method below
        removeFromPriceAndMileageQueues(vin);


        // With the values now deleted from our structure, we need to do some more accounting.
        // Remove the value from our Key Index Array
        for (int i=0; i<count-1; i++) {
            // If we have a match, and we must if we get this far,
            // Then we need to remove it from the Key Index Array
            if (keyIndex[i].equals(vin)) {
                // We can do this by swapping the locations of foundkey and lastkey,
                // Then, we just make lastkey null
                String swapKey = keyIndex[i];
                String lastKey = keyIndex[count-1];
                keyIndex[i] = lastKey;
                keyIndex[count-1] = swapKey;
                keyIndex[count-1] = null;
            }
        }

        // update our count
        count--;


        return true;
    }


    public void removeFromPriceAndMileageQueues(String removalVin) {
        Car carUpdates = new Car(removalVin, "", "", 0.0, 0.0, "", true, false);
        this.update(carUpdates, true, true, false);
        priceQueue.delMin();
        mileageQueue.delMin();
    }



    ///////////////////////////
    ///// HELPER FUNCTIONS ////
    ///////////////////////////


}
