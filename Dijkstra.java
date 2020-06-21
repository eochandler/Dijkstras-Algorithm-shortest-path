package graph;

/** Class Dijkstra. Implementation of Dijkstra's algorithm for finding the shortest path
 * between the source vertex and other vertices in the graph.
 *  Fill in code. You may add additional helper methods or classes.
 */

import java.util.*;
import java.awt.Point;

//Do this after all of edge and graph are completed


public class Dijkstra {
    private Graph graph; // stores the graph of CityNode-s and edges connecting them
    private List<Integer> shortestPath = null; // nodes that are part of the shortest path

    /** Constructor
     *
     * @param filename name of the file that contains info about nodes and edges
     * @param graph graph
     */
    public Dijkstra(String filename, Graph graph) {
        this.graph = graph;
        graph.loadGraph(filename);
    }

    //not exceptionally complicated just compute shortest path:


    /**
     * Returns the shortest path between the origin vertex and the destination vertex.
     * The result is stored in shortestPathEdges.
     * This function is called from GUIApp, when the user clicks on two cities.
     * @param origin source node
     * @param destination destination node
     * @return the ArrayList of nodeIds (of nodes on the shortest path)
     */
    public List<Integer> computeShortestPath(CityNode origin, CityNode destination) {

        // FILL IN CODE

        // Create and initialize Dijkstra's table
        int[][] dijkstrasTable = new int[graph.numNodes()][2];
        //fill in false
        //orgin cost will be 0, other will be -1
        for(int i = 0; i < graph.numNodes(); i++){
            dijkstrasTable[i][0] = Integer.MAX_VALUE;
            dijkstrasTable[i][1] = -1;
        }
        boolean[] known = new boolean[graph.numNodes()];

        //add a seperate array called visited
        //function that gets number of nodes
        // Create and initialize a Priority Queue
        int OriginId = graph.getId(origin);
        dijkstrasTable[OriginId][0] = 0;
        known[OriginId] = true;

        //make count known verticies = num verticies becomes true
        //bigger loop that contains both of these operations.

        int verticesCount = 0;

        Edge headOfLinkedList = graph.getHeadOfLinkedList(origin);
        Edge current = headOfLinkedList;

        //updates all neighbors of the original node we choose

        int firstNodeID = current.getNeighbor();
        int firstNewCost = current.getCost();
        while(current != null){
            dijkstrasTable[firstNodeID][0] = firstNewCost;
            dijkstrasTable[firstNodeID][1] = 0;
            current = current.getNext();
        }

        //now set the value of that node equal to true
        int smallestInitialNode = 0;
        int smallestInitialValue = Integer.MAX_VALUE;
        for(int y = 0; y < known.length; y ++){
            if((known[y] == false) && (dijkstrasTable[y][0] < smallestInitialNode)){
                smallestInitialNode = y;
                smallestInitialValue = dijkstrasTable[y][0];

            }
        }

        known[smallestInitialNode] = true;

        headOfLinkedList = graph.getHeadOfLinkedListForID(smallestInitialNode);
        current = headOfLinkedList;

        verticesCount = 2;

        //we are now looking at current which is centered on node 1

        while(verticesCount != graph.numNodes()) {

            //lLooking through the linkedList of a specific node

            while (current != null) {


                //smallest current node information
//                int smallestKnownNode= current.getNeighbor();
//                int SmallestKnownCost = current.getCost();

                //take the cost of the smallest node plus the cost of the edge and compare with what you currently store in the table.
                //add the cost of the smallest node, and compare with cost of node ID
                //cost of smallest node plus cost of edge
                int smallestNode = current.getNeighbor();
                int smallestDistance = current.getCost();


                //updating dijkstras table here
                for(int z = 1; z < known.length; z++){
                    if((known[z] == false) && (dijkstrasTable[z][0]< smallestDistance)){
                        smallestDistance = dijkstrasTable[z][0];
                        dijkstrasTable[z][1] = smallestInitialNode;
                        smallestNode = 0;

                    }
                }


                if(dijkstrasTable[smallestInitialNode][0] + smallestDistance <dijkstrasTable[smallestNode][0]){
                    dijkstrasTable[smallestNode][0] = dijkstrasTable[smallestInitialNode][0] + smallestDistance;
                    dijkstrasTable[smallestNode][1] = smallestInitialNode;
                }

            }

            int smallestNode = 0;
            int smallestDistance = 10000;

            for (int j = 0; j < dijkstrasTable.length; j++) {
                if (dijkstrasTable[j][0] < smallestDistance) {
                    smallestDistance = dijkstrasTable[j][0];
                    smallestNode = j;
                }
            }

            known[smallestNode] = true;
            current = current.getNext();
            verticesCount++;
            current = graph.getHeadOfLinkedListForID(smallestNode);


        }


        // Compute the nodes on the shortest path by "backtracking" using the table

        // The result should be in an instance variable called "shortestPath" and
        // should also be returned by the method
        return null; // don't forget to change it
    }

    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
    public Point[][] getPath() {
        if (shortestPath == null)
            return null;
        return graph.getPath(shortestPath); // delegating this task to the Graph class
    }

    /** Set the shortestPath to null.
     *  Called when the user presses Reset button.
     */
    public void resetPath() {
        shortestPath = null;
    }

}