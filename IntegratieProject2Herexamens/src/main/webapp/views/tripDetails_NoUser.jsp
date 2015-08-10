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
        var poly;
        var map;
        var locationList = [];
        var sortableList;
        var markerList = [];

        function initialize() {
            // NIET NODIG, KAN NIET EDITEN
//            sortableList = Sortable.create(document.getElementById("locationList"), {
//                animation:150,
//                onUpdate: function (event) {
//                    var testlist = [];
//                    var htmlListOrder = document.getElementById("locationList");
//                    $('#locationList li').each(function (index) {
//                        testlist.push($(this).attr('id'));
//                    });
//                    $.each(locationList, function (outerIndex) {
//                        var location = this;
//                        var id = this.id;
//                        $.each(testlist, function (innerIndex) {
//                            if (id.toString() === this.toString()) {
//                                locationList[outerIndex].order = innerIndex + 1;
//                            }
//                        });
//
//                    }); //alert(JSON.stringify(locationList));
//
//                }
//            });

            var mapProp = {
                center: new google.maps.LatLng(51.508742, -0.120850),
                zoom: 5,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
            poly = new google.maps.Polyline(
                    {
                        strokeColor: '#00ABD3',
                        strokeOpacity: 1.0,
                        strokeWeight: 3
                    });
            poly.setMap(map);
            // Try HTML5 geolocation
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    var pos = new google.maps.LatLng(position.coords.latitude,
                            position.coords.longitude);

                    map.setCenter(pos);
                    map.setZoom(14);
                }, function () {
                    handleNoGeolocation(true);
                });
            } else {
                // Browser doesn't support Geolocation
                handleNoGeolocation(false);
            }

            //Add initial locations to list

            <c:forEach items="${tripLocations}" var="tripLocation">
            addInitLatLng(${tripLocation.lng}, ${tripLocation.lat}, ${tripLocation.locationId}, ${tripLocation.orderNumber}, "${tripLocation.name}");
            </c:forEach>
            if (locationList.length === 0) {
                $('#defaultEmptyLi').remove();
                var li = document.createElement("li");
                li.setAttribute("id", "defaultEmptyLi");
                li.appendChild(document.createTextNode("Nothing found"));
                document.getElementById("locationList").appendChild(li);
            } else {
                $('#defaultEmptyLi').remove();
            }


        }


        // FUNCTION TO LOAD INITIAL LOCATIONS ON MAP
        function addInitLatLng(long, lat, id, orderN, name) {
            var path = poly.getPath();
            var latLngVar = new google.maps.LatLng(lat, long, false);
            path.push(latLngVar);
            var marker = new google.maps.Marker(
                    {
                        position: latLngVar,
                        title: '#' + path.getLength(),
                        map: map
                    });
            google.maps.event.addListener(marker, 'click', function () {
                window.location.href = "../${tripId}/locations/" + id;
            });
            markerList.push(marker);
            var li = document.createElement("li");
            li.setAttribute("id", path.getLength());
            var a = document.createElement("a");
            a.setAttribute("href", "../${tripId}/locations/" + id);
            a.appendChild(document.createTextNode("#" + orderN + ": " + name));
            li.appendChild(a);

            document.getElementById("locationList").appendChild(li);
            var listObject = {
                id: path.getLength(),
                order: path.getLength(),
                lat: latLngVar.lat(),
                lng: latLngVar.lng(),
                placeName: name,
                desc: ""
            };
            locationList.push(listObject);
            // alert(JSON  .stringify(locationList));
        }

        google.maps.event.addDomListener(window, 'load', initialize);

    </script>
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
                        <a href="${pageContext.request.contextPath}/index"><i class="fa fa-fw fa-dashboard"></i>Events</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/myEvents"><i class="fa fa-fw fa-dashboard"></i>My Events</a>
                    </li>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/trips"><i class="fa fa-fw fa-dashboard"></i>Trips</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/myTrips"><i class="fa fa-fw fa-dashboard"></i>My Trips</a>
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
                            <i class="glyphicon glyphicon-road active"></i>Trip: ${tripId}
                        </li>
                    </ol>
                </div>
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-8">


                    <form:form method="POST" modelAttribute="trip">
                        <div class="input-group">
                            <span class="input-group-addon readonly equalWidth" id="basic-addon1">   Name   </span>
                            <form:input type="text" placeholder="Not found" path="title" id="title" readonly="true"
                                        class="form-control input-sm backgroundWhite"/>
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon readonly equalWidth"
                                  id="basic-addon1">Description</span><form:textarea path="description" readonly="true"
                                                                                     id="description" rows="5"
                                                                                     class="form-control backgroundWhite"/>
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon readonly equalWidth" id="basic-addon1">Created By</span>
                            <form:input type="text" placeholder="Not found" path="createdBy.username"
                                        id="createdBy.username" readonly="true"
                                        class="form-control input-sm backgroundWhite"/>
                        </div>
                    </form:form>
                </div>
                <div class="col-lg-4">
                    <form:form modelAttribute="labels">
                        <div class="input-group">
                            <span class="input-group-addon readonly equalWidth">Labels</span>
                            <textarea id="labels" rows="8" class="form-control backgroundWhite">${labels}</textarea>
                        </div>
                    </form:form>
                </div>
            </div>
            </br>
            <div class="row">
                <div class="col-lg-8">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Trip locations map</h3>
                        </div>

                        <div id="googleMap"></div>

                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Trip locations list</h3>
                        </div>
                        <div id="listContainer maxHeight">
                            <ul id="locationList">
                                <li id="defaultEmptyLi">Nothing found</li>
                            </ul>
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
    <!-- END MAIN                           -->
</div>
<!-- /#wrapper -->


</body>
</html>