package graph;
//do this graph second


/** A class that represents a graph where nodes are cities (of type CityNode).
 * The cost of each edge connecting two cities is the distance between the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a PriorityQueue from scratch to get full credit.
 */
import org.w3c.dom.Node;

import java.util.*;
import java.io.*;
import java.awt.Point;

public class Graph {
    public final int EPS_DIST = 5;

    //create the edge in load graph
    //call add edge from load graph
    //
    private int numNodes;     // total number of nodes
    private int numEdges; // total number of edges
    private CityNode[] nodes; // array of nodes of the graph
    private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
    private HashMap<String, Integer> labelsToIndices; // a HashMap that maps each city to the corresponding node id

    /**
     * Read graph info from the given file, and create nodes and edges of
     * the graph.
     *
     * @param filename name of the file that has nodes and edges
     */
    //relies on add edge
    public void loadGraph(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            //make an adjacencyList
            line = br.readLine();

            int nodeID = 0;
            int numNodesFromFile = Integer.parseInt(line);
            nodes = new CityNode[numNodesFromFile];

            //ley city to value nodeID
            labelsToIndices = new HashMap<String, Integer>();
            adjacencyList = new Edge[numNodesFromFile*2];

            ///third line of nodes with coordinates starts

            while (nodeID < numNodesFromFile) {
                line = br.readLine();
                String[] cityAndCoordinates = line.split(" ");
                CityNode cNode = new CityNode(cityAndCoordinates[0], Float.parseFloat(cityAndCoordinates[1]), Float.parseFloat(cityAndCoordinates[2]));


                //adding a city node to the nodes array

                //adding a city key to its NodeId Value
                labelsToIndices.put(cityAndCoordinates[0], nodeID);
                addNode(cNode);
                numNodes++;
                nodeID++;
            }

            line = br.readLine();
            line = br.readLine();

            ///Edge processing starts
            while(line != null){

                String[] edgeInfo = line.split(" ");

                //edgeInfo[0] first city
                //edgeInfo[1] is destination city
                //edgeInfo[2] cost between cities

                //san francisco los angeles 500

                //outgoing edge to las angeles added to the San Francisco list



                //san francisco edge
                //add to the adjacency of los angeles
                Edge newEdge1 = new Edge(labelsToIndices.get(edgeInfo[0]), Integer.parseInt(edgeInfo[2]));
                Edge newEdge2 = new Edge(labelsToIndices.get(edgeInfo[1]), Integer.parseInt(edgeInfo[2]));

                //add La edge to the adjacency list

                //go to la adjacency list



                //edge neighbor los angeles
                // cost set as shared cost


                addEdge(labelsToIndices.get(edgeInfo[0]),newEdge2);
                addEdge(labelsToIndices.get(edgeInfo[1]),newEdge1);

                line = br.readLine();

            }
            System.out.println(adjacencyList.length + " is the length of the adjacency list");

        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    public Edge getHeadOfLinkedListForID(int nodeId){

        return adjacencyList[nodeId];
    }

    public Edge getHeadOfLinkedList(CityNode cNode){
        String city = cNode.getCity();

        int nodeId = labelsToIndices.get(city);

        return adjacencyList[nodeId];

    }

    /**
     * Add a node to the array of nodes.
     * Increment numNodes variable.
     * Called from loadGraph.
     *
     * @param node a CityNode to add to the graph
     */
    public void addNode(CityNode node) {
        int nodeID = labelsToIndices.get(node.getCity());
        nodes[nodeID] = node;
    }

    /**
     * Return the number of nodes in the graph
     * @return number of nodes
     */
    public int numNodes() {
        return numNodes;
    }

    /**
     * Adds the edge to the linked list for the given nodeId
     * Called from loadGraph.
     *
     * @param nodeId id of the node
     * @param edge edge to add
     */
    //do this method first
    public void addEdge(int nodeId, Edge edge) {
        Edge head = adjacencyList[nodeId];//points to a linkedList
        if(head == null) {
    //        edge.setNext(head);
            adjacencyList[nodeId] = edge;
            //increment number of edges
            numEdges++;
        }else{
            edge.setNext(head);
           adjacencyList[nodeId] = edge;
           numEdges++;
        }
    }

    /**
     * Returns an integer id of the given city node
     * @param city node of the graph
     * @return its integer id
     */
    public int getId(CityNode city) {
        //private HashMap<String, Integer> labelsToIndices; // a HashMap that maps each city to the corresponding node id
        int ID = labelsToIndices.get(city.getCity());
        return ID; // Don't forget to change this
    }

    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     * This info can be obtained from the adjacency list
     */
    public Point[][] getEdges() {
        int i = 0;
        Point[][] edges2D = new Point[numEdges][2];
        // FILL IN CODE
        //go over the adjacency list every edge
        //for every edge put the origin
        int edgeCount = 0;
        for(int j = 0; j < adjacencyList.length; j++){
            Edge current = adjacencyList[j];
            while(current != null){
                //goes from j to this destination
                int destination = current.getNeighbor();
                Point point1 = nodes[j].getLocation();
                Point point2 = nodes[destination].getLocation();
                //put in edges 2d
                edges2D[edgeCount][0] = point1;
                edges2D[edgeCount][1] = point2;
                edgeCount++;
                current = current.getNext();
            }
        }
        return edges2D;
    }

    /**
     * Get the nodes of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getNodes() {
        if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
        Point[] pnodes = new Point[this.nodes.length];

        for(int i = 0; i < this.nodes.length; i ++){
            pnodes[i] = this.nodes[i].getLocation();
        }

        return pnodes;
    }

    /**
     * Used in GUIApp to display the names of the airports.
     * @return the list that contains the names of cities (that correspond
     * to the nodes of the graph)
     */
    public String[] getCities() {
        if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
        String[] labels = new String[nodes.length];
        for( int i = 0; i < this.nodes.length; i ++){
            labels[i] = this.nodes[i].getCity();
        }


        return labels;

    }

    /** Take a list of node ids on the path and return an array where each
     * element contains two points (an edge between two consecutive nodes)
     * @param pathOfNodes A list of node ids on the path
     * @return array where each element is an array of 2 points
     */
    public Point[][] getPath(List<Integer> pathOfNodes) {
        int i = 0;
        Point[][] edges2D = new Point[pathOfNodes.size()-1][2];
        // Each "edge" is an array of size two (one Point is origin, one Point is destination)
        // FILL IN CODE
        //private int numNodes;     // total number of nodes
        //private int numEdges; // total number of edges
        //private CityNode[] nodes; // array of nodes of the graph
        //private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
        //private HashMap<String, Integer> labelsToIndices; // a HashMap that maps each city to the corresponding node id

        for(int j = 0; j < pathOfNodes.size(); j++){
            System.out.println(pathOfNodes.get(i) + "*&^&*&^*&*&^*&^*&^*&^");
        }

        return edges2D;
    }

    /**
     * Return the CityNode for the given nodeId
     * @param nodeId id of the node
     * @return CityNode
     */
    public CityNode getNode(int nodeId) {
        return nodes[nodeId];
    }

    /**
     * Take the location of the mouse click as a parameter, and return the node
     * of the graph at this location. Needed in GUIApp class. No need to modify.
     * @param loc the location of the mouse click
     * @return reference to the corresponding CityNode
     */
    public CityNode getNode(Point loc) {
        if (nodes == null) {
            System.out.println("No node at this location. ");
            return null;
        }
        for (CityNode v : nodes) {
            Point p = v.getLocation();
            if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
                return v;
        }
        return null;

    }

}