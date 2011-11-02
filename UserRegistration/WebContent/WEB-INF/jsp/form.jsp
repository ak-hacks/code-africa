<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.even {
background-color: silver;
}
</style>
<title>Registration Page</title>
</head>
<body>
<form:form action="add.htm" commandName="user" method="GET">
<table>
<tr>
<td>Firstname :</td>
<td><form:input path="firstname" /></td>
</tr>
<tr>
<td>Surname :</td>
<td><form:input path="surname" /></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value="Submit"></td>
</tr>
</table>
</form:form>
<c:if test="${fn:length(userList) > 0}">
<table cellpadding="5">
<tr class="even">
<th>Firstname</th>
<th>Surname</th>
</tr>
<c:forEach items="${userList}" var="user" varStatus="status">
<tr class="<c:if test="${status.count % 2 == 0}">even</c:if>">
<td>${user.firstname}</td>
<td>${user.surname}</td>
</c:forEach>
</table>
</c:if>
</body>
</html>