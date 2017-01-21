<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.intuit.utils.WebUtils"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ipp="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="css/master.css" />
<title>Application Linking Page</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script>
	function linkThisUser(){
		document.forms["linkingform"].submit();
	}
	var formHiddenState = true;

	function actionLinkingForm()
	{
		if(formHiddenState == false)
			hideLinkingForm();
		else
		{
			$('#linkingForm').show();
			showLinkingForm();
		}
	}

	function hideLinkingForm()
	{
		$('#linkingForm').animate({
				height: '-10'
		}, 1000, function() {
			$('#linkingForm').hide();
			formHiddenState = true;
  });
	}

	function showLinkingForm()
	{
		$('#linkingForm').animate({
			height: '+10'
		}, 1000, function() {
			$('#linkingForm').css('height','120px');
			formHiddenState = false;
	  });
	}

</script>
</head>

<body>
<div id="wrapper">
    <div id="header">
    	<div class="logo"><h1><a href="#">Map My Customers</a></h1></div>
    </div>
    <div id="body" class="linking">
    	
        <div class="container">
        	<h2>Linking Intuit Credentials to App</h2>
			<div class="linkCredential">
				<input type="button" value="Complete My Registration" onclick="javascript:linkThisUser()"/> <input type="button" value="I Already Have An Account" onclick="actionLinkingForm()" />
			</div>
            <form name="linkingform" id="linkingForm" method="get" style="height:0px;display:none" action="redirect.htm">
            <div class="row">
            	<label>Email (or Username)</label>
                <input type="text" name="eMailId" id="eMailId" value="<%=session.getAttribute("intuitEmailId")%>"/>
            </div>
            <div class="row">
            	<label>Password</label>
                <input type="text"  name="intuitPassword" value=""/>
            </div>
            <input type="hidden"  name="intuitUserName" value="<%=session.getAttribute("intuitUserName")%>"/>
            <input type="button" value="Link it" onclick="javascript:linkThisUser()"/>
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
