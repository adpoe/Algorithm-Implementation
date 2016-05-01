import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;

/**
 * @author Anthony (Tony) Poerio
 * @email adp59@pitt.edu
 * CS1501 - University of Pittsburgh
 * Spring 2016 - Assignment #4
 * Airline Project, Using Graph Algorithms
 *
 * USAGE:
 *      to compile --> javac Airline.java
 *      to run --> java Airline "input_file.txt"
 *          :: If no input file is specified, then "airline_data1.txt" will be used.
 *
 * INPUT EXAMPLES:
 *      See two example input files, "airline_data1.txt", and "airline_data2.txt"
 *      Input data must match these files in format.
 *
 * NOTES:
 *      This program uses a variety of Graph Algorithms to determine shortest paths,
 *      minimum spanning trees, etc., based on two edge-weights:  price and distance.
 *      The program models the system created for all of an airline's routes, and we use the
 *      graph algorithms to crunch data about the overall system.
 *
 * >> See assignment info sheet for dependencies and brief implementation details.
 */
public class Airline {
    // Class level variables
    int howManyCities;
    String[] cityNames;  // need to initialize this from the file i/o when we read it in
    public EdgeWeightedGraph myRoutes;
    LinkedQueue<Edge> myEdges = new LinkedQueue<Edge>();
    String filePath;

