package del.ac.id.demo.jpa;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface RoleRepository extends MongoRepository<Role, Integer>{
	Role findByRoleid(int roleid);
	List<Role> findAll();
}
