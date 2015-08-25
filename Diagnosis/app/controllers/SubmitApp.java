package controllers;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.annotation.Transactional;

import models.AttachedFile;
import models.Diagnosis;
import models.Employee;
import models.Plan;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Security.Authenticated;
import services.EmployeeService;
import services.PlanService;
import services.SubmitService;
import util.ExcelUtil;
import util.FileUtil;
import views.html.submit.*;

public class SubmitApp extends Controller {
	//
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private PlanService planService;
	
	@Inject
	private SubmitService submitService;
	
    public Result registerForm(String planId) {
    	//
    	Plan plan = planService.find(planId);
    	if(plan == null) {
    		return badRequest("존재하지 않는 기획입니다.");
    	}
    	Form<Diagnosis> diagnosisForm = new Form<Diagnosis>(Diagnosis.class);
    	
    	return ok(register.render(diagnosisForm, plan));
    }
    
    public Result register() {
    	//
    	Diagnosis diagnosis = Form.form(Diagnosis.class).bindFromRequest().get();
    	
    	if(!employeeService.isExist(diagnosis.getCompanyId())) {
    		return badRequest("제출 중 오류가 발생했습니다.");
    	}
    	
    	FilePart filePart = request().body().asMultipartFormData().getFile("excelFile");
    	if (filePart != null) {
    		String fileName = diagnosis.getCompanyId() + "." + FileUtil.getFileExtension(filePart.getFilename());
    		String contentType = filePart.getContentType();
    		
    		try {
    			submitService.submit(diagnosis, FileUtil.saveFile(filePart.getFile()), fileName, contentType);
    		} catch (PersistenceException e) {
    			return badRequest("제출 중 오류가 발생했습니다.");
    		}
    	}
    		
    	return ok(result.render("제출되었습니다."));
    }
    
    public Result edit() {
    	//
    	Diagnosis diagnosis = Form.form(Diagnosis.class).bindFromRequest().get();
    	
    	if(!employeeService.isExist(diagnosis.getCompanyId())) {
    		return badRequest("제출 중 오류가 발생했습니다.");
    	}
    	
    	FilePart filePart = request().body().asMultipartFormData().getFile("excelFile");
    	if (filePart != null) {
    		String fileName = diagnosis.getCompanyId() + "." + FileUtil.getFileExtension(filePart.getFilename());
    		String contentType = filePart.getContentType();
    		
    		try {
    			submitService.overwriteSubmit(diagnosis, FileUtil.saveFile(filePart.getFile()), fileName, contentType);
    		} catch (PersistenceException e) {
    			return badRequest("제출 중 오류가 발생했습니다.");
    		}
    	}
    		
    	return ok(result.render("제출되었습니다."));
    }
}