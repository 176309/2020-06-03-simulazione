package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	Graph<Player, DefaultWeightedEdge> grafo;
	Map<Integer, Player> idMap;
	PremierLeagueDAO dao;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(double x) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer, Player>();
		
		dao.getVertici(x, idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		for(Adiacenza adj: dao.getAdiacenze(idMap)) {
			if(grafo.containsVertex(adj.getP1()) && grafo.containsVertex(adj.getP2())) {
				if(adj.getPeso()<0) // arco da p2>p1
					Graphs.addEdgeWithVertices(grafo, adj.getP2(), adj.getP1(), ((double) -1)* adj.getPeso());
			}else if (adj.getPeso()>0) // arco da p1 a p2
				Graphs.addEdgeWithVertices(grafo, adj.getP1(), adj.getP2(), adj.getPeso());
				
			}
		
			
	
  System.out.println(String.format("Grafo creato con %d vertici e %d archi " , grafo.vertexSet().size(), grafo.edgeSet().size()));
}


	public int nVertici() {
	return grafo.vertexSet().size();
}

	public int nArchi() {
	return grafo.edgeSet().size();
}
	
	public Graph<Player, DefaultWeightedEdge> getGrafo(){
		return grafo;
	}
	}
