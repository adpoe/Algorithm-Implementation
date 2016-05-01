
public class Main {


    public static void main(String[] args) {
    RelativePQ carDatabase = new RelativePQ();

	// write your code here
    //    CarTracker.main();

    // TEST CASES
        System.err.println("------TEST_CASES-------");
        boolean dataCorrect = addDataSet(carDatabase);
        System.err.println("Data correctly added=" + dataCorrect);
        System.err.println("............");
        boolean canGetLowestPrice = carDatabase.getLowestPrice().getVin().equals("AAAAAAAAAAAAAAAAAH");
        System.err.println("Can get LOWEST PRICE correctly=" + canGetLowestPrice);
        System.err.println("............");
        boolean canGetLowestMileage = carDatabase.getLowestMileage().getVin().equals("AAAAAAAAAAAAAAAAAH");
        System.err.println("Can get LOWEST MILEAGE correctly=" + canGetLowestMileage);
        System.err.println("............");
        boolean colorUpdateCorrect = canUpdateColor(carDatabase);
        System.err.println("Car COLOR updated correctly="+colorUpdateCorrect);
        System.err.println("............");
        boolean mileageUpdateCorrect = canUpdateMileage(carDatabase);
        System.err.println("Car MILEAGE updated correctly="+mileageUpdateCorrect);
        //System.out.println("Car Lowest Mileage is NOW: " + carDatabase.getLowestMileage().toString());
        System.err.println("............");
        boolean priceUpdateCorrect = canUpdatePrice(carDatabase);
        System.err.println("Car PRICE updated correctly="+priceUpdateCorrect);
        //System.out.println("Car Lowest price is NOW: " + carDatabase.getLowestPrice().toString());
        System.err.println("............");
        boolean priceByMakeAndModelCorrect = canGetLowestPriceByMakeAndModel(carDatabase);
        System.err.println("Can get LOWEST PRICE by MAKE and MODEL=" + priceByMakeAndModelCorrect);
        System.err.println("............");
        boolean mileageByMakeAndModelCorrect = canGetLowestMileageByMakeAndModel(carDatabase);
        System.err.println("Can get LOWEST MILEAGE by MAKE and MODEL="+mileageByMakeAndModelCorrect);
        System.err.println("............");
        boolean removalWorksCorrectly = canRemoveCorrectly(carDatabase);
        System.err.println("Can REMOVE an item from consideration correctly="+removalWorksCorrectly);
        System.err.println("");

    }

    public static boolean addDataSet(RelativePQ carDatabase) {
        // First Toyota Celica
        Car toyotaCelica01 = new Car("AAAAAAAAAAAAAAAAAB", "Toyota", "Celica",
                                     29995.0, 101000.0, "Black", false, true);
        carDatabase.add(toyotaCelica01);

        // Second Toyota Celica
        Car toyotaCelica02 = new Car("AAAAAAAAAAAAAAAAAC", "Toyota", "Celica",
                29996.0, 102000.0, "Black", false, true);
        carDatabase.add(toyotaCelica02);

        // Third Toyota Celica
        Car toyotaCelica03 = new Car("AAAAAAAAAAAAAAAAAD", "Toyota", "Celica",
                29997.0, 103000.0, "Black", false, true);
        carDatabase.add(toyotaCelica03);

        // Fourth Toyota Celica -> has LOWEST price for TOYOTA CELICAs
        Car toyotaCelica04 = new Car("AAAAAAAAAAAAAAAAAF", "Toyota", "Celica",
                19997.0, 104000.0, "Green", false, true);
        carDatabase.add(toyotaCelica04);

        // Fifth Toyota Celica -> has LOWEST mileage for TOYOTA CELICAs
        Car toyotaCelica05 = new Car("AAAAAAAAAAAAAAAAAG", "Toyota", "Celica",
                29998.0, 14000.0, "Black", false, true);
        carDatabase.add(toyotaCelica05);

        // First Hyundai Accord -> has LOWEST everything
        Car hyundaiAccord01 = new Car("AAAAAAAAAAAAAAAAAH", "Honda", "Accord",
                9998.0, 1000.0, "Black", false, true);
        carDatabase.add(hyundaiAccord01);

        // First Toyota Camry -> Same make, different model
        Car toyotaCamry01 = new Car("AAAAAAAAAAAAAAAAAJ", "Toyota", "Camry",
                29998.0, 103000.0, "Orange", false, true);
        carDatabase.add(toyotaCamry01);

        // If we have the right minKey after all these adds, then add is functional
        return carDatabase.priceQueue.minKey().equals(hyundaiAccord01);
    }

