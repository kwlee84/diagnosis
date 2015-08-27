package controllers;


import java.beans.Encoder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.client.utils.DateUtils;

import com.avaje.ebean.PagedList;

import exception.DiagnosisException;
import models.AttachedFile;
import models.Diagnosis;
import models.Employee;
import models.Plan;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import services.EmployeeService;
import services.ManagementService;
import services.PlanService;
import util.ExcelUtil;
import util.FileUtil;
import util.SearchKey;
import views.html.management.*;

@Authenticated(Secured.class)
public class ManagementApp extends Controller {
	//
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private PlanService planService;
	
	@Inject
	private ManagementService managementService;
	
    public Result index(String planId, int page) {
    	//
    	DynamicForm bindedForm = Form.form().bindFromRequest();
    	SearchKey searchKey = new SearchKey();
    	searchKey.addKey(Employee.SearchCompanyId, bindedForm.get("companyId"));
    	searchKey.addKey(Employee.SearchName, bindedForm.get("name"));
    	searchKey.addKey(Employee.SearchTeam, bindedForm.get("team"));
    	searchKey.addKey(Employee.SearchSubmitted, bindedForm.get("submitted"));
    	searchKey.addSort("sort_dateCreated", bindedForm.get("sort_dateCreated"));
    	
    	PagedList<Employee> employees = managementService.findEmployeeWithDiagnosis(planId, page, searchKey);
    	
    	List<Plan> plans = planService.findAll();
    	
    	int countAllEmployee = employeeService.findCountAll();
    	int countSubmitted = managementService.findCountSubmitted(planId);
    	
    	return ok(list.render(employees, searchKey, plans, planId, countAllEmployee, countSubmitted));
    }
    
    public Result download(String planId) {
    	//
    	File zipFile = managementService.findAllFiles(planId);
		response().setHeader("Content-disposition","attachment; filename=" + zipFile.getName());
		
		return ok(zipFile);
    }
    
    public Result downloadExcelFile(String planId) {
    	DynamicForm bindedForm = Form.form().bindFromRequest();
    	SearchKey searchKey = new SearchKey();
    	searchKey.addKey(Employee.SearchCompanyId, bindedForm.get("companyId"));
    	searchKey.addKey(Employee.SearchName, bindedForm.get("name"));
    	searchKey.addKey(Employee.SearchTeam, bindedForm.get("team"));
    	searchKey.addKey(Employee.SearchSubmitted, bindedForm.get("submitted"));
    	searchKey.addSort("sort_dateCreated", bindedForm.get("sort_dateCreated"));
    	
    	List<Employee> employees = managementService.findEmployeeWithDiagnosis(planId, searchKey);
    	
    	File excelFile = ExcelUtil.employeesToExcel(employees);
    	response().setHeader("Content-disposition","attachment; filename=" + DateUtils.formatDate(new Date(), "yyyyMMdd") + ".xlsx");
    	return ok(excelFile);
    }
    
}