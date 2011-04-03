package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Stock implements Serializable {	
	
	@Id
	private long stockid;
	
	private BigDecimal quantite;

	public long getStockid() {
		return stockid;
	}

	public void setStockid(long stockid) {
		this.stockid = stockid;
	}

	public BigDecimal getQuantite() {
		return quantite;
	}

	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}
}
