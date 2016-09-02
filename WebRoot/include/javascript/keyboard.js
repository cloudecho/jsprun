/**
 * JspRun_KeyBoard 1.0
 * author: Chang
 * date:2010-08-10
 */
/**
 * 配置信息
 */
lang["kb_closeKeyBoard"] = "X";

var jkbConfig = {
    /**
     * 键位数据
     * pre:前部不随机按键
     * randKey:随机按键
     * end:后部不随机按键
     * funKey:功能按键
     */
    keys: {
        row1: {
            pre: "`",
            randKey: "0123456789",
            end: "-=",
            funKey: "backSpace"
        },
        row2: {
            pre: "~!@#$%^&*()_+",
            funKey: "capsLock"
        },
        row3: {
            randKey: "qwertyuiop",
            end: "[]{}",
            funKey: "clear"
        },
        row4: {
            randKey: "asdfghjkl",
            end: ":;'\"|",
            funKey: "enter"
        },
        row5: {
            randKey: "zxcvbnm",
            end: "<>?,./\\"
        }
    },
    funKeyBtn: {
        "closeKeyBoard": {
            tag: "input",
            type: "button",
            value: lang["kb_closeKeyBoard"]
        },
        "backSpace": {
            btn: {
                tag: "input",
                type: "button",
                value: lang["kb_backSpace"]
            
            },
            td: {
                tag: "td",
                colSpan: "2"
            }
        },
        "capsLock": {
            btn: {
                tag: "input",
                type: "button",
                value: lang["kb_capsLockOn"]
            },
            td: {
                tag: "td",
                colSpan: "2"
            }
        },
        "clear": {
            btn: {
                tag: "input",
                type: "button",
                value: lang["kb_clear"]
            },
            td: {
                tag: "td",
                colSpan: "1"
            }
        },
        "enter": {
            btn: {
                tag: "input",
                type: "button",
                value: lang["kb_enter"]
            },
            td: {
                tag: "td",
                rowSpan: "2",
                colSpan: "1"
            }
        }
    },
    tableTag: {
        table: {
            align: "center",
            tag: "table",
            border: "0",
            cellspacing: "1",
            cellpadding: "0",
			width:"400px"
        },
        td: {
            tag: "td",
            valign: "middle"
        }
    },
    //标题
    title: lang["kb_title"]
};

/**
 * 使用软键盘输入
 * @param {Object} inputBox 绑定到的输入框 （类型:documentElement或elementID）
 * @param {Object} handle	触发控件（软键盘将在触发控件后面显示，若为空则在绑定的输入框后面显示）
 */
