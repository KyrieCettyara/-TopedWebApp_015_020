package del.ac.id.demo.jpa;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {
	@Id
	private String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	private String username;
	private String pwd;
	private int roleid;
	
	public User() {}
	public User(String username, String pwd, int roleid) {
		this.username = username;
		this.pwd = pwd;
		this.roleid = roleid;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getPwd() {
		return this.pwd;
	}
	public int getRoleid() {
		return this.roleid;
	}
}
