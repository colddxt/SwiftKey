import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

class Graph{
    int V;
    Map<Integer, List<Integer> > adj; // Adjacency list
	
	
    Graph(int v){
	V = v;
	adj = new HashMap<Integer, List<Integer>>();
    }
		
    void addEdge(int u, int v){
	if(!adj.containsKey(u)){
	    adj.put(u, new ArrayList<Integer>());
	}
	if(!adj.containsKey(v)){
	    adj.put(v, new ArrayList<Integer>());
	}
	adj.get(u).add(v);
	adj.get(v).add(u);
    }

    boolean hasEdge(int u, int v){
	return adj.get(u).contains(v);
    }
	    
	
    List<List<Integer>> getAllPaths(int u, int v){
	List<List<Integer>> result = new ArrayList<List<Integer>>();
	if(u == v){
	    List<Integer> temp = new ArrayList<Integer>();
	    temp.add(u);
	    result.add(temp);
	    return result;
	}
	boolean[] visited = new boolean[V];
	Deque<Integer> path = new ArrayDeque<Integer>();
	getAllPathsDFS(u, v, visited, path, result);
	return result;
    }
	
	
    void getAllPathsDFS(int u, int v, boolean[] visited, Deque<Integer> path, List<List<Integer>> result){
	visited[u] = true; // Mark visited
	path.add(u); // Add to the end
	if(u == v){
	    result.add(new ArrayList<Integer>(path));
	}
	else{
	    if(adj.containsKey(u)){
		for(Integer i : adj.get(u)){
		    if(!visited[i]){
			getAllPathsDFS(i, v, visited, path, result);
		    }
		}
	    }
	}
	path.removeLast();
	visited[u] = false;
    }
	
    public List<List<Integer>> getMinPath(List<List<Integer>> array, int range) {
	int min = Integer.MAX_VALUE;
	for (List<Integer> list: array
	     ) {
	    if(list.size() <= min) {
		min = list.size();
	    }
	}

	Iterator<List<Integer>> iterator = array.iterator();
	while (iterator.hasNext()) {
	    /*
	    if (iterator.next().size() > min + range) {
		iterator.remove();
	    }
	    */
	    if ( min <= range && iterator.next().size() != min)
		iterator.remove();
	    if ( min > range && iterator.next().size() > min + 2)
		iterator.remove();
	}
	return array;
    }
	
}
