<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<head>
    <title>Herexamen_trips</title>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="${request.getContextPath()}/css/bootstrap.min.css"/>" rel="stylesheet">



    <!-- Custom Fonts -->
    <link href="<c:url value="${request.getContextPath()}WEB-INF/resources/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css">
    <script src="http://maps.googleapis.com/maps/api/js"></script>
    <script src="<c:url value="${request.getContextPath()}/js/Sortable.min.js"/>"></script>
    <script src="<c:url value="${request.getContextPath()}/js/jquery-2.1.4.min.js"/>"></script>
    <!-- jQuery -->
    <script src="<c:url value="${request.getContextPath()}/js/jquery.js"/>"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="${request.getContextPath()}/js/bootstrap.min.js"/>"></script>
    <script>


    </script>
    <!-- Custom CSS -->
    <link href="<c:url value="${request.getContextPath()}/css/sb-admin.css"/>" rel="stylesheet">
    <link href="<c:url value="${request.getContextPath()}/css/customCss.css"/>" rel="stylesheet">

    <!--imagegallary-->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
    <link href="<c:url value="${request.getContextPath()}/css/bootstrap-image-gallery.min.css"/>" rel="stylesheet">
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
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b
                        class="caret"></b></a>
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
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b
                        class="caret"></b></a>
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
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b
                        class="caret"></b></a>
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
        </div>
        <!-- /.navbar-collapse -->
    </nav>
    <!-- MAIN                      -->
    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        Blank Page
                        <small>Subheading</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li>
                            <i class="fa fa-dashboard active"></i> <a href="index.html">Dashboard</a>
                        </li>
                    </ol>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-4">
                    <form:form method="POST" modelAttribute="tripLocation">
                        <span class="input-group-addon" id="basic-addon1">Name</span>
                        <form:input  type="text" placeholder="Not found" path="name" id="name" class="form-control input-sm backgroundWhite" />

                        <span class="input-group-addon" id="basic-addon1">Description</span>
                        <form:input type="text" placeholder="Not found" path="description" id="description" class="form-control input-sm backgroundWhite" />

                        <span class="input-group-addon readonly" id="basic-addon1">Question</span>
                        <form:input type="text" placeholder="Not found" path="question" id="question" class="form-control input-sm backgroundWhite" />

                        <span class="input-group-addon readonly" id="basic-addon1">Longitude</span>
                        <form:input type="text" placeholder="Not found" path="lng" id="lng" readonly="true" class="form-control input-sm backgroundWhite" />

                        <span class="input-group-addon readonly" id="basic-addon1">Latitude</span>
                        <form:input type="text" placeholder="Not found" path="lat" id="lat.username" readonly="true" class="form-control input-sm backgroundWhite" />

                        <input type="submit" value="Save" class="btn btn-primary btn-sm">
                    </form:form>
                </div>




            </div>
            <div class="row">
                <div class="col-lg-4">
                    <a href="<c:url value='/file/singleUpload/${tripLocationId}' />"><button type="button" class="btn btn-primary"> Add picture</button></a>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div id="links">
                        <c:forEach items="${images}" var="image">
                            <a href="${image.imgUrl}" title="${image.description}" data-gallery>
                                <img src="${image.thumbUrl}" alt="${image.description}" />
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <<div id="blueimp-gallery" class="blueimp-gallery">
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
    <!-- END MAIN                           -->
</div>
<!-- /#wrapper -->

<!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="//blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
<script src="<c:url value="${request.getContextPath()}/js/bootstrap-image-gallery.min.js"/>"></script>
</body>
</html>