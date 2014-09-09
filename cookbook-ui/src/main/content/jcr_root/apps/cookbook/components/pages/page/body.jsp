<!--  body.jsp -->
<%@page import="org.apache.sling.settings.SlingSettingsService"%>
<%@include file="/apps/cookbook/global.jsp" %>
<%
    StringBuffer cls = new StringBuffer();
    for (String c: componentContext.getCssClassNames()) {
        cls.append(c).append(" ");
    }
%>

<body id="uxBody">

<cq:include path="clientcontext" resourceType="cq/personalization/components/clientcontext"/>

<div style="display:none;">
    <%-- The page language  --%>
    <span id="page-language">en</span>
</div>
    <div class="wrapper">
        <noscript>
            <div style="padding: 50px 75px; color: white;">
                <span>
                    <fmt:message key="Your browser does not support JavaScript, or JavaScript support is turned off. 
                    Without JavaScript many features of this site will not function properly. Please enable JavaScript, 
                    or download a browser which supports JavaScript."/>
                </span>
            </div>
        </noscript>
        <div><cq:include script="header.jsp"/></div>
        <div><cq:include script="content.jsp"/></div>
        <div><cq:include script="footer.jsp"/></div>
    </div>

</body>
