package com.eni.dvtejb.web;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestResource {

 @GET
 @Produces("text/html")
 public String getMessage( ) {
	 System.out.println("Test *************************************************** depuis getMessage");
  return "hello";
 }
 
 @POST
 @Produces("text/html")
 @Path("toto")
 public Response  putMess( ) {
	 System.out.println("Test *************************************************** depuis putMess");
	   Response response = response = Response.status(200).build(); //updated
	   return response;
  //return "hello";
 }
}
