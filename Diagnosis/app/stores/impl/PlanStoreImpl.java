package stores.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import models.Plan;
import stores.PlanStore;

import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.annotation.Transactional;

@Repository
@Transactional
public class PlanStoreImpl implements PlanStore {
	//
	private static Finder<String, Plan> finder = new Finder<String, Plan>(Plan.class);

	@Override
	public List<Plan> retrieveAll() {
		//
		return finder.all();
	}

	@Override
	public void create(Plan plan) {
		//
		plan.insert();
	}

	@Override
	public void delete(String id) {
		//
		finder.byId(id).delete();
	}

	@Override
	public Plan retrieve(String id) {
		//
		return finder.byId(id);
	}
	
	
}
