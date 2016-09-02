/*
	[JspRun!] (C)2007-2011 JspRun Inc.
	This is NOT a freeware, use is subject to license terms

	$RCSfile: post.js,v $
	$Revision: 1.9 $
	$Date: 2011/01/04 10:27:03 $
*/

var postSubmited = false;
var smdiv = new Array();
var codecount = '-1';
var codehtml = new Array();

function AddText(txt) {
	obj = $('postform').message;
	selection = document.selection;
	checkFocus();
	if(!isUndefined(obj.selectionStart)) {
		var opn = obj.selectionStart + 0;
		obj.value = obj.value.substr(0, obj.selectionStart) + txt + obj.value.substr(obj.selectionEnd);
	} else if(selection && selection.createRange) {
		var sel = selection.createRange();
		sel.text = txt;
		sel.moveStart('character', -strlen(txt));
	} else {
		obj.value += txt;
	}
}

function checkFocus() {
	var obj = typeof wysiwyg == 'undefined' || !wysiwyg ? $('postform').message : editwin;
	if(!obj.hasfocus) {
		obj.focus();
	}
}

function ctlent(event) {
	if(postSubmited == false && (event.ctrlKey && event.keyCode == 13) || (event.altKey && event.keyCode == 83) && $('postsubmit')) {
		if(in_array($('postsubmit').name, ['topicsubmit', 'replysubmit', 'editsubmit', 'pmssubmit']) && !validate($('postform'))) {
			doane(event);
			return;
		}
		postSubmited = true;
		$('postsubmit').disabled = true;
		$('postform').submit();
	}
}

function deleteData() {
	if(is_ie) {
		saveData('', 'delete');
	} else if(window.sessionStorage) {
		try {
			sessionStorage.removeItem('JspRun!');
		} catch(e) {}
	}
}

function insertSmiley(smilieid) {
	checkFocus();
	var src = $('smilie_' + smilieid).src;
	var code = $('smilie_' + smilieid).code;
	if(typeof wysiwyg != 'undefined' && wysiwyg && allowsmilies && (!$('smileyoff') || $('smileyoff').checked == false)) {
		if(is_moz) {
			applyFormat('InsertImage', false, src);
			var smilies = editdoc.body.getElementsByTagName('img');
			for(var i = 0; i < smilies.length; i++) {
				if(smilies[i].src == src && smilies[i].getAttribute('smilieid') < 1) {
					smilies[i].setAttribute('smilieid', smilieid);
					smilies[i].setAttribute('border', "0");
				}
			}
		} else {
			insertText('<img src="' + src + '" border="0" smilieid="' + smilieid + '" alt="" /> ', false);
		}
	} else {
		code += ' ';
		AddText(code);
	}
}

