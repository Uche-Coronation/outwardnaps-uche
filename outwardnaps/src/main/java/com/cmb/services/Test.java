/**
 * 
 */
package com.cmb.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.cmb.model.neft.PendingNeftData;
import com.cmb.model.neft.PendingNeftTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * @author waliu.faleye
 *
 */
public class Test {
	
	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		PendingNeftTransaction tranData = mapper.readValue("{\"responseCode\":\"00\",\"responseMessage\":\"Inward Presentment transactions Retrieval Successfully\",\"IPFDataStorelist\":[{\"Id\":1,\"BankCode\":\"559\",\"TotalValue\":150.00,\"MsgID\":1531,\"ItemCount\":1,\"ItemSequenceNo\":\"081331081457\",\"SerialNo\":\"36133121\",\"SortCode\":\"559150000\",\"AccountNo\":\"1990008109\",\"TranCode\":\"20\",\"Amount\":150.00,\"Currency\":\"NGN\",\"BankOfFirstDepositDate\":\"2019-10-07T08:00:01\",\"BankOfFirstDepositSortCode\":\"044150000\",\"PresentmentDate\":\"2019-10-07T00:00:00\",\"PayerName\":\"Tester\",\"Beneficiary\":\"Tester Testee\",\"BeneficiaryAccountNo\":null,\"BVNBeneficiary\":null,\"BVNPayer\":null,\"CollectionType\":\"71\",\"InstrumentType\":\"DB\",\"Narration\":\"Test\",\"PresentingBankSortCode\":\"044150000\",\"SpecialClearing\":false,\"InstrumentDate\":\"2019-10-07T00:00:00\",\"MICRRepairInd\":\"000000\",\"SettlementTime\":\"2019-10-07T14:00:00\",\"CycleNo\":\"01\",\"status\":null,\"approval\":null,\"finaclestatus\":null}]}", PendingNeftTransaction.class);
		PendingNeftData data = tranData.getTransactionList().get(0);
		System.out.println(LocalDateTime.parse(data.getBankOfFirstDepositDateDisplay()));
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS")));
	}

}
