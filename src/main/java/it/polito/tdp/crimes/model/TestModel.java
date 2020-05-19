package it.polito.tdp.crimes.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class TestModel {

	public static void main(String[] args) {
		
		Model m= new Model();
		
		m.creaGrafo("burglary", 6);
		System.out.println(m.getGrafo());
		System.out.println(m.getGrafo().vertexSet().size());
		System.out.println(m.getGrafo().edgeSet().size());
		
		for(DefaultWeightedEdge e : m.getGrafo().edgeSet())
			System.out.println(m.getGrafo().getEdgeWeight(e));
		
		System.out.println(m.cercaCammino("burglary-residence-by-force", "burglary-residence-no-force"));
	}

}
