package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.AttachmentsDao;
import cn.jsprun.domain.Attachments;
import cn.jsprun.struts.form.AttachmentsForm;
import cn.jsprun.utils.BeanFactory;
public class AttachmentsService {
	public Integer deleteArray(String[] aids) {
		if (aids.length > 0) {
			((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).deleteArray(aids);
		}
		return null;
	}
	public String findByAttachmentsForm(AttachmentsForm attachmentsForm) {
		if (attachmentsForm != null) {
			return ((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).findByAttachmentsForm(attachmentsForm);
		}
		return null;
	}
	public Attachments findAttachmentsById(int aid){
		return ((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).findAttachmentsById(aid);
	}
	public List<Attachments> findByattaByTid(Integer tid){
		return ((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).findByattaByTid(tid);
	}
}
