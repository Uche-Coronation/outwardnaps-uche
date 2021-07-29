package com.cmb.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmb.model.remita.RRRGenDetail;
import com.cmb.services.RemitaService;

@Transactional
@Controller
public class RemitaController {
	private static final String PATH = "/error";
	@Autowired
	RemitaService remitaService;
	
	@GetMapping(value = "/viewRemitaDetail")
	public String viewRemitaDetail(Model model, @RequestParam Long id) {
		RRRGenDetail rrdet=remitaService.getInitiatedRRRById(id);
		model.addAttribute("rrrdetailbyid", rrdet);
		return "";
	}

}
