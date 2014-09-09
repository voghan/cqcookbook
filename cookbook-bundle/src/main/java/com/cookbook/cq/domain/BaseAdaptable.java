package com.cookbook.cq.domain;

import org.apache.sling.api.adapter.Adaptable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * Convenience class for overriding toString and the required adaptable
 * methods.
 * 
 * Adaptable methods should be abstracted to a more specific class.
 */
public abstract class BaseAdaptable extends Base implements Adaptable {
	private static final long serialVersionUID = 1881783101112267L;
    
    public <AdapterType> AdapterType adaptTo(Class<AdapterType> type) {
        return getResource().adaptTo(type);
    }
    
    abstract Resource getResource();
    
    public ValueMap getProperties() {
        ValueMap properties = null;

        if (getResource() != null) {
            properties = getResource().adaptTo(ValueMap.class);
        }

        return properties;
    }

    public String getPropertyAsString(String propertyName) {
        ValueMap properties = getProperties();
        String property = null;
        if (properties != null) {
            property = properties.get(propertyName, null);
        }

        return property;
    }
}
