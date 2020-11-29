package org.covid19.tracker.controllers;

import java.util.List;

import org.covid19.tracker.model.LocationStats;
import org.covid19.tracker.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Autowired
	DataService dataService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allStats = dataService.getAllStats();
		int totalReportedCases = allStats
								.stream()
								.mapToInt(stat -> stat.getLatestCases())
								.sum();
		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		return "home";
	}
}
