package net.sgf.ws.simple.api;

import javax.jws.WebService;

import net.sgf.ws.simple.param.User;

@WebService
public class UserService {

	public User create(User user) {
		user.setId(123L);
		return user;
	}
	
}
