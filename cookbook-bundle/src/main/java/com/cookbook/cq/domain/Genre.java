package com.cookbook.cq.domain;

import com.day.cq.tagging.Tag;
import org.apache.sling.api.resource.Resource;

public class Genre extends BaseAdaptable {

	private static final long serialVersionUID = -6602264934105603364L;
	
	private String title;
	
	public transient Resource baseResource = null;

	public Genre(Object o) {
        if (o instanceof Resource) {
            this.baseResource = (Resource)o;
        }
        else if (o instanceof Tag) {
            Resource r = ((Tag)o).adaptTo(Resource.class);
            if (r != null) {
                this.baseResource = r;
            }
        }
        buildGenre();
    }
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	Resource getResource() {
		return baseResource;
	}

	private void buildGenre() {
		this.title = getPropertyAsString("jcr:title");
	}

}
