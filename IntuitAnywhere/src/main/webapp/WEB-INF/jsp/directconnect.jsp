<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.intuit.utils.WebUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ipp="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>IntuitAnywhere Demo Application</title>
<link rel="stylesheet" type="text/css" href="css/intuit.css" />

<script type="text/javascript"
	src="<%=WebUtils.APPCENTER_URL%>/Content/IA/intuit.ipp.anywhere.js"></script>

<script>intuit.ipp.anywhere.setup({
    menuProxy: '<%=WebUtils.APP_URL%>/bluedot.htm',
    grantUrl: '<%=WebUtils.APP_URL%>/requesttoken.htm'
	});
</script>

<script>
	delayInvoke();
</script>
<script>
	function delayInvoke() {
		// The delay is needed to give the page time to load before calling directConnectToIntuit().
		var t = setTimeout("invokeDirectConnect()", 500);
	}
	function invokeDirectConnect() {
		<%System.out.println("inside directconnect.jsp, about to call directConnectToIntuit js method");%>
		// The following call is equivalent to clicking the Connect To Intuit button.
		intuit.ipp.anywhere.directConnectToIntuit();
	}
</script>
</head>
<body onload="delayInvoke()">
</body>
</html>
