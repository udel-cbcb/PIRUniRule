/**
 * 
 */

var appBase = "/PIRSitePredict";

function ruleCentralView(data, type, full) {
	return '<a class="viewOntology" data-fancybox-type="iframe" href="'
			+ appBase + '/prediction/detailView/job/' + full.jobId + '/rule/'
			+ full.pirsrId + '">' + full.pirsrId + '</a>';
};

function proteinCentralView(data, type, full) {
	return '<a class="viewOntology" data-fancybox-type="iframe" href="'
			+ appBase + '/prediction/detailView/job/' + full.jobId
			+ '/protein/' + full.proteinId + '">' + full.proteinId + '</a>';
};

function nucleotideCentralView(data, type, full) {
	return '<a class="viewOntology" data-fancybox-type="iframe" href="'
			+ appBase + '/prediction/detailView/job/' + full.jobId
			+ '/nucleotide/' + full.nucleotideId + '">' + full.nucleotideId
			+ '</a>';
};

jQuery(document).ready(function($) {
	$('[data-toggle="popover"]').popover({
		html : true
	});

	$(window).scroll(function() {
		if ($(this).scrollTop()) {
			$('#toTop').fadeIn();
		} else {
			$('#toTop').fadeOut();
		}
	});

	$("#toTop").click(function() {
		// 1 second of animation time
		// html works for FFX but not Chrome
		// body works for Chrome but not FFX
		// This strange selector seems to work universally
		$("html, body").animate({
			scrollTop : 0
		}, 1000);
	});
/*
	var bar = $('.bar');
	var percent = $('.percent');
	var status = $('#msg');
	// alert("hello")
	$('form').ajaxForm({
		dataType : 'json',
		// beforeSubmit: validate,

		beforeSend : function() {
			status.empty();
			var percentVal = '0%';
			bar.width(percentVal)
			percent.html(percentVal);
			$('#progressbar').attr('class', 'progress')
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
				console.log(data.errorMsg);
				status.html(data.errorMsg);
			}
		},
		error : function(data) {
			// status.html(xhr.responseText);
			status.html(data.errorMsg)
		}
	});*/
});
