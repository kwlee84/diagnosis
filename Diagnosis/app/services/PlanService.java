package services;

import java.util.List;

import models.Plan;


public interface PlanService {
	//
	List<Plan> findAll();
	void register(Plan plan);
	void remove(String id);
	Plan find(String planId);
	
}
