<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.company.entities.Employee"%>
<%@page import="com.company.controller.EmpController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Read all data</title>
</head>
<body style="background-color: #87CEEB">
	<h1>Here's your data:</h1>

	<table border="5">
		<thead style="font-weight: bold;">
			<tr>
				<td>Name</td>
				<td>Skills</td>
				<!-- <td>Age</td> -->
				<td>Salary</td>
				<td>Date of Birth</td>
				<td>Action</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="employee" items="${list}" varStatus="loop">
				<tr>
					<td>${employee.name}</td>
					<td><c:forEach var="employeeSkill1" items="${employee.skills}"
							varStatus="loop">
							<c:forEach var="employeeSkill2" items="${employeeSkill1.skills}"
								varStatus="loop">
								${employeeSkill2},
							</c:forEach>
						</c:forEach></td>
					<%-- <td>${employee.age}</td> --%>
					<td>${employee.salary}</td>
					<td>${employee.birthDate}</td>
					<td><a href="empListById?employeeID=${employee.employeeID}"><button>Update</button></a>
						&nbsp <a href="deleteEmpById?employeeID=${employee.employeeID}"><button>Delete</button></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<hr>
	<hr>
	<button>
		<a href="FirstPage.jsp">Insert Page</a>
	</button>
</body>
</html>
