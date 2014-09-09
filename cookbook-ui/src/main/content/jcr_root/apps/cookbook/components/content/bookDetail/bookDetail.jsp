<%--

  Book Detail component.

  

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%><%

	String imagePath = properties.get("image/fileReference", "missingContent.png");

%>
<div class="bookDetail">
	<div class="book">
		<img src="<%=imagePath%>" alt="bookCover" />
		<p><span class="label">Title:</span> <cq:text property="title"/></p>
		<p><span class="label">Author:</span> <cq:text property="author"/></p>
		<p><span class="label">Publisher:</span> <cq:text property="publisher"/></p>
		<p><span class="label">ISDN:</span> <cq:text property="isdn"/></p>
		<p><span class="label">Year:</span> <cq:text property="year"/></p>
	</div>
	
	<div class="description">
		<p><span class="label">Description:</span> <cq:text property="descr"/></p>
	</div>
</div>
