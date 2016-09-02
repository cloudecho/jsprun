/*
	[JspRun!] (C)2007-2011 JspRun Inc.
	This is NOT a freeware, use is subject to license terms

	$RCSfile: drag_space.js,v $
	$Revision: 1.5 $
	$Date: 2011/01/04 10:27:03 $
*/

function Space_Memcp() {
	this.init = function() {
		Drag.rootTable = $("parentTable");
		Drag.mozinit();
	}

	this.dragStart = function() {
	}

	this.onDrag = function(tX, tY) {
		for(var i = 0; i < Drag.rootTable.cells.length; i++) {
			var parentCell = Drag.getInfo(Drag.rootTable.cells[i]);
			if(tX >= parentCell.left && tX <= parentCell.right && tY >= parentCell.top && tY <= parentCell.bottom) {
				var layouti = Drag.rootTable.cells[i].id.replace('main_layout', '');
				if((',' + Drag.disable + ',').indexOf(',' + layouti + ',') != -1) {
					return;
				}
				var subTables = Drag.rootTable.cells[i].getElementsByTagName("DIV");
				if(subTables.length == 0) {
					if(tX >= parentCell.left && tX <= parentCell.right && tY >= parentCell.top && tY <= parentCell.bottom) {
						Drag.rootTable.cells[i].appendChild(Drag.obj);
						Drag.handler.resize();
					}
					break;
				}
				Drag.layout = layouti;
				for(var j = 0; j < subTables.length; j++) {
					var subTable = Drag.getInfo(subTables[j]);
					if(tX >= subTable.left && tX <= subTable.right && tY >= subTable.top && tY <= subTable.bottom) {
						Drag.rootTable.cells[i].insertBefore(Drag.obj, subTables[j]);
						Drag.handler.resize();
						break;
					} else {
						Drag.rootTable.cells[i].appendChild(Drag.obj);
						Drag.handler.resize();
					}
				}
			}
		}
	}

	this.dragEnd = function() {
		var pid = Drag.obj;
		do {
			if(!pid.previousSibling) {
				pid = pid.parentNode;
				break;
			}
			pid = pid.previousSibling;
		} while(pid.tagName != 'DIV');
		pid = pid.id;
		if(Drag.layout != null) {
			Drag.handler.clearResult(Drag.obj);
			if(layout[Drag.layout].indexOf(pid) != -1) {
				layout[Drag.layout] = layout[Drag.layout].replace('[' + pid + ']', '[' + pid + '][' + Drag.obj.id + ']');
			} else {
				layout[Drag.layout] = '[' + Drag.obj.id + '][' + layout[Drag.layout] + ']';
			}
			Drag.handler.trimResult();
		}
	}

	this.resize = function() {
		Drag.tdiv.style.width = Drag.obj.offsetWidth + 'px';
	}

	this.del = function(obj) {
		try {
			if($('check_' + obj.id)) {
				$('check_' + obj.id).checked = false;
				$('menuitem_' + obj.id).style.display = 'none';
			}
		} catch (e) {}
		Drag.handler.clearResult(obj);
		Drag.handler.trimResult();
		obj.parentNode.removeChild(obj);
	}

	this.add = function(layoutn, divid, title, disable) {
		var clone = $('dragClone').innerHTML;
		layoutid = 'main_layout' + layoutn;
		if($(layoutid).style.display == 'none') {
			if(layoutn == 2) {
				layoutn = 0;
			} else if(layoutn == 0) {
				layoutn = 2;
			}
			layoutid = 'main_layout' + layoutn;
		}
		clone = clone.replace(/\[id\]/g, divid);
		clone = clone.replace(/\[title\]/g, title);
		clone = clone.replace('[disable]', disable);
		$(layoutid).innerHTML += clone;
		layout[layoutn] += '[' + divid + ']';
		Drag.handler.trimResult();
	}

	this.check = function(layoutn, divid, title, disable) {
		var exist = 0;
		for (var side in layout) {
			var s = ']' + layout[side] + '[';
			s = s.split('][');
			for (var i in s) {
				if(s[i] == divid) {
					exist = 1;break;
				}
			}
		}
		try {
			if(exist) {
				Drag.handler.del($(divid));
				$('menuitem_' + divid).style.display = 'none';
			} else {
				Drag.handler.add(layoutn, divid, title, disable);
				$('menuitem_' + divid).style.display = '';
			}
		} catch(e) {}
	}

	this.clearResult = function(o) {
		for(i = 0; i < layout.length; i++) {
			layout[i] = layout[i].replace('[' + o.id + ']', '');
		}
	}

	this.trimResult = function() {
		for(i = 0; i < layout.length; i++) {
			layout[i] = layout[i].replace('[]', '');
			layout[i] = layout[i].replace('[[', '[');
			layout[i] = layout[i].replace(']]', ']');
		}
	}
}

