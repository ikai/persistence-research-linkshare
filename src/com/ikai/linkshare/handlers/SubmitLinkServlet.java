package com.ikai.linkshare.handlers;


import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class SubmitLinkServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String url = req.getParameter("url");
		// Check for link in session, if it's already there, forget it
		// just use that one
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); 
		
		// User is not logged in, let's ask the User to log in
		if(user == null) {
			// TODO: Stash the URL into the session
			// For the time being let's just not optimize this case
			resp.sendRedirect("/");
		} else {
			// TODO: Validate the link, prevent script injection, etc
			
			// We're setting the primary key to the userId + the URL to prevent
			// a duplicate submission (naive)
			// This also lets us (naively) prevent someone from stomping another
			// user's changes
			Entity link =  new Entity("Link", user.getUserId() + url);
			link.setProperty("url", url);
			link.setProperty("user", user);
			link.setProperty("score", 1);
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(link);
			resp.sendRedirect("/edit?key=" + KeyFactory.keyToString(link.getKey()));
			
		}

		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		
	}
}
