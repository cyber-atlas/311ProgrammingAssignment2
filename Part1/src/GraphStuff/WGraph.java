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

//public class WGraph2 {
    public class WGraph {

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


    //Hashset to hold whether or not we visited a node
    protected HashSet<String> visitedHashSet;
    //Hashmap to hold distances
    protected HashMap<String, Integer> distanceMap;

    protected HashMap<String, String> previousMap;

    protected ArrayList<String> vertexList = new ArrayList<>();

    /**
     * Constructor for WGraph
     * @param FName the filename of the file in the same directory
     */
//    public WGraph2(String FName){
    public WGraph(String FName){
        //Saves the filename of the file
        fileName = FName;
        parseFile();
    }


    public void parseFile() {
        //Initalize the hashmap and hashset
        edgeSet = new HashSet<Edge>();
        graphMap = new HashMap<String, ArrayList<String>>();
        File f  = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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


            String sourceXYString  = String.valueOf(sourceX) + ',' + String.valueOf(sourceY);
            String destXYString = String.valueOf(destX) + ',' + String.valueOf(destY);

            vertexList.add(sourceXYString);
            vertexList.add(destXYString);

            System.out.println("source: " + sourceXYString + " dest " + destXYString + " weight " + weight);

            //Let's try this again adding it to a hashtable with the full edgename as the key, new edge as value
            edgeTable.put((sourceXYString+","+destXYString), new Edge(sourceXYString, destXYString, weight));

            //If this entry in the hashmap does not exist, add it and initialize the arraylist
            if (!(graphMap.containsKey(sourceXYString))){
                graphMap.put(sourceXYString, new ArrayList<String>());
            }

            //Gets the key of the sourceXY string and adds the destination string to the arraylist of children
            graphMap.get(sourceXYString).add(destXYString);
        }
    }

    //TODO implement V2V
    //TODO look into whether or not we need to mark as visited or not
    /** Given vertices u and v, ﬁnd the shortest path to from u to v.
     pre: ux, uy, vx, vy are valid coordinates of vertices u and v
        in the graph
     post: return arraylist contains even number of integers,
        for any even i,
        i-th and i+1-th integers in the array represent
        the x-coordinate and y-coordinate of the i/2-th vertex
        in the returned path (path is an ordered sequence of vertices) */

    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){

        String sourcenode = String.valueOf(ux) + "," + String.valueOf(uy);
        String destnode = String.valueOf(vx) + "," + String.valueOf(vy);

        ArrayList<Integer> returnList = new ArrayList<Integer>();

       visitedHashSet = new HashSet<>();
        //Hashmap to hold distances
       distanceMap = new HashMap<>();
       previousMap = new HashMap<>();

        //Make a new priority queue
        PriorityQueue priorityQueue = new PriorityQueue(comparator);

        //loops through all the verteces and adds them to the priority queue
        for(String v:vertexList) {
            priorityQueue.add(v);
        }

        //Remove source, set it's distance to 0 then read bc PQ only updates on insertion/deletion
        priorityQueue.remove(sourcenode);
        distanceMap.put(sourcenode, 0);
        priorityQueue.add(sourcenode);

        while(!(priorityQueue.isEmpty())){
            //pops the lowest vertex off the priority queue
           String tempNode = (String)priorityQueue.poll();

           System.out.println( "tempNode" + tempNode);

           System.out.println("tempnode " + tempNode + " dest " + destnode );

           //TODO check if the node we popped is the node we need
            if (tempNode.equals(destnode)){

                //TODO invert the prev list, and convert them to ints
//                return  returnList;
                return getParent(tempNode);
            }

           //If tempNode does not have a child, continue the loop
           if ((graphMap.get(tempNode)) == null){
               continue;
           }

           //For each neighbor of ourNode
           for(String child: graphMap.get(tempNode)){
               //The weight of the edge
              int distToAdd = edgeTable.get(tempNode+ "," + child).getWeight();
              //TODO if not in our distance map, then we set this to 0 I am assuming
               //The previous weight of the node
               int previousWeight = (distanceMap.get(tempNode) == null)? 0 : distanceMap.get(tempNode);
               int newDist = distToAdd + previousWeight;
               //Set the previous child node value to max int if it is not in the distanceMap or set it to prev value
               int prevChildVal = (distanceMap.get(child) == null)? Integer.MAX_VALUE : distanceMap.get(child);
               //If the new distance is less than the old update the child's weight and the previous map
               if(newDist < prevChildVal) {
                   distanceMap.put(child, (distToAdd + previousWeight));
                   previousMap.put(child,tempNode);
               }
           }
        }

        //TODO return a reverse version of previous.
        //reverse traverse prev, and return vertex in order
        /*
        while(not null){
            go through previous, and for every previous, add that to the array and go through it
        } */
        return returnList;
    }


    //TODO V2S instead of checking if the chld node is the node we need, check if it is int the set of nodes that we need, if it's in the hashset



    Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String node1, String node2) {

            //If the value is null in the distancemap, then say that it is the biggest int since it's not been traversed
            int n1 = (distanceMap.get(node1) == null) ? Integer.MAX_VALUE : distanceMap.get(node1);

            int n2 = (distanceMap.get(node2) == null) ? Integer.MAX_VALUE : distanceMap.get(node2);

            //If the nodes are equal it will be 0
            //If n1 - n2 is negative then n2 is bigger else n1
            return n1 - n2;
        }
    };

    //TODO optimize this and finish this
    private List<String> reverse(String start) {
        Stack<String> s = new Stack<String>();
        String cur = start;
        while(cur != null) {
            s.push(cur);
            cur = previousMap.get(cur);
        }
        List<String> s2 = new ArrayList<String>();
        while(!s.empty()) {
            s2.add(s.pop());
        }
        return s2;
    }

    private ArrayList<Integer> getParent(String endNode){
        ArrayList<Integer> returnList = new ArrayList<>();

//        String nodeBeforeCur = previousMap.get(endNode);
        String nodeBeforeCur = (endNode);


        //TODO find a way to deal with it becoming an int. Ex its 1122 rn instead of 11 22
        //While the node before actually has a parent, null after it grabs the source which has no parent
//        while(nodeBeforeCur != null){
            do{
            //Grabs the int value of the previous node

            String[] tmpList = nodeBeforeCur.split(",");

//            for(String s : tmpList){
//                returnList.add(Integer.parseInt(s));
//            }

            //Since they get rerversed later, adding them in reverse order
            for(int i = tmpList.length-1; i>=0; i--){
                String s = tmpList[i];
                System.out.println(s);
                returnList.add(Integer.parseInt(s));
            }

//            returnList.add(Integer.valueOf(nodeBeforeCur.split(",")));
           //Grabs the next parent node
            nodeBeforeCur = previousMap.get(nodeBeforeCur);
        } while (nodeBeforeCur != null);

        //Reverses the list before it is returns
        Collections.reverse(returnList);
        return returnList;
        }






    }


