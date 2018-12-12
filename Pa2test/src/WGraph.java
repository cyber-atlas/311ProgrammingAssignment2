import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

//public class WGraph2 {
public class WGraph {

	//Instance variables

	//The name of the file
	public String fileName;

	//The file we are reading in
	private FileReader fileIn;

	//Instance variable to hold the number of verticies
	private int numVertices;
	//private int to hold the number of edges
	private int numEdges;

	private Hashtable<String, Integer> hash;



	//Making a Hashmap of linked lists to hold the adjacency list of the graph
	protected HashMap<String, ArrayList<String>> graphMap;
	//Making a HashTable to hold the edges...
	protected Hashtable<String, Edge> edgeTable;
	//TODO trying to fix heap
	protected HashMap<String,HashMap<String,Edge>> adjEdgeTest;
	//Hashset to hold whether or not we visited a node
	protected HashSet<String> visitedHashSet;
	//Hashmap to hold distances
	protected HashMap<String, Integer> distanceMap;

	protected HashMap<String, String> previousMap;

	protected ArrayList<String> vertexList = new ArrayList<>();

	/**
	 * Constructor for WGraph
	 * @param FName the filename of the file in the same directory
	 */
	public WGraph(String FName){
		//Saves the filename of the file
		fileName = FName;
		parseFile();
	}


	public void parseFile() {
		//Initalize the hashmap and hashset
		graphMap = new HashMap<String, ArrayList<String>>();
		File f  = new File(fileName);
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//Initalize the hashmap that holds the graph adjacency list
		graphMap = new HashMap<String, ArrayList<String>>();
		//Try to have a hashTable of edges, the key would be the edgename, the value would be the edge object
		edgeTable = new Hashtable<String, Edge>();
		//TODO initializign the test
		adjEdgeTest = new HashMap<String, HashMap<String, Edge>>();


		//The first line is going to be the number of vertices in the graph
		numVertices = s.nextInt();
//        System.out.println(numVertices);
		//The second is going to be the number of edges in the graph
		numEdges = s.nextInt();
//        System.out.println(numEdges);


		//So now we know that we can get the input properly and that we ignore the new line character
		//A scanner should only have 5 ints on the line seperated by spaces
		//TODO fixed a null error by not going parsing a line that does not have the stuff we need
		while (s.hasNextLine() && s.hasNextInt()){
			//Make the source vertex
			int sourceX = s.nextInt();
			int sourceY = s.nextInt();
			//Make the destination verex
			int destX = s.nextInt();
			int destY = s.nextInt();
			//Save the weight of the edge
			int weight = s.nextInt();


			String sourceXYString  = String.valueOf(sourceX) + ',' + String.valueOf(sourceY);
			String destXYString = String.valueOf(destX) + ',' + String.valueOf(destY);

			vertexList.add(sourceXYString);
			vertexList.add(destXYString);

//            System.out.println("source: " + sourceXYString + " dest " + destXYString + " weight " + weight);

			//Let's try this again adding it to a hashtable with the full edgename as the key, new edge as value
			edgeTable.put((sourceXYString+","+destXYString), new Edge(sourceXYString, destXYString, weight));
			//TODO testing making nested hashmap
			//If the key doesn't already exist, make a new hashmap for it
			if (!(adjEdgeTest.containsKey(sourceXYString))){
				adjEdgeTest.put(sourceXYString, new HashMap<String, Edge>());
			}
			//Get the hashmap returned by our node, add the child node and the edge
			adjEdgeTest.get(sourceXYString).put(destXYString, new Edge(sourceXYString,destXYString,weight));

			//If this entry in the hashmap does not exist, add it and initialize the arraylist
			if (!(graphMap.containsKey(sourceXYString))){
				graphMap.put(sourceXYString, new ArrayList<String>());
				if (!(graphMap.containsKey(destXYString))) {
					graphMap.put(destXYString, new ArrayList<String>());
				}
			}

			//Gets the key of the sourceXY string and adds the destination string to the arraylist of children
			graphMap.get(sourceXYString).add(destXYString);
		}
	}

	/** Given vertices u and v, ﬁnd the shortest path to from u to v.
	 pre: ux, uy, vx, vy are valid coordinates of vertices u and v
	 in the graph
	 post: return arraylist contains even number of integers,
	 for any even i,
	 i-th and i+1-th integers in the array represent
	 the x-coordinate and y-coordinate of the i/2-th vertex
	 in the returned path (path is an ordered sequence of vertices) */

