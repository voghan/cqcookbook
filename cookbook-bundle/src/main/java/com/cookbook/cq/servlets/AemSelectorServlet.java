package com.cookbook.cq.servlets;

import com.cookbook.cq.domain.selector.AemSelectorItem;
import com.cookbook.cq.domain.selector.AemSelectorItems;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: bvaughn
 * Date: 9/8/14
 */
@SlingServlet(paths = {"/services/cookbook/selector"})
public class AemSelectorServlet extends AbstractServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AemSelectorServlet.class);

    @Override
    protected void performRequest(SlingHttpServletRequest request,
        SlingHttpServletResponse response)
        throws IOException {

        response.setContentType("application/json; charset=UTF-8");

        AemSelectorItems<AemSelectorItem> mailListResponse = new AemSelectorItems();

        try {

            List<AemSelectorItem> newsletters = buildList();

            mailListResponse.setData(newsletters);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        response.getWriter().write(mailListResponse.toString());
    }

    private List<AemSelectorItem> buildList() {
        List<AemSelectorItem> items = new ArrayList<AemSelectorItem>();

        items.add(new AemSelectorItem("--gold","Gold"));
        items.add(new AemSelectorItem("--maroon","Maroon"));
        items.add(new AemSelectorItem("--teal","Teal"));

        return items;
    }
}
