package com.cookbook.cq.service.impl;

import com.cookbook.cq.dao.AuthorDao;
import com.cookbook.cq.dao.BookDao;
import com.cookbook.cq.dao.impl.AuthorDaoImpl;
import com.cookbook.cq.dao.impl.BookDaoImpl;
import com.cookbook.cq.domain.Author;
import com.cookbook.cq.domain.Book;
import com.cookbook.cq.domain.Genre;
import com.cookbook.cq.domain.email.EmailContent;
import com.cookbook.cq.domain.email.FileAttachment;
import com.cookbook.cq.service.CookbookService;
import com.day.cq.dam.api.Asset;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.felix.scr.annotations.*;
import org.apache.felix.scr.annotations.Property;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.jcr.*;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * OSGi service implementation that can be accessed from within AEM.
 * <p/>
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

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private MessageGatewayService gatewayService;

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

        Author author = page.adaptTo(Author.class);

        Tag[] tags = page.getTags();

        Tag authorTag = null;
        //List<Tag> genres = new ArrayList<Tag>();
        for (int i = 0; i < tags.length; i++) {

            Tag tmp = tags[i];
            LOG.info("Tag found:" + tmp.getPath());
            if ("cookbook:authors".equals(tmp.getParent().getTagID())) {
                authorTag = tmp;
            } else if ("cookbook:genres".equals(tmp.getParent().getTagID())) {
                Genre genre = tmp.adaptTo(Genre.class);
                if (genre != null) {
                    author.getGenres().add(genre);
                }
            }
        }

        Asset image = getAuthorImage(resourceResolver, authorTag);
        if (image != null) {
            author.setImagePath(image.getPath());
        }

        return author;
    }

    public void writeNode(String parentNode, String nodeTitle, String data)
        throws LoginException, RepositoryException {

        ResourceResolver resourceResolver = null;

        try {
            resourceResolver =
                resourceResolverFactory.getServiceResourceResolver(
                    null);
            Session session = resourceResolver.adaptTo(Session.class);

            Node node = session.getNode(parentNode);
            ValueFactory valueFactory = session.getValueFactory();
            Value contentValue = valueFactory.createValue(data);
            Node fileNode = node.addNode(nodeTitle, "nt:file");
            Node resNode = fileNode.addNode("jcr:content", "nt:unstructured");

            resNode.setProperty("jcr:data", contentValue);
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(lastModified.getTimeInMillis());
            resNode.setProperty("jcr:lastModified", lastModified);
            session.save();
        } finally {
            if (resourceResolver != null
                && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
    }

    public boolean sendEmail(EmailContent emailContent) throws MessagingException, EmailException {

        HtmlEmail email = new HtmlEmail();
        String htmlContent = emailContent.getBody();
        List<InternetAddress> emailRecipients = new ArrayList<InternetAddress>();
        if (emailContent.getAddresses().contains(",")) {
            String[] items = emailContent.getAddresses().split(",");
            for (String str : items) {
                emailRecipients.add(new InternetAddress(str));
            }
        } else {
            emailRecipients.add(new InternetAddress(emailContent.getAddresses()));
        }

        email.setFrom(emailContent.getFromUser());
        email.setTo(emailRecipients);
        email.setSubject(emailContent.getSubject());
        email.setHtmlMsg(htmlContent);

        MessageGateway<HtmlEmail> messageGateway = this.gatewayService.getGateway(HtmlEmail.class);
        messageGateway.send(email);

        return true;
    }

    public boolean sendEmailWithAttachment(EmailContent emailContent, List<FileAttachment> attachments)
        throws MessagingException, EmailException {

        MultiPartEmail email = new MultiPartEmail();
        String htmlContent = emailContent.getBody();
        List<InternetAddress> emailRecipients = new ArrayList<InternetAddress>();
        if (emailContent.getAddresses().contains(",")) {
            String[] items = emailContent.getAddresses().split(",");
            for (String str : items) {
                emailRecipients.add(new InternetAddress(str));
            }
        } else {
            emailRecipients.add(new InternetAddress(emailContent.getAddresses()));
        }

        email.setFrom(emailContent.getFromUser());
        email.setTo(emailRecipients);
        email.setSubject(emailContent.getSubject());

        MimeMultipart mimeMultipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");
        mimeMultipart.addBodyPart(messageBodyPart);

        for (FileAttachment attachment : attachments) {
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(attachment.getDataSource()));
            messageBodyPart.setFileName(attachment.getFileName());
            mimeMultipart.addBodyPart(messageBodyPart);

        }
        email.addPart(mimeMultipart);

        MessageGateway<MultiPartEmail> messageGateway = this.gatewayService.getGateway(MultiPartEmail.class);
        messageGateway.send(email);

        return true;
    }

    public void writePageReverseReplication(Object form)
        throws LoginException, RepositoryException, IOException {

        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(null);

            String parentNode = "/content/usergenerated/cookbook/en/forms";

            String nodeTitle = UUID.randomUUID().toString();

            Session session = resourceResolver.adaptTo(Session.class);
            Node node = session.getNode(parentNode);

            Node pageNode = node.addNode(nodeTitle, "cq:Page");
            Node contentNode = pageNode.addNode("jcr:content", "cq:PageContent");


            contentNode.setProperty("hideInNav", true);

            //Properties needed for reverse replication
            contentNode.setProperty("cq:lastModified", Calendar.getInstance());
            contentNode.setProperty("cq:lastModifiedBy", session.getUserID());
            contentNode.setProperty("cq:distribute", true);

            session.save();
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally
        {
            if (resourceResolver != null
                && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }

    }

}
