/*
	[JspRun!] (C)2007-2011 JspRun Inc.
	This is NOT a freeware, use is subject to license terms

	$RCSfile: post_editor.js,v $
	$Revision: 1.4 $
	$Date: 2011/01/04 10:27:03 $
*/

function checklength(theform) {
	var message = bbinsert && wysiwyg ? html2bbcode(getEditorContents()) : (!theform.parseurloff.checked ? parseurl(theform.message.value) : theform.message.value);
	var showmessage = postmaxchars != 0 ? lang['board_allowed'] + ': ' + postminchars + ' ' + lang['lento'] + ' ' + postmaxchars + ' ' + lang['bytes'] : '';
	alert('\n' + lang['post_curlength'] + ': ' + mb_strlen(message) + ' ' + lang['bytes'] + '\n\n' + showmessage);
}

if(!tradepost) {
	var tradepost = 0;
}

function validate(theform, previewpost) {
	var message = bbinsert && wysiwyg ? html2bbcode(getEditorContents()) : (!theform.parseurloff.checked ? parseurl(theform.message.value) : theform.message.value);
	if(($('postsubmit').name != 'replysubmit' && !($('postsubmit').name == 'editsubmit' && !isfirstpost) && theform.subject.value == "") || message == "") {
		alert(lang['post_subject_and_message_isnull']);
		if(special != 2) {
			theform.subject.focus();
		}
		return false;
	} else if(mb_strlen(theform.subject.value) > 80) {
		alert(lang['post_subject_toolong']);
		theform.subject.focus();
		return false;
	}
	if(tradepost) {
		if(theform.item_name.value == '') {
			alert(lang['post_trade_goodsname_null']);
			theform.item_name.focus();
			return false;
		} else if(theform.item_price.value == '') {
			alert(lang['post_trade_price_null']);
			theform.item_price.focus();
			return false;
		} else if(!parseInt(theform.item_price.value)) {
			alert(lang['post_trade_price_is_number']);
			theform.item_price.focus();
			return false;
		} else if(theform.item_costprice.value != '' && !parseInt(theform.item_costprice.value)) {
			alert(lang['post_trade_costprice_is_number']);
			theform.item_costprice.focus();
			return false;
		} else if(theform.item_number.value != '0' && !parseInt(theform.item_number.value)) {
			alert(lang['post_trade_amount_is_number']);
			theform.item_number.focus();
			return false;
		}
	}
	if(in_array($('postsubmit').name, ['topicsubmit', 'editsubmit'])) {
		if(theform.typeid && (theform.typeid.options && theform.typeid.options[theform.typeid.selectedIndex].value == 0) && typerequired) {
			alert(lang['post_type_isnull']);
			theform.typeid.focus();
			return false;
		}
		if(special == 3 && isfirstpost) {
			if(theform.rewardprice.value == "") {
				alert(lang['post_reward_credits_null']);
				theform.rewardprice.focus();
				return false;
			}
		} else if(special == 4 && isfirstpost) {
			if(theform.activityclass.value == "") {
				alert(lang['post_activity_sort_null']);
				theform.activityclass.focus();
				return false;
			} else if($('starttimefrom_0').value == "" && $('starttimefrom_1').value == "") {
				alert(lang['post_activity_fromtime_null']);
				return false;
			} else if(theform.activityplace.value == "") {
				alert(lang['post_activity_addr_null']);
				theform.activityplace.focus();
				return false;
			}
		} else if(special == 6 && isfirstpost) {
			$('subjectu8').value = encodeURIComponent($('subject').value);
			if($('tags') != null) $('tagsu8').value = encodeURIComponent($('tags').value);
			if($('vid').value == '') {
				alert(lang['post_video_uploading']);
				return false;
			} else if($('vclass') && getradiovalue('vclass') == '') {
				alert(lang['post_video_vclass_required']);
				return false;
			}
		}
	}

	if(!disablepostctrl && ((postminchars != 0 && mb_strlen(message) < postminchars) || (postmaxchars != 0 && mb_strlen(message) > postmaxchars))) {
		alert(lang['post_message_length_invalid'] + '\n\n' + lang['post_curlength'] + ': ' + mb_strlen(message) + ' ' + lang['bytes'] + '\n' +lang['board_allowed'] + ': ' + postminchars + ' ' + lang['lento'] + ' ' + postmaxchars + ' ' + lang['bytes']);
		return false;
	}
	theform.message.value = message;
	if(in_array($('postsubmit').name, ['topicsubmit', 'replysubmit'])) seccheck(theform, seccodecheck, secqaacheck, previewpost);
	if(previewpost || $('postsubmit').name == 'editsubmit') return true;
}

function seccheck(theform, seccodecheck, secqaacheck, previewpost) {
	if(!previewpost && (seccodecheck || secqaacheck)) {
		var url = 'ajax.do?inajax=1&action=';
		if(seccodecheck) {
			var x = new Ajax();
			x.get(url + 'checkseccode&seccodeverify=' + (is_ie && document.charset == 'utf-8' ? encodeURIComponent($('seccodeverify').value) : $('seccodeverify').value), function(s) {
				if(s != 'succeed') {
					alert(s);
					$('seccodeverify').focus();
				} else if(secqaacheck) {
					checksecqaa(url, theform);
				} else {
					postsubmit(theform);
				}
			});
		} else if(secqaacheck) {
			checksecqaa(url, theform);
		}
	} else {
		postsubmit(theform, previewpost);
	}
}

function checksecqaa(url, theform) {
	var x = new Ajax();
	var secanswer = $('secanswer').value;
	secanswer = is_ie && document.charset == 'utf-8' ? encodeURIComponent(secanswer) : secanswer;
	x.get(url + 'checksecanswer&secanswer=' + secanswer, function(s) {
		if(s != 'succeed') {
			alert(s);
			$('secanswer').focus();
		} else {
			postsubmit(theform);
		}
	});
}

function postsubmit(theform, previewpost) {
	if(!previewpost) {
		theform.replysubmit ? theform.replysubmit.disabled = true : theform.topicsubmit.disabled = true;
		theform.submit();
	}
}

function previewpost(){
	if(!validate($('postform'), true)) {
		$('subject').focus();
		return;
	}
	$("previewmessage").innerHTML = '<span class="bold"><span class="smalltxt">' + $('subject').value + '</span></span><br /><br /><span style="font-size: {MSGFONTSIZE}">' + bbcode2html($('postform').message.value) + '</span>';
	$("previewtable").style.display = '';
	window.scroll(0, 0);
}

function clearcontent() {
	if(wysiwyg && bbinsert) {
		editdoc.body.innerHTML = is_moz ? '<br />' : '';
	} else {
		textobj.value = '';
	}
}

function resizeEditor(change) {
	var editorbox = bbinsert ? editbox : textobj;
	var newheight = parseInt(editorbox.style.height, 10) + change;
	if(newheight >= 100) {
		editorbox.style.height = newheight + 'px';
	}
}

function relatekw() {
	var message = getEditorContents();
	message = message.substr(0, 500);
	message = message.replace(/&/ig, '', message);
	ajaxget('relatekw.jsp?subjectenc=' + $('subject').value + '&messageenc=' + message, 'tagselect');
}

function getradiovalue(radioname) {
	var tags = document.getElementsByTagName('input');
	for(var i=0; i<tags.length; i++) {
		if(tags[i].name == radioname && tags[i].checked) {
			return tags[i].value;
		}
	}
	return '';
}