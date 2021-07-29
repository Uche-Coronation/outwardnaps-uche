package com.cmb.services;

import java.util.List;

import com.cmb.interfaces.RRRGenDetailRepository;
import com.cmb.model.remita.AuthDetail;
import com.cmb.model.remita.BillerRes;
import com.cmb.model.remita.RRRGenDetail;
import com.cmb.model.remita.billpaymentnotification.BillPaymtReq;
import com.cmb.model.remita.billpaymentnotification.BillPaymtRes;
import com.cmb.model.remita.customfield.CustomFieldReq;
import com.cmb.model.remita.customfield.Customfield;
import com.cmb.model.remita.genrrr.GenrrrReq;
import com.cmb.model.remita.genrrr.GenrrrRes;
import com.cmb.model.remita.paymentstatus.PaymtstatusReq;
import com.cmb.model.remita.paymentstatus.PaymtstatusRes;
import com.cmb.model.remita.receipt.Biller;
import com.cmb.model.remita.rrrdetail.RRRdetRes;
import com.cmb.model.remita.servicetype.BillerServicetypeRes;
import com.cmb.model.remita.validaterequest.VdReq;
import com.cmb.model.remita.validaterequest.res.VdRes;

public interface RemitaService {
	public BillerRes getBiller();
	public BillerServicetypeRes getBillerServicetype(String billerid);
	public Customfield customFields(CustomFieldReq customFieldReq);
	public VdRes validateRequest(VdReq vdReq);
	public GenrrrRes generaterrr(GenrrrReq genrrrReq,String acct,String user);
	public RRRdetRes rrrdetails(String rrr,String user);
	public BillPaymtRes billpaymentnotification(BillPaymtReq billPaymtReq);
	public PaymtstatusRes paymentstatus(PaymtstatusReq paymtstatusReq);
	public Biller printreceipt();
	public String generateHashedStringSHA512(String hashString);
	public List<RRRGenDetail> getAllInitiatedRRR();
	public List<AuthDetail> getAllAwaitAuth();
	public RRRGenDetail getInitiatedRRR(Long id);
	public RRRGenDetail getInitiatedRRRById(Long id);
	public AuthDetail getAuthRRRById(Long id);
	public BillPaymtRes billpaymentnotify(String rrr,String debitacct,String debitamt,String tellername,String token,String approval,String comment);
	public BillPaymtRes billpaymentinitiate(String rrr,String debitacct,String debitamt,String tellername,String token);
	public String printremitareceipt(String rrr);

}
