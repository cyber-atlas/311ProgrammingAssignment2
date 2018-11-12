package GraphStuff;

public class Edge {
    //TODO make it hold coordinates instead if source and dest
    //TODO maybe turn them into  a vertice class
    private int source;
    private int dest;
    private int weight;

    //Save the coordinates as a string
    private String sourceNode;
    private String destNode;

    public Edge(int source, int dest, int weight){
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }


    //TODO Let's try this again without being stoopid
    public Edge(String sourceNode, String destNode, int weight){
        this.sourceNode = sourceNode;
        this.destNode = destNode;
        weight = weight;

    }
}
