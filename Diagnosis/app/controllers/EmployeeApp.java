package controllers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;

import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.annotation.Transactional;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.AttachedFile;
import models.Diagnosis;
import models.Employee;
import models.Plan;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Security.Authenticated;
import services.EmployeeService;
import util.ExcelUtil;
import util.FileUtil;
import util.SearchKey;
import views.html.employee.*;


public class EmployeeApp extends Controller {
	//
	@Inject
	private EmployeeService employeeService;
	
	@Authenticated(Secured.class)
    public Result index(int page) {
    	//
    	DynamicForm bindedForm = Form.form().bindFromRequest();
    	SearchKey searchKey = new SearchKey();
    	searchKey.addKey(Employee.SearchCompanyId, bindedForm.get("companyId"));
    	searchKey.addKey(Employee.SearchName, bindedForm.get("name"));
    	searchKey.addKey(Employee.SearchEmail, bindedForm.get("email"));
    	searchKey.addKey(Employee.SearchTeam, bindedForm.get("team"));
    	
    	PagedList<Employee> employees = employeeService.findPaginated(page, searchKey);
    	return ok(list.render(employees, searchKey));
    }
    
	@Authenticated(Secured.class)
    public Result create() {
    	//
		Employee employee = Form.form(Employee.class).bindFromRequest().get();
		
    	employeeService.register(employee);
    	
    	return redirect(routes.EmployeeApp.index(1));
    }
	
	@Authenticated(Secured.class)
    public Result createByExcel() {
    	//
    	FilePart filePart = request().body().asMultipartFormData().getFile("excelFile");
    	if (filePart != null) {
    		employeeService.registerByExcel(filePart.getFile());
    	}
    	
    	return redirect(routes.EmployeeApp.index(1));
    }

	@Authenticated(Secured.class)
    public Result remove() {
    	//
    	DynamicForm requestData = Form.form().bindFromRequest();
    	String id = requestData.get("id");
    	
    	employeeService.remove(id);
    	
    	return redirect(routes.EmployeeApp.index(1));
    }
	
	@Authenticated(Secured.class)
    public Result removeAll() {
    	//
    	employeeService.removeAll();
    	
    	return redirect(routes.EmployeeApp.index(1));
    }
    
    public Result findAsJson() {
    	DynamicForm bindedForm = Form.form().bindFromRequest();
    	String companyId = bindedForm.get("companyId");
    	String planId = bindedForm.get("planId");
    	
    	Employee employee = employeeService.findByCompanyId(companyId, planId);
    	
    	if(employee == null) {
    		return badRequest();
    	} else {
    		return ok(Json.toJson(employee));
    	}
    }
    
    @Authenticated(Secured.class)
    public Result downloadTemplate() {
    	String rootPath = Play.application().path().getPath() + File.separatorChar + "..";
    	return ok(new File(rootPath + File.separatorChar + "conf" + File.separatorChar + "template" +File.separatorChar + "template.xls"));
    }
}