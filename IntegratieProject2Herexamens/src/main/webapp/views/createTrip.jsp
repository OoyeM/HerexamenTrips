<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tag" uri="WEB-INF/customTaglib.tld" %>
<%@page session="true" %>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Trips app - Bootstrap Admin Template</title>
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
      <a class="navbar-brand" href="index">Trips app</a>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-right top-nav">
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i
                class="fa fa-user"></i> ${pageContext.request.userPrincipal.name} <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li>
            <a href="javascript:formSubmit()"><i class="fa fa-fw fa-power-off"></i> Log Out</a>

          </li>
        </ul>
      </li>
    </ul>
    <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav side-nav">
        <li>
          <a href="index"><i class="fa fa-fw fa-dashboard"></i>Events</a>
        </li>
        <li>
          <a href="myEvents"><i class="fa fa-fw fa-dashboard"></i>My Events</a>
        </li>
        <li>
          <a href="trips"><i class="fa fa-fw fa-dashboard"></i>Trips</a>
        </li>
        <li class="active">
          <a href="myTrips"><i class="fa fa-fw fa-dashboard"></i>My Trips</a>
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
              <i class="fa fa-dashboard"></i> <a href="index">Events</a>
            </li>
          </ol>
        </div>
      </div>
      <%--FILTER--%>
      <div class="row">
        <div class="col-lg-12">
          <form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath}"
                method="GET">
            <a href="<c:url value='/new' />">
              <button type="button" class="btn btn-primary"> Add trip</button>
            </a>

            <div class="form-group">
              <input type="text" class="form-control" placeholder="Search" id="search" name="search">
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
          </form>
        </div>
      </div>
      <!-- /.row -->
      <div class="row">
        <div class="col-lg-12">
          <form:form method="POST" modelAttribute="trip">
            <form:input type="hidden" path="tripId" id="id"/>
            <table>
              <tr>
                <td><label for="title">Title: </label> </td>
                <td><form:input path="title" id="title"/></td>
                <td><form:errors path="title" cssClass="error"/></td>
              </tr>

              <tr>
                <td><label for="description">Joining Date: </label> </td>
                <td><form:input path="description" id="description"/></td>
                <td><form:errors path="description" cssClass="error"/></td>
              </tr>


              <tr>
                <td colspan="3">
                  <c:choose>
                    <c:when test="${edit}">
                      <input type="submit" value="Update"/>
                    </c:when>
                    <c:otherwise>
                      <input type="submit" value="Register"/>
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
            </table>
          </form:form>



        </div>
      </div>

    </div>
    <!-- /.container-fluid -->

  </div>
  <!-- /#page-wrapper -->
  <%--logout--%>
  <c:url value="/logout" var="logoutUrl"/>
  <form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
  </form>
  <script>
    function formSubmit() {
      document.getElementById("logoutForm").submit();
    }
  </script>
</div>
<!-- /#wrapper -->


</body>

</html>