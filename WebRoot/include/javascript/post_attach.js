/*
	[JspRun!] (C)2007-2011 JspRun Inc.
	This is NOT a freeware, use is subject to license terms

	$RCSfile: post_attach.js,v $
	$Revision: 1.6 $
	$Date: 2011/01/04 10:27:03 $
*/

var aid = 1;
var attachexts = new Array();
var attachwh = new Array();

function delAttach(id) {
	$('attachbody').removeChild($('attach_' + id).parentNode.parentNode);
	$('attachbody').innerHTML == '' && addAttach();
	$('localimgpreview_' + id + '_menu') ? document.body.removeChild($('localimgpreview_' + id + '_menu')) : null;
}

function addAttach() {
	newnode = $('attachbodyhidden').firstChild.cloneNode(true);
	var id = aid;
	var tags;
	tags = newnode.getElementsByTagName('input');
	for(i in tags) {
		if(tags[i].name == 'attach') {
			tags[i].id = 'attach_' + id;
			tags[i].onchange = function() {insertAttach(id)};
			tags[i].unselectable = 'on';
			tags[i].name = 'attachfile'+id;
		}else if(tags[i].name == 'localid[]') {
			tags[i].id = 'attachlocal_'+id;
			tags[i].value = id;
		}
	}
	tags = newnode.getElementsByTagName('span');
	for(i in tags) {
		if(tags[i].id == 'localfile[]') {
			tags[i].id = 'localfile_' + id;
		}
	}
	aid++;
	$('attachbody').appendChild(newnode);
}

addAttach();
function imageValue(id){
        id.select();
        return document.selection.createRange().text;
}
function insertAttach(id) {
	var localimgpreview = '';
	var path = $('attach_' + id).value;
	var ext = path.lastIndexOf('.') == -1 ? '' : path.substr(path.lastIndexOf('.') + 1, path.length).toLowerCase();
	var re = new RegExp("(^|\\s|,)" + ext + "($|\\s|,)", "ig");
	var localfile = $('attach_' + id).value.substr($('attach_' + id).value.replace(/\\/g, '/').lastIndexOf('/') + 1);
	if(path == '') {
		return;
	}
	if(extensions != '' && (re.exec(extensions) == null || ext == '')) {
		alert(lang['post_attachment_ext_notallowed']);
		return;
	}
	attachexts[id] = is_ie && in_array(ext, ['gif', 'jpg', 'png', 'bmp']) && typeof supe == 'undefined' ? 2 : 1;
	if(attachexts[id] == 2) {
		var imageName = imageValue($('attach_' + id));
		$('attachlocal_'+id).value=imageName;
		$('img_hidden').alt = id;
		$('img_hidden').filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'image';
		try {
			$('img_hidden').filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imageName;
		} catch (e) {
			alert(lang['post_attachment_img_invalid']);
			return;
		}
		var wh = {'w' : $('img_hidden').offsetWidth, 'h' : $('img_hidden').offsetHeight};
		var aid = $('img_hidden').alt;
		if(wh['w'] >= thumbwidth || wh['h'] >= thumbheight) {
			wh = attachthumbImg(wh['w'], wh['h']);
		}
		attachwh[id] = wh;
		$('img_hidden').style.width = wh['w']
		$('img_hidden').style.height = wh['h'];
		$('img_hidden').filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'scale';
		div = document.createElement('div');
		div.id = 'localimgpreview_' + id + '_menu';
		div.style.display = 'none';
		div.style.marginLeft = '20px';
		div.className = 'popupmenu_popup';
		document.body.appendChild(div);
		div.innerHTML = '<img style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=\'scale\',src=\'' + imageName +'\');width:'+wh['w']+'px;height:'+wh['h']+'px" src=\'images/common/none.gif\' border="0" aid="attach_'+ aid +'" alt="" />';
	}

	$('localfile_' + id).innerHTML = '<a href="###delAttach" onclick="delAttach(' + id + ')">[' + lang['post_attachment_deletelink'] + ']</a> <a href="###insertAttach" title="' + lang['post_attachment_insert'] + '" onclick="insertAttachtext(' + id + ');return false;">[' + lang['post_attachment_insertlink'] + ']</a> ' +
		(attachexts[id] == 2 ? '<span id="localimgpreview_' + id + '" onmouseover="showMenu(this.id, 0, 0, 1, 0)"> <span class="smalltxt">[' +id + ']</span> <a href="###attachment" onclick="insertAttachtext(' + id + ');return false;">' + localfile + '</a></span>' : '<span class="smalltxt">[' + id + ']</span> ' + localfile);
	$('attach_' + id).style.display = 'none';
	addAttach();
}

