package com.tobias.saul.COVID19Tracker.controller;

import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tobias.saul.COVID19Tracker.service.LocationStatService;

@Controller
public class LocationStatController {
	
	@Autowired
	LocationStatService locationStatService;
	
	@GetMapping("/")
	public String getHome(Model model) {
		
		Map<String, Integer> locationStats = locationStatService.getTcp();
		int totalCases = locationStats.values().stream().collect(Collectors.summingInt(Integer::intValue));
		int usTotalCases = locationStats.get("US");
		model.addAttribute("locationstats", locationStats);
		model.addAttribute("totalCases", totalCases);
		model.addAttribute("usTotalCases", usTotalCases);
		
		return "index";
	}
}
