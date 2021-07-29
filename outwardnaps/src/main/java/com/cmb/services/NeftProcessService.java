/**
 * 
 */
package com.cmb.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cmb.model.AcctId;
import com.cmb.model.FITransactionStatus;
import com.cmb.model.FITransferRequest;
import com.cmb.model.FITransferResponse;
import com.cmb.model.FeeRequest;
import com.cmb.model.FeeResponse;
import com.cmb.model.PartTrnRec;
import com.cmb.model.TrnAmt;
import com.cmb.model.neft.CollectionType;
import com.cmb.model.neft.NEFTBank;
import com.cmb.model.neft.NeftBaseBatchDetail;
import com.cmb.model.neft.NeftBatchDetail;
import com.cmb.model.neft.NeftBatchReturnDetail;
import com.cmb.model.neft.NeftOutwardResponse;
import com.cmb.model.neft.OutwardNeftItem;
import com.cmb.model.neft.OutwardNeftReturn;
import com.cmb.model.neft.PendingNeftData;
import com.cmb.model.neft.ReturnReason;
import com.cmb.neft.interfaces.CollectionTypeRepository;
import com.cmb.neft.interfaces.NeftBatchDetailRepository;
import com.cmb.neft.interfaces.NeftProcessServiceInterface;
import com.cmb.neft.interfaces.OutwardNeftItemRepository;
import com.cmb.neft.interfaces.PendingNeftDataRepository;
import com.cmb.neft.interfaces.ReturnReasonRepository;

/**
 * @author waliu.faleye
 *
 */
@Service
public class NeftProcessService implements NeftProcessServiceInterface {

	@Value("${neft.credit.gl}")
	private String neftCreditGlAccount;

	@Value("${neft.credit.commission.gl}")
	private String neftCommissionGlAccount;

	@Value("${neft.credit.vat.gl}")
	private String neftVatGlAccount;

	@Autowired
	NeftBatchDetailRepository batchRepo;

	@Autowired
	OutwardNeftItemRepository itemRepo;

	@Autowired
	PendingNeftDataRepository pendingNeftRepo;

	@Autowired
	BaseInterface baseInterface;

	@Value("${vat.percent}")
	private String percentVat;

	@Autowired
	List<NEFTBank> neftBanks;
	
	@Autowired
	CollectionTypeRepository collectionTypeRepository;
	
	@Autowired
	ReturnReasonRepository returnReasonRepository;

	@Override
	public List<NeftBatchDetail> findNeftBatchDetails() {
		// TODO Auto-generated method stub
		return batchRepo.findAll();
	}

	@Transactional
	@Override
	public NeftBatchDetail saveNeftBatchDetail(NeftBatchDetail neftBatchDetail) {
		// TODO Auto-generated method stub

		neftBatchDetail.getOutwardNeftItems().forEach(a -> a.setNeftBatchDetail(neftBatchDetail));
		NeftBatchDetail batchDetail = batchRepo.save(neftBatchDetail);
		return batchDetail;
	}

	@Override
	public Optional<NeftBatchDetail> findNeftBatchDetailById(Long neftBatchId) {
		// TODO Auto-generated method stub
		return batchRepo.findById(neftBatchId);
	}

	@Override
	public String formatAmount(BigDecimal value) {
		// System.out.println("formatting value == "+value);
		String format1 = "###,###,##0.00";

		DecimalFormat fm1 = new DecimalFormat(format1);

		String formattedValue = fm1.format(Double.valueOf(value.toString()));

		return formattedValue;
	}

