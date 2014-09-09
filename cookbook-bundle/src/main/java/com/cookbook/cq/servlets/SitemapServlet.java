package com.cookbook.cq.servlets;

import com.cookbook.cq.domain.ContentPage;
import com.cookbook.cq.service.AemPropertyService;
import com.cookbook.cq.utilities.StringUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@SlingServlet(paths = "/services/sitemap.xml", methods = {"GET","POST"})
public class SitemapServlet extends AbstractServlet {
    private static final long serialVersionUID = -536784266512058118L;

    private static final Logger log = LoggerFactory
        .getLogger(SitemapServlet.class);

    private String URLBEGIN = "<url>";
    private String URLEND = "</url>";

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Reference
    private SlingRepository repository;

    @Reference
    private AemPropertyService propertyService;

    @Override protected void performRequest(SlingHttpServletRequest request,
        SlingHttpServletResponse response) throws IOException {
        log.info("...professing request");
        response.setHeader("Content-Type", "text/xml");
        response.getWriter().print(generateSitemap(request));
    }

    private String generateSitemap(SlingHttpServletRequest request) {
        String rootPath = propertyService.getProperty("com.ams.corp.aem.common","rootPath");
        //int maxDepth = Integer.parseInt(propertyService.getProperty("com.ams.corp.aem","maxDepth"));

        String hostPath = "http://" + request.getServerName().replaceAll("www","");
        if (request.getServerPort()!=80) {
            hostPath = hostPath + ":" + Integer.toString(request.getServerPort());
        }

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";

        Session session = null;
        try{
            session = repository.login();
            Page rootPage = request.getResourceResolver().adaptTo(PageManager.class).getPage(rootPath);

            // rootpage
            xml = xml + generateUrlNode(rootPath, rootPage, hostPath, "");

            // generate children
            xml = xml + childUrlNodes(rootPath, rootPage, hostPath, ".html");
        }
        catch ( Exception e ) {
            e.printStackTrace();
            log.error( "Fatal error while getting content", e );
        }
        finally { // release all the resources associated to the session
            if (session != null) {
                session.logout();
            }
        }

        xml = xml + "</urlset>";

        return xml;
    }

    private String childUrlNodes(String rootPath, Page page, String hostPath, String extension) {
        String childXml = "";
        // filter the pages to exclude hidden pages
        PageFilter filter = new PageFilter(false, true);

        Iterator<Page> pagesIter = page.listChildren(filter);

        while (pagesIter.hasNext()) {
            Page child = (Page) pagesIter.next();

            ContentPage ctaPage = child.adaptTo(ContentPage.class);
            if (StringUtil.isUnassigned(ctaPage.getRedirect())) {
                childXml = childXml + generateUrlNode(rootPath, child, hostPath, extension);

                Iterator<Page> childIter = child.listChildren(filter);
                if (childIter.hasNext()) {
                    childXml = childXml + childUrlNodes(rootPath, child, hostPath, extension);
                }
            }
        }

        return childXml;
    }

    private String generateUrlNode(String rootPath, Page page, String hostPath, String extension) {
        String pagePath = page.getPath();
        pagePath = pagePath.replace(rootPath, "");
        String urlXml = URLBEGIN + "<loc>" + hostPath + pagePath + extension + "</loc>";

        Date lastmod = page.getLastModified().getTime();

        urlXml = urlXml + "<lastmod>" + df.format(lastmod) + "</lastmod>";

        urlXml += "<priority>.5</priority><changefreq>monthly</changefreq>";

        return urlXml + URLEND;
    }

}
