package com.ikai.linkshare.handlers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Logger;

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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ikai.linkshare.domain.models.Link;

@SuppressWarnings("serial")
public class EditLinkServlet extends HttpServlet {
	
    private static final Logger log = Logger.getLogger(EditLinkServlet.class.getName());
	

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
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// User is not logged in, let's ask the User to log in
		if (user == null) {
			// TODO: Stash the URL into the session
			// For the time being let's just not optimize this case
			resp.sendRedirect("/?error="
					+ URLEncoder.encode("Please log in", "UTF-8"));
		} else {
			String generatedKey = user.getUserId() + req.getParameter("url");
			Key key = KeyFactory.createKey(Link.class.getSimpleName(),
					generatedKey);

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			try {
				Entity entity = datastore.get(key);
				String title = req.getParameter("title");
				String summary = req.getParameter("summary");
				entity.setProperty("title", title);
				entity.setProperty("summary", summary);
				entity.setProperty("submittedAt", new Date());
				entity.setProperty("score", 1);
				datastore.put(entity);
				resp.sendRedirect("/?success=" + URLEncoder.encode("Successfully submitted link", "UTF-8"));
			} catch (EntityNotFoundException e) {
				log.warning("Error finding Entity with key: " + generatedKey);
				resp.sendRedirect("/?error="
						+ URLEncoder.encode("Error editing link", "UTF-8"));
			}

		}
	}
}
