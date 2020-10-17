package del.ac.id.demo.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import del.ac.id.demo.jpa.Item;
import del.ac.id.demo.jpa.ItemRepository;
import del.ac.id.demo.jpa.LoginRepository;
import del.ac.id.demo.jpa.Role;
import del.ac.id.demo.jpa.User;
import del.ac.id.demo.jpa.UserRepository;

@RestController
public class LoginController {
	
	private UserRepository userRepository;
	private LoginRepository loginRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	public LoginController(UserRepository userRepository, LoginRepository loginRepository, ItemRepository itemRepository) {
		this.userRepository = userRepository;
		this.loginRepository = loginRepository;
		this.itemRepository = itemRepository;
	}
	

	
	Date sekarang = new Date();
	java.sql.Date sqlTime = new java.sql.Date(sekarang.getTime());
	Timestamp stampTime = new Timestamp(sekarang.getTime());
	

	@RequestMapping(value="/loginIndex",method = RequestMethod.POST)
	public ModelAndView loginIndex(@ModelAttribute User user, BindingResult bindingResult, Model model, RedirectAttributes attribute) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		
		User userLogin = userRepository.findByUsername(user.getUsername());
		
		if(userLogin == null) {
			attribute.addFlashAttribute("NotRegostered", "Akun tidak terdaftar");
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
				mv = new ModelAndView("item");
				mv.addObject("user",userLogin);
				mv.addObject("items", listItem);
			}
			else if(userLogin.getRoleid() == 2) {
				mv = new ModelAndView("item");
				mv.addObject("items", listItem);
			}
			
			return mv;
		}
	}
	
	@GetMapping("/logout/{un}")
	public ModelAndView Logout(@ModelAttribute("user") User user,BindingResult bindingResult, Model model, @PathVariable(value="un") String username) {
		del.ac.id.demo.jpa.Login userLogin = loginRepository.findByUsername(username);
		userLogin.setIsactive(0);
		loginRepository.save(userLogin);
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
	public String loginSubmit(@ModelAttribute User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		model.addAttribute("user",user);
		loginRepository.findByUsername(user.getUsername());
		return "redirect:index";
	}
	

	
	@RequestMapping(value="/registration",method = RequestMethod.POST)
	public ModelAndView registrationSubmit(@ModelAttribute User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("Error");
		}
		model.addAttribute("user",user);
		ModelAndView mv = new ModelAndView("login");
		userRepository.save(user);
		return mv;
	}
		
	
}
