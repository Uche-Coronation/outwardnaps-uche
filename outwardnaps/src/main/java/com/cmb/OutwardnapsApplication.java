package com.cmb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cmb.interfaces.FinancialInstitutionRepository;
import com.cmb.interfaces.MaximumAuthorizationLevelsRepository;
import com.cmb.interfaces.TransactionStatusRepository;
import com.cmb.interfaces.UserStatusRepository;
import com.cmb.model.FinancialInstitution;
import com.cmb.model.MaximumAuthorizationLevels;
import com.cmb.model.SevenupConfig;
import com.cmb.model.TransactionStatus;
import com.cmb.model.UserStatus;
import com.cmb.model.neft.CollectionType;
import com.cmb.model.neft.NEFTBank;
import com.cmb.model.neft.ReturnReason;
import com.cmb.neft.interfaces.NEFTBankRepository;
import com.cmb.neft.interfaces.NeftProcessServiceInterface;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value = SevenupConfig.class)
public class OutwardnapsApplication {
	
	@Autowired
	TransactionStatusRepository trnStatusRepo;
	
	@Autowired
	NEFTBankRepository neftBankRepo;
	
	@Autowired
	FinancialInstitutionRepository financialInstitutionRepo;
	
	@Autowired
	MaximumAuthorizationLevelsRepository malRepo;
	
	@Autowired
	UserStatusRepository userStatusRepo;
	
	@Autowired
	NeftProcessServiceInterface neftInterface;

	public static void main(String[] args) {
		SpringApplication.run(OutwardnapsApplication.class, args);
	}

	@Bean
	public List<NEFTBank> getNeftBanks() {
		return neftBankRepo.findAll();
	}

	@Bean
	public List<TransactionStatus> getTransactionStatuses() {
		return trnStatusRepo.findAll();
	}

	@Bean
	public List<FinancialInstitution> getFinancialInstitutions() {
		return financialInstitutionRepo.findAll();
	}
	
	@Bean
	public List<MaximumAuthorizationLevels> getMaximumAutorizationLevels() {
		return malRepo.findAll();
	}
	
	@Bean
	public List<UserStatus> getUserStatuses() {
		return userStatusRepo.findAll();
	}
	
	@Bean
	public List<CollectionType> getCollectionTypes() {
		return neftInterface.findAllCollectionTypes();
	}
	
	@Bean
	public List<ReturnReason> getReturnReasons() {
		return neftInterface.findAllReturnReasons();
	}
}
