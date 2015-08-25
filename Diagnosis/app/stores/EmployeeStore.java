package stores;

import java.util.List;

import models.Employee;
import util.SearchKey;

import com.avaje.ebean.PagedList;

public interface EmployeeStore {
	//
	PagedList<Employee> retrieve(int page, SearchKey searchKey);
	PagedList<Employee> retrieve(String planId, int page, SearchKey searchKey);
	
	Employee retrieve(String id);
	Employee retrieveByCompanyId(String companyId);
	List<Employee> retrieveAll();
	int retrieveAllCount();
	void create(List<Employee> employees);
	void delete(String id);
	void create(Employee employee);
}
