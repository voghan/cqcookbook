<%@include file="/libs/foundation/global.jsp"%>
<%@page import="com.cookbook.cq.domain.Book,
    		com.cookbook.cq.service.CookbookService,
			com.day.cq.dam.api.Asset,
			com.day.cq.tagging.Tag,
			java.util.List" %><%
%>
<div class="authorDetails">
<%

CookbookService service = sling.getService(CookbookService.class);

Tag[] tags = currentPage.getTags();

Tag tag = null;
for (int i =0; i < tags.length; i++) {

    Tag tmp = tags[i];
    if ("cookbook:authors".equals(tmp.getParent().getTagID())) {
        tag = tmp;
    }
}

String imagePath = "";

if ( tag == null) {
	%> Page not setup, please add author tag in page properties. <%
} else {
	
	Asset image = service.getAuthorImage(resourceResolver, tag);
	if ( image != null ) { 
		imagePath = image.getPath();
	}
    %>
    <h2><%=tag.getTitle()%></h2>

    <img src="<%=imagePath%>" alt="authorImage" />
    
    <h3>About the author:</h3>
    
    <cq:text property="aboutAuthor"/>
    
    <h3>Books</h3>

    <%

	List<Book> books = service.findBooks(resourceResolver, tag);

	for (Book book: books) {
	%>
	
		<p><a href="<%=book.getPath()+".html"%> "><%=book.getTitle()%></a></p>
	<%
	}
}

%>
</div>