function clearSide(side) {
	if(side == 0) {
		targetside = 2;
	} else if(side == 2) {
		targetside = 0;
	} else {
		return;
	}
	targetcellid = 'main_layout' + targetside;
	layout[targetside] += layout[side];
	var s = ']' + layout[side] + '[';
	s = s.split('][');
	for (var i in s) {
		if(s[i] != '') {
			$(targetcellid).appendChild($(s[i]));
		}
	}
	layout[side] = '';
}

function leftSide() {
	$('main_layout0').style.display = '';
	$('main_layout2').style.display = 'none';
	$('side_1').checked = true;
	tmp_spaceside = 1;
	clearSide(2);
	Drag.mozinit();
}

function rightSide() {
	$('main_layout0').style.display = 'none';
	$('main_layout2').style.display = '';
	$('side_2').checked = true;
	tmp_spaceside = 2;
	clearSide(0);
	Drag.mozinit();
}

function bothSide() {
	$('main_layout0').style.display = '';
	$('main_layout2').style.display = '';
	$('side_0').checked = true;
	tmp_spaceside = 0;
	Drag.mozinit();
}

function setStyle(styledir, stylename) {
	$('style_' + styledir).checked = true;
	$('stylecss').href = '';
	$('stylecss').href = 'mspace/' + styledir + '/style.css';
	tmp_styledir = styledir;
	if(is_ie) {
		$('stylecss').onload = setStyleonLoad;
	}
}

function setStyleonLoad() {
	var obj = document.styleSheets['stylecss'].rules;
	for (i in obj) {
		if(obj[i].selectorText == '#menu_top' && obj[i].style.width) {
			$('menu_top').style.width = obj[i].style.width;
		}
		if(obj[i].selectorText == '#header' && obj[i].style.width) {
			$('header').style.width = obj[i].style.width;
		}
		if(obj[i].selectorText == '#header DIV.bg' && obj[i].style.width) {
			$('headerbg').style.width = obj[i].style.width;
		}
		if(obj[i].selectorText == '#menu' && obj[i].style.width) {
			$('menu').style.width = obj[i].style.width;
		}
		if(obj[i].selectorText == '.outer' && obj[i].style.width) {
			$('outer').style.width = obj[i].style.width;
		}
		if(obj[i].selectorText == '#footer' && obj[i].style.width) {
			$('footer').style.width = obj[i].style.width;
		}
	}
}

function saveLayout() {
	if(layout[1] == '') {
		alert(space_layout_nocenter);
	} else {
		$('spacelayout0').value = layout[0];
		$('spacelayout1').value = layout[1];
		$('spacelayout2').value = layout[2];
		$('dragform').submit();
	}
}

function previewLayout(uid) {
	if(layout[1] == '') {
		alert(space_layout_nocenter);
	}else{
		if(layout[0] == '') {
			tmp_spaceside = 2;
		}
		if(layout[2] == '') {
			tmp_spaceside = 1;
		}
		window.open('space.jsp?uid=' + uid + '&preview=' + layout[0] + '|' + layout[1] + '|' + layout[2] + '&spaceside=' + tmp_spaceside + '&style=' + tmp_styledir, '', '');
	}
}

function previewtext(obj, text) {
	if(obj == 'pre_title' && text == '') {
		text = $('pre_title_default').innerHTML;
	}
	$(obj).innerHTML = htmlspecialchars(text);
}

function checkinit(module) {
	try {
		if($('check_' + module)) {
			$('check_' + module).checked = true;
			$('menuitem_' + module).style.display = '';
		}
	} catch(e) {}
}