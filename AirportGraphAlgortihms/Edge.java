
/******************************************************************************
 *  Compilation:  javac Edge.java
 *  Execution:    java Edge
 *  Dependencies: StdOut.java
 *
 *  Immutable weighted edge.
 *
 ******************************************************************************/

/**
 *  The <tt>Edge</tt> class represents a weighted edge in an
 *  {@link EdgeWeightedGraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the edge and
 *  the weight. The natural order for this data type is by
 *  ascending order of weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double miles;
    private final double cost;
    public final double weight;
    public boolean compareMiles;
    private String sourceCityName;
    private String destCityName;


    /**
     * Initializes an edge between vertices <tt>v</tt> and <tt>w</tt> of
     * the given <tt>weight</tt>.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  miles the weight of this edge
     * @throws IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
     *         is a negative integer
     * @throws IllegalArgumentException if <tt>weight</tt> is <tt>NaN</tt>
     */
    public Edge(int v, int w, double miles, double cost, String srcCityName, String dstCityName) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
        if (Double.isNaN(cost)) throw new IllegalArgumentException("Cost is NaN");
        if (Double.isNaN(miles)) throw new IllegalArgumentException("Miles is NaN");
        this.v = v;
        this.w = w;
        this.miles = miles;
        this.cost = cost;
        this.compareMiles = true;
        this.sourceCityName = srcCityName;
        this.destCityName = dstCityName;
        this.weight = weight();
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double getCost() {
        return this.cost;
    }

    public double getMiles() {
        return this.miles;
    }

    public String getSourceCityName() {
        return this.sourceCityName;
    }

    public String getDstCityName() {
        return this.destCityName;
    }

    public int getV() {
        return v;
    }

    public int getW() {
        return w;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * Compares two edges by weight.
     * Note that <tt>compareTo()</tt> is not consistent with <tt>equals()</tt>,
     * which uses the reference equality implementation inherited from <tt>Object</tt>.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge that) {
        if (this.compareMiles) {
            return compareToMiles(that);
        } else {
            return compareToCost(that);
        }
    }

    public int compareToCost(Edge that) {
        if      (this.getCost() < that.getCost()) return -1;
        else if (this.getCost() > that.getCost()) return +1;
        else                                      return  0;
    }

    public int compareToMiles(Edge that) {
        if      (this.getMiles() < that.getMiles()) return -1;
        else if (this.getMiles() > that.getMiles()) return +1;
        else                                        return  0;
    }

    public double weight() {
        if (this.compareMiles) return miles;
        else return cost;
    }

    public void setCompareMiles(boolean compareMiles) {
        this.compareMiles = compareMiles;
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("[FLIGHT: %s-%s Miles:%.5f Cost:%.5f] ", sourceCityName, destCityName, miles, cost);
    }
}