	public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){

		String sourcenode = String.valueOf(ux) + "," + String.valueOf(uy);
		String destnode = String.valueOf(vx) + "," + String.valueOf(vy);

		//Either the source or the dest node aren't in our graph, so no shortest path
		if(!graphMap.containsKey(sourcenode) || !graphMap.containsKey(destnode)){
			return new ArrayList<Integer>() ;
		}


		visitedHashSet = new HashSet<>();
		//Hashmap to hold distances
		distanceMap = new HashMap<>();
		previousMap = new HashMap<>();


		String tempNode;

		//Make a new priority queue
		PriorityQueue priorityQueue = new PriorityQueue(comparator);
		if (sourcenode.equals(destnode)){

//                return  returnList;
			return getParent(sourcenode);
//                continue;
		}

		//loops through all the verteces and adds them to the priority queue and distance map if they aren't already in
		//If something is in distance map it will intially be in PQ. All nodes start at MAXINT
		for(String v:vertexList) {
			if (!distanceMap.containsKey(v)) {
				priorityQueue.add(v);
				distanceMap.put(v, Integer.MAX_VALUE);
			}
		}

		//Remove source, set it's distance to 0 then read bc PQ only updates on insertion/deletion
		priorityQueue.remove(sourcenode);
		distanceMap.put(sourcenode, 0);
		priorityQueue.add(sourcenode);

		while(!(priorityQueue.isEmpty())){
			//pops the lowest vertex off the priority queue
			tempNode = (String)priorityQueue.poll();

			//TODO find why this works and my initial idea does not
			if (tempNode.equals(destnode)){

//                return  getParent(tempNode);
				//TODO checked to make sure that the parent node was reached from the source
				ArrayList<Integer> tmpList = getParent(tempNode);
				if(checkParent(tmpList, ux, uy) == true){
					return tmpList;
				}
//                continue;
			}

			//If tempNode does not have a child, continue the loop
			if (!graphMap.containsKey(tempNode)){
				continue;
			}

			//For each neighbor of ourNode
			for(String child: graphMap.get(tempNode)){
				//The weight of the edge
//              int distToAdd = edgeTable.get(tempNode+ "," + child).getWeight();

				int distToAdd = adjEdgeTest.get(tempNode).get(child).getWeight();

				//The previous weight of the node
				int previousWeight = (distanceMap.get(tempNode) == null)? 0 : distanceMap.get(tempNode);
				int newDist = distToAdd + previousWeight;
				//Set the previous child node value to max int if it is not in the distanceMap or set it to prev value
				int prevChildVal = (distanceMap.get(child) == null)? Integer.MAX_VALUE : distanceMap.get(child);
				//If the new distance is less than the old update the child's weight and the previous map
				if(newDist < prevChildVal) {
					//Returns true or false whether it was removed or not
					Boolean didRemove = priorityQueue.remove(child) ? true : false;
					distanceMap.put(child, newDist);
					previousMap.put(child,tempNode);
					//Updating the edge
					if (didRemove){
						priorityQueue.add(child);
					}
				}
			}
		}

		//TODO the dest node may have been modified. Need to make sure that we did reach it from the child
		if((distanceMap.get(destnode)) != Integer.MAX_VALUE) {
//            return getParent(destnode);
//        }


			ArrayList<Integer> retList = getParent(destnode);
			int lastNum = retList.size() - 1;
//	        if (retList.get(lastNum - 1) == Integer.valueOf(vx) && retList.get(lastNum) == Integer.valueOf(vy)) {
			if(checkParent(retList, ux, uy)){
				return retList;
			} else {
				return new ArrayList<Integer>();
			}

		}

//        return getParent(sourcenode);
		return new ArrayList<Integer>();

	}


	//TODO V2S instead of checking if the child node is the node we need, check if it is int the set of nodes that we need, if it's in the hashset
	public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){

		HashSet<String> sHash = new HashSet<String>();
		for(int i = 0; i < S.size() - 1; i += 2) {
			sHash.add(""+ S.get(i) + "," + S.get(i+1));
		}
		String sourcenode = String.valueOf(ux) + "," + String.valueOf(uy);


		//edge case S equals v
//		    if(sHash.contains(sourcenode)) {return getParent(sourcenode);}
		visitedHashSet = new HashSet<>();
		//Hashmap to hold distances
		distanceMap = new HashMap<>();
		previousMap = new HashMap<>();


		String tempNode;



		if(!graphMap.containsKey(sourcenode)) {return new ArrayList<Integer>();}

		//Make a new priority queue
		PriorityQueue priorityQueue = new PriorityQueue(comparator);

		if (sHash.contains(sourcenode)){

//                return  returnList;
			return getParent(sourcenode);
//                continue;
		}

		//loops through all the verteces and adds them to the priority queue and distance map if they aren't already in
		//If something is in distance map it will intially be in PQ. All nodes start at MAXINT
		for(String v:vertexList) {
			if (!distanceMap.containsKey(v)) {
				//priorityQueue.add(v);
				distanceMap.put(v, Integer.MAX_VALUE);
			}
		}

		//Remove source, set it's distance to 0 then read bc PQ only updates on insertion/deletion
		priorityQueue.remove(sourcenode);
		distanceMap.put(sourcenode, 0);
		priorityQueue.add(sourcenode);

		while(!(priorityQueue.isEmpty())){
			tempNode = (String)priorityQueue.poll();

			//TODO find why this works and my initial idea does not
			if (sHash.contains(tempNode)){

//                return  returnList;
//                return getParent(tempNode);
				//TODO added checkParent
				//Checks if the source node is in the list of parents to the dest
				ArrayList<Integer> tmpList = getParent(tempNode);
				if(checkParent(tmpList, ux, uy)){
					return tmpList;
				}

//                continue;
			}

			//If tempNode does not have a child, continue the loop
			if (!graphMap.containsKey(tempNode)){
				continue;
			}

			//For each neighbor of ourNode
			for(String child: graphMap.get(tempNode)){
				//The weight of the edge
				int distToAdd = edgeTable.get(tempNode+ "," + child).getWeight();
				//The previous weight of the node
				int previousWeight = (distanceMap.get(tempNode) == null)? 0 : distanceMap.get(tempNode);
				int newDist = distToAdd + previousWeight;
				//Set the previous child node value to max int if it is not in the distanceMap or set it to prev value
				int prevChildVal = (distanceMap.get(child) == null)? Integer.MAX_VALUE : distanceMap.get(child);
				//If the new distance is less than the old update the child's weight and the previous map
				if(newDist < prevChildVal) {
					//Returns true or false whether it was removed or not
					Boolean didRemove = priorityQueue.remove(child) ? true : false;
					distanceMap.put(child, newDist);
					previousMap.put(child,tempNode);
					//Updating the edge
					/**
					 Edge tempE = edgeTable.get(tempNode+","+child);
					 tempE.weight = (newDist);
					 edgeTable.put((tempNode+","+child), tempE);
					 **/
//                   if (didRemove){
					priorityQueue.add(child);
//                   }
				}
			}
		}

//        return returnList;
//
//        if((distanceMap.get(destnode)) != Integer.MAX_VALUE){
//            return getParent(destnode);
//        }
//        return getParent(sourcenode);

		//If it gets here then to path to set
		return new ArrayList<Integer>();

	}


	public ArrayList<Integer> S2S(ArrayList<Integer> F, ArrayList<Integer> S){

		HashSet<String> sHash = new HashSet<String>();
		for(int i = 0; i < S.size() - 1; i += 2) {
			sHash.add(""+ S.get(i) + "," + S.get(i+1));
		}
		//rev
		HashMap<String, String> tempPath = new HashMap<>();
		Integer tempWeight = Integer.MAX_VALUE;
		String end = "";

		for(int i = 0;  i < F.size(); i += 2) {
			String sourcenode = String.valueOf(F.get(i)) + "," + String.valueOf(F.get(i+1));
//        String destnode = String.valueOf(vx) + "," + String.valueOf(vy);

			//Either the source or the dest node aren't in our graph, so no shortest path
//        if(!graphMap.containsKey(sourcenode) || !graphMap.containsKey(destnode)){
//        	ArrayList<Integer>
//            return new ArrayList<Integer>() ;
//        }

//        ArrayList<Integer> returnList = new ArrayList<Integer>();
			//edge case S equals v
//		    if(sHash.contains(sourcenode)) {return getParent(sourcenode);}
			visitedHashSet = new HashSet<>();
			//Hashmap to hold distances
			distanceMap = new HashMap<>();
			previousMap = new HashMap<>();


			String tempNode;


			if (!graphMap.containsKey(sourcenode)) {
				return new ArrayList<Integer>();
			}

			//Make a new priority queue
			PriorityQueue priorityQueue = new PriorityQueue(comparator);

			if (sHash.contains(sourcenode)) {

//                return  returnList;
				return getParent(sourcenode);
//                continue;
			}

			//loops through all the verteces and adds them to the priority queue and distance map if they aren't already in
			//If something is in distance map it will intially be in PQ. All nodes start at MAXINT
			for (String v : vertexList) {
				if (!distanceMap.containsKey(v)) {
					//priorityQueue.add(v);
					distanceMap.put(v, Integer.MAX_VALUE);
				}
			}

			//Remove source, set it's distance to 0 then read bc PQ only updates on insertion/deletion
			priorityQueue.remove(sourcenode);
			distanceMap.put(sourcenode, 0);
			priorityQueue.add(sourcenode);

			while (!(priorityQueue.isEmpty())) {
				//pops the lowest vertex off the priority queue
//           String tempNode = (String)priorityQueue.poll();
				tempNode = (String) priorityQueue.poll();
//           System.out.println( "tempNode" + tempNode);
//           System.out.println("tempnode " + tempNode + " dest " + destnode );

				//TODO find why this works and my initial idea does not
//            if (tempNode.equals(destnode)){
				if (sHash.contains(tempNode)) {
					Integer tempDist = distanceMap.get(tempNode);
					if(tempDist == null) continue;

					if(tempDist < tempWeight) {
						// deep copy
						for(String key: previousMap.keySet()){
							tempPath.put(key, previousMap.get(key));
						}
						tempWeight = tempDist;
						end = tempNode;
						break;
					}
				}

				//If tempNode does not have a child, continue the loop
				if (!graphMap.containsKey(tempNode)) {
					continue;
				}

				//For each neighbor of ourNode
				for (String child : graphMap.get(tempNode)) {
					//The weight of the edge
//					int distToAdd = edgeTable.get(tempNode + "," + child).getWeight();

					int distToAdd = adjEdgeTest.get(tempNode).get(child).getWeight();

					//The previous weight of the node
					int previousWeight = (distanceMap.get(tempNode) == null) ? 0 : distanceMap.get(tempNode);
					int newDist = distToAdd + previousWeight;
					//Set the previous child node value to max int if it is not in the distanceMap or set it to prev value
					int prevChildVal = (distanceMap.get(child) == null) ? Integer.MAX_VALUE : distanceMap.get(child);
					//If the new distance is less than the old update the child's weight and the previous map
					if (newDist < prevChildVal) {
						//Returns true or false whether it was removed or not
						priorityQueue.remove(child);
						distanceMap.put(child, newDist);
						previousMap.put(child, tempNode);
						//Updating the edge
						/**
						 Edge tempE = edgeTable.get(tempNode+","+child);
						 tempE.weight = (newDist);
						 edgeTable.put((tempNode+","+child), tempE);
						 **/
						priorityQueue.add(child);
					}
				}
			}
		}

		if(end != "") {


//			System.out.println(tempPath);

			//don't know why commenting this out helped but it did
			previousMap = tempPath;

//			System.out.println(previousMap.toString());
			return getParent(end);
		}
//        return returnList;
//
//        if((distanceMap.get(destnode)) != Integer.MAX_VALUE){
//            return getParent(destnode);
//        }
//        return getParent(sourcenode);

		//If it gets here then to path to set
		return new ArrayList<Integer>();

	}





	Comparator<String> comparator = new Comparator<String>() {
		@Override
		public int compare(String node1, String node2) {

			//If the value is null in the distancemap, then say that it is the biggest int since it's not been traversed
			int n1 = (distanceMap.get(node1) == null) ? Integer.MAX_VALUE : distanceMap.get(node1);
			int n2 = (distanceMap.get(node2) == null) ? Integer.MAX_VALUE : distanceMap.get(node2);

			//If the nodes are equal it will be 0
			//If n1 - n2 is negative then n2 is bigger else n1
			return n1 - n2;
		}
	};


	private ArrayList<Integer> getParent(String endNode){
		ArrayList<Integer> returnList = new ArrayList<>();

//        String nodeBeforeCur = previousMap.get(endNode);
		String nodeBeforeCur = (endNode);


		//TODO testing to save space
//	    LinkedList<Integer> tempLL = new LinkedList<>();

		//While the node before actually has a parent, null after it grabs the source which has no parent
		do{
			//Grabs the int value of the previous node

			String[] tmpList = nodeBeforeCur.split(",");
//            System.out.println(tmpList[0]+tmpList[1]);

//            for(String s : tmpList){
//                returnList.add(Integer.parseInt(s));
//            }

			//Since they get reversed later, adding them in reverse order
			for(int i = tmpList.length-1; i>=0; i--){
				String s = tmpList[i];
//                System.out.println(s);
				returnList.add(Integer.parseInt(s));
//	            tempLL.add(Integer.parseInt(s));
			}

//            returnList.add(Integer.valueOf(nodeBeforeCur.split(",")));
			//Grabs the next parent node
			nodeBeforeCur = previousMap.get(nodeBeforeCur);
		} while (nodeBeforeCur != null);

		//TODO more testing
		//Reverses the list before it is returns
//	    returnList = new ArrayList<Integer>(tempLL);
		Collections.reverse(returnList);
		return returnList;
	}

	//TODO added helper method
	//Checks to make sure the dest was reached from the source
	private boolean checkParent(ArrayList<Integer> numList, int sourceX, int sourceY){
		if(numList.get(0) == Integer.valueOf(sourceX) && numList.get(1) == sourceY){
			return true;
		}
		return false;
	}






}

