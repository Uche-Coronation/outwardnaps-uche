$(document).ready(function() {
	// alert('testing')
	var url = window.location;
	/*document.getElementById("cftext").style.display = "none";
	document.getElementById("cfdate").style.display = "none";
	document.getElementById("cfsigleselect").style.display = "none";
	document.getElementById("cfmultiselect").style.display = "none";
	document.getElementById("confirmdialog").style.display = "none";
	document.getElementById("cfmultiselect22").style.display = "none";*/

	//$('#cffields').style.visibility = "hidden";
	//alert(url);   debittedAccount
	// x.style.display = 'none';
	// var verifiedAccountDiv = document.getElementById('verifiedAccountDiv');
	// verifiedAccountDiv.style.display = 'none';
	$('#billId').change(function(event) {
		// var bankCode = $("#bank1").val();
		var billerid = $("#billId").val();
		$("#billidName").val($(this).find("option:selected").text());
		// alert($(this).find("option:selected").text());
		//alert(billerid);
		// alert('testing acct: ' + accountNumber + ' bank == ' + bankCode)
		fire_ajax_webservice();

	});



	$('#debittedAccount').change(function(event) {

		var xtty = $("#debittedAccount").val();
		alnumeric(xtty);
	});

	$('#cftextx').change(function(event) {
		// var bankCode = $("#bank1").val();
		var xtt = $("#cftextx").val();
		var cltyp=$("#cotyp").val();
		//alert(billerid);
		// alert('testing acct: ' + accountNumber + ' bank == ' + bankCode)
		if(cltyp == "AN" ){
			alphanumeric(xtt);
		}
		if(cltyp == "A" ){
			alphab(xtt);
		}
		if(cltyp == "N" ){
			alnumeric(xtt);
		}
	});

	$('#serviceType').change(function(event) {
		// var bankCode = $("#bank1").val();  cffields
		//$('#cffields').style.display = "none";
		 $('#cffields').hide();
		var billida = $( "#servicetypeName option:selected" ).text();
		//alert($(this).find("option:selected").text());
		//$("#servicetypeName").val($(this).find("option:selected").text());
		//alert(billid);
		$("#servicetypeName").val(billida);

		// alert('testing acct: ' + accountNumber + ' bank == ' + bankCode)
		fire_ajax_webserviceax();
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
	var billerid = $("#billId").val();
	console.log("where are you : " + billerid);
	// searchViaAjax();
	// $( "#accountNumber" ).load( "resources/templates/error.html" );
	var search = {}
	search["appid"] ="cb01"
	search["hash"] = "";
	search["billerid"] = billerid;
	// search["bank"] = $("#bank1").val();
	var url2=document.location.origin +"/payment-portal/billerServiceType";
	console.log("Url: ",url2);

	$.ajax({
		type : "post",
		contentType : "application/json",
		//url : "http://132.10.200.172:9292/service/accountDetail",
		url : url2,
		data : JSON.stringify(search),
		dataType : 'json',
		// async: false,
		cache : false,
		timeout : 600000,
		success : function(data) {

			$('#serviceType').empty();
			   $.each(data, function (index, value) {
                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
				   console.log("list returned : ",value.name );
                   $('#serviceType')
                   .append('<option value="' + value.id + '">' + value.name + '</option>');
               });

		},
		error : function(e) {

			var json = "<h4>Error retrieving Remita Service Type </h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);

			console.log("ERROR : ", e);
		}
	});



}

function changeFunction(val,nal,fxpri,fid,sss) {
	//  alert("The input value has changed. The new value is: " + val+" : "+nal+"  : "+fxpri+" : "+fid);
	  var content=$("#cfcsubmit").val();
	  if(nal == "AN"){
		 var sd= $("#cfcan").val();
		 //cfcsubmit
		 content = content +nal+":"+fid+":"+val+":"+"0"+":"+fxpri+";";
		 alphanumeric(val);
		// alert("custom fields == "+content);
		 $("#cfcsubmit").val(content);


	  }
	  if(nal == "ALL"){
			 var sd= $("#cfcall").val();
			 //cfcsubmit
			 content = content+nal+":" +fid+":"+val+":"+"0"+":"+fxpri+";";
			 //alphanumeric(val);
			// alert("custom fields == "+content);
			 $("#cfcsubmit").val(content);


		  }

	  if(nal == "DD"){
		  var values = $('#this').val();
		//  alert(values);
			$('#val.id option:selected').each(function() {
				//alert(val);
			   // alert($(this).val());
			});

			 content = content +nal+":"+fid+":"+val.value+":"+"1"+":"+0+";";
			// alert("custom fields == "+content);
			 $("#cfcsubmit").val(content);
	  }

	  if(nal == "SL"){
		  var values = $('#this').val();
		 // alert(values);
			$('#val.id option:selected').each(function() {
				//alert(val);
			   // alert($(this).val());
			});

			 content = content+nal+":" +fid+":"+val.value+":"+"1"+":"+0+";";
			// alert("custom fields == "+content);
			 $("#cfcsubmit").val(content);
	  }

	  if(nal == "SP"){
		 var values = val.value;
		  var res = values.split(":");

		 // alert(res);
		 	$('#val.id option:selected').each(function() {


			});

			 content = content +nal+":"+fid+":"+res[0]+":"+"1"+":"+res[1]+";";
			// alert("custom fields == "+content);
			 $("#cfcsubmit").val(content);
			 alert("You just selected :"+res[0] +". You can click again to select multiple items.  ");

		/* $.each(values, function (index, value) {
			  var res = values[index].split(":");
			   content = content +fid+":"+res[0]+":"+"1"+":"+res[1]+";";
			   alert("custom fields == "+content);
			   $("#cfcsubmit").val(content);
			   console.log(content);
			  });   */

	  }

	  if(nal == "A"){
			 var sd= $("#cfca").val();
			 //cfcsubmit
			 content = content+nal+":" +fid+":"+val+":"+"0"+":"+fxpri+";";
			 alphab(val);
			// alert("custom fields == "+content);
			 $("#cfcsubmit").val(content);


		  }
	  if(nal == "N"){
			 var sd= $("#cfcn").val();
			 //cfcsubmit
			 content = content +nal+":"+fid+":"+val+":"+"0"+":"+fxpri+";";
			 alnumeric(val);
			// alert("custom fields == "+content);
			 $("#cfcsubmit").val(content);


		  }
	  if(nal == "D"){
			 var sd= $("#cfcd").val();
			 //cfcsubmit
			 content = content+nal+":" +fid+":"+val+":"+"0"+":"+fxpri+";";

			// alert("custom fields == "+content);
			 $("#cfcsubmit").val(content);


		  }

	}



//Function to check letters
function alphab(inputtxt)
{
 var letterNumber = /^[A-Za-z]+$/;
 if((inputtxt.match(letterNumber)) )
  {
   return true;
  }
else
  {
   alert("Please enter alphabet only!");
   $("#cftextx").val(" ");
   document.mform.cftextx.focus;
   return false;
  }
  }

//format val to n number of decimal places
//modified version of Danny Goodman's (JS Bible)
function formatDecimal(val, n) {
 n = n || 2;
 var str = "" + Math.round ( parseFloat(val) * Math.pow(10, n) );
 while (str.length <= n) {
     str = "0" + str;
 }
 var pt = str.length - n;
 return str.slice(0,pt) + "." + str.slice(pt);
}

//call onload or in script segment below form
function attachCheckboxHandlers() {
    // get reference to element containing chkbx checkboxes
    //var el = document.getElementById('chkbx');

    // get reference to input elements in toppings container element
   // var tops = el.getElementsByTagName('input');
	alert("before chk!");
    var tops=$( "[type=checkbox]" );

    alert("after chk!");
    // assign updateTotal function to onclick property of each checkbox
    for (var i=0, len=tops.length; i<len; i++) {
        if ( tops[i].type === 'checkbox' ) {
            tops[i].onclick = updateTotal();
            alert("after onclick checkbox!");
        }
    }
}

//called onclick of checkboxes
function updateTotal(e) {
	var content=$("#cfcsubmit").val();    //cfcsp
	var fid=$("#cfcsp").val();
	var spid=e.id;
	var pps=e.value;
	//var qyy=document.getElementById(qty).value;
    // 'this' is reference to checkbox clicked on
    //var form = this.form;
   // alert("inside updateTotal!");
    var qty= e.id+"n";
    var qty1= parseFloat(document.getElementById(qty).value) ;  //parseFloat(qqty);
    //alert("qty1:"+qty1);
    /*    // get current value in total text box, using parseFloat since it is a string
    var ppria=e.id+"p";
    alert("ppria:"+ppria);  //'+ppria+'
    var ii='"#'+ppria+'"';
    alert(ii);
   // var ssd=$(ii).html(); // $('#table').find("tr:eq(0)").find("td:eq(3)").html();
    alert("ssd:"+$('#table').find("tr:eq(0)").find("td:eq(3)").html());
    var ppria2= "'"+ppria+"'";
    //var ppri1= parseFloat(document.getElementById(ppria2).innerHTML) ;
    var ppri1=parseFloat(ssd);
    alert("ppri1:"+ppri1);   */
    var val = parseFloat( $("#total_potential").text() );   //$('#myDiv').text();

    // if check box is checked, add its value to val, otherwise subtract it
    if ( e.checked ) {
    	//alert("amount:"+parseFloat(e.value));
    	//ppri1 += (parseFloat(e.value) * qty1);
        val += (parseFloat(e.value) * qty1);
        content = content +"SP"+":"+fid+":"+spid+":"+qty1+":"+pps+";";
        $("#cfcsubmit").val(content);

    } else {
    	//alert("amount:"+parseFloat(e.value));
    	//ppri1 -= (parseFloat(e.value) * qty1);
        val -= (parseFloat(e.value) * qty1);

    }

    // format val with correct number of decimal places
    // and use it to update value of total text box
   // $("#ppria").html(ppri1);
    //document.getElementById(ppri).text = ppri1;
    //alert("ppria:"+ppria);
   // alert("ppri1 2:"+ppri1);
   //document.getElementById(ppria2).innerHTML = val;
    //document.getElementById(ppria).innerHTML =ppri1;
    $("#total_potential").text(val);
    $("#amount").val(val);

}

//Function to check letters and numbers
function alphanumeric(inputtxt)
{
 var letterNumber = /^[0-9a-zA-Z]+$/;
 var letterNumber2 = /^[A-Za-z0-9]+$/;
 var letterNumber3 = /^[0-9A-Za-z]+$/;
 if((inputtxt.match(letterNumber)) || (inputtxt.match(letterNumber2)) || (inputtxt.match(letterNumber3)) )
  {
   return true;
  }
else
  {
   alert("Please enter alphanumeric only!");
   $("#cftextx").val(" ");
   document.mform.cftextx.focus;
   return false;
  }
  }
//input_string.match(/[^\w]|_/) == null
//Function to check  numbers
function alnumeric(inputtxt)
{
 var letterNumber = /^[0-9]+$/;
 if((inputtxt.match(letterNumber))){
 //if((isNaN(inputtxt)) )
  //{
   alert("Please enter numeric only!");
   $("#cftextx").val(" ");
   document.mform.cftextx.focus();
   return false;
  }
else
  {

   return true;
  }
  }

function alphnumeric(inputtxt)
{
 var letterNumber = /^[0-9]+$/;
 var letterNumber2 = /^[a-zA-Z]+$/;
 if((inputtxt.match(letterNumber) & inputtxt.match(letterNumber2) ) )
  {
	 return true;
  }
else
  {
	alert("Please enter alphanumeric only!");
	   $('#cftextx').val(" ");
	   document.mform.cftextx.focus();
	   return false;

  }
  }

function fire_ajax_webserviceax() {
	// var bvn = $("#bvn").val();
	//var billerid = $("#billId").val();
	var billid = $("#serviceType").val();

	//alert(billid);
	console.log("where are you : " + billid);
	// searchViaAjax();
	// $( "#accountNumber" ).load( "resources/templates/error.html" );
	var search = {};
	search["appid"] ="cb01";
	search["hash"] = "x";
	search["billid"] = billid;
	// search["bank"] = $("#bank1").val();
	var url2a=document.location.origin +"/payment-portal/getCustomField";

	$.ajax({
		type : "post",
		contentType : "application/json",
		url : url2a,
		data : JSON.stringify(search),
		dataType : 'json',
		// async: false,
		cache : false,
		timeout : 600000,

		success : function(data) {

			   console.log("Response returned : ",data.responseCode );
			   console.log("list returned : ",data.responseData);
			   console.log("list count : ",data.responseData.length);
			  // console.log("Parent id : ",data.responseData[0].id);
			  // console.log("Column Name : ",data.responseData[0].columnName);
			   var count=data.responseData.length;
			  // var colname=data.responseData[0].columnName;
			   $('#cffields').show();
			   var cth="";  //cfccount
			   for (j = 0; j < count; j++) {
			   var resda=data.responseData[j];
			   var coltype=resda.columnType;
			   var cfd=resda.customFieldDropDown;
			   var cfdcnt=resda.customFieldDropDown.length;
			   var colname=resda.columnName;
			   var fid=resda.id;
			   var fxamt=data.fixedAmount;
			   var fxpri=data.fixedPrice;

			   if(fxpri == true){
				   //amount
				   $("#amount").val(fxamt);
			   }

			   if(data.responseCode == "00"){
				   console.log("data returned : ",cfd);
				   $("#cotyp").val(coltype);
				   $("#cfccount").val(count);
				   console.log("column type : ",coltype);
				   $("#cfpid").val(fid);


					if (cfdcnt > 0){

					       if(coltype == "DD"){
					    	   var x= "DD";
								  cth=cth +'<div id="cfsigleselect">'+
				                     '<div class="form-group row">'+
				                    		'<label  class="col-sm-3 col-form-label"><span name="txt3" id="txt3">'+colname+'</span><span style="color: #ff0000;">*</span></label>'+
				                    		'<div class="col-sm-9">'+
							                    '<select class="js-example-basic-single w-100" name="cfssk" id="cfssk" onchange="changeFunction(this,\''+x+'\',\''+fxamt+'\',\''+fid+'\')">'+
							                      '<option value="AL">-Select One-</option>';
							              		$.each(cfd, function (index, value) {
									                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
													   console.log("list returned : ",value.description );
													   cth = cth + '<option value="' + value.id + '">' + value.description + '</option>';
									               });
							                      cth = cth +'</select>'+
							                    '<input type="hidden" name="cfcdd" id="cfcdd" value="'+fid+'" />'+
						                    '</div>'+
					                    '</div>'+
				                    '</div>';
								  $('#cffields').html(cth);
								  console.log("After cth : ",cth);


									$('#cfssk').empty().append('<option value="" selected>-Select One-</option>');
									$.each(cfd, function (index, value) {
						                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
										   console.log("list returned : ",value.description );
						                   $('#cfssk').append('<option value="' + value.id + '">' + value.description + '</option>');
						               });

					       }
						 if (coltype == "SL") {
							 var x= "SL";
							 var y="cfmultiselecta" +j;
									cth = cth
											+ '<div id="cfmultiselect'+j+'">'
											+ '<div class="form-group row">'
											+ '<label  class="col-sm-3 col-form-label"><span >'+colname+'</span><span style="color: #ff0000;">*</span></label>'
											+ '<div class="col-sm-9">'
											+ '<select class="js-example-basic-multiple w-100" id="'+y+'" name="'+y+'" multiple="multiple" onchange="changeFunction(this,\''+x+'\',\''+fxamt+'\',\''+fid+'\')">'
											+ '<option value="">-Select MUltiple-</option>';
											$.each(cfd, function (index, value) {
								                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
												   console.log("list returned : ",value.description );
												   cth = cth + '<option value="' + value.id + '">' + value.description + '</option>';
								               });
											cth = cth+ '</select>' + '</div>'
											+ '<input type="hidden" name="cfcsl" id="cfcsl" value="'+fid+'" />'
											+ '</div>'
											+ '</div>';

									console.log("After cth : ", cth);
									$('#cffields').html(cth);
								/*	$('#cfmultiselecta'+j+'').empty().append('<option value="" selected>-Select Multiple-</option>');
									$.each(cfd, function (index, value) {
						                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
										   console.log("list returned : ",value.description );
						                   $('#cfmultiselecta'+j+'').append('<option value="' + value.id + '">' + value.description + '</option>');
						               });
									$(".js-example-basic-multiple").select2({
									    placeholder: "-Select Multiple-"
									}); */



								}
						 if (coltype == "SP") {
							 var x= "SP";
								/*cth = cth
										+ '<div id="'+'cfmultiselects'+j+'">'
										+ '<div class="form-group row">'
										+ '<label  class="col-sm-3 col-form-label"><span >'+colname+'</span><span style="color: #ff0000;">*</span></label>'
										+ '<div class="col-sm-9">'
										+ '<select class="js-example-basic-multiple w-100" id="cfmultiselectasp'+j+'" name="cfmultiselectasp'+j+'" multiple="multiple" onchange="changeFunction(this,\''+x+'\',\''+fxamt+'\',\''+fid+'\')">'
										+ '<option value="">-Select MUltiple-</option>'; */

							 cth=cth
							 +'<div>'+'<div id="chkbx" class="table-responsive">'+
								'<table class="table">'+
								  	'<thead>'+
										'<tr>'+
											'<th>item</th>'+
											'<th>Unit Price</th>'+
											'<th>Qty</th>'+
											'<th>Price </th>'+
										'</tr>'+
									'</thead>'+
									'<tbody>';
										$.each(cfd, function (index, value) {
											console.log("list returned : ",value.description );
											cth=cth +'<tr>'+
											'<td>'+'<input id="'+value.id+'" class="sp-items" name="sp-item[]" type="checkbox" value="'+value.unitprice+'" data-price="5000"  onclick="updateTotal(this)" data-code="Shirt" data-description="Shirt" data-fixedprice="true" data-columnname="Amount Item List">'+value.description+'</td>'+
											'<td>'+value.unitprice+'</td>'+
											'<td><input type="number" id="'+value.id+'n" class="sp-items" name="'+value.id+'n" value="1" style="width:45px" min="1" max="99999"></td>'+
											'<td  id="'+value.id+'p" class="41032536-pricePerQty sumTotal4AllItems">0.00</td>'+ //class="41032536-pricePerQty sumTotal4AllItems"

										'</tr>';
										});
							 		cth=cth +'</tbody>'+
									'<tfoot>'+
										'<tr>'+
											'<td></td>'+
											'<td></td>'+
											'<td>Your Total</td>'+
											'<td id="total_potential" style="background:#f2f2f2;">0.00</td>'+
										'</tr>'+
									'</tfoot>'+
								'</table>'+
								'</div>';
							 		cth = cth //+ '</select>' + '</div>'
									+ '<input type="hidden" name="cfcsp" id="cfcsp" value="'+fid+'" />'
									+ '</div>';
							var st='cfmultiselect'+j;
							$('#cffields').html(cth);
							console.log("After cth : ", cth);
						  		/*$.each(cfd, function (index, value) {
					                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
									   console.log("list returned : ",value.description );
									   cth = cth + '<option value="' + value.id +":"+value.unitprice+ '">' + value.description+" - "+value.unitprice + '</option>';
					               });
						  		cth = cth + '</select>' + '</div>'
										+ '<input type="hidden" name="cfcsp" id="cfcsp" value="'+fid+'" />'
										+ '</div>'
										+ '</div>';
								var st='cfmultiselect'+j;
								$('#cffields').html(cth);
								console.log("After cth : ", cth); */


								/*		$('#cfmultiselecta'+j+'').empty().append('<option value="" selected>-Select Multiple-</option>');
								$.each(cfd, function (index, value) {
					                   // APPEND OR INSERT DATA TO SELECT ELEMENT.
									   console.log("list returned : ",value.description );
					                   $('#cfmultiselecta').append('<option value="' + value.id + '">' + value.description + '</option>');
					               });
								$(".js-example-basic-multiple").select2({
								    placeholder: "-Select Multiple-"
								});  */

							}


					}else{

					       if(coltype == "AN"){
					    	   var x= "AN";
					    	   cth=cth + ' <div id="cftext">'+
					                   '<div class="form-group row">'+
					                      '<label  class="col-sm-3 col-form-label"><span name="txt" id="txt">'+colname+'</span><span style="color: #ff0000;">*</span></label>'+
					                      '<div class="col-sm-9">'+
					                        '<input type="text" class="form-control" name="cftextx" id="cftextx" placeholder="Text" onchange="changeFunction(this.value,\''+x+'\',\''+fxamt+'\',\''+fid+'\')"/>'+
					                      '</div>'+
					                       '<input type="hidden" name="cfcan" id="cfcan" value="'+fid+'" />'+
					                    '</div>'+
				                    '</div>';
					    	   $('#cffields').html(cth);
					    	   console.log("After cth : ",cth);
						   }

					       if(coltype == "A"){
					    	   var x= "A";
					    	   cth=cth + ' <div id="cftext">'+
					                   '<div class="form-group row">'+
					                      '<label  class="col-sm-3 col-form-label"><span name="txt" id="txt">'+colname+'</span><span style="color: #ff0000;">*</span></label>'+
					                      '<div class="col-sm-9">'+
					                        '<input type="text" class="form-control" name="cftextx" id="cftextx" placeholder="Text" onchange="changeFunction(this.value,\''+x+'\',\''+fxamt+'\',\''+fid+'\')"/>'+
					                      '</div>'+
					                      '<input type="hidden" name="cfca" id="cfca" value="'+fid+'" />'+
					                    '</div>'+
				                    '</div>';
					    	   $('#cffields').html(cth);
					    	   console.log("After cth : ",cth);
						   }

					       if(coltype == "N"){
					    	   var x= "N";
					    	   cth=cth + ' <div id="cftext">'+
					                   '<div class="form-group row">'+
					                      '<label  class="col-sm-3 col-form-label"><span name="txt" id="txt">'+colname+'</span><span style="color: #ff0000;">*</span></label>'+
					                      '<div class="col-sm-9">'+
					                        '<input type="text" class="form-control" name="cftextx" id="cftextx" placeholder="Text" onchange="changeFunction(this.value,\''+x+'\',\''+fxamt+'\',\''+fid+'\')"/>'+
					                      '</div>'+
					                      '<input type="hidden" name="cfcn" id="cfcn" value="'+fid+'" />'+
					                    '</div>'+
				                    '</div>';
					    	   $('#cffields').html(cth);
					    	   console.log("After cth : ",cth);
						   }

					       if(coltype == "D"){
					    	   var x= "D";
					    	   cth=cth + '<div id="cfdate">'+
		                    	'<div class="form-group row">'+
			                      '<label  class="col-sm-3 col-form-label"><span name="txt2" id="txt2">'+colname+'</span><span style="color: #ff0000;">*</span></label>'+
			                      '<div class="col-sm-9">'+
			                        '<input type="date" id="date-picker-example" class="form-control datepicker" placeholder="dd/mm/yyyy" onchange="changeFunction(this.value,\''+x+'\',\''+fxamt+'\',\''+fid+'\')" />'+
			                      '</div>'+
			                      '<input type="hidden" name="cfcd" id="cfcd" value="'+fid+'" />'+
			                    '</div>'+
		                    '</div>';
					    	   $('#cffields').html(cth);
					    	   console.log("After cth : ",cth);
					    	  // $( "#date-picker-example" ).datepicker();
					          //  $( "#date-picker-example" ).datepicker("show");

					       }

					       if(coltype == "ALL"){
					    	   var x= "ALL";

					    	   cth=cth + ' <div id="cftext">'+
			                   '<div class="form-group row">'+
			                      '<label  class="col-sm-3 col-form-label"><span name="txt" id="txt">'+colname+'</span><span style="color: #ff0000;">*</span></label>'+
			                      '<div class="col-sm-9">'+
			                        '<input type="text" class="form-control" name="cftextx" id="cftextx" placeholder="Text" onchange="changeFunction(this.value,\''+x+'\',\''+fxamt+'\',\''+fid+'\')"/>'+
			                      '</div>'+
			                      '<input type="hidden" name="cfca" id="cfca" value="'+fid+'" />'+
			                    '</div>'+
		                    '</div>';
			    	   $('#cffields').html(cth);
			    	   console.log("After cth : ",cth);
							}



					}

			   }  //end response code 00
			 }//end upper loop


		},

		error : function(e) {

			var json = "<h4>Error retrieving Custom Field(s) </h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);

			console.log("ERROR : ", e);
		}
	});



}