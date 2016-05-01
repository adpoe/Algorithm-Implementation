import java.util.Scanner;
/**
 * Created by tony on 3/5/16.
 */
public class CarTracker {

    /* REMEMBER::  Put String[] args back int Main's params before compiling on cmd line*/
    public static void main(String[] args ) {
        RelativePQ carDatabase = new RelativePQ();

        // Startup info
        System.out.println("Welcome! This program will help you select the perfect car," +
                "based on the following attributes:" +
                "\n\tMAKE" +
                "\n\tMODEL" +
                "\n\tMILEAGE" +
                "\n\tCOST" +
                "\n\tCOLOR");
        System.out.println("To begin, start adding cars to the selection database.");

        // create scanner and sentinel value
        boolean quit = false;
        Scanner kb = new Scanner(System.in);

        // Prompt for user to select an option
        while (!quit) {
            System.out.println("-----------------------");
            System.out.println("Please select an action:");
            System.out.println("\t1. ADD a car to the DB");
            System.out.println("\t2. UPDATE an existing car in the DB");
            System.out.println("\t3. REMOVE a car from consideration");
            System.out.println("\t4. RETRIEVE the LOWEST PRICE car from the DB");
            System.out.println("\t5. RETRIEVE the LOWEST MILEAGE car from the DB");
            System.out.println("\t6. RETRIEVE the LOWEST PRICE car given --> MAKE and MODEL");
            System.out.println("\t7. RETRIEVE the LOWEST MILEAGE car given --> MAKE and MODEL");
            System.out.println();
            System.out.println("\t\tor, type 'q' to QUIT at any time");
            System.out.println();
            System.out.print(" > ");

            // get user input for selected action
            String selectedAction = "";
            selectedAction = kb.nextLine();

            // HOW TO HANDLE NEWLINES ON JAVA INPUT, W/ NEXTLINE?

            // If user selects ADD
            if (selectedAction.equals("1")) {
                System.out.print("Input New Car's VIN: ");
                String newVin = null;
                newVin = kb.nextLine();

                System.out.print("Input New Car's Make: ");
                String newMake = null;
                newMake = kb.nextLine();

                System.out.print("Input New Car's Model: ");
                String newModel = null;
                newModel = kb.nextLine();

                System.out.print("Input New Car's Price: ");
                double newPrice = 0.0;
                newPrice = kb.nextDouble();
                kb.nextLine();

                System.out.print("Input New Car's Mileage: ");
                double newMileage = 0.0;
                newMileage = kb.nextDouble();
                kb.nextLine();

                System.out.print("Input New Car's Color: ");
                String newColor = "";
                newColor = kb.nextLine();

                // create a new car object with our params
                Car newCar = new Car(newVin, newMake, newModel, newPrice,
                        newMileage, newColor, true, false); // default is to compare on price
                                                            // but we handle both options
                                                            // in the RelativePQ class

                // add car to our data structure
                if (newCar.getVin() != null
                        && !carDatabase.containsVin(newVin) )  {
                              add(carDatabase, newCar);
                    System.out.println(carDatabase.mileageQueue.minKey().toString());
                    System.out.println(carDatabase.priceQueue.minKey().toString());
                    System.out.println(carDatabase.keyIndex[0]);
                }  else  {
                    System.out.println("Could not add new car. VIN cannot contain an I, O, or Q");
                    System.out.println("Or, VIN ID was already taken.");
                }
            }

            // If user selects UPDATE
            if (selectedAction.equals("2")) {

                // get updates for existing car
                System.out.print("Input VIN of the car to update: ");
                String updatedVin = null;
                updatedVin = kb.nextLine();

                // determine what to update
                System.out.println("What attribute would you like to update?");
                System.out.println("\t1. Price");
                System.out.println("\t2. Mileage");
                System.out.println("\t3. Color");
                int selection = kb.nextInt();
                kb.nextLine();


                if (selection == 1) {
                    // update price
                    Double newPrice = 0.0;
                    System.out.print("Input new price: ");
                    newPrice = kb.nextDouble();
                    kb.nextLine();

                    updatePrice(updatedVin, newPrice, carDatabase);
                } else if (selection == 2) {
                    // update mileage
                    Double newMileage = 0.0;
                    System.out.print("Input new mileage: ");
                    newMileage= kb.nextDouble();
                    kb.nextLine();

                    updateMileage(updatedVin, newMileage, carDatabase);
                } else if (selection == 3) {
                    // update color
                    String newColor = "";
                    System.out.print("Input new color: ");
                    newColor = kb.nextLine();

                    updateColor(updatedVin, newColor, carDatabase);

                } else System.out.println("Input selection invalid.");


            }

            // If user selects REMOVE
            if (selectedAction.equals("3")) {
                // get updates for existing car
                System.out.print("Input VIN of the car to remove: ");
                String vinToRemove = null;
                vinToRemove = kb.nextLine();

                // get vin to remove
                remove(vinToRemove, carDatabase);
            }

            // If user selects RETRIEVE lowest PRICE
            if (selectedAction.equals("4")) {
                Car lowestPriceCar = retrievLowestPrice(carDatabase);
                String lowestPriceCarString = "";
                try {
                    lowestPriceCarString = lowestPriceCar.toString();
                } catch (NullPointerException npe) {
                    System.out.println("The Database is currently empty. Please add a car!");
                }

                System.out.println("LowestPrice car is: " + lowestPriceCarString);
            }

            // If user selects RETRIEVE lowest MILEAGE
            if (selectedAction.equals("5")) {
                Car lowestMileageCar = retrieveLowestMileage(carDatabase);
                String lowestMileageCarString = "";
                try {
                    lowestMileageCarString = lowestMileageCar.toString();
                } catch (NullPointerException npe) {
                    System.out.println("The Database is currently empty. Please add a car!");
                }

                System.out.println("LowestPrice car is: " + lowestMileageCarString );

            }

            // If user selects RETRIEVE lowest PRICE by MAKE and MODEL
            if (selectedAction.equals("6")) {
                // get make
                String make = "";
                System.out.println("Input make: ");
                make = kb.nextLine();
                // get model
                String model = "";
                System.out.println("Input model: ");
                model = kb.nextLine();

                Car lowestPriceCar = retrieveLowestPriceGivenMakeAndModel(make, model, carDatabase);
                try {
                    System.out.println("Lowest price car is: " + lowestPriceCar.toString());
                } catch (NullPointerException npe) {
                    System.out.println("No cars match that make and mode. Please add one!");
                }

            }

            // If user selects RETRIEVE lowest MILEAGE by MAKE and MODEL
            if (selectedAction.equals("7")) {
                // get make
                String make = "";
                System.out.println("Input make: ");
                make = kb.nextLine();
                // get model
                String model = "";
                System.out.println("Input model: ");
                model = kb.nextLine();

                Car lowestMileageCar = retrieveLowestMileageGivenMakeAndModel(make, model, carDatabase);
                try {
                    System.out.println("Lowest mileage car is: " + lowestMileageCar.toString());
                } catch (NullPointerException npe) {
                    System.out.println("No cars match that make and mode. Please add one!");
                }
            }

            // If user wants to quit
            if (selectedAction.contains("q")) {
                System.out.println("Exiting program.");
                quit = true;
            }

        } // end-while

        // close input stream
        kb.close();
    }

