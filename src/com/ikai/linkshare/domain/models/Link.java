package com.ikai.linkshare.domain.models;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;

public class Link {

	private Key key;
	
	private String encodedKey;
	
	private User user;
	
	private String url;
	
	private String title;
	
	private String summary;
	
	private Long score;
	
	private Date submittedAt;
	
	private Long numComments;
	
	public static Link fromEntity(Entity entity){
		if(!entity.getKind().equals(Link.class.getSimpleName())) {
			throw new IllegalArgumentException("Entity " + entity.getKind() + " not of kind " + Link.class.getSimpleName());
		}
		Link link = new Link();
		link.setKey(entity.getKey());
		link.setEncodedKey(KeyFactory.keyToString(entity.getKey()));
		link.setUrl((String) entity.getProperty("url"));
		link.setUser((User) entity.getProperty("user"));
		link.setTitle((String) entity.getProperty("title"));
		link.setSummary((String) entity.getProperty("summary"));
		link.setScore((Long) entity.getProperty("score"));		
		link.setSubmittedAt((Date) entity.getProperty("submittedAt"));
		link.setNumComments((Long) entity.getProperty("numComments"));
		return link;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Date getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(Date submittedAt) {
		this.submittedAt = submittedAt;
	}

	public Long getNumComments() {
		return numComments;
	}

	public void setNumComments(Long numComments) {
		this.numComments = numComments;
	}
		
}
