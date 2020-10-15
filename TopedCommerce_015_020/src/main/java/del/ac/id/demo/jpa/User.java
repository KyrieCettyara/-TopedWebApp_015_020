package del.ac.id.demo.jpa;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="user")
public class User {
	@Id
	private String username;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	private String pwd;
	private String roledesc;
	
	public User() {}
	public User(String username, String pwd, String roledesc) {
		this.username = username;
		this.pwd = pwd;
		this.roledesc = roledesc;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRoledesc() {
		return roledesc;
	}
	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}
	
}
