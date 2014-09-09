<%@include file="/libs/foundation/global.jsp"%>
<%@page import="com.cookbook.cq.domain.location.Location,
    		com.cookbook.cq.service.StoreLocatorService,
			com.day.cq.tagging.Tag" %><%
%>
<div class="storeDetails">
<%

StoreLocatorService service = sling.getService(StoreLocatorService.class);

Tag[] tags = currentPage.getTags();

Tag tag = null;
for (int i =0; i < tags.length; i++) {

    Tag tmp = tags[i];
    if (tmp.getTagID().startsWith("cookbook:stores/")) {
        tag = tmp;
    }
}


if ( tag == null) {
	%> Page not setup, please add store tag in page properties. <%
} else {
	
	String storeId = tag.getName();
	Location location = service.getLocation(storeId);
	
	%>

	<% if (location != null) { %>

		<%=location.toString()%>
	
	<%
	}
}

%>
</div>
