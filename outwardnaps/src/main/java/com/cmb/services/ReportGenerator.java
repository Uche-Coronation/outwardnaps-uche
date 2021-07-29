/**
 * 
 */
package com.cmb.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cmb.interfaces.FinancialInstitutionRepository;
import com.cmb.interfaces.ReportGeneratorInterface;
import com.cmb.interfaces.TransactionAuthorizerDetailRepository;
import com.cmb.interfaces.UploadRepository;
import com.cmb.model.AuthorizerLevel;
import com.cmb.model.BatchDetail;
import com.cmb.model.FinancialInstitution;
import com.cmb.model.TransactionAuthorizerDetail;
import com.cmb.model.TransactionStatus;
import com.cmb.model.UploadRequestData;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author waliu.faleye
 *
 */
@Service
public class ReportGenerator implements ReportGeneratorInterface {
	@Value("${job.reporttemplate.path}")
	String templatePath;
	@Value("${job.reportgenerated.path}")
	String reportGeneratedPath;
	@Value("${reciept.file.template}")
	String recieptFileTemplate;
	@Value("${final.authorizer.level}")
	String finalAuthorizerLevel;

	@Autowired
	TransactionAuthorizerDetailRepository tadRepo;

	@Autowired
	UploadRepository uploadRepo;
	
	@Autowired
	FinancialInstitutionRepository financialInstitutionRepo;

	/**
	 * public static void main(String args[]) { SimpleDateFormat tranDateFormatter =
	 * new SimpleDateFormat("dd-MMMM-yyyy");
	 * System.out.println(tranDateFormatter.format(new Date())); }
	 **/

	@Override
	public String generateTransactionReciept(BatchDetail batchDetail) {
		// TODO Auto-generated method stub
		String pdfFileName = "";
		try {
			DateTimeFormatter reportFileDateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmm");
			pdfFileName = reportGeneratedPath.concat(batchDetail.getOrderingPartyAccountNumber()).concat("_")
					.concat(LocalDateTime.now().format(reportFileDateformatter)).concat(".pdf");
			InputStream inputStream = new FileInputStream(templatePath.concat(recieptFileTemplate));
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
			SimpleDateFormat tranDateFormatter = new SimpleDateFormat("dd-MMMM-yyyy");
			List<UploadRequestData> uploadList = uploadRepo.findByUploadSessionId(batchDetail.getUploadSessionId());
			uploadList.forEach(a -> {
			a.setItemAmountStr(this.formatAmount(a.getItemAmount()));
			if(Strings.isNullOrEmpty(a.getBankName())) {
				Optional<FinancialInstitution> fiOpt = financialInstitutionRepo.findById(a.getAccountSortCode());
				a.setBankName(fiOpt.isPresent()?fiOpt.get().getInstitutionName():"");
			}
			});
			TransactionStatus stat = new TransactionStatus();
			stat.setStatus("P");
			TransactionAuthorizerDetail tad = new TransactionAuthorizerDetail();
			Optional<TransactionAuthorizerDetail> tadOpt = tadRepo.findByUploadSessionIdAndLevelAndTransactionStatus(
					batchDetail.getUploadSessionId(), Long.valueOf(finalAuthorizerLevel), stat);
			if (tadOpt.isPresent())
				tad = tadOpt.get();
			HashMap parameters = new HashMap();
			parameters.put("transactionDate", tad != null ? tranDateFormatter.format(tad.getAuthorizeDate())
					: tranDateFormatter.format(new Date()));
			parameters.put("sender", batchDetail.getOrderingPartyAccountName());
			parameters.put("accountNumber", batchDetail.getOrderingPartyAccountNumber());
			parameters.put("batchId", batchDetail.getUploadSessionId());
			parameters.put("uploadData", uploadList);
			System.out.println("uploadList.size()===="+uploadList.size());
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(uploadList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFileName);
		} catch (JRException | FileNotFoundException ex) {
			ex.printStackTrace();
			// Logger.getLogger(JasperTutorial2.class.getName()).log(Level.SEVERE,
			// null, ex);
		}
		return pdfFileName;
	}

	public String formatAmount(BigDecimal value) {
		// System.out.println("formatting value == "+value);
		String format1 = "###,###,##0.00";

		DecimalFormat fm1 = new DecimalFormat(format1);

		String formattedValue = fm1.format(Double.valueOf(value.toString()));

		return formattedValue;
	}

}
