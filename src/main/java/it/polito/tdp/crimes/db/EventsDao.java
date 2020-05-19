package it.polito.tdp.crimes.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;

import it.polito.tdp.crimes.model.Coppia;
import it.polito.tdp.crimes.model.Event;
//import it.polito.tdp.crimes.model.OffenseCategory;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
			e.printStackTrace();
			return null ;
		}
	}
	
	
	//SBAGLIATO IL COUNT!!
	public List<Coppia> creaCoppie (String categoryId, int mese)  {
		
		//SQL SBAGLIATO
		/*String sql = "SELECT E1.offense_type_id AS t1, E2.offense_type_id AS t2, COUNT(*) AS c " +
						"FROM EVENTS AS E1, EVENTS AS E2 " + 
						"WHERE (E1.offense_category_id = ? AND E2.offense_category_id = ? AND " + 
						"MONTH(E1.reported_date) = ? AND MONTH(E2.reported_date) = ? AND E1.neighborhood_id=E2.neighborhood_id) " + 
						"GROUP BY t1, t2";*/
		String sql = "SELECT E1.offense_type_id AS t1, E2.offense_type_id AS t2, COUNT(DISTINCT E1.neighborhood_id) AS c " +
				"FROM EVENTS AS E1, EVENTS AS E2 " + 
				"WHERE (E1.offense_category_id = ? AND E2.offense_category_id = ? AND " + 
				"MONTH(E1.reported_date) = ? AND MONTH(E2.reported_date) = ? AND E1.neighborhood_id=E2.neighborhood_id) " + 
				"GROUP BY t1, t2";
		
		List<Coppia> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, categoryId);
			st.setString(2, categoryId);
			st.setInt(3, mese);
			st.setInt(4, mese);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(!res.getString("t1").equals(res.getString("t2"))) {
					Coppia c1 = new Coppia(res.getString("t1"), res.getString("t2"), Integer.parseInt(res.getString("c")));
					Coppia c2 = new Coppia(res.getString("t2"), res.getString("t1"), Integer.parseInt(res.getString("c")));
					if(!list.contains(c1) && !list.contains(c2))
						list.add(c1);
				}
			}
			conn.close();	
			return list;
		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
			e.printStackTrace();
		}		
		return null;
	}
	
	public List<String> getCategorie() {
		
		String sql = "SELECT DISTINCT offense_category_id FROM events";
		List<String> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getString("offense_category_id"));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			System.out.println("Errore connessione al database");
			e.printStackTrace();
		}		
		return null;
	}
}