	@Override
	public NeftOutwardResponse processNeftBatchTransactions(NeftBatchDetail neftBatchDetail) {
		// TODO Auto-generated method stub
		neftBatchDetail.setMsgId(Long.valueOf(neftBatchDetail.getMsgIdStr()));
		NeftOutwardResponse outwardNeftResp = null;
		// debit transaction
//		List<OutwardNeftItem> pendingDebitItems = neftBatchDetail.getOutwardNeftItems().parallelStream()
//				.filter(a -> !"success".equalsIgnoreCase(a.getDebitResponse())).collect(Collectors.toList());
//		for (OutwardNeftItem item : pendingDebitItems) {
//			FITransferRequest transferRequest = new FITransferRequest();
//			FITransactionStatus tranStatus = baseInterface.fiTransactionQueryStatus(item.getFiRequestUuid());
//			if (tranStatus != null && tranStatus.getBeTranStatus() != null
//					&& "success".equalsIgnoreCase(tranStatus.getBeTranStatus())) {
//				item.setDebitResponse(tranStatus.getBeTranStatus().toUpperCase());
//				item.setDebitResponseDesc(tranStatus.getBeTranStatus().toUpperCase());
//				// item.setDebitTranDateTime(fiResp.getTranDateTime());
//				// item.setDebitTranId(fiResp.getTranId());
//			} else {
//				transferRequest
//						.setReqUuid(item.getFiRequestUuid().substring(0, 20).concat(String.valueOf(item.getCounter())));
//				transferRequest.setRecs(getPartTransaction(item));
//				// if (!"success".equalsIgnoreCase(item.getDebitResponse())) {
//				FITransferResponse fiResp = baseInterface.fiFundTransfer(transferRequest);
//				if (fiResp != null) {
//					item.setDebitResponse(fiResp.getResponseCode());
//					item.setDebitResponseDesc(fiResp.getResponseDescription());
//					item.setDebitTranDateTime(fiResp.getTranDateTime());
//					item.setDebitTranId(fiResp.getTranId());
//				}
//			}
//			item.setFiRequestUuid(transferRequest.getReqUuid());
//			item.setCounter(Long.sum(item.getCounter() == null ? 0l : item.getCounter(), 1l));
//			itemRepo.save(item);
//			// }
//		}
//
//		List<OutwardNeftItem> successfulDebitItems = neftBatchDetail.getOutwardNeftItems().parallelStream()
//				.filter(a -> "success".equalsIgnoreCase(a.getDebitResponse())).collect(Collectors.toList());
//		List<OutwardNeftItem> failedDebitItems = neftBatchDetail.getOutwardNeftItems().parallelStream()
//				.filter(a -> !"success".equalsIgnoreCase(a.getDebitResponse())).collect(Collectors.toList());
//		System.out.println("successfulDebitItems.size() ===" + successfulDebitItems.size());
//		System.out.println("failedDebitItems.size() ===" + failedDebitItems.size());
//		if (!failedDebitItems.isEmpty()) {
//			/*
//			 * // reverse the successful entries successfulDebitItems.forEach(a -> {
//			 * FITransferReversal fiRequest = new FITransferReversal(a.getFiRequestUuid(),
//			 * a.getDebitTranDateTime(), a.getDebitTranId());
//			 * baseInterface.fiTransferReversal(fiRequest); });
//			 */
//		} else {
			// System.out.println("failedDebitItems.size() ===" + failedDebitItems.size());
			neftBatchDetail.setSettlementTimeF(LocalDateTime.now().toString());
			neftBatchDetail.setDateDisplay(neftBatchDetail.getDate() != null ? neftBatchDetail.getDate().toString()
					: LocalDateTime.now().toString());
			neftBatchDetail.getOutwardNeftItems().forEach(a -> {
				a.setBankOfFirstDepositDateDisplay(
						a.getBankOfFirstDepositDate() != null ? a.getBankOfFirstDepositDate().toString()
								: LocalDateTime.now().toString());
				a.setInstrumentDateDisplay(a.getInstrumentDate() != null ? a.getInstrumentDate().toString()
						: LocalDateTime.now().toString());
				a.setPresentmentDateDisplay(a.getPresentmentDate() != null ? a.getPresentmentDate().toString()
						: LocalDateTime.now().toString());
				a.setSettlementTimeDisplay(a.getSettlementTime() != null ? a.getSettlementTime().toString()
						: LocalDateTime.now().toString());
			});
			//if (!Strings.isNullOrEmpty(neftBatchDetail.getResponseCode())
			//		&& !neftBatchDetail.getResponseCode().equals("00")) {
				//outwardNeftResp = processNeftBatchReturn(neftBatchDetail);
			//} else {
				outwardNeftResp = baseInterface.neftOutwardSubmit(neftBatchDetail);
			//}
		//}
		return outwardNeftResp;
	}

