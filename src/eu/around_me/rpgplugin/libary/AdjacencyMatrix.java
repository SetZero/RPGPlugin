package eu.around_me.rpgplugin.libary;

import java.util.ArrayList;
import java.util.List;

import skills.Skill;


/**
 * Creates an Adjacency matrix (https://en.wikipedia.org/wiki/Adjacency_matrix) to connect the Skill nodes
 * @author Sebastian
 * @version 0.0.1
 */
public class AdjacencyMatrix {
	private int n;
    private Skill[][] a;
    
    public AdjacencyMatrix(int n0) {
    	n = n0;
        a = new Skill[n0][n0];
    }
    
    public Skill getSkill(int i, int j) {
    	return a[i][j];
    }
    
    public void addEdge(Skill sk, int i, int j) {
        a[i][j] = sk;
    }
    
    public void addDblEdge(Skill sk, int i, int j) {
        a[i][j] = sk;
        a[j][i] = sk;
    }
    
    public void addDblEdge(Skill sk, int j) {
    	int i = sk.getID();
        a[i][j] = sk;
        a[j][i] = sk;
    }
   
    public void removeEdge(int i, int j) {
        a[i][j] = null;
    }
    
    public void removeDblEdge(int i, int j) {
        a[i][j] = null;
        a[j][i] = null;
    }
    
    public boolean hasEdge(int i, int j) {
    	if(a[i][j] != null)
    		return true;
    	return false;
    }
    public List<Integer> outEdges(int i) {
        List<Integer> edges = new ArrayList<Integer>();
        for (int j = 0; j < n; j++) 
            if (a[i][j] != null) edges.add(j);
        return edges;
    }
    public List<Integer> inEdges(int i) {
        List<Integer> edges = new ArrayList<Integer>();
        for (int j = 0; j < n; j++)
            if (a[j][i] != null) edges.add(j);
        return edges;
    }
    
    public List<Skill> outEdgesSkills(int i) {
    	List<Skill> edges = new ArrayList<Skill>();
         for (int j = 0; j < n; j++) 
             if (a[i][j] != null) edges.add(a[i][j]);
         return edges;
    }
    public List<Skill> inEdgesSkills(int i) {
    	List<Skill> edges = new ArrayList<Skill>();
        for (int j = 0; j < n; j++)
            if (a[j][i] != null) edges.add(a[j][i]);
        return edges;
    }
    
    public boolean isInstance(Skill s) {
    	for(int i=0;i<n;i++) {
    		for(int j=0;j<=i;j++) {
    			if(s == a[i][j])
    				return true;
    		}
    	}
    	return false;
    }
    
    public int[] getPosition(Skill s) {
    	for(int i=0;i<n;i++) {
    		for(int j=0;j<=i;j++) {
    			if(s == a[i][j]){
    				int[] ret = {i, j};
    				return ret;
    			}
    		}
    	}
    	int[] ret = {-1};
    	return ret;
    }
    
    public boolean isLearned(Skill s) {
    	for(int i=0;i<n;i++) {
    		if(s == a[s.getID()][i])
    			return true;
    	}
    	return false;
    }
}
