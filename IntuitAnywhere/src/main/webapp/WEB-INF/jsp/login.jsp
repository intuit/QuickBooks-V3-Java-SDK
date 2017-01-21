<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.intuit.utils.WebUtils"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ipp="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="css/master.css" />
<title>Application Login Page</title>
<script type="text/javascript" src="<%=WebUtils.APPCENTER_URL%>/Content/IA/intuit.ipp.anywhere.js"></script>
</head>

<body>

<div id="wrapper">
	
    <div id="header">
    	<div class="logo"><h1><a href="#">Map My Customers</a></h1></div>
        <div class="topNavigation">
        	<ul>
            	<li><a href="#" title="Coming Soon">Sign Up</a></li>
            </ul>
        </div>
    </div>
    <div id="body" class="login">
    	
        <div class="container">
        
        	<h2>Sign In</h2>
            <form action="login.htm" method="post">
            <div class="row">
            	<label>User Name</label>
                <input type="text" name="username" />
            </div>
            <div class="row">
            	<label>Password</label>
                <input type="password"  name="passcode"/>
                <div class="rememberMe">
                	<input type="checkbox" />
                    <span>Remember Me</span>
                </div>
            </div>
            <input type=submit value="Sign In" />
            <% if(request.getParameter("isValidUser") != null && request.getParameter("isValidUser").equals("false")){ 
			%>
			<div class="error">Invalid Credentials</div>
			<%} %>
        	<% if(request.getParameter("isLoggedIn") != null && request.getParameter("isLoggedIn").equals("false")){ 
			%>
			<div class="error">Please Login to access this page</div>
							
			<%} %>
             	<div class="tpl">Or...<b style="color:#3366CC;">sign in with a 3rd-party service</b></div>
				<div><ipp:login href="<%=WebUtils.APP_URL%>/initialize.htm"></ipp:login></div>
            </form>
        </div>
    </div>
    <div id="footer"><div class="cont">
    	<div class="footerNavigation">
        <div class="copyright">Copyright &copy2012 Company, Inc.</div></div>
    </div>
</div>
</div>
</body>
</html>
