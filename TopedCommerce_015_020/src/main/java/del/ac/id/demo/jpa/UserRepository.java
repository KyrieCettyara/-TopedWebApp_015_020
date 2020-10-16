package del.ac.id.demo.jpa;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
	List<User> findAll();
	User findByUsername(String username);

}
