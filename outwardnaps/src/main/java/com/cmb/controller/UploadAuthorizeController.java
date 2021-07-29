/**
 * 
 */
package com.cmb.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmb.interfaces.BatchDetailRepository;
import com.cmb.interfaces.ReportGeneratorInterface;
import com.cmb.model.BatchDetail;
import com.cmb.model.UserStatus;

/**
 * @author waliu.faleye
 *
 */
@Controller
public class UploadAuthorizeController {
	
	@Autowired
	ReportGeneratorInterface reportGenInterface;
	
	@Autowired
	BatchDetailRepository batchRepo;
	
	@GetMapping(value="/authorizeNapsRequest")
	public String authorizeNapsPage(){
		
		
		return "";
	}
	
	@PostMapping(value="/authorizeNapsRequest")
	public String authorizeNapsRequest(){
		
		
		return "";
	}
	
	@GetMapping(value="/generateNapsReciept")
	public String generateNapsReciept(Model model,@RequestParam Long id, HttpServletResponse response){
		try {
			Optional<BatchDetail> batchOpt = batchRepo.findById(id);
			if(batchOpt.isPresent()) {
			BatchDetail batchDetail = batchOpt.get();
			String returnFile = reportGenInterface.generateTransactionReciept(batchDetail);
			if (returnFile != null) {
				Path pdfPath = Paths.get(returnFile);
				byte[] dataObj = Files.readAllBytes(pdfPath);
				File file = new File(returnFile);
				streamReport(response, dataObj, file.getName());
			}}
		}catch(Exception ex) {}
		
		return null;
	}

	protected void streamReport(HttpServletResponse response, byte[] data, String name) throws IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + name);
		response.setContentLength(data.length);

		response.getOutputStream().write(data);
		response.getOutputStream().flush();
	}

}
