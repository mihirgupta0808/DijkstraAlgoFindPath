package col106.assignment6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
public class ShortestPathFinder implements ShortestPathInterface {
  /**
   * Computes shortest-path from the source vertex s to destination vertex t 
   * in graph G.
   * DO NOT MODIFY THE ARGUMENTS TO THIS CONSTRUCTOR
   *
   * @param  G the graph
   * @param  s the source vertex
   * @param  t the destination vertex 
   * @param left the cost of taking a left turn
   * @param right the cost of taking a right turn
   * @param forward the cost of going forward
   * @throws IllegalArgumentException unless 0 <= s < V
   * @throws IllegalArgumentException unless 0 <= t < V
   * where V is the number of vertices in the graph G.
   */
	Digraph G ;
	int[] s ; 
	int[] t ; 
	int left ; 
	int right ; 
	int forward ; 
	int W ; 
	int dEdges ; 
	int numNodes;
	bigNode start ; 
	HashMap<Integer,ArrayList<bigNode>> startMap;
	HashMap<bigNode,Boolean> visited ;
	HashMap<bigNode,Integer> distances ; 
	HashMap<bigNode,HashMap<bigNode,Integer>> bigAdj ; 
	ArrayList<bigNode> endNodes ; 
	HashMap<bigNode,bigNode> before; 
	
	// value 0 for not visited , 1 for visited
	
	public class bigNode{
		int from ;
		int to;
		double inDis;
		public bigNode(int from , int to , double inDis) {
			this.from = from ; 
			this.to = to ; 
			this.inDis = inDis ; 
		}
		
		
		
	}
	// key of -1,-1 will be -1
	public int getKey(int i , int j ) {
		return i*W + j ; 
	}
	public int getKey(int[] a) {
		return a[0]*W + a[1] ;
	}
	public int getX(int key) {
		if(key == -1) return -1;
		return key/W ; 
	}
	public int getY(int key) {
		if(key == -1) return -1 ; 
		return key%W ; 
	}
	
