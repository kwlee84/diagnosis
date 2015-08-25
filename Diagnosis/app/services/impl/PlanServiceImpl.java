package services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.Plan;
import services.PlanService;
import stores.DiagnosisStore;
import stores.PlanStore;

import com.avaje.ebean.annotation.Transactional;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {
	//
	@Autowired
	private PlanStore planStore;
	
	@Autowired
	private DiagnosisStore diagnosisStore;
	
	@Override
	public List<Plan> findAll() {
		//
		return planStore.retrieveAll();
	}

	@Override
	public void register(Plan plan) {
		//
		planStore.create(plan);
	}

	@Override
	public void remove(String id) {
		//
		diagnosisStore.deleteByPlanId(id);
		planStore.delete(id);
	}

	@Override
	public Plan find(String id) {
		//
		return planStore.retrieve(id);
	}
}
