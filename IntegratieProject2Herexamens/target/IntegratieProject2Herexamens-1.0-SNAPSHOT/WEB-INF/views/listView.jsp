<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin - Bootstrap Admin Template</title>
  <title>Herexamen_trips</title>

  <!-- Bootstrap Core CSS -->
  <link href="<c:url value="resources/css/bootstrap.min.css"/>" rel="stylesheet">

  <!-- Custom CSS -->
  <link href="<c:url value="resources/css/sb-admin.css"/>" rel="stylesheet">
  <link href="<c:url value="resources/css/customCss.css"/>" rel="stylesheet">


  <!-- Custom Fonts -->
  <link href="<c:url value="resources/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
  <script src="http://maps.googleapis.com/maps/api/js"></script>
  <script src="<c:url value="resources/js/Sortable.min.js"/>"></script>
  <script src="<c:url value="resources/js/jquery-2.1.4.min.js"/>"></script>
  <!-- jQuery -->
  <script src="<c:url value="resources/js/jquery.js"/>"></script>

  <!-- Bootstrap Core JavaScript -->
  <script src="<c:url value="resources/js/bootstrap.min.js"/>"></script>
  <!--bootstrap table-->
  <link href="<c:url value="resources/css/bootstrap-table.css"/>" rel="stylesheet">
  <script src="<c:url value="resources/js/bootstrap-table.js"/>"></script>



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
      <a class="navbar-brand" href="index.html">SB Admin</a>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-right top-nav">
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b class="caret"></b></a>
        <ul class="dropdown-menu message-dropdown">
          <li class="message-preview">
            <a href="#">
              <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                <div class="media-body">
                  <h5 class="media-heading">
                    <strong>John Smith</strong>
                  </h5>
                  <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                  <p>Lorem ipsum dolor sit amet, consectetur...</p>
                </div>
              </div>
            </a>
          </li>
          <li class="message-preview">
            <a href="#">
              <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                <div class="media-body">
                  <h5 class="media-heading">
                    <strong>John Smith</strong>
                  </h5>
                  <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                  <p>Lorem ipsum dolor sit amet, consectetur...</p>
                </div>
              </div>
            </a>
          </li>
          <li class="message-preview">
            <a href="#">
              <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://placehold.it/50x50" alt="">
                                    </span>
                <div class="media-body">
                  <h5 class="media-heading">
                    <strong>John Smith</strong>
                  </h5>
                  <p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
                  <p>Lorem ipsum dolor sit amet, consectetur...</p>
                </div>
              </div>
            </a>
          </li>
          <li class="message-footer">
            <a href="#">Read All New Messages</a>
          </li>
        </ul>
      </li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
        <ul class="dropdown-menu alert-dropdown">
          <li>
            <a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
          </li>
          <li>
            <a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
          </li>
          <li>
            <a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
          </li>
          <li>
            <a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
          </li>
          <li>
            <a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
          </li>
          <li>
            <a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
          </li>
          <li class="divider"></li>
          <li>
            <a href="#">View All</a>
          </li>
        </ul>
      </li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li>
            <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
          </li>
          <li>
            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
          </li>
          <li>
            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
          </li>
          <li class="divider"></li>
          <li>
            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
          </li>
        </ul>
      </li>
    </ul>
    <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
    <div class="collapse navbar-collapse navbar-ex1-collapse">
      <ul class="nav navbar-nav side-nav">
        <li>
          <a href="index.html"><i class="fa fa-fw fa-dashboard"></i> Dashboard</a>
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
              <i class="fa fa-dashboard"></i>  <a href="index.html">Trip List</a>
            </li>
          </ol>
        </div>
      </div>
      <!-- /.row -->
    <div class="row">
      <div class="col-lg-12">
        <h2>List of Employees</h2>
        <table data-toggle="table">
          <thead>
          <tr>
            <th>NAME</th>
            <th>Joining Date</th>
            <th>Salary</th>
            <th>SSN</th>
            <th></th>
          </tr>
          </thead>

            <tbody>
            <c:forEach items="${trips}" var="trip">
            <tr>
              <td>${trip.description}</td>
              <td>${trip.title}</td>
              <td>${trip.tripId}</td>
              <td><a href="<c:url value='/edit-${trip.tripId}-trip' />">${trip.tripId}</a></td>
              <td><a href="<c:url value='/delete-${trip.tripId}-trip' />">delete</a></td>
            </tr>
            </c:forEach>
            </tbody>

        </table>
        <br/>
        <a href="<c:url value='/new' />">Add New Employee</a>
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
