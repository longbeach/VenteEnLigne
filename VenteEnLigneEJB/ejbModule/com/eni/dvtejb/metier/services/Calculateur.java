package com.eni.dvtejb.metier.services;

import java.rmi.Remote;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService
@SOAPBinding(style=Style.RPC)
public interface Calculateur extends Remote
{
   @WebMethod int ajouter(int x, int y);
   @WebMethod int soustraire(int x, int y);

}