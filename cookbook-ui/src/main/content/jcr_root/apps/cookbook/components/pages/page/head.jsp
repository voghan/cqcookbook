<%--
  Copyright 1997-2010 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Default head script.

  Draws the HTML head with some default content:
  - includes the WCML init script
  - includes the head libs script
  - includes the favicons
  - sets the HTML title
  - sets some meta data

  ==============================================================================

--%><%@include file="/apps/cookbook/global.jsp" %>
<%@ page import="com.day.cq.wcm.api.WCMMode,
                 org.apache.commons.lang3.StringEscapeUtils" %><%
    String favIcon = currentDesign.getPath() + "/favicon.ico";
    if (resourceResolver.getResource(favIcon) == null) {
        favIcon = null;
    }
    
    pageContext.setAttribute("wcmmode", WCMMode.fromRequest(slingRequest));
	pageContext.setAttribute("wcmmodeDisabled", WCMMode.DISABLED);

    boolean disableCache = properties.get("disableCache", false);

    if (disableCache) {
        response.setHeader("Dispatcher", "no-cache");
    }
%>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>

    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>    
    <cq:include script="/libs/wcm/mobile/components/simulator/simulator.jsp"/>
    <cq:include script="headlibs.jsp"/>
    
    <% if (favIcon != null) { %>
    <link rel="icon" type="image/vnd.microsoft.icon" href="<%= favIcon %>"/>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="<%= favIcon %>"/>
    <% } %>
    <title><%= currentPage.getTitle() == null ? StringEscapeUtils.escapeHtml4(currentPage.getName()) : StringEscapeUtils.escapeHtml4(currentPage.getTitle()) %></title>
</head>
