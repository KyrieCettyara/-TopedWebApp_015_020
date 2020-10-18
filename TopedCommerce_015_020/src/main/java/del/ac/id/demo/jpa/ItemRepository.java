package del.ac.id.demo.jpa;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String>{
	
	List<Item> findAll();
	
}
