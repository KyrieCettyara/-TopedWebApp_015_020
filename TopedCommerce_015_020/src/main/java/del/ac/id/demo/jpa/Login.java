package del.ac.id.demo.jpa;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="login")
public class Login {
	@Id
	private String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	private String username;
	private int roleid;
	private Timestamp date;
	private int isactive;
	
	public Login() {}
	public Login(String username, int roleid,Timestamp date, int isactive) {
		this.username = username;
		this.roleid = roleid;
		this.date = date;
		this.isactive = isactive;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public void setRoleId(int roleid) {
		this.roleid = roleid;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}
	
	public String getUsername() {
		return this.username;
	}
	public int getRoleid() {
		return this.roleid;
	}
	public Date getDate() {
		return this.date;
	}
	public int getIsactive() {
		return this.isactive;
	}
}