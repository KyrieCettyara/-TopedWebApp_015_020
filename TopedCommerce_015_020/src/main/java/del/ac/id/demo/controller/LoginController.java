package del.ac.id.demo.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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

import del.ac.id.demo.jpa.Item;
import del.ac.id.demo.jpa.ItemDetail;
import del.ac.id.demo.jpa.ItemDetailRepository;
import del.ac.id.demo.jpa.ItemRepository;
import del.ac.id.demo.jpa.ItemService;
import del.ac.id.demo.jpa.LoginRepository;
import del.ac.id.demo.jpa.Role;
import del.ac.id.demo.jpa.RoleRepository;
import del.ac.id.demo.jpa.User;
import del.ac.id.demo.jpa.UserRepository;

@RestController
public class LoginController {
	
	private UserRepository userRepository;
	private LoginRepository loginRepository;
	private RoleRepository roleRepository;
	@Autowired
	private ItemRepository itemRepository;
	MongoTemplate mongoTemplate;
	private ItemService itemService;
	
	public LoginController(UserRepository userRepository, 
			LoginRepository loginRepository, 
			ItemRepository itemRepository, 
			ItemDetailRepository itemDetailRepository, 
			ItemService itemService,
			RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.loginRepository = loginRepository;
		this.itemRepository = itemRepository;
		this.itemService = itemService;
		this.roleRepository = roleRepository;
	}
	

	
	Date sekarang = new Date();
	java.sql.Date sqlTime = new java.sql.Date(sekarang.getTime());
	Timestamp stampTime = new Timestamp(sekarang.getTime());
	

	@RequestMapping(value="/loginIndex",method = RequestMethod.POST)
	public ModelAndView loginIndex(@ModelAttribute User user, 
			BindingResult bindingResult, 
			Model model, 
			RedirectAttributes attribute) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		
		User userLogin = userRepository
				.findByUsername(user.getUsername());
		
		
		if(userLogin == null) {
			attribute.addFlashAttribute("NotRegistered", "Akun tidak terdaftar");
			return new ModelAndView("redirect:/login");
		}
		else {
			ModelAndView mv = new ModelAndView();
			
			List<Item> listItem = itemRepository.findAll();
			
			System.out.println(listItem.size());
			System.out.println(userLogin.getUsername());
			System.out.println(userLogin.getRoleid());
			
			if(userLogin.getRoleid() == 1) {
				del.ac.id.demo.jpa.Login LoginDetail = new del.ac.id.demo.jpa.Login(userLogin.getUsername(),userLogin.getRoleid(),stampTime,1);
				loginRepository.save(LoginDetail);
				mv = new ModelAndView("itemAdmin");
				mv.addObject("user",userLogin);
				mv.addObject("items", listItem);
			}
			else if(userLogin.getRoleid() == 2) {
				del.ac.id.demo.jpa.Login LoginDetail = new del.ac.id.demo.jpa.Login(userLogin.getUsername(),userLogin.getRoleid(),stampTime,1);
				loginRepository.save(LoginDetail);
				mv = new ModelAndView("item");
				mv.addObject("user",userLogin);
				mv.addObject("items", listItem);
			}
			
			return mv;
		}
	}
	
	@GetMapping("/logout/{un}")
	public ModelAndView Logout(@ModelAttribute("user") User user,
		BindingResult bindingResult, Model model, @PathVariable(value="un") String username) {

		ModelAndView mv = new ModelAndView("login");
		mv.addObject("user",new User());
		return mv;
	}
	
	@GetMapping("/loginIndex")
	public ModelAndView LoginIndex() {
		ModelAndView mv = new ModelAndView("item");
		mv.addObject("user",new User());
		return mv;
	}
	
	

	@GetMapping("/login")
	public ModelAndView Login() {
		List<Role> listRole = roleRepository.findAll();
		System.out.println(listRole.size());
		
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("user",new User());
		return mv;
	}
	
	@GetMapping("/")
	public ModelAndView Index() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("user",new User());
		return mv;
	}
	
	@RequestMapping(value="/",method = RequestMethod.POST)
	public String Dashboard(@ModelAttribute User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		model.addAttribute("user",user);
		return "redirect:login";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String loginSubmit(@ModelAttribute User user, 
			BindingResult bindingResult, 
			Model model) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		model.addAttribute("user",user);
		loginRepository.findByUsername(user.getUsername());
		return "redirect:index";
	}
	
	@RequestMapping("/itemAdmin")
	public ModelAndView allItem(Model model) {
		List<Item> listItem = itemRepository.findAll();
		ModelAndView mv = new ModelAndView("itemAdmin");
		
		System.out.println(listItem.size());
		mv.addObject("items", listItem);
		
		return mv;
		
	}
	
	//add item
	@GetMapping("/addItem")
	public ModelAndView addItem(){
		ModelAndView mv = new ModelAndView("addItem");
		mv.addObject("itemAdmin", new Item());
		mv.addObject("itemDetailAdmin", new ItemDetail());
		return mv;
	}
	
	
	
	@RequestMapping(value="/addItem",method = RequestMethod.POST)
	public RedirectView ItemAdminSubmit(@ModelAttribute Item item, @ModelAttribute ItemDetail itemDetail, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		model.addAttribute("itemDetailAdmin", itemDetail);
		model.addAttribute("itemAdmin",item);
		
		item.setItemDetail(itemDetail);
		
		itemRepository.save(item);

		return new RedirectView("/itemAdmin");
	}
	
	//delete item
	@GetMapping("/deleteItem/{id}")
    public RedirectView deleteItem(@PathVariable("id") String Id, Model model) {
		System.out.println(Id);
		
		itemService.delete(Id);
		
		return new RedirectView("/itemAdmin");
    }
	
	//update item
	@GetMapping("/editItem/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") String id, Model model) { 
		ModelAndView mv = new ModelAndView("updateItem");
	
		Item item = itemService.getItemById(id);
		ItemDetail itemDetail = item.getItemDetail();
		
		System.out.println(item.toString());
		
		model.addAttribute("itemAdmin");
		mv.addObject("itemAdmin", item);
		
		model.addAttribute("itemDetailAdmin");
		mv.addObject("itemDetailAdmin", itemDetail);	
		
		return mv; 
		
	}
	
	//admin save update maskapai	 
	@RequestMapping(value = "/save", method = RequestMethod.POST) 
	public RedirectView updateItem(@ModelAttribute("item") Item item, @ModelAttribute("itemDetail") ItemDetail itemDetail) { 
		item.setItemDetail(itemDetail);
		
		System.out.println(item.getItem_name());
		System.out.println(item.getId());
		
		itemService.saveOrUpdate(item);
		
		return new RedirectView("/itemAdmin");
	}
	
	
	
	
	
		
	
}
