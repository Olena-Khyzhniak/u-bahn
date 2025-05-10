package ok.ubahn.Model;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeAL<T> {
    public T data; // Generalized data storage
    public String name; // Station name
    public int nodeValue; // algorithm use e.g Djikstra , DFS etc....
    public List<GraphLinkAL> adjList = new ArrayList<>(); // List for connected nodes

    public  int x;
    public  int y;
    public GraphNodeAL(T data,String name, int x, int y){
        this.data = data;
        this.name = name;
        this.nodeValue = Integer.MAX_VALUE;
        this.adjList = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public GraphNodeAL(T data, String name) {
        this(data, name, -1, -1);
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void addLink(GraphNodeAL<T> destination, double cost, String line){
        adjList.add(new GraphLinkAL(this, destination, cost, line));
        destination.adjList.add(new GraphLinkAL(destination, this, cost, line)); // undirected graph
    }

    public T getData(){
        return data;
    }

    public String getName(){
        return name;
    }

    public List<GraphLinkAL> getAdjList() {
        return adjList;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(){
        this.nodeValue = nodeValue;
    }
}

