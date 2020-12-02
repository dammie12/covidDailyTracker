package org.covid19.tracker.controllers;

import java.util.List;

import org.covid19.tracker.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SearchController {

	@Autowired
	DataService dataService;
	
	@GetMapping("/search")
	public String search(Model model) {

		List<String> allStates = dataService.getAllStates();
		model.addAttribute("allStates", allStates);
		return "search";
	}
}
