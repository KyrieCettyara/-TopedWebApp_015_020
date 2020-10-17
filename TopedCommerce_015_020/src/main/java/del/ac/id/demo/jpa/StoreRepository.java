package del.ac.id.demo.jpa;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<Store, String>{
	List<Store> findAll();
}
