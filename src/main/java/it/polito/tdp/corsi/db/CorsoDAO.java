package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {
	
	public List<Corso> getCorsiByPeriodo(Integer periodo){
		
		String sql="select * "
				+ "from corso "
				+ "where pd=?" ;
		
		List<Corso> result= new ArrayList<Corso>();
		
		try {
			
			Connection conn=DBConnect.getConnection(); //collegata con dbconnect
			PreparedStatement st=conn.prepareStatement(sql); //per inserire parametro ?
			st.setInt(1, periodo); //imposto primo parametro
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Corso c= new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				//mi creo un corso per ogni riga del db
				result.add(c);
			}
			
		rs.close();
		st.close();
		conn.close();	
			
		}catch ( SQLException e) {
			
			throw new RuntimeException(e);
			
		}
		
		return result;
		
	}
	
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		
		String sql= "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) as tot "
				+ "FROM Corso c, Iscrizione i "
				+ "WHERE c.codins=i.codins AND c.pd= ? "
				+ "GROUP BY  c.codins, c.nome, c.crediti, c.pd";
		
		Map<Corso, Integer> result= new HashMap<Corso, Integer>();
		
		try {
			
			Connection conn=DBConnect.getConnection(); //collegata con dbconnect
			PreparedStatement st=conn.prepareStatement(sql); //per inserire parametro ?
			st.setInt(1, pd); //imposto primo parametro
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Corso c= new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				//mi creo un corso per ogni riga del db
				Integer n= rs.getInt("tot");
				result.put(c,n); 
			}
			
		rs.close();
		st.close();
		conn.close();	
			
		}catch ( SQLException e) {
			
			throw new RuntimeException(e);
			
		}
		
		return result;
		
		
		
	}
	
	
	
	

}
