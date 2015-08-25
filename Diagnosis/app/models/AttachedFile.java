package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

@Entity
public class AttachedFile extends Model {
	//
    @Id
    private String id;
	private String path;
	private String fileName;
	private String contentType;
	
	public AttachedFile (String path, String fileName, String contentType) {
		//
		this.path = path;
		this.fileName = fileName;
		this.contentType = contentType;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public String getFileName() {
		return fileName;
	}
	public String getContentType() {
		return contentType;
	}
	
	
	
}
