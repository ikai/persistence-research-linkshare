package com.ikai.linkshare.handlers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class AddVoteServlet extends HttpServlet {
	
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		Gson gson = new Gson();
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (user == null) {
			response.put("error", "No user logged in");
			resp.sendError(401, gson.toJson(response));
		} else {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			String encodedKey = req.getParameter("key");
			Key key = KeyFactory.stringToKey(encodedKey);

			try {
				Entity entity = datastore.get(key);

				// Let's just increment the thing right now
				// We'll prevent multiple votes later it's not important
				Long score = (Long) entity.getProperty("score");
				score++;
				entity.setProperty("score", score);
				datastore.put(entity);
				response.put("score", score);
				resp.getWriter().println(gson.toJson(response));
			} catch (EntityNotFoundException e) {
				// Let's create a new child Vote object with user ID as a Key				
				response.put("error", "Item not found");
				resp.sendError(404, gson.toJson(response));
			}

		}
	}
}
