package org.proteininformationresource.pirsitepredict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class NewsController {
	
	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String index() {
		return "news/index";
	}
}
