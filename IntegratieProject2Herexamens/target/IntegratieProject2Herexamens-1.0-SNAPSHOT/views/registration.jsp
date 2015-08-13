<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tag" uri="WEB-INF/customTaglib.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Trips app</title>

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
      <a class="navbar-brand" href="index">Trips app</a>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-right top-nav">
      <c:if test="${not empty pageContext.request.userPrincipal}">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${pageContext.request.userPrincipal.name} <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li>
              <a href="javascript:formSubmit()"><i class="fa fa-fw fa-power-off"></i> Log Out</a>

            </li>
          </ul>
        </li>
      </c:if>
      <c:if test="${empty pageContext.request.userPrincipal}">
        <li class="dropdown">
          <a href="login" class="dropdown-toggle"><i class="fa fa-user"></i> Login <b class="caret"></b></a>
        </li>
      </c:if>
    </ul>
    <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav side-nav">
        <li>
          <a href="${pageContext.request.contextPath}/index"><i class="glyphicon glyphicon-road"></i> Trips</a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/trips"><button type="button" class="btn"><span class="glyphicon glyphicon-fast-backward" aria-hidden="true"></span>  Back</button></a>
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
              <i class="glyphicon glyphicon-road"></i>  <a href="index">Registration</a>
            </li>
          </ol>
        </div>
      </div>
      <!-- /.row -->
      <%--FILTER--%>
      <div class="row">

        <div class="col-lg-12">

          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="text-center">Registration</h1>
                <c:if test="${not empty error}">
                  <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                  <div class="msg">${msg}</div>
                </c:if>
              </div>
              <div class="modal-body">
                <form:form class="form col-md-12 center-block" modelAttribute="user" name='loginForm'
                       method='POST'>
                  <div class="form-group">
                    <form:input type='text' path="username" name='username' class="form-control input-lg" placeholder="Email"/>
                    <div class="has-error">
                      <form:errors path="username" class="help-inline error"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <form:input type='password' path="password" name='password'   class="form-control input-lg" placeholder="Password"/>
                    <form:errors path="password" class="help-inline error"/>
                  </div>
                  <div class="has-error">

                  </div>
                  <div class="form-group">
                    <input name="submit" type="submit"
                           value="Create" class="btn btn-primary btn-lg btn-block" />

                  </div>
                  <input type="hidden" name="${_csrf.parameterName}"
                         value="${_csrf.token}" />
                </form:form>
                <c:url value="/logout" var="logoutUrl" />
                <form action="${logoutUrl}" method="post" id="logoutForm">
                  <input type="hidden" name="${_csrf.parameterName}"
                         value="${_csrf.token}" />
                </form>
                <script>
                  function formSubmit() {
                    document.getElementById("logoutForm").submit();
                  }
                </script>
              </div>
              <div class="modal-footer">
                <div class="col-md-12">
                  <a href="<c:url value='/' />"><button class="btn"  aria-hidden="true">Cancel</button></a>
                </div>
              </div>
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
