<%@include file="/libs/foundation/global.jsp"%>
<%@page import="com.cookbook.cq.domain.location.Location,
    		com.cookbook.cq.service.StoreLocatorService,
			com.day.cq.tagging.Tag,
			com.day.cq.wcm.api.WCMMode" %><%
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
	
	<% if (WCMMode.fromRequest(request) == WCMMode.EDIT)  {%>
		<%=tag.getTagID() %>
	<% } %>
	
	<% if (location == null) { %>
		Page not setup, please verify the store tag in page properties.
	<%} else { %>
	
		<h2><%=location.getCity()%></h2>
		<p>
		<span><%=location.getAddress()%></span>
		<span><%=location.getCity()%></span>
		</p>
		<p>
		<span><%=location.getPhone()%></span>
		</p>
		<p>
		<span><%=location.getHours()%></span>
		</p>
	
	<%
	}
}

%>
</div>
