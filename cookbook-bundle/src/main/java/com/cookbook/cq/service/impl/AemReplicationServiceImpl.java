package com.cookbook.cq.service.impl;

import com.cookbook.cq.service.AemReplicationService;
import com.day.cq.replication.*;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

public class AemReplicationServiceImpl implements AemReplicationService {
    private static final Logger LOG = LoggerFactory.getLogger(AemReplicationServiceImpl.class);
    @Reference
	private Replicator replicator;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	/**
	 * Reverse replication on publisher
	 */
	public void reverseReplicate(String path) throws LoginException,
			ReplicationException {
		ResourceResolver resourceResolver = null;

		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(null);
			final ReplicationOptions replicationOptions = new ReplicationOptions();

			replicationOptions.setFilter(new AgentFilter() {
				public boolean isIncluded(Agent agent) {
					return agent.getConfiguration().isTriggeredOnDistribute();
				}
			});
			replicationOptions.setSynchronous(true);

			Session session = resourceResolver.adaptTo(Session.class);
			replicator.replicate(session, ReplicationActionType.ACTIVATE, path,
					replicationOptions);
		} finally {
			if (resourceResolver != null && resourceResolver.isLive()) {
				resourceResolver.close();
			}
		}
	}

	/**
	 * Replicate on author
	 */
	public void replicate(String path) throws LoginException {
		ResourceResolver resourceResolver = null;

		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(null);

			Session session = resourceResolver.adaptTo(Session.class);
			replicator.replicate(session, ReplicationActionType.ACTIVATE, path);

		} catch (ReplicationException e) {
        LOG.warn(e.getMessage(), e);
		} finally {
			if (resourceResolver != null && resourceResolver.isLive()) {
				resourceResolver.close();
			}
		}
	}

}
