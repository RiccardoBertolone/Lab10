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
		
		Author a = model.mappaAutori.get(719);
		List<Author> coautori = model.getCoautori(a) ;
		for (Author a2 : coautori) {
			System.out.println(a2);
		}
		
	}

}
