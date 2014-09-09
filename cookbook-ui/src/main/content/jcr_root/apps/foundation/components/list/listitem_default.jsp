<%--
  Copyright 1997-2008 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  List component sub-script

  Draws a list item as a default link.

  request attributes:
  - {com.day.cq.wcm.foundation.List} list The list
  - {com.day.cq.wcm.api.Page} listitem The list item as a page

--%><%
%><%@ page session="false" import="com.day.cq.wcm.api.Page" import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@include file="/libs/foundation/global.jsp"%><%

    Page listItem = (Page)request.getAttribute("listitem");
    
%><li>
    <p>
        <a href="<%= listItem.getPath() %>.html" title="<%= StringEscapeUtils.escapeHtml4(listItem.getTitle()) %>"
           onclick="CQ_Analytics.record({event: 'listItemClicked', values: { listItemPath: '<%= listItem.getPath() %>' }, collect:  false, options: { obj: this }, componentPath: '<%=resource.getResourceType()%>'})"><%= StringEscapeUtils.escapeHtml4(listItem.getTitle()) %></a>
    </p>
</li>