import java.util.*;
import java.util.stream.Collectors;

public class SolutionFinder {

    // Graph instance to be used
    public Graph graph;


    // The constructor
    public SolutionFinder(Graph g) {
        this.graph = g;
        findOptimalSolution();
    }

    /**
     * Prints the optimal solution
     */
    public void findOptimalSolution() {

    	
    	// remove start and end from the permutations
    	List<Integer> mustVisit = graph.mustVisit.subList(1, graph.mustVisit.size()-1);
        List<List<Integer>> permutations = getPermutations(mustVisit);
        
        // add start and end to beginnig and end of the permutations 
        for(List<Integer> per : permutations) {
        	per.add(0,graph.start);
        	per.add(graph.end);
        }
        
//        System.out.println("permutations: " + permutations);
       
        // optimal path
        Path optimalPath = new Path();
        boolean f = true;
        for (List<Integer> investedPath : permutations) {
        	//creates a new path for new path
        	Path path = new Path();
        	path.investedPath = investedPath;

        	for (int i = 0; i < investedPath.size() - 1; i++) {
            	// search path for the investedPath(i)
            	findPath(investedPath.get(i), investedPath.get(i + 1),path);
            }
        	// if first iteration optimal path equals to path
            if(f) {
            	optimalPath = path;
            	f = false;
            }
            
            // looks for the optimal Path
            if(path.getWeight() < optimalPath.getWeight()){
            	optimalPath = path;
            }
            
            // add the last destination to path, it couldnt be added in Path.java
            path.path.add(graph.end);
            
            //prints path
            System.out.println("Investigating: " + investedPath);
            System.out.println("Path found: " + path.path);
            System.out.println("With weight: " + Double.valueOf(path.getWeight()) + "\n");
        }
        //prints optimal 
        System.out.println("Optimal solution is: " + optimalPath.investedPath.toString());
        System.out.println("With optimal path: " +  optimalPath.path.toString());
        System.out.print("With optimal weight: " + Double.valueOf(optimalPath.getWeight()));

    }

    private void findPath(int sourceVertex, int destVertex, Path path) {
            int first = 0;
            
        	// <destination , weight>
            Map<Integer, Integer> totalWeights = new HashMap<Integer, Integer>();

            //path for navigation
            Map<Integer, Integer> paths = new HashMap<>();

            // holds the visited vertices
            ArrayList<Vertex> visited = new ArrayList<Vertex>();

            // all values inside of distances
            // should be max integer 
            // before the search begins
            for(int i=0; i<graph.vertices.size();i++) {
            	totalWeights.put(graph.vertices.get(i).number, Integer.MAX_VALUE);            	
            }

            // weight of sourceVertex should be 0
            // because we r starting from sourceVertex
            totalWeights.put(sourceVertex, 0);

            visited.add(graph.vertexMap.get(sourceVertex));

            while (visited.size()!=0) {

            	Vertex searching = visited.get(0);
            	visited.remove(0);
//                System.out.println("searching: " + searching.number + " distances get: " + totalWeights.get(searching.number)) ;

                int Weight;

                for (Edge edge : searching.adj) {
                	
                	//get the appropriate vertex of edge
                	Vertex vertex = graph.vertexMap.get(edge.destination);
                    
                    //if we add an integer value to Integer.maxValue, 
//                  Integer.maxValue behaves like zero, due to binary representation 0111 + 0001 -> 1000
                    Weight = totalWeights.get(searching.number) + edge.weight;
//                  if weight is not greater than appropriate index of totalWeight
//                  this weight should be added to totalWeights
                    if (Weight <= totalWeights.get(edge.destination)) {
                    	totalWeights.put(edge.destination, Weight);
                        paths.put(edge.destination, edge.source);
                        // add destination vertex to visited
                        // add vertex to visited, so new iteration of for loop will be this vertex
                        visited.add(vertex);
                    }
                }
            }

            List<Integer> shortestPath = new ArrayList<>();

            //add destinations to shortestPath and Store it in path object

            shortestPath.add(graph.vertexMap.get(destVertex).number);
            
            Vertex destination = graph.vertexMap.get(destVertex);
            while (destination != graph.vertexMap.get(sourceVertex)) {
                shortestPath.add(paths.get(destination.number));
                destination = graph.vertexMap.get(paths.get(destination.number));
            }
        // send weight and path to Path object
        path.addWeight(totalWeights.get(destVertex));
	    path.addSubPath(shortestPath);
    }

    /**
     *
     * @param mustVisitList Will be used to generate permutations
     * @return All permutations of the must-visit list, sorted (learn how to sort a list of lists)
     */
    public List<List<Integer>> getPermutations(List<Integer> mustVisitList) {
       	List<List<Integer>> output = new ArrayList<List<Integer>>();
    	if(mustVisitList.isEmpty()) {
    		output.add(new ArrayList<Integer>());
    		return output;
    	}
    	List<Integer> list = new ArrayList<Integer>(mustVisitList);
    	Integer head = list.get(0);
    	List<Integer> rest = list.subList(1, list.size());
    	for(List<Integer> permutations : getPermutations(rest)) {
    		List<List<Integer>> subLists = new ArrayList<List<Integer>>();
    		for(int i=0; i<=permutations.size(); i++) {
    			List<Integer> subList = new ArrayList<Integer>();
    			subList.addAll(permutations);
    			subList.add(i, head);
    			subLists.add(subList);
    		}
    		output.addAll(subLists);
    	}

        // sorts list of lists
    	output = output.stream().sorted((o1,o2) -> {
    		for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
    			int c = o1.get(i).compareTo(o2.get(i));
    			if (c != 0) {
    				return c;
    			}
    		}
    		return Integer.compare(o1.size(), o2.size());
    	}).collect(Collectors.toList());
    	
        return output;
    }
}
