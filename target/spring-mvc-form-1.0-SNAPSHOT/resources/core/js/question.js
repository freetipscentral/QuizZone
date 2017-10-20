$( document ).ready(function() {
    alert( "ready!" );   
    
    $('#deleteAnswer').click(function () {
    	alert("Hello");
    });
    
    $("button[id^='deleteAnswer']").click(function () {
    	
    	var clickedButton = $(this).attr('id');
    	alert(clickedButton.split('-')[1]);
    });
});