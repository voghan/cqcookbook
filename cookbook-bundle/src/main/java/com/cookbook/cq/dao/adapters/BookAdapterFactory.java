package com.cookbook.cq.dao.adapters;

import com.cookbook.cq.domain.Book;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;

@Component
@Service(value=AdapterFactory.class)
@Properties({
	@Property(name = "adaptables", value = { "org.apache.sling.api.resource.Resource",
												"com.day.cq.wcm.api.Page" }),
	@Property(name = "adapter.condition", value="Any existing resource"),
	@Property(name = "service.vendor", value = "Digitaria"),
	@Property(name = "service.description", value = "Service for adapting Sling Resources to Book Objects.") ,
	@Property(name = "adapters", value = { "com.cookbook.cq.domain.Book" })
})
public class BookAdapterFactory implements AdapterFactory {

	/**
	 * Adapter is required to convert a Page to an Object
	 */
	@SuppressWarnings("unchecked")
	public <AdapterType> AdapterType getAdapter(final Object adaptable,
			Class<AdapterType> type) {
		Book author = new Book(adaptable);
		return (AdapterType)author;
	}

}
