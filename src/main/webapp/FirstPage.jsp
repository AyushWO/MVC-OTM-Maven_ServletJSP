<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.company.controller.EmpController"%>
<%@page import="com.company.entities.Employee"%>
<%@page import="com.company.entities.EmployeeSkills"%>
<%@ page isELIgnored = "false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>registration Page</title>
</head>
<body style="background-color: #87CEEB">
	<div style="border: solid black 2px; margin-left:710px; margin-top: 150px; padding: 10px; width: 500px " class="d1">
		<form action="insertEmp" method="post" autocomplete="off">
			<h1 style="margin-left:70px;  "><U>Registration Form</U></h1>
			<!-- <p style="display: inline-block;">Employee ID: </p><input name="empid" placeholder="type here..." type="number" /> -->
			<hr>
			<p style="display: inline-block;">Name: </p><input type="text" name="EmpName" placeholder="type here..." /><br>
			<small style="color: red">${name1}</small>
			<hr>
			<c:set var="EmployeeSkills1" value="JAVA ,OOPS, COLLECTIONS, SERVLET&JSP, JDBC, SPRING" scope="application" />
			<c:forEach var="skills11" items="${EmployeeSkills1}" >
				<input name="skills" value="${skills11}" type="checkbox" />${skills11} &nbsp &nbsp
			</c:forEach>										  
			<hr>
			<p style="display: inline-block;">Date of birth:</p><input name="EmployeeDateOfBirth" id="dob" onfocus="this.max=new Date().toISOString().split('T')[0]" value="${employee1.birthDate}" placeholder="type here..." onblur="ageCount()" type="date" /><br> 
			<small style="color: red">${dob1}</small>
			<hr>
			<p style="display: inline-block;">Salary:</p><input type="number" name="EmpSalary" value="${employee1.salary}" placeholder="type here..." pattern="\d{1,15}" /><br>
			<small style="color: red">${salary1}</small> 
			<input type="hidden" name="EmpAge" value="0" class="age_control" id="age" placeholder="click here after filling DOB" pattern="\d{1,2}" />
			<br>
			<br>
			<button>Insert</button> <button><a href="FirstPage.jsp">Reload</a> </button>
		</form>
		<hr><hr>
		<button> <a href="empAllList">Employees Data</a> </button>
	</div>
</body>
<script type="text/javascript">
function ageCount() {
	if(isNaN(today) || isNaN(date1) || isNaN(dob) || isNaN(month) || isNaN(day)|| isNaN(age) || age == undefined || date1 == undefined || day == undefined || dob == undefined || month == undefined || today == undefined){
		return false;
	}
	else{
		var today = new Date();
		var date1 = document.getElementById("dob").value;
		var dob = new Date(date1);  
		var month = dob.getMonth();
	    var day = dob.getDate();  
	    var age = today.getFullYear() - dob.getFullYear();
	}
    
    if (today.getMonth() < month || (today.getMonth() == month && today.getDate() < day)) {
    	age--;
    }                
	if(age < 18 || age > 65) {
		document.getElementById("age").value = age;
		return false;
	}
	else {
    	document.getElementById("age").value = age;
    	doucment.getElementById("age").focus ();
    	alert(age);
    return true;
	}
}
</script>
</html>