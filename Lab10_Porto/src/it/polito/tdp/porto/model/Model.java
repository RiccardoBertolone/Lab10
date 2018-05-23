package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private PortoDAO dao;
	private List<Author> autori;
	private Multigraph<Author, DefaultEdge> grafo ;
	public Map<Integer, Author> mappaAutori ;
	
	
	
	public Model() {
		dao = new PortoDAO() ;
		mappaAutori = new HashMap<>() ;
		autori = dao.getAutori() ;
		for (Author a : autori) {
			mappaAutori.put(a.getId(), a);
		}
		grafo = new Multigraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, autori);
		List<Vertice> vertici = this.getVertici();
		for (Vertice v : vertici) {
			grafo.addEdge(v.getA1(), v.getA2()) ;
		}
		
	}



	public List<Vertice> getVertici() {
		return dao.getVertici(mappaAutori);
	}



	public List<Author> getAuthors() {
		return autori;
	}



	public List<Author> getCoautori(Author a) {
		List<Author> coautoriRipetuti = new LinkedList<>() ;
		coautoriRipetuti = Graphs.neighborListOf(grafo, a);
		List<Author> coautori = new LinkedList<>() ;
		for (Author a2 : coautoriRipetuti) {
			if (!coautori.contains(a2))
				coautori.add(a2) ;
		}
		
		return coautori;
	}



	public List<Paper> getPapers1(Author a1, Author a2) {
		ShortestPathAlgorithm<Author,DefaultEdge> spa = new DijkstraShortestPath<Author, DefaultEdge>(grafo);
		GraphPath<Author,DefaultEdge> gp = spa.getPath(a1, a2);
		
		if (gp == null)
			return new LinkedList<Paper>() ;
		
		//List<DefaultEdge> vertici = spa.getPath(a1, a2).getEdgeList();
		List<Author> camminoMinimo = gp.getVertexList();
		List<Paper> papers = new LinkedList<>() ;
		if (camminoMinimo.size()<2) {
			return papers ;
		}
		for (int i = 0; i<camminoMinimo.size()-1; i++) {
			papers.add(dao.getVertice(camminoMinimo.get(i), camminoMinimo.get(i+1))) ;
		}
		return papers;
	}



	public List<Paper> getPapers2(Author a1, Author a2) {
		// TODO Auto-generated method stub
		return null;
	}

}
