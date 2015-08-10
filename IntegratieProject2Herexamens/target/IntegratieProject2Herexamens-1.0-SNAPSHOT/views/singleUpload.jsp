<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<head>
    <title>Herexamen_trips</title>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">


    <!-- Custom Fonts -->
    <link href="<c:url value="/resources/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
    <script src="http://maps.googleapis.com/maps/api/js"></script>
    <script src="<c:url value="/resources/js/Sortable.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery-2.1.4.min.js"/>"></script>
    <!-- jQuery -->
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script>


    </script>
    <!--imagegallary-->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
    <link href="<c:url value="/resources/css/bootstrap-image-gallery.min.css"/>" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value="/resources/css/sb-admin.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/customCss.css"/>" rel="stylesheet">


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
            <a class="navbar-brand" href="${pageContext.request.contextPath}">Trips app</a>
        </div>
        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">
            <c:if test="${not empty pageContext.request.userPrincipal}">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i
                            class="fa fa-user"></i> ${pageContext.request.userPrincipal.name} <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="javascript:formSubmit()"><i class="fa fa-fw fa-power-off"></i> Log Out</a>

                        </li>
                    </ul>
                </li>
            </c:if>
            <c:if test="${empty pageContext.request.userPrincipal}">
                <li class="dropdown">
                    <a href="${pageContext.request.contextPath}/login" class="dropdown-toggle"><i
                            class="fa fa-user"></i>
                        Login <b class="caret"></b></a>
                </li>
            </c:if>
        </ul>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <li>
                        <a href="index"><i class="fa fa-fw fa-dashboard"></i>Events</a>
                    </li>
                    <li>
                        <a href="myEvents"><i class="fa fa-fw fa-dashboard"></i>My Events</a>
                    </li>
                    <li class="active">
                        <a href="trips"><i class="fa fa-fw fa-dashboard"></i>Trips</a>
                    </li>
                    <li>
                        <a href="myTrips"><i class="fa fa-fw fa-dashboard"></i>My Trips</a>
                    </li>
                </c:if>
                <c:if test="${empty pageContext.request.userPrincipal}">
                    <li>
                        <a href="${pageContext.request.contextPath}"><i class="fa fa-fw fa-dashboard"></i> Trips</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>
    <!-- MAIN -->
    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li>
                            <i class="glyphicon glyphicon-list-alt active"></i> <a
                                href="${pageContext.request.contextPath}">Trips</a>
                        </li>
                        <li>
                            <i class="glyphicon glyphicon-road active"></i><a
                                href="${pageContext.request.contextPath}/trip/${tripId}">Trip: ${tripId}</a>
                        </li>
                        <li>
                            <i class="glyphicon glyphicon-road active"></i>Triplocation: ${tripLocation.locationId}
                        </li>
                    </ol>
                </div>
            </div>


            <!-- /.row -->
            <div class="row">
                <div class="col-lg-6">
                    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/singleUpload/${tripLocationId}">
                        Upload File:
                        <div class="input-group">
                            <input type="file" name="file">
                        </div>
                        <div class="input-group">
                            <span>Description: <input type="text" name="desc" class="input-sm" maxlength="50"/></span>
                        </div>
                        <div class="input-group">
                            <input type="submit" value="Upload">
                        </div>
                    </form>
                </div>
            </div>
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
<!-- END MAIN -->
</div>
<!-- /#wrapper -->

<!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap-image-gallery.min.js"/>"></script>
</body>
</html>