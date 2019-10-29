package net.sgf.ws.simple;

import javax.xml.ws.Endpoint;

import net.sgf.ws.simple.api.UserService;

public class SimpleWebserviceTest {

	public static void main(String[] args) {
		UserService userService = new UserService();
	    String address1="http://127.0.0.1:9281/ws/userService";
		Endpoint.publish(address1, userService);
	}
	
}
