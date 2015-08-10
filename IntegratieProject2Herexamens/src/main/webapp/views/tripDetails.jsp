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
    <script>
        var poly;
        var map;
        var locationList = [];
        var sortableList;
        var markerList=[];

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
            addInitLatLng(${tripLocation.lng},${tripLocation.lat},${tripLocation.locationId},${tripLocation.orderNumber},"${tripLocation.name}");
            </c:forEach>
            if(locationList.length===0){
                $('#defaultEmptyLi').remove();
                var li = document.createElement("li");
                li.setAttribute("id", "defaultEmptyLi");
                li.appendChild( document.createTextNode("Nothing found"));
                document.getElementById("locationList").appendChild(li);
            }else{
                $('#defaultEmptyLi').remove();
            }


        }


        // FUNCTION TO LOAD INITIAL LOCATIONS ON MAP
        function addInitLatLng(long,lat,id,orderN,name) {
            var path = poly.getPath();
            var latLngVar = new google.maps.LatLng(lat,long,false);
            path.push(latLngVar);
            var marker = new google.maps.Marker(
                    {
                        position: latLngVar,
                        title: '#' + path.getLength(),
                        map: map
                    });
            google.maps.event.addListener(marker, 'click', function() {window.location.href = "../${tripId}/locations/"+id;});
            markerList.push(marker);
            var li = document.createElement("li");
            li.setAttribute("id", path.getLength());
            var a = document.createElement("a");
            a.setAttribute("href","../${tripId}/locations/"+id);
            a.appendChild(document.createTextNode("#" + orderN + ": "+name));
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
                            <i class="fa fa-dashboard"></i>  <a href="index">Trips</a>
                        </li>
                    </ol>
                </div>
            </div>
            <!-- /.row -->
            <%--FILTER--%>
            <div class="row">
                <div class="col-lg-12">
                    <form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath}/trips" method="GET">
                        <div class="form-group" >
                            <input type="text" class="form-control" placeholder="Search" id="search" name="search" >
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="table-responsive">


                        <table data-toggle="table" data-click-to-select="true">

                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Short description</th>
                                <th>Creator</th>
                            </tr>
                            </thead>

                            <tbody data-link="row" class="rowlink">
                            <c:forEach items="${trips}" var="trip">
                                <tr>
                                    <td><a href="<c:url value='/trip/${trip.tripId}' />">${trip.title}</a></td>
                                    <td><a href="<c:url value='/trip/${trip.tripId}' />">${fn:substring(trip.description,0,100)}</a></td>
                                    <td><a href="<c:url value='/trip/${trip.tripId}' />">${trip.createdBy.username}</a></td>

                                </tr>
                            </c:forEach>
                            </tbody>

                        </table>
                    </div>
                    <div>
                        <tag:paginate limit="${limit}" offset="${offset}" count="${count}" uri="${pageContext.request.contextPath}"
                                      next="&raquo;" previous="&laquo;" search="${search}" />
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
</div>
<!-- /#wrapper -->


</body>

</html>
