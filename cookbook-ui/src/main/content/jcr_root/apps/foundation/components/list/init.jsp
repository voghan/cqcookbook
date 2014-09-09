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

  Creates a {com.day.cq.wcm.foundation.List} list from the request and sets
  it as a request attribute.

--%><%
%><%@page import="com.day.cq.wcm.api.PageFilter, com.day.cq.wcm.foundation.List" %><%
%><%@include file="/libs/foundation/global.jsp"%><%
    
List list = new List(slingRequest, new PageFilter());
request.setAttribute("list", list);

%>
