
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;


public class Test {
 
    
    /**
     * Calculates the eigenvector centrality for a given network
     * @param matrix adjacency matrix of network
     * @param t number of times you want to recurse
     * @return the eigenvector centralities for each node in the network at each recursive call t.
     */
    
    public static double [][] eigenvectorCentrality(int [][] matrix, int t) {
       // int edges = numEdges(matrix);
        double [][] eigenvector = new double[t+1][matrix.length];
        for (int i = 0; i < matrix.length; i ++){
            eigenvector[0][i] = 1;
        }
        for (int r = 1; r <= t; r++){
            double scoreBefore = 0;
            for (int i = 0; i<matrix.length; i++){
                scoreBefore += eigenvector[r-1][i];
            }
            for (int c = 0; c < matrix.length; c++){
                double value = 0;
                for(int j = 0; j < matrix.length; j++){
                    value += matrix[c][j]*eigenvector[r-1][j];
                }
                eigenvector[r][c] = value/scoreBefore;
            }
        }
        return eigenvector;
    }
    
    public static int numEdges (int [][]matrix){
        int counter = 0;
        for (int r =0; r<matrix.length; r++){
            for (int c = 0; c< matrix.length; c++){
                counter += matrix[r][c];
            }
        }
        return counter/2;
    }
    
    /**
     * Calculates the degree centrality for each node in a network
     * @param matrix the adjacency matrix for the network
     * @return a matrix with the degree centrality of each node
     */
    public static double [] degreeCentrality(int [][] matrix) {
        int n = matrix.length;
        double [] data = new double [n];
        double count = 0;
        for (int r =0; r<matrix.length; r++) {
            for (int c=0; c<matrix.length; c++) {
                count+=matrix[r][c];
            }
            data[r] = count/(n-1);
            count =0;
        }
        return data;
    }
    
    /**
     * Calculates the harmonic centrality for each node in a network
     * @param matrix the adjacency matrix for the network
     * @return a matrix with the harmonic centralities of each node
     */
    public static double [] harmonicCentrality(int [][] matrix) {
        int length = matrix.length;
        double[] data = new double [length];
        int[][] dist = getShortestPaths(matrix);
        
        for (int r = 0; r< length; r++){
            double counter = 0;
            for (int c = 0; c<length; c++){
                if (r!=c){
                    int distBw = dist[r][c];
                    if (distBw != 0){
                        counter += 1/ (double) distBw;
                    }
                    
                }
                
            }
            
            data[r] = counter/(length - 1);
        }
        return data;
    }
    