 ///////////////////////
    ///// USER INPUT //////
    ///////////////////////
    // Handle user input
    public void getUserInput() {
        System.out.println();
        Scanner kb = new Scanner(System.in);
        // get filename
        System.out.println("CS1501 - Assignment 4:: Airline");
        System.out.println("-------------------------------");
        System.out.println("Please enter an input file: ");
        System.out.print(" > ");
        String myFilePath = kb.nextLine();
        // read the file's data and start the program
        this.readInputFromFile(myFilePath);

        String userSelection = "";
        System.out.println();
        // while user has not entered 7
        while (!userSelection.equals("9")){
            System.out.println("---------------MAIN MENU----------------------");
            System.out.println("Please select an option");
            System.out.println("1.  SHOW LIST of routes available");
            System.out.println("2.  DISPLAY MST to the console for the service routes based on distances");
            System.out.println("3.  Get Shortest Path based on TOTAL MILES");
            System.out.println("4.  Get Shortest Path based on TOTAL PRICE");
            System.out.println("5.  Get Shortest Path based on FEWEST HOPS");
            System.out.println("6.  FIND all trips less than some dollar amount");
            System.out.println("7.  ADD new route");
            System.out.println("8.  REMOVE a route");
            System.out.println("9.  EXIT program");
            System.out.print(" >  ");
            userSelection = kb.nextLine();
        // With route list loaded, program should be able to answer the following queries:
        //          i.  show ENTIRE LIST of direct routes, distances and prices.
        //                  means outputting graph in a well-formatted way
            if (userSelection.equals("1")) {
                // my code
                printDirectRoutes();
            }
        //          ii. Display an MST to the console for the SERVICE ROUTES based on DISTANCES
        //                  IF route graph is NOT CONNECTED --> Query should identify and show EACH of the connected components
        //                      :: IF NO MST
        //                             -- Display EACH SET
        //                             -- Can do that KRUZKAL or whatever it's called, bc everything connected
            else if (userSelection.equals("2")) {
                // prints a minimum spanning forest
                System.out.println("THE __EDGES__ IN THE **MST** (or forest) BASED ON DISTANCE (unordered): ");
                System.out.println("----------------------------------------------------------------------- ");
                printMSTByDistance();
            }
        //          iii. Allow for each of the 'shortest path' searches below. For each search, user should be able to
        //               enter SOURCE and DESTINATION cities (names) --> OUTPUT: cities in the path, from src to dst
        //               SUM:  Cost of each link to find 'cost' of entire trip
        //               IF TIE:  Only print one out
        //               IF NO PATH:  Print that
        //
        //                  PATHS:
        //                  ------
        //                a.  Shortest past based on TOTAL MILES (ONE WAY)
            else if (userSelection.equals("3")) {
                System.out.print("What is your START city? > ");
                System.out.println();
                // get a start city from user
                String startCity = kb.nextLine();

                System.out.print("What is your END city? > ");
                System.out.println();
                // get an end city from user
                String endCity = kb.nextLine();

                System.out.println("YOUR SHORTEST PATH IS: ");
                System.out.println("---------------------- ");
                getShortestPathByMiles(startCity, endCity);
                System.out.println();
                System.out.println();
            }
        //                b.  Shortest path based on TOTAL PRICE
            else if (userSelection.equals("4")) {
                System.out.print("What is your START city? > ");
                System.out.println();
                // get a start city from user
                String startCity = kb.nextLine();

                System.out.print("What is your END city? > ");
                System.out.println();
                // get an end city from user
                String endCity = kb.nextLine();

                System.out.println("YOUR SHORTEST PATH IS: ");
                System.out.println("---------------------- ");
                getShortestPathByPrice(startCity, endCity);
                System.out.println();
                System.out.println();
            }
        //                c.  Shortest path based on NUMBER OF HOPS
            else if (userSelection.equals("5")) {
                System.out.print("What is your START city? > ");
                System.out.println();
                // get a start city from user
                String startCity = kb.nextLine();

                System.out.print("What is your END city? > ");
                System.out.println();
                // get an end city from user
                String endCity = kb.nextLine();


                getShortestPathByHops(startCity, endCity);
                System.out.println();
                System.out.println();
            }

        //          iv. Given $$$ amount --> OUTPUT:  All trips that cost LESS THAN OR EQ TO (<=) that amount
        //                ENSURE:
        //                        o NO REPEATS
        //                        o NO CYCLES
        //                        o DONE EFFICIENTLY --> AVOID EXPONENTIAL RUNTIME
            else if (userSelection.equals("6")) {
                System.out.print("Please enter the MAXIMUM COST: ");
                System.out.println();
                double userCost = kb.nextDouble();
                // eat the nextline, since nextDouble doesn't do that
                kb.nextLine();
                // add user specified cost
                System.out.println("LIST OF PATHS AT MOST "+userCost+" DOLLARS:");
                System.out.println("---------------------------------------------");
                //findTripsLessThanAmount(userCost);
                findTripsLessThanAmountDEPTH_FIRST(userCost);
            }
        //           v. ADD A NEW ROUTE  :: Assume - both cities already exist
        //                 ACCEPT:
        //                        o Vertices
        //                        o Distances
        //                        o Price
        //                            --> ADD A NEW EDGE
        //                 CONSIDER:  How does this affect other algorithms from above
        //                 UPDATE METHOD:  Update everything
            else if (userSelection.equals("7")) {
                // get the start city
                System.out.print("Enter START city: ");
                String startCity = kb.nextLine();
                System.out.print("Enter END city: ");
                String endCity = kb.nextLine();
                System.out.print("Enter DISTANCE: ");
                double routeDistance = kb.nextDouble();
                kb.nextLine();
                System.out.print("Enter PRICE: ");
                double routePrice = kb.nextDouble();
                kb.nextLine();
                addNewRoute(startCity, endCity, routeDistance, routePrice);
            }
        //          vi. REMOVE A ROUTE
        //                ACCEPT:
        //                        o Vertices
        //                WHEN THIS HAPPENS --> NEED TO @UPDATE()
        //
            else if (userSelection.equals("8")) {
                // get the start city
                System.out.print("Enter START city: ");
                String startCity = kb.nextLine();
                System.out.print("Enter END city: ");
                String endCity = kb.nextLine();
                System.out.print("Enter DISTANCE: ");
                double routeDistance = kb.nextDouble();
                kb.nextLine();
                System.out.print("Enter PRICE: ");
                double routePrice = kb.nextDouble();
                kb.nextLine();
                removeRoute(startCity, endCity, routeDistance, routePrice);
            }
        //         vii:  QUIT --> Save the routes back to file in SAME FORMAT they were read in
            else if (userSelection.equals("9")) {
                exitProgram();
            } else {
                System.out.println();
                System.out.println("Input invalid. Please try again");
                System.out.println();
            }
        }
    }