  public ShortestPathFinder (final Digraph G, final int[] s, final int[] t, 
  final int left, final int right, final int forward) {
  	this.G = G ; 
  	this.s = s ; 
  	this.t = t ; 
  	this.left = left ; 
  	this.right = right ; 
  	this.forward = forward ; 
  	startMap = new HashMap<Integer,ArrayList<bigNode>>() ; 
  	visited = new HashMap<bigNode,Boolean>();
  	distances = new HashMap<bigNode,Integer>();
  	bigAdj = new HashMap<bigNode,HashMap<bigNode,Integer>>() ;
  	dEdges = 0 ; 
  	endNodes = new ArrayList<bigNode>();
  	before = new HashMap<bigNode,bigNode>();
  	searched = false ; 
  	
  	
  	ArrayList<Edge> edges = (ArrayList<Edge>)G.edges();
  
  	this.W = G.W();
   
  	start = new bigNode(-1,getKey(s),0);
  	visited.put(start,false);
  	distances.put(start,0);
  	numNodes++;
  	HashMap<bigNode,Integer> bi = new HashMap<bigNode,Integer>();
  	bigAdj.put(start,bi);
  	before.put(start,start);
  	
  	//bigAdj.put(start,)
  	
  	
  	for(int i = 0 ; i < edges.size() ;i++) {
  		numNodes++;
  		//System.out.println(numNodes);
  		
  		
  		Edge e = edges.get(i);
  		//System.out.println("(" + getX(e.from()) + "," + getY(e.from()) + ")" +" " + "(" + getX(e.to()) + "," + getY(e.to()) + ")");
  		//if(e.to() == getKey(s)) continue ;
  		bigNode node = new bigNode(e.from() , e.to() , e.weight());
  		visited.put(node,false);
  		distances.put(node,Integer.MAX_VALUE);
  		HashMap<bigNode,Integer> bi2 = new HashMap<bigNode,Integer>();
  		bigAdj.put(node, bi2);
  		if (startMap.containsKey(e.from()) == false ) startMap.put(e.from(), new ArrayList<bigNode>());
  		
  		ArrayList<bigNode> snodes = startMap.get(e.from());
  		snodes.add(node);
  		startMap.replace(e.from(),snodes);
  		
  		
  		if(getKey(s) == e.to()) {
  			endNodes.add(start);
  		}
  		if(e.to() == getKey(t)) {
  			endNodes.add(node);
  		}
  		before.put(node,node);
  		
  	}  	
  	for(bigNode node : bigAdj.keySet()) {
  		if(bigAdj.get(node) == null) {
  			HashMap<bigNode,Integer> connect = new HashMap<bigNode,Integer>();
  			bigAdj.replace(node,connect);
  			
  		}
  		HashMap<bigNode,Integer> connect = bigAdj.get(node);
  		Integer bef = node.from ; 
  		Integer mid = node.to ; 
  		ArrayList<bigNode> startList = startMap.get(mid);
  		//System.out.println("hi");
  		if(startList != null) {
  			
  			for(int i = 0 ; i < startList.size(); i++) {
  	  			bigNode nextNode = startList.get(i) ;
  	  			Integer af = nextNode.to ;
  	  			
  	  			
  	  		    int inval = (int)(nextNode.inDis);
  	  		    int add = 0 ; 
  	  		    if(bef == -1) {
  	  		    	add = forward ;     	
  	  		    }
  	  		    else {
  	  		    	int x1 = getX(bef);
  	  		    	int y1 = getY(bef);
  	  		    	int x2 = getX(mid);
  	  		    	int y2 = getY(mid);
  	  		    	int x3 = getX(af);
  	  		    	int y3 = getY(af);
  	  		    	
  	  		    	
  	  		    	int step1 ;
  	  		    	int step2 ;
  	  		    	// up 1 , down 2 , right 3 , left 4
  	  		    	
  	  		    	
  	  		    	if(x2 < x1 ) {
  	  		    		// up
  	  		    		step1 = 1 ;
  	  		    	}
  	  		    	else if(x2 > x1){
  	  		    		// down
  	  		    		step1 = 2 ;
  	  		    		
  	  		    	}
  	  		    	else if(y2 > y1) {
  	  		    		// right
  	  		    		step1 = 3 ; 
  	  		    	}
  	  		    	else {
  	  		    		// left
  	  		    		step1 = 4 ; 
  	  		    	}
  	  		    	
  	  		    	
  	  		    	
  	  		    	
  	  		    	if(x3 < x2 ) {
  	  		    		// up
  	  		    		step2 = 1 ;
  	  		    	}
  	  		    	else if(x3 > x2){
  	  		    		// down
  	  		    		step2 = 2 ;
  	  		    		
  	  		    	}
  	  		    	else if(y3 > y2) {
  	  		    		// right
  	  		    		step2 = 3 ; 
  	  		    	}
  	  		    	else {
  	  		    		// left
  	  		    		step2 = 4 ; 
  	  		    	}
  	  		    	
  	  		           // straight
  	  		    	if( ((x1 == x2) && (x2 == x3))  || ((y1 == y2) && (y2 == y3)) ){
  	  		    		add = forward ; 
  	  		    		
  	  		    	}
  	  		   // up 1 , down 2 , right 3 , left 4
  	  		    	else if(                ( (step1 == 1) &&(step2 == 3))  || ((step1 ==4) &&(step2 == 1) ) || ( (step1 == 3) && (step2 == 2)) ||(   (step1 == 2) &&( step2 == 4)     )                      ) {
  	  		    		add = right ; 
  	  		    	}
  	  		    	// left 
  	  		    	else {
  	  		    		
  	  		    		add = left ; 
  	  		    		
  	  		    	}
  	  		    	
  	  		    	
  	  		    }
  	  		    dEdges++;
  	  		    connect.put(nextNode, inval + add);
  	  		    
  	  		}
  			
  			
  			
  			
  			
  		}
  		
  		bigAdj.replace(node,connect);
  	}
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
      // YOUR CODE GOES HERE
  }

  // Return number of nodes in dual graph
  public int numDualNodes() {
      // YOUR CODE GOES HERE
	  return distances.size();
      //return 0;
  }

