package com.cookbook.cq.components;

import com.adobe.cq.sightly.WCMUse;
import com.cookbook.cq.domain.Book;
import com.cookbook.cq.service.CookbookService;
import com.day.cq.dam.api.Asset;
import com.day.cq.tagging.Tag;

import java.util.List;

/**
 * User: bvaughn
 * Date: 9/12/14
 */
public class AuthorDetails extends WCMUse {

    private CookbookService service;

    private Tag authorTag;
    private String title = "";
    private String imagePath = "";
    private List<Book> books;
    private String aboutAuthor = "";

    @Override public void activate() throws Exception {
        this.service = getSlingScriptHelper().getService(CookbookService.class);

        buildAuthorDetails();
    }


    public void buildAuthorDetails() {

        this.aboutAuthor = getProperties().get("aboutAuthor", "");

        Tag[] tags = getCurrentPage().getTags();

        for (int i =0; i < tags.length; i++) {

            Tag tmp = tags[i];
            if ("cookbook:authors".equals(tmp.getParent().getTagID())) {
                this.authorTag = tmp;
            }
        }

        if ( authorTag != null) {
            this.title = authorTag.getTitle();

            Asset image = service.getAuthorImage(getResourceResolver(), authorTag);
            if (image != null) {
                imagePath = image.getPath();
            }

            books = service.findBooks(getResourceResolver(), authorTag);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getAboutAuthor() {
        return aboutAuthor;
    }
}