    ///////////////////////
    ///// I/O Methods /////
    ///////////////////////
    // Get the input from a data file
    public void readInputFromFile(String filepath) {
        try {
            this.filePath = filepath;
            // read from the file
            File myFile = new File(filepath);
            Scanner scan = new Scanner(myFile);

            // get number of cities, since that's expected to be first thing in our data set
            if (scan.hasNextInt()) {
                 howManyCities = scan.nextInt();
            } else {
                System.out.println("Could not read from input file. The format is incorrect. Must have number of " +
                        "cities as the as first line.");
                System.exit(0);
            }
            // confirm number of cities
            System.out.println("HOW MANY CITIES: " + howManyCities);
            // Initialize our graph to have however many vertices we just put in
            myRoutes = new EdgeWeightedGraph(howManyCities+1);

            // initialize our array of city names
            cityNames = new String[howManyCities+1];
            cityNames[0] = null;

            // eat the nextline from before, because we only got an int and didn't get the newline yet
            scan.nextLine();
            // get each of our cities and place them in our city name array
            for (int i=1; i<=howManyCities; i++) {
                cityNames[i] = scan.nextLine();
                // confirm we're getting this okay
                System.out.println("CITY "+i+": "+cityNames[i]);
            }

            // get the remaining items in our input, they will (or should) routes

            while (scan.hasNextLine()) {
                String currentRoute = scan.nextLine();
                String[] routeData = currentRoute.split(" ");
                int sourceCity = Integer.parseInt(routeData[0]);
                int destinationCity = Integer.parseInt(routeData[1]);
                double flightMiles = Double.parseDouble(routeData[2]);
                double flightCost = Double.parseDouble(routeData[3]);

                // create an edge
                Edge nextEdge = new Edge(sourceCity, destinationCity, flightMiles, flightCost, cityNames[sourceCity], cityNames[destinationCity]);
                System.out.println(nextEdge.toString());
                myEdges.enqueue(nextEdge); // add our edge to the list of edges. we'll need this in an easily readable format later

                // add edge to our class level graph
                myRoutes.addEdge(nextEdge);

                // print everything to make sure it's right
                System.out.println("Source, Dest = " +sourceCity+","+destinationCity);
                System.out.println("flight miles = " +flightMiles);
                System.out.println("flight cost = " + flightCost);
                System.out.println("-----end route----");

            }
            // close scanner
            scan.close();

            // print our graph
            System.out.println("===== MY GRAPH =====");
            System.out.println(myRoutes.toString());


        } catch (FileNotFoundException fnfe){
            System.out.println("Sorry, that file was not found.");
            System.out.println("Please re-start the program and enter your file name again");
            System.exit(0);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }




    // Write output to data file
    public void writeDataToFile(String filePath) {
        // create a new File object
        File outFile = new File(filePath);
        try {
            FileWriter fw = new FileWriter(outFile);
            // write the number of cities
            String cityNumber = String.valueOf(howManyCities);
            fw.write(cityNumber);
            fw.write("\n");  // do we need to do this?

            // write each city
            for (int i=1; i<=howManyCities; i++) {
                fw.write(cityNames[i]);
                fw.write("\n");
            }

            // write our routes
            while (!myEdges.isEmpty()) {
                Edge currentEdge = myEdges.dequeue();
                String printString = "";
                printString += currentEdge.getV() + " ";
                printString += currentEdge.getW() + " ";
                printString += (int)currentEdge.getMiles() + " ";
                printString += currentEdge.getCost() + "\n";
                fw.write(printString);
            }

            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // print direct routes
    public void printDirectRoutes() {
        System.out.println("HERE IS A LIST OF ALL ROUTES AVAILABLE: ");
        System.out.println("----------------------------------------");
        System.out.println(myRoutes.toString());
    }

    //////////////////////
    ///// FUNCTIONS //////
    //////////////////////
    public void printMSTByDistance(){
        KruskalMST myMST = new KruskalMST(myRoutes);

        for (Edge e : myMST.edges()) {
            StdOut.println(e);
        }
        System.out.println();
        StdOut.printf(">> TOTAL DISTANCE in the MST (or forest) is: %.5f\n", myMST.weight());
        System.out.println("\t note:  MST or forest is computed using Kruskal's Algorithm");
        System.out.println();

    }


    public void getShortestPathByMiles(String startCity, String endCity) {
        // find the index number associated with our start city
        int startCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (startCity.equals(cityNames[i])) {
                startCityIndex = i;
                break;
            }
        }

        if (startCityIndex == 0) {
            System.out.println("Sorry, your START CITY was not found.");
            return;
        }

        // find the index number associated with our end city
        int endCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (endCity.equals(cityNames[i])) {
                endCityIndex = i;
                break;
            }
        }

        if (endCityIndex == 0) {
            System.out.println("Sorry, your END CITY was not found.");
            return;
        }

        // pass the edgeweighted graph and start city to dijkstra
        DijkstraUndirectedSP myDijkstra = new DijkstraUndirectedSP(myRoutes, startCityIndex);

        // CHECK if our dijkstra has path from startCity to endCity
        // and if ti does, PRINT that path
        // print shortest path
        String compareValue = "MILES";
        //for (int t = 0; t < myRoutes.V(); t++) {
            if (myDijkstra.hasPathTo(endCityIndex)) {
                StdOut.printf("Route: %s to %s, Distance: (%.2f) ~ ", startCity, endCity, myDijkstra.distTo(endCityIndex));
                for (Edge e : myDijkstra.pathTo(endCityIndex)) {
                    StdOut.print(e + "   ");

                    // check what we're comparing on, need this later when we print
                    if (!e.compareMiles) {
                        compareValue = "DOLLARS";
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", startCityIndex, endCityIndex);
            }
        //}

        StdOut.println("\t >>> SHORTEST trip is: " + myDijkstra.distTo(endCityIndex) + " " + compareValue);
        //if (myDijkstra.hasPathTo(endCityIndex)) {
        //    // and if it does, PRINT that path
        //    myDijkstra.pathTo(endCityIndex);
        //}
    }

    public void getShortestPathByPrice(String startCity, String endCity) {
        // iterate through heap and change the boolean values so we get shortest path by price
        for (Edge e : myRoutes.edges()) {
            e.setCompareMiles(false);
        }

        // get shortest path by price --> by re-using our code from above,
        // we're just comparing everything on PRICE now
        getShortestPathByMiles(startCity, endCity);


        // iterate through heap and change the boolean values so we get shortest path by miles again
        // getting paths by miles is our default
        for (Edge e : myRoutes.edges()) {
            e.setCompareMiles(true);
        }
    }

    public void getShortestPathByHops(String startCity, String endCity) {
        // create a graph out of our input file data
        // --> DO THIS WHENEVER WE FIRST READ THE DATA IN
        // idea --> use the graph class..


        // WORKING ON:  SHORTEST PATH BY FEWEST HOPS

        // find the index number associated with our start city
        int startCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (startCity.equals(cityNames[i])) {
                startCityIndex = i;
                break;
            }
        }

        if (startCityIndex == 0) {
            System.out.println("Sorry, your START CITY was not found.");
            return;
        }


        // find the index number associated with our end city
        int endCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (endCity.equals(cityNames[i])) {
                endCityIndex = i;
                break;
            }
        }

        if (endCityIndex == 0) {
            System.out.println("Sorry, your END CITY was not found.");
            return;
        }
        // initialize an unweighted graph with the number of vertices we've already counted
        Graph myGraph = new Graph(howManyCities);
        for( Edge e : myEdges) {
            // add all the edges we've already seen
            // BUT need to subtract one from our values,
            // since Graph stores starting at 0
            myGraph.addEdge((e.getV()-1),(e.getW()-1));
        }

        // get shortest path for a regular graph, without weights
        BreadthFirstPaths myBFPs = new BreadthFirstPaths(myGraph, startCityIndex-1);

        // need to translate with -1 here, since Graph starts counting at 0
        if (myBFPs.hasPathTo((endCityIndex-1))) {
            // get path and print it
            // can do this using the BREADTH FIRST SEARCH of our graph
            Iterable<Integer> myPath = myBFPs.pathTo((endCityIndex-1));
            System.out.println("Shortest path by FEWEST HOPS from " + startCity + " to " + endCity + " is:");
            System.out.print("\tROUTE ~ ");
            int countHops = 0;
            boolean startCityUsed = false;

            for (Integer i : myPath) {
                // need to ADD 1 when we come back from this, since Graph stores starting at 0
                i += 1;
                //  index into our array of city names with the correct index

                if (i == startCityIndex) {
                    if (startCityUsed) break;
                    StdOut.print(cityNames[startCityIndex]);
                    startCityUsed = true;
                } else  {
                    StdOut.print("-" + cityNames[i]);
                    countHops++;
                }

            }
            StdOut.println();
            System.out.println("\tNumber of hops TOTAL on our shortest path: "+countHops);


        } else {
            System.out.println("There is no complete path between " + startCity + "to " + endCity);
        }

    }

    public void findTripsLessThanAmount(Double maxCost) {
        int count = 0;

        // Make a new Directed Graph
        EdgeWeightedDigraph myDigraph = new EdgeWeightedDigraph(howManyCities);
        // Translate our edges into "Directed Edges"
        for (Edge e : myEdges) {
            // subtract one before putting into our DirectedEdge because these start at 0
            DirectedEdge de = new DirectedEdge((e.getV()-1), (e.getW()-1), e.getCost());
            // And add each directed edge to our directed graph
            myDigraph.addEdge(de);
        }

        // Get all the Paths for *EACH PAIR* of vertices in our directed graph
        // This ensures >>NO REPEATS<<
        // This next line initializes the data structure we need
        DijkstraAllPairsSP allPairsSP = new DijkstraAllPairsSP(myDigraph);

        // and this iterates through all possible pairs and prints the paths,
        // as long as they are less than user specified value
        for (int startVertex=0; startVertex<howManyCities; startVertex++) {
            for (int endVertex=1; endVertex<howManyCities; endVertex++) {
               // checking if a path exists is constant time,
               // so we effective PRUNE by only checking paths when we need to

               if (allPairsSP.hasPath(startVertex,endVertex) &&
                       // also PRUNE by only looking at paths with a max cost less than
                       // what the user has specified
                       // finding cost is constant time
                       allPairsSP.dist(startVertex,endVertex) <= maxCost &&
                       // and ENSURE we have >> NO CYCLES <<
                       startVertex != endVertex)
               {
                   // initialize iterable if our edges
                   Iterable<DirectedEdge> myPath = allPairsSP.path(startVertex, endVertex);
                   // print out all the paths in our edge list to the console
                   System.out.println("PATH from " + cityNames[startVertex + 1] + " to " + cityNames[endVertex + 1]);
                   System.out.print("\tStart at");
                   double totalCost = 0.0;
                   for (DirectedEdge e : myPath) {
                       System.out.print(" ~ ");
                       System.out.print(cityNames[e.from()+1] + "->" + cityNames[e.to()+1]
                               + " " + String.format("%5.2f", e.weight()));
                       totalCost += e.weight();
                   }
                   // print a newline if our iterator wasn't null
                   String myPathString = myPath.toString();
                   if (!myPath.toString().equals("")) {
                       System.out.println();
                       System.out.println("\tTOTAL COST: " + totalCost);
                       System.out.println();
                       count += 1;
                   }
               }

            }
        }

        System.out.println("TOTAL COUNT = " + count);
    }

    public void addNewRoute(String startCity, String endCity, double miles, double cost) {
        // find the index number associated with our start city
        int startCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (startCity.equals(cityNames[i])) {
                startCityIndex = i;
                break;
            }
        }

        if (startCityIndex == 0) {
            System.out.println("Sorry, your START CITY was not found.");
            return;
        }

        // find the index number associated with our end city
        int endCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (endCity.equals(cityNames[i])) {
                endCityIndex = i;
                break;
            }
        }

