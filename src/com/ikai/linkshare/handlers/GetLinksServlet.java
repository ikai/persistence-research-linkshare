package com.ikai.linkshare.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.ikai.linkshare.domain.models.Link;

/**
 * 
 * Return the links. Do so using JSON.
 * 
 * @author Ikai Lan
 * 
 */
@SuppressWarnings("serial")
public class GetLinksServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Build the query
		Query findTopLinksQuery = new Query("Link");
		findTopLinksQuery.addSort("score", SortDirection.DESCENDING);

		// Retrieve results
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Iterable<Entity> results = datastore.prepare(findTopLinksQuery)
				.asIterable(FetchOptions.Builder.withLimit(25));

		// Iterate through results and make them Entity objects
		ArrayList<Link> links = new ArrayList<Link>();

		for (Entity entity : results) {
			Link link = Link.fromEntity(entity);			
			links.add(link);
		}
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("links", links);
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser(); 
		response.put("currentUser", user);
		
		Gson gson = new Gson();		
		resp.getWriter().println(gson.toJson(response));
	}

}
