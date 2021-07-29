/**
 * 
 */
$(document).ready(function() {
	// alert('testing')
	var url = window.location;

	// x.style.display = 'none';
	// var verifiedAccountDiv = document.getElementById('verifiedAccountDiv');
	// verifiedAccountDiv.style.display = 'none';
	$('#orderingPartyAccountNumber').change(function(event) {
		// var bankCode = $("#bank1").val();
		var accountNumber = $("#orderingPartyAccountNumber").val();
		// alert('testing acct: ' + accountNumber + ' bank == ' + bankCode)
		fire_ajax_webservice();
	});
	/***************************************************************************
	 * $('#referralOptionId').change(function(event) { referralChange(); });
	 * $('#bvn').change(function(event) { var bankCode = $("#bank1").val(); var
	 * accountNumber = $("#accountNumber").val(); // alert('testing acct: ' +
	 * accountNumber + ' bank == ' + bankCode) // fire_ajax_webservice(); });
	 **************************************************************************/

});

function fire_ajax_webservice() {
	// var bvn = $("#bvn").val();
	var accountNumber = $("#orderingPartyAccountNumber").val();
	console.log("where are you : " + accountNumber);
	// searchViaAjax();
	// $( "#accountNumber" ).load( "resources/templates/error.html" );
	var search = {}
	search["accountNumber"] = accountNumber;
	// search["bank"] = $("#bank1").val();

	$.ajax({
		type : "post",
		contentType : "application/json",
		url : "http://132.10.200.172:9292/service/accountDetail",
		data : JSON.stringify(search),
		dataType : 'json',
		// async: false,
		cache : false,
		timeout : 600000,
		success : function(data) {

			console.log("SUCCESS : ", data);
			console.log("SUCCESS : ", data["responseCode"]);
			console.log("account number == ",accountNumber);

			if (accountNumber != '') {
				if ("00" != data["responseCode"]) {
					$("#orderingPartyAccountName").val('');
				} else {
					$("#orderingPartyAccountName").val(data["accountName"]);
				}
			} else {
				console.log("empty account number ");
				$("#orderingPartyAccountName").val('');
			}

		},
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#verifiedName').html(json);

			console.log("ERROR : ", e);
		}
	});

}

function fire_ajax_formsubmit() {

	var $form = $(this), url = $form.attr('action');
	alert('submit 2');
	/* Send the data using post */
	var posting = $.post(url);

	/* Alerts the results */
	posting.done(function(data) {
		alert('success');
	});

}

function referralChange() {

	var referralOptionId = $("#referralOptionId").val();
	var referralName = document.getElementById('referralName');
	var otherReferralDetail = document.getElementById('otherReferralDetail');
	if (referralOptionId == "Referral") {
		// console.log("1 referralOptionId >> ", referralOptionId);
		referralName.disabled = false;
	} else {
		// console.log("2 referralOptionId >> ", referralOptionId);
		$("#referralName").val('')
		referralName.disabled = true;
	}
	if (referralOptionId == "Others") {
		// console.log("1 referralOptionId >> ", referralOptionId);
		otherReferralDetail.disabled = false;
	} else {
		// console.log("2 referralOptionId >> ", referralOptionId);
		$("#otherReferralDetail").val('')
		otherReferralDetail.disabled = true;
	}

}

function chooseApplicant() {
	var incorporationNumber = document.getElementById('incorporationNumber');
	var otherName = document.getElementById('otherName');
	$("#individualApplicant").change(function() {
		if (this.checked) {
			// Do stuff
			// console.log('ind');
			$("#incorporationNumber").val('');
			incorporationNumber.disabled = true;
			otherName.required = true;
		} else {
			incorporationNumber.disabled = false;
			otherName.required = false;

		}
	});
	$("#jointApplicant").change(function() {
		if (this.checked) {
			// Do stuff
			// console.log('joi');
			incorporationNumber.disabled = false;
			otherName.required = false;
		} else {
			$("#incorporationNumber").val('');
			incorporationNumber.disabled = true;
			otherName.required = true;

		}
	});
}

function nameEnquiry() {
	console.log('new method ...');
	var search = {}
	search["accountNumber"] = accountNumber;
	search["bank"] = $("#bank1").val();
	// make the AJAX request, dataType is set to json
	// meaning we are expecting JSON data in response from the server
	$.ajax({
		type : "POST",
		url : "${home}/service/nipenquiry",
		data : JSON.stringify(search),
		dataType : 'jsonp',
		jsonp : 'callback',
		jsonpCallback : 'jsonpCallback',

		// if received a response from the server
		success : function(data, textStatus, jqXHR) {
			// our country code was correct so we have some information
			// to
			// display
			if (data.success) {
			}
			// display error message
			else {

			}
		},// If there was no resonse from the server
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("Something really bad happened " + textStatus);
			$("#ajaxResponse").html(jqXHR.responseText);
		},

		// capture the request before it was sent to server
		beforeSend : function(jqXHR, settings) {
			// disable the button until we get the response
			// $('#myButton').attr("disabled", true);
		},

		// this is called after the response or error functions are
		// finsihed
		// so that we can take some action
		complete : function(jqXHR, textStatus) {
			// enable the button
			// $('#myButton').attr("disabled", false);
		}

	});
}
function searchViaAjax() {

	var search = {}
	search["username"] = "waliu@gmail.com";
	search["email"] = "waliu@gmail.com";
	var bankId = $("#bank1").val();
	var accountNumber = $("#accountNumber").val();
	console.log("JSON.stringify(search):", JSON.stringify(search));
	$.ajax({
		type : "GET",
		// contentType : "application/json",
		// url :
		// "/search/api/getSearchResult?username="+username+"&email="+email,
		url : "/service/nipenquiry?bankId=" + bankId + "&accountNumber="
				+ accountNumber,
		// url : "/test",
		// data : JSON.stringify(search),
		// dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			// display(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
		},
		complete : function() {
			window.location = 'home.html';
		},
		done : function(e) {
			console.log("DONE");
			enableSearchButton(true);
		}
	});

}
