package com.eni.dvtejb.metier.services;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService(endpointInterface="com.eni.dvtejb.metier.services.Calculateur")
public class CalculateurBean {
   public int ajouter(int x, int y)
   {
      return x + y;
   }

   public int soustraire(int x, int y)
   {
      return x - y;
   }
}