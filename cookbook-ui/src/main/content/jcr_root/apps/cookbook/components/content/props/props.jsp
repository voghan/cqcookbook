<%--

  Book Detail component.

  

--%><%@ page session="false" 
import="com.cookbook.cq.service.AemPropertyService,
	org.apache.commons.lang3.StringEscapeUtils"
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%><%

	AemPropertyService service = sling.getService(AemPropertyService.class);
    String storeEndpoint = service.getProperty("com.cookbook.cq.common","ws.store.endpoint");
    String environment = service.getProperty("com.cookbook.cq","cq.environemnt");
    String storeBaseUrl = service.getProperty("com.cookbook.cq","ws.store.baseUrl");

%>
<div class="props">
	<p><span class="label">Store Endpoint:</span> <%=StringEscapeUtils.escapeHtml4(storeEndpoint) %></p>
	<p><span class="label">Environment:</span> <%=StringEscapeUtils.escapeHtml4(environment) %></p>
	<p><span class="label">Store Base Url:</span> <%=StringEscapeUtils.escapeHtml4(storeBaseUrl) %></p>
</div>

