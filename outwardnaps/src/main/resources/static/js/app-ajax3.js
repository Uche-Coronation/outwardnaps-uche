/**
 * 
 */
$(document).ready(function() {
	$('#myTable').dataTable();

	var pictureName = $("#pictureName").val();
	var signatureName = $("#signatureName").val();
	var moIdentificationName = $("#moIdentificationName").val();
	var poAddressName = $("#poAddressName").val().trim();
	console.log('poAddressName ==',poAddressName)
	if (pictureName == '') {
		var x = document.getElementById('picture');
		x.style.display = 'none';
	}
	if (signatureName == '') {
		var x1 = document.getElementById('signature');
		x1.style.display = 'none';
	}
	if (moIdentificationName == '') {
		var x2 = document.getElementById('identification');
		x2.style.display = 'none';
	}
	if (poAddressName.trim() == '') {
		console.log('poAddressName 2 ==',poAddressName)
		var x3 = document.getElementById('address1');
		x3.style.display = 'none';
	}
});

function fire_download(fileName, id) {

	console.log('In to submit..', fileName);
	console.log('In to submit..', id);
	// var encoded = encodeURIComponent(str);

	/* I put the above code for check data before send to ajax */
	$.ajax({
		url : "http://132.10.200.117:9293/downloadFile",
		type : 'get',
		data : "fileNameId=" + fileName + "," + id,
		success : function(data) {

		}
	});
	return false;

}
