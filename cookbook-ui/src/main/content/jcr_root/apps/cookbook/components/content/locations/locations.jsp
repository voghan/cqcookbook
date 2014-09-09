<%--

  Store Locations component.

  

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%>

<form id="loactionSearch">
    <label for="search">Search</label>
    <input id="search" name="search" type="text" value="" />
    <input type="submit" value="Search" />
	<input id="clearResults" type="button" value="Clear Results" />
</form>

<div class="location-result">
	<ul class="location-results-list">
    </ul>
    
    <ul style="display: none;">
        <li id="location-template" class="results-location">
            <p class="results-location-title">
            	<span data-selector="city"></span>
   				<a class="results-location_url" href="#">Store Page</a>
            </p>
            <p class="results-location-details">
            	<span data-selector="address"></span>
            	<span data-selector="city"></span>
            	<span data-selector="state"></span>
            </p>
            <p class="results-location-phone">
            	<span data-selector="phone"></span>
            </p>
            <p class="results-location-hours">
            	<span data-selector="hours"></span>
            </p>
        </li>
    </ul>
</div>

<script>

$(document).ready(function () {
	locations.init();
});
</script>