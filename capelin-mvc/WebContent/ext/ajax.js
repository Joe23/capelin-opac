/** 
 * Capelin-OPAC
 *
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @date 2012-02
 */

/** jquery function **/
function capelin_ajax_folder_check(){
	var jqId = '#d_' + $(this).val();
	$(jqId).fadeTo(200,0.01);
	if($(this).is(':checked')){
		capelinAjax.addOneToFolder($("#Capelin-Path").val(),
				$(this).val(),
				{callback:function(){
					$(jqId).fadeTo(200,1);
				}
			});
	}else{		
		capelinAjax.deleteOneFromFolder($("#Capelin-Path").val(),
				$(this).val(),
				{callback:function(){
					$(jqId).fadeTo(200,1);
				}
			});
	}
}

$('document').ready(function(){
	$('.keptsCheckbox').bind('click',capelin_ajax_folder_check);
});
