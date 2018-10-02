package com.iToolsV2.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iToolsV2.form.MachineReport;
import com.iToolsV2.form.MachineReportSearch;
import com.iToolsV2.form.TransactionReport;
import com.iToolsV2.form.TransactionSearch;
import com.iToolsV2.service.MachineReportService;
import com.iToolsV2.service.TransactionReportService;
import com.iToolsV2.service.TrayService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MachineReportController extends AbstractController {
	
	@Autowired
	private TrayService trayService;
	@Autowired
	private MachineReportService machineReportservice;
	
	@GetMapping("/machineReport")
	public String getReport(Model model, @ModelAttribute TransactionSearch searchForm) {
		List<TransactionReport> lstTransaction = new ArrayList<>();
		model.addAttribute("machineReportSearch", new MachineReportSearch());
		model.addAttribute("lstTransaction", lstTransaction);
		
		model.addAttribute("lstTray", null);
		
		/*try {
			InetAddress inetAddress = InetAddress. getLocalHost();
			System.out.println("IP Address:- " + inetAddress. getHostAddress());
			System.out.println("Host Name:- " + inetAddress. getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		return "transaction/machineReport";
	}
	
	@PostMapping("/machineReport/query")
	public String searchTransaction(Model model, @ModelAttribute MachineReportSearch searchForm) {
		List<MachineReport> lstTransaction = new ArrayList<>();
		model.addAttribute("machineReportSearch", searchForm);
		
		List<String> lstTray = new ArrayList<>();
		try {
			lstTray = trayService.findTrayByMachineCode(searchForm.getMachineCode());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		model.addAttribute("lstTray", lstTray);
		
		try {
			lstTransaction = machineReportservice.searchMachineReport(searchForm);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("lstTransaction", lstTransaction);
		
		return "transaction/machineReport";
	}
}
