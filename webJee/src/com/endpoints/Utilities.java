package com.endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import com.bean.Game;
import com.bean.User;
import com.google.gson.Gson;

public class Utilities {
	public static User getUserFromBody(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		User user = gson.fromJson(reader, User.class);
		
		return user;
    }
	
	public static Game getGameFromBody(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		Gson gson = new Gson();
		Game game = gson.fromJson(reader, Game.class);
		
		return game;
	}
	
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

	public static String generateNewToken() {
	    byte[] randomBytes = new byte[24];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}

}
