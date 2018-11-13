package GraphStuff;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class JunitTest {

    WGraph2 graph;
    @BeforeAll
    public static void init() {
        /*
        WGraph2 graph = new WGraph2("test2.txt");

        try {
            graph.parseFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        */
    }

   @Test
   public void testGraphStorage(){
       WGraph2 graph = new WGraph2("test2.txt");
       try {
          graph.parseFile();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (URISyntaxException e) {
           e.printStackTrace();
       }

       Assertions.assertArrayEquals( new String[]{"2355","1111"},graph.graphMap.keySet().toArray(),
               "ensures the keys are correct");
       System.out.println("2355 "+"1111 " + graph.graphMap.keySet().toString()+
               " ensures the keys are correct");

   }

   @Test
   public void testEdgeTable(){
       WGraph2 graph = new WGraph2("test2.txt");
       try {
          graph.parseFile();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (URISyntaxException e) {
           e.printStackTrace();
       }

        System.out.println("2355 "+"1111 " + graph.edgeTable.keySet().toString()+
               " ensures the edges are correct");
       Assertions.assertArrayEquals( new String[]{"23555533","11111111"},graph.edgeTable.keySet().toArray());
//       System.out.println("2355 "+"1111 " + graph.edgeTable.keySet().toString()+
//               " ensures the edges are correct");

   }


}
