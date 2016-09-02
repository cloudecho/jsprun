<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
<title>JspRun! -JAVA.NO.1<bean:message key="admincp_community"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="images/admincp/login.css" rel="stylesheet" type="text/css" />
<link rel="SHORTCUT ICON" href="favicon.ico" />
<script type="text/javascript">
if(self.parent.frames.length != 0) {
	self.parent.location=document.location;
}
function redirect(url) {
	window.location.replace(url);
}
</script>
<link href="include/css/keyboard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
var lang = {};
lang["kb_backSpace"] = '← <bean:message key="kb_backSpace"/>';
lang["kb_capsLockOff"] = '<bean:message key="kb_capsLockOff"/>';
lang["kb_capsLockOn"] = '<bean:message key="kb_capsLockOn"/>';
lang["kb_clear"] = '<bean:message key="kb_clear"/>';
lang["kb_enter"] = '←|\n<bean:message key="kb_enter"/>';
lang["kb_title"] = '<bean:message key="kb_title"/>';
</script>
<script src="include/javascript/keyboard.js" type="text/javascript"></script>
</head>
        <body class="a0v2">
<style>
A.applink:hover {border: 2px dotted #DCE6F4;padding:2px;background-color:#ffff00;color:green;text-decoration:none}
A.applink       {border: 2px dotted #DCE6F4;padding:2px;color:#2F5BFF;background:transparent;text-decoration:none}
A.info          {color:#2F5BFF;background:transparent;text-decoration:none}
A.info:hover    {color:green;background:transparent;text-decoration:underline}
</style>
        
        <div class="hp0 hp0v4">
	    <div class="hp0w3"><div class="hp0w4">
        <div class="hp4 hp4v1">
            <h2><a><bean:message key="admincp"/></a></h2>

            

            <div class="hp4w1"><div class="hp4w2"><div class="hp4w3"><div class="hp4w4">
				<table width="100%" border="0" cellpadding="8" cellspacing="0">
					<tr>
						<td width="80"></td><td width="140"></td>
						<td></td>
						<td width="120"></td>
						<td width="80"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					${message}
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>

            </div></div></div></div>
        </div>

	    </div></div>
        
	    </div>
        <div class="footer">
        <table align="center"><tr><td align="center" colspan="5">Powered by <a style="color: rgb(255, 255, 255);" target="_blank" href="http://www.jsprun.net"><b>JspRun!</b></a> &nbsp;&copy; 2007-2011 <a style="color: rgb(255, 255, 255);" target="_blank" href="http://www.jsprun.com">JspRun! Inc.</a></td></tr>
</table>
</div>
</body>
</html>