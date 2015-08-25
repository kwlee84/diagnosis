package models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
public class Employee extends Model {
	//
	public static final String SearchCompanyId = "companyId";
	public static final String SearchName = "name";
	public static final String SearchEmail = "email";
	public static final String SearchTeam = "team";
	public static final String SearchSubmitted = "submitted";
	
    @Id
	private String id;
    @Required
    @Column(unique = true, nullable = false)
    private String companyId;
    @Required
	private String name;
    @Required
	private String email;
    private String team;
	
    @Transient
	private Diagnosis diagnosis;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Diagnosis getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
