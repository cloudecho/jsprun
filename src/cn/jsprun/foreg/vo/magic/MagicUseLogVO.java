package cn.jsprun.foreg.vo.magic;
public class MagicUseLogVO extends MagicLogBaseVO {
	private String targetTid;
	private String targetUid;
	private String contextPath;
public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public void setTargetTid(String targetTid) {
		this.targetTid = targetTid;
	}
	public void setTargetUid(String targetUid) {
		this.targetUid = targetUid;
	}
	public boolean isDrop() {
		return getUrlOnSeeObject()==null;
	}
	public String getUrlOnSeeObject() {
		if(targetTid!=null){
			return contextPath+"/viewthread.jsp?tid="+targetTid;
		}else if(targetUid!=null){
			return contextPath+"/space.jsp?action=viewpro&uid="+targetUid;
		}else {
			return null;
		}
	}
}