  // Return number of edges in dual graph
  public int numDualEdges() {
      // YOUR CODE GOES HERE
	  return dEdges ; 
      //return 0;
  }

  // Return hooks in dual graph
  // A hook (0,0) - (1,0) - (1,2) with weight 8 should be represented as
  // the integer array {0, 0, 1, 0, 1, 2, 8}
  public ArrayList<int[]> dualGraph() {
      // YOUR CODE GOES HERE
	  ArrayList<int[]> ans = new ArrayList<int[]>();
	  for(bigNode node : bigAdj.keySet()) {
		  int bef = node.from ; 
		  int mid = node.to ; 
		  
		  HashMap<bigNode,Integer> connect = bigAdj.get(node);
		  for(bigNode nextNode : connect.keySet()) {
			  int af = nextNode.to ;
			  int wt = connect.get(nextNode);
			  int[] arr = new int[7];
			  if(bef != -1) {
				  arr[0] = getX(bef);
				  arr[1] = getY(bef);
				  
			  }
			  else {
				  arr[0] = -1;
				  arr[1] = -1 ; 
			  }
			  arr[2] = getX(mid);
			  arr[3] = getY(mid);
			  arr[4] = getX(af);
			  arr[5] = getY(af);
			  arr[6] = wt;
			  ans.add(arr);
		  }
		  
	  }
	  return ans ; 
      //return null;
  }

  // Return true if there is a path from s to t.
  boolean dfs(int i,ArrayList<Edge>[] nb,int source,int target) {
	if(seen[i] == true) return false ; 
	seen[i] = true ; 
	  if(i == target) return true ; 
	  ArrayList<Edge> elist = nb[i];
	  for(int j = 0 ; j < elist.size() ; j++) {
		  Edge e = elist.get(j);
		  int index = e.to() ; 
		  if(dfs(index,nb,source,target) == true) return true ;
	  }
	  return false ;
  }
  boolean[] seen;
  public boolean hasValidPath() {
      // YOUR CODE GOES HERE
	  
	  ArrayList<Edge>[] nb = G.adjacency();
	  int sz = nb.length ; 
	  seen = new boolean[sz];
	  for(int i = 0 ; i < sz ; i++) {
		  seen[i] = false;
	  }
	  int source = getKey(s);
	  int target = getKey(t);
	  int si ;
	  int ti ;
	  
	  // start dfs from adj source 
	  return dfs(source,nb,source,target);
	  
	  
      //return false;
  }

  // Return the length of the shortest path from s to t.
  
  class bigNodeComparator implements Comparator<bigNode>{ 
      
      // Overriding compare()method of Comparator  
                  // for descending order of cgpa 
      public int compare(bigNode b1, bigNode b2) { 
          if (distances.get(b1) < distances.get(b2)) {
        	  return -1 ; 
          }
             
          else if (distances.get(b1) > distances.get(b2)) {
        	  return 1;
        	  
          }
               
           return 0; 
      } 
  } 
  
  
  
  
  
  
  
  
  
