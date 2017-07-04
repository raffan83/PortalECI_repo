<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
<%@attribute name="title"%>
<%@attribute name="bodyClass"%>
<%@attribute name="extra_css" fragment="true" %>
<%@attribute name="extra_js_header" fragment="true" %>
<%@attribute name="extra_js_footer" fragment="true" %>
<%@attribute name="body_area" fragment="true" %>

<html>
 <head>

 <title>${title}</title>
	 <t:header />
     <jsp:invoke fragment="extra_css"/>
     <jsp:invoke fragment="extra_js_header"/>
     
 </head>
 <body class="${bodyClass}">
 	 <form id="callActionForm" method="post"></form>
 
     <jsp:invoke fragment="body_area"/>
     <t:footer />
     <jsp:invoke fragment="extra_js_footer"/>

 </body>
</html>