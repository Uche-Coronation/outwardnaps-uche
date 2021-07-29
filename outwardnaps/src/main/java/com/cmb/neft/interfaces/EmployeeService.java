/**
 * 
 */
package com.cmb.neft.interfaces;

import org.springframework.data.domain.Page;

import com.cmb.model.datatable.Employee;
import com.cmb.model.datatable.PagingRequest;

/**
 * @author waliu.faleye
 *
 */
public interface EmployeeService {

	public Page<Employee> getEmployees(PagingRequest pagingRequest);
}
