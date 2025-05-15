package org.proteininformationresource.pirsitepredict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		//return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
		return "home/index";
	}
}
