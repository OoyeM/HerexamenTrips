<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
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


        function initialize() {
            sortableList = Sortable.create(document.getElementById("locationList"), {
                animation:150,
                onUpdate: function (event) {
                    var testlist = [];
                    var htmlListOrder = document.getElementById("locationList");
                    $('#locationList li').each(function (index) {
                        testlist.push($(this).attr('id'));
                    });
                    $.each(locationList, function (outerIndex) {
                        var location = this;
                        var id = this.id;
                        $.each(testlist, function (innerIndex) {
                            if (id.toString() === this.toString()) {
                                locationList[outerIndex].order = innerIndex + 1;
                            }
                        });

                    }); //alert(JSON.stringify(locationList));

                }
            });

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

                    var infowindow = new google.maps.InfoWindow({
                        map: map,
                        position: pos,
                        content: 'Location found using HTML5.'
                    });

                    map.setCenter(pos);
                    map.setZoom(14);
                }, function () {
                    handleNoGeolocation(true);
                });
            } else {
                // Browser doesn't support Geolocation
                handleNoGeolocation(false);
            }
            document.addEventListener("click", function(){
                if(locationList.length===0){
                    $('#defaultEmptyLi').remove();
                    var li = document.createElement("li");
                    li.setAttribute("id", "defaultEmptyLi");
                    li.appendChild( document.createTextNode("Nothing found"));
                    document.getElementById("locationList").appendChild(li);
                }else{
                    $('#defaultEmptyLi').remove();
                }
            });
            //Add initial locations to list

            <c:forEach items="${tripLocations}" var="tripLocation">
            addInitLatLng(${tripLocation.lng},${tripLocation.lat},${tripLocation.locationId},${tripLocation.orderNumber});

            </c:forEach>

            google.maps.event.addListener(map, 'click', addLocation);
        }

        function addLocation(event) {
            window.location = "../editTrip/${tripId}/createLocation/"+event.latLng.lng()+"/"+event.latLng.lat()+"/";

        }


        function handleNoGeolocation(errorFlag) {
            if (errorFlag) {
                var content = 'Error: The Geolocation service failed.';
            } else {
                var content = 'Error: Your browser doesn\'t support geolocation.';
            }

            var options = {
                map: map,
                position: new google.maps.LatLng(60, 105),
                content: content
            };

            var infowindow = new google.maps.InfoWindow(options);
            map.setCenter(options.position);
        }
//        function addLatLng(event) {
//            var path = poly.getPath();
//            path.push(event.latLng);
//            var marker = new google.maps.Marker(
//                    {
//                        position: event.latLng,
//                        title: '#' + path.getLength(),
//                        map: map
//                    });
//            var li = document.createElement("li");
//            li.setAttribute("id", path.getLength());
//            li.appendChild(document.createTextNode("#" + path.getLength() + " Lat: " + event.latLng.lat().toFixed(10) + " - Lng: " + event.latLng.lng().toFixed(10)));
//
//            document.getElementById("locationList").appendChild(li);
//            var listObject = {
//                id: path.getLength(),
//                order: path.getLength(),
//                lat: event.latLng.lat(),
//                lng: event.latLng.lng()
//            };
//            locationList.push(listObject);
//            // alert(JSON  .stringify(locationList));
//        }
        //Load initial locations
        function addInitLatLng(long,lat,id,orderN) {
            var path = poly.getPath();
            var latLngVar = new google.maps.LatLng(lat,long,false);
            path.push(latLngVar);
            var marker = new google.maps.Marker(
                    {
                        position: latLngVar,
                        title: '#' + path.getLength(),
                        map: map
                    });
            var li = document.createElement("li");
            li.setAttribute("id", path.getLength());
            li.appendChild(document.createTextNode("#" + path.getLength() + " Lat: " + latLngVar.lat().toFixed(10) + " - Lng: " + latLngVar.lng().toFixed(10)));

            document.getElementById("locationList").appendChild(li);
            var listObject = {
                id: path.getLength(),
                order: path.getLength(),
                lat: latLngVar.lat(),
                lng: latLngVar.lng(),
                placeName: "",
                desc: ""
            };
            locationList.push(listObject);
            // alert(JSON  .stringify(locationList));
        }

        //Save locations

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
            <a class="navbar-brand" href="index.html">SB Admin</a>
        </div>
        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${pageContext.request.userPrincipal.name} <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="javascript:formSubmit()"><i class="fa fa-fw fa-power-off"></i> Log Out</a>

                    </li>
                </ul>
            </li>
        </ul>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens// customSide class because of inteference with image gallery-->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav customSide">
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
                    <form:form id="formId" method="POST" modelAttribute="trip">
                        <span class="input-group-addon" id="basic-addon1">Name</span>
                        <form:input  type="text" placeholder="Fill in" path="title" id="title" class="form-control input-sm" />

                        <span class="input-group-addon" id="basic-addon1">Description</span>
                        <form:input type="text" placeholder="Fill in" path="description" id="description" class="form-control input-sm" />

                        <span class="input-group-addon readonly" id="basic-addon1">Created By</span>
                    <form:input type="text" placeholder="Fill in" path="createdBy.username" id="createdBy" readonly="true" class="form-control input-sm backgroundWhite" />
                    </form:form>

                </div>
                <div class="col-lg-4">
                    <a href="<c:url value='/editTrip/${trip.tripId}' />"><button type="button" class="btn btn-primary">Edit trip</button></a>
                </div>
                <div class="col-lg-4">
                    <c:forEach items="${tripLocations}" var="tripLocation">
                        ${tripLocation.lng} ${tripLocation.lat}
                    </c:forEach>
                </div>
            </div>
            </br>
            <div class="row">
                <div class="col-lg-8">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Trip map</h3>
                        </div>

                        <div id="googleMap"></div>

                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Trip map</h3>
                        </div>
                        <div id="listContainer maxHeight">
                            <ul id="locationList"><li id="defaultEmptyLi">Nothing found</li></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
               <div>
                   <input type="submit" value="Save" class="btn btn-primary btn-sm">
               </div>

            </div>

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <!-- END MAIN                           -->
</div>
<!-- /#wrapper -->


</body>
</html>