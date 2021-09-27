import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    // List of all vertices
    public final List<Vertex> vertices = new ArrayList<>();

    // A data structure that maps vertex numbers to Vertex instances
    public Map<Integer, Vertex> vertexMap = new HashMap<>();

    // Start and end vertices given in the input file
    public int start, end;

    // Must-visit list defined in the input file
    public List<Integer> mustVisit = new ArrayList<>();

}
