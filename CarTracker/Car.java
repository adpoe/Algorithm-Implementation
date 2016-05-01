
/**
 * Created by tony on 3/5/16.
 */
public class Car implements Comparable<Car> {
    String vin;     // Cannot contain I, O, or Q.  17-char string fo digits and capital letters
    String make;    // (e.g. - Ford, Toyota, Honda)
    String model;   // (e.g. - Fiesta, Camry, Civic)
    Double price;   // Purchase price, in dollars
    Double mileage; // The car's mileage
    String color;   // The car's color
    boolean compareOnPrice;
    boolean compareOnMiles;


    /**
     * No args constructor. Creates the object, but initializes everything to null.
     */
    Car () {
        vin = null;
        make = "N/A";
        model = "N/A";
        price = 0.0;
        mileage = 0.0;
        color = "N/A";
        compareOnPrice = true;
        compareOnMiles = false;
    }

    /**
     * Args constructor, using all the setters and getters
     * @param newVin the car's vin
     * @param newMake the car's make
     * @param newModel the car's model
     * @param newPrice the car's price
     * @param newMileage the car's mileage
     * @param newColor the car's color
     */
    Car (String newVin, String newMake, String newModel, Double newPrice, double newMileage, String newColor,
          boolean useMiles, boolean usePrice) {
        if (newVin.contains("I")) {
            System.out.println("VIN cannot contain an I.");
            return;
        }
        if (newVin.contains("O")) {
            System.out.println("VIN cannot contain an O.");
            return;
        }
        if (newVin.contains("Q")) {
            System.out.println("VIN cannot contain a Q.");
            return;
        }

        newVin = newVin.toUpperCase();

        this.setVin(newVin);
        this.setMake(newMake);
        this.setModel(newModel);
        this.setPrice(newPrice);
        this.setMileage(newMileage);
        this.setColor(newColor);
        compareOnPrice = usePrice;
        compareOnMiles = useMiles;
    }


    //////////////////////
    /////  EQUALS   //////
    //////////////////////

    /**
     * Equals is compared against the VIN numbers
     * @param compareCar
     * @return true if VIN's match. False if the VIN's don't match.
     */
    public boolean equals(Car compareCar) {
        return this.getVin().equalsIgnoreCase(compareCar.getVin());
    }


    ////////////////////////
    /////  COMPARABLE  /////
    ////////////////////////

    /**
     * Define what we are comparing on.
     * @param compareCar
     * @return
     */
    public int compareTo(Car compareCar) {
        if (compareOnPrice) {
            return comparePrice(compareCar);
        } else {
            return compareMileage(compareCar);
        }

    }

    /**
     * Comparable for mileage on car objects
     * @param compareCar Car to compare against
     * @return -1 if this < comparable
     *          0 if this == comparable
     *          1 if this > comparable
     */
    public int compareMileage(Car compareCar) {
        if (this.getMileage() < compareCar.getMileage()) {
            return -1;
        } else if (this.getMileage().equals(compareCar.getMileage())) {
            return  0;
        } else {
            return 1;
        }
    }

    /**
     * Comparable for price on car objects
     * @param compareCar Car to compare against
     * @return -1 if this < comparable
     *          0 if this == comparable
     *          1 if this > comparable
     */
    public int comparePrice(Car compareCar) {
        if (this.getPrice() < compareCar.getPrice()) {
            return -1;
        } else if (this.getPrice().equals(compareCar.getPrice())) {
            return  0;
        } else {
            return 1;
        }
    }


    ////////////////////////
    ///////  PRINT  ////////
    ////////////////////////

    public String toString() {
        String concatSring = "Make: " + this.getMake() + "\n" +
                             "Model: " + this.getModel() + "\n" +
                             "VIN: " + this.getVin() + "\n" +
                             "Color: " + this.getColor() + "\n" +
                             "Mileage: " + this.getMileage().toString() + "\n" +
                             "Price: " + this.getPrice().toString();

        return concatSring;
    }


    /////////////////////////////////
    ////// GETTERS AND SETTERS //////
    /////////////////////////////////

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
