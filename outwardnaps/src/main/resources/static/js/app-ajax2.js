/**
 * 
 */
document.addEventListener("DOMContentLoaded", function(event) {

	/***************************************************************************
	 * document .getElementById("submit") .addEventListener( "click", function() {
	 * var transactionAmount = $("#transactionAmount").val(); var
	 * transactionCurrency = $("#currency").val(); var customerEmail =
	 * $("#customerEmail").val(); //alert('testing acct: ' +
	 * transactionCurrency) var flw_ref = "", chargeResponse = "", trxref =
	 * "FDKHGK" + Math.random(), pubkey =
	 * "FLWPUBK-4e89f89528693434b14647e83598d546-X"; getpaidSetup({
	 * customer_email : customerEmail, payment_method : "card", amount :
	 * transactionAmount, country : "NG", currency : transactionCurrency,
	 * custom_logo : "", custom_description : "", custom_title : "Forte Pay",
	 * txref : trxref, PBFPubKey : pubkey, onclose : function(response) { },
	 * callback : function(response) { // flw_ref = // response.tx.flwRef; } });
	 * });
	 **************************************************************************/

	/*
	 * document.getElementById("generateOTP").addEventListener( "click",
	 * function() {
	 * 
	 * $("form").submit(function(e) { e.preventDefault(); }); var phoneNumber =
	 * $("#mobileNumber").val(); fire_ajax_generateOtp_webservice(); });
	 */

	document.getElementById("submitPay").addEventListener("click", function() {
		$("form").submit(function(e) {
			e.preventDefault();
		});		
		test();
		//var transactionAmount = $("#amountStr").val();
		var transactionAmount = $("#amount").val();
		var transactionCurrency = $("#currency").val();
		var customerEmail = $("#email").val();
		var paymentMethod = $("#paymentMethod").val();
		var trxref = $("#txRef").val();
		//var hashValue = $("#hashValue").val();
		var hashValue = '';
		console.log("paymentMethod : ", paymentMethod);
		console.log("hashValue : ", hashValue);
		console.log("trxref : ", trxref);
		var pubkey = $("#fwPubk").val();
		var customerName = $("#customerName").val();
		var otherName = $("#otherName").val();
		var mobileNumber = $("#mobileNumber").val();
		var flw_ref = "", chargeResponse = "";// , pubkey =
		// "FLWPUBK-33197ed68c8a9ec75173301a5521f56b-X";
		console.log("transactionAmount : ", transactionAmount);
		getpaidSetup({
			customer_email : customerEmail,
			payment_method : paymentMethod,
			amount : transactionAmount,
			country : "NG",
			currency : transactionCurrency,
			// recurring_stop: "2017-07-06",
			custom_logo : "",
			custom_description : "",
			custom_title : "Coronation Mutual Fund Pay",
			txref : trxref,
			customer_firstname : customerName,
			customer_lastname : otherName,
			customer_phone : mobileNumber,
			PBFPubKey : pubkey,
			integrity_hash : hashValue,
			onclose : function(response) {
			},
			callback : function(response) {
				flw_ref = response.tx.flwRef;
				chargeResponse = response.tx.chargeResponseCode;
				var respcode = response.respcode;
				console.log("respcode : ", respcode);
				console.log("response : ", response);
				console.log("flw_ref : ", flw_ref);
				console.log("chargeResponse : ", chargeResponse);
				if (respcode == '00' || respcode == '0' || chargeResponse == '00' || chargeResponse == '0') {
					//requery(flw_ref);
					fire_submit("PAID");
					//$('#paymentStatus').val("PAID");
				}
				else
				{
					fire_submit("FAILED");					
				}

			}
		});
	});

	/***************************************************************************
	 * document .getElementById("submitaccountcard") .addEventListener( "click",
	 * function() { var transactionAmount = $("#transactionAmount").val(); var
	 * transactionCurrency = $("#currency").val(); var customerEmail =
	 * $("#customerEmail").val(); var flw_ref = "", chargeResponse = "", trxref =
	 * "FDKHGK" + Math.random(), pubkey =
	 * "FLWPUBK-4e89f89528693434b14647e83598d546-X"; getpaidSetup({
	 * customer_email : customerEmail, payment_method : "both", amount :
	 * transactionAmount, country : "NG", currency : transactionCurrency,
	 * custom_logo : "", custom_description : "", custom_title : "Forte Pay",
	 * txref : trxref, PBFPubKey : pubkey, onclose : function(response) { },
	 * callback : function(response) { // flw_ref = response.tx.flwRef; } });
	 * });
	 **************************************************************************/
});
function divCheckPaymentMethod() {
	var paymentMethod = $("#paymentMethod").val();
	console.log('paymentMethod == ',paymentMethod);
	var x = document.getElementById('card');
	var y = document.getElementById('ibank');
	if (paymentMethod === 'card') {
		x.style.display = 'block';
		y.style.display = 'none';
	} else {
		x.style.display = 'none';
		y.style.display = 'block';
	}
}
function paymentLimit() {
	divCheckPaymentMethod();
	$('#paymentMethod').change(function(event) {		
		divCheckPaymentMethod();
	});
}

