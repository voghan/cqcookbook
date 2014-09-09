package com.cookbook.cq.service.impl;

import com.cookbook.cq.service.AemPropertyService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Dictionary;

@Service
@Component(metatype = true)
public class AemPropertyServiceImpl implements AemPropertyService {
	private static final Logger LOG = LoggerFactory.getLogger(AemPropertyServiceImpl.class);

	//com.cookbook.cq.common
	private static final String common_config = "com.cookbook.cq.common";
		
	//ws.store.endpoint
	private String wsStoreEndpoint;
	private static final String WS_STORE_ENDPOINT = "ws.store.endpoint";
	
	//com.cookbook.cq
	private static final String env_config = "com.cookbook.cq";
		
	//cq.environemnt
	private String environemnt;
	private static final String CQ_ENVIRONMENT = "cq.environemnt";
	
	//ws.store.baseUrl
	private String storeBaseUrl;
	private static final String WS_STORE_BASEURL = "ws.store.baseUrl";

    @Reference
    private ConfigurationAdmin configurationAdmin;

    @SuppressWarnings("unchecked")
    public Dictionary<String, String> getProperty(String configuration) {
        Dictionary<String, String> dictionary = null;
        try {
            Configuration config = configurationAdmin.getConfiguration(configuration);
            dictionary = config.getProperties();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return dictionary;
    }


    public String getProperty(String config, String property) {
        String prop = "";

        Dictionary dictionary = getProperty(config);

        if (dictionary != null) {
            prop = (String) dictionary.get(property);
        }

        return prop;
    }
}
