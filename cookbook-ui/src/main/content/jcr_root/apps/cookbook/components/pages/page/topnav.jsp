<%@include file="/libs/wcm/global.jsp"%><%
%><%@ page import="com.day.cq.wcm.api.Page,
        com.day.cq.wcm.api.PageFilter,
        com.day.text.Text, java.util.Iterator" %>
<div class="topnav">
<%

    // get starting point of navigation
    Page navRootPage = currentPage.getAbsoluteParent(2);
        
    if (navRootPage == null && currentPage != null) {
    	navRootPage = currentPage;
    }
    
    if (navRootPage != null) {
    	%> <ul> <% 
        Iterator<Page> children = navRootPage.listChildren(new PageFilter(request));
        while (children.hasNext()) {
            Page child = children.next();
            %><li><%
            %><a href="<%= child.getPath() %>.html"><%=child.getTitle() %></a><%
            %></li><%
        }
        %> </ul> <% 
    }
%>
</div>