function parseurl(str, mode) {
	str= str.replace(/\s*\[code(=(.*?))?\]([\s\S]+?)\[\/code\]\s*/ig, function($1, $2,$3,$4) {return codetag($2,$4);});
	str = str.replace(/([^>=\]"'\/]|^)((((https?|ftp):\/\/)|www\.)([\w\-]+\.)*[\w\-\u4e00-\u9fa5]+\.([\.a-zA-Z0-9]+|\u4E2D\u56FD|\u7F51\u7EDC|\u516C\u53F8)((\?|\/|:)+[\w\.\/=\?%\-&~`@':+!]*)+\.(jpg|gif|png|bmp))/ig, mode == 'html' ? '$1<img src="$2" border="0">' : '$1[img]$2[/img]');
	str = str.replace(/([^>=\]"'\/@]|^)((((https?|ftp|gopher|news|telnet|rtsp|mms|callto|bctp|ed2k|thunder|synacast):\/\/))([\w\-]+\.)*[:\.@\-\w\u4e00-\u9fa5]+\.([\.a-zA-Z0-9]+|\u4E2D\u56FD|\u7F51\u7EDC|\u516C\u53F8)((\?|\/|:)+[\w\.\/=\?%\-&~`@':+!#]*)*)/ig, mode == 'html' ? '$1<a href="$2" target="_blank">$2</a>' : '$1[url]$2[/url]');
	str = str.replace(/([^\w>=\]"'\/@]|^)((www\.)([\w\-]+\.)*[:\.@\-\w\u4e00-\u9fa5]+\.([\.a-zA-Z0-9]+|\u4E2D\u56FD|\u7F51\u7EDC|\u516C\u53F8)((\?|\/|:)+[\w\.\/=\?%\-&~`@':+!#]*)*)/ig, mode == 'html' ? '$1<a href="$2" target="_blank">$2</a>' : '$1[url]$2[/url]');
	str = str.replace(/([^\w->=\]:"'\.\/]|^)(([\-\.\w]+@[\.\-\w]+(\.\w+)+))/ig, mode == 'html' ? '$1<a href="mailto:$2">$2</a>' : '$1[email]$2[/email]');
	for(var i = 0; i <= codecount; i++) {
		str = str.replace("[\tJSPRUN_CODE_" + i + "\t]", codehtml[i]);
	}
	return str;
}

function parsemozcode(str, mode) {
	str= str.replace(/\s*\[code(=(.*?))?\]([\s\S]+?)\[\/code\]\s*/ig, function($1, $2,$3,$4) {return codetag($2,$4);});
	for(var i = 0; i <= codecount; i++) {
		var codeh = codehtml[i];
		codeh = trim(codeh.replace(/&((#(32|127|160|173))|shy|nbsp);/ig, ' '));
		//codeh= codeh.replace(/(\r\n|\n|\r)/ig, '');
		str = str.replace("[\tJSPRUN_CODE_" + i + "\t]", codeh);
	}
	return str;
}
function codetag(text1,text2) {
	codecount++;
	text2 = text2.replace(/<br[^\>]*>/ig, '\n');
	text2 = text2.replace(/^[\n\r]*([\s\S]+?)[\n\r]*$/ig, '$1');
	if(typeof wysiwyg != 'undefined' && wysiwyg) text2 = text2.replace(/<(\/|)[A-Za-z].*?>/ig, '');
	text1 = isUndefined(text1)?'':text1;
	codehtml[codecount] = '[code'+text1+']' + text2 + '[/code]';
	return '[\tJSPRUN_CODE_' + codecount + '\t]';
}

function loadData() {
	var message = '';
	if(is_ie) {
		try {
			textobj.load('JspRun!');
			var oXMLDoc = textobj.XMLDocument;
			var nodes = oXMLDoc.documentElement.childNodes;
			message = nodes.item(nodes.length - 1).getAttribute('message');
		} catch(e) {}
	} else if(window.sessionStorage) {
		try {
			message = sessionStorage.getItem('JspRun!');
		} catch(e) {}
	}
	message = message.toString();

	if(in_array((message = trim(message)), ['', 'null', 'false', null, false])) {
		alert(lang['post_autosave_none']);
		return;
	}
	if(!confirm(lang['post_autosave_confirm'])) {
		return;
	}

	var formdata = message.split(/\x09\x09/);
	for(var i = 0; i < $('postform').elements.length; i++) {
		var el = $('postform').elements[i];
		if(el.name != '' && (el.tagName == 'TEXTAREA' || el.tagName == 'INPUT' && (el.type == 'text' || el.type == 'checkbox' || el.type == 'radio'))) {
			for(var j = 0; j < formdata.length; j++) {
				var ele = formdata[j].split(/\x09/);
				if(ele[0] == el.name) {
					elvalue = !isUndefined(ele[3]) ? ele[3] : '';
					if(ele[1] == 'INPUT') {
						if(ele[2] == 'text') {
							el.value = elvalue;
						} else if((ele[2] == 'checkbox' || ele[2] == 'radio') && ele[3] == el.value) {
							el.checked = true;
							evalevent(el);
						}
					} else if(ele[1] == 'TEXTAREA') {
						if(ele[0] == 'message') {
							if(typeof wysiwyg == 'undefined' || !wysiwyg) {
								textobj.value = elvalue;
							} else {
								editdoc.body.innerHTML = bbcode2html(elvalue);
							}
						} else {
							el.value = elvalue;
						}
					}
					break
				}
			}
		}
	}
}

function evalevent(obj) {
	var script = obj.parentNode.innerHTML;
	var re = /onclick="(.+?)["|>]/ig;
	var matches = re.exec(script);
	if(matches != null) {
		matches[1] = matches[1].replace(/this\./ig, 'obj.');
		eval(matches[1]);
	}
}

function saveData(data, del) {
	if(!data && isUndefined(del)) {
		return;
	}
	if(typeof wysiwyg != 'undefined' && typeof editorid != 'undefined' && typeof bbinsert != 'undefined' && bbinsert && $(editorid + '_mode') && $(editorid + '_mode').value == 1) {
		data = html2bbcode(data);
	}
	var formdata = '';
	if(isUndefined(del)) {
		for(var i = 0; i < $('postform').elements.length; i++) {
			var el = $('postform').elements[i];
			if(el.name != '' && (el.tagName == 'TEXTAREA' || el.tagName == 'INPUT' && (el.type == 'text' || el.type == 'checkbox' || el.type == 'radio')) && el.name.substr(0, 6) != 'attach') {
				var elvalue = el.name == 'message' ? data : el.value;
				if((el.type == 'checkbox' || el.type == 'radio') && !el.checked) {
					continue;
				}
				formdata += el.name + String.fromCharCode(9) + el.tagName + String.fromCharCode(9) + el.type + String.fromCharCode(9) + elvalue + String.fromCharCode(9, 9);
			}
		}
	}
	if(is_ie) {
		try {
			var oXMLDoc = textobj.XMLDocument;
			var root = oXMLDoc.firstChild;
			if(root.childNodes.length > 0) {
				root.removeChild(root.firstChild);
			}
			var node = oXMLDoc.createNode(1, 'POST', '');
			var oTimeNow = new Date();
			oTimeNow.setHours(oTimeNow.getHours() + 24);
			textobj.expires = oTimeNow.toUTCString();
			node.setAttribute('message', formdata);
			oXMLDoc.documentElement.appendChild(node);
			textobj.save('JspRun!');
		} catch(e) {}
	} else if(window.sessionStorage) {
		try {
			sessionStorage.setItem('JspRun!', formdata);
		} catch(e) {}
	}
}

function setCaretAtEnd() {
	var obj = typeof wysiwyg == 'undefined' || !wysiwyg ? $('postform').message : editwin;
	if(typeof wysiwyg != 'undefined' && wysiwyg) {
		if(is_moz || is_opera) {

		} else {
			var sel = editdoc.selection.createRange();
			sel.moveStart('character', strlen(getEditorContents()));
			sel.select();
		}
	} else {
		if(obj.createTextRange)  {
			var sel = obj.createTextRange();
			sel.moveStart('character', strlen(obj.value));
			sel.collapse();
			sel.select();
		}
	}
}

function smileyMenu(ctrl) {
	var smiley = ctrl.firstChild;
	ctrl.style.cursor = 'pointer';
	if(smiley.alt) {
		smiley.code = smiley.alt;
		smiley.alt = '';
	}
	if(smiley.title) {
		smiley.lw = smiley.title;
		smiley.title = '';
	}
	var url = smiley.src;
	var id = smiley.id;
	if(!is_ie){
		var smilie = ctrl.id;
		var smilies = smilie.substring(0,smilie.length-7);
		url = $(smilies).src;
		id = $(smilies).id;
	}
	smdiv[ctrl.id] = document.createElement('div');
	smdiv[ctrl.id].id = id + '_menu';
	smdiv[ctrl.id].style.display = 'none';
	smdiv[ctrl.id].style.width = '60px';
	smdiv[ctrl.id].style.height = '60px';
	smdiv[ctrl.id].className = 'popupmenu_popup';
	$('smilieslist').appendChild(smdiv[ctrl.id]);
	smdiv[ctrl.id].innerHTML = '<table width="100%" height="100%"><tr><td align="center" valign="middle"><img src="' + url + '" border="0" width="' + smiley.lw + '" /></td></tr></table>';
	showMenu(ctrl.id, 0, 0, 1, 0, 0, id);
}

function storeCaret(textEl){
	if(textEl.createTextRange){
		textEl.caretPos = document.selection.createRange().duplicate();
	}
}

if(is_ie >= 5 || is_moz >= 2) {
	window.onbeforeunload = function () {
		try {
			saveData(wysiwyg && bbinsert ? editdoc.body.innerHTML : textobj.value);
		} catch(e) {}
	};
}

function setmediacode(editorid) {
	insertText('[media='+$(editorid + '_mediatype').value+
		','+$(editorid + '_mediawidth').value+
		','+$(editorid + '_mediaheight').value+
		','+$(editorid + '_mediaautostart').value+']'+
		$(editorid + '_mediaurl').value+'[/media]');
	hideMenu();
}

function setmediatype(editorid) {
	var ext = $(editorid + '_mediaurl').value.lastIndexOf('.') == -1 ? '' : $(editorid + '_mediaurl').value.substr($(editorid + '_mediaurl').value.lastIndexOf('.') + 1, $(editorid + '_mediaurl').value.length).toLowerCase();
	if(ext == 'rmvb') {
		ext = 'rm';
	}
	if($(editorid + '_mediatyperadio_' + ext)) {
		$(editorid + '_mediatyperadio_' + ext).checked = true;
		$(editorid + '_mediatype').value = ext;
	}
}

var divdragstart = new Array();
function divdrag(e, op, obj) {
	if(op == 1) {
		if(is_ie) {
			document.body.onselectstart = function() {
				return false;
			}
		}
		divdragstart = is_ie ? [event.clientX, event.clientY] : [e.clientX, e.clientY];
		divdragstart[2] = parseInt(obj.style.left);
		divdragstart[3] = parseInt(obj.style.top);
		doane(e);
	} else if(op == 2 && divdragstart[0]) {
		var divdragnow = is_ie ? [event.clientX, event.clientY] : [e.clientX, e.clientY];
		obj.style.left = (divdragstart[2] + divdragnow[0] - divdragstart[0]) + 'px';
		obj.style.top = (divdragstart[3] + divdragnow[1] - divdragstart[1]) + 'px';
		doane(e);
	} else if(op == 3) {
		if(is_ie) {
			document.body.onselectstart = function() {
				return true;
			}
		}
		divdragstart = [];
		doane(e);
	}
}

function savePos(textBox){
    if(typeof(textBox.selectionStart) == "number"){
       textBox.setAttribute("start",textBox.selectionStart);
       textBox.setAttribute("end",textBox.selectionEnd);
     }
     else if(document.selection){
     	 var start=0;
     	 var end=0;
         var range = document.selection.createRange();
         if(range.parentElement().id == textBox.id){
                var range_all = document.body.createTextRange();
                range_all.moveToElementText(textBox);
                for (start=0; range_all.compareEndPoints("StartToStart", range) < 0; start++)
                    range_all.moveStart('character', 1);
                for (var i = 0; i <= start; i ++){
                    if (textBox.value.charAt(i) == '\n')
                        start++;
                }
                 var range_all = document.body.createTextRange();
                 range_all.moveToElementText(textBox);
                 for (end = 0; range_all.compareEndPoints('StartToEnd', range) < 0; end ++)
                     range_all.moveStart('character', 1);
                     for (var i = 0; i <= end; i ++){
                         if (textBox.value.charAt(i) == '\n'){
                             end ++;
                         }
                 }
           }
           textBox.start=start;
           textBox.end=end;
     }
}