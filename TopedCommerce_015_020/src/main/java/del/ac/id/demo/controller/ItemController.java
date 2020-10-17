package del.ac.id.demo.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import del.ac.id.demo.jpa.Buy;
import del.ac.id.demo.jpa.Item;
import del.ac.id.demo.jpa.ItemRepository;
import del.ac.id.demo.jpa.LoginRepository;
import del.ac.id.demo.jpa.User;
import del.ac.id.demo.jpa.UserRepository;

@RestController
public class ItemController {
	@Autowired 
	ItemRepository itemRepository;
	@Autowired
	MongoTemplate mongoTemplate;
	
	
	@RequestMapping("/item")
	public ModelAndView item() {
		List<Item> items = itemRepository.findAll();
		ModelAndView mv = new ModelAndView("item");
		mv.addObject("items",items);
		
		return mv;
	}
	
	@RequestMapping(value="/buyItem/{id}",method= RequestMethod.POST)
	public RedirectView insertData(@ModelAttribute Buy buy, BindingResult bindingResult,
			Model model,@PathVariable("id") String id,RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		Query query = new Query(Criteria.where("id").is(id));
		Item temp = mongoTemplate.findOne(query, Item.class);
		List<Item> items = itemRepository.findAll();
		ModelAndView mv = new ModelAndView("item");
		Item selectedItem;
		if(temp != null) {
			selectedItem = temp;
			model.addAttribute("item",selectedItem);
			double stock = selectedItem.getStock();
			double sisa = stock - buy.getTotal_item();
			double rating = selectedItem.getRating();
			mv.addObject("items",items);
			
			
			System.out.println("Total Item = "+buy.getTotal_item());
			System.out.println("Sisa Item = "+sisa);
			
			Update update = new Update().inc("seen",1);
			mongoTemplate.updateFirst(query,update, Item.class);
			
			if(sisa < 0) {
				attributes.addFlashAttribute("stockStatus", "Stock Tidak mencukupi, Hanya tersisa "+stock+" item");
				return new RedirectView("/startOrder/{id}");
			}
			else if(buy.getTotal_item() <= 0) {
				attributes.addFlashAttribute("stockStatus", "Pembelian Barang tidak boleh kurang atau sama dengan 0");
				return new RedirectView("/startOrder/{id}");
			}
			else if(buy.getTotal_item() > 0 && sisa >= 0) {
				double ratingNow = (rating + buy.getRating())/2;
				selectedItem.setStock(sisa);
				Update updateItem = new Update();
				updateItem.set("stock", sisa);
				updateItem.set("rating", ratingNow);
				updateItem.set("sold", buy.getTotal_item());
				mongoTemplate.updateFirst(query,updateItem, Item.class);
				return new RedirectView("/item");
			}
		}
		mv.addObject("items",items);
		return new RedirectView("/item");
	}
	@GetMapping(value="/startOrder/{id}")
	public ModelAndView startOrder(@ModelAttribute Item item, BindingResult bindingResult,
			Model model,@PathVariable("id") String id,RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		Buy buy = new Buy();
		ModelAndView mv = new ModelAndView("buyItem");
		Optional<Item> temp = itemRepository.findById(id);
		Item selectedItem;
		if(temp != null) {
			selectedItem = temp.get();
			model.addAttribute("item",selectedItem);
			System.out.println(item.getId());
			mv.addObject("item",selectedItem);
			mv.addObject("buy",buy);}
		return mv;
	}
	@GetMapping(value="/cancelBuy/{id}")
	public RedirectView cancelBuy(@ModelAttribute Item item, BindingResult bindingResult,
			Model model,@PathVariable("id") String id,RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.inc("seen",1);
		mongoTemplate.updateFirst(query, update, Item.class);
		
		ModelAndView mv = new ModelAndView("item");
		List<Item> items = itemRepository.findAll();
		mv.addObject("items",items);
		return new RedirectView("/item");
	}
	
	
	//INI BUKAN YANG AKAN TERJADI
	
}
