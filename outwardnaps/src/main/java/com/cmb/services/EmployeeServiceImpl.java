/**
 * 
 */
package com.cmb.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.cmb.model.datatable.Employee;
import com.cmb.model.datatable.PagingRequest;
import com.cmb.neft.interfaces.EmployeeService;

/**
 * @author waliu.faleye
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public Page<Employee> getEmployees(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
