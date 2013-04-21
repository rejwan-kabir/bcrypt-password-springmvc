package test.password.springmvc;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("form", new User());
		model.addAttribute("login", new User());
		return "home";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createUser(@ModelAttribute("form") User newUser, Model model,
			BindingResult bindingResult) {
		this.userService.createUser(newUser);
		model.addAttribute("form", new User());
		model.addAttribute("login", new User());
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String checkUser(@ModelAttribute("login") User loginUser,
			Model model, BindingResult bindingResult) {
		if (!this.userService.findUserByUsername(loginUser)) {
			model.addAttribute("message", "Username Not Found.");
		} else if (this.userService.checkUser(loginUser) == null) {
			model.addAttribute("message", "Password Mismatch");
		} else {
			model.addAttribute("message", "User Found");
		}
		model.addAttribute("form", new User());
		model.addAttribute("login", new User());
		return "home";
	}
}
