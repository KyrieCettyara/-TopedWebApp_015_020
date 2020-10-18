package del.ac.id.demo.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	public Item getItemById(String id) {
		return itemRepository.findById(id).get();
	}
	
	public void saveOrUpdate(Item item) {
		itemRepository.save(item);
	}
	
	public void delete(String id) {
		itemRepository.deleteById(id);;
	}
}
