package com.eni.dvtejb.domaine.sessions;
import javax.ejb.Local;

@Local
public interface DaoGeneriqueLocal {
		
	<T> T ajouter(T t);
	void supprimer(Object t);
	<T> T chercher(Class<T> t, Object id );
	<T> T updater(T t);
    //Flush de la session JPA pour MAJ immédiate de la base
    public void flush();
}
