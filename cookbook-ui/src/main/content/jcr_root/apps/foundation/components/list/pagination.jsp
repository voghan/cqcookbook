<%@ page session="false"  import="com.day.cq.wcm.foundation.List" %>
<%@include file="/libs/foundation/global.jsp"%><%

List list = (List)request.getAttribute("list");

%><div class="pagination"><%
    if (list.getPreviousPageLink() != null) {
        %><div class="previous"><%
            %><a href="<%= list.getPreviousPageLink() %>" onclick="CQ_Analytics.record({event: 'listPreviousPage', values: { listPageStart: '<%= list.getPageStart() %>' }, collect:  false, options: { obj: this }, componentPath: '<%=resource.getResourceType()%>'})">&laquo; Previous</a><%
        %></div><%
    }
    if (list.getNextPageLink() != null) {
        %><div class="next"><%
            %><a href="<%= list.getNextPageLink() %>" onclick="CQ_Analytics.record({event: 'listNextPage', values: { listPageStart: '<%= list.getPageStart() %>' }, collect:  false, options: { obj: this }, componentPath: '<%=resource.getResourceType()%>'})">Next &raquo;</a><%
        %></div><%
    }
%></div>