package com.cts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.entity.Employee;
import com.cts.entity.SalaryIncrement;
import com.cts.service.IncrementService;

@Controller
public class EmployeeController {

	@Autowired
	IncrementService service;

	@RequestMapping(value = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Employee getEmployees(@RequestParam("id") Integer id) {
		//
		// Employee emp1 = new Employee(name, 34344.44);
		// Employee emp2 = new Employee("Priya", 35455.00);
		// List<Employee> employees = new ArrayList<Employee>();
		// employees.add(emp1);
		// employees.add(emp2);
		Employee emp = service.findEmployeeById(id);
		// for(Employee emp: employees){
		List<SalaryIncrement> increments = emp.getIncrements();
		for (SalaryIncrement increment : increments) {
			increment.setEmployee(null);
		}
		return emp;

	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String findEmployee(@RequestParam("id") int id, Model model) {

		Employee employee = service.findEmployeeById(id);
		model.addAttribute("employee", employee);
		model.addAttribute("salaryIncrement", new SalaryIncrement());
		return "employee";
	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public String saveOrUpdateEmployee(@ModelAttribute("salaryIncrement") @Valid SalaryIncrement salaryIncrement,
			BindingResult result, Model model) {

		Employee employee;
		try {
			SalaryIncrement test = new SalaryIncrement(salaryIncrement.getAmount(), new Date());
			employee = service.incrementSalary(salaryIncrement.getEmployeeId(), test);
			model.addAttribute("employee", employee);
		} catch (ConstraintViolationException e) {
			System.out.println("****************");
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
			Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
			while (iterator.hasNext()) {
				ConstraintViolation<?> next = iterator.next();
				System.out.println("Validation message: " + next.getMessage());
				System.out.println("Invalid field: " + next.getPropertyPath());
				System.out.println("Validation class/bean: " + next.getRootBean());
				// result.reject(next.getPropertyPath(), next.getMessage());
				result.rejectValue(next.getPropertyPath().toString(), "", next.getMessage());
				// result.rejectValue(arg0, arg1, arg2);
			}
		} catch (Exception e) {
			System.out.println("&&&&&&&&&");
		}

		return "employee";
	}

}
