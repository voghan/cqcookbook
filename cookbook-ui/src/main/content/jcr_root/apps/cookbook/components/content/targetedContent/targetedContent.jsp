<%@ page import="com.cookbook.cq.domain.ContentPage" %>
<%@ page import="com.day.cq.wcm.api.Page" %>
<%@include file="/apps/cookbook/global.jsp"%>
<%

	String targetPath = properties.get("targetPage", "");
	Page target = pageManager.getPage(targetPath);

	if ( target != null ) {
	    ContentPage content = target.adaptTo(ContentPage.class);

	  %>
        <div class="targeted--content">
            <h1 class='targeted--content__header'>
                <%=content.getContentTitle()%>
            </h1>
            <div class="targeted--content__content">
                <%=content.getContentBody()%>
                <a class='button--gold' href="<%=content.getUrl()%>" <%=content.getTarget()%>><%=content.getContentLinkText()%></a>
            </div>
        </div>
	  <%
	} else {
      %>
      <cq:include script="empty.jsp"/>
      <%
  }

%>
