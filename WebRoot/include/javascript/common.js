/*
	[JspRun!] (C)2007-2011 JspRun Inc.
	This is NOT a freeware, use is subject to license terms

	$RCSfile: common.js,v $
	$Revision: 1.8 $
	$Date: 2011/01/04 10:27:03 $
*/

var lang = new Array();
var userAgent = navigator.userAgent.toLowerCase();
var is_opera = userAgent.indexOf('opera') != -1 && opera.version();
var is_moz = (navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3);
var is_ie = (userAgent.indexOf('msie') != -1 && !is_opera) && userAgent.substr(userAgent.indexOf('msie') + 5, 3);
function $(id) {
	return document.getElementById(id);
}
Array.prototype.push = function(value) {
	this[this.length] = value;
	return this.length;
}

function checkall(form, prefix, checkall) {
	var checkall = checkall ? checkall : 'chkall';
	for(var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if(e.name && e.name != checkall && (!prefix || (prefix && e.name.match(prefix)))) {
			e.checked = form.elements[checkall].checked;
		}
	}
}

function doane(event) {
	e = event ? event : window.event;
	if(is_ie) {
		e.returnValue = false;
		e.cancelBubble = true;
	} else if(e) {
		e.stopPropagation();
		e.preventDefault();
	}
}

function fetchCheckbox(cbn) {
	return $(cbn) && $(cbn).checked == true ? 1 : 0;
}

function getcookie(name) {
	var cookie_start = document.cookie.indexOf(name);
	var cookie_end = document.cookie.indexOf(";", cookie_start);
	return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
}

