function confirmSubmit(url) {
	var agree = confirm("Are you sure you wish to delete this record?");
	if (agree)
		window.location = url;
}

function validate(form_id, email) {
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var address = document.forms[form_id].elements[email].value;
	if (reg.test(address) == false) {
		alert('Invalid Email Address');
		return false;
	}
}

function capelin_validate_form(fId,fFields,error_ext){
	var object_form = document.getElementById(fId);
	var array_fields = fFields.split(",");
	errors_required = capelin_form_check_required(object_form, array_fields);
	error_msg = "";
	for(i = 0; i < errors_required.length; i++){
		error_msg += errors_required[i] + " is required\n";
	}
	if(error_ext != undefined && "" != error_ext){
		error_msg += error_ext;
	}
	
	if("" == error_msg){
		alert("GOOD!"); return true;
	}else{
		alert(error_msg);
	}
	return false;
}

function capelin_string_trim(str){
	return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

function capelin_form_check_required(object_form, array_fields_required){
	var error_message=new Array();
	var j = 0;
	for(i = 0; i < array_fields_required.length; i++){
		f_value = capelin_string_trim(object_form[array_fields_required[i]].value);
		if("" == f_value){
			error_message[j++] = array_fields_required[i];
		}	
	}
	return error_message;
}

function sortFolder(sortId, url){
	var sort_by = document.getElementById(sortId).value;
	var to_url = url + "&sort_by=" + sort_by;	
	window.location = to_url;
}
