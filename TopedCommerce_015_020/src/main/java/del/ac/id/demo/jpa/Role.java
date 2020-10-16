package del.ac.id.demo.jpa;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="role")
public class Role {
	@Id
	private int id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	private int roleid;
	private String roledesc;
	
	protected Role() {}
	public Role(int roleid,String roledesc) {
		this.roledesc = roledesc;
		this.roleid = roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}
	
	public int getRoleid() {
		return this.roleid;
	}
	public String getRoledesc() {
		return this.roledesc;
	}
}
