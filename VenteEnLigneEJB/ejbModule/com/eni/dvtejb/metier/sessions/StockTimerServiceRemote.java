package com.eni.dvtejb.metier.sessions;

import javax.ejb.Remote;

@Remote
public interface StockTimerServiceRemote {

	public void verifierStocks(); 
	public void arreterTimers();
}
