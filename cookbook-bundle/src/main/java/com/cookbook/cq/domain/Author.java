package com.cookbook.cq.domain;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Author extends BaseAdaptable {
	private static final long serialVersionUID = 3311958315770404923L;
	
	private String author;
	private List<Book> books;
	private String imagePath;
	private String path;
	private List<Genre> genres;
	
	public transient Resource baseResource = null;

    public Author(Object o) {
        if (o instanceof Resource) {
            this.baseResource = (Resource)o;
        }
        else if (o instanceof Page) {
            Resource r = ((Page)o).adaptTo(Resource.class);
            if (r != null) {
                this.baseResource = r;
            }
        }
        buildAuthor();
    }

    public Author(Resource r) {
        this.baseResource = r;
        buildAuthor();
    }
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	@Override
	Resource getResource() {
		return baseResource;
	}
	
	private void buildAuthor() {
		this.path = getResource().getPath();
    	this.author = getPropertyAsString("jcr:content/jcr:title");
    	this.genres = new ArrayList<Genre>();
    }
}
