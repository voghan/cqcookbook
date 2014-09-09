<%--

  Book Filter component.

  

--%><%
%><%@include file="/libs/foundation/global.jsp"%>
<%@page import="com.day.cq.tagging.Tag,
				com.day.cq.tagging.TagManager,
				java.util.Iterator" %>
<%@page session="false" %>
<%

TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

Tag authorParentTag = tagManager.resolve("cookbook:authors");
Iterator<Tag> authorTags = authorParentTag.listChildren();

Tag gernreParentTag = tagManager.resolve("cookbook:genres");
Iterator<Tag> genreTags = gernreParentTag.listChildren();
%>

<div class="filterBooks">

	Authors:
	<select class="authorFilter">
      	<option value="<%=authorParentTag.getTagID()%>"></option>
      	<% while(authorTags.hasNext()) {  %>
      		<% Tag authorTag = authorTags.next(); %>
      	<option value="<%=authorTag.getTagID() %>"><%=authorTag.getTitle()%></option>
		<% } %>
    </select>

	Genres:
    <select class="genreFilter">
      <option value="<%=gernreParentTag.getTagID()%>"></option>
      	<% while(genreTags.hasNext()) {  %>
      		<% Tag genreTag = genreTags.next(); %>
      	<option value="<%=genreTag.getTagID() %>"><%=genreTag.getTitle()%></option>
		<% } %>
    </select>

</div>
<div class="filterResults">

	<ul class="book-results-list">
    </ul>
    
    <ul style="display: none;">
        <li id="book-template" class="results-book">
            <p class="results-book-title">
            	<a class="results-book_url" href="#" ><span data-selector="bookTitle"></span></a> 
            	by <span data-selector="bookAuthor"></span>
            </p>
        </li>
    </ul>


</div>

<script>

$(document).ready(function () {
	booksFilter.init();
	$('.genreFilter').change();
});
</script>
