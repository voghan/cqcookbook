package com.cookbook.cq.servlets;

import com.cookbook.cq.utilities.PdfHelper;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@SlingServlet(paths = "/services/cookbook/pdf", methods = {"GET","POST"})
public class PdfServlet extends AbstractServlet{
    private static final Logger LOG = LoggerFactory
        .getLogger(PdfServlet.class);

    private static final String CONTENT_TYPE_PDF = "application/pdf";


    @Override protected void performRequest(SlingHttpServletRequest request,
        SlingHttpServletResponse response) throws IOException {

        try {
            ByteArrayOutputStream output = new PdfHelper().createPDF();
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setContentType(CONTENT_TYPE_PDF);
            response.addHeader("Content-Disposition", "attachment; filename=\""
                + "AEM-GreenLight-AUA-Symptom-Score.pdf");
            response.setContentLength(output.size());
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
            out.flush();
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }


    }
}