/**
 * 
 */
$(document).ready(function() {
	paymentLimit();
	// var x = document.getElementById('methodDiv');
	// x.style.display = 'none';
	$('#paymentChannel').change(function(event) {
		var paymentChannel = $("#paymentChannel").val();
		// alert('paymentChannel: ' + paymentChannel)
		var x = document.getElementById('methodDiv');
		if (paymentChannel === 'FLUTTERWAVE') {
			x.style.display = 'block';
		} else {
			x.style.display = 'none';
		}
	});

});
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

function fire_ajax_generateOtp_webservice() {

	var search = {}
	search["phoneNumber"] = $("#mobileNumber").val();
	// search["bank"] = $("#bank1").val();

	$.ajax({
		type : "post",
		contentType : "application/json",
		// url : "http://132.10.200.117:8080/brokerage-1.0/service/nipenquiry",
		url : "http://132.10.200.140:9292/service/generateOTP",
		data : JSON.stringify(search),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {

			var json = "<h4>Ajax Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			// $('#verifiedName').html(json);

			console.log("SUCCESS : ", data);
			console.log("SUCCESS : ", data["responseCode"]);
			// $('#verifiedBVN').val(data["customerBVN"]);

		},
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			// $('#verifiedName').html(json);

			console.log("ERROR : ", e);
		}
	});

}

function fire_ajax_authenticateOtp_webservice() {
	console.log("otp : ", $("#OTP").val());
	var search = {}
	search["otp"] = $("#OTP").val();
	// search["bank"] = $("#bank1").val();

	$.ajax({
		type : "post",
		contentType : "application/json",
		// url : "http://132.10.200.117:8080/brokerage-1.0/service/nipenquiry",
		url : "http://132.10.200.140:9292/service/authenticateOTP",
		data : JSON.stringify(search),
		dataType : 'json',
		async : false,
		cache : false,
		timeout : 600000,
		success : function(data) {

			var json = "<h4>Ajax Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			// $('#verifiedName').html(json);

			console.log("SUCCESS 1 : ", data);
			console.log("SUCCESS 2 : ", data["responseCode"]);
			$('#otpAuthenticateResponse').val(data["responseCode"]);
			console.log("otpAuthenticateResponse 2 : ", $(
					'#otpAuthenticateResponse').val());

		},
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			// $('#verifiedName').html(json);

			console.log("ERROR : ", e);
		}
	});

}

function fire_submit(paymentStatus) {
	// data = $(this).serializeArray();
	var datastring = $("#paymentfw-form").serialize();
	console.log('In to submit..');

	/* I put the above code for check data before send to ajax */
	$.ajax({
		contentType : "application/json",
		//url : "https://132.10.200.117:443/submitPayment",
		// url: "http://localhost:9293/submitPayment",
		//url : "https://197.253.10.18:443/submitPayment",
		url : '/service/submitPayment?id='+$("#id").val()+"&paymentStatus="+paymentStatus+"&paymentMethod="+$("#paymentMethod").val(),
		type : 'GET',
		//data : datastring,
		dataType : 'json',
		success : function(data) {
			/*
			 * console.log(data); if (data.success) { console.log('success'); }
			 * else { console.log('failed'); }
			 */
		}
	});
	return false;

}

