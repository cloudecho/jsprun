package cn.jsprun.struts.form;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.utils.Common;
public class UserForm extends ActionForm {
	private static final long serialVersionUID = 7095284415113994738L;
	private String[] uids = {""};
	private String username = "";
	private String password = "";
	private String email = "";
	private Short groupid = 0;
	private String[] usergroupids = {""};
	private String lower_credits = "";
	private String higher_credits = "";
	private String lower_extcredits1 = "";
	private String higher_extcredits1 = "";
	private String lower_extcredits2 = "";
	private String higher_extcredits2 = "";
	private String lower_extcredits3 = "";
	private String higher_extcredits3 = "";
	private String lower_extcredits4 = "";
	private String higher_extcredits4 = "";
	private String lower_extcredits5 = "";
	private String higher_extcredits5 = "";
	private String lower_extcredits6 = "";
	private String higher_extcredits6 = "";
	private String lower_extcredits7 = "";
	private String higher_extcredits7 = "";
	private String lower_extcredits8 = "";
	private String higher_extcredits8 = "";
	private String lower_posts = "";
	private String higher_posts = "";
	private String regip = "";
	private String lastip = "";
	private String regdatebefore = "";
	private String regdateafter = "";
	private String lastvisitbefore = "";
	private String lastvisitafter = "";
	private String lastpostbefore = "";
	private String lastpostafter = "";
	private String birthyear = "";
	private String birthmonth = "";
	private String birthday = "";
	private String cins = "";
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		if(!Common.isNum(lower_credits)){
			lower_credits = "";
		}
		if(!Common.isNum(higher_credits)){
			higher_credits = "";
		}
		if(!Common.isNum(lower_extcredits1)){
			lower_extcredits1 = "";
		}
		if(!Common.isNum(higher_extcredits1)){
			higher_extcredits1 = "";
		}
		if(!Common.isNum(lower_extcredits2)){
			lower_extcredits2 = "";
		}
		if(!Common.isNum(higher_extcredits2)){
			higher_extcredits2 = "";
		}
		if(!Common.isNum(lower_posts)){
			lower_posts = "";
		}
		if(!Common.isNum(higher_posts)){
			higher_posts = "";
		}
		if(!Common.datecheck(regdatebefore)){
			regdatebefore = "";
		}
		if(!Common.datecheck(regdateafter)){
			regdateafter = "";
		}
		if(!Common.datecheck(lastvisitbefore)){
			lastvisitbefore = "";
		}
		if(!Common.datecheck(lastvisitafter)){
			lastvisitafter = "";
		}
		if(!Common.datecheck(lastpostbefore)){
			lastpostbefore = "";
		}
		if(!Common.datecheck(lastpostafter)){
			lastpostafter = "";
		}
		if(!birthyear.matches("\\d{4}")){
			birthyear = "";
		}
		if(!Common.isNum(lower_extcredits3)){
			lower_extcredits3 = "";
		}
		if(!Common.isNum(lower_extcredits4)){
			lower_extcredits4 = "";
		}
		if(!Common.isNum(lower_extcredits5)){
			lower_extcredits5 = "";
		}
		if(!Common.isNum(lower_extcredits6)){
			lower_extcredits6 = "";
		}
		if(!Common.isNum(lower_extcredits7)){
			lower_extcredits7 = "";
		}
		if(!Common.isNum(lower_extcredits8)){
			lower_extcredits8 = "";
		}
		if(!Common.isNum(higher_extcredits3)){
			higher_extcredits3 = "";
		}
		if(!Common.isNum(higher_extcredits4)){
			higher_extcredits4 = "";
		}
		if(!Common.isNum(higher_extcredits5)){
			higher_extcredits5 = "";
		}
		if(!Common.isNum(higher_extcredits6)){
			higher_extcredits6 = "";
		}
		if(!Common.isNum(higher_extcredits7)){
			higher_extcredits7 = "";
		}
		if(!Common.isNum(higher_extcredits8)){
			higher_extcredits8 = "";
		}
		return null;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = Common.addslashes(email);
	}
	public Short getGroupid() {
		return groupid;
	}
	public void setGroupid(Short groupid) {
		this.groupid = groupid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = Common.addslashes(username);
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBirthmonth() {
		return birthmonth;
	}
	public void setBirthmonth(String birthmonth) {
		this.birthmonth = birthmonth;
	}
	public String getBirthyear() {
		return birthyear;
	}
	public void setBirthyear(String birthyear) {
		this.birthyear = birthyear;
	}
	public String getHigher_credits() {
		return higher_credits;
	}
	public void setHigher_credits(String higher_credits) {
		this.higher_credits = higher_credits;
	}
	public String getHigher_extcredits1() {
		return higher_extcredits1;
	}
	public void setHigher_extcredits1(String higher_extcredits1) {
		this.higher_extcredits1 = higher_extcredits1;
	}
	public String getHigher_extcredits2() {
		return higher_extcredits2;
	}
	public void setHigher_extcredits2(String higher_extcredits2) {
		this.higher_extcredits2 = higher_extcredits2;
	}
	public String getHigher_posts() {
		return higher_posts;
	}
	public void setHigher_posts(String higher_posts) {
		this.higher_posts = higher_posts;
	}
	public String getLastip() {
		return lastip;
	}
	public void setLastip(String lastip) {
		this.lastip = Common.addslashes(lastip);
	}
	public String getLastpostafter() {
		return lastpostafter;
	}
	public void setLastpostafter(String lastpostafter) {
		this.lastpostafter = lastpostafter;
	}
	public String getLastpostbefore() {
		return lastpostbefore;
	}
	public void setLastpostbefore(String lastpostbefore) {
		this.lastpostbefore = lastpostbefore;
	}
	public String getLastvisitafter() {
		return lastvisitafter;
	}
	public void setLastvisitafter(String lastvisitafter) {
		this.lastvisitafter = lastvisitafter;
	}
	public String getLastvisitbefore() {
		return lastvisitbefore;
	}
	public void setLastvisitbefore(String lastvisitbefore) {
		this.lastvisitbefore = lastvisitbefore;
	}
	public String getLower_credits() {
		return lower_credits;
	}
	public void setLower_credits(String lower_credits) {
		this.lower_credits = lower_credits;
	}
	public String getLower_extcredits1() {
		return lower_extcredits1;
	}
	public void setLower_extcredits1(String lower_extcredits1) {
		this.lower_extcredits1 = lower_extcredits1;
	}
	public String getLower_extcredits2() {
		return lower_extcredits2;
	}
	public void setLower_extcredits2(String lower_extcredits2) {
		this.lower_extcredits2 = lower_extcredits2;
	}
	public String getLower_posts() {
		return lower_posts;
	}
	public void setLower_posts(String lower_posts) {
		this.lower_posts = lower_posts;
	}
	public String getRegdateafter() {
		return regdateafter;
	}
	public void setRegdateafter(String regdateafter) {
		this.regdateafter = regdateafter;
	}
	public String getRegdatebefore() {
		return regdatebefore;
	}
	public void setRegdatebefore(String regdatebefore) {
		this.regdatebefore = regdatebefore;
	}
	public String getRegip() {
		return regip;
	}
	public void setRegip(String regip) {
		this.regip = Common.addslashes(regip);
	}
	public String[] getUsergroupids() {
		return usergroupids;
	}
	public void setUsergroupids(String[] usergroupids) {
		this.usergroupids = usergroupids;
	}
	public String getCins() {
		return cins;
	}
	public void setCins(String cins) {
		this.cins = cins;
	}
	public String[] getUids() {
		return uids;
	}
	public void setUids(String[] uids) {
		this.uids = uids;
	}
	public String getHigher_extcredits3() {
		return higher_extcredits3;
	}
	public void setHigher_extcredits3(String higher_extcredits3) {
		this.higher_extcredits3 = higher_extcredits3;
	}
	public String getHigher_extcredits4() {
		return higher_extcredits4;
	}
	public void setHigher_extcredits4(String higher_extcredits4) {
		this.higher_extcredits4 = higher_extcredits4;
	}
	public String getHigher_extcredits5() {
		return higher_extcredits5;
	}
	public void setHigher_extcredits5(String higher_extcredits5) {
		this.higher_extcredits5 = higher_extcredits5;
	}
	public String getHigher_extcredits6() {
		return higher_extcredits6;
	}
	public void setHigher_extcredits6(String higher_extcredits6) {
		this.higher_extcredits6 = higher_extcredits6;
	}
	public String getHigher_extcredits7() {
		return higher_extcredits7;
	}
	public void setHigher_extcredits7(String higher_extcredits7) {
		this.higher_extcredits7 = higher_extcredits7;
	}
	public String getHigher_extcredits8() {
		return higher_extcredits8;
	}
	public void setHigher_extcredits8(String higher_extcredits8) {
		this.higher_extcredits8 = higher_extcredits8;
	}
	public String getLower_extcredits3() {
		return lower_extcredits3;
	}
	public void setLower_extcredits3(String lower_extcredits3) {
		this.lower_extcredits3 = lower_extcredits3;
	}
	public String getLower_extcredits4() {
		return lower_extcredits4;
	}
	public void setLower_extcredits4(String lower_extcredits4) {
		this.lower_extcredits4 = lower_extcredits4;
	}
	public String getLower_extcredits5() {
		return lower_extcredits5;
	}
	public void setLower_extcredits5(String lower_extcredits5) {
		this.lower_extcredits5 = lower_extcredits5;
	}
	public String getLower_extcredits6() {
		return lower_extcredits6;
	}
	public void setLower_extcredits6(String lower_extcredits6) {
		this.lower_extcredits6 = lower_extcredits6;
	}
	public String getLower_extcredits7() {
		return lower_extcredits7;
	}
	public void setLower_extcredits7(String lower_extcredits7) {
		this.lower_extcredits7 = lower_extcredits7;
	}
	public String getLower_extcredits8() {
		return lower_extcredits8;
	}
	public void setLower_extcredits8(String lower_extcredits8) {
		this.lower_extcredits8 = lower_extcredits8;
	}
}