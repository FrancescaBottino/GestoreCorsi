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
import it.polito.tdp.corsi.model.Studente;

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
	
	public List<Studente> getStudentiByCorso(Corso corso){
		
		String sql="SELECT s.matricola, s.cognome, s.nome, s.cds "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola AND i.codins=? ";
		
		List<Studente> result= new ArrayList<Studente>();
		
		try {
			
			Connection conn=DBConnect.getConnection(); //collegata con dbconnect
			PreparedStatement st=conn.prepareStatement(sql); //per inserire parametro ?
			st.setString(1, corso.getCodins()); //imposto primo parametro
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				
				Studente s= new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));
				result.add(s);
				
			}
			
			rs.close();
			st.close();
			conn.close();
			
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	
		return result;
	}
	
	public Map<String, Integer> getDivisioneStudenti(Corso corso){
		
		String sql="SELECT s.CDS, COUNT(*) as tot "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola AND i.codins=? AND s.cds <> '' "
				+ "GROUP BY s.cds ";
		
		Map<String, Integer> result=new HashMap<String, Integer>();
		
          try {
			
			Connection conn=DBConnect.getConnection(); //collegata con dbconnect
			PreparedStatement st=conn.prepareStatement(sql); //per inserire parametro ?
			st.setString(1, corso.getCodins()); //imposto primo parametro
			
			ResultSet rs=st.executeQuery();
			
			while (rs.next()) {
				result.put(rs.getString("CDS"), rs.getInt("tot"));
				
				
			}
			
			rs.close();
			st.close();
			conn.close();
			
           }catch(SQLException e) {
   			throw new RuntimeException(e);
   		}
   		
		return result;
		
	}


	public boolean esisteCorso(Corso corso) {
		
		String sql="SELECT * FROM corso WHERE codins= ?";
		
		try {
			
			Connection conn=DBConnect.getConnection(); //collegata con dbconnect
			PreparedStatement st=conn.prepareStatement(sql); //per inserire parametro ?
			st.setString(1, corso.getCodins()); //imposto primo parametro
			
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return true;
				
			}
			else {
				rs.close();
				st.close();
				conn.close();
				return false;
			}
			
			
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}	
	

}
