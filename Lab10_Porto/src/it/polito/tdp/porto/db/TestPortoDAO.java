package it.polito.tdp.porto.db;

import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
		//System.out.println(pd.getAutore(85));
//		List<Author> autori = pd.getAutori();
//		for (Author a : autori) {
//			System.out.println(a);
//		}
		//		System.out.println(pd.getArticolo(2293546));
//		System.out.println(pd.getArticolo(1941144));
		
		Paper p = pd.getVertice(new Author(85, null, null), new Author(2669, null, null)) ;
		System.out.println(p);
		
	}

}
