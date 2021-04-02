package com.endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
	    Cookie cookie = new Cookie( nom, valeur );
	    cookie.setMaxAge( maxAge );
	    response.addCookie( cookie );
	}
	
	public static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
