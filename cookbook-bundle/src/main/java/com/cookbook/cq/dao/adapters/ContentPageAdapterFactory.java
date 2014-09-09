package com.cookbook.cq.dao.adapters;

import com.cookbook.cq.domain.ContentPage;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;

@Component
@Service(value = AdapterFactory.class)
@Properties({
    @Property(name = "adaptables", value = {"org.apache.sling.api.resource.Resource",
        "com.day.cq.wcm.api.Page"}),
    @Property(name = "adapter.condition", value = "Any existing resource"),
    @Property(name = "service.vendor", value = "AMS"),
    @Property(name = "service.description", value = "Service for adapting Pages to CTA Objects."),
    @Property(name = "adapters", value = {"com.cookbook.cq.domain.ContentPage"})
})
public class ContentPageAdapterFactory implements AdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        ContentPage page = new ContentPage(adaptable);
        return (AdapterType) page;
    }

}
