package it.polito.tdp.porto.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
//		List<Author> autori = model.getAuthors();
//		for (Author a : autori) {
//			System.out.println(a);
//		}
//		List<Vertice> vertici = model.getVertici() ;
//		for (Vertice v : vertici) {
//			System.out.println(v);
//		}
		
//		Author a = model.mappaAutori.get(719);
//		List<Author> coautori = model.getCoautori(a) ;
//		for (Author a2 : coautori) {
//			System.out.println(a2);
//		}
		
		Author a1 = model.mappaAutori.get(23099) ;
		Author a2 = model.mappaAutori.get(29770) ;
		List<Paper> papers = model.getPapers1(a1, a2) ;
		if (papers.size()==0)
			System.out.println("No connessioni");
		for (Paper p : papers) {
			System.out.println(p);
		}
		
	}

}
