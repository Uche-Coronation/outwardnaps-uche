/**
 * 
 */
package com.cmb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmb.model.datatable.Employee;
import com.cmb.model.datatable.PagingRequest;
import com.cmb.neft.interfaces.EmployeeService;

/**
 * @author waliu.faleye
 *
 */
@RestController
@RequestMapping("employees")
public class EmployeeRestController {

	private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Page<Employee> list(@RequestBody PagingRequest pagingRequest) {
        return employeeService.getEmployees(pagingRequest);
    }
}
