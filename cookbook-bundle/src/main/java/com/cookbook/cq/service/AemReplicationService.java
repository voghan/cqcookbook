package com.cookbook.cq.service;

import com.day.cq.replication.ReplicationException;
import org.apache.sling.api.resource.LoginException;

public interface AemReplicationService {
	
	void reverseReplicate(String path) throws LoginException, ReplicationException;
	
	void replicate(String path) throws LoginException;

}
