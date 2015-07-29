<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Employee Registration Form</title>

  <style>

    .error {
      color: #ff0000;
    }
  </style>

</head>

<body>

<h2>Registration Form</h2>

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
<br/>
<br/>
Go back to <a href="<c:url value='/list' />">List of All Employees</a>
</body>
</html>