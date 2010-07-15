package com.ikai.linkshare.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.ikai.linkshare.domain.models.Link;

@SuppressWarnings("serial")
public class EditLinkServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Key key = KeyFactory.stringToKey(req.getParameter("key"));
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		try {
			Entity linkEntity = datastore.get(key);
			Link link = Link.fromEntity(linkEntity);
			req.setAttribute("link", link);
			req.getRequestDispatcher("WEB-INF/templates/editlink.jsp").forward(
					req, resp);


		} catch (EntityNotFoundException e) {
			// 404 or something
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

	}
}