    /**
     * Helper function to getNumPaths; calculates the number of shortest paths between s and all other nodes using bfs
     * @param matrix the adjacency matrix for the network
     * @param s source node
     * @return a matrix with the number of shortest paths from s to other nodes
     */
    public static int [] bfsPaths(int matrix[][], int s) {
        int t = s;
        int n = matrix.length;
        int [] visited = new int [n];
        int [] val = new int [n];
        int [] count = new int [n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(t);
        visited[s] = 1;
        val[t] = 0;
        count[t] = 1;
        while (!queue.isEmpty()) {
          int u = queue.remove();
        for (int v=0; v<n; v++) {
            if (matrix[u][v] == 1) {
                if (visited[v]==0) {
                    val[v] = val[u]+1;    
                    count[v] = count[u]; 
                    visited[v] = 1;
                    queue.add(v);
                }
                else if (visited[v]==1) {
                    if (val[v] == val[u]+1) {
                        count[v] = count[v]+count[u];
                    }
                    else if (val[v] > val[u]+1) {
                        val[v] = val[u]+1;
                        count[v] = count[u];
                    }
            }
                
            }
            
        }
        
        }
        count[t] = 0;
       return count; 
        
    }
    
      /**
     * Helper function to getShortestPaths; calculates the lengths of the shortest path between s and all other nodes using bfs
     * @param matrix the adjacency matrix for the network
     * @param s source node
     * @return a matrix with the distance of the shortest path from s to other nodes
     */
    public static int [] bfsLengths(int [][] matrix, int s) {
        int t = s;
        int n = matrix.length;
        int [] visited = new int [n];
        int [] val = new int [n];
        int [] count = new int [n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(t);
        visited[s] = 1;
        val[t] = 0;
        count[t] = 1;
        while (!queue.isEmpty()) {
          int u = queue.remove();
        for (int v=0; v<n; v++) {
            if (matrix[u][v] == 1) {
                if (visited[v]==0) {
                    val[v] = val[u]+1;    
                    count[v] = count[u]; 
                    visited[v] = 1;
                    queue.add(v);
                }
                else if (visited[v]==1) {
                    if (val[v] == val[u]+1) {
                        count[v] = count[v]+count[u];
                    }
                    else if (val[v] > val[u]+1) {
                        val[v] = val[u]+1;
                        count[v] = count[u];
                    }
            }
                
            }
            
        }
        
        }
        count[t] = 0;
       return val; 
    }
    
    /**
     * Finds the number of shortest paths between all nodes in a network
     * @param matrix the adjacency matrix for the network
     * @return 2D matrix where index i,j is the number of shortest paths between i and j
     */
    public static int[][] getNumPaths(int [][] matrix) {
        int [][] numPaths = new int [matrix.length][matrix.length];
        for (int r = 0; r<matrix.length;r++){
            numPaths[r] = bfsPaths(matrix, r);
        }
        return numPaths;
    }
    
    /**
     * Finds the length of shortest paths between all nodes in a network
     * @param matrix the adjacency matrix for the network
     * @return 3D matrix where index i,j is the length of the shortest path between 1 and j
     */
    public static int[][] getShortestPaths(int [][] matrix) {
        int [][] numPaths = new int [matrix.length][matrix.length];
        for (int r = 0; r<matrix.length;r++){
            numPaths[r] = bfsLengths(matrix, r);
        }
        return numPaths;
    }
    
    /**
     * Finds the betweenness centrality for all the nodes in a network
     * @param matrix the adjacency matrix of the network
     * @return 1D matrix with betweenness centralities for all the nodes in the network
     */
    public static double[] betweeness (int [][]matrix){
        int[][] dist = getShortestPaths(matrix);
        int[][] numPaths = getNumPaths (matrix);
        double[] betweenessCoef = new double[matrix.length];
        for (int i = 0; i < betweenessCoef.length; i++){
            double counter = 0;
            for (int r = 0; r < numPaths.length; r++){
                for (int c = 0; c < numPaths.length; c++){
                    int distRC = dist[r][c]; //shortest path between r and c
                    int distRI = dist[r][i]; //shortest path between r and i
                    int distIC = dist[i][c]; //shortest path between i and c
                    // If distRC is equal to distRI + distIC then there must be a shortest path through iu
                    if(distRC == distRI + distIC){
                        int pathsThroughI = numPaths[r][i] * numPaths[i][c]; //calculates the number of shortest paths through I
                        int pathsOverall = numPaths[r][c];
                        if (pathsOverall != 0){
                            counter += (double)pathsThroughI/(double)pathsOverall;
                          
                        }
                    }
                }
            }
              betweenessCoef[i] = counter/(double)(matrix.length - 1)/(double)(matrix.length - 2);
        }
        return betweenessCoef;
    }
    
    

    public static void main(String[] args) {
        String [] families = {"Acciaiuoli", "Albizzi", "Barbadori", "Bischeri", "Castellani", "Ginori", "Guadagni", "Lamberteschi", "Medici",
                              "Pazzi", "Peruzzi", "Pucci", "Riddfi", "Salviati", "Strozzi", "Tornabuoni"};
        int [][] data = 
                   {{0,1,1,1},
                    {1,0,1,0},
                    {1,1,0,1},
                    {1,0,1,0}};
     
        
   int [][] fam = {{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                   {0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0},
                   {0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0},
                   {0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0},
                   {0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0},
                   {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                   {0,1,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
                   {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
                   {1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,1},
                   {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                   {0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0},
                   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                   {0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1},
                   {0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0},
                   {0,0,0,1,1,0,0,0,0,0,1,0,1,0,0,0},
                   {0,0,0,0,0,0,1,0,1,0,0,0,1,0,0,0}};

   
   double [] degree = Test.degreeCentrality(fam).clone();
   double [] harmonic = Test.harmonicCentrality(fam).clone();
   double [] betweeness = Test.betweeness(fam).clone();
   double [][] eigenvector = Test.eigenvectorCentrality(fam, 30);
   
   

   System.out.format("%15s", "Families");
   for (int i=0; i<families.length; i++) {
       System.out.format("%15s", families[i]);
   
   }
   System.out.println();
   System.out.format("%15s", "Degree");
   for (double j: degree) {
       System.out.format("%15f", j);
   }
   System.out.println();
   System.out.format("%15s", "Harmonic");
   for (double k: harmonic) {
       System.out.format("%15f", k);
   }
   System.out.println();
   System.out.format("%15s", "Betweenness");
   for (double l: betweeness) {
       System.out.format("%15f", l);
   }
   System.out.println();
   System.out.format("%15s", "Eigenvector");
   for (int i=0; i<eigenvector[30].length; i++) {
       System.out.format("%15f", eigenvector[30][i]);
   }
   System.out.println();
}

}