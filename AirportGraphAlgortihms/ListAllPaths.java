
/**
 * Class used to get ALL PATHS in a given graph
 * @param <Vertex>
 */
public class ListAllPaths<Vertex> {

    // Store our current path in a stack
    private Stack<Integer> currentPath  = new Stack<Integer>();
    // Keep a set of all the vertices on the path
    private SET<Integer> verticesOnPath  = new SET<Integer>();
    // A Queue containing ALL THE PATHS we get between two vertices
    public Queue<String> allPaths = new Queue<String>();


    /**
     * Constructor for this data structure.
     * @param myGraph the graph we are searching within
     * @param startVertex the start vertex
     * @param endVertex the end vertex
     */
    public ListAllPaths(Graph myGraph, Integer startVertex, Integer endVertex) {
        // get all the paths after we initialize the structure
        // and store them in a queue
        getPaths(myGraph, startVertex, endVertex);
    }

    /**
     * Gets all the paths using a DEPTH FIRST SEARCH
     * @param myGraph the graph we are searching within
     * @param startVertex the starting vertex
     * @param endVertex the ending vertex
     */
    private Queue<String> getPaths(Graph myGraph, Integer startVertex, Integer endVertex) {

        // add node v to current path from s
        currentPath.push(startVertex);
        verticesOnPath.add(startVertex);

        if (startVertex.equals(endVertex)) {
            // get the current path and enqueue it
            String pathAsString = currentPath.toString();
            allPaths.enqueue(pathAsString);

        } else {
            // go through all the neighbors and get their paths too, do this recursively
            for (Integer w : myGraph.adj(startVertex)) {
                // get all the the paths between our vertices recursively
                if (!verticesOnPath.contains(w)) {
                    getPaths(myGraph, w, endVertex);
                }
            }
        }

        // remove last vertex from the path once we complete searching from that point
        currentPath.pop();
        // also clean up on the vertices set
        verticesOnPath.delete(startVertex);

        // return a queue with all the path we found
        return allPaths;
    }
}