function fire_getHashedValue() {

	// var datastring = $("#paymentfw-form").serialize();
	console.log('In to fire_getHashedValue..');
	var amount = $("#amount").val();
	var currency = $("#currency").val();
	var email = $("#email").val();
	var paymentMethod = $("#paymentMethod").val();
	var txRef = $("#txRef").val();
	var firstName = $("#customerName").val();
	var lastName = $("#otherName").val();
	var phone = $("#mobileNumber").val();
	console.log('In to fire_getHashedValue..', amount);
	console.log('In to fire_getHashedValue..', currency);
	console.log('In to fire_getHashedValue..', email);
	console.log('In to fire_getHashedValue..', paymentMethod);
	console.log('In to fire_getHashedValue..', txRef);
	console.log('In to fire_getHashedValue..', firstName);
	console.log('In to fire_getHashedValue..', lastName);
	console.log('In to fire_getHashedValue..', phone);

	var search = {}
	search["amount"] = amount;
	/*
	 * search["currency"] = currency; search["email"] = email;
	 * search["paymentMethod"] = paymentMethod; search["txRef"] = txRef;
	 * search["firstName"] = firstName; search["lastName"] = lastName;
	 * search["phone"] = phone;
	 */

	$.ajax({
		type : "post",
		contentType : "application/json",
		// url : "http://132.10.200.117:8080/brokerage-1.0/service/nipenquiry",
		//url : "https://132.10.200.117:443/service/getHashedValues",
		// url : "http://localhost:9293/service/getHashedValues",
		url : "https://197.253.10.18:443/service/getHashedValues",
		// data : JSON.stringify(search),
		data : encodeURIComponent(JSON.stringify(search)),

		dataType : 'json',
		async : false,
		cache : false,
		timeout : 600000,
		success : function(data) {

			var json = "<h4>Ajax Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			$('#verifiedName').html(json);

			console.log("SUCCESS : ", data);
			console.log("SUCCESS : ", data["responseCode"]);
			$('#hashValue').val(data["responseCode"]);

		},
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#hashValue').html(json);

			console.log("ERROR : ", e);
		}
	});

}

function test() {
	var bvn = $("#bvn").val();
	var accountNumber = $("#accountNumber").val();
	console.log("where are you : " + bvn);

	var amount = $("#amount").val();
	var currency = $("#currency").val();
	var email = $("#email").val();
	var paymentMethod = $("#paymentMethod").val();
	var txRef = $("#txRef").val();
	var firstName = $("#customerName").val();
	var lastName = $("#otherName").val();
	var phone = $("#mobileNumber").val();

	var search = {}
	search["accountNumber"] = accountNumber;
	search["bank"] = $("#bank1").val();

	search["amount"] = amount;
	search["currency"] = currency;
	search["email"] = email;
	search["paymentMethod"] = paymentMethod;
	search["txRef"] = txRef;
	search["customerName"] = firstName;
	search["otherName"] = lastName;
	search["mobileNumber"] = phone;

	$.ajax({
		//type : "post",
		type : "GET",
		contentType : "application/json",
		// url : "http://132.10.200.117:8080/brokerage-1.0/service/nipenquiry",
		// url : "http://132.10.200.117:9293/service/nipenquiry",
		//url : "https://132.10.200.117:443/service/hashValue",
		//url : "https://197.253.10.18:443/service/hashValue",
		url : "/service/hashValue?id="+$("#id").val()+"&paymentMethod="+$("#paymentMethod").val(),
		//data : JSON.stringify(search),
		dataType : 'json',
		async : false,
		cache : false,
		timeout : 600000,
		success : function(data) {

			var json = "<h4>Ajax Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			$('#verifiedName').html(json);

			console.log("SUCCESS : ", data);
			console.log("SUCCESS : ", data["responseCode"]);
			console.log("SUCCESS : ", data["feeValue"]);
			$('#hashValue').val(data["responseCode"]);
			$('#amountStr').val(data["feeValue"]);

		},
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#hashValue').html(json);

			console.log("ERROR : ", e);
		}
	});

}

function requery(flw_ref) {

	var enteredAmount = $("#amount").val();
	var search = {}
	search["amount"] = enteredAmount;
	search["txRef"] = flw_ref;
	console.log('flw_ref ==', flw_ref)
	$.ajax({
		type : "post",
		contentType : "application/json",
		// url :
		// "http://132.10.200.117:8080/brokerage-1.0/service/nipenquiry",
		//url : "https://132.10.200.117:443/service/requery",
		url : "https://197.253.10.18:443/service/requery",
		// url :
		// "http://flw-pms-dev.eu-west-1.elasticbeanstalk.com/flwv3-pug/getpaidx/api/verify",
		data : JSON.stringify(search),
		dataType : 'json',
		async : false,
		cache : false,
		timeout : 600000,
		success : function(data) {

			var json = "<h4>Ajax Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";

			console.log("SUCCESS : ", data);
			console.log("status : ", data.status);
			console.log("message : ", data["message"]);
			console.log("response amount : ", data["data"].amount);
			// $('#hashValue').val(data["responseCode"]);
			if (data.status == 'success') {
				console.log('correct txn');
				fire_submit();
				$('#paymentStatus').val("PAID");

			} else {
				// $('#paymentStatus').val("PAID");
				// fire_submit();
			}

		},
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#hashValue').html(json);

			console.log("ERROR : ", e);
		}
	});

}