function thumbImg(obj) {
	var zw = obj.width;
	var zh = obj.height;
	if(is_ie && zw == 0 && zh == 0) {
		var matches;
		re = /width=(["']?)(\d+)(\1)/i;
		matches = re.exec(obj.outerHTML);
		zw = matches[2];
		re = /height=(["']?)(\d+)(\1)/i;
		matches = re.exec(obj.outerHTML);
		zh = matches[2];
	}
	obj.resized = true;
	obj.style.width = zw + 'px';
	obj.style.height = 'auto';
	if(obj.offsetHeight > zh) {
		obj.style.height = zh + 'px';
		obj.style.width = 'auto';
	}
	if(is_ie) {
		var imgid = 'img_' + Math.random();
		obj.id = imgid;
		setTimeout('try {if ($(\''+imgid+'\').offsetHeight > '+zh+') {$(\''+imgid+'\').style.height = \''+zh+'px\';$(\''+imgid+'\').style.width = \'auto\';}} catch(e){}', 1000);
	}
	obj.onload = null;
}

function imgzoom(obj) {}

function in_array(needle, haystack) {
	if(typeof needle == 'string' || typeof needle == 'number') {
		for(var i in haystack) {
			if(haystack[i] == needle) {
					return true;
			}
		}
	}
	return false;
}

function setcopy(text, alertmsg){
	if(is_ie) {
		clipboardData.setData('Text', text);
		alert(alertmsg);
	} else if(prompt('Press Ctrl+C Copy to Clipboard', text)) {
		alert(alertmsg);
	}
}
function setcopys(text, alertmsg){
	if(is_ie) {
		clipboardData.setData('Text', text);
		alert(alertmsg);
	} else {
		alert(alertmsg);
	}
}

function isUndefined(variable) {
	return typeof variable == 'undefined' ? true : false;
}

function mb_strlen(str) {
	var len = 0;
	for(var i = 0; i < str.length; i++) {
		len += str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255 ? (charset == 'utf-8' ? 3 : 2) : 1;
	}
	return len;
}

function setcookie(cookieName, cookieValue, seconds, path, domain, secure) {
	var expires = new Date();
	expires.setTime(expires.getTime() + seconds);
	document.cookie = escape(cookieName) + '=' + escape(cookieValue)
		+ (expires ? '; expires=' + expires.toGMTString() : '')
		+ (path ? '; path=' + path : '/')
		+ (domain ? '; domain=' + domain : '')
		+ (secure ? '; secure' : '');
}

function strlen(str) {
	return (is_ie && str.indexOf('\n') != -1) ? str.replace(/\r?\n/g, '_').length : str.length;
}

function updatestring(str1, str2, clear) {
	str2 = '_' + str2 + '_';
	return clear ? str1.replace(str2, '') : (str1.indexOf(str2) == -1 ? str1 + str2 : str1);
}

function toggle_collapse(objname, noimg) {
	var obj = $(objname);
	obj.style.display = obj.style.display == '' ? 'none' : '';
	if(!noimg) {
		var img = $(objname + '_img');
		img.src = img.src.indexOf('_yes.gif') == -1 ? img.src.replace(/_no\.gif/, '_yes\.gif') : img.src.replace(/_yes\.gif/, '_no\.gif')
	}
	var collapsed = getcookie('jsprun_collapse');
	collapsed =  updatestring(collapsed, objname, !obj.style.display);
	setcookie('jsprun_collapse', collapsed, (collapsed ? 86400 * 30 : -(86400 * 30 * 1000)));
}

function trim(str) {
	return (str + '').replace(/(\s+)$/g, '').replace(/^\s+/g, '');
}

function updateseccode() {
	type = seccodedata[2];
	var rand = Math.random();
	if(type == 2) {
		$('seccodeimage').innerHTML = '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="' + seccodedata[0] + '" height="' + seccodedata[1] + '" align="middle">'
			+ '<param name="allowScriptAccess" value="sameDomain" /><param name="movie" value="servlet/seccode?update=' + rand + '" /><param name="quality" value="high" /><param name="wmode" value="transparent" /><param name="bgcolor" value="#ffffff" />'
			+ '<embed src="servlet/seccode?update=' + rand + '" quality="high" wmode="transparent" bgcolor="#ffffff" width="' + seccodedata[0] + '" height="' + seccodedata[1] + '" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" /></object>';
	} else {
		$('seccodeimage').innerHTML = '<img id="seccode" onclick="updateseccode()" width="' + seccodedata[0] + '" height="' + seccodedata[1] + '" src="servlet/seccode?update=' + rand + '" class="absmiddle" alt="" />';
	}
}

function updatesecqaa() {
	var x = new Ajax();
	x.get('ajax.do?action=updatesecqaa&inajax=1', function(s) {
		$('secquestion').innerHTML = s;
	});
}

function _attachEvent(obj, evt, func) {
	if(obj.addEventListener) {
		obj.addEventListener(evt, func, false);
	} else if(obj.attachEvent) {
		obj.attachEvent("on" + evt, func);
	}
}
function preg_replaces(rewritestatus,urls) {
	var htmlcontent = document.body.innerHTML;
	if(rewritestatus&1){
		searcharray = /<a\s+?(title=[^>]+(\s+?))?href=\"forumdisplay\.jsp\?fid=(\d+)(&amp;orderby=(\w+)&amp;ascdesc=(\w+))?(&amp;page=(\d+)?)?\"([^>]*)>/ig;
		replacearray = rewrite_forum("$3", "$8", "$9");
		htmlcontent = htmlcontent.replace(searcharray, replacearray);
	}
	if(rewritestatus&2){
		searcharray = /<a\s+?([^>]*)href=\"viewthread\.jsp\?tid=(\d+)(&amp;highlight=\w*)?(&amp;orderby=(\w+)&amp;ascdesc=(\w+))?(&amp;extra=(page%3D(\d+))?)?(&amp;page=(\d+))?\"([^>]*)>/ig;
	}	
}


//***************************************************新添加****************************************
//var VERHASH=Math.random();
//Smilies
function smilies_show(id, smcols, method, seditorkey) {
	if(seditorkey && !$(seditorkey + 'smilies_menu')) {
		var div = document.createElement("div");
		div.id = seditorkey + 'smilies_menu';
		div.style.display = 'none';
		div.className = 'smilieslist';
		$('append_parent').appendChild(div);
		var div = document.createElement("div");
		div.id = id;
		div.style.overflow = 'hidden';
		$(seditorkey + 'smilies_menu').appendChild(div);
	}
	if(typeof smilies_type == 'undefined') {
		var scriptNode = document.createElement("script");
		scriptNode.type = "text/javascript";
		scriptNode.src = 'forumdata/cache/smilies_var.js?'+Math.random();
		$('append_parent').appendChild(scriptNode);
		if(is_ie) {
			scriptNode.onreadystatechange = function() {
				smilies_onload(id, smcols, method, seditorkey);
			}
		} else {
			scriptNode.onload = function() {
				smilies_onload(id, smcols, method, seditorkey);
			}
		}
	} else {
		smilies_onload(id, smcols, method, seditorkey);
	}
}

var currentstype = null;
function smilies_onload(id, smcols, method, seditorkey) {
	smile = getcookie('smile').split('D');
	if(typeof smilies_type != 'undefined') {
		currentstype = smile[0] ? smile[0] : 1;
		smiliestype = '<div class="smiliesgroup" style="margin-right: 0"><ul>';
		for(i in smilies_type) {
			smiliestype += '<li><a href="javascript:;" hidefocus="ture" ' + (currentstype == i ? 'class="current"' : '') + ' id="stype'+i+'" onclick="smilies_switch(\'' + id + '\', \'' + smcols + '\', '+i+', 1, ' + method + ', \'' + seditorkey + '\');if(currentstype) {$(\'stype\'+currentstype).className=\'\';}this.className=\'current\';currentstype='+i+';">'+smilies_type[i][0]+'</a></li>';
		}
		smiliestype += '</ul></div>';
		$(id).innerHTML = smiliestype + '<div style="clear: both" class="float_typeid" id="' + id + '_data"></div><table class="smilieslist_table" id="' + id + '_preview_table" style="display: none"><tr><td class="smilieslist_preview" id="' + id + '_preview"></td></tr></table><div style="clear: both" class="smilieslist_page" id="' + id + '_page"></div>';
		smilies_switch(id, smcols, smile[0], smile[1], method, seditorkey);
	}
}

function smilies_switch(id, smcols, type, page, method, seditorkey) {
	type = type? type : 1;
	page = page ? page : 1;
	setcookie('smile', type + 'D' + page, 31536000);
	smiliesdata = '<table id="' + id + '_table" cellpadding="0" cellspacing="0" style="clear: both"><tr>';
	j = 0;
	for(i in smilies_array[type][page]) {
		if(j >= smcols) {
			smiliesdata += '<tr>';
			j = 0;
		}
		s = smilies_array[type][page][i];
		smiliesdata += s && s[0] ? '<td onmouseover="smilies_preview(\'' + id + '\', this, ' + s[5] + ')" onmouseout="smilies_preview(\'' + id + '\')" onclick="' + (method ? 'insertSmiley(' + s[0] + ')': 'seditor_insertunit(\'' + seditorkey + '\', \'' + s[1].replace(/'/, '\\\'') + ' \')') +
			'"><img id="smilie_' + s[0] + '" width="' + s[3] +'" height="' + s[4] +'" src="images/smilies/' + smilies_type[type][1] + '/' + s[2] + '" alt="' + s[1] + '" />' : '<td>';
		j++;
	}
	smiliesdata += '</table>';
	smiliespage = '';
	if(smilies_array[type].length > 1) {
		prevpage = ((prevpage = parseInt(page) - 1) < 1) ? smilies_array[type].length - 1 : prevpage;
		nextpage = ((nextpage = parseInt(page) + 1) == smilies_array[type].length) ? 1 : nextpage;
		smiliespage = '<div class="pags_act"><a href="javascript:;" onclick="smilies_switch(\'' + id + '\', \'' + smcols + '\', ' + type + ', ' + prevpage + ', ' + method + ', \'' + seditorkey + '\')">'+lang['last_page']+'</a>' +
			'<a href="javascript:;" onclick="smilies_switch(\'' + id + '\', \'' + smcols + '\', ' + type + ', ' + nextpage + ', ' + method + ', \'' + seditorkey + '\')">'+lang['next_page']+'</a></div>' +
			page + '/' + (smilies_array[type].length - 1);
	}
	$(id + '_data').innerHTML = smiliesdata;
	$(id + '_page').innerHTML = smiliespage;
}

function smilies_preview(id, obj, v) {
	if(!obj) {
		$(id + '_preview_table').style.display = 'none';
	} else {
		$(id + '_preview_table').style.display = '';
		$(id + '_preview').innerHTML = '<img width="' + v + '" src="' + obj.childNodes[0].src + '" />';
	}
}

function seditor_insertunit(key, text, textend, moveend) {
	$(key + 'message').focus();
	textend = isUndefined(textend) ? '' : textend;
	moveend = isUndefined(textend) ? 0 : moveend;
	startlen = strlen(text);
	endlen = strlen(textend);
	if(!isUndefined($(key + 'message').selectionStart)) {
		var opn = $(key + 'message').selectionStart + 0;
		if(textend != '') {
			text = text + $(key + 'message').value.substring($(key + 'message').selectionStart, $(key + 'message').selectionEnd) + textend;
		}
		$(key + 'message').value = $(key + 'message').value.substr(0, $(key + 'message').selectionStart) + text + $(key + 'message').value.substr($(key + 'message').selectionEnd);
		if(!moveend) {
			$(key + 'message').selectionStart = opn + strlen(text) - endlen;
			$(key + 'message').selectionEnd = opn + strlen(text) - endlen;
		}
	} else if(document.selection && document.selection.createRange) {
		var sel = document.selection.createRange();
		if(textend != '') {
			text = text + sel.text + textend;
		}
		sel.text = text.replace(/\r?\n/g, '\r\n');
		if(!moveend) {
			sel.moveStart('character', -endlen);
			sel.moveEnd('character', -endlen);
		}
		sel.select();
	} else {
		$(key + 'message').value += text;
	}
	hideMenu();
}
function codeinit(id,obj){
	if(is_ie){
		$(id).innerHTML = '<img src="images/common/flashvar.png" width="14" height="15" title='+lang['copy_code']+'>';
	}else{
		var code = obj.textContent;
		$(id).innerHTML = '<embed src="include/javascript/clipboard.swf" flashvars="clipboard='+encodeURIComponent(code)+'" quality="high" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" width="14" height="15" title='+lang['copy_code']+'>';
	}
}
function changeLanguage(language){
	var arr=language.split("_");
	if(arr.length>1){
		window.location='language.jsp?language='+arr[0]+'&country='+arr[1];
	}else{
		window.location='language.jsp?language='+arr[0];
	}
}