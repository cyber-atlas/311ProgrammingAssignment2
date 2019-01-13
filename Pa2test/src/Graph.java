//package GraphStuff;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//
//public class Graph {
//
//    private int numVertices;
//    private LinkedList<Edge>[] adjacencyList;
//
//
//    public Graph (int numVertices){
//        //pass the number of vertice in the Graph
//        this.numVertices = numVertices;
//        //Initializes the adjacency list array
//        adjacencyList = new LinkedList[numVertices];
//        //Initializes all of the linked lists in the adjacency list array
//        for(int i= 0; i < numVertices; i++){
//            adjacencyList[i] = new LinkedList<>();
//        }
//    }
//
//   public void addEgde(int source, int destination, int weight) {
//        Edge edge = new Edge(source, destination, weight);
//        adjacencyList[source].addFirst(edge); //for directed graph
//
//    }
//
//
//
//    //Using the first 2 numbers as strings
//
////    HashMap
//
//}