	private Set<PartTrnRec> getPartTransaction(OutwardNeftItem item) {
		// TODO Auto-generated method stub
		Set<PartTrnRec> recs = new HashSet<>();
		PartTrnRec drRec = new PartTrnRec();
		AcctId drAcctId = new AcctId();
		drAcctId.setAcctId(item.getAccountNo());
		drRec.setAcctId(drAcctId);
		drRec.setCreditDebitFlg("D");
		drRec.setSerialNum(1l);
		TrnAmt drTrnAmt = new TrnAmt();
		drTrnAmt.setAmountValue(item.getAmount().toString());
		drTrnAmt.setCurrencyCode("NGN");
		drRec.setTrnAmt(drTrnAmt);
		drRec.setTrnParticulars(item.getNarration());

		recs.add(drRec);

		PartTrnRec crRec = new PartTrnRec();
		AcctId crAcctId = new AcctId();
		crAcctId.setAcctId(neftCreditGlAccount);
		crRec.setAcctId(crAcctId);
		crRec.setCreditDebitFlg("C");
		crRec.setSerialNum(2l);
		TrnAmt crTrnAmt = new TrnAmt();
		crTrnAmt.setAmountValue(item.getAmount().toString());
		crTrnAmt.setCurrencyCode("NGN");
		crRec.setTrnAmt(crTrnAmt);
		crRec.setTrnParticulars(item.getNarration());

		recs.add(crRec);

		if (!item.isCustomerWaiveCharge()) {
			FeeRequest feeRequest = new FeeRequest(item.getAmount().toString(), "NEFT");
			FeeResponse feeResponse = baseInterface.getChannelFee(feeRequest);
			if (feeResponse != null) {
				PartTrnRec drFeeRec = new PartTrnRec();
				AcctId drFeeAcctId = new AcctId();
				drFeeAcctId.setAcctId(item.getAccountNo());
				drFeeRec.setAcctId(drFeeAcctId);
				drFeeRec.setCreditDebitFlg("D");
				drFeeRec.setSerialNum(3l);
				TrnAmt drFeeTrnAmt = new TrnAmt();
				drFeeTrnAmt.setAmountValue(feeResponse.getFeeValue());
				drFeeTrnAmt.setCurrencyCode("NGN");
				drFeeRec.setTrnAmt(drFeeTrnAmt);
				drFeeRec.setTrnParticulars(item.getNarration());

				recs.add(drFeeRec);

				PartTrnRec crFeeRec = new PartTrnRec();
				AcctId crFeeAcctId = new AcctId();
				crFeeAcctId.setAcctId(neftCommissionGlAccount);
				crFeeRec.setAcctId(crFeeAcctId);
				crFeeRec.setCreditDebitFlg("C");
				crFeeRec.setSerialNum(4l);
				TrnAmt crFeeTrnAmt = new TrnAmt();
				crFeeTrnAmt.setAmountValue(feeResponse.getFeeValue());
				crFeeTrnAmt.setCurrencyCode("NGN");
				crFeeRec.setTrnAmt(crFeeTrnAmt);
				crFeeRec.setTrnParticulars(item.getNarration());

				recs.add(crFeeRec);

				String vat = formatAmount(new BigDecimal(feeResponse.getFeeValue()).multiply(new BigDecimal(percentVat))
						.divide(new BigDecimal("100")));

				PartTrnRec drVatRec = new PartTrnRec();
				AcctId drVatAcctId = new AcctId();
				drVatAcctId.setAcctId(item.getAccountNo());
				drVatRec.setAcctId(drVatAcctId);
				drVatRec.setCreditDebitFlg("D");
				drVatRec.setSerialNum(5l);
				TrnAmt drVatTrnAmt = new TrnAmt();
				drVatTrnAmt.setAmountValue(vat);
				drVatTrnAmt.setCurrencyCode("NGN");
				drVatRec.setTrnAmt(drVatTrnAmt);
				drVatRec.setTrnParticulars(item.getNarration());

				recs.add(drVatRec);

				PartTrnRec crVatRec = new PartTrnRec();
				AcctId crVatAcctId = new AcctId();
				crVatAcctId.setAcctId(neftVatGlAccount);
				crVatRec.setAcctId(crVatAcctId);
				crVatRec.setCreditDebitFlg("C");
				crVatRec.setSerialNum(6l);
				TrnAmt crVatTrnAmt = new TrnAmt();
				crVatTrnAmt.setAmountValue(vat);
				crVatTrnAmt.setCurrencyCode("NGN");
				crVatRec.setTrnAmt(crVatTrnAmt);
				crVatRec.setTrnParticulars(item.getNarration());

				recs.add(crVatRec);

			}

		}

		return recs;
	}

	@Override
	public NeftOutwardResponse processNeftBatchReturn(NeftBatchDetail neftBatchDetail) {
		NeftBaseBatchDetail baseBatchDetail = neftBatchDetail;
		System.out.println("baseBatchDetail.getAppid()===" + baseBatchDetail.getAppid());
		NeftBatchReturnDetail returnDetail = new NeftBatchReturnDetail(baseBatchDetail);
		// NeftBaseBatchDetail baseBatchDetail = neftBatchDetail;
		// returnDetail = (NeftBatchReturnDetail) baseBatchDetail;
		System.out.println("returnDetail.getAppid()===" + returnDetail.getAppid());
		Set<OutwardNeftReturn> items = new HashSet<OutwardNeftReturn>();
		for (OutwardNeftItem mainItem : neftBatchDetail.getOutwardNeftItems()) {
			// OutwardNeftBaseItem base = mainItem;
			OutwardNeftReturn returnItem = new OutwardNeftReturn(mainItem);
			returnItem.setCheckDigit("123");
			returnItem.setExpiryDate("20230501");
			returnItem.setReturnReason("Connection issue");
			returnItem.setSessionId("123");
			items.add(returnItem);
		}
		returnDetail.setOutwardNeftReturns(items);
		returnDetail.setDateDisplay(
				returnDetail.getDate() != null ? returnDetail.getDate().toString() : LocalDateTime.now().toString());
		returnDetail.getOutwardNeftReturns().forEach(a -> {
			a.setSettlementTimeDisplay(
					a.getSettlementTime() != null ? a.getSettlementTime().toString() : LocalDateTime.now().toString());
		});

		return baseInterface.neftOutwardSubmitReturn(returnDetail);
	}

