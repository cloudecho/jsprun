/*
	[JspRun!] (C)2007-2011 JspRun Inc.
	This is NOT a freeware, use is subject to license terms

	$RCSfile: drag.js,v $
	$Revision: 1.4 $
	$Date: 2011/01/04 10:27:03 $
*/

var _Event = new Moz_event();
var Drag = new Drag_Events();
is_moz = is_moz || is_opera;

function Moz_event() {
	this.srcElement = null,
	this.setEvent = function(e) {
		_Event.srcElement = e.target;
		_Event.clientX = e.clientX;
		_Event.clientY = e.clientY;
	}
}

function Drag_Events() {
	this.handler = null;
	this.dragged = false;
	this.obj = null;
	this.tdiv = null;
	this.rootTable = null;
	this.layout = null;
	this.disable = null;
	this.getEvent = function() {
		if(is_ie) {
			return event;
		} else if(is_moz) {
			return _Event;
		}
	}

	this.dragStart = function(event, disable) {
		if(Drag.dragged) return;
		Drag.disable = disable;
		if(is_ie) {
			document.body.onselectstart = function() {
				return false;
			}
		}
		Drag.obj = Drag.getEvent().srcElement;

		if(Drag.obj.tagName == "TD" || Drag.obj.tagName == "TR") {
			Drag.obj = Drag.obj.offsetParent.parentNode;
			Drag.obj.style.zIndex = 100;
		} else if(Drag.obj.parentNode.tagName == "TD" || Drag.obj.parentNode.tagName == "TR") {
			Drag.obj = Drag.obj.parentNode;
			Drag.obj = Drag.obj.offsetParent.parentNode;
			Drag.obj.style.zIndex = 100;
		} else if(Drag.obj.tagName == "DIV") {
			Drag.obj = Drag.obj.parentNode;
			Drag.obj.style.zIndex = 100;
		} else {
			return;
		}

		Drag.dragged = true;
		Drag.tdiv = document.createElement("DIV");
		Drag.tdiv.innerHTML = Drag.obj.innerHTML;
		Drag.tdiv.className = "tempDIV";
		Drag.tdiv.style.filter = "alpha(opacity=50)";
		Drag.tdiv.style.opacity = 0.5;
		Drag.tdiv.style.width = Drag.obj.offsetWidth + 'px';
		Drag.tdiv.style.Height = Drag.obj.offsetHeight + 'px';
		Drag.tdiv.style.top = Drag.getInfo(Drag.obj).top + 'px';
		Drag.tdiv.style.left = Drag.getInfo(Drag.obj).left + 'px';
		Drag.obj.parentNode.appendChild(Drag.tdiv);
		Drag.lastX = Drag.getEvent().clientX;
		Drag.lastY = Drag.getEvent().clientY;
		Drag.lastLeft = parseInt(Drag.tdiv.style.left);
		Drag.lastTop = parseInt(Drag.tdiv.style.top);
		Drag.lastTop = parseInt(Drag.lastTop) - document.body.scrollTop;
		if(is_ie) {
			event.returnValue = false;
		} else {
			event.preventDefault();
		}
		Drag.handler.dragStart();
	}

	this.onDrag = function() {
		if((!Drag.dragged) || Drag.obj == null) {
			return;
		}
		var tX = Drag.getEvent().clientX;
		var tY = Drag.getEvent().clientY;
		Drag.tdiv.style.left = (parseInt(Drag.lastLeft) + tX - Drag.lastX) + 'px';
		Drag.tdiv.style.top = (parseInt(Drag.lastTop) + tY - Drag.lastY) + 'px';
		Drag.tdiv.style.top = (parseInt(Drag.tdiv.style.top) + document.body.scrollTop) + 'px';
		tY = tY + document.body.scrollTop;
		Drag.handler.onDrag(tX, tY);
		var s_area = Drag.getInfo(Drag.tdiv);
		if(tX > s_area.right) {
			Drag.tdiv.style.left = tX - 20 + 'px';
		}
		if(tY > s_area.bottom) {
			Drag.tdiv.style.top = tY - 10 + 'px';
		}
	}

	this.dragEnd = function() {
		if(is_ie) {
			document.body.onselectstart = function() {
				return true;
			}
		}
		if(!Drag.dragged) {
			return;
		}
		Drag.dragged = false;
		Drag.handler.dragEnd();
		Drag.obj.style.zIndex = 1;
		Drag.tdiv.parentNode.removeChild(Drag.tdiv);
		Drag.obj = null;
	}

	this.getInfo = function(o) {
		var to = new Object();
		to.left = to.right = to.top = to.bottom = 0;
		var twidth = o.offsetWidth;
		var theight = o.offsetHeight;
		while(o) {
			to.left += o.offsetLeft;
			to.top += o.offsetTop;
			o = o.offsetParent;
		}
		to.right = to.left + twidth;
		to.bottom = to.top + theight;
		return to;
	}

	this.mozinit = function() {
		if(is_moz) {
			Drag.rootTable.cells = new Array();
			var tcells = Drag.rootTable.getElementsByTagName("TD");
			for(var i = 0; i < tcells.length; i++) {
				if(tcells[i].offsetParent == Drag.rootTable) {
					Drag.rootTable.cells.push(tcells[i]);
				}
			}
		}
	}

	this.init = function(handler) {
		Drag.handler = new handler();
		Drag.handler.init();
		if(is_ie) {
			document.onmousemove = Drag.onDrag;
			document.onmouseup = Drag.dragEnd;
		} else if(is_moz) {
			document.body.setAttribute("onMouseMove", "_Event.setEvent(event);Drag.onDrag();");
			document.body.setAttribute("onMouseUp", "_Event.setEvent(event);Drag.dragEnd();");
		}
	}
}