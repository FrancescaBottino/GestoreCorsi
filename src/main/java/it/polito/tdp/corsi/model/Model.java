package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model { //classe da passacarte per il dao
	
	//riferimento a dao
	private CorsoDAO corsoDao;
	
	public Model() {
		corsoDao= new CorsoDAO();
		
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		
		return corsoDao.getCorsiByPeriodo(pd); 
		
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		
		return corsoDao.getIscrittiByPeriodo(pd);
	}
	
	public List<Studente> getStudentiByCorso(String codice){
		
		return corsoDao.getStudentiByCorso(new Corso(codice, null, null, null));
	}
	
	public Map<String, Integer> getDivisioneCDS(String codice) {
		
		//dato il corso con codice abd mi aspetto GEST-->50 , INF --> 30 ecc
		
		//SOLUZIONE 1 
		/*
		Map<String, Integer> divisione=new HashMap<String, Integer>();
		
		List<Studente> studenti=this.getStudentiByCorso(codice);
		
		for(Studente s: studenti) {
			
			if(s.getCds()!=null && s.getCds()!= "") {
			
			if(divisione.get(s.getCds())==null) {
				divisione.put(s.getCds(), 1);
			}else {
				divisione.put(s.getCds(), divisione.get(s.getCds())+1);
			}
			}
			
		}
		return divisione;
		*/
		
		//SOLUZIONE 2
		
		return corsoDao.getDivisioneStudenti(new Corso(codice, null, null, null));	
		
	}

	public boolean esisteCorso(String codice) {
		
		return corsoDao.esisteCorso(new Corso(codice, null, null,null));
	}
	
	
}
