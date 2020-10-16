package del.ac.id.demo.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import del.ac.id.demo.jpa.Item;
import del.ac.id.demo.jpa.ItemRepository;
import del.ac.id.demo.jpa.LoginRepository;
import del.ac.id.demo.jpa.User;
import del.ac.id.demo.jpa.UserRepository;

public class LoginController {
	
	private UserRepository userRepository;
	private LoginRepository loginRepository;
	private ItemRepository itemRepository;
	
	public LoginController(UserRepository userRepository, LoginRepository loginRepository, ItemRepository itemRepository) {
		this.userRepository = userRepository;
		this.loginRepository = loginRepository;
		this.itemRepository = itemRepository;
	}
	
	Date sekarang = new Date();
	java.sql.Date sqlTime = new java.sql.Date(sekarang.getTime());
	Timestamp stampTime = new Timestamp(sekarang.getTime());
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ModelAndView LoginIndex(@ModelAttribute User user, BindingResult bindingResult, Model model,RedirectAttributes attribute) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		User userLogin = userRepository.findByUsername(user.getUsername());
		if(userLogin == null) {
			attribute.addFlashAttribute("NotRegistered", "Akun tidak terdaftar di database");
			return new ModelAndView("redirect:/login");
		}
		else {
			ModelAndView mv = new ModelAndView();
			List<Item> listItem = itemRepository.findAll();
			System.out.println(userLogin.getUsername());
			System.out.println(userLogin.getRoledesc());
			
			if(userLogin.getRoledesc().equals("admin")) {
				del.ac.id.demo.jpa.Login LoginDetail = new del.ac.id.demo.jpa.Login(userLogin.getUsername(),stampTime,1);
				loginRepository.save(LoginDetail);
				mv.addObject("user",userLogin);
			}
			else if(userLogin.getRoledesc().equals("user")) {
				mv = new ModelAndView("item");
				mv.addObject("items", listItem);
			}
			return mv;
		}
	}
	
	@GetMapping("/login")
	public ModelAndView LoginIndex() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("user",new User());
		return mv;
	}
	
	
}
