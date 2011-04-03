package com.eni.dvtejb.metier.sessions;

import javax.ejb.Remote;

@Remote
public interface CommandeDAO {
	
	void traitementCarteCredit();
}
