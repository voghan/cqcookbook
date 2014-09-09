package com.cookbook.cq.utilities;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Required bundle activator.  
 */
public class Activator implements BundleActivator {
    /**
     * Implements BundleActivator.start(). Registers an instance of a dictionary
     * service using the bundle context; attaches properties to the service that
     * can be queried when performing a service look-up.
     *
     * @param context
     *            the framework context for the bundle.
     */
    public void start(BundleContext context) {

    }

    /**
     * Implements BundleActivator.stop(). Does nothing since the framework will
     * automatically unregister any registered services.
     *
     * @param context
     *            the framework context for the bundle.
     */
    public void stop(BundleContext context) {
        // NOTE: The service is automatically unregistered.
    }
}
