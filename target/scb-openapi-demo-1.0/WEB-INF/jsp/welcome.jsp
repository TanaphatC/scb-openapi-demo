<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Cashless</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">

			</div>
		</div>
	</nav>
	<div class="container center">
		<div class="starter-template center">
			<div class="div-table">
                <div class="div-table-row">
                    <div class="div-table-col">
                        <img src="images/scb.jpg">
                    </div>
                    <div class="div-table-col">
                        <img src="images/ktb.jpg">
                    </div>
                </div>
                <div class="div-table-row">
                    <div class="div-table-col">
                        <img src="images/bay.jpg">
                    </div>
                    <div class="div-table-col">
                        <img src="images/kbank.jpg">
                    </div>
                </div>
			</div>
		</div>
	</div>
</body>
</html>
