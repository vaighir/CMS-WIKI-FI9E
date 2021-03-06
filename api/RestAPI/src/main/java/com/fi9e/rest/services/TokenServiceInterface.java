package com.fi9e.rest.services;

import com.fi9e.rest.entity.User;

import io.jsonwebtoken.Claims;

/**
 * 
 * @author Christopher
 *
 */
public interface TokenServiceInterface {
	public Claims verifyToken(String token);
	public String createToken(User user);
}