    public static boolean canUpdateColor(RelativePQ carDatabase) {
        Car updatedCar = new Car("AAAAAAAAAAAAAAAAAH", "Hyundai", "Accord",
                9998.0, 1000.0, "Red", true, false);
        carDatabase.update(updatedCar, false, false, true);
        System.out.println(carDatabase.getLowestMileage().toString());
        return carDatabase.getLowestMileage().getColor().equals("Red");
    }

    public static boolean canUpdateMileage(RelativePQ carDatabase) {
        Car updatedCar = new Car("AAAAAAAAAAAAAAAAAH", "Hyundai", "Accord",
                9998.0, 99999999999.0, "Red", true, false);
        carDatabase.update(updatedCar, false, true, false);
        return !carDatabase.getLowestMileage().equals(updatedCar);
    }

    public static boolean canUpdatePrice(RelativePQ carDatabase) {
        Car updatedCar = new Car("AAAAAAAAAAAAAAAAAH", "Honda", "Accord",
                9999999998.0, 10000, "Red", false, true);
        carDatabase.update(updatedCar, true, false, false);
        return !carDatabase.getLowestPrice().equals(updatedCar);
    }

    public static boolean canGetLowestPriceByMakeAndModel(RelativePQ carDatabase) {
        boolean validResponse = true;

        // Check we can get correct Toyota Celica
        Car minToyotaByPrice = carDatabase.getLowestPriceByMakeAndModel("Toyota", "Celica");
        // System.out.println(minToyotaByPrice.toString());
        if (!minToyotaByPrice.getVin().equals("AAAAAAAAAAAAAAAAAF")) {
            return false;
        }

        // Check we can get correct Honda Accord
        Car minHondaByPrice = carDatabase.getLowestPriceByMakeAndModel("Honda", "Accord");
        // System.out.println(minHondaByPrice.toString());
        if (!minHondaByPrice.getVin().equals("AAAAAAAAAAAAAAAAAH")) {
            return false;
        }

        // Check if we can get correct Toyota Camry
        Car minCamryByPrice = carDatabase.getLowestPriceByMakeAndModel("Toyota", "Camry");
        if (!minCamryByPrice.getVin().equals("AAAAAAAAAAAAAAAAAJ")) {
            return false;
        }

        // Check we can get a null for something not in the database
        Car minCadillacByPrice = carDatabase.getLowestPriceByMakeAndModel("Cadillac", "CTS");
        if (minCadillacByPrice != null) {
            return false;
        }

        return validResponse;
    }

    public static boolean canGetLowestMileageByMakeAndModel(RelativePQ carDatabase) {
        boolean validResponse = true;

        // Check we can get correct Toyota Celica
        Car minToyotaByPrice = carDatabase.getLowestMileageByMakeAndModel("Toyota", "Celica");
        // System.out.println(minToyotaByPrice.toString());
        if (!minToyotaByPrice.getVin().equals("AAAAAAAAAAAAAAAAAG")) {
            return false;
        }

        // Check we can get correct Honda Accord
        Car minHondaByPrice = carDatabase.getLowestPriceByMakeAndModel("Honda", "Accord");
        // System.out.println(minHondaByPrice.toString());
        if (!minHondaByPrice.getVin().equals("AAAAAAAAAAAAAAAAAH")) {
            return false;
        }

        // Check if we can get correct Toyota Camry
        Car minCamryByPrice = carDatabase.getLowestPriceByMakeAndModel("Toyota", "Camry");
        if (!minCamryByPrice.getVin().equals("AAAAAAAAAAAAAAAAAJ")) {
            return false;
        }

        // Check we can get a null for something not in the database
        Car minCadillacByPrice = carDatabase.getLowestPriceByMakeAndModel("Cadillac", "CTS");
        if (minCadillacByPrice != null) {
            return false;
        }

        return validResponse;
    }

    public static boolean canRemoveCorrectly(RelativePQ carDatabase) {

        // First Rolls Royce -> will be new LOW PRICE and LOW MILEAGE after we remove the Camry
        Car rollsRoyce01 = new Car("AAAAAAAAAAAAAAAAAK", "Rolls-Royce", "Phantom-2",
                12000.0, 12000.0, "Crimson", false, true);
        carDatabase.add(rollsRoyce01);

        carDatabase.remove("AAAAAAAAAAAAAAAAAJ");

        // Check that price queue has correct value, after removal
        // We return FALSE if it's NOT the Rolls
        if (!carDatabase.getLowestPrice().getVin().equals("AAAAAAAAAAAAAAAAAK")) {
            return false;
        }


        // Check that mileage queue has correct value, after removal
        // Again, we return FALSE if it's NOT the Rolls
        if (!carDatabase.getLowestMileage().getVin().equals("AAAAAAAAAAAAAAAAAK")) {
            return false;
        }

        // if we make it this far, we're okay. Return True
        return true;
    }
}

