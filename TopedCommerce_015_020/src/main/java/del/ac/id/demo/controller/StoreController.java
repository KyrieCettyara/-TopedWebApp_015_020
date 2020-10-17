package del.ac.id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.data.mongodb.core.query.Criteria;

import del.ac.id.demo.jpa.Item;
import del.ac.id.demo.jpa.ItemRepository;
import del.ac.id.demo.jpa.StoreRepository;

@RestController
public class StoreController {
	@Autowired StoreRepository storeRepository;
	@Autowired ItemRepository itemRepository;
	@Autowired MongoTemplate mongoTemplate;
	
	public StoreController(StoreRepository storeRepository,MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		this.storeRepository = storeRepository;
	}
	
	@GetMapping("/index")
	public ModelAndView Index(@ModelAttribute Item item, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		ModelAndView mv = new ModelAndView("index");
		
		List<Item> listItem = itemRepository.findAll();
		mv.addObject("listItem", listItem);
		return mv;
	}
	@GetMapping("/")
	public ModelAndView NoData(@ModelAttribute Item item, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
				
		ModelAndView mv = new ModelAndView("index");
		List<Item> listItem =  itemRepository.findAll();
		mv.addObject("listItem", listItem);
		return mv;
	}
	@GetMapping(value="/storeDetail/{sn}")
	public ModelAndView storeDetail(@ModelAttribute Item item, BindingResult bindingResult,
			Model model,@PathVariable("sn") String sn,RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		ModelAndView mv = new ModelAndView("storeDetail");
		
		Query query = new Query(Criteria.where("store_name").is(sn));
		List<Item> listItem = mongoTemplate.find(query,Item.class);
		System.out.println("Jumlah Data : "+ listItem.size());
		mv.addObject("items",listItem);
		return mv;
	}
}
