package com.eni.dvtejb.metier.sessions;

import javax.ejb.Local;

@Local
public interface CompteurStatefulLocal {

	public int incrementer();
    public void raz();

}
