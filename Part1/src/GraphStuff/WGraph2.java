package GraphStuff;

/**
 * TODO Let's try this again without sipping dumbfuck juice
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class WGraph2 {

    //Instance variables

    //The name of the file
    public String fileName;


    //The file we are reading in
    private FileReader fileIn;

    //Instance variable to hold the number of verticies
    private int numVertices;
    //private int to hold the number of edges
    private int numEdges;


//    Pair<String, Integer> edge;

    private Hashtable<String, Integer> hash;



    //Making a Hashset to hold the Edges
    private HashSet<Edge> edgeSet;
    //Making a Hashmap of linked lists to hold the adjacency list of the graph
    protected HashMap<String, ArrayList<String>> graphMap;

    //Making a HashTable to hold the edges...
    protected Hashtable<String, Edge> edgeTable;


    /**
     * Constructor for WGraph
     * @param FName the filename of the file in the same directory
     */
    public WGraph2(String FName){
        //Saves the filename of the file
        fileName = FName;
    }


    public void parseFile() throws FileNotFoundException, URISyntaxException {
        //Initalize the hashmap and hashset
        edgeSet = new HashSet<Edge>();
        graphMap = new HashMap<String, ArrayList<String>>();
        File f  = new File(fileName);
        Scanner s = new Scanner(f);
        //Initalize the hashmap that holds the graph adjacency list
        graphMap = new HashMap<String, ArrayList<String>>();
        //Try to have a hashTable of edges, the key would be the edgename, the value would be the edge object
        edgeTable = new Hashtable<String, Edge>();

        //The first line is going to be the number of vertices in the graph
        numVertices = s.nextInt();
        System.out.println(numVertices);
        //The second is going to be the number of edges in the graph
        numEdges = s.nextInt();
        System.out.println(numEdges);

        //So now we know that we can get the input properly and that we ignore the new line character
        //A scanner should only have 5 ints on the line seperated by spaces
        while (s.hasNextLine()){
            //Make the source vertex
            int sourceX = s.nextInt();
            int sourceY = s.nextInt();
            //Make the destination verex
            int destX = s.nextInt();
            int destY = s.nextInt();
            //Save the weight of the edge
            int weight = s.nextInt();

            String sourceXYString  = String.valueOf(sourceX) + String.valueOf(sourceY);
            String destXYString = String.valueOf(destX) + String.valueOf(destY);

            System.out.println("source: " + sourceXYString + " dest " + destXYString + " weight " + weight);

            //Let's try this again adding it to a hashtable with the full edgename as the key, new edge as value
            edgeTable.put((sourceXYString+destXYString), new Edge(sourceXYString, destXYString, weight));

            //If this entry in the hashmap does not exist, add it and initialize the arraylist
            if (!(graphMap.containsKey(sourceXYString))){
                graphMap.put(sourceXYString, new ArrayList<String>());
            }

            //Gets the key of the sourceXY string and adds the destination string to the arraylist of children
            graphMap.get(sourceXYString).add(destXYString);
        }
    }

    //TODO
    //Given vertices u and v, ﬁnd the shortest path to from u to v.
    
    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){

        ArrayList<Integer> returnList = new ArrayList<Integer>();

        return returnList;
    }


}
