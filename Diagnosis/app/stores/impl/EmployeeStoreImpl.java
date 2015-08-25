package stores.impl;

import java.util.List;

import org.h2.util.StringUtils;
import org.springframework.stereotype.Repository;

import models.Employee;
import stores.EmployeeStore;
import util.SearchKey;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.annotation.Transactional;

@Repository
@Transactional
public class EmployeeStoreImpl implements EmployeeStore {
	//
	private static Finder<String, Employee> finder = new Finder<String, Employee>(Employee.class);
	
	@Override
	public PagedList<Employee> retrieve(int page, SearchKey searchKey) {
		//
		ExpressionList<Employee> list = finder.where();
		
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchCompanyId))) {
			list = list.eq(Employee.SearchCompanyId, searchKey.get(Employee.SearchCompanyId));
		}
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchName))) {
			list = list.like(Employee.SearchName, "%"+searchKey.get(Employee.SearchName)+"%");
		}
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchEmail))) {
			list = list.like(Employee.SearchEmail, "%"+searchKey.get(Employee.SearchEmail)+"%");
		}
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchTeam))) {
			list = list.like(Employee.SearchTeam, "%"+searchKey.get(Employee.SearchTeam)+"%");
		}
		return list.findPagedList(page-1, 20);
	}

	@Override
	public PagedList<Employee> retrieve(String planId, int page, SearchKey searchKey) {
		//
		String sql = " select e.id"  
		+ " from employee e"   
		+ " left join diagnosis d on e.company_id = d.company_id and d.plan_id='"+planId+"'";  
		  
		RawSql rawSql =  RawSqlBuilder.parse(sql).columnMapping("e.id",  "id").create();  
		
		ExpressionList<Employee> list = finder.setRawSql(rawSql).where();
		
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchCompanyId))) {
			list = list.eq("e.company_id", searchKey.get(Employee.SearchCompanyId));
		}
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchName))) {
			list = list.like(Employee.SearchName, "%"+searchKey.get(Employee.SearchName)+"%");
		}
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchEmail))) {
			list = list.like(Employee.SearchEmail, "%"+searchKey.get(Employee.SearchEmail)+"%");
		}
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchTeam))) {
			list = list.like(Employee.SearchTeam, "%"+searchKey.get(Employee.SearchTeam)+"%");
		}
		if(!StringUtils.isNullOrEmpty(searchKey.get(Employee.SearchSubmitted))) {
			if("submitted".equals(searchKey.get(Employee.SearchSubmitted))){
				list.ne("d.id", null);
			} else {
				list.eq("d.id", null);
			}
		}
		if(searchKey.getSortField().get("sort_dateCreated") != null) {
			if(searchKey.getSortField().get("sort_dateCreated")) {
				list.orderBy().desc("d.date_created");
			} else {
				list.orderBy().asc("d.date_created");
			}
		}
		
		return list.findPagedList(page-1, 20);
	}

	@Override
	public Employee retrieve(String id) {
		//
		return finder.byId(id);
	}

	@Override
	public Employee retrieveByCompanyId(String companyId) {
		//
		return finder.where().eq("companyId", companyId).findUnique();
	}

	@Override
	public List<Employee> retrieveAll() {
		//
		return finder.all();
	}

	@Override
	public int retrieveAllCount() {
		//
		return finder.findRowCount();
	}

	@Override
	public void create(List<Employee> employees) {
		//
		for (Employee employee : employees) {
			employee.insert();
		}
	}

	@Override
	public void delete(String id) {
		//
		finder.byId(id).delete();
	}

	@Override
	public void create(Employee employee) {
		//
		employee.insert();
	}
	
}
