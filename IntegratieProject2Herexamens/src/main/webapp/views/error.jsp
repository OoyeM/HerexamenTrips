<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tag" uri="WEB-INF/customTaglib.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

    <title>Herexamen_trips</title>

  <!-- Bootstrap Core CSS -->
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">


  <!-- Custom CSS -->
  <link href="<c:url value="/resources/css/sb-admin.css"/>" rel="stylesheet" type="text/css">
  <link href="<c:url value="/resources/css/customCss.css"/>" rel="stylesheet" type="text/css">


  <!-- Custom Fonts -->
  <link href="<c:url value="/resources/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
  <script src="http://maps.googleapis.com/maps/api/js"></script>
  <script src="<c:url value="/resources/js/Sortable.min.js"/>"></script>
  <script src="<c:url value="/resources/js/jquery-2.1.4.min.js"/>"></script>
  <!-- jQuery -->
  <script src="<c:url value="/resources/js/jquery.js"/>"></script>

  <!-- Bootstrap Core JavaScript -->
  <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
  <!--bootstrap table-->
  <link href="<c:url value="/resources/css/bootstrap-table.css"/>" rel="stylesheet">
  <script src="<c:url value="/resources/js/bootstrap-table.js"/>"></script>
  <link href="<c:url value="/resources/css/jasny-bootstrap.css"/>" rel="stylesheet">
  <script src="<c:url value="/resources/js/jasny-bootstrap.js"/>"></script>



</head>

<body>
<div id="wrapper">

  <!-- Navigation -->
  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/index">Trips app</a>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-right top-nav">
      <li class="dropdown">
        <a href="${pageContext.request.contextPath}/login" class="dropdown-toggle"><i class="fa fa-user"></i> Login <b class="caret"></b></a>
      </li>
    </ul>
    <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav side-nav">
        <li class="active">
          <a href="index"><i class="glyphicon glyphicon-road"></i> Trips</a>
        </li>
      </ul>
      </li>
      </ul>
    </div>
    <!-- /.navbar-collapse -->
  </nav>

  <div id="page-wrapper">

    <div class="container-fluid">

      <!-- Page Heading -->
      <div class="row">
        <div class="col-lg-12">
          <ol class="breadcrumb">
            <li>
              <i class="glyphicon glyphicon-road"></i> Error
            </li>
          </ol>
        </div>
      </div>
      <!-- /.row -->
      <%--FILTER--%>

      <div class="row">
        <div class="col-lg-12">
          <div class="error-template">
          <h1>
            Oops!</h1>
          <h2>
            Error</h2>
          <div class="error-details">
            <p>Sorry, an error has occured!</p>
            <p>${errMsg}</p>
          </div>
          <div class="error-actions">
            <a href="${pageContext.request.contextPath}/index" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
              Take Me Home </a><a href="mailto:integratieprojectherexamen@gmail.com" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-envelope"></span> Contact Support </a>
          </div>
        </div>
        </div>
      </div>
    </div>
    <!-- /.container-fluid -->

  </div>
  <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->


</body>

</html>
