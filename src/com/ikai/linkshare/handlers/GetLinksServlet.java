package com.ikai.linkshare.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
			links.add(Link.fromEntity(entity));
		}

		// Turn them into JSON
		Gson gson = new Gson();
		String response = gson.toJson(links);

		PrintWriter out = resp.getWriter();
		out.println(response);

	}

}
