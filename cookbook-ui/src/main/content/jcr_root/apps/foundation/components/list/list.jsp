<%--
  Copyright 1997-2009 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  List component

--%><%@ page import="com.day.cq.wcm.api.WCMMode,
                   com.day.cq.wcm.api.components.DropTarget,
                   com.day.cq.wcm.foundation.List,
                   java.util.Iterator"%><%
%><%@include file="/libs/foundation/global.jsp"%><%

    WCMMode mode = WCMMode.fromRequest(request);

    if (mode == WCMMode.EDIT) {
        //drop target css class = dd prefix + name of the drop target in the edit config
        String ddClassName = DropTarget.CSS_CLASS_PREFIX + "pages";
        %><div class="<%= ddClassName %>"><%
    }

    if (properties.get("feedEnabled", false)) {
        %><link rel="alternate" type="application/atom+xml" title="Atom 1.0 (List)" href="<%= resource.getPath() %>.feed" /><%
    }

    // initialize the list
    %><cq:include script="init.jsp"/><%
    List list = (List)request.getAttribute("list");
    if (!list.isEmpty()) {
        String cls = list.getType();
        cls = (cls == null) ? "" : cls.replaceAll("/", "");

        %><%= list.isOrdered() ? "<ol" : "<ul" %> class="<%= xssAPI.encodeForHTML(cls) %>"><%
        Iterator<Page> items = list.getPages();
        String listItemClass = null;
        while (items.hasNext()) {
            request.setAttribute("listitem", items.next());

            if (null == listItemClass) {
                listItemClass = "first";
            } else if (!items.hasNext()) {
                listItemClass = "last";
            } else {
                listItemClass = "item";
            }
            request.setAttribute("listitemclass", " class=\"" + listItemClass + "\"");

            String script = "listitem_" + cls + ".jsp";
            %><cq:include script="<%= script %>"/><%
        }
        %><%= list.isOrdered() ? "</ol>" : "</ul>" %><%
        if (list.isPaginating()) {
            %><cq:include script="pagination.jsp"/><%
        }
    } else {
        %><cq:include script="empty.jsp"/><%
    }

    if (mode == WCMMode.EDIT) {
        %></div><%
    }
%>
