import java.util.ArrayList;
import java.util.List;

public class Path {
	public List<Integer> path;
	public List<Integer> investedPath;
	private int weight;
	public Path() {
		this.path = new ArrayList<>();
		this.weight = 0;
	}
	public void addSubPath(List<Integer> subPath) {
//		System.out.println("subPath: " + subPath);
//		add elements of subPaths to path
//		don't add the last element
//		graph end will be added in SolutionFinder
		for(int i=subPath.size()-1;i>=1;i--) {
			this.path.add(subPath.get(i));
		}
	}
	public void addWeight(int weight) {
		this.weight = this.weight + weight;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}