function attachpreview(obj, preview, width, height) {
	if(is_ie) {
		$(preview + '_hidden').filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'image';
		try {
			$(preview + '_hidden').filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = obj.value;
		} catch (e) {
			alert(lang['post_attachment_img_invalid']);
			return;
		}
		var wh = {'w' : $(preview + '_hidden').offsetWidth, 'h' : $(preview + '_hidden').offsetHeight};
		var aid = $(preview + '_hidden').alt;
		if(wh['w'] >= width || wh['h'] >= height) {
			wh = attachthumbImg(wh['w'], wh['h'], width, height);
		}
		$(preview + '_hidden').style.width = wh['w']
		$(preview + '_hidden').style.height = wh['h'];
		$(preview + '_hidden').filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'scale';
		$(preview).style.width = 'auto';
		$(preview).innerHTML = '<img style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=\'scale\',src=\'' + obj.value+'\');width:'+wh['w']+';height:'+wh['h']+'" src=\'images/common/none.gif\' border="0" alt="" />';
	}
}

function insertAttachtext(id) {
	if(!attachexts[id]) {
		return;
	}
	if(attachexts[id] == 2) {
		bbinsert && wysiwyg ? insertText($('localimgpreview_' + id + '_menu').innerHTML, false) : AddText('[localimg=' + attachwh[id]['w'] + ',' + attachwh[id]['h'] + ']' + id + '[/localimg]');
	} else {
		bbinsert && wysiwyg ? insertText('[local]' + id + '[/local]', false) : AddText('[local]' + id + '[/local]');
	}
}

function attachthumbImg(w, h, twidth, theight) {
	twidth = !twidth ? thumbwidth : twidth;
	theight = !theight ? thumbheight : theight;
	var x_ratio = twidth / w;
	var y_ratio = theight / h;
	var wh = new Array();
	if((x_ratio * h) < theight) {
		wh['h'] = Math.ceil(x_ratio * h);
		wh['w'] = twidth;
	} else {
		wh['w'] = Math.ceil(y_ratio * w);
		wh['h'] = theight;
	}
	return wh;
}

function restore(aid) {
	obj = $('attach'+aid);
	objupdate = $('attachupdate'+aid);
	obj.style.display = '';
	objupdate.innerHTML = '';

}

function attachupdate(id) {
	obj = $('attach'+id);
	objupdate = $('attachupdate'+id);
	obj.style.display = 'none';
	objupdate.innerHTML = '<input type="file" name="uploadFile[' + id + ']" size="15"> <input class="button" type="button" value="' + lang['cancel'] + '" onclick="restore(\'' + id + '\')">';
}

function insertAttachTag(aid) {
	if(bbinsert && wysiwyg) {
		insertText('[attach]' + aid + '[/attach]', false);
	} else {
		AddText('[attach]' + aid + '[/attach]');
	}
}

function insertAttachimgTag(aid) {
	if(bbinsert && wysiwyg) {
		eval('var attachimg = $(\'preview_' + aid + '\')');
		insertText('<img src="' + attachimg.src + '" border="0" aid="attachimg_' + aid + '" width="' + attachimg.width + '" alt="" />', false);
	} else {
		AddText('[attachimg]' + aid + '[/attachimg]');
	}
}
