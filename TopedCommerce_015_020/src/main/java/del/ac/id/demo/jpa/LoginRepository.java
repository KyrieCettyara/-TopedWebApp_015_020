package del.ac.id.demo.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<Login, String> {
	Login findByUsername(String username);
}
