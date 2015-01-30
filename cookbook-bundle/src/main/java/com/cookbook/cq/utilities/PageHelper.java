package com.cookbook.cq.utilities;

import com.cookbook.cq.service.AemPropertyService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.designer.Design;
import com.day.cq.wcm.api.designer.Designer;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: bvaughn
 * Date: 5/27/14
 */
public class PageHelper {
    private static final Logger LOG = LoggerFactory.getLogger(PageHelper.class);

    public static List<Page> findChildren(String path, PageManager pageManager) {

        List<Page> pages =  new ArrayList<Page>();

        Page p = pageManager.getPage(path);
        Iterator<Page> iterator = p.listChildren();

        while (iterator.hasNext()) {
            pages.add(iterator.next());
        }

        return pages;
    }

    public static List<Page> convertPages(String[] pagesStr, PageManager pageManager) {
        List<Page> pages =  new ArrayList<Page>();

        for (String page : pagesStr) {
            Page p = pageManager.getPage(page);
            if ( p != null ) {
                pages.add(p);
            }
        }

        return pages;
    }

    public static Design getHomepageDesign(Page currentPage, ResourceResolver resourceResolver) {
        Design design = null;
        try {
            String path = getApplicationPath(currentPage).getPath() + "/home";

            Page page = resourceResolver.adaptTo(PageManager.class).getPage(path);

            design = resourceResolver.adaptTo(Designer.class).getDesign(page);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        return design;
    }

    public static Page getApplicationPath(Page currentPage) {
        AemPropertyService propertyService = (AemPropertyService) ServiceFactory.getService(AemPropertyService.class.getName());

        String appDepth = propertyService.getProperty("com.ams.corp.aem.common","application.depth");

        LOG.info("Application depth:" + appDepth);

        return currentPage.getAbsoluteParent(Integer.parseInt(appDepth));
    }
}
