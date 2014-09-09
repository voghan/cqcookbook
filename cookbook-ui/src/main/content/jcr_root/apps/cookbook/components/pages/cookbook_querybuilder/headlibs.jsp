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

  Includes the scripts and css to be included in the head tag

  ==============================================================================

--%><%@include file="/libs/foundation/global.jsp" %><%
%><cq:includeClientLib categories="apps.geometrixx-main"/><%
    currentDesign.writeCssIncludes(pageContext); %>
<cq:includeClientLib categories="cq.dam.assetshare, cq.dam.edit, cq.jquery"/>
<link href="/etc/designs/geometrixx/ui.widgets.css" rel="stylesheet" type="text/css">