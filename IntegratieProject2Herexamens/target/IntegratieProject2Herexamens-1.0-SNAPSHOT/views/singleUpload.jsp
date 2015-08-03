<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Matthias
  Date: 1/08/2015
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<html>
<body>
<%--<h1>Single File Upload</h1>--%>
<%--<form:form method="POST"   enctype="multipart/form-data" modelAttribute="imageModel">--%>
  <%--<span class="input-group-addon" id="basic-addon1">Name</span>--%>
  <%--<form:input  type="file" path="file" name="file" id="file" class="form-control input-sm backgroundWhite" />--%>
  <%--<span class="input-group-addon" id="basic-addon1">Name</span>--%>
  <%--<form:input  type="text" path="description" id="description" name="description" class="form-control input-sm backgroundWhite" />--%>
  <%--<input type="submit" value="upload"/>--%>
<%--</form:form>--%>
<form method="post" enctype="multipart/form-data">
  Upload File: <input type="file" name="file">
  <br /><br />
  Description: <input type="text" name="desc"/>
  <br/><br/><input type="submit" value="Upload">
</form>

</body>
</html>