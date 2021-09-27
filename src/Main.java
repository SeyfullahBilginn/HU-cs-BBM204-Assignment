import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    /**
     *
     * @param args The first argument is the name of the input file
     * @throws FileNotFoundException when the given file does not exist
     * @throws IllegalArgumentException when no arguments are specified
     */
	public static void main(String[] args) throws FileNotFoundException {
    	
        // Call Main.readFile and SolutionFinder.findOptimalSolution here
    	String filename = args[0];
//    	String filename = "C:\\Users\\SEYFULLAH\\eclipse-workspace\\resit2\\src\\example.txt";
        Graph graph = readFile(filename);
        

        
        SolutionFinder solutionFinder = new SolutionFinder(graph);

	}

    
    /**
     * Use groups! Check the instructions for IO
     * @return A pattern that should be used to parse source and destination vertices
     */
    public static Pattern getSourceDestPattern() {
        Pattern pattern = Pattern.compile("\\d+");
    	return pattern;
    }

    /**
     * Use groups! Check the instructions for IO
     * @return A pattern that should be used to parse information about graph construction
     */
    public static Pattern getGraphConstructionPattern() {
        Pattern pattern = Pattern.compile("-");
        return pattern;
    }

    /**
     * Check the instructions for details
     * @param filepath Path of the input file
     * @return A new filled graph with necessary properties
     * @throws FileNotFoundException when the given file does not exist
     */
    public static Graph readFile(String filepath) throws FileNotFoundException {
    	Graph graph = new Graph(); // created new graph object

    	File myObj = new File(filepath);
        Scanner myReader = new Scanner(myObj);
        int counter = 0;
        while (myReader.hasNextLine()) {
            List<Edge> adj = new ArrayList<>();
        	int index = 0;
        	int src = 0;
        	int dst = 0;
        	int weight = 0;
        	String data = myReader.nextLine();
        	data = data.replace(" ","");
        	Pattern patternDigit = getSourceDestPattern(); // detects all integers
        	Matcher matcherDigit = patternDigit.matcher(data);      	
        	
        	Pattern patternMustVisit = getGraphConstructionPattern(); //detects must-visit 
        	Matcher matcherMustVisit = patternMustVisit.matcher(data);  
        	Boolean mv = matcherMustVisit.find();
        	
        	
        	while (matcherDigit.find()) {
        		

        		int num = Integer.parseInt(data.substring(matcherDigit.start(),matcherDigit.end()));
        		if(index == 0 & mv) {
        			graph.mustVisit.add(num);	// if must-visit true adds first integer to mustVisit array
        		}
        		
        		if(counter == 0) { // goes if first line of example
        			if(index==0) graph.start = num;
        			else {
        				graph.end = num;
        			}
        		}else { // goes except first line of example
        			if(index == 0) { // takes first integer(source)
        				src = num;
        			}
        			if(index%2 == 1) { //takes the edge destination
        				dst = num;
        			}
        			if((index)%2 == 0 & index != 0) { // takes the weights
        				weight = num;
        				Edge edge = new Edge(src,dst,weight);
        				adj.add(edge);
        			}
//        			vertices.add(num);
        		}
        		index++;
        	}       	
        	counter++;
        	Vertex vertex = new Vertex(); // create new vertex
        	vertex.number = src; // assign source
        	vertex.adj = adj; // assign adj lists
        	graph.vertices.add(vertex);
        	graph.vertexMap.put(src, vertex);
        } 
        myReader.close();
            
        
        //graphs source and destinations is added to mustVisit array
        graph.mustVisit.add(0,graph.start);
        graph.mustVisit.add(graph.mustVisit.size(),graph.end);
        
        return graph;
    }
    
}