var jrunKeyBoard = function(inputBox, handle){
    if (typeof inputBox === "string") {
        inputBox = document.getElementById(inputBox);
    }
    var bindId = inputBox.id;//绑定输入框ID
    var bindElement = inputBox;//绑定的输入框对象
    if (bindElement.getAttribute("showKeyBoard")) {
        //若该输入框已有开启软键盘
        return;
    }
    var keyElements = [];//存放所有键位按钮
    var capsLockOn = false;//存放是否开启大写的状态
    var keyBoardDiv = null;//显示层
    //关闭软键盘
    var closeKeyBoard = function(){
        bindElement.removeAttribute("showKeyBoard");
        document.body.removeChild(keyBoardDiv);
        bindElement.focus();
        var tRange = bindElement.createTextRange();
        tRange.moveStart("character", bindElement.value.length);
        tRange.moveEnd("character", 0);
        tRange.select();
        
    };
    var funKey = {
        //功能键事件
        funKeyHander: {
            //退格
            "backSpace": function(bindElement){
                return function(){
                    var value = bindElement.value;
                    if (typeof value === "string" && value.length > 0) {
                        bindElement.value = value.slice(0, value.length - 1);
                    }
                }
            }(bindElement),
            //转换大小写
            "capsLock": function(keyElements, capsLockOn){
                return function(e){
                    for (var i = 0; i < keyElements.length; i++) {
                        if (capsLockOn) {
                            keyElements[i].value = keyElements[i].value.toLowerCase();
                        }
                        else {
                            keyElements[i].value = keyElements[i].value.toUpperCase();
                        }
                    }
					
                    capsLockOn = !capsLockOn;
					this.value=capsLockOn?lang["kb_capsLockOff"]:lang["kb_capsLockOn"];
                }
            }(keyElements),
            //清空
            "clear": function(bindElement){
                return function(){
                    bindElement.value = "";
                }
            }(bindElement),
            //确定
            "enter": function(closeKeyBoard){
                return function(){
                    closeKeyBoard(true);
                }
            }(closeKeyBoard)
        }
    };
    /**
     * 创建键位按钮
     * @param {Object} value 值
     * @param {Object} handler 绑定的事件
     */
    var createKeyBtn = function(value, clickHandler,className){
        var btn = document.createElement("input");
        btn.type = "button";
        btn.value = value;
        btn.onclick = clickHandler;
        btn["className"]="keyBtn";
        btn.onmouseover = function(){
            this["className"]="keyBtnHover";
        };
        btn.onmouseout = function(){
            this["className"]="keyBtn";
        }
        
        keyElements.push(btn);
        return btn;
    };
    //获取输入键按钮绑定事件
    var getBtnHandler = function(bindElement){
        return function(e){
            bindElement.value += this.value;
        }
    };
    /**
     * 创建键盘
     */
    var createKeyBox = function(){
        var box = common.newControl({
            tag: "div",
            "className": "keyboard"
        });//容器层
        var boxTitleDiv = common.newControl({
            tag: "div",
            "className": "boxTitleDiv"
        });//标题层
        var boxTitleSpan = common.newControl({
            tag: "div",
            text: jkbConfig.title,
            "className": "boxTitleSpan"
        });//标题文字
        var boxTitleBtn = common.newControl(jkbConfig.funKeyBtn["closeKeyBoard"]);//标题按钮
        boxTitleBtn["className"]= "boxTitleBtn";
        var clearDiv = common.newControl({
            tag: "div",
            "className": "clear"
        });
        boxTitleBtn.onclick = function(){//标题按钮事件（退出使用键盘输入）
            closeKeyBoard(false);
        };
        boxTitleDiv.appendChild(boxTitleSpan);
        boxTitleDiv.appendChild(boxTitleBtn);
        boxTitleDiv.appendChild(clearDiv);
        box.appendChild(boxTitleDiv);
        
        //创建键位表格
        var keysTable = common.newControl(jkbConfig.tableTag.table);
        var keysTbody = common.newControl({
            tag: "tbody"
        });
        var keyData = jkbConfig.keys;//键位数据
        for (var row in keyData) {
            var tr = common.newControl({
                tag: "tr"
            });
            
            //生成行头部非随机键
            var pre = (keyData[row].pre || "").split("");
            for (var i = 0; i < pre.length; i++) {
                var td = common.newControl(jkbConfig.tableTag.td);
                td.appendChild(createKeyBtn(pre[i], getBtnHandler(bindElement)));
                tr.appendChild(td);
            }
            //生成行内随机键
            var randKeys = (keyData[row].randKey || "").split("");
            for (var i = randKeys.length - 1; i >= 0; i--) {
                var randNum = parseInt(Math.random() * i);
                //alert(randKeys+":"+randKeys[randNum]);
                var td = common.newControl(jkbConfig.tableTag.td);
                td.appendChild(createKeyBtn(randKeys[randNum], getBtnHandler(bindElement)));
                randKeys.splice(randNum, 1);
                tr.appendChild(td);
            }
            //生成行尾部非随机键
            var end = (keyData[row].end || "").split("");
            for (var i = 0; i < end.length; i++) {
                var td = common.newControl(jkbConfig.tableTag.td);
                td.appendChild(createKeyBtn(end[i], getBtnHandler(bindElement)));
                tr.appendChild(td);
            }
            //生成行内功能键
            var funKeyName = keyData[row].funKey;
            if (funKeyName) {
                var td = common.newControl(jkbConfig.funKeyBtn[funKeyName].td);
                td.setAttribute("valign", "middle");
                var btn = common.newControl(jkbConfig.funKeyBtn[funKeyName].btn);
                btn.setAttribute("valign", "middle");
                btn["className"]="funKeys_" + funKeyName;
                btn.onclick = funKey.funKeyHander[funKeyName];
                td.appendChild(btn);
                tr.appendChild(td);
            }
            keysTbody.appendChild(tr);
        }
        keysTable.appendChild(keysTbody);
        box.appendChild(keysTable);
        bindElement.setAttribute("showKeyBoard", true);//设置绑定输入框开启软键盘状态为True
        document.body.appendChild(box);//将软键盘加入页面Body
        endE(handle || bindElement, box);//使软键盘在触发控件后显示
        keyBoardDiv = box;
        common.conCanMove(box, boxTitleDiv);//使显示层可移动
        return box;
    };
    return createKeyBox();
    
};


