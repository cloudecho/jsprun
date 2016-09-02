/*
Offical Style for JspRun!(R)
URL: http://www.jsprun.net
(C) 2007-2011 JspRun Inc.
<style type="text/css">
*/

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Common Style ~~~~ */

* { word-wrap: break-word;}
body { {BGCODE}; text-align: center;}
body, td, input, textarea, select { color: {TABLETEXT}; font: {FONTSIZE}/1.6em {FONT}; }
button {color:{MENUCONTEXTCOLOR}; font: {FONTSIZE}/1.6em {FONT};}
body, ul, dl, dd, p, h1, h2, h3, h4, h5, h6, form, fieldset { margin: 0; padding: 0; }
h1, h2, h3, h4, h5, h6 { font-size: 1em; }
#menu li, .popupmenu_popup li, #announcement li, .portalbox li, .tabs li, .postmessage fieldset li, .side li, .formbox li, .notice li { list-style: none; }
a { color: {LINK}; text-decoration: none; }
	a:hover { text-decoration: underline; }
	a img { border: none; }
em, cite, strong, th { font-style: normal; font-weight: normal; }
table { empty-cells: show; border-collapse: collapse; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Layout & Block Style ~~~~ */

.wrap { width: {MAINTABLEWIDTH}; text-align: left; margin: 0 auto; }
.notice { font-size: {MSGFONTSIZE}; border: 1px solid {NOTICEBORDER}; background: {NOTICEBG} url({IMGDIR}/notice.gif) no-repeat 1em 0.7em; padding: 0.5em 1em 0.3em 3em; margin-bottom: {BOXSPACE}; color: {NOTICETEXT}; }
.message { margin: 3em 10em 5em !important; }
	.message h1 { line-height: 26px; border: 1px solid; {PORTALBOXBGCODE}; background-repeat: repeat-x; background-position: 0 0; border-color: {TABLEBG} {TABLEBG} {CATBORDER} {TABLEBG}; padding-left: 1em; margin-bottom: 1em; }
	.message p { margin: 1.5em 1em; }
	.message a { color: {HIGHLIGHTLINK}; }
#header { width: 100%; overflow: hidden; }
	#header h2 { float: left; padding: 5px 0; }
#footer { border-top: 1px solid {BORDERCOLOR}; background: {ALTBG2}; color: {INFOTEXT}; padding: 12px 0; }
	#footlinks { float: right; margin-top: -3px; text-align: right; }
	#footlinks p a{color:{INFOTEXT}}
	#footer img { float: left; margin: 0 10px 0 0; }
	#copyright, #debuginfo { font: {SMFONTSIZE}/1.5em {SMFONT}; }
		#copyright strong, #copyright em { font-weight: bold; }
			#copyright strong a { color: #FF9D25; }
			#copyright em { color: #FF9D25; }
			#debuginfo { color: {LIGHTTEXT}; }
	.scrolltop { cursor: pointer; }
#menu { height: 31px; border: 1px solid {CATBORDER}; {HEADERMENUBGCODE}; background-repeat: repeat-x; }
	#menu ul { float: right; padding: 4px 10px 0; border-right: 1px solid {TABLEBG}; }
		#menu li { float: left; }
			#menu li a { text-decoration: none; float: left; color: {HEADERMENUTEXT}; padding: 4px 8px 3px; background: url({IMGDIR}/menu_itemline.gif) no-repeat 0 6px; }
				#menu li.hover, #menu li.current { background-color: {BGCOLOR}; border: 1px solid; border-color: {CATBORDER} {CATBORDER} {BGCOLOR}; }
					#menu li.current { font-weight: bold; }
					#menu li.hover a { padding: 3px 7px; background-image: none; }
					#menu li.current a { padding: 4px 7px 3px; background-image: none; }
			#menu cite a { font-weight: bold; background-image: none; }
	.frameswitch { float: left; height: 30px; line-height: 30px; padding-left: 10px; border-left: 1px solid {TABLEBG}; }
		#menu a.frameoff, #menu a.frameon { float: left; border: none; padding-left: 16px; margin-left: 0; background: no-repeat 0 50%; color:{MENUCONTEXTCOLOR} }
			#menu a.frameoff { background-image: url({IMGDIR}/frame_off.gif); }
			#menu a.frameon { background-image: url({IMGDIR}/frame_on.gif); }
#foruminfo { width: 100%; overflow: hidden; margin: 10px 0; color: {INFOTEXT}; }
	#userinfo, #foruminfo #nav { float: left; padding-left: 5px; }
	#forumstats, #headsearch { float: right; text-align: right; padding-right: 5px; }
	#foruminfo p { margin: 0; }
		#foruminfo a{ color: {INFOTEXT}; }
		#foruminfo em { color: {INFOTEXT}; }
		#foruminfo cite { font-weight: bold; }
			#foruminfo strong a { font-weight: bold; color: {INFOTEXT}; }
	#nav { margin: 10px 5px; color:{INFOTEXT}}
		#foruminfo #nav { margin: 0; }
		#userinfo #nav { float: none; padding: 0; }
			#nav a { font-weight: bold; color: {INFOTEXT}; }
#ann { border-top: 1px dashed {CATBORDER}; line-height: 36px; height: 28px; overflow: hidden; }
	#ann dt { float: left; width: 5em; background: url({IMGDIR}/ann_icon.gif) no-repeat 0 50%; text-indent: 2em; font-weight: 700; }
		#ann dd { margin-left: 30px; }
			#ann li { white-space: nowrap; }
				#ann em { color: {TEXT}; font-size: 0.83em; }
		#annbody { height: 25px; overflow: hidden; padding-right: 16px; }
#announcement { border-top: 1px dashed {CATBORDER}; line-height: 36px; height: 36px; overflow: hidden; }
	#announcement div { border: 1px solid {BGCOLOR}; padding: 0 10px; line-height: 35px !important; height: 36px; overflow-y: hidden;}
		#announcement li { float: left; margin-right: 20px; padding-left: 10px; background: url({IMGDIR}/ann_icon.gif) no-repeat 0 50%; white-space: nowrap; }
			#announcement li em { font-size: 0.83em; margin-left: 5px; color: {TEXT}; }
.portalbox { width: 100%; background: {CATBGCODE}; margin-bottom: {BOXSPACE}; border-collapse: separate; }
	.portalbox td { padding: 10px; vertical-align: top; background: {ALTBG2}; background-repeat: repeat-x; background-position: 0 0;  border: 1px solid {TABLEBG}; }
		.portalbox h3 { margin: 0 0 5px; font-size: 1em; white-space: nowrap; }
		.portalbox strong { font-weight: bold; margin-top: 4px;}
		.portalbox em { color: {LIGHTTEXT}; }
			.portalbox em a { color: {LIGHTTEXT}; }
			.portalbox cite a { color: {HIGHLIGHTLINK}; }
		#supeitems li { float: left; height: 1.6em; overflow: hidden; }
		#hottags a { white-space: nowrap; margin-right: 0.5em; }
		#hottags h3 { clear:both; }
.headactions { float: right; line-height: 1em; padding: 10px 10px 0 0; }
	.headactions img { vertical-align: middle; cursor: pointer; padding: 0 5px; }
		.mainbox .headactions { color: {HEADERTEXT}; }
		.mainbox .headactions a, .mainbox .headactions span, .mainbox .headactions strong { background: url({IMGDIR}/headactions_line.gif) no-repeat 100% 50%; padding-right: 10px; margin-right: 8px; color: {HEADERTEXT}; }
			.mainbox .headactions strong { font-weight: bold; background-image: url({IMGDIR}/arrow_left.gif); }
.pages_btns { width: 100%; padding: 0 0 8px; overflow: hidden; }
	.postbtn, .replybtn { float: left; }
		.postbtn { margin-left: 10px; cursor: pointer; }
	.pages_btns .pages em { line-height: 26px; }
.pages, .pageback, .threadflow { float: right; border: 1px solid {CATBORDER}; background: {PBG}; height: 24px; line-height: 26px; color: {LIGHTTEXT}; overflow: hidden; }
	.pages a, .pages strong, .pages em, .pages kbd, #multipage .pages em { float: left; padding: 0 8px; line-height:26px; }
		.pages a:hover { background-color: {BGCOLOR}; }
		.pages strong { font-weight: bold; color: {NOTICETEXT}; background: {BGBORDER}; }
			.pages a.prev, .pages a.next { line-height: 24px; font-family: Verdana, Arial, Helvetica, sans-serif; }
				.pages a.next { padding: 0 15px; }
		.pages kbd { border-left: 1px solid {CATBORDER}; margin: 0; }
			* html .pages kbd { padding: 1px 8px; }
			.pages kbd input { border: 1px solid {CATBORDER}; margin-top: 3px !important; * > margin-top: 1px  !important; margin: 1px 4px 0 3px; padding: 0 2px; height: 17px; }
				.pages kbd>input { margin-bottom: 2px; }
	.threadflow { margin-right: 3px; padding: 0 10px; }
	.pageback { margin-right: 1px; padding: 0 10px; }
	.pageback a { border-color: {COMMONBORDER}; background-color: {WRAPBG}; color: {HIGHLIGHTLINK}; }
	.pageback a:hover { text-decoration: none; }
	.pageback a { padding-left: 16px; background:  url({IMGDIR}/arrow_left.gif) no-repeat 0 50%; }
	.pageback a { border-color: {HIGHLIGHTLINK}; }
.editor_tb { margin: 5px 0 0; width: 600px; height: 26px; border: 1px solid; border-color: {INPUTBORDERDARKCOLOR} {INPUTBORDER} {INPUTBORDER} {INPUTBORDERDARKCOLOR}; background: {COMMONBG}; border-bottom: none; }
		.editor_tb .right a { color: {HIGHLIGHTLINK}; }
		.editor_tb .right { padding-right: 10px; line-height: 26px; }
		.editor_tb div a { float: left; margin: 5px 3px 0; width: 16px; height: 16px; background: url(../../images/common/editor.gif) no-repeat; text-indent: -9999px; line-height: 16px;  overflow: hidden; }
			.editor_tb div a.tb_bold { background-position: -3px -1px; }
			.editor_tb div a.tb_color { background-position: -3px -81px; }
			.editor_tb div a.tb_img { background-position: -3px -161px; }
			.editor_tb div a.tb_link { background-position: -3px -121px; }
			.editor_tb div a.tb_quote { background-position: -3px -441px; }
			.editor_tb div a.tb_code { background-position: -3px -461px; }
			.editor_tb div a.tb_smilies { background-position: -2px -102px; }
			.editor_tb div a.tb_colora { background-position: -2px -744px; }
		.editor_tb .popupmenu_popup td { padding: 0 !important; height: 14px; width: 14px; }
			.editor_tb .popupmenu_popup td div { height: 10px; width: 10px; cursor: pointer; }
			.editor_tb .tb_color input { margin: 2px; padding: 0px; float:left; cursor: pointer; width: 10px; height: 10px; border: 0; }
.tabs { padding-bottom: 26px; margin-bottom: 15px; background: {ALTBG2}; border-top: 1px solid {TABLEBG}; border-bottom: 1px solid {CATBORDER}; }
	.tabs li { float: left; line-height: 25px; border-right: 1px solid {BGBORDER}; }
		.tabs li.current { background: {BGCOLOR}; height: 27px; font-weight: bold; }
			.tabs li a { float: left; padding: 0 10px; }
	.headertabs { background: {BGCOLOR} none; margin-bottom: 0; }
		.headertabs li.current { background-color: {CATCOLOR}; }
	.sendpm a { color: {NOTICETEXT}; background: url({IMGDIR}/buddy_sendpm.gif) no-repeat 15px 50%; padding: 0 20px 0 35px !important; }
#headfilter { border: solid {BORDERCOLOR}; border-width: 1px 1px 0; }
	#headfilter .tabs { border-bottom-color: {BGCOLOR}; margin-bottom: 0; }
#footfilter { padding: 10px; he\ight: 44px; height: 24px; line-height: 24px; background: {COMMONBOXBG}; border-color: {COMMONBOXBORDER}; font-family: Simsun, "Times New Roman"; }
	#footfilter form { float: right; }
		#footfilter * { vertical-align: middle; }
.legend { border: 1px solid {BGBORDER}; background: {OBG}; padding: 10px; margin: 10px auto; width: 500px; text-align: center; line-height: 35px; }
	.legend label { padding: 0 20px; }
	.legend img { vertical-align: middle; margin-right: 10px; }
.avatarlist { overflow: hidden; padding: 5px 0; }
	* html .avatarlist { height: 1%; }
	.avatarlist dl { float: left; width: 70px; border: 1px solid {COMMONBOXBORDER}; padding: 5px; margin-right: 5px; text-align: center; }
		.avatarlist dt { width: 70px; height: 70px; }
		.avatarlist dl img { width: 64px; height: 64px; }
	.avatarlist dd { height: 22px; line-height: 22px; overflow: hidden; }
.taglist { width: 100%; padding: 10px 0; overflow: hidden; }
	.taglist li { float: left; display: inline; width: 10em; height: 24px; overflow: hidden; margin: 0 10px; }
		.taglist li em { font-size: 10px; color: {LIGHTTEXT}; }
.attriblist * { color: {TABLETEXT}; }
	.attriblist dt { float: left; margin-right: 10px; }
	.attriblist .name { font-weight: bold; }
	.attriblist dd a { color: {HIGHLIGHTLINK};}
#forumlinks {}
	#forumlinks td { padding: 5px 5px 5px 55px; background: url({IMGDIR}/link.gif) no-repeat 18px 50%; color: {LIGHTTEXT}; }
	#forumlinks .forumlink_logo { float: right; }
#online {}
	#online h4 { font-weight: normal; color: {TEXT}; }
		#online h4 strong { font-weight: bold; }
	#online dl { padding: 5px 5px 5px 55px; }
		#onlinelist { background: url({IMGDIR}/online.gif) no-repeat 10px 10px; border-top: 1px solid {COMMONBOXBORDER}; }
		#online dt { padding: 5px; }
			#online dt img { margin-bottom: -3px; }
		#online dd { border-top: 1px solid {COMMONBOXBORDER}; }
	#bdayslist { padding: 10px 0 10px 55px; border-top: 1px solid {COMMONBOXBORDER}; background: url({IMGDIR}/bdays_cake.gif) no-repeat 10px 5px; }
.userlist { overflow: hidden; padding: 5px 5px 0; }
	* html .userlist { height: 1%; }
	.userlist li { float: left; width:128px; height: 20px; overflow: hidden; }
		.userlist li img { vertical-align: middle; }
	#onlinelist .userlist li { height: auto; margin:4px auto ;}
#recommendlist { }
	#recommendlist li { float: left; white-space: nowrap; width: 24.9%; overflow: hidden; text-indent: 12px; background: url({IMGDIR}/arrow_right.gif) no-repeat 2px 7px; }
	#recommendlist.rules li { width: 49%; }
.recommendrules { padding: 0px;}
#seccode { cursor: pointer; }
.autosave { behavior: url(#default#userdata); }
#menu a.notabs { background: none; }
.headactions a.notabs { background: none; margin-right: 0px; padding-right: 0px; }
.absmiddle { vertical-align: middle; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Box Style ~~~~ */

/*Main Box*/
.mainbox { background: {TABLEBG}; border: {BORDERWIDTH} solid {BORDERCOLOR}; padding: {TABLESPACE}; margin-bottom: {BOXSPACE}; }
	.mainbox h1, .mainbox h3, .mainbox h6 { line-height: 31px; padding-left: 1em; {HEADERBGCODE}; background-repeat: repeat-x; background-position: 0 0; color: {HEADERTEXT}; }
		.mainbox h1 a, .mainbox h3 a { color: {HEADERTEXT}; }
	.mainbox table { width: 100%; }
		.forumlist table, .threadlist table { border-collapse: separate; }
		.mainbox thead th, .mainbox thead td, .mainbox thead td a { background: {CATCOLOR}; padding: 2px 5px; line-height: 22px; color: {HEADERTEXT}; }
			.mainbox thead.category th, .mainbox thead.category td { {CATBGCODE}; color:{HEADERTEXT}; }
			.mainbox thead.separation th, .mainbox thead.separation td { border-top: 1px solid {BGBORDER}; color:{HEADERTEXT}; }
		.mainbox tbody th, .mainbox tbody td { border-top: 1px solid {BGBORDER}; padding: 5px; }
			.mainbox tbody cite, .mainbox tbody em { line-height: 1.3em; }
				.forumlist tbody strong, .threadlist tbody strong , .formbox tbody strong  { color: {NOTICETEXT}; }
	/*Tabel Footer: Button Operation*/
	.footoperation { background: {CATCOLOR}; padding: 5px; border-top: 1px solid {CATBORDER}; }
		.threadlist .footoperation { padding-left: 61px; }
		.footoperation * { vertical-align: middle; }
		.footoperation label { margin-right: 1em; cursor: pointer; }
		.footoperation button { line-height: 1em; display: inline; width: 0; overflow: visible; padding: 3px 5px 2px; border: 1px solid {BGBORDER}; background: {TABLEBG}; color: {TABLETEXT}; cursor: pointer; margin-left: 2px; }
			.footoperation>button { width: auto; }
	/*Forum & Thread List*/
	thead.category .caption { text-indent: 6em; }
	.forumlist tbody th, .forumlist tbody td, .threadlist tbody th, .threadlist tbody td { color: {TEXT}; padding: 1px 5px; border-bottom: 1px solid {TABLEBG}; background-color: {ALTBG1}; }
		.forumlist tbody th { height: 40px; }
		.forumlist th, .threadlist th { text-align: left; }
				.forumlist th { padding-left: 55px !important; }
					.forumlist h2 em { color: {HIGHLIGHTLINK}; }
				.forumlist tbody th { background-image: url({IMGDIR}/forum.gif); background-repeat: no-repeat; background-position: 13px 50%; }
					.forumlist tbody th.new { background-image: url({IMGDIR}/forum_new.gif); }
					.moderators a { color: {HIGHLIGHTLINK}; }
					.moderators a strong { font-weight: bold; color: {HIGHLIGHTLINK}; }
				.threadlist th label { float: right; }
				.threadpages { background: url({IMGDIR}/multipage.gif) no-repeat 0 100%; font-size: 11px; margin-left: 5px; white-space: nowrap; }
					.threadpages a { padding-left: 8px; }
						.threadpages a:hover { text-decoration: underline; }
				.threadlist th a.new { color: {NOTICETEXT}; text-transform: uppercase; font-size: 9px; white-space: nowrap; }
				.threadlist th input { float: left; margin-right: 5px; }
				.threadlist th em, .threadlist th em a { color: {LIGHTTEXT}; }
				.threadlist th img.icon { float: left; margin-left: -22px; }
				.threadlist th img.attach, .threadlist th img.icon { margin-bottom: -3px; }
				.threadlist .target { float: left; display: block; width: 20px; height: 20px; margin-left: -28px; text-indent: -9999px; overflow: hidden;  }
					* html .threadlist .target  { margin-left: -14px; margin-right: 5px; }
	.forumlist tbody tr:hover th, .forumlist tbody tr:hover td, .threadlist tbody tr:hover th, .threadlist tbody tr:hover td { background-color: {ALTBG2}; }
		.forumlist td.lastpost { width: 260px; }
		.threadlist td.folder { text-align: center; width: 30px; }
		.threadlist td.icon { text-align: center; padding: 3px 0; width: 16px; }
		.threadlist td.author { width: 120px; }
		.threadlist td.lastpost { text-align: right; width: 120px; padding-right: 15px; }
			.threadlist td.lastpost cite a { color: {LIGHTTEXT}; }
		.forumlist cite, .threadlist cite { display: block; }
		.threadlist td.author em, .threadlist td.lastpost em { font-size: {SMFONTSIZE}; }
		label.highlight { width: 40px; margin-right: 30px; }
		label.highlight, label.highlight input { float: left; }
		label.highlight em { float: right; width: 16px; height:16px;  }
		.quickmanage label.highlight { width: 40px; margin: 3px 18px 3px 0; }

		#updatecircles th { background-image: none; }
			#updatecircles .circlelogo { float: left; margin-left: -40px; margin-top: 10px;  max-height: 32px; max-width: 32px; width: expression(this.width > 32 && this.height < this.width ? 32: true); height: expression(this.height > 32 ? 32: true); }

	/*Viewthread*/
	.viewthread { padding-bottom: 1px; }
		.viewthread table, #pmprompt table, #forumlinks, #pmlist, #specialpost, #newpost, #editpost { table-layout: fixed; }
		.viewthread ins, .mainbox ins { display: block; text-align: center; text-decoration: none; margin-bottom: 1px; background: {COMMONBOXBG}; border-bottom: 1px solid {CATBORDER}; line-height: 26px; }
			ins.logininfo { background: {CATCOLOR}; padding: 2px 5px; line-height: 22px; color: {TEXT}; text-align: left; border: none; }
			.viewthread ins, .viewthread ins a { color: {NOTICETEXT}; }
			.viewthread td.postcontent, .viewthread td.postauthor { vertical-align: top; padding: 0 1px; border: none; overflow: hidden; background: {ALTBG2}; }
			.postinfo { color: {TEXT}; border-bottom: 1px solid {BGBORDER}; padding: 0 5px; line-height: 26px; height: 26px; overflow: hidden; }
				.postinfo strong, .postinfo em { float: right; line-height: 26px !important; cursor: pointer; padding: 0 3px; color: {HIGHLIGHTLINK}; }
					.postinfo strong { margin-left: 5px; color: {NOTICETEXT}; font-weight: bold; }
						* html .postinfo strong { margin-top: -2px; }
						.postinfo strong sup { font-weight: normal; margin-left: 1px; color: {LIGHTTEXT}; }
				.postinfo a { color: {HIGHLIGHTLINK}; }
				.postinfo label { color:{NOTICETEXT}; cursor: pointer; }
			.postmessage { padding: 10px; overflow-x: hidden; }
				.postmessage *, .pmmessage *, .register *  { line-height: normal; }
			.defaultpost {  height: auto !important; height:{POSTMINHEIGHT}px; min-height:{POSTMINHEIGHT}px !important; }
				.postmessage h2 {font-size: 1.17em; margin-bottom: 0.5em; }
				.t_msgfont, .t_msgfont td { font-size: {MSGFONTSIZE}; line-height: 1.6em; }
				.t_smallfont, .t_smallfont td { font-size: {MSGSMALLSIZE}; line-height: 1.6em; }
				.t_bigfont, .t_bigfont td { font-size: {MSGBIGSIZE}; line-height: 1.6em; }
					.t_msgfont *, .t_smallfont *, .t_bigfont * { line-height: normal; }
					.t_msgfont a, .t_smallfont a, .t_bigfont a { color: {LINK}; }
				.postratings { float: right; }
			.signatures { overflow: hidden; height: expression(signature(this)); max-height: {MAXSIGROWS}px; background: url({IMGDIR}/sigline.gif) no-repeat 0 0; margin: 10px; padding-top: 20px; color: {TEXT}; line-height: 1.6em; }
				.signatures * { line-height: normal; }
				.signatures strong { font-weight: bold; }
			.postactions { border-top: 1px solid {COMMONBOXBORDER}; background: {ALTBG1}; line-height: 30px; height: 30px; padding: 0 10px; }
				.postactions strong { cursor: pointer; }
				.postactions input { float: right; margin: 5px 0 0 5px; }
				.postactions p { float: right; }
			.postmessage .box { border-width: 0; margin: 5px 0; }
			.postmessage .typeoption { width: 500px; }
			 	.typeoption tbody th { width: 100px; }
				.typeoption tbody td, .typeoption tbody th { border-top: 0px; border-bottom: 1px dashed {COMMONBOXBORDER}; }
				.postmessage .box tbody th, .postmessage .box tbody td { border-top-color: {COMMONBOXBORDER}; }
		.postmessage fieldset { font-size: 12px; width: 500px; padding: 10px; border: 1px solid {BGBORDER}; margin-top: 2em; }
			.postmessage fieldset li { color: {LIGHTTEXT}; line-height: 1.6em; }
			.postmessage fieldset li cite, .postmessage fieldset li em { margin: auto 10px; }
		.t_msgfont li, .t_bigfont li, .t_smallfont li, .faq li { margin-left: 2em; }
		.postattach { width: 500px; margin: 10px 0; }
		.postattachlist { width: auto; font-size: 12px; margin-top: 2em; }
		.t_attach { border: 1px solid {COMMONBOXBORDER}; background: {TABLEBG}; font-size: 12px; padding: 5px; }
			.t_attach em { color: {LIGHTTEXT}; }
		.t_attachlist { border-bottom: 1px dashed {COMMONBOXBORDER}; padding: 5px 0; }
			.t_attachlist dt { font-weight: bold; }
				.t_attachlist dt img { margin-bottom: -4px; }
			.t_attachlist dd { padding-left: 20px; color: {LIGHTTEXT}; }
		.t_attachinsert { margin: 1em 0; font-size: 12px; }
			.t_attachinsert p img { margin-bottom: -4px; }
		.t_table { border: 1px solid {CATBORDER}; empty-cells: show; border-collapse: collapse; }
			.t_table td { padding: 4px; border: 1px solid {CATBORDER}; overflow: hidden; }
		/* JspRun! Code */
		/*CODE & Quote*/
		.blockcode, .quote { font-size: 12px; margin: 10px 20px; border: solid {CATBORDER}; border-width: 4px 1px 1px; overflow: hidden; }
			.blockcode h5, .quote h5 { border: 1px solid; border-color: {TABLEBG} {TABLEBG} {CATBORDER} {TABLEBG}; line-height: 26px; padding-left: 5px; color: {TEXT}; }
				.blockcode code, .quote blockquote { margin: 1em 1em 1em 3em; line-height: 1.6em; }
					.blockcode code { font: 14px/1.4em "Courier New", Courier, monospace; display: block; padding: 5px; }
					.blockcode .headactions { color: {TEXT}; font-size: {MSGSMALLSIZE}; cursor: pointer; padding-top: 5px; }
		p.posttags { margin: 2em 0em 0.5em 0em; }
			p.posttags a, .footoperation span.posttags a { color: #F00; font-weight: bold; }
					p.posttags .postkeywords a, { color: {NOTICETEXT}; }
		.postmessage strong { font-weight: bold; }
		.postmessage em { color:{TEXT}; }
		.postmessage span.t_tag { cursor: pointer; border-bottom: 1px solid #F00; white-space: nowrap; }
		.mainbox td.postauthor { width: 180px; {BGCODE}; padding: 5px; overflow: hidden; }
			.postauthor cite { font-weight: bold; display: block; border-bottom: 1px solid {BGBORDER}; height: 21px; overflow: hidden; margin-bottom: 5px; }
				.postauthor cite label a { float: right; padding: 3px; }
			div.avatar { margin: 5px; text-align: center; width: 160px; overflow: hidden }
			.postauthor dt { float: left; margin-right: 0.5em; color: {TEXT}; }
			.postauthor dd, .postauthor dt { height: 1.6em; line-height: 1.6em; }
			.postauthor dd { overflow: hidden; }
			.postauthor p { margin: 0 10px; }
				.postauthor p.customstatus { color: {TEXT} }
				.postauthor p em, .postauthor dt em { color: {NOTICETEXT}; }
			.postauthor ul { margin: 5px 10px; line-height: 1.6em; overflow: hidden; }
				.postauthor li { text-indent: 22px; width: 49.5%; height: 1.6em; overflow: hidden; float: left; background-position: 0 50%; background-repeat: no-repeat; }
					.postauthor li.pm { background-image: url({IMGDIR}/buddy_sendpm.gif); }
					.postauthor li.buddy { background-image: url({IMGDIR}/user_add.gif); }
					.postauthor li.space { background-image: url({IMGDIR}/forumlink.gif); }
					.postauthor li.online { background-image: url({IMGDIR}/user_online.gif); color: {NOTICETEXT}; }
					.postauthor li.offline { color: {TEXT}; background-image: url({IMGDIR}/user_offline.gif); }
					.postauthor li.magic { background-image: url({IMGDIR}/magic.gif);}
			.postauthor dl.profile, .postauthor div.bio { margin: 5px 10px; padding-top: 5px; }
/* NEW CODE */
.blockcodenew { margin: 10px 0; padding: 10px 10px 10px 65px; }
.blockcodenew { padding: 10px 0 5px 10px; width: 598px; border: 1px solid #CCC; background: #F7F7F7 url({IMGDIR}/codebg.gif) repeat-y 0 0; overflow: hidden; }
	.blockcodenew ol { margin: 0 0 0 10px; padding: 0; }
		.blockcodenew ol li { padding-left: 10px; list-style-type: decimal-leading-zero; font-family: Monaco,Consolas,"Lucida Console","Courier New",serif; font-size: 12px; line-height: 1.8em; }
			.blockcodenew ol li:hover { background: #F7F7F7; color: {HIGHLIGHTLINK}; }
			.blockcodenew ol li { font-family: "Courier New",serif; }
	.blockcodenew em { color: #09C !important; font-size: 12px;cursor: pointer;}
/*---END------*/
/*Common Box*/
.box { background: {OBG}; border: {BORDERWIDTH} solid {CATBORDER}; padding: {TABLESPACE}; margin-bottom: {BOXSPACE}; }
	.box h4 { {PORTALBOXBGCODE}; background-repeat: repeat-x; background-position: 0 0; line-height: 30px; padding: 0 10px;}
	.box table { width: 100%; }
		.box td { border-top: 1px solid {COMMONBOXBORDER}; }
		.box .box li { list-style: none;}
	.postattachlist h4, .tradethumblist h4, .pollpanel h4, .activitythread h4, .typeoption h4 { border-top: 1px solid {BGBORDER}; }
	#pmprompt { border-color: {NOTICEBORDER}; }
		#pmprompt h4 { background: {NOTICEBG}; border-top: none; }
		#pmprompt th, #pmprompt td { border-top-color: {NOTICEBORDER}; }
/*List*/
td.user { width: 120px; }
td.nums { width: 80px; text-align: center; }
td.time { width: 120px; }
td.selector { width: 20px; text-align: center; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Spacial Threads ~~~~ */

.specialthread h1 { background: {TABLEBG}; font-size: 1.5em; color: {TABLETEXT}; padding: 10px 5px; border-bottom: 1px solid {COMMONBOXBORDER}; }
	.specialthread h1 a { color: {HIGHLIGHTLINK}; }
.specialthread .postcontent label { float: right; display: inline; margin: 12px 12px 0; border: 1px solid {CATBORDER}; padding: 3px 5px; background: {CATCOLOR} no-repeat 3px 50%; }
	.specialthread .postcontent label strong { color: {NOTICETEXT}; }
	.specialthread .postcontent label a { color: {HIGHLIGHTLINK}; }
.specialthread .postauthor { width: 180px; }
.specialpostcontainer { padding: 0 1px; }
.specialpost { border-bottom: 4px solid {ALTBG1}; text-align: right; }
	.specialpost .postinfo h2  { float: left; font-weight: normal; padding-left: 8px; font-size: {SMFONTSIZE}; }
	.specialpost .postinfo h2 em {}
		.specialpost .postinfo { border-color: {ALTBG2}; height: 1.8em; }
		.specialpost strong { border: none; }
		.specialpost .postinfo h2 a { font-size: 12px; }
	.specialpost .postmessage { text-align: left; min-height: 30px; border-bottom: 1px solid {CATBORDER}; }
		* html .specialpost .postmessage { height: 30px; overflow: visible; }
		p.imicons { margin: 8px auto; width: 160px;}
/* [ Poll ] */
.pollpanel { margin: 1em 0; border-width: 1px 0 0; }
	.pollpanel h4 span { float: left;line-height:30px;}
	.pollpanel h4 a { float: right;line-height:30px; font-weight:normal; }
	.pollpanel tbody td { vertical-align: middle; }
		.pollpanel tbody td a { color: {HIGHLIGHTLINK} }
		.optionbar { float: left; margin-right: 0.5em; border: 1px solid {BGBORDER}; {HEADERBGCODE}; background-repeat: repeat-x; background-position: 0 100%; height: 12px; }
			.optionbar div { float: left; border: 1px solid {TABLEBG}; height: 10px; overflow: hidden; }
/* [ Reward ] */
.rewardthread .postcontent label { background-image: url({IMGDIR}/rewardsmallend.gif); padding-left: 25px; }
	.rewardthread .postcontent label.unsolved { background-image: url({IMGDIR}/rewardsmall.gif); float:right; }
	#bestpost { padding-top: 10px; margin-top: 10px; border-top: 1px solid {CATBORDER}; }
/* [ Activity ] */
.activitythread .box th { width: 7em; }
	#activityjoin label { float: none; border: none; background: transparent; padding: 0; margin: 0; }
/* [ Trade ] */
.tradethread .postmessage { min-height: 160px; }
	* html .tradethread .postmessage { height: 360px !important; }
	.tradethread .postauthor dt, .tradethread .postauthor dd { height: 20px; overflow: hidden; }
*>.tradeinfo { overflow: hidden; }
	* html .tradeinfo { height: 1%; }
	.tradeinfo h1 { background: {ALTBG2}; font-size: 1.5em; color: {TABLETEXT}; padding: 10px 5px; border-bottom: 1px solid {COMMONBOXBORDER}; margin-bottom: 1em; }
	.tradethumb, .tradeattribute { float: left; }
		.tradethumb { width: 260px; text-align: center;}
		.tradeattribute { padding-left: 1em; }
		.tradeattribute img { vertical-align: middle; }
			.tradeattribute dl { overflow: hidden; padding-bottom:2em !important;  }
				.tradeattribute dt { float: left; width: 5em; padding: 0.5em; line-height: 2em; clear: left; }
				.tradeattribute dd { border-bottom: 1px dotted {COMMONBOXBORDER}; padding: 0.5em 1.5em; line-height: 2em; }
					.tradeattribute em, .tradeattribute del { color: {LIGHTTEXT}; }
					.tradeattribute strong { font-size: 1.6em; font-weight: bold; color: #F00; }
	.sellerinfo { float: right; display: inline; margin-right: 1em; width: 180px; }
		.sellerinfo h4 { border-bottom: 1px dotted {COMMONBOXBORDER}; }
		.sellerinfo dl { margin: 1em; }
	.tradeinfo .postinfo { clear: both; }
	.tradeinfo .postmessage { min-height: 100px; }
		* html .tradethread .postmessage { height: 100px; }

	*>.tradethumblist { overflow: hidden; }
		* html .tradethumblist { height: 1%; }
		.tradethumblist dl { float: left; text-align: center; padding: 10px; width: 170px; height: 220px; w\idth: 150px; he\ight: 200px; }
			.tradethumblist dd.thumblist { height: 100px; overflow: hidden; }
				.tradethumblist dd img { vertical-align: middle; cursor: pointer; }
			.tradethumblist dl p { height: 1.6em; overflow: hidden; }
			.tradethumblist p.tradename { height: 45px; line-height: 18px; margin-top: 5px; }
			.tradethumblist del { color: {LIGHTTEXT}; }
			.tradethumblist strong { font-weight: bold; color: #F00; }
	 #ajaxtradelist .price { text-align: right; }
	 	#ajaxtradelist strong { font-weight: bold; color: #F00; }
	 	#ajaxtradelist .popupmenu_popup { white-space: nowrap;overflow: visible; }
	 	#ajaxtradelist .popupmenu_popup a { color: {HIGHLIGHTLINK}; }
/* [ Debate ] */
.debatethread .postmessage { min-height: inherit; height: auto; }
.debatethread .box { margin: 0;}
.debatethread .debatepoints { border-width: 1px 0 0; padding-bottom: 0; border-top: none; margin-bottom: 10px; background:none; }
	.debatepoints tbody td { border: 0px; width: 50%; border-bottom: none; vertical-align: top; }
		.debatepoints .message td.stand1 { border:1px solid {NOTICEBORDER}; background: {NOTICEBG}; border-bottom: none; }
		.debatepoints .message td.stand2 { border:1px solid {BGBORDER}; background: {ALTBG1}; border-bottom: none; }
		.debatepoints .button td.stand1 { border:1px solid {NOTICEBORDER}; background: {NOTICEBG}; border-top: none; }
		.debatepoints .button td.stand2 { border:1px solid {BGBORDER}; background: {ALTBG1}; border-top: none; }
	.debatepoints h2 { padding-left: 40px; line-height: 2em; background-position: 5px 1px; background-repeat: no-repeat; }
		.stand1 h2 { }
		.stand2 h2 { }
		.poststand0, .poststand1, .poststand2 { font-size: 1.17em; text-align: center; display: block; float: left; border: 1px solid {COMMONBOXBORDER}; background: {COMMONBOXBG}; color: {LIGHTTEXT}; width: 40px; height: 22px; line-height: 22px; margin-right: 12px; }
		.poststand1 { border: 1px solid {NOTICEBORDER}; background: {NOTICEBG}; color: {NOTICETEXT}; }
		.poststand2 { border: 1px solid {BGBORDER}; background: {ALTBG1}; color: {HIGHLIGHTLINK}; }
	.debatepoints p { padding: 0 10px; overflow: hidden; }
	.debatepoints a { margin: 0 auto; display: block; width: 80px; text-align: center; border: 1px solid; padding: 0.3em 1em; }
		.debatepoints #affirmbutton { border-color: {NOTICEBORDER}; background: {NOTICEBG}; color: {NOTICETEXT}; }
		.debatepoints #negabutton { border-color: {BGBORDER}; background: {ALTBG1}; color: {HIGHLIGHTLINK}; }
.debatethread .optionbar div { float: none; }

.payinfo dt { float: left; width: 10em; padding: 0.5em; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Control Panel ~~~~ */
.container { width: 100%; overflow: hidden; }
	.content { float: right; width: 80%; }
		.content .mainbox { padding-bottom: 5px; }
		.content .footoperation, .content .mainbox thead th, .content .mainbox thead td,
		.content .mainbox tbody th, .content .mainbox tbody td { border-top: none; border-bottom: 1px solid {BGBORDER}; }
	.side { float: left; width: 18%;}
		.side div { border: 1px solid {CATBORDER};{PORTALBOXBGCODE}; background-repeat: repeat-x; background-position: 0 0; margin-bottom: {BOXSPACE}; }
			.side h2 { padding-left: 10px; line-height: 2.4em; font-size: 1.17em; border: 1px solid; border-color: {TABLEBG} {TABLEBG} {COMMONBOXBORDER} {TABLEBG};}
			.side ul { padding: 1px; }
				.side li{ text-indent: 26px; line-height: 2.4em; }
					.side h3 { font-weight: normal; background:url({IMGDIR}/arrow_right.gif) no-repeat 14px 46%; }
						.side_on h3 { font-weight: bold; border: solid {BGBORDER}; border-width: 1px 0; background: {ALTBG2} url({IMGDIR}/arrow_down.gif) no-repeat 14px 46%; }
					.side li ul { border-bottom: 1px solid {COMMONBOXBORDER}; }
						.side li li { padding-left: 1em;}
				.side li.current { font-weight: bold; }
					.side li.current a { color: {TEXT}; }
			.side li.first h3 { border-top: none; }
			.side li.last ul { border-bottom: none; }
	#memberinfo { }
		#memberinfo .memberinfo_avatar { text-align: center; width: 170px; font-weight: bold; }
			#memberinfo li label { color: {TEXT}; margin-right: 0.5em; }
			#memberinfo .memberinfo_forum label { float: left; width: 8em; text-align: right; }
	.mysearch { float: left; display: block; margin-top: -10px; margin-left: 10px;}
		/* Message Tabs*/
		.msgtabs { border-bottom: 1px solid {CATBORDER}; padding-bottom: 23px; padding-right: 5px; margin-top: 0.8em;}
			.msgtabs strong { float: right; padding: 0 12px; border: 1px solid {CATBORDER}; border-bottom: 1px solid {CATCOLOR};  margin-right: 5px; text-decoration: none; height: 22px; line-height: 22px; font-weight: bold; background: {CATCOLOR}; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DropMenu ~~~~ */

.dropmenu { padding-right: 15px !important; background-image: url({IMGDIR}/arrow_down.gif) !important; background-position: 100% 50% !important; background-repeat: no-repeat !important; cursor: pointer; }
.popupmenu_popup { text-align: left; line-height: 1.4em; padding: 10px; overflow: hidden; border: 1px solid {COMMONBOXBORDER}; background-color:{COMMONBOXBG}; background-position:bottom;}
.headermenu_popup { width: 170px; }
	.headermenu_popup li { float: left; width: 7em; line-height: 24px; height: 24px; overflow: hidden; white-space:nowrap; border-bottom: 1px solid {COMMONBOXBORDER}; }
.newspecialmenu { width: 100px; }
	.newspecialmenu li { background: url({IMGDIR}/folder_s.gif) no-repeat 3px 50%; float: left; }
		.newspecialmenu li.poll { background-image: url({IMGDIR}/pollsmall_m.gif); }
		.newspecialmenu li.trade { background-image: url({IMGDIR}/tradesmall_m.gif); }
		.newspecialmenu li.reward { background-image: url({IMGDIR}/rewardsmall.gif); }
		.newspecialmenu li.activity { background-image: url({IMGDIR}/activitysmall.gif); }
		.newspecialmenu li.debate { background-image: url({IMGDIR}/debatesmall_m.gif); }
		.newspecialmenu li.video { background-image: url({IMGDIR}/videosmall.gif); }
		.newspecialmenu a { float: left; width: 75px; border-bottom: 1px solid {COMMONBOXBORDER}; padding: 5px 0 5px 25px; }
			.newspecialmenu a:hover { text-decoration: none; color: {HIGHLIGHTLINK}; border-bottom-color: {CATBORDER}; }
#forumlist_menu { padding: 10px 30px 10px 20px; }
	#forumlist_menu dl { padding: 5px 0; }
		#forumlist_menu dt a { font-weight: bold; color: {HEADERMENUTEXT}; }
		#forumlist_menu dd { padding-left: 1em;}
			#forumlist_menu li.sub a { padding-left: 1em;}
			#forumlist_menu li.current a { font-weight: bold;}
			#forumlist_menu li a { font-weight: normal; color: {INFOTEXT}; }
.userinfopanel { border: 1px solid {CATBORDER}; width: 160px;background-color: #FFF; background-image: url({IMGDIR}/jpbg.gif); background-repeat: repeat-x; background-position: 0 0; padding: 10px;}
	.imicons { text-align: center; border: 1px solid {COMMONBOXBORDER}; background: {TABLEBG}; padding: 4px 1px; }
		.imicons img { vertical-align: middle; }
	.userinfopanel p { text-align: left; margin: 0; }
		.userinfopanel p a { color: {HIGHLIGHTLINK}; }
	.userinfopanel dl { border-bottom: 1px solid {COMMONBOXBORDER}; margin: 5px 0; padding: 5px 0; }
	.postauthor cite a { float: left; padding: 5px; border: solid {ALTBG1}; border-width: 1px 1px 0; height: 10px; overflow: hidden; }
		.postauthor cite a.hover { border-color: {CATBORDER}; background-color: {TABLEBG}; }
	.popupmenu_popup .postauthor { width: 180px; }
		.popupmenu_popup .postauthor a { color: {HIGHLIGHTLINK}; }
/*Popup Calendar*/
#calendar { border: 1px solid {BORDERCOLOR}; background: {ALTBG1}; margin-bottom: 0.8em;}
	#calendar td { padding: 2px; font-weight: bold;}
	#calendar_week td { height: 2em; line-height: 2em; border-bottom: 1px solid {CATBORDER};}
	#hourminute td {padding: 4px 2px; border-top: 1px solid {CATBORDER};}
		.calendar_expire, .calendar_expire a:link, .calendar_expire a:visited {	color: {TEXT}; font-weight: normal; }
		.calendar_default, .calendar_default a:link, .calendar_default a:visited { color: {HIGHLIGHTLINK};}
		.calendar_checked, .calendar_checked a:link, .calendar_checked a:visited { color: {NOTICETEXT}; font-weight: bold;}
		td.calendar_checked, span.calendar_checked{ background: {CATBORDER};}
		.calendar_today, .calendar_today a:link, .calendar_today a:visited { color: {TABLETEXT}; font-weight: bold; }
	#calendar_header td{ width: 30px; height: 20px; border-bottom: 1px solid {CATBORDER}; font-weight: normal; }
	#calendar_year { display: none;	line-height: 130%; background: {ALTBG1}; position: absolute; z-index: 10; }
		#calendar_year .col { float: left; background: {ALTBG1}; margin-left: 1px; border: 1px solid {CATBORDER}; padding: 4px; }
	#calendar_month { display: none; background: {ALTBG1}; line-height: 130%; border: 1px solid #DDD; padding: 4px; position: absolute; z-index: 11; }
#styleswitcher_menu { overflow: visible; }
	#styleswitcher_menu, #styleswitcher_menu ul li, #styleswitcher_menu ul li.current a, #styleswitcher_menu ul li a { white-space: nowrap; padding:0; text-indent:10px;}
	#styleswitcher_menu ul li.current { font-weight: bold; }
		#styleswitcher_menu ul li.current a { color: {TEXT}; }
#styleswitcher_menu {}
	#styleswitcher_menu li.current { font-weight: bold; }
.tagthread { width: 360px; }
	.tagthread .close { float: right; padding-top: 5px; }
	.tagthread h4 { line-height: 26px; border-bottom: 1px solid {COMMONBOXBORDER}; }
	.tagthread ul { padding: 5px; }
		.tagthread li { line-height: 1.8em; }
	.tagthread li.more { text-align: right; background: url({IMGDIR}/arrow_right.gif) no-repeat 100% 50%; padding-right: 10px; }
.headactions .popupmenu_popup a, .headactions .popupmenu_popup strong { color: {TEXT}; background: none; white-space: nowrap; }
	.headactions .popupmenu_popup { overflow: visible; }

*+html #my_menu, *+html #memcp_menu, *+html #stats_menu, *+html #plugin_menu { margin-left: 1px; }
* html #my_menu, * html #memcp_menu, * html #stats_menu, * html #plugin_menu { margin-left: 1px; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Form Style ~~~~ */

fieldset { border: none; }
input, textarea { border-width: 1px; background: {TABLEBG}; border-color: {INPUTBORDER}; padding: 2px; }
	input[type="radio"], input[type="checkbox"] { border: none; background: none; }
	.radio, .checkbox{ border: none; background: none; }
	.invitecodelist input { border: none; font-family: "Courier New", Courier, monospace; font-size: 13px; cursor: pointer; }
button { border: 1px solid; border-color: {COMMONBOXBORDER} {LIGHTTEXT} {LIGHTTEXT} {COMMONBOXBORDER}; background: {CATCOLOR}; height: 2em; line-height: 2em; cursor: pointer; }
#postsubmit, button.submit { margin-right: 1em; border: 1px solid; border-color: #FFFDEE #FDB939 #FDB939 #FFFDEE; background: #FFF8C5; color: {NOTICETEXT}; padding: 0 10px; }
button.insmsg { margin: 1em 0; border: 1px solid {NOTICEBORDER}; background: {NOTICEBG}; color: {NOTICETEXT}; }
.formbox th { width: 180px; text-align: left; }
	.formbox th, .formbox td { padding: 5px; }
	.formbox th, .formbox td { border-bottom: 1px solid {BGBORDER}; }
	.formbox table a { color: {HIGHLIGHTLINK}; }
.formbox label { cursor: pointer; }
.lighttxt, .formbox *.tips { color: {LIGHTTEXT}; }
.formbox th ul { padding: 5px 0; margin: 5px 0; }
	#threadtypes table td, #threadtypes table th { border-top: 1px solid {BGBORDER}; border-bottom: 0;}
/*Login Form*/
#loginform * { vertical-align: middle; }
	#loginform button { line-height: 21px; height: 21px; padding: 0 4px; margin-left: 3px; }
/*PostForm & Editor*/
.editor_cell { vertical-align: top; }
#editor { border: solid; border-color: {CATBORDER}; border-width: 1px 1px 0; background: {COMMONBOXBG}; }
	#editor td { border: none; padding: 2px; }
.editortoolbar table { width: auto; }
.editortoolbar a, .editortoolbar .a { display: block; padding: 1px; border: 1px solid {COMMONBOXBG}; cursor: pointer; }
	.editortoolbar a.hover, .editortoolbar a:hover, .editortoolbar .a1 { background-color: {ALTBG2}; border: 1px solid {BORDERCOLOR}; text-decoration: none; }
.editor_switcher_bar {  position: relative; }
	.editor_switcher_bar a { float: right; padding: 0 3px; margin-right: 5px; }
	.editor_switcher_bar button { border: 1px solid; border-color: {CATBORDER} {CATBORDER} {TABLEBG} {CATBORDER}; font-weight: bold; height: 30px; he\ight: 28px; line-height: 28px; background: {TABLEBG}; margin: 0 2px; position: relative; top: 6px; cursor: pointer; }
		*+html .editor_switcher_bar button { top: 4px; }
		* html .editor_switcher_bar button { top: 4px; }
		.editor_switcher_bar .editor_switcher { border-bottom-color: {CATBORDER}; font-weight: normal; }
.editor_text { border: 1px solid; border-color: {CATBORDER} {CATBORDER} {CATBORDER} {CATBORDER}; }
	.editor_text textarea { border: none; width: 99%; font: 12px/1.6em "Courier New", Courier, monospace; }
.editor_button { background: {COMMONBOXBG}; border: solid {CATBORDER}; border-width: 0 1px 0; margin-bottom: {BOXSPACE}; }
	.editor_button button { background: transparent; border-width: 0 0 0 1px; color: {HIGHLIGHTLINK}; }
.editor_attach {  border: 1px solid {CATBORDER}; }
#wysiwyg { font: {MSGFONTSIZE}/1.6em {FONT} !important; }
	#wysiwyg * { line-height: normal; }
	#wysiwyg a { text-decoration:underline; color: {HIGHLIGHTLINK} !important; color: {HIGHLIGHTLINK}; }
	#wysiwyg li { margin-left: 2em; }
	#wysiwyg strong, #wysiwyg b { font-weight: bold; }
	#wysiwyg em, #wysiwyg i { font-style: italic; }
.fontname_menu { width: 97px; }
.fontsize_menu { width: 27px; line-height: normal; }
#posteditor_popup_table_menu { width: 220px; }
.fontname_menu li, .fontsize_menu li { cursor: pointer; }
.editor_colornormal, .editor_colorhover { border: none !important; padding: 2px !important; }
	.editor_colornormal div { width: 10px; height: 10px; overflow: hidden; cursor: pointer; border: 1px solid {TABLEBG}; }
		.editor_colorhover div { width: 10px; height: 10px; overflow: hidden; cursor: pointer; border: 1px solid {TABLETEXT}; }
/*QuickPost*/
	#quickpost { overflow: hidden; padding-bottom: 0; }
		* html #quickpost { height: 1%; overflow: visible; }
		#quickpost h5 { margin: 0.5em 1em; }
		.postoptions, .postform, .smilies { float: left; }
		.postoptions, .smilies { width: 20%; }
			.postoptions p { margin: 2px 0.7em; }
		.postform { width: 59%; padding-bottom: 10px; }
			.postform p label { vertical-align: top; font-weight: bold; }
			.postform h5 input { width: 60%; }
			.postform p, .postform div { margin: 0 1em; }
			.postform h4 * { vertical-align: middle; }
				.postform h4 input { width: 60%; }
			.postform textarea { width: 90%; height: 160px; }
			.postform .btns { margin-top: 0.5em; line-height: 30px; color: {LIGHTTEXT}; }
				.postform .btns button { vertical-align: middle; }
				.postform .btns a { color: {HIGHLIGHTLINK}; }
					.postform button { border: none; background: transparent; color: {HIGHLIGHTLINK}; padding: 0; cursor: pointer; }
					.postform #postsubmit { float: left; display: inline; margin-left: 2.3em; }
					.btns em { color: {LIGHTTEXT}; }
		#smilieslist { border: 1px solid {COMMONBOXBORDER}; overflow: hidden; text-align: center; }
			#quickpost #smilieslist { margin: 6px 1em 0 ; }
			#quickpost h4 { border-bottom: 1px solid {COMMONBOXBORDER};}
			#smilieslist td { border: none; padding: 8px 0; cursor: pointer; }
				#smilieslist td:hover { background: {COMMONBOXBORDER}; }
			#smilieslist .pages { float: none; border-width: 1px 0 0; }
			#smilieslist h4 { color: {HIGHLIGHTLINK}; padding: 5px; line-height: 20px; background: {COMMONBOXBG}; border-bottom: 1px solid {COMMONBOXBORDER}; text-align: left; }
				#smilieslist .popupmenu_popup { overflow: visible; padding: 5px 10px; white-space: nowrap; }
					#smilieslist .popupmenu_popup a { color: {HIGHLIGHTLINK}; }
/*Ajax Form*/
.ajaxform {}
	.ajaxform th, .ajaxform td { border-bottom: 1px solid {COMMONBOXBORDER}; padding: 5px; }
		.ajaxform thead th { font-weight: bold; }
	.ajaxform a { color: {HIGHLIGHTLINK}; }
.btns th, .btns td { border: none !important; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Advertisments ~~~~ */

#ad_headerbanner { float: right; margin-top: 7px; }
.ad_text { border: 1px solid {CATBORDER}; margin-bottom: {BOXSPACE}; padding: 6px; background:{OBG};}
.ad_text table { width: 100%; border-collapse: collapse; }
	.ad_text td { background-repeat: repeat-x; background-position: 0 0; padding: 2px 10px; }.ad_textlink1 { float: left; white-space: nowrap; }
.ad_textlink2 { margin: 10px; }
.ad_textlink1,.ad_textlink2 { padding-left: 25px; background: url({IMGDIR}/ad_icon.gif) no-repeat 0 50%; }
.ad_pip { clear: right; float: right; display: inline; margin: 10px 10px 10px; }
.ad_topicrelated { clear: both; float: right; display: inline; margin: 0 10px 10px; padding: 10px 10px 10px 30px; border: 1px solid #78A73D; background: #CAEEC0; }
.ad_column { text-align: center; margin-bottom: {BOXSPACE}; }
.ad_footerbanner { text-align: center; clear: both; background: url({IMGDIR}/wrap_shadow.gif) repeat-y center; padding: 5px; }

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Archiver ~~~~ */
.archiver .wrap { margin: 20px auto 10px; width: 760px; padding: 10px; border: 1px solid {CATBORDER}; }
	.archiver_banner { text-align: center; padding: 5px; margin-top: 40px;}
	.archiver h1, .archiver h2 { font-size: 1.17em; padding: 0 5px; }
.archiver_forumlist, .archiver_threadlist { padding: 1em; font-size: 1.17em; line-height: 1.6em; }
	.archiver_forumlist ul { padding-left: 2em; }
	.archiver_threadlist li { list-style: none; padding-left: 10px; background: url({IMGDIR}/arrow_right.gif) no-repeat 0 46%; }
		.archiver_threadlist li em { color: {LIGHTTEXT}; font-size: 0.83em; }
.archiver_post {  border-top: 1px solid {CATBORDER}; }
	.archiver_post cite { padding-left: 10px; font-weight: bold; }
	.archiver_post p { line-height: 3em; height: 3em; margin-bottom: 0.5em; background: {ALTBG2}; }
	.archiver_postbody { overflow:hidden;  font-size: 1.17em; padding: 0 10px 10px; border-bottom: 1px solid {COMMONBOXBORDER}; }
.archiver_pages, .archiver_fullversion { padding: 10px; }
	.archiver_pages strong, .archiver_fullversion strong, .archiver_fullversion strong a { font-weight: bold; color: {NOTICETEXT}; }
#ajaxwaitid { position: absolute; display: none; z-index: 100; width: 100px; height: 1.6em; top: 0px; right: 0px; line-height: 1.6em; overflow: hidden; background: #dd0000; color: #ffffff;}
.postform .special, #postform .special { font-weight: bold; color: {HIGHLIGHTLINK};}
#newpost em { color: {LIGHTTEXT} }

/* ~~~~ ~~~~ ~~~~ jammer ~~~~ ~~~~ ~~~~ */
.displayfont { font-size:0px; color:{TABLEBG}; }