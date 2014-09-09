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

  Simple Utility Functions
  ==============================================================================

--%>
<%@include file="/libs/foundation/global.jsp" %>
<%@page import="org.apache.sling.settings.SlingSettingsService"%>
<%@ page import="java.util.Iterator" %>
<%! public String getRunMode(SlingSettingsService slingService){
	//GET CURRENT CQ RUN MODE	
	Iterator<String> rmIterator=slingService.getRunModes().iterator();	
	String currentMode="";
	while(rmIterator.hasNext()){
		String cqmode=rmIterator.next();
	    if(!cqmode.equalsIgnoreCase("author") && !cqmode.equalsIgnoreCase("publish")){
	    	currentMode=cqmode;
	    }	                                                                                                        
	}
	return currentMode;
}
%>
<%
  Resource pageResource = resourceResolver.getResource(currentPage.getPath());
%>
