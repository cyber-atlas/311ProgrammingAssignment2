//package GraphStuff;
//
//import java.io.FileNotFoundException;
//import java.net.URISyntaxException;
//
//public class Main {
//
//    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
//
//        //Initializes a new graph object
//        WGraph2 g = new WGraph2("test2.txt");
//
//        g.parseFile();
//
//        System.out.println("Checking the edgetable");
//        System.out.println(g.edgeTable);
//        //Prints the weight of each key in the edgeTable
//        System.out.println("Printing the edgeTable key & weight");
//        for(String key: g.edgeTable.keySet()){
//            System.out.println("key " + key + " " + "value " + g.edgeTable.get(key).getWeight());
//        }
//
//        System.out.println("Checking the graphRepresentation");
//        System.out.println(g.graphMap);
//
//        System.out.print(g.V2V(23, 55, 14, 10));
//
//
//
//    }
//}
