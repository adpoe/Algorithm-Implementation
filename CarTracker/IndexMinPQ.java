
/******************************************************************************
 *  Compilation:  javac IndexMinPQ.java
 *  Execution:    java IndexMinPQ
 *  Dependencies: StdOut.java
 *
 *  Minimum-oriented indexed PQ implementation using a binary heap.
 *
 ******************************************************************************/

import java.util.NoSuchElementException;

/**
 *  The <tt>IndexMinPQ</tt> class represents an indexed priority queue of generic keys.
 *  It supports the usual <em>insert</em> and <em>delete-the-minimum</em>
 *  operations, along with <em>delete</em> and <em>change-the-key</em>
 *  methods. In order to let the client refer to keys on the priority queue,
 *  an integer between 0 and maxN-1 is associated with each key&mdash;the client
 *  uses this integer to specify which key to delete or change.
 *  It also supports methods for peeking at the minimum key,
 *  testing if the priority queue is empty, and iterating through
 *  the keys.
 *  <p>
 *  This implementation uses a binary heap along with an array to associate
 *  keys with integers in the given range.
 *  The <em>insert</em>, <em>delete-the-minimum</em>, <em>delete</em>,
 *  <em>change-key</em>, <em>decrease-key</em>, and <em>increase-key</em>
 *  operations take logarithmic time.
 *  The <em>is-empty</em>, <em>size</em>, <em>min-index</em>, <em>min-key</em>, and <em>key-of</em>
 *  operations take constant time.
 *  Construction takes time proportional to the specified capacity.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 */
public class IndexMinPQ  {
    private int maxN;        // maximum number of elements on PQ
    private int N;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Car[] cars;      // keys[i] = priority of i

    /**
     * Initializes an empty indexed priority queue with indices between <tt>0</tt>
     * and <tt>maxN - 1</tt>.
     * @param  maxN the keys on this priority queue are index from <tt>0</tt>
     *         <tt>maxN - 1</tt>
     * @throws IllegalArgumentException if <tt>maxN</tt> &lt; <tt>0</tt>
     */
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        cars = new Car[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return <tt>true</tt> if this priority queue is empty;
     *         <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Is <tt>i</tt> an index on this priority queue?
     *
     * @param  i an index
     * @return <tt>true</tt> if <tt>i</tt> is an index on this priority queue;
     *         <tt>false</tt> otherwise
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     */
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
        return N;
    }

    /**
     * Associates key with index <tt>i</tt>.
     *
     * @param  i an index
     * @param  car the key to associate with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if there already is an item associated
     *         with index <tt>i</tt>
     */
    public void insert(int i, Car car) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        N++;
        qp[i] = N;
        pq[N] = i;
        cars[i] = car;
        swim(N);
    }

    /**
     * Returns an index associated with a minimum key.
     *
     * @return an index associated with a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int minIndex() {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    /**
     * Returns a minimum key.
     *
     * @return a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Car minKey() {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        return cars[pq[1]];
    }

    /**
     * Removes a minimum key and returns its associated index.
     * @return an index associated with a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int delMin() {
        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, N--);
        sink(1);
        assert min == pq[N+1];
        qp[min] = -1;        // delete
        cars[min] = null;    // to help with garbage collection
        pq[N+1] = -1;        // not needed
        return min;
    }

    /**
     * Returns the key associated with index <tt>i</tt>.
     *
     * @param  i the index of the key to return
     * @return the key associated with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public Car keyOf(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return cars[i];
    }

    /**
     * Change the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to change
     * @param  car change the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void changeKey(int i, Car car) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        cars[i] = car;
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * Change the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to change
     * @param  car change the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @deprecated Replaced by {@link #changeKey(int, Car)}.
     */
    public void change(int i, Car car) {
        changeKey(i, car);
    }

    /**
     * Decrease the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to decrease
     * @param  car decrease the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if key &ge; key associated with index <tt>i</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void decreaseKey(int i, Car car) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (cars[i].compareTo(car) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        cars[i] = car;
        swim(qp[i]);
    }

    /**
     * Increase the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to increase
     * @param  car increase the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if key &le; key associated with index <tt>i</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void increaseKey(int i, Car car) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (cars[i].compareTo(car) >= 0)
            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
        cars[i] = car;
        sink(qp[i]);
    }

    /**
     * Remove the key associated with index <tt>i</tt>.
     *
     * @param  i the index of the key to remove
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <t>i</tt>
     */
    public void delete(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, N--);
        swim(index);
        sink(index);
        cars[i] = null;
        qp[i] = -1;
    }


    /***************************************************************************
     * General helper functions.
     ***************************************************************************/
    private boolean greater(int i, int j) {
        return cars[pq[i]].compareTo(cars[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }


    /***************************************************************************
     * Heap helper functions.
     ***************************************************************************/
    public void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    public void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }




    /////////////////
    //// GETTERS ////
    /////////////////
    public int getN() {
        return N;
    }

    public int getMaxN() {
        return maxN;
    }

    public int[] getPq() {
        return pq;
    }

    public int[] getQp() {
        return qp;
    }

    public Car[] getCars() {
        return cars;
    }

    public void setN(int n) {
        N = n;
    }

    public void setMaxN(int maxN) {
        this.maxN = maxN;
    }

    public void setPq(int[] pq) {
        this.pq = pq;
    }

    public void setQp(int[] qp) {
        this.qp = qp;
    }

    public void setCars(Car[] cars) {
        this.cars = cars;
    }

}
