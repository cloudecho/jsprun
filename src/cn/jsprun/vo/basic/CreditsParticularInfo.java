package cn.jsprun.vo.basic;
import java.util.Map;
import java.util.TreeMap;
public class CreditsParticularInfo {
	private Extcredit currentExtcredit = new Extcredit(); 
	private Map<String,String> credits_id_nameMap = new TreeMap<String, String>();
	private String creditspolicy_post = null;
	private String creditspolicy_reply = null;
	private String creditspolicy_digest = null;
	private String creditspolicy_postattach = null;
	private String creditspolicy_getattach = null;
	private String creditspolicy_pm = null;
	private String creditspolicy_search = null;
	private String creditspolicy_promotion_visit = null;
	private String creditspolicy_promotion_register = null;
	private String creditspolicy_tradefinished = null;
	private String creditspolicy_votepoll = null;
	private String creditspolicy_lowerlimit = null;
	private CreditsParticularInfo() {
	}
	public CreditsParticularInfo(String id,String title,
			String unit,boolean available,
			boolean allowexchangein,boolean allowexchangeout,
			boolean showinthread,String lowerlimit,
			String ratio,String initcredit){
		currentExtcredit.setAllowexchangein(allowexchangein);
		currentExtcredit.setAllowexchangeout(allowexchangeout);
		currentExtcredit.setAvailable(available);
		currentExtcredit.setId(id);
		currentExtcredit.setLowerlimit(lowerlimit);
		currentExtcredit.setRatio(ratio);
		currentExtcredit.setShowinthread(showinthread);
		currentExtcredit.setTitle(title);
		currentExtcredit.setUnit(unit);
		currentExtcredit.setInitcredit(initcredit);
	}
	public String getCreditspolicy_post() {
		return creditspolicy_post;
	}
	public void setCreditspolicy_post(String creditspolicy_post) {
		this.creditspolicy_post = creditspolicy_post;
	}
	public String getCreditspolicy_reply() {
		return creditspolicy_reply;
	}
	public void setCreditspolicy_reply(String creditspolicy_reply) {
		this.creditspolicy_reply = creditspolicy_reply;
	}
	public String getCreditspolicy_digest() {
		return creditspolicy_digest;
	}
	public void setCreditspolicy_digest(String creditspolicy_digest) {
		this.creditspolicy_digest = creditspolicy_digest;
	}
	public String getCreditspolicy_postattach() {
		return creditspolicy_postattach;
	}
	public void setCreditspolicy_postattach(String creditspolicy_postattach) {
		this.creditspolicy_postattach = creditspolicy_postattach;
	}
	public String getCreditspolicy_getattach() {
		return creditspolicy_getattach;
	}
	public void setCreditspolicy_getattach(String creditspolicy_getattach) {
		this.creditspolicy_getattach = creditspolicy_getattach;
	}
	public String getCreditspolicy_pm() {
		return creditspolicy_pm;
	}
	public void setCreditspolicy_pm(String creditspolicy_pm) {
		this.creditspolicy_pm = creditspolicy_pm;
	}
	public String getCreditspolicy_search() {
		return creditspolicy_search;
	}
	public void setCreditspolicy_search(String creditspolicy_search) {
		this.creditspolicy_search = creditspolicy_search;
	}
	public String getCreditspolicy_promotion_visit() {
		return creditspolicy_promotion_visit;
	}
	public void setCreditspolicy_promotion_visit(
			String creditspolicy_promotion_visit) {
		this.creditspolicy_promotion_visit = creditspolicy_promotion_visit;
	}
	public String getCreditspolicy_promotion_register() {
		return creditspolicy_promotion_register;
	}
	public void setCreditspolicy_promotion_register(
			String creditspolicy_promotion_register) {
		this.creditspolicy_promotion_register = creditspolicy_promotion_register;
	}
	public String getCreditspolicy_tradefinished() {
		return creditspolicy_tradefinished;
	}
	public void setCreditspolicy_tradefinished(String creditspolicy_tradefinished) {
		this.creditspolicy_tradefinished = creditspolicy_tradefinished;
	}
	public String getCreditspolicy_votepoll() {
		return creditspolicy_votepoll;
	}
	public void setCreditspolicy_votepoll(String creditspolicy_votepoll) {
		this.creditspolicy_votepoll = creditspolicy_votepoll;
	}
	public String getCreditspolicy_lowerlimit() {
		return creditspolicy_lowerlimit;
	}
	public void setCreditspolicy_lowerlimit(String creditspolicy_lowerlimit) {
		this.creditspolicy_lowerlimit = creditspolicy_lowerlimit;
	}
	public Extcredit getCurrentExtcredit() {
		return currentExtcredit;
	}
	public String getCurrentCreditsName_nc() {
		return "-extcredits"+currentExtcredit.getId()+"("+currentExtcredit.getTitle()+")";
	}
	public Map<String, String> getCredits_id_nameMap() {
		return credits_id_nameMap;
	}
}
