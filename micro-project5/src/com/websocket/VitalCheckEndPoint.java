package com.websocket;


import java.io.StringWriter;
import java.util.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value="/VitalCheckEndPoint",configurator=VitalCheckconfigurator.class)
public class VitalCheckEndPoint  {
	
       
	static Set<Session> subscribers=Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void handleOpen(EndpointConfig epc, Session usersession) {
		
		usersession.getUserProperties().put("username",epc.getUserProperties().get("username"));
		subscribers.add(usersession);
	}
	
	@OnMessage
	public void handleMessage(String msg,Session usersession) {
		String username =(String)usersession.getUserProperties().get("username");
		if(username!=null && !username.equals("doctor")) {
			subscribers.stream().forEach(x->{
				try {
					if((x.getUserProperties().get("username")).equals("doctor")) {
						x.getBasicRemote().sendText(buildJSON(username,msg));
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			});
		}
		else if(username!=null && username.equals("doctor"))
	    {
	     String[] messages=msg.split(",");
	     String patient=messages[0];
	     String subject=messages[1];
	     subscribers.stream().forEach(x->{
	     try 
	    {
	        if(subject.equals("ambulance"))
	        {
	           if(x.getUserProperties().get("username").equals(patient))
		 {
		 x.getBasicRemote().sendText(buildJSON("doctor","has summoned an ambulance"));
		 }
	           else if(x.getUserProperties().get("username").equals("ambulance"))
		{
		 x.getBasicRemote().sendText(buildJSON(patient,"Requires an ambulance"));
		 }
	          }
	       else if(subject.equals("medication"))
	         {
	             if(x.getUserProperties().get("username").equals(patient))
		{
		 x.getBasicRemote().sendText(buildJSON("doctor",messages[2]+","+messages[3]));
		 }
		
	          }
	 }
	 catch(Exception e)
	 {
	     e.printStackTrace();
	 }
	 });
	 }
	}
	
	@OnClose
	public void handleClose(Session usersession) {
		subscribers.remove(usersession);
	}
	
	@OnError
	public void handleError(Throwable t) {
		
	}
	private String buildJSON(String username,String msg) {
		JsonObject jsonObject=Json.createObjectBuilder().add("message",username+","+msg).build();
		 StringWriter stringWriter=new StringWriter();
		   try(JsonWriter jsonWriter=Json.createWriter(stringWriter))
		     {
		         jsonWriter.write(jsonObject);
		     }
		   return stringWriter.toString();
	}
}
