public class Edge {


    public int weight;
    //Save the coordinates as a string
    private String sourceNode;
    private String destNode;



    public Edge(String sourceNode, String destNode, int weight){
        this.sourceNode = sourceNode;
        this.destNode = destNode;
        this.weight = weight;

    }

    public String getSourceNode() {
        return sourceNode;
    }

    public String getDestNode() {
        return destNode;
    }

    public int getWeight() {
        return weight;
    }
}
