<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.intuit.utils.WebUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ipp="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link type="text/css" rel="stylesheet" href="css/master.css" />
<title>Application Settings Page</title>
<script type="text/javascript"
	src="<%=WebUtils.APPCENTER_URL%>/Content/IA/intuit.ipp.anywhere.js">
</script>
<script>intuit.ipp.anywhere.setup({
    menuProxy: '<%=WebUtils.APP_URL%>/bluedot.htm',
    grantUrl: '<%=WebUtils.APP_URL%>/requesttoken.htm'
	});
</script>
<script type="text/javascript">
	function disconnect(){
		window.location.href = '<%=WebUtils.APP_URL%>/disconnect.htm';
	}
</script>
</head>

<body>
<div id="wrapper">
	<div id="header">
    	<div class="logo"><h1><a href="#">Map My Customers</a></h1></div>
    	<div class="topNavigation">
        	<ul>
            	<li><a href="javascript:void(0)" onclick="return intuit.ipp.anywhere.logout(function () { window.location.href = '<%=WebUtils.APP_URL%>/logout.htm'; });"><b>Sign out</b></a></li>
            </ul>
        </div>
    </div>
    <div id="body" class="settings">
    	 <div class="pageHeader">
        	<div class="pageTitle">
            	<h2>configure your Settings</h2>
            </div>
            <div class="primaryNavigation">
            	<ul>
                	<li><a href="home.htm">Home</a></li>
                	<li class="active"><a href="#">Settings</a></li>
                </ul>
            </div>
        </div>
         <div class="container">
         	<div class="welcome">
            	<h3>Welcome, 
            		<span class="username">
            		<% if(session.getAttribute("displayUserName") != null) {
            			out.println(session.getAttribute("displayUserName"));
            			}
            			else 
            				if(session.getAttribute("firstName") != null && session.getAttribute("lastName") != null){
            				out.println(session.getAttribute("firstName").toString() + " " + session.getAttribute("lastName").toString());
            			}%>
            		</span>
            	</h3>
                <p><a href="http://www.intuit.com/">Intuit: Going Beyond Innovation</a> As the world evolves, so do we. Yet we remain driven by our passion for inventing solutions to solve important problems, perfecting those solutions and delighting our customers.</p>
            </div>
         	<div class="block noWidth"><h3>Application Settings</h3>
            <div class="section">
            	<c:choose>
					<c:when test="${authorized != null}">
						<p>You have connected to Quick Books </p>
            			<p>If you are done with your operations you can disconnect to QuickBooks by clicking the button.</p>
            			<input type="button" value="Disconnect" onclick="javascript:disconnect()"/>
            			<ipp:blueDot></ipp:blueDot>
					</c:when>
					<c:otherwise>
						<p>Do you use QuickBooks? </p>
            			<p>Create projects from QuickBooks estimates.</p>
    				   	<p>Link project tasks and deliverables to QuickBooks billing items
    				   	   Save tracked time from a project into QuickBooks
                       	   Manage and designate account members as QuickBooks resources
                       		. . . and more!
                       	</p> 
                       	<ipp:connecttointuit></ipp:connecttointuit> 	
					</c:otherwise>
				</c:choose>
            </div>
            <div class="section">
            	<% if(session.getAttribute("openIDidentity") != null &&
            			!session.getAttribute("openIDidentity").equals("") && 
            			session.getAttribute("emailMapped") == null ||
            			!session.getAttribute("emailMapped").equals("ippuser@gmail.com")){ %>
            		<% out.println("OpenId : " + session.getAttribute("openIDidentity")); %>
            		<br />
            		<% out.println("Name : " + session.getAttribute("firstName").toString() + " " + session.getAttribute("lastName").toString());%>
            		<br />
            		<% out.println("Email : " + session.getAttribute("email"));
            	} %>
            </div></div>
        
        </div>
        
    </div>
    <div id="footer"><div class="cont">
        <div class="copyright">Copyright &copy2012 Company, Inc.</div></div>
    </div>
</div>
</body>
</html>
