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
    <%--Datepicker--%>
    <script src="<c:url value="/resources/js/moment.js"/>"></script>

    <link href="<c:url value="/resources/css/bootstrap-datetimepicker.min.css"/>" rel="stylesheet">
    <script src="<c:url value="/resources/js/bootstrap-datetimepicker.js"/>"></script>

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
                <li class="active">
                    <a href="${pageContext.request.contextPath}/events"><i class="glyphicon glyphicon-calendar"></i> Events</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/myEvents"><i class="glyphicon glyphicon-map-marker"></i> My Events</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/trips"><i class="glyphicon glyphicon-road"></i> Trips</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/myTrips"><i class="glyphicon glyphicon-edit"></i> My Trips</a>
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
                            <a href="${pageContext.request.contextPath}/events"><i class="glyphicon glyphicon-calendar"></i> Events</a>
                        </li>
                        <li>
                            View event</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/events"><button type="button" class="btn"><span class="glyphicon glyphicon-fast-backward" aria-hidden="true"></span>  Back</button></a>
                        </li>
                    </ol>
                </div>
            </div>
            <%--FILTER--%>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-4">
                    <form:form id="eventForm" modelAttribute="event"
                               action="${pageContext.request.contextPath}/myEvents/create/${event.eventId}/1"
                               method="post">
                        <div class="form-group">
                            <label for="title">Name:</label>
                            <form:input type="text" path="title" class="form-control" id="title" readonly="true"/>
                        </div>
                        <label for="eventDate">Date:</label>

                        <div class='input-group date' id='datetimepicker1'>

                            <form:input type="text" path="eventDate" class="form-control datepicker" id="eventDate"
                                        readonly="true"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                        </div>
                    </form:form>
                </div>
                <div class="col-lg-8">
                    <div class="table-responsive">
                        <label for="userTable">Users:</label>
                        <table data-toggle="table" data-click-to-select="true" id="userTable">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Joining</th>
                            </tr>
                            </thead>

                            <tbody data-link="row" class="rowlink">
                            <c:forEach items="${invitedUsers}" var="invitedUser" varStatus="loop">
                                <c:if test="${userEvents.get(loop.index).accepted}">
                                <tr>
                                    <td>${invitedUser.username}

                                    </td>
                                    <td>

                                        <c:if test="${invitedUser.username ne pageContext.request.userPrincipal.name}">
                                            <c:if test="${userEvents.get(loop.index).accepted}">
                                                <span class="glyphicon glyphicon-ok green" aria-hidden="true"></span>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${invitedUser.username eq pageContext.request.userPrincipal.name}">
                                            <c:if test="${not userEvents.get(loop.index).accepted}">
                                                <a href="<c:url value='/myEvents/alter/${event.eventId}/acceptInvite/${pageContext.request.userPrincipal.name}/true' />"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></a>
                                                <span class="glyphicon glyphicon-remove red" aria-hidden="true"></span>
                                            </c:if>
                                            <c:if test="${userEvents.get(loop.index).accepted}">
                                                <span class="glyphicon glyphicon-ok green" aria-hidden="true"></span>
                                                <a href="<c:url value='/myEvents/alter/${event.eventId}/acceptInvite/${pageContext.request.userPrincipal.name}/false' />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                                            </c:if>
                                        </c:if>
                                    </td>
                                </c:if>
                                <c:if test="${not userEvents.get(loop.index).accepted}">
                                    <c:if test="${invitedUser.username eq pageContext.request.userPrincipal.name}">
                                    <tr>
                                    <td>${invitedUser.username}

                                    </td>
                                    <td>

                                        <c:if test="${invitedUser.username ne pageContext.request.userPrincipal.name}">
                                            <c:if test="${userEvents.get(loop.index).accepted}">
                                                <span class="glyphicon glyphicon-ok green" aria-hidden="true"></span>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${invitedUser.username eq pageContext.request.userPrincipal.name}">
                                            <c:if test="${not userEvents.get(loop.index).accepted}">
                                                <a href="<c:url value='/myEvents/alter/${event.eventId}/acceptInvite/${pageContext.request.userPrincipal.name}/true' />"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></a>
                                                <span class="glyphicon glyphicon-remove red" aria-hidden="true"></span>
                                            </c:if>
                                            <c:if test="${userEvents.get(loop.index).accepted}">
                                                <span class="glyphicon glyphicon-ok green" aria-hidden="true"></span>
                                                <a href="<c:url value='/myEvents/alter/${event.eventId}/acceptInvite/${pageContext.request.userPrincipal.name}/false' />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
                                            </c:if>
                                        </c:if>
                                    </td>
                                </c:if>


                                </c:if>

                                </tr>
                            </c:forEach>
                            </tbody>

                        </table>
                    </div>
                    <form:form method="POST" id="userForm" class="navbar-form navbar-left nopad"
                               action="/Trips/myEvents/create/${event.eventId}/addUser">
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary"> Add user</button>
                        </div>
                        <div class="form-group">
                            <input type="text" placeholder="Not found" name="username" id="username"
                                   class="form-control"/>
                        </div>

                    </form:form>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4">

                        <div class="form-group">
                            <label for="title">Current trip:</label>
                            <c:if test="${event.trip ne null}">
                                ${event.trip.tripId}
                            </c:if>
                            <c:if test="${event.trip eq null}">
                                No trip selected.
                            </c:if>
                        </div>
                    <c:if test="${event.trip ne null}">
                        <div class="form-group">
                            <a href="<c:url value='/events/event/${eventId}/${event.trip.tripId}' />">
                                <button type="button" class="btn btn-danger">View trip</button>
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
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
<c:if test="${not empty error}">
    <div class="modal fade in" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Error:</h4>
                </div>
                <div class="modal-body">
                    <p class="error">${error}</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <script type="text/javascript">
        $(window).load(function () {
            $('#myModal').modal('show');
        });
    </script>
</c:if>
</body>

</html>
