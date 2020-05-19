package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph <String, DefaultWeightedEdge> grafo;
	private List <String> cammino;
	public Model() {
		dao = new EventsDao();
	}

	public void creaGrafo(String categoryId, int mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Event> eventsGrafo = new ArrayList<>();
		
		//Vertici
		for(Event e : dao.listAllEvents())
			if(e.getOffense_category_id().equals(categoryId) && e.getReported_date().getMonth().getValue()==mese &&
					!grafo.containsVertex(e.getOffense_type_id())) {
				grafo.addVertex(e.getOffense_type_id());
				eventsGrafo.add(e);
			}
		
		//Archi
		for(Coppia c : dao.creaCoppie(categoryId, mese))
			Graphs.addEdge(grafo, c.getOffenseType1(), c.getOffenseType2(), c.getPeso());
		
	}
	
	public Graph <String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<DefaultWeightedEdge> archiPesoMedio(){
		double somma = 0;
		int count = 0;
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			somma += grafo.getEdgeWeight(e);
			count++;
		}
		List<DefaultWeightedEdge> list = new ArrayList<>();
		double media = somma/count;
		for(DefaultWeightedEdge e : grafo.edgeSet())
			if(grafo.getEdgeWeight(e)>media) 
				list.add(e);
		return list;
	}
	
	public List<String> getCategorie() {
		return dao.getCategorie();
	}
	
	//CORRETTA! Funziona solo con grafi di piccole dimensioni e pochi archi (es. "burglary")
	public List<String> cercaCammino(String source, String end) {
		cammino = new ArrayList<>();
		List<String> parziale= new ArrayList<>();
		parziale.add(source);
		cerca(parziale, end, 0);
		return cammino;
	}

	private void cerca(List<String> parziale, String end, int i) {
		if(parziale.get(parziale.size()-1).equals(end) && parziale.size()>cammino.size()) {
			cammino = new ArrayList<>(parziale);
			//System.out.println(cammino);
			return;
		}
		else {
			for(String s : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1)))
				if(!parziale.contains(s)) {
					parziale.add(s);
					cerca(parziale, end, i+1);
				}
			parziale.remove(parziale.size()-1);
		}
	}
}
