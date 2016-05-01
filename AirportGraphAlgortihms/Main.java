
public class Main {

    public static void main(String[] args) {
	// write your code here
        // 1.  Get filename
        // ask user for a filename containing all the routes offered by the airline
        // route files must contain all routes offered by airline. (data1 and data2 are examples)
        // Structure - 1 number of cities serviced by airline
        //             2 names of cities serviced by airline
        //             3 list of routes between those cities
        //                  :: route is:  numbers of the two cities (order in which they appear in file, starting @ 1)
        //                                distance between them
        //                                cost of the flight
        // Use this to build the graph

        System.out.println("Ciao!");

        Airline myAirline = new Airline();
        myAirline.readInputFromFile("airline_data1.txt");
        myAirline.getUserInput();

        /////// Confirm that read file works //////
        // Tests.testReadFile();
        Tests.testWriteFile();
        ///////////////////////////////////////////

        // 2.  Must represent graph using an ADJACENCY LIST.
        //        --  cities should minimally have a string for a name, and any other info we want to add
        //        --  DATA should be the input from the routes file specified by the user
        //        --  EDGES will have multiple values (distance, price)
        //                  :: this means they have multiple weights
        //        -- Assume that the flights are BI-DIRECTIONAL
        //                  :: means don't need a DIGRAPH, just a weighted graph




        //////////////////////
        /////// TESTS ////////
        //////////////////////

        // Minimum Spanning Tree

        // Shortest COST path (shortest path based on cost weight)

        // Shortest MILES path

        // Shortest HOPS

    }
}
