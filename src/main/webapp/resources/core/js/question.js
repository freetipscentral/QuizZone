$( document ).ready(function() {
	

	$('#deleteAnswer').click(function () {
		alert("Hello");
	});

	$("button[id^='deleteAnswer']").click(function () {

		var clickedButton = $(this).attr('id');
		alert(clickedButton.split('-')[1]);

	$.getJSON('/jquery/getjsondata', { name:'Steve'}, function(data, textStatus, jqXHR){
		alert(data.firstName);
	})
	.done(function () { alert('Request done!'); })
	.fail(function (jqxhr,settings,ex) { alert('failed, '+ ex); });
	});
});
