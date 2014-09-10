package com.cookbook.cq.domain;

import com.cookbook.cq.utilities.ImageHelper;
import com.cookbook.cq.utilities.StringUtil;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;

public class ContentPage extends BaseAdaptable {
    private static final long serialVersionUID = -3665528673213677271L;

    public transient Resource baseResource = null;

    private String title;
    private String path;
    private String redirect;
    private String url;
    private String target = "";
    private String pageTitle;
    private String description = "";
    private String imagePath;
    private String contentTitle;
    private String contentBody;
    private String contentLinkText;

    public ContentPage(Object o) {
        if (o instanceof Resource) {
            this.baseResource = (Resource) o;
        } else if (o instanceof Page) {
            Resource r = ((Page) o).adaptTo(Resource.class);
            if (r != null) {
                this.baseResource = r;
            }
        }
        buildPage();
    }

    @Override Resource getResource() {
        return baseResource;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getRedirect() {
        return redirect;
    }

    public String getUrl() {
        return url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTarget() {
        return target;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public String getContentBody() {
        return contentBody;
    }

    public String getContentLinkText() {
        return contentLinkText;
    }

    private void buildPage() {
        this.path = getResource().getPath();
        this.title = getPropertyAsString("jcr:content/jcr:title");
        this.pageTitle = getPropertyAsString("jcr:content/pageTitle");
        this.description = getPropertyAsString("jcr:content/jcr:description");
        String imagePath = getPropertyAsString("jcr:content/image/fileReference");
        this.imagePath = ImageHelper.getImagePath(imagePath, getResource().getResourceResolver());
        this.redirect = getPropertyAsString("jcr:content/redirectTarget");

        this.contentTitle = getPropertyAsString("jcr:content/jcr:content_title");
        this.contentBody = getPropertyAsString("jcr:content/jcr:content_body");
        this.contentLinkText = getPropertyAsString("jcr:content/jcr:content_link_text");


        if (redirect != null && redirect.trim().length() > 0) {
            this.url = redirect;
            this.target = "target=\"_blank\"";
        } else {
            this.url = path;
        }

        if (url.startsWith("/content/cookbook")) {
            this.url += ".html";
            this.target = "";
        }

        if (StringUtil.isUnassigned(pageTitle)) {
            pageTitle = title;
        }
    }

}
