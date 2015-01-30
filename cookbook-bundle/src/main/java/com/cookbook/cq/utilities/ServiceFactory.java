package com.cookbook.cq.utilities;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class ServiceFactory {
	
	@SuppressWarnings("unchecked")
	public static Object getService(String className) {
        //Boilerplate code to get a service from a pojo
        BundleContext bundleContext = FrameworkUtil.getBundle(Activator.class).getBundleContext();
        ServiceReference factoryRef = bundleContext.getServiceReference(className);
        return bundleContext.getService(factoryRef);
    }

}
