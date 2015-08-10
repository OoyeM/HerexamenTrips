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
                    <a href="${pageContext.request.contextPath}/login" class="dropdown-toggle"><i class="fa fa-user"></i> Login <b class="caret"></b></a>
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
    <!-- MAIN                      -->
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
            <form:form method="POST" modelAttribute="tripLocation">
            <div class="row">
                <div class="col-lg-6">
                    <div class="input-group">
                        <span class="input-group-addon equalWidth" id="basic-addon1">Name</span>
                        <form:input type="text" placeholder="Not found" path="name" id="name" readonly="true"
                                    class="form-control input-sm backgroundWhite"/>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon equalWidth" id="basic-addon1">Longitude</span>
                        <form:input type="text" placeholder="Not found" path="lng" id="lng" readonly="true"
                                    class="form-control input-sm backgroundWhite"/>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon equalWidth" id="basic-addon1">Latitude</span>
                        <form:input type="text" placeholder="Not found" path="lat" id="lat" readonly="true"
                                    class="form-control input-sm backgroundWhite"/>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon equalWidth" id="basic-addon1">Description</span>
                        <form:textarea placeholder="Not found" path="description" readonly="true" id="description"
                                       rows="5" class="form-control backgroundWhite"/>
                    </div>
                    </br>

                </div>

                <div class="col-lg-6">
                    <div class="input-group">
                        <span class="input-group-addon equalWidth" id="basic-addon1">Question</span>
                        <form:textarea placeholder="Not found" path="question" readonly="true" id="question"
                                       rows="9" class="form-control backgroundWhite"/>
                    </div>
                </div>


                </form:form>
                </div>
                </br>
                <div class="row">
                    <div class="col-lg-6">
                        <div class="panel panel-green">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Trip location pictures</h3>
                            </div>
                            <div id="listContainer">

                                <c:forEach items="${images}" var="image">
                                    <a href="${image.imgUrl}" title="${image.description}" data-gallery>
                                        <img src="${image.thumbUrl}" alt="${image.description}"/>
                                    </a>
                                </c:forEach>
                                <c:if test="${empty images}">
                                    <p style="margin-left: 5px"> No Images Found</p>
                                </c:if>
                            </div>
                        </div>

                    </div>
                </div>
               <%--IMAGE GALLARY--%>
                <div id="blueimp-gallery" class="blueimp-gallery">
                    <!-- The container for the modal slides -->
                    <div class="slides"></div>
                    <!-- Controls for the borderless lightbox -->
                    <h3 class="title"></h3>
                    <a class="prev">?</a>
                    <a class="next">?</a>
                    <a class="close">×</a>
                    <a class="play-pause"></a>
                    <ol class="indicator"></ol>
                    <!-- The modal dialog, which will be used to wrap the lightbox content -->
                    <div class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title"></h4>
                                </div>
                                <div class="modal-body next"></div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default pull-left prev">
                                        <i class="glyphicon glyphicon-chevron-left"></i>
                                        Previous
                                    </button>
                                    <button type="button" class="btn btn-primary next">
                                        Next
                                        <i class="glyphicon glyphicon-chevron-right"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->
    <%--logout--%>
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
        <!-- END MAIN                           -->
    </div>
    <!-- /#wrapper -->

    <!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
    <script src="<c:url value="/resources/js/bootstrap-image-gallery.min.js"/>"></script>
</body>
</html>