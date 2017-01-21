<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.intuit.utils.WebUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ipp="">
<head>
	<title>Application Home Page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	
	<link type="text/css" rel="stylesheet" href="css/master.css" />
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">

	<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=WebUtils.APPCENTER_URL%>/Content/IA/intuit.ipp.anywhere.js"></script>
	<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDoe1ngg_rQupWyBfxcng84qTwWZK1A-Jg&sensor=false"></script>
	
	<script>intuit.ipp.anywhere.setup({
    		menuProxy: '<%=WebUtils.APP_URL%>/bluedot.htm',
    		grantUrl: '<%=WebUtils.APP_URL%>/requesttoken.htm'
	});
	</script>


	<script type="text/javascript">
		var geocoder;
		var map;

    	function initialize() {
			geocoder = new google.maps.Geocoder();
        	var myOptions = {
          		center: new google.maps.LatLng(37.43021, -122.09822),
          		zoom: 16,
          		mapTypeId: google.maps.MapTypeId.ROADMAP
        	};
        	map = new google.maps.Map(document.getElementById("googleMap"), myOptions);
      	}

		function getSelected(){
			var row = $('#tt').datagrid('getSelected');
			if (row){
		 		geocoder.geocode( { 'address': row.custmoerAddress}, function(results, status) {
			 		if (status == google.maps.GeocoderStatus.OK) {
						map.setCenter(results[0].geometry.location);
						var marker = new google.maps.Marker({map: map, position: results[0].geometry.location});
	      			} 
			 		else {
	        			alert("Invalid Address");
	      			}
	    		});
			}
		}
</script>

</head>
<body onload="initialize()">
<div id="wrapper">
	
    <div id="header">
    	<div class="logo"><h1><a href="#">Map My Customers</a></h1></div>
    	<div class="topNavigation">
        	<ul>
            	<li><a href="javascript:void(0)" onclick="return intuit.ipp.anywhere.logout(function () { window.location.href = '<%=WebUtils.APP_URL%>/logout.htm'; });"><b>Sign out</b></a></li>
            </ul>
        </div>
    </div>
    <div id="body">
    	<div class="pageHeader">
        	<div class="pageTitle">
            	<h2>Access your Quick Books Account</h2>
            </div>
            <div class="primaryNavigation">
            	<ul>
                	<li class="active"><a href="#">Home</a></li>
                	<li><a href="settings.htm">Settings</a></li>
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
            <div class="blocks">
            	<div class="block latestNews">
                	<h3>Latest News</h3>
                    <ul>
                    	<li>
                        	<img src="images/raywang_136.jpg" />
                            <p><a href="http://www.forbes.com/sites/raywang/2012/02/09/how-intuit-uses-cloud-computing/"> How Intuit Uses Cloud Computing </a> DBrad Smith became Intuit's president and chief executive officer in January ....</p>
                        </li>
                    	<li>
                        	<img src="images/news.png" />
							<p><a href="http://www.pcmag.com/article2/0,2817,2379132,00.asp/"> TurboTax Premier Online (2011) </a> As much as personal tax preparation software and Web sites have evolved over the last ....</p>
                        </li>
                    	<li>
                        	<img src="images/intuit-logo.jpg" />
                            <p><a href="http://money.cnn.com/magazines/fortune/best-companies/2012/snapshots/19.html">100 Best Companies to Work For</a> The TurboTax maker uses its culture to spark innovation, with "idea jams," formal ....</p>
                        </li>
                    	<li>
                        	<img src="images/brad_smith_lg1-142x150.jpg" />
                            <p><a href="http://news.investors.com/article/596820/201201051641/intuit-anticipated-social-media-impact.htm">Intuit CEO: We Foresaw The Social Media Revolution</a> Social media's rocking the financial software industry just ....</p>
                        </li>
                    </ul>
                    <a href="http://about.intuit.com/about_intuit/press_coverage/" class="readMore">Show More</a>
                </div>
                <div class="block keyFeatures">
                	<h3>Key Features</h3>
					<%
					String connectionStatus = (String) session
							.getAttribute("connectionStatus");
					if (connectionStatus != null
							&& connectionStatus.equalsIgnoreCase("authorized")) {
					%>
						<p> Welcome QuickBooks user! To get started, use following links.</p>
						<br />
						<p>To see the applications available to the user, select the Intuit "blue dot" menu.</p>
						<br />
						<p>The OAuth flow has been completed. Application now has an authorized access token, it can access QuickBooks data.</p>
						<br />
					<ul>
						<li><b><a href="customers.htm" style="color:#0000FF;">Get All QuickBooks Customers</a></b></li>
					</ul>

					<ipp:blueDot></ipp:blueDot>
					<%
						} else {
					%>
					<p>Do you use QuickBooks? Enable QuickBooks integration to seamlessly connect your accounting software with your work in AditiApp.</p>
					<br />
					<ul>
						<li><b><a href="settings.htm" style="color:#660033;">Connect Now ....</a></b></li>
					</ul>						
					<%
						session.setAttribute("flowType", "connect_button");
						}
					%>
                </div>
                <div class="block map">
                	<h3>Map the User</h3>
                    <div class="googleMap" id="googleMap" ></div>
                </div>
            </div>
        </div>
        <div class="container">
        	<c:if test="${not empty customerList}">
				<div class="smallHeading">
					<h2>Listing all Customers</h2>
				</div>
				<div>
					<p>
						<a href="home.htm">Hide</a>
					</p>
					<br />
					<table id="tt" class="easyui-datagrid" style="width:900px;height:auto;" rownumbers="true" singleSelect="true">
						<thead>
							<tr>
								<th field="custmoerName" width="200"><b>Customer Name</b></th>
								<th field="jobStatus" width="100"><b>Job Status</b></th>
								<th field="custmoerAddress" width="500"><b>Customer Address</b></th>
								<th field="mapAddress" width="100"><b>Map</b></th>
							</tr>                          
						</thead>                           
						<tbody>                            
							<c:forEach items="${customerList}" var="customer">
							<tr>                           
								<td>${customer.displayName}</td>            
								<c:forEach var="address" items="${customer.billingAddr}">
									<c:set var="completeAddress" value="${address.line1} ${address.city} ${address.country} ${address.postalCode}"/>
								</c:forEach>
								<td>${customer.jobInfo.status}</td>
								<td>${completeAddress}</td>
								<td><a href="javascript:void(0)" onclick="getSelected()">Map It</a>
								<c:set var="completeAddress" value=""/>
							</tr> 
							</c:forEach>
						</tbody>                           
					</table>
				</div>
			</c:if>
        </div>
    </div>
    <div id="footer"><div class="cont">
        <div class="copyright">Copyright &copy2012 Company, Inc.</div></div>
    </div>
</div>
</body>
</html>
