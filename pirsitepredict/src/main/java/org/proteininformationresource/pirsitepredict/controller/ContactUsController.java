package org.proteininformationresource.pirsitepredict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactUsController {
	
	@RequestMapping(value = "/contactus", method = RequestMethod.GET)
	public String index() {
		return "contactus/index";
	}
}
