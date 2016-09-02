package cn.jsprun.struts.form;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
@SuppressWarnings("serial")
public class FileUploadForm extends ActionForm{
	private FormFile customavatar;
	public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request) {
		return null;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}
	public FormFile getCustomavatar() {
		return customavatar;
	}
	public void setCustomavatar(FormFile customavatar) {
		this.customavatar = customavatar;
	}
}
