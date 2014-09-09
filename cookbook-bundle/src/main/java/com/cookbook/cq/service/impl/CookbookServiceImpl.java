package com.cookbook.cq.service.impl;

import com.cookbook.cq.dao.AuthorDao;
import com.cookbook.cq.dao.BookDao;
import com.cookbook.cq.dao.impl.AuthorDaoImpl;
import com.cookbook.cq.dao.impl.BookDaoImpl;
import com.cookbook.cq.domain.Author;
import com.cookbook.cq.domain.Book;
import com.cookbook.cq.domain.Genre;
import com.cookbook.cq.service.CookbookService;
import com.day.cq.dam.api.Asset;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * OSGi service implementation that can be accessed from within AEM.
 * 
 * This service uses the Apache Felix annotations to define the service.
 * This is a traditional AEM OSGi service without auto wiring dependencies.
 */
@Service
@Component(metatype = true)
@Properties({
        @Property(
                name = "service.vendor",
                value = "Your Company Name")
})
public class CookbookServiceImpl implements CookbookService {
	private static final Logger LOG = LoggerFactory.getLogger(CookbookServiceImpl.class);
	
	private AuthorDao authorDao;
	
	private BookDao bookDao;
	
	public CookbookServiceImpl() {
		this.authorDao = new AuthorDaoImpl();
		this.bookDao = new BookDaoImpl();
	}

	public List<Book> findBooks(ResourceResolver resourceResolver, Tag tag) {
		LOG.info("...findBooks for " + tag);
		return bookDao.findBooks(resourceResolver, tag);
	}
	
	public Asset getAuthorImage(ResourceResolver resourceResolver, Tag tag) {
		if (tag == null) {
			return null;
		}
		return authorDao.getAuthorImage(resourceResolver, tag);
	}
	
	public List<Book> findBooks(ResourceResolver resourceResolver, List<Tag> tags) {
		return bookDao.findBooks(resourceResolver, tags);
	}
	
	public Author getAuthor(ResourceResolver resourceResolver, Page page) {
		
		Author author  = page.adaptTo(Author.class);
		
		Tag[] tags = page.getTags();

		Tag authorTag = null;
		//List<Tag> genres = new ArrayList<Tag>();
		for (int i =0; i < tags.length; i++) {
			
		    Tag tmp = tags[i];
		    LOG.info("Tag found:" + tmp.getPath());
		    if ("cookbook:authors".equals(tmp.getParent().getTagID())) {
		    	authorTag = tmp;
		    } else if ("cookbook:genres".equals(tmp.getParent().getTagID())) {
		    	Genre genre = tmp.adaptTo(Genre.class);
		    	if ( genre != null ) {
		    		author.getGenres().add(genre);
		    	}
		    }
		}
		
		Asset image = getAuthorImage(resourceResolver, authorTag);
		if ( image != null ) { 
			author.setImagePath(image.getPath());
		}
		
		return author;
	}

}
