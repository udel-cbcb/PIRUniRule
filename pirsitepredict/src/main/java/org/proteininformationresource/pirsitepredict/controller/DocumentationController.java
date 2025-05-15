package org.proteininformationresource.pirsitepredict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DocumentationController {
	
	@RequestMapping(value = "/documentation/standalone", method = RequestMethod.GET)
	public String standalone() {
		return "documentation/standalone";
	}
	
	@RequestMapping(value = "/documentation/online", method = RequestMethod.GET)
	public String onlineSevice() {
		return "documentation/onlineservice";
	}
	
	@RequestMapping(value = "/documentation/pirsr", method = RequestMethod.GET)
	public String pirsr() {
		return "documentation/pirsr";
	}
}
