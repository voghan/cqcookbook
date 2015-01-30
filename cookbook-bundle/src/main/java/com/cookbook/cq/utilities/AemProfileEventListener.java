package com.cookbook.cq.utilities;

import com.cookbook.cq.service.AemReplicationService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

public class AemProfileEventListener implements EventListener {
	private static final Logger LOG = LoggerFactory.getLogger(AemProfileEventListener.class);

	@Reference
    private SlingSettingsService slingSettingsService;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private com.day.cq.replication.Replicator replicator;

    private Session session;
    private ObservationManager observationManager;
    
    @Reference
    private SlingRepository repository;
    
    @Reference
    private AemReplicationService replicationService;

    protected void activate(ComponentContext context)  throws Exception {
        session = repository.loginService(null, null);

        if (slingSettingsService.getRunModes().contains("author") &&
            repository.getDescriptor(Repository.OPTION_OBSERVATION_SUPPORTED).equals("true")) {
            observationManager = session.getWorkspace().getObservationManager();
            final String[] types = { "rep:User" };
            final String path = "/home/users/cookbook";
            observationManager.addEventListener(this, Event.NODE_ADDED, path, true, null, types, false);
            observationManager.addEventListener(this, Event.NODE_REMOVED, path, true, null, types, false);
            observationManager.addEventListener(this, Event.PERSIST, path, true, null, types, false);
            observationManager.addEventListener(this, Event.PROPERTY_ADDED, path, true, null, types, false);
            observationManager.addEventListener(this, Event.PROPERTY_CHANGED, path, true, null, types, false);
            observationManager.addEventListener(this, Event.PROPERTY_REMOVED, path, true, null, types, false);
        }

    }

    protected void deactivate(ComponentContext componentContext) throws RepositoryException {

        if(observationManager != null) {
            observationManager.removeEventListener(this);
        }
        if (session != null) {
            session.logout();
            session = null;
        }
    }

    public void onEvent(EventIterator eventIterator) {

        LOG.info("... event occurred ");
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(null);
            while (eventIterator.hasNext()) {

                LOG.info("... event - " + eventIterator.nextEvent().getPath());
                String path = eventIterator.nextEvent().getPath();
                Resource r = resourceResolver.resolve(path);
                replicationService.replicate(r.getParent().getPath());
            }
        } catch (RepositoryException e) {
            LOG.warn(e.getMessage(), e);
        } catch (LoginException e) {
            LOG.warn(e.getMessage(), e);
        } finally {
            if (resourceResolver != null && resourceResolver.isLive() ) {
                resourceResolver.close();
            }
        }
    }

}
