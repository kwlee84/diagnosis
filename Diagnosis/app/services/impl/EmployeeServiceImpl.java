package services.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.Diagnosis;
import models.Employee;
import services.EmployeeService;
import stores.DiagnosisStore;
import stores.EmployeeStore;
import util.ExcelUtil;
import util.SearchKey;

import com.avaje.ebean.PagedList;
import com.avaje.ebean.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	//
	@Autowired
	private EmployeeStore employeeStore;
	
	@Autowired
	private DiagnosisStore diagnosisStore;
	
	@Override
	public PagedList<Employee> findPaginated(int page, SearchKey searchKey) {
		//
		return employeeStore.retrieve(page, searchKey);
	}

	@Override
	public PagedList<Employee> findPaginated(String planId, int page, SearchKey searchKey) {
		//
		PagedList<Employee> employees = employeeStore.retrieve(planId, page, searchKey);
		
		if(employees != null && employees.getList() != null) {
			for (Employee employee : employees.getList()) {
				if(employee != null) {
					Diagnosis diagnosis = diagnosisStore.retrieveByCompanyId(planId, employee.getCompanyId());
					employee.setDiagnosis(diagnosis);
				}
			}
		}
		return employees;
	}

	@Override
	public void registerByExcel(File file) {
		//
		List<Employee> employees = ExcelUtil.convertToEmployees(file);
		employeeStore.create(employees);
	}

	@Override
	public void remove(String id) {
		//
		diagnosisStore.deleteByCompanyId(id);
		employeeStore.delete(id);
	}

	@Override
	public void removeAll() {
		//
		List<Employee> employees = employeeStore.retrieveAll();
		if(employees != null) {
			for (Employee employee : employees) {
				diagnosisStore.deleteByCompanyId(employee.getCompanyId());
				employeeStore.delete(employee.getId());
			}
		}
	}

	@Override
	public Employee findByCompanyId(String companyId, String planId) {
		//
		Employee employee = employeeStore.retrieveByCompanyId(companyId);
		if(employee != null) {
			employee.setDiagnosis(diagnosisStore.retrieveByCompanyId(planId, companyId));
		}
		
		return employee;
	}

	@Override
	public List<Employee> findAll() {
		//
		return employeeStore.retrieveAll();
	}

	@Override
	public boolean isExist(String companyId) {
		//
		return employeeStore.retrieveByCompanyId(companyId) == null ? false:true;
	}

	@Override
	public int findCountAll() {
		//
		return employeeStore.retrieveAllCount();
	}

	@Override
	public void register(Employee employee) {
		//
		employeeStore.create(employee);
	}
}