	@Override
	public List<NEFTBank> findNeftBanks() {
		// TODO Auto-generated method stub
		return neftBanks;
	}

	@Override
	public List<NEFTBank> findNeftBankBySortCode(String sortCode) {
		// TODO Auto-generated method stub
		List<NEFTBank> banks = new ArrayList<NEFTBank>();
		banks = neftBanks.parallelStream().filter(a -> a.getSortCode().equals(sortCode)).collect(Collectors.toList());
		return banks;
	}

	@Override
	public Optional<OutwardNeftItem> findOutwardNeftItemById(Long itemId) {
		// TODO Auto-generated method stub
		return itemRepo.findById(itemId);
	}

	@Override
	public OutwardNeftItem findOutwardNeftItemByIdAndPaymentStatus(Long itemId, String paymentStatus) {
		// TODO Auto-generated method stub
		return itemRepo.findByIdAndPaymentStatus(itemId, paymentStatus);
	}

	@Override
	public NeftOutwardResponse processNeftDebitBatchTransactions(NeftBatchDetail neftBatchDetail) {
		// TODO Auto-generated method stub
		neftBatchDetail.setMsgId(Long.valueOf(neftBatchDetail.getMsgIdStr()));
		NeftOutwardResponse outwardNeftResp = null;

		// System.out.println("failedDebitItems.size() ===" + failedDebitItems.size());
		neftBatchDetail.setSettlementTimeF(LocalDateTime.now().toString());
		neftBatchDetail.setDateDisplay(neftBatchDetail.getDate() != null ? neftBatchDetail.getDate().toString()
				: LocalDateTime.now().toString());
		neftBatchDetail.getOutwardNeftItems().forEach(a -> {
			a.setBankOfFirstDepositDateDisplay(
					a.getBankOfFirstDepositDate() != null ? a.getBankOfFirstDepositDate().toString()
							: LocalDateTime.now().toString());
			a.setInstrumentDateDisplay(
					a.getInstrumentDate() != null ? a.getInstrumentDate().toString() : LocalDateTime.now().toString());
			a.setPresentmentDateDisplay(a.getPresentmentDate() != null ? a.getPresentmentDate().toString()
					: LocalDateTime.now().toString());
			a.setSettlementTimeDisplay(
					a.getSettlementTime() != null ? a.getSettlementTime().toString() : LocalDateTime.now().toString());
		});
		outwardNeftResp = baseInterface.neftOutwardSubmit(neftBatchDetail);

		return outwardNeftResp;
	}

	@Override
	public PendingNeftData savePendingNeftData(PendingNeftData pendingNeftData) {
		// TODO Auto-generated method stub
		return pendingNeftRepo.save(pendingNeftData);
	}

	@Override
	public List<PendingNeftData> saveAllPendingNeftData(List<PendingNeftData> pendingNeftData) {
		// TODO Auto-generated method stub
		return pendingNeftRepo.saveAll(pendingNeftData);
	}

	@Override
	public List<PendingNeftData> findAllPendingNeftData() {
		// TODO Auto-generated method stub
		return pendingNeftRepo.findAll();
	}

	@Override
	public Optional<PendingNeftData> findPendingNeftDataById(Long id) {
		// TODO Auto-generated method stub
		return pendingNeftRepo.findById(id);
	}

	@Override
	public List<CollectionType> findAllCollectionTypes() {
		// TODO Auto-generated method stub
		return collectionTypeRepository.findAll();
	}

	@Override
	public List<ReturnReason> findAllReturnReasons() {
		// TODO Auto-generated method stub
		return returnReasonRepository.findAll();
	}

	@Override
	public PendingNeftData findPendingNeftDataByMsgIdAndItemSequenceNo(Long msgId, String itemSequenceNo) {
		// TODO Auto-generated method stub
		return pendingNeftRepo.findByMsgIdAndItemSequenceNo(msgId, itemSequenceNo);
	}

}
