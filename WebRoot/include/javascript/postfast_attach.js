/*
	[JspRun!] (C)2007-2011 JspRun Inc.
	This is NOT a freeware, use is subject to license terms

	$RCSfile: postfast_attach.js,v $
	$Revision: 1.5 $
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
				tags[i].name= 'attachfile'+id;
			}else if(tags[i].name == 'localid[]') {
				tags[i].value = id;
			}
		}
		tags = newnode.getElementsByTagName('span');
		for(i in tags) {
			if(tags[i].id == 'localfile[]') {
				tags[i].id = 'localfile_' + id;
			}
		}
		$('attachbody').appendChild(newnode);
		aid++;
	}
	
	addAttach();
	
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
		$('localfile_' + id).innerHTML = '<a href="###delAttach" onclick="delAttach(' + id + ')">[' + lang['post_attachment_deletelink'] + ']</a> <a href="###insertAttach" title="' + lang['post_attachment_insert'] + '" onclick="insertAttachtext(' + id + ');return false;">[' + lang['post_attachment_insertlink'] + ']</a> ' +
			(attachexts[id] == 2 ? '<span id="localimgpreview_' + id + '" onmouseover="showMenu(this.id, 0, 0, 1, 0)"> <span class="smalltxt">[' +id + ']</span> <a href="###attachment" onclick="insertAttachtext(' + id + ');return false;">' + localfile + '</a></span>' : '<span class="smalltxt">[' + id + ']</span> ' + localfile);
		$('attach_' + id).style.display = 'none';
		addAttach();
	}
	function insertAttachtext(id) {
		if(!attachexts[id]) {
			return;
		}
		if(attachexts[id] == 2) {
			AddText('[local]' + id + '[/local]');
		} else {
			AddText('[local]' + id + '[/local]');
		}
	}
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