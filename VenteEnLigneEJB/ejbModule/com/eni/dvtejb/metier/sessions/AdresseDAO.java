package com.eni.dvtejb.metier.sessions;

import java.util.List;

import javax.ejb.Remote;

import com.eni.dvtejb.metier.entities.Adresse;

@Remote
public interface AdresseDAO {
	
	public List findAll();
	public List findByRue(String rue);
	public Adresse findById(Integer id);
	public void save(Adresse adresse);
	public void merge(Adresse adresse);
	public List findByVille(String ville);

}
