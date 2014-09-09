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
%><%@ page session="false" 
import="com.cookbook.cq.domain.Author,
		com.cookbook.cq.domain.Genre,
		com.cookbook.cq.service.CookbookService,
    	com.day.cq.wcm.api.Page,
    	org.apache.commons.lang3.StringEscapeUtils"%>
<%@include file="/libs/foundation/global.jsp"%><%

    Page listItem = (Page)request.getAttribute("listitem");

	CookbookService service = sling.getService(CookbookService.class);
	Author author = service.getAuthor(resourceResolver, listItem);
	
	String genres = "";
	for (Genre genre : author.getGenres()) {
		genres += " " + genre.getTitle(); 
	}
    
%><li>
    <div>
    	<a href="<%= author.getPath() %>.html" title="<%= StringEscapeUtils.escapeHtml4(author.getAuthor()) %>">
    	<img src="<%=author.getImagePath()%>/jcr:content/renditions/cq5dam.thumbnail.48.48.png" alt="authorImage" />
        <%= StringEscapeUtils.escapeHtml4(author.getAuthor())%></a>
        <div class="genres"><%=genres%></div>
    </div>
</li>
