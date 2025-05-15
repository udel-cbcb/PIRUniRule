/**
 * 
 */


//$(document).ready(function() {
jQuery(document).ready(function($) {
	var bar = $('.bar');
	var percent = $('.percent');
	var msg = $('#msg');
	// alert("hello")
	$('form').ajaxForm({
		dataType : 'json',
		// beforeSubmit: validate,

		beforeSend : function() {
			//status.empty();
			var percentVal = '0%';
			bar.width(percentVal)
			percent.html(percentVal);
			$('#progressbar').attr('class', 'progress')
			// document.getElementById("progressbar").attr('class',
			// 'progress');
		},

		uploadProgress : function(event, position, total, percentComplete) {
			var percentVal = percentComplete + '%';
			bar.width(percentVal)
			percent.html(percentVal);
		},
		success : function(data) {
			if (data.redirectURL) {
				var percentVal = '100%';
				bar.width(percentVal)
				percent.html(percentVal);
				window.location.href = data.redirectURL;
			} else {
				$('#progressbar').attr('class', 'progress hidden')
				$('#msg').attr('class', 'alert alert-danger')
				//console.log(data.errorMsg);
				msg.html(data.errorMsg);
			}
		},
		error : function(data) {
			// status.html(xhr.responseText);
			msg.html(data.errorMsg)
		}
	});
});
