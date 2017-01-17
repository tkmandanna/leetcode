import java.util.*;

public class WordLadderMine{
	
	public Map<String,List<String>> graph;  /*used to create adjacency matrix ..graph = {lot=[hot], log=[lot], dot=[hot], cot=[hot], cog=[cot], hot=[hit]}*/
	public List<String> nodes; // used for backtracing
	public List<List<String>> results; // final list of routes
	
	public List<List<String>> findLadders(String start, String end, Set<String> dict) {
				
		graph = new HashMap<String,List<String>>();
		results = new ArrayList<List<String>>();
		nodes = new ArrayList<String>();
		
		Set<String> visited = new HashSet<String>();
		Set<String> unvisited = new HashSet<String>(dict);
		Queue<String> queue = new ArrayDeque<String>();
		Map<String,Integer>distances = new HashMap<String,Integer>(); /*distances = {lot=2, hit=0, log=3, dot=2, cot=2, cog=3, hot=1} distance of each node from first word*/
		
		unvisited.add(end);
		queue.add(start);
		distances.put(start,new Integer(0));
		Iterator iter = unvisited.iterator();
		while(iter.hasNext())
		{
			distances.put(""+iter.next(),Integer.MAX_VALUE);
		}
		
		
		int len = start.length();
		while(!queue.isEmpty())
		{
			String current = queue.poll();
			System.out.println("Popping "+current+" off the queue");
			Integer curDist = distances.get(current);
			System.out.println("graph3 = "+graph.toString());
			for(int i =0 ;i< len;i++)
			{
				StringBuilder builder = new StringBuilder(current);
				for(char c='a';c<'z';c++)
				{
					builder.setCharAt(i,c);
					
					String new_word=builder.toString();
					
					
					if(unvisited.contains(new_word))
					{	
						
						if((curDist+1)<=distances.get(new_word) )
						{
							System.out.println("current word = "+current+" current distance = "+curDist.toString()+" new_word = "+new_word+" new_word distance = "+distances.get(new_word).toString());
							if(!queue.contains(new_word))
							queue.add(new_word);
						
							distances.put(new_word,new Integer(curDist+1));
							System.out.println("distances2 after adding = "+distances.toString());
							System.out.println("visited = "+visited.toString());
							if(visited.contains(new_word))
							{
								
								List<String> routes = graph.get(new_word);
								System.out.println("current routes = "+routes.toString());
								routes.add(current);
								System.out.println("Adding new route for"+new_word+" "+routes.toString());
								graph.put(new_word,routes);
								System.out.println("graph2 = "+graph.toString());
								
								
							}
							else{
								System.out.println("VISITING "+new_word+" NOW");
								visited.add(new_word);
								List<String> routes = new ArrayList<String>();
								routes.add(current);
								graph.put(new_word,routes);
							}
							System.out.println("graph = "+graph.toString());
							System.out.println("distances = "+distances.toString());
						}
						else{						
								System.out.println("NOT ADDING A NEW ROUTE ="+current+" -> "+new_word+" old = "+distances.get(new_word)+" new = "+(curDist+1));
							}
						
					}else{
						
						if(current.equals("log"))
						System.out.println("new_word = "+new_word);
						
					}
					
				}
				
			}
		}
		System.out.println("----------AT THE END--------");
		System.out.println("graph = "+graph.toString());
		System.out.println("distances = "+distances.toString());
		createPaths(end,start);
		
		return results;
		
	}
	
	public void createPaths(String word, String start)
	{
		System.out.println("nodes = "+nodes.toString());
		if(word.equals(start))
		{
			nodes.add(0,start);
			results.add(new ArrayList<String>(nodes));
			System.out.println("Adding routes "+results.toString());
			nodes.remove(0);
			return;
			
		}
		nodes.add(0,word);
		if(graph.get(word)!=null)
		{
			for(String s: graph.get(word))
			{
				createPaths(s,start);
			}
		}
		nodes.remove(0);
	}
	
	public static void main(String args[])
	{
		WordLadderMine wl = new WordLadderMine();
		Set<String> dict = new HashSet<String>();
		
		dict.add("hot");dict.add("dot");dict.add("dog");dict.add("lot");dict.add("log");
		
		List<List<String>> results = wl.findLadders("hit","cog",dict);
		
		for(List<String> strList : wl.results)
		{
			for(String str : strList)
			System.out.print(str+" -> ");
		
			System.out.println("");
		}
	}
}