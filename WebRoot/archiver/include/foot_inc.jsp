<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="archiver_fullversion"><bean:message key="full_version" />: <strong><a href="${valueObject.footer.link }" target="_blank">${valueObject.footer.title }</a></strong></div>
</div>
<c:if test="${!empty valueObject.footer.footerbanner1}"><div class="archiver_banner">${valueObject.footer.footerbanner1 }</div></c:if>
<c:if test="${!empty valueObject.footer.footerbanner2}"><div class="archiver_banner">${valueObject.footer.footerbanner2 }</div></c:if>
<c:if test="${!empty valueObject.footer.footerbanner3}"><div class="archiver_banner">${valueObject.footer.footerbanner3 }</div></c:if>
<p id="copyright">Powered by <strong><a href="http://www.jsprun.net" target="_blank">JspRun! Archiver</a></strong> <em>${valueObject.footer.version }</em>&nbsp; &copy; 2007-2011 <a href="http://www.jsprun.com" target="_blank">JspRun! Inc.</a><br /><br /></p>
</body>
</html>