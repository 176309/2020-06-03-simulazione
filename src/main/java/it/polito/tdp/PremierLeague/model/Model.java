package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedGraph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	private PremierLeagueDAO dao;
	
	public Model() {
		idMap= new HashMap<Integer, Player>();
		dao = new PremierLeagueDAO ();
		this.dao.listAllPlayers();
	}
	
	public void creaGrafo(double x) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer, Player>();
		
		dao.getVertici(x, idMap);
		Graphs.addAllVertices(grafo, idMap.values());
			
			for(Adiacenza a: dao.getAdiacenze(idMap)) {
				if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
					if(a.getPeso()<0) {
						Graphs.addEdgeWithVertices(grafo, a.getP2(), a.getP1(), ((double) -1)*a.getPeso());
					}else if(a.getPeso()>0) {
						Graphs.addEdgeWithVertices(grafo, a.getP1(),  a.getP2(), a.getPeso());
					}
				}
			}
			
			System.out.println(String.format("Grafo creato con % d vertici e %d archi ", grafo.vertexSet().size(), grafo.edgeSet().size()));
		
}
	
	public int VertexNumber() {
		return this.grafo.vertexSet().size();
	}

	
	public int EdgeNumber() {
		return this.grafo.edgeSet().size();
	}
}
