//boolean var for add button activation
var nameOK=false;
var introOK=true;
var discoOK=true;

//date regex
var regex = "(19[7-9][0-9]|20[0-3][0-9])[\-](0[1-9]|1[012])[\-](0[1-9]|[12][0-9]|3[01])";

/**************** EVENTS ***************/
// input computerName
$("#computerName").keyup( function() {     
	validateNameField();
});
$("#computerName").blur(function(){
	validateNameField();
});

//input introduced
$("#introduced").keyup( function() {
	validateIntroducedField();
});
$("#introduced").blur(function(){
	validateIntroducedField();
});

//input disontinued
$("#discontinued").keyup( function() {
	validateDiscontinuedField();
});
$("#discontinued").blur(function(){
	validateDiscontinuedField();
});


/**************** VALIDATION ***************/
//check the name field (not null)
function validateNameField(){
    var str = $.trim($(this).val());
    if(str != "") {
    	hideError("computerName", "errorComputerName");
    	nameOK = true;
    } else {
    	showError("computerName", "errorComputerName");
    	nameOK = false;
    }
    checkForm();
}

//check the introduced date format
function validateIntroducedField(){
	var str = $.trim($(this).val());
	if(str != "") {
		 if (!$("#introduced").val().match(regex)) {
			 showError("introduced", "errorIntroduced");
			 introOK = false;
		 } else {
			 hideError("introduced", "errorIntroduced");
			 introOK = true;
		 }
	} else {
		hideError("introduced", "errorIntroduced");
		 introOK = true;
	}
	checkForm();
}

//check the discontinued date format
function validateDiscontinuedField(){
	var str = $.trim($(this).val());
	if(str != "") {
		 if (!$("#discontinued").val().match(regex)) {
		       showError("discontinued", "errorDiscontinued");
		       discoOK = false;
		 } else {
			 hideError("discontinued", "errorDiscontinued");
			 discoOK = true;
		 }
	} else {
		hideError("discontinued", "errorDiscontinued");
		discoOK = true;
	}
	checkForm();
}

//check all fields validation
function checkForm(){
	if (nameOK && introOK && discoOK){
		$("#submitButton").removeAttr("disabled")
	} else {
		$("#submitButton").attr('disabled', 'disabled');
	}
}

/******** ERRORS MANAGEMENT **********/
function showError(inputId, errorId){
	var input = "#"+inputId;
	var error = "#"+errorId;
	
	$(input).parent().first().addClass("has-error");
    $(input).parent().first().removeClass("has-success");
    $(error).removeClass("sr-only");
	
}

function hideError(inputId, errorId){
	var input = "#"+inputId;
	var error = "#"+errorId;
	
	$(input).parent().first().addClass("has-success");
	$(input).parent().first().removeClass("has-error");
	$(error).addClass("sr-only");
}

