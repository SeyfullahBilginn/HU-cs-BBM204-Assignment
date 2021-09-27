// Just a directed and weighted edge
public class Edge {
    public int weight = 0;
    public int source, destination;

    public Edge(int src, int dst, int weight) {
        this.source = src;
        this.destination = dst;
        this.weight = weight;
    }
}