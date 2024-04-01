package com.company.empService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.company.dao.EmpDAOImpl;
import com.company.dao.EmpDAOInterface;
import com.company.dao.EmpSkillsDAOImpl;
import com.company.dao.EmpSkillsDAOInterface;
import com.company.entities.Employee;
import com.company.entities.EmployeeSkills;

public class EmpServiceImpl implements EmpServiceInterface {
	EmpDAOInterface empDao = new EmpDAOImpl();
	EmpSkillsDAOInterface empSkillsDao = new EmpSkillsDAOImpl();

	@Override
	public int empInsert(Employee employee, String skill) {
		int employeeID = empDao.insertEmpDAO(employee);
		return empSkillsDao.insertEmpSkillsDAO(null, skill, null, employeeID);
	}

	@Override
	public ArrayList<Employee> empReadAll() {
		ArrayList<Employee> employees = empDao.readAllEmpDAO();
		ArrayList<EmployeeSkills> employeeSkills = empSkillsDao.readAllEmpSkillsDAO();

		for (Employee employee1 : employees) {
			ArrayList<EmployeeSkills> employeSkillsBlank1 = new ArrayList<EmployeeSkills>();
			for (EmployeeSkills employeeSkills1 : employeeSkills) {
				if (employee1.getEmployeeID() == employeeSkills1.getEmployeeID()) {
					employeSkillsBlank1.add(employeeSkills1);
				}
			}
			employee1.setSkills(employeSkillsBlank1);
		}
		return employees;
	}

	@Override
	public Employee getEmpById(int id) {
		return empDao.getEmpById(id);
	}

	@Override
	public boolean empUpdate(Employee employee) {
		return empDao.updateEmpDAO(employee);
	}

	@Override
	public boolean empDelete(int id) {
		return empDao.deleteEmpDAO(id);
	}

	@Override
	public HashSet<EmployeeSkills> getEmpSkillsById(int id, String[] skills) {
		return empSkillsDao.getEmpSkillsById(id, null);
	}

	@Override
	public boolean updateEmpSkills(EmployeeSkills employeeSkill, String[] skills, int id) {
		HashSet<EmployeeSkills> oldSkills = empSkillsDao.getEmpSkillsById(id, skills);
		String[] newSkills1 = skills;
//		ArrayList<String> newSkills2 = new ArrayList<String>(Arrays.asList(newSkills1)); //won't work
		List<String> newSkills2 = Arrays.asList(Optional.ofNullable(newSkills1).orElse(new String[0]));

		if(newSkills1 == null) {
			empSkillsDao.insertEmpSkillsDAO(null, null, null, id);
		}
		else {
			String skillString = "";
			for (int i = 0; i < newSkills1.length; i++) {
				if (!oldSkills.contains(newSkills2)) {
					skillString = String.join(", ", newSkills2);
				}
			}
			empSkillsDao.insertEmpSkillsDAO(null, skillString, null, id);
		}

		for (EmployeeSkills OSkill : oldSkills) {
			if (!newSkills2.contains(OSkill)) {
				int skillId1 = OSkill.getSkillID();
				empSkillsDao.deleteEmpSkillsDAO(skillId1);
			}
		}
		return empSkillsDao.updateEmpSkillsDAO(employeeSkill, id);
	}

	@Override
	public boolean deleteEmpSkills(int id) {
		return empSkillsDao.deleteEmpSkillsDAO(id);
	}
}