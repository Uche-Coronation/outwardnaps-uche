/**
 * 
 */
package com.cmb.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author waliu.faleye
 *
 */

@SuppressWarnings("deprecation")
@Data
public class FITransferRequest {
	
	@Valid
	private Set<PartTrnRec> recs = new HashSet<>();
	
	@NotBlank @NotNull
	private String reqUuid;

	@JsonIgnore	
	private Long id;
	
	@JsonIgnore
	private String response;
	
	@JsonIgnore
	private String transactionTime;
	
	@JsonIgnore
	private String tranId;
	
	@JsonIgnore
	private LocalDateTime entryTime;
	
	@JsonIgnore
	private String responseDescription;

}