        if (endCityIndex == 0) {
            System.out.println("Sorry, your END CITY was not found.");
            return;
        }

        // add the route to myRoutes
        Edge newEdge = new Edge(startCityIndex,endCityIndex,miles,cost,startCity,endCity);
        myEdges.enqueue(newEdge);
        myRoutes.addEdge(newEdge);

        // save to file
        this.writeDataToFile(filePath);
        // re-open file
        this.readInputFromFile(filePath);

        // this IO read/write will re-set everything!

    }


    public void removeRoute(String startCity, String endCity, double miles, double cost) {
        // find the index number associated with our start city
        int startCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (startCity.equals(cityNames[i])) {
                startCityIndex = i;
                break;
            }
        }

        if (startCityIndex == 0) {
            System.out.println("Sorry, your START CITY was not found.");
            return;
        }

        // find the index number associated with our end city
        int endCityIndex = 0;
        for (int i=1; i<= howManyCities; i++) {
            // if we find a match, update our index, and break
            if (endCity.equals(cityNames[i])) {
                endCityIndex = i;
                break;
            }
        }

        if (endCityIndex == 0) {
            System.out.println("Sorry, your END CITY was not found.");
            return;
        }

        // remove the route to myRoutes
        Iterator<Edge> edgesIterator = myEdges.iterator();

        // create the edge we want
        Edge newEdge = new Edge(startCityIndex,endCityIndex,miles,cost,startCity,endCity);
        // make a copy of our queue
        // move items from one to the other and if it matches the new edge, don't move it
        LinkedStack<Edge> edgeStack = new LinkedStack<Edge>();

        // check each edge
        while (!myEdges.isEmpty()) {
            // make a copy of each edge
            Edge currentEdge = myEdges.dequeue();
            // check all fields of the edge, to see if we have a match
            if (currentEdge.getV() != startCityIndex ||
                    currentEdge.getW() != endCityIndex ||
                    currentEdge.getCost() != cost ||
                    currentEdge.getMiles() != miles)
            {
                // as long as we DON'T have a match, push onto stack
                edgeStack.push(currentEdge);
            }
            // at the end of this process, we'll have a stack with everything..
            // EXCEPT for an edge that matches what the user specified
        } // end-while

        // replace everything from our stack,
        // and we will have effectively the specified Edge
        while (!edgeStack.isEmpty()) {
            myEdges.enqueue(edgeStack.pop());
        }

        // save to file
        this.writeDataToFile(filePath);
        // re-open file
        this.readInputFromFile(filePath);

        // this IO read/write will re-set everything!
    }

    public void exitProgram() {
        // code goes here
        System.out.println("Confirming exit. Your data has been saved.");
        writeDataToFile(filePath);
        System.exit(0);
    }

    public void findTripsLessThanAmountDEPTH_FIRST(Double maxCost) {
        // translate into an unweighted graph
        Graph myGraph = new Graph(howManyCities+1);
        for (Edge e : myEdges) {
            int start = e.getV();
            int end = e.getW();
            myGraph.addEdge(start,end);
        }

        int pathCount = 0;
        // get all paths for each pair of vertices
        for (int start=0; start<howManyCities; start++) {
            for (int end=0; end<howManyCities; end++) {
                // create a queue to store each edge on our path
                Queue<Edge> edgesOnPath = new Queue<Edge>();
                ListAllPaths<Integer> myPaths = new ListAllPaths<Integer>(myGraph,start,end);

                // iterate through all the paths
                for (String path : myPaths.allPaths) {
                    // split path on spaces
                    String[] vertices = path.split(" ");
                    // build all edges
                    for (int vertex=0; vertex<vertices.length-1; vertex++){
                        // de-reference start city, and translate it back to our number system by adding +1
                        String startCity = vertices[vertex];
                        Integer startCityInt = Integer.parseInt(startCity);
                        //startCity += 1;

                        // de-reference end city, and translate it back to our number system by adding +1
                        String endCity = vertices[vertex+1];
                        Integer endCityInt = Integer.parseInt(endCity);
                        //endCity += 1;

                        // get city names
                        String startCityName = cityNames[startCityInt];
                        String endCityName = cityNames[endCityInt];

                        double edgePrice = 0.0;
                        double edgeMiles = 0.0;

                        // find the proper cost values by matching them up against our set of edges
                        for (Edge e : myEdges) {
                            if (e.getV() == startCityInt &&    // if start city ints match
                                e.getW() == endCityInt ) {     // and if end city ints match
                                    edgePrice = e.getCost();
                                    edgeMiles = e.getMiles();
                            } // end-if

                            // need to also check the other direction
                            if (e.getV() == endCityInt &&
                                e.getW() == startCityInt) {
                                    edgePrice = e.getCost();
                                    edgeMiles = e.getMiles();
                            }
                        } // end-for

                        // create an edge for our path
                        Edge pathEdge = new Edge(startCityInt, endCityInt, edgeMiles, edgePrice, startCityName, endCityName);

                        // add it to a Queue
                        edgesOnPath.enqueue(pathEdge);
                    }
                    // check if the path is < cost
                    double pathCost = 0.0;
                    for (Edge e : edgesOnPath) {
                        pathCost += e.getCost();
                    }

                    // and print it if so...
                    if (pathCost <= maxCost && pathCost > 0.0) {
                        pathCount += 1;
                        System.out.print("ROUTE ~ ");
                        for (Edge e : edgesOnPath) {
                            System.out.print(e.toString());
                            System.out.print("-> ");
                        }
                        System.out.print("END");
                        System.out.println("\n\tTOTAL COST: " + pathCost);
                        System.out.println();
                    }

                    // empty the queue, so we can build a new path in it
                    while (!edgesOnPath.isEmpty()) edgesOnPath.dequeue();
                }
            }
        }

        //System.out.println("TOTAL PATHS FOUND: "+pathCount);

    }


    //////////////////////////
    //// MAIN ENTRY POINT ////
    //////////////////////////
    public static void main(String[] args) {
        Airline myAirline = new Airline();
        myAirline.getUserInput();
    }
}
