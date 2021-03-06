package com.fi9e.rest.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;

import org.mindrot.jbcrypt.BCrypt;

import com.fi9e.rest.dao.UserDao;
import com.fi9e.rest.dto.UserDTO;
import com.fi9e.rest.entity.User;
import com.fi9e.rest.exceptions.ApiException;
import com.fi9e.rest.mappers.UserMapper;
import com.fi9e.rest.models.UserCredentials;

import io.jsonwebtoken.Claims;

/**
 * 
 * @author Christopher
 *
 */
public class UserService implements UserServiceInterface {

	private final UserDao userDao;
	private final TokenServiceInterface tokenService;
	
	@Inject
	public UserService(TokenServiceInterface tokenService) {
		this.userDao = new UserDao();
		this.tokenService = tokenService;
	}
	
	/**
	 * 
	 * @param plainPassword the unencrypted password
	 * @param hashedPassword the hashed password
	 * @return boolean
	 */
	public boolean checkPassword(String plainPassword, String hashedPassword) {
		boolean result = false;
		
		if (BCrypt.checkpw(plainPassword, hashedPassword)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Get user but strip password!
	 * @param id the user id
	 * @return UserDTO
	 */
	public UserDTO getUserById(int id) {
		User user = this.userDao.getUserById(id);
		UserDTO dto = UserMapper.mapUserToUserDTO(user);
		
		dto.setPassword("");//don´t allow password read for this method!
		
		return dto;
	}
	
	/**
	 * Get full user information
	 * @param credentials the user credentials
	 * @return UserDTO
	 */
	public UserDTO getUserDTOByEmail(UserCredentials credentials) {
		User user = this.getUserByEmail(credentials);
		
		return UserMapper.mapUserToUserDTO(user);
	}
	
	
	/**
	 * Get User by Credentials (email)
	 * 
	 * @param credentials the user credentials
	 * @return User the user
	 */
	public User getUserByEmail(UserCredentials credentials) {
		List<User> users = this.userDao.get(credentials.getUserName());
		
		if(users.size() > 0) {
			return users.get(0);
		}
		
		return null;
	}
	
	/**
	 * Create a user in DB and return created object as DTO
	 * 
	 * @param user the user to create
	 * @return UserDTO
	 * @throws ApiException if user id is invalid
	 */
	public UserDTO createUser(UserDTO user) throws ApiException {

		int newUserId = this.userDao.createUser(user);
		
		if(newUserId <= 0) {
			throw new ApiException("Error creating new user.");
		}
		
		User newUser = this.userDao.getUserById(newUserId);
		
		UserDTO userDTO = UserMapper.mapUserToUserDTO(newUser);

		return userDTO;
	}

	/**
	 * Get specific user by id
	 * 
	 * @param id the user id
	 * @return UserDTO
	 */
	public UserDTO getUserById(final String id) {
		return this.getUserById(Integer.parseInt(id));
	}
	
	/**
	 * Login user by creating token that gets saved in user model
	 * 
	 * @param form form data from client (credentials)
	 * @throws ApiException if credentials are invalid
	 */
	public String login(MultivaluedMap<String, String> form) throws ApiException {
		String token = "";
		
		if(form == null) {
			throw new ApiException("Invalid form data");
		}
		
		String email;
		String password;
		email = !form.getFirst("username").isEmpty() ? form.getFirst("username") : "";
		password = !form.getFirst("password").isEmpty() ? form.getFirst("password"): "";
		
		UserCredentials credentials = new UserCredentials(email, password);
		
		User user = this.getUserByEmail(credentials);
		
		if(user == null) {
			throw new ApiException("User with email not found");
		}
		
		if(!this.checkPassword(password, user.getPassword())) {
			throw new ApiException("Passwords do not match our records");
		}
		
		//make token for user
		token = this.tokenService.createToken(user);
		
		//update user and save token
		user.setToken(token);
		this.userDao.updateUser(user);
		
		return token;
	}
	
	/**
	 * Logout user
	 * 
	 * @param authHeader auth header String
	 */
	public void logout(String authHeader) {
		String token = this.stripToken(authHeader);
		Claims payload = this.tokenService.verifyToken(token);
		
		int user_id =  payload.get("user_id", Integer.class) ;		
		User user = this.userDao.getUserById(user_id);
		user.setToken(null);
		
		this.userDao.updateUser(user);
	}
	
	
	/**
	 * Strip "Bearer " from authorization header value and
	 * only return token.
	 */
	public String stripToken(String authHeader) {
		final String regex = "\\s(.*)";
        
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(authHeader);
        String token = "";
        
        if(matcher.find()) {
        	token = matcher.group(1);
        }
        
        return token;
	}
	
	/**
	 * Check if email is already taken by a user
	 */
	public boolean isEmailTaken(String email) {
		return this.userDao.hasEmail(email.toLowerCase());
	}
}
