package models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.avaje.ebean.Model;

@Entity
public class User extends Model {
	
	private static Finder<String, User> finder = new Finder<String, User>(User.class);
	
	@Id
	private String id;
	@Column(unique = true, nullable = false)
	private String email;
	@Transient
	private String password;
	private String name;
	private String authToken;
	private byte[] shaPassword;
	
	private static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public String createAuthToken() {
		authToken = UUID.randomUUID().toString();
		save();
		return authToken;
	}
	
	public void deleteAuthToken() {
		authToken = null;
		save();
	}
	
	public static User find(String id) {
		return finder.byId(id);
	}
	
	public static User findUserByEmailAndPassword(String email, String password) {
		//
		if(User.find("001") == null) {
			User user = new User();
			user.setId("001");
			user.setEmail("admin");
			user.setName("관리자");
			user.setShaPassword(getSha512("admin123"));
			user.insert();
		}
		if(User.find("002") == null) {
			User user = new User();
			user.setId("002");
			user.setEmail("admin2");
			user.setName("관리자");
			user.setShaPassword(getSha512("admin123"));
			user.insert();
		}
		if(User.find("003") == null) {
			User user = new User();
			user.setId("003");
			user.setEmail("admin3");
			user.setName("관리자");
			user.setShaPassword(getSha512("admin123"));
			user.insert();
		}
		return finder.where().eq("email", email).eq("shaPassword", getSha512(password)).findUnique();
	}
	
	public static User findByAuthToken(String authToken) {
		//
		if(authToken == null) {
			return null;
		}
		try {
			return finder.where().eq("authToken", authToken).findUnique();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		this.shaPassword = getSha512(password);
	}
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
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public byte[] getShaPassword() {
		return shaPassword;
	}
	public void setShaPassword(byte[] shaPassword) {
		this.shaPassword = shaPassword;
	}

}
