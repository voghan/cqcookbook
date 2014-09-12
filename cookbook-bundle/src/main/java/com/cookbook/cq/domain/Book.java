package com.cookbook.cq.domain;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;

/**
 * User: bvaughn
 * Date: 10/6/13
 */
public class Book extends BaseAdaptable {
    private static final long serialVersionUID = -3493414463695518701L;
    public transient Resource baseResource = null;

    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String path;
    private String year;

    public Book(Object o) {
        if (o instanceof Resource) {
            this.baseResource = (Resource) o;
        } else if (o instanceof Page) {
            Resource r = ((Page) o).adaptTo(Resource.class);
            if (r != null) {
                this.baseResource = r;
            }
        }
        buildBook();
    }

    public Book(Resource r) {
        this.baseResource = r;
        buildBook();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override Resource getResource() {
        return baseResource;
    }

    private void buildBook() {
        this.path = getResource().getPath();
        this.title = getPropertyAsString("jcr:content/par/bookdetail/title");
        this.author = getPropertyAsString("jcr:content/par/bookdetail/author");
        this.publisher = getPropertyAsString("jcr:content/par/bookdetail/publisher");
        this.isbn = getPropertyAsString("jcr:content/par/bookdetail/isbn");
        this.year = getPropertyAsString("jcr:content/par/bookdetail/year");
    }
}