  void findShortest() {
	  //HashMap<Integer,ArrayList<bigNode>> startMap;
	  //ArrayList<bigNode> startsource = startMap.get(getKey(s));
	  searched = true ; 
	  int n = distances.size();
	  
	  PriorityQueue<bigNode> q = new PriorityQueue<bigNode>(distances.size(), new bigNodeComparator()); 
	  /*
	  for(bigNode node : distances.keySet()) {
		  q.add(node);
	  }
	  */
	  q.add(start);
	  
	  bigNode cur = start ; 
	  int totalvisited = 0 ;
	  
	  //while(totalvisited!= n ) {
	  while(q.isEmpty() == false) {
		  cur = q.poll();
		  
		  visited.replace(cur, true);
		  totalvisited++;
		  //System.out.println(totalvisited);
		  //System.out.println("current node in diji");
		  //printNode(cur);
		  HashMap<bigNode,Integer> con = bigAdj.get(cur);
		  if(con.size() > 0 ) {
			  for(bigNode node : con.keySet()) {
				  
				  //System.out.println("neighbout is : "  );
				  //printNode(node);
				  int sourceD = distances.get(cur);
				  //System.out.println("source dis : " + sourceD);
				  
				  int pathD = con.get(node);
				  //System.out.println("path ln: " + pathD);
				  
				  
				  int curD = distances.get(node);
				  //System.out.println("curD :"  + curD );
				  int newVal;
				  if(sourceD == Integer.MAX_VALUE) newVal = Integer.MAX_VALUE;
				  if(curD == Integer.MAX_VALUE && sourceD == Integer.MAX_VALUE) {
					  newVal = Integer.MAX_VALUE;
				  }
				  else if(curD == Integer.MAX_VALUE) {
					  if(pathD == Integer.MAX_VALUE) newVal = Integer.MAX_VALUE;
					  else {
						  newVal = sourceD + pathD ; 
						  
						  before.replace(node,cur);
						  distances.replace(node, newVal);
						  q.add(node);
						  
						  
					  }
					 
					  
				  }
				  else {
					  if(sourceD + pathD <= curD) {
						  newVal = sourceD + pathD ; 
						  before.replace(node, cur);
						  distances.replace(node, newVal);
						  q.add(node);
						  
					  }
					  else {
						  newVal = curD ; 
					  }
				  }
				  //distances.replace(node, newVal);
				  
				  //System.out.println("updated dis is: " + distances.get(node));
			  }
		  }
		  
		  if(totalvisited == n ) break;
		  /*
		  int smallest = Integer.MAX_VALUE;
		  for(bigNode node : visited.keySet()) {
			  if( visited.get(node) == false && distances.get(node) < smallest) {
				  smallest = distances.get(node);
				  cur = node ; 
				  
			  }
		  }
		  */
		  
		  
	
	  }
	  shortestVal = Integer.MAX_VALUE;
	  //System.out.println("line 416 ," + endNodes.size());
	  for(int i = 0 ; i < endNodes.size() ; i++) {
		  
		  bigNode en = endNodes.get(i);
		  //System.out.println("(" + getX(en.from) + "," + getY(en.from) + ")" +" " + "(" + getX(en.to) + "," + getY(en.to) + ")");
		  if(distances.get(en) < shortestVal) {
			  shortestVal = distances.get(en);
			  //System.out.println("hui");
			  lastNode = en ; 
		  }
	  }
	  //System.out.println("last node");
	  //System.out.println("(" + getX(lastNode.from) + "," + getY(lastNode.from) + ")" +" " + "(" + getX(lastNode.to) + "," + getY(lastNode.to) + ")");
	  
	  
	  
  }
  void printNode(bigNode b) {
	  System.out.println("(" + getX(b.from) + "," + getY(b.from) + ")" +" " + "(" + getX(b.to) + "," + getY(b.to) + ")");
	  
  }
  
  int shortestVal;
  bigNode lastNode;
  boolean searched;
  
  
  
  
  
  
  
  
  
  
  
  
  public int ShortestPathValue() {
      // YOUR CODE GOES HERE
	  if(searched == false) {
		  findShortest();
	  }
	  return shortestVal; 
	  
	  
	  
	  
	  
	  
	  
	  
      //return 0;
  }

  // Return the shortest path computed from s to t as an ArrayList of nodes, 
  // where each node is represented by its location on the grid.
  public ArrayList<int[]> getShortestPath() {
      // YOUR CODE GOES HERE
	  
	  if(searched == false) {
		  findShortest();
	  }
	  bigNode cur = lastNode ; 
	  ArrayList<int[]> ans = new ArrayList<int[]>() ; 
	  while( cur != start) {
		  int[] arr = new int[2];
		  //System.out.println("line 474");
		  if(cur == null ) {
			  //System.out.println("null");
			  
		  }
		  //System.out.println(getX(cur.from));
		  arr[0] = getX(cur.to);
		  arr[1] = getY(cur.to);
		  ans.add(arr);
		  cur = before.get(cur);
	  }
	  ans.add(s);
	  
	  ArrayList<int[]> rev = new ArrayList<int[]>();
	  for(int i = ans.size()-1 ; i >= 0  ; i--) {
		  rev.add(ans.get(i));
		  
	  }
	  
	  return rev; 
	  
	  
      //return null;
  }
}


