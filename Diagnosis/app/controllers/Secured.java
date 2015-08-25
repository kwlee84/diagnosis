package controllers;

import models.User;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

public class Secured extends Authenticator {
	
    @Override
    public String getUsername(Context ctx) {
        //String authToken = ctx.request().cookies().get(LoginApp.AUTH_TOKEN).value().toString();
        String authToken = ctx.session().get(LoginApp.AUTH_TOKEN);
    	if (authToken != null && !authToken.isEmpty()) {
        	User user = User.findByAuthToken(authToken);
            if (user != null) {
            	return user.getEmail();
            }
        }
        return null;
        
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.LoginApp.login());
    }
}