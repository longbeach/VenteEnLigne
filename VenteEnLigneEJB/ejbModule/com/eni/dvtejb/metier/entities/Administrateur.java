package com.eni.dvtejb.metier.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="A")
public class Administrateur extends Utilisateur {

}
