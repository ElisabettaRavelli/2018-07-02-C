package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private ExtFlightDelaysDAO dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private List<ArcoPeso> arcopeso;
	private Map<Integer, Airport> idMap;
	private List<Airport> aeroporti;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.arcopeso = new ArrayList<>();
		this.idMap = new HashMap<>();
		this.aeroporti = new ArrayList<>();
	}
	
	public void creaGrafo(Integer numCompagnie) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		System.out.println("Creo la lista");
		this.arcopeso = this.dao.getConnessioni(numCompagnie);
		System.out.println("Creo il grafo");
		for(ArcoPeso ap: this.arcopeso) {
			Graphs.addEdgeWithVertices(this.grafo, ap.getV1(), ap.getV2(), ap.getPeso());
			System.out.println("Arco aggiunto: "+ ap.getV1()+" -> "+ap.getV2()+" con peso = "+ ap.getPeso());
		}
		
	}

	public int getVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Airport> getAeroporti(){
		this.dao.loadAllAirports(idMap);
		for(Integer i: this.grafo.vertexSet()) {
			if(idMap.containsKey(i)) {
				this.aeroporti.add(idMap.get(i));
			}
		}
		Collections.sort(this.aeroporti);
		return this.aeroporti;
	}
	
	public List<AeroportoPeso> getAdiacenti(Integer aeroporto){
		List<AeroportoPeso> result = new ArrayList<>();
		List<Integer> adiacenti = new ArrayList<>();
		adiacenti = Graphs.neighborListOf(this.grafo, aeroporto);
		for(Integer i : adiacenti){
			AeroportoPeso ap = new AeroportoPeso(i,
					this.grafo.getEdgeWeight(this.grafo.getEdge(aeroporto, i)));
			System.out.println("Adiacente: "+ ap.getAeroporto());
			result.add(ap);
		}
		Collections.sort(result);
		return result;
	}

}
