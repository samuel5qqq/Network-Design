import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Graph {
	
	public int cost[][];
	public int traffic[][];
	public int totalcost = 0;
	public int totalpath = 0;
	public String shortestpath[][];
	public int used[][];
	
	public int number;
	public int k;
	
	public Graph(int number, int k) {
		this.number = number;
		this.k = k;
	}
	
	//Generate aij of undirected graph
	public void GenerateCost() {
		
		Random random = new Random();
		
		for(int i = 0; i < number; i++) 
			for(int j = 0; j < number; j++) {
				int temp = random.nextInt(4);
				traffic[i][j] = temp;
			}
		
	}
	
	//Generate bij of undirected graph
	public void GenerateTraffic() {
		
		int pick[] = new int[number];
		
		for(int i = 0; i < number; i++)
			for(int j = 0; j < number; j++)
				cost[i][j] = 250;
		
		for(int i = 0; i< number; i++) {
			
			List list = new ArrayList();
			
			int count = 0;
			for(int j = 0; j < number; j ++)
				list.add(j);
			
			while(count < k) {
				int random = (int) (Math.random() *list.size());
				int j = (int)list.get(random);
				if(i != j) {
					cost[i][j] = 1;
				    count++;
				    list.remove(random);
				}
			}
				
		}
			
		/*
		//For every node i, pick k random number and assign the edge as 1
		for(int i = 0; i < number; i++) {
			
			List list = new ArrayList();
			for(int j = 0; j < number; j ++)
				list.add(j);
			
			while(pick[i] < k && list.size() > 0) {
				
				int random = (int) (Math.random() *list.size());
				int j = (int)list.get(random);
				
				if(cost[i][j] == 250 && cost[j][i] == 250 && i != j && pick[j] < k) {
					cost[i][j] = 1;
					cost[j][i] = 1;
					pick[j]++;
					pick[i]++;
				}			
				list.remove(random);		
			}
		}
		*/	
	}
	
	public void dijkstra(int graph[][], int source) {
		
        int dist[] = new int[number];
        int path[] = new int[number];
        Boolean sptSet[] = new Boolean[number];
 
        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < number; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
            path[i] = -1;
        }
 
        // Distance of source vertex from itself is always 0
        dist[source] = 0;
 
        // Find shortest path for all vertices
        for (int count = 0; count < number-1; count++){

            int u = minDistance(dist, sptSet);
            // Mark the picked vertex as processed
            sptSet[u] = true;

            for (int v = 0; v < number; v++) {
                if (!sptSet[v] && graph[u][v]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+graph[u][v] < dist[v]) {
                	path[v] = u;
                    dist[v] = dist[u] + graph[u][v]; 
                }
            }
        }
        traverse(dist, path, source);
    }
	
	public int minDistance(int dist[], Boolean sptSet[]) {
		// Initialize min value
		int min = Integer.MAX_VALUE, min_index=-1;
	 
		for (int v = 0; v < number; v++)
			if (sptSet[v] == false && dist[v] <= min)
			{
				min = dist[v];
	            min_index = v;
	        }
		return min_index;
	}
	 
	// A utility function to print the constructed distance array
	public void traverse(int dist[], int path[], int source) {
		
		for (int i = 0; i < number; i++) {
			path(source, i, path, i);
		}
	}
	
	//Record every node along the shortest path
	public void path(int source, int destination, int path[], int i) {
		
		if(path[i] == -1)
			return;
	    path(source, destination, path, path[i]);
	    shortestpath[source][destination] = shortestpath[source][destination] +" "+ i;
	}
	//Initialize different aij and bij depend on different k
	public void initialize() {
		
		traffic = new int[number][number];
		cost = new int[number][number];
		shortestpath = new String[number][number];
		used = new int[number][number];
		for(int i = 0; i < number; i++) {
			for(int j = 0; j < number; j++) {
				cost[i][j] = 0;
				traffic[i][j] = 0;
				shortestpath[i][j] = String.valueOf(i);
				used[i][j] = 0;
			}
		}
		totalcost = 0;
	}
	
	public void WriteToFile(int source) throws IOException {
		
		String filename = "File"+k+".dot";
		File file = new File(filename);
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
		if(source == 0) {
			writer.write("digraph demo"+k+"{");
			writer.newLine();
		}
		
		for(int i= 0; i < number; i++) {
			String temp = "";
		    temp = shortestpath[source][i].replaceAll(" ", "->");			
			writer.write(temp+";");
			writer.newLine();
		}
		
		if(source == number-1) {
			writer.write("}");
		}
		writer.close();
	}
	
	public void calculate() {
		for (int i = 0; i < number; i++) {
			for(int j = 0; j < number; j++) {
				String[] temp = shortestpath[i][j].split(" ");
				int k = 0;
				while(k < temp.length-1) {
					int current = Integer.parseInt(temp[k]);
					int next = Integer.parseInt(temp[k+1]);
					used[current][next] = 1;
					k++;
				}
			}		
		}
		
		for (int i = 0; i < number; i++) {
			for(int j = 0; j < number; j++) {
				if(used[i][j] == 1) {
					totalcost += traffic[i][j]*cost[i][j];
					totalpath++;
				}
			}		
		}
	}
	
	public void NetworkAnalysis() throws IOException {
		
		float result;
		File file = new File("Density.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		result = (float)totalpath /(float)420;
		writer.write(String.valueOf(result)+" ");
		writer.write(String.valueOf(totalpath)+" ");
		writer.write(String.valueOf(totalcost));
		writer.newLine();
		writer.close();	 
	}
	
	
	public static void main(String args[]) throws IOException {
		
		Graph[] a = new Graph[13];
		for(int count = 0; count < 13; count++) {
			int k = count+3;
			a[count] = new Graph(21, k);
			a[count].initialize();
			a[count].GenerateCost();
			a[count].GenerateTraffic();
			for(int i = 0; i < 21; i++) {
				a[count].dijkstra(a[count].cost, i);
				a[count].WriteToFile(i);
			}
			a[count].calculate();
			a[count].NetworkAnalysis();
			
			System.out.println(a[count].totalcost);
		}

	}
}
