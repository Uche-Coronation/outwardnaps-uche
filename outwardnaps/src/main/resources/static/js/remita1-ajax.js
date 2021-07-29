$(document).ready(function() {
	// alert('testing')
	var url = window.location;

	//$('#validatesubmit').click(function(){
	//	fire_ajax_click_webservice();
	//});

	/***************************************************************************
	 * $('#referralOptionId').change(function(event) { referralChange(); });
	 * $('#bvn').change(function(event) { var bankCode = $("#bank1").val(); var
	 * accountNumber = $("#accountNumber").val(); // alert('testing acct: ' +
	 * accountNumber + ' bank == ' + bankCode) // fire_ajax_webservice(); });
	 **************************************************************************/

});

function fire_ajax_click_webservice() {
	// var bvn = $("#bvn").val();
	var billerid = $("#serviceType").val();
	var amt = $("#amount").val();
	var cur = $("#currency").val();
	var phone = $("#payerPhone").val();
	var name = $("#payerName").val();
	var email = $("#payerEmail").val();
	var	cotyp = $("#cotyp").val();
	var	cfpid = $("#cfpid").val();
	var	cfcid = $("#cfcid").val();
	var	cfcamt = $("#cfcamt").val();
	var	cfccount = $("#cfccount").val();
	var	cfcsubmit = $("#cfcsubmit").val();
	console.log("where are you : " + billerid);
	// searchViaAjax();
	// $( "#accountNumber" ).load( "resources/templates/error.html" );
	var search = {};
	var cus={};
	search["appid"] ="cb01";
	search["hash"] = "x";
	search["billId"] = billerid;
	search["amount"] = amt;
	search["payerPhone"] = phone;
	search["currency"] = cur;
	search["payerName"] = name;
	search["payerEmail"] = email;
	search["cotyp"] = cotyp;
	search["cfpid"] = cfpid;
	search["cfcid"] = cfcid;
	search["cfcamt"] = cfcamt;
	search["cfccount"] = cfcamt;
	search["cfcsubmit"] = cfcamt;
	// search["bank"] = $("#bank1").val();
	var url11=document.location.origin +"/payment-portal/validateRemita";
	console.log("url : ", url11);
	$.ajax({
		type : "post",
		contentType : "application/json",
		//url : "http://132.10.200.172:9292/service/accountDetail",

		url : url11,
		data : JSON.stringify(search),
		dataType : 'json',
		// async: false,
		cache : false,
		timeout : 600000,
		success : function(data) {

                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
				   console.log("Response : ", data);
				   console.log("Validate response code : ", data["responseCode"]);

				   var ft= data["responseCode"];
				   if(ft == "00"){
					   console.log("Validation response message : ", data["responseMsg"]);
					  // fire_ajax_click_webservice2();
                //   $('#selectServiceType').append('<option value="' + value.id + '">' + value.name + '</option>');
               }else{
            	   var json = "<h4>Error Validating Form data </h4>";
			$('#feedback').html(json);

			console.log("ERROR : ", data["responseMsg"]);
			return;
               }

		},
		error : function(e) {

			var json = "<h4>Error Response </h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);

			console.log("ERROR : ", e);
		}
	});
}

function fire_ajax_click_webservice2() {
	var billerid = $("#selectMerchant").val();
	var amt = $("#amount").val();
	var cur = $("#currency").val();
	var phone = $("#payerPhone").val();
	var name = $("#payerName").val();
	var email = $("#payerEmail").val();
	console.log("where are you : " + billerid);
	// searchViaAjax();
	// $( "#accountNumber" ).load( "resources/templates/error.html" );
	var search = {}
	search["appid"] ="cb01"
	search["hash"] = "x";
	search["billId"] = billerid;
	search["amount"] = amt;
	search["payerPhone"] = phone;
	search["currency"] = cur;
	search["payerName"] = name;
	search["payerEmail"] = email;
	var url11a=document.location.origin +"/payment-portal/generateRRR";

	$.ajax({
		type : "post",
		contentType : "application/json",
		//url : "http://132.10.200.172:9292/service/accountDetail",
		url : url11a,
		data : JSON.stringify(search),
		dataType : 'json',
		// async: false,
		cache : false,
		timeout : 600000,
		success : function(data) {
					var resp= data["responseCode"];
					var rrr= data["rrr"];
					var amtdue= data["amountDue"];
                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
				   console.log("SUCCESS : ", data);
				   console.log("Validate response : ", data["responseCode"]);
                //   $('#selectServiceType').append('<option value="' + value.id + '">' + value.name + '</option>');
              // });

		},
		error : function(e) {

			var json = "<h4>ServiceType Error Response </h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);

			console.log("ERROR : ", e);
		}
})
}