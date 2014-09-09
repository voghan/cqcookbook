<%@include file="/libs/foundation/global.jsp"%>
<%@page import="com.cookbook.cq.domain.Author,
    com.cookbook.cq.domain.Book" %><%

    Book book = new Book(currentPage);

	Author author = new Author(currentPage);
%>
<%=book.toString()%>
<%=author.toString()%>
