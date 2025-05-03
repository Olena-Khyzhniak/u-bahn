package ok.ubahn.Data;

import ok.ubahn.Model.Graph;

public abstract class DataSource {
    protected Graph graph = new Graph();

    public  Graph getGraph(){
        return graph;
    }

    public abstract void load() throws Exception;
}
