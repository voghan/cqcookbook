package com.cookbook.cq.servlets;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;

@SlingServlet(paths = { "/services/cookbook/add-user" })
public class UserCreationServlet extends AbstractServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserCreationServlet.class);

    //?email=brian@iam.com&pword=password&lname=Vaughn&fname=Brian&gender=male

    @Override protected void performRequest(SlingHttpServletRequest request,
        SlingHttpServletResponse response) throws IOException {

        LOG.info("...performRequest");
        response.setContentType("application/json");

        String password = super.getValidParameter(request, "pword");
        String fname = super.getValidParameter(request, "fname");
        String lname = super.getValidParameter(request, "lname");
        String gender = super.getValidParameter(request, "gender");
        String jobTitle = super.getValidParameter(request, "jobTitle");
        String aboutMe = super.getValidParameter(request, "aboutMe");

        String street = super.getValidParameter(request, "street");
        String city = super.getValidParameter(request, "city");
        String state = super.getValidParameter(request, "state");
        String postalCode = super.getValidParameter(request, "postalCode");
        String country = super.getValidParameter(request, "country");

        String phoneNumber = super.getValidParameter(request, "phoneNumber");
        String mobile = super.getValidParameter(request, "mobile");
        String email = super.getValidParameter(request, "email");

        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            UserManager userManager = resourceResolver.adaptTo(UserManager.class);

            User user = userManager.createUser(email, password);

            Node node = session.getNode(user.getPath());
            Node profileNode = node.addNode("profile", "nt:unstructured");

            //profile
            profileNode.setProperty("givenName", fname);
            profileNode.setProperty("familyName", lname);
            profileNode.setProperty("gender", gender);
            profileNode.setProperty("jobTitle", jobTitle);
            profileNode.setProperty("aboutMe", aboutMe);

            //address
            profileNode.setProperty("street", street);
            profileNode.setProperty("city", city);
            profileNode.setProperty("state", state);
            profileNode.setProperty("postalCode", postalCode);
            profileNode.setProperty("country", country);

            //communication methods
            profileNode.setProperty("phoneNumber", phoneNumber);
            profileNode.setProperty("mobile", mobile);
            profileNode.setProperty("email", email);

            profileNode.setProperty("sling:resourceType","cq/security/components/profile");

            Group group = (Group) userManager.getAuthorizable("cookbook");
            group.addMember(user);

            session.save();

        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
        }

        response.getWriter().write("Success?");
    }
}
