package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;
import it.polito.tdp.porto.model.Vertice;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			conn.close();
			return null;
			

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	public List<Author> getAutori() {
		final String sql = "SELECT * FROM author";
		List<Author> autori = new LinkedList<Author>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autori.add(autore);
			}

			conn.close();
			return autori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Vertice> getVertici(Map<Integer, Author> mappaAutori) {
		final String sql = "SELECT c1.authorid, c1.eprintid, c2.authorid " + 
							"FROM creator c1, creator c2 " + 
							"WHERE c1.eprintid=c2.eprintid " + 
							"AND c1.authorid<c2.authorid";
		List<Vertice> vertici = new LinkedList<Vertice>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Vertice v = new Vertice(mappaAutori.get(rs.getInt("c1.authorid")), mappaAutori.get(rs.getInt("c2.authorid")));
				vertici.add(v);
			}

			conn.close();
			return vertici;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}		
	}

	public Paper getVertice(Author a1, Author a2) {
		final String sql = "SELECT p.eprintid, p.title, p.issn, p.publication, p.type, p.types " + 
							"FROM creator c1, creator c2, paper p " + 
							"WHERE c1.eprintid=c2.eprintid " + 
							"AND p.eprintid=c1.eprintid " + 
							"AND c1.authorid=? AND c2.authorid=? " + 
							"limit 1";
		Paper paper = null;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {				
				paper = new Paper(rs.getInt("p.eprintid"), rs.getString("p.title"), rs.getString("p.issn"), rs.getString("p.publication"), rs.getString("p.type"), rs.getString("p.types")) ;
			}

			conn.close();
			return paper;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}