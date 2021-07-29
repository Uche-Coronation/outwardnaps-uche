$(document).ready(function() {
	// alert('testing')
	var url = window.location;
	//alert(url);
	// x.style.display = 'none';
	// var verifiedAccountDiv = document.getElementById('verifiedAccountDiv');
	// verifiedAccountDiv.style.display = 'none';
	$('#proceedbtn').click(function() {
		// var bankCode = $("#bank1").val();
		var rrr = $("#rrr").val();
		//alert(billerid);
		// alert('testing acct: ' + accountNumber + ' bank == ' + bankCode)
		fire_ajax_webservice3();
	});
	/***************************************************************************
	 * $('#referralOptionId').change(function(event) { referralChange(); });
	 * $('#bvn').change(function(event) { var bankCode = $("#bank1").val(); var
	 * accountNumber = $("#accountNumber").val(); // alert('testing acct: ' +
	 * accountNumber + ' bank == ' + bankCode) // fire_ajax_webservice(); });
	 **************************************************************************/


});

function fire_ajax_webservice3() {
	// var bvn = $("#bvn").val();
	var rrr = $("#rrr").val();
	console.log("Pay with rrr : " + rrr);
	// searchViaAjax();
	// $( "#accountNumber" ).load( "resources/templates/error.html" );
	var search = {}
	search["appid"] ="cb01"
	search["hash"] = "d";
	search["rrr"] = rrr;
	// search["bank"] = $("#bank1").val();
	var url1a=document.location.origin +"/payment-portal/getRRRDetail";

	$.ajax({
		type : "post",
		contentType : "application/json",
		//url : "http://132.10.200.172:9292/service/accountDetail",
		url : url1a,
		data : JSON.stringify(search),
		dataType : 'json',
		// async: false,
		cache : false,
		timeout : 600000,
		success : function(data) {
			   console.log("SUCCESS : ", data);
			   console.log("Validate response : ", data["responseCode"]);
			   console.log("payername : ", data.responseData[0]['payerName']);
			   var cars=data['responseData'];
			   $("#payername").val(data.responseData[0]['payerName']);
			   //$("#exampleModal-4").modal("show");
			   $('#exampleModal-4').on('show.bs.modal', function (e) {
				   $("#payername").val(data.responseData[0]['payerName']);
				   $("#payeremail").val(data.responseData[0]['payerEmail']);
				   alert('Modal is successfully shown!');
				 });
			   /*
			   console.log("payer email : ", cars[0].payerEmail);
			   document.getElementById("rr").innerHTML = cars[0];
			   document.getElementById("payername").innerHTML = cars[2];
			   document.getElementById("payeremail").innerHTML = cars[3]; */
			//   console.log("payeremail : ", (data["responseData"])["payerEmail"]);

			  // $.each(data, function (index, value) {
                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
				//   console.log("list returned : ",value.name );
              //     $('#selectServiceType').append('<option value="' + value.id + '">' + value.name + '</option>');
             //  });

		},
		error : function(e) {

			var json = "<h4>ServiceType Error Response </h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);

			console.log("ERROR : ", e);
		}
	});



}