    //////////////////////////
    //// DB QUERY METHODS ////
    //////////////////////////
    /**
     * Add a new car to the database
     * @param carDB
     * @param newCar
     * @return
     */
    public static void add(RelativePQ carDB, Car newCar) {
        // adds a new car to the DB, if it is valid
        carDB.add(newCar);
    }

    /**
     * Update a selected car's price
     * @param vinNumber
     * @param newPrice
     * @return
     */
    public static void updatePrice(String vinNumber, Double newPrice, RelativePQ carDB) {
        // updates a car which is ALREADY IN the database
        // Here, we just make a new car object, since this is an easy way to pass the values into our existing car
        // the non-relevant items won't get touched.
        Car carUpdates = new Car(vinNumber, "", "", newPrice, 0.0, "", true, false);
        // first TRUE, means we update price
        carDB.update(carUpdates, true, false, false);
    }


    /**
     * Update a selected car's mileage
     * @param vinNumber
     * @param newMileage
     * @return
     */
    public static void updateMileage(String vinNumber, Double newMileage, RelativePQ carDB) {
        // updates a car which is ALREADY IN the database
        // Here, we just make a new car object, since this is an easy way to pass the values into our existing car
        // the non-relevant items won't get touched.
        Car carUpdates = new Car(vinNumber, "", "", 0.0, newMileage, "", true, false);
        // second TRUE, means we update mileage
        carDB.update(carUpdates, false, true, false);
    }

    /**
     * Update a selected car's color
     * @param vinNumber
     * @param newColor
     * @return
     */
    public static void updateColor(String vinNumber, String newColor, RelativePQ carDB) {
        // updates a car which is ALREADY IN the database
        // Here, we just make a new car object, since this is an easy way to pass the values into our existing car
        // the non-relevant items won't get touched.
        Car carUpdates = new Car(vinNumber, "", "", 0.0, 0.0, newColor, true, false);
        // second TRUE, means we update mileage
        carDB.update(carUpdates, false, false, true);
    }

    /**
     * Remove a car from the database, given its VIN
     * @param vinID
     * @return
     */
    public static void remove(String vinID, RelativePQ carDB) {
        // remove a car from the database, given its VIN
        carDB.remove(vinID);
    }

    /**
     * Retrieve lowest price car in the DB
     * @return
     */
    public static Car retrievLowestPrice(RelativePQ carDB) {
        // retrieve he lowest price car in the database
        return carDB.getLowestPrice();
    }

    /**
     * Retrieve lowest mileage car in the DB
     * @return
     */
    public static Car retrieveLowestMileage(RelativePQ carDB) {
        // retrieve lowest mileage car in the database
        return carDB.getLowestMileage();
    }

    /**
     * Retrieve lowest price car, given make and model
     * @param carMake
     * @param carModel
     * @return
     */
    public static Car retrieveLowestPriceGivenMakeAndModel(String carMake, String carModel, RelativePQ carDB) {
        // retrieves lowest price car, given make and model
        return carDB.getLowestPriceByMakeAndModel(carMake, carModel);
    }

    /**
     * Retrieve lowest mileage car, given make and model
     * @param carMake
     * @param carModel
     * @return
     */
    public static Car retrieveLowestMileageGivenMakeAndModel(String carMake, String carModel, RelativePQ carDB) {
        // retrieves lowest mileage car, given make and model
        return carDB.getLowestMileageByMakeAndModel(carMake, carModel);
    }

}

