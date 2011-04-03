package com.eni.dvtejb.metier.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue(value="C")
@NamedQuery(name="Client.findByTitre", 
		query="select c from Client c where c.titre = :leTitre")
public class Client extends Utilisateur {

}
