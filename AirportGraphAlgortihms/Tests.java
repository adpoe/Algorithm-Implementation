
/**
 * Created by tony on 3/25/16.
 */
public class Tests {

    public static void testReadFile() {
        Airline myAirline = new Airline();
        System.out.println("READING INPUT FOR DATA 1");
        System.out.println("=========================");
        myAirline.readInputFromFile("airline_data1.txt");
        System.out.println("READING INPUT FOR DATA 2");
        System.out.println("=========================");
        myAirline.readInputFromFile("airline_data2.txt");
    }

    public static void testWriteFile() {
        Airline myAirline = new Airline();
        myAirline.readInputFromFile("airline_data1.txt");
        myAirline.writeDataToFile("confirm_file1.txt");
        myAirline.readInputFromFile("airline_data2.txt");
        myAirline.writeDataToFile("confirm_file2.txt");
    }

}