/**
 * 功能方法
 * @param {Object} control
 * @param {Object} mHandle
 */
var common = {
    /**
     * 让控件可移动
     * @param {Object} control 可移动的控件
     * @param {Object} mHandle 移动触发单元
     */
    conCanMove: function(control, mHandle){
    
        control = typeof(control) == 'string' ? document.getElementById(control) : control;
        if (mHandle) {
            mHandle = typeof(mHandle) == 'string' ? document.getElementById(mHandle) : mHandle;
        }
        else {
            mHandle = control;
        }
        //mHandle.style.cursor = "move";
        control.style.position = "absolute";
        /**
         * 开始移动
         * @param {Object} e event
         */
        function startDrag(e){
            var e = e || window.event;
            document.onmousemove = onDrag;
            document.onmouseup = stopDrag;
            
            //储存当前坐标差值参数
            
            control.subX = control.offsetLeft - e.clientX;
            control.subY = control.offsetTop - e.clientY;
            
            return false;
        }
        /**
         * 移动中
         * @param {Object} e event
         */
        function onDrag(e){
            e = e || window.event;
            
            control.style.left = control.subX + e.clientX + 'px';
            control.style.top = control.subY + e.clientY + 'px';
            
            return false;
        }
        /**
         * 释放移动
         */
        function stopDrag(){
            document.onmousemove = null;
            document.onmouseup = null;
        }
        mHandle.onmousedown = startDrag;
        
    },
    /**
     * 绑定样式
     * @param {Object} control 要绑定到的控件
     * @param {Object} styleCFG 要绑定的样式对象
     */
    bindStyle: function(control, styleCFG, addAttribute){
        var styleItem;
        if (addAttribute) {
            var styleArray = [];
            for (styleItem in styleCFG) {
                styleArray.push(styleItem + ":" + styleCFG[styleItem]);
            }
            control.setAttribute("style", styleArray.join(";"));
        }
        else {
            for (styleItem in styleCFG) {
                control.style[styleItem] = styleCFG[styleItem];
            }
        }
        return control;
        
    },
    /** 
     * 创建一个HTML标签对象
     * @param {Object} args 标签属性
     * @param {String} args["tag"] 标签名(必需)
     * @param {String} bStyle 绑定的样式
     */
    newControl: function(args, bStyle){
        var nControl = null;
        if (args["tag"]) {
            var t = args["tag"];
            if (t === "button" || t === "text" || t === "checkbox" || t === "file" || t === "hidden" || t === "image" || t === "password" || t === "radio" || t === "reset" || t === "submit") {
            
                args["tag"] = "input";
                args["type"] = t;
                return this.newControl(args);
            }
            nControl = document.createElement(args["tag"]);
        }
        if (nControl) {
            for (var attr in args) {
                switch (attr) {
                    case "text":
                        if (document.all) {
                            nControl.innerText = args["text"];
                        }
                        else {
                            nControl.textContent = args["text"];
                        }
                        break;
                    case "value":
                        if (typeof nControl.value === "undefined") {
                            if (typeof nControl.innerText !== "undefined") 
                                nControl.innerText = args["value"];
                        }
                        else {
                            nControl.value = args["value"];
                        }
                        break;
                    default:
                        nControl[attr] = args[attr];
                        break;
                }
            }
            if (bStyle) {
                this.bindStyle(nControl, bStyle);
            }
        }
        return nControl;
    }
};
/**
 * 获取传入Element的宽度
 * @param {Object} e
 */
function eWidth(e){
    return e.offsetWidth || parseInt(e.style.width) || 1;
}

/**
 * 获取传入Element的高度
 * @param {Object} e
 */
function eHeight(e){
    return e.offsetHeight || parseInt(e.style.height) || 1;
}

/**
 * 将o放的位置调整到e的下面
 * @param {Object} e
 * @param {Object} o
 */
function endE(e, o){
    function getposition(obj){
        var r = new Array();
        r['x'] = obj.offsetLeft;
        r['y'] = obj.offsetTop;
        while (obj = obj.offsetParent) {
            r['x'] += obj.offsetLeft;
            r['y'] += obj.offsetTop;
        }
        return r;
    }
    if (o) {
        var point = getposition(e);
        o.style.top = (point.y + 20) + "px";
        o.style.left = point.x + "px";
        return o;
    }
    return getPosition();
}
