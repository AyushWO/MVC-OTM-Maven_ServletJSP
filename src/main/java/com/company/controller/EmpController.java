package com.company.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.empService.EmpServiceImpl;
import com.company.empService.EmpServiceInterface;
import com.company.entities.Employee;
import com.company.entities.EmployeeSkills;

public class EmpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Employee employee = new Employee();
	EmployeeSkills employeeSkills = new EmployeeSkills();
	EmpServiceInterface service = new EmpServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println("Do get path: "+path);
		switch (path) {
		case "/empAllList":
			readAllEmpController(request, response);
			break;

		case "/empListById":
			readEmpByIdController(request, response);
			break;

		case "/deleteEmpById":
			deleteEmpControllerById(request, response);
			break;
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println("Do post path: "+path);
		switch (path) {
		case "/insertEmp":
			insertEmpController(request, response);
			break;

		case "/updateEmp":
			updateEmpControllerById(request, response);
			break;

		default:
			System.out.println("Nothing is there...");
		}
	}


	protected void insertEmpController(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    
	    String name = request.getParameter("EmpName");
	    String[] skillArray = request.getParameterValues("skills");
	    String salary = request.getParameter("EmpSalary");
	    String birthDate = request.getParameter("EmployeeDateOfBirth");
	    int age = Integer.parseInt(request.getParameter("EmpAge"));
	    request.setAttribute("name",name);
	    
	    RequestDispatcher rd = request.getRequestDispatcher("/FirstPage.jsp");
	    if(name.isEmpty() || salary.isEmpty() || salary.contains("-") || birthDate.isEmpty() || name.matches("[0-9]*") || age<=18||age>66||age==0 ) {
	        if(name.isEmpty()) {
	            request.setAttribute("name1","Enter Enter Your Name");
	        }
	        if(name != null) {
	            if(name.matches("[0-9]*")) {
	            	request.setAttribute("name1","Enter name Here. Please Type in Letters Only");       
	            }
	        }
	        if(salary.isEmpty()) {
	            request.setAttribute("salary1","Please Enter Your Salary");
	        }
	        if(salary.contains("-")) {
	            request.setAttribute("salary1","Salary Should Not be Negative");
	        }
	        if(birthDate.isEmpty()) {
	            request.setAttribute("dob1","Please Enter Your DOB");
	        }
	        if(age<18||age>66||age==0) {
	            	request.setAttribute("dob1","Your Age should be from 18 to 65");       
	        }
	        rd.forward(request, response);
	    }
	    else {
	        Employee employee = new Employee();
	        employee.setName(name);
	        employee.setAge(age);
	        employee.setSalary(Integer.parseInt(salary));
	        employee.setBirthDate(birthDate);
	        String skill = null;
	        if(skillArray != null && skillArray.length > 0) {
	            skill = String.join(", ", skillArray);
	            employeeSkills.setSkill(skill);
	            
	        } else {
	            employee.setSkill(null);
	        }

	        service.empInsert(employee, skill);

	        readAllEmpController(request, response);
	    }
	}

	protected void readAllEmpController(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Employee> list = service.empReadAll();
		request.setAttribute("list", list);

		RequestDispatcher rd = request.getRequestDispatcher("/ReadAll.jsp");
		rd.forward(request, response);
	}

	protected void readEmpByIdController(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashSet<EmployeeSkills> skillSet = new HashSet<EmployeeSkills>();
		int empId = Integer.parseInt(request.getParameter("employeeID"));

		Employee employee1 = service.getEmpById(empId);
		request.setAttribute("employee1", employee1);

		skillSet = service.getEmpSkillsById(empId, null);
		request.setAttribute("skillSet", skillSet);

		RequestDispatcher rd = request.getRequestDispatcher("UpdatePage.jsp");
		rd.forward(request, response);
	}

	protected void updateEmpControllerById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int employeeID = Integer.parseInt(request.getParameter("empid"));
		String name = request.getParameter("EmpName");
		String[] skillArray = request.getParameterValues("skills");
		int age = Integer.parseInt(request.getParameter("EmpAge"));
		String salary = request.getParameter("EmpSalary");
		String birthDate = request.getParameter("EmployeeDateOfBirth");
		
		RequestDispatcher rd = request.getRequestDispatcher("/UpdatePage.jsp");
		if(name.isEmpty() || salary.isEmpty() || salary.contains("-") || birthDate.isEmpty() || name.matches("[0-9]*") ) {
	        if(name.isEmpty()) {
	            request.setAttribute("name1","Enter name here");
	        }
	        if(name != null) {
	            if(name.matches("[0-9]*")) {
	            	request.setAttribute("name1","Type in letters only");       
	            }
	        }
	        if(salary.isEmpty()) {
	            request.setAttribute("salary1","Enter salary here");
	        }
	        if(salary.contains("-")) {
	            request.setAttribute("salary1","Salary should not be negative");
	        }
	        if(birthDate.isEmpty()) {
	            request.setAttribute("dob1","Enter DOB here");
	        }
	        rd.forward(request, response);
	    }
		else {
			employee.setEmployeeID(employeeID);
			employee.setName(name);
			employeeSkills.setSkills(skillArray);
			employee.setAge(age);
			employee.setSalary(Integer.parseInt(salary));
			employee.setBirthDate(birthDate);
			String skill = null;
	        if(skillArray != null && skillArray.length > 0) {
	            EmployeeSkills employeeSkills = new EmployeeSkills();
	            skill = String.join(", ", skillArray);
	            employeeSkills.setSkill(skill);
	            
	        } else {
	        	employeeSkills.setSkills(null);
	        }
	        			
			service.empUpdate(employee);
			service.updateEmpSkills(employeeSkills, skillArray, employeeID);

			readAllEmpController(request, response);
		}
	}

	protected void deleteEmpControllerById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employeeID"));
		service.empDelete(id);

		readAllEmpController(request, response);
	}
}
