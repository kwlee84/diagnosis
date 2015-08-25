package controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;

import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.annotation.Transactional;

import models.Diagnosis;
import models.Plan;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.mvc.Security.Authenticated;
import services.PlanService;
import views.html.plan.*;

@Authenticated(Secured.class)
public class PlanApp extends Controller {
	//
	@Inject
	private PlanService planService;
	
    public Result index() {
    	Form<Plan> planForm = new Form<Plan>(Plan.class);
    	
    	List<Plan> plans = planService.findAll();
		return ok(list.render(plans, planForm));
    	
    }
    
    @Transactional
    public Result register() {
    	//
    	Form<Plan> planForm = Form.form(Plan.class).bindFromRequest();
    	
    	if(planForm.hasErrors()){
			Logger.debug(planForm.errorsAsJson().toString());
			return badRequest();
		}
    	
    	Plan plan = planForm.get();
    	planService.register(plan);
    	
    	return redirect(routes.PlanApp.index());
    }

    @Transactional
    public Result remove() {
    	//
    	DynamicForm requestData = Form.form().bindFromRequest();
    	String id = requestData.get("id");
    	
    	planService.remove(id);
    	
    	return redirect(routes.PlanApp.index());
    }
}