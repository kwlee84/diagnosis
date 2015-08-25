package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;

@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"company_id", "plan_id"})) 
public class Diagnosis extends Model {
	//
    @Id
	private String id;
    @Required
    private String companyId;
    @Required
	private String planId;
    private Date dateCreated;
    private Date dateUpdated;
    @OneToOne(cascade = CascadeType.ALL)
	private AttachedFile excelFile;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public AttachedFile getExcelFile() {
		return excelFile;
	}
	
	public void setExcelFile(AttachedFile excelFile) {
		this.excelFile = excelFile;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

}
