<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Users Manage Page</title>
<style>
table,th,td {
	border: 1px solid black;
}
</style>
</head>
<body>
	<c:if test="${requestScope.error ne null}">
		<strong style="color: red;"><c:out
				value="${requestScope.error}"></c:out></strong>
	</c:if>
	<c:if test="${requestScope.success ne null}">
		<strong style="color: green;"><c:out
				value="${requestScope.success}"></c:out></strong>
	</c:if>
	<c:url value="/addUser" var="addURL"></c:url>
	<c:url value="/editUser" var="editURL"></c:url>

	<c:if test="${requestScope.user ne null}">
		<form action='<c:out value="${editURL}"></c:out>' method="post">
			ID: <input type="text" value="${requestScope.user.id}"
				readonly="readonly" name="id"><br> Name: <input
				type="text" value="${requestScope.user.name}" name="name"><br>
			Surname: <input type="text" value="${requestScope.user.surname}"
				name="surname"><br> <input type="submit"
				value="Edit User">
		</form>
	</c:if>

	<%-- Add Request --%>
	<c:if test="${requestScope.user eq null}">
		<form action='<c:out value="${addURL}"></c:out>' method="post">
			Name: <input type="text" name="name"><br> Surname: <input
				type="text" name="surname"><br> <input type="submit"
				value="Add User">
		</form>
	</c:if>

	<c:if test="${not empty requestScope.users}">
		<table>
			<tbody>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Surname</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${requestScope.users}" var="user">
					<c:url value="/editUser" var="editURL">
						<c:param name="id" value="${user.id}"></c:param>
					</c:url>
					<c:url value="/deleteUser" var="deleteURL">
						<c:param name="id" value="${user.id}"></c:param>
					</c:url>
					<tr>
						<td><c:out value="${user.id}"></c:out></td>
						<td><c:out value="${user.name}"></c:out></td>
						<td><c:out value="${user.surname}"></c:out></td>
						<td><a
							href='<c:out value="${editURL}" escapeXml="true"></c:out>'>Edit</a></td>
						<td><a
							href='<c:out value="${deleteURL}" escapeXml="true"></c:out>'>Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>