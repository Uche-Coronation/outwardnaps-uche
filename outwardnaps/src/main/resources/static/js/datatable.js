$(document).ready( function () {
	var newMyUrl = '';
	newMyUrl = document.location.origin + '/payment-portal/getNAPSBatchDetails'
	 var table = $('#napsTable').DataTable({
			console.log('napsTable==', responseMessage);
			"sAjaxSource": newMyUrl,
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "id"},
		      { "mData": "name" },
				  { "mData": "lastName" },
				  { "mData": "email" },
				  { "mData": "phone" },
				  { "mData": "active" }
			]
	 })
});