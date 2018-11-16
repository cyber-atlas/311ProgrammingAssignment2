//package GraphStuff;
//
//import java.io.*;
//import java.lang.reflect.Array;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Scanner;
//
//public class WGraph {
//
//    //Instance variables
//
//    //The name of the file
//    public String fileName;
//
//
//    //The file we are reading in
//    private FileReader fileIn;
//
//    //Instance variable to hold the number of verticies
//    private int numVertices;
//    //private int to hold the number of edges
//    private int numEdges;
//
//
//    //Making a Hashset to hold the Edges
//    HashSet<Edge> edgeSet;
//    //Making a Hashmap of linked lists to hold the adjacency list of the graph
//    HashMap<String, ArrayList<String>> graphMap;
//
//
//    /**
//     * Constructor for WGraph
//     * @param FName the filename of the file in the same directory
//     */
//    public WGraph(String FName){
//        //Saves the filename of the file
//        fileName = FName;
//    }
//
//
//    private void parseFile() throws FileNotFoundException, URISyntaxException {
////        fileIn  = new FileReader(fileName);
//         //Initalize the hashmap and hashset
//        edgeSet = new HashSet<Edge>();
//        graphMap = new HashMap<String, ArrayList<String>>();
//        URL path = ClassLoader.getSystemResource(fileName);
//        File f  = new File(fileName);
//        Scanner s = new Scanner(f);
//        //Initalize the hashmap and hashset
//        edgeSet = new HashSet<Edge>();
//        graphMap = new HashMap<String, ArrayList<String>>();
//        //The first line is going to be the number of vertices in the graph
//        numVertices = s.nextInt();
//        System.out.println(numVertices);
//        //The second is going to be the number of edges in the graph
//        numEdges = s.nextInt();
//        System.out.println(numEdges);
//        System.out.println(String.valueOf(numVertices)+String.valueOf(numEdges));
//
//        //So now we know that we can get the input properly and that we ignore the new line character
//
//        //Getting the source
//
//        //A scanner should only have 5 ints on the line seperated by spaces
//        while (s.hasNextLine()){
//            //Make the source vertex
//            int sourceX = s.nextInt();
//            int sourceY = s.nextInt();
//            //Make the destination verex
//            int destX = s.nextInt();
//            int destY = s.nextInt();
//            //Save the weight of the edge
//            int weight = s.nextInt();
//
//            String sourceXYString  = String.valueOf(sourceX) + String.valueOf(sourceY);
//            String destXYString = String.valueOf(destX) + String.valueOf(destY);
//
//            //TODO store the edge
//            Edge tempEdge = new Edge(sourceXYString, destXYString, weight);
//
//            //Adds the temp edge to the hashset
//            edgeSet.add(tempEdge);
//
//            //TODO create the hashmap of adjacies
//            //TODO figure out another hashmap contstructor thing
////            graphMap(sourceXYString, so,
////                    (String.valueOf(sourceX) + String.valueOf(sourceY)));
//
//
//
//        }
//
//        //TODO get the next line
//        //TODO Parse the ints from the string/line
//        //
//    }
//
//    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
//
//        System.out.println("Hello World!");
//        WGraph g = new WGraph("test2.txt");
////        WGraph g = new WGraph("C:\\Users\\ruski\\Documents\\ProgrammingAssignment2\\Part1\\src\\GraphStuff\\test2.txt");
////        g.parseFile();
//
//    }
//
//
//}

