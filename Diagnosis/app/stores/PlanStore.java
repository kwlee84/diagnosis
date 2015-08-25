package stores;

import java.util.List;

import models.Plan;


public interface PlanStore {
	//
	List<Plan> retrieveAll();

	void create(Plan plan);

	void delete(String id);

	Plan retrieve(String id);
}
