package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.List;
public class Stats_mainVO  {
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private Double runtime;
	private String membersNum;
	private String memberOfManageNum;
	private String membersOfNoPostsNum;
	private String newMemberName;
	private String postsNum_allNum;
	private String bestMem;
	private String bestMemPosts;
	private String avg_everyBodyPost;
	private String formsCount;
	private String avg_addPostsEveryDay;
	private String bestModuleID;
	private String bestModule;
	private String bestModuleThreadNum;
	private String bestModulePostsNum;
	private String threadNum;
	private String postsNum;
	private String avg_returnPostsEyeryThread;
	private String avg_loginEveryDay;
	private String addPostsInLast24;
	private String addMemberInLast24;
	private boolean showFluxSurvey;
	private String allPageFlux;
	private String accesserNum;
	private String memberOfAccess;
	private String visitorOfAccess;
	private String accessMaxNum;
	private String allPageFluxOfMonth;
	private String accessTime;
	private String accessTimeAllFlux; 
	private List<PageInfo> monthFlux = new ArrayList<PageInfo>();
	private SubPostlog subPostLog = new SubPostlog();
	private String lastTime;
	private String nextTime;
	public String getAccesserNum() {
		if(accesserNum==null ){
			if(showFluxSurvey){
				return accesserNum = (Long.valueOf(memberOfAccess)+Long.valueOf(visitorOfAccess))+"";
			}else{
				return "1";
			}
		}else{
			return accesserNum;
		}
	}
	public String getAccessMaxNum() {
		return accessMaxNum;
	}
	public void setAccessMaxNum(String accessMaxNum) {
		this.accessMaxNum = accessMaxNum;
	}
	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	public String getAddMemberInLast24() {
		return addMemberInLast24;
	}
	public void setAddMemberInLast24(String addMemberInLast24) {
		this.addMemberInLast24 = addMemberInLast24;
	}
	public String getAddPostsInLast24() {
		return addPostsInLast24;
	}
	public void setAddPostsInLast24(String addPostsInLast24) {
		this.addPostsInLast24 = addPostsInLast24;
	}
	public String getAllPageFlux() {
		return allPageFlux;
	}
	public void setAllPageFlux(String allPageFlux) {
		this.allPageFlux = allPageFlux;
	}
	public String getAllPageFluxOfMonth() {
		return allPageFluxOfMonth;
	}
	public void setAllPageFluxOfMonth(String allPageFluxOfMonth) {
		this.allPageFluxOfMonth = allPageFluxOfMonth;
	}
	public String getAvg_addPostsEveryDay() {
		if (avg_addPostsEveryDay == null) {
			if(runtime == 0){
				return avg_addPostsEveryDay = 0+"";
			}
			Double temp = Integer.valueOf(postsNum) / runtime;
			if (temp % 1 == 0) {
				return avg_addPostsEveryDay = temp.intValue() + "";
			} else {
				if (temp < 1) {
					return avg_addPostsEveryDay = 1 + "";
				}
				return avg_addPostsEveryDay = (temp.intValue() + 1) + "";
			}
		} else {
			return avg_addPostsEveryDay;
		}
	}
	public String getAvg_everyBodyPost() {
		if(avg_everyBodyPost==null){
			return avg_everyBodyPost = get2Point(Float.parseFloat(postsNum)/Integer.parseInt(membersNum)+"");
		}else{
			return avg_everyBodyPost;
		}
	}
	public String getAvg_loginEveryDay() {
		if (avg_loginEveryDay == null) {
			Double temp = null;
			if(runtime!=0){
				temp = Integer.parseInt(membersNum) / runtime;
			}else{
				return "0";
			}
			if (temp % 1 == 0) {
				return avg_loginEveryDay = temp.intValue() + "";
			} else {
				if (temp < 1) {
					return avg_loginEveryDay = 1 + "";
				}
				return avg_loginEveryDay = (temp.intValue() + 1) + "";
			}
		} else {
			return avg_loginEveryDay;
		}
	}
	public String getAvg_returnPostsEyeryThread() {
		if(avg_returnPostsEyeryThread==null){
			float postsNumfloat = Float.parseFloat(postsNum);
			int threadNumInt = Integer.parseInt(threadNum);
			if(threadNumInt == 0){
				return avg_returnPostsEyeryThread = "0.00";
			}
			float resutl = Math.round((postsNumfloat-threadNumInt)/threadNumInt*100)/100f;
			return 	avg_returnPostsEyeryThread = resutl+"";
		}else{
			return avg_returnPostsEyeryThread;
		}
	}
	public String getAvg_scanEverybody() {
		float result = Math.round(Float.valueOf(allPageFlux)/Float.valueOf( getAccesserNum())*100)/100f;
		return result+"";
	}
	public boolean isBeingBestMem() {
		return !bestMem.equals("NULL");
	}
	public String getBestMem() {
		return bestMem;
	}
	public void setBestMem(String bestMem) {
		this.bestMem = bestMem;
	}
	public String getBestMemPosts() {
		return bestMemPosts;
	}
	public void setBestMemPosts(String bestMemPosts) {
		this.bestMemPosts = bestMemPosts;
	}
	public String getBestModule() {
		return bestModule;
	}
	public void setBestModule(String bestModule) {
		this.bestModule = bestModule;
	}
	public String getBestModuleActivityInfo() {
		double temp1 = 0;
		if(membersNum!=null&&!membersNum.equals("0")){
			temp1 = Double.parseDouble(getAvg_loginEveryDay())/Double.parseDouble(membersNum);
		}
		double num1 = 0;
		if(postsNum!=null&&!postsNum.equals("0")){
			num1 = (temp1+Double.parseDouble(getAvg_addPostsEveryDay())/Double.parseDouble(postsNum))*1500;
		}
		double num2 = (Double.parseDouble(getAvg_returnPostsEyeryThread()))*10;
		double num3 = Double.parseDouble(getPostsNum_allNum())*0.1;
		double num4 = Math.round((Long.parseLong(allPageFlux) / Long.parseLong(getAccesserNum()))* 100) / 100+Double.parseDouble(getAvg_everyBodyPost());
		return Double.valueOf(num1+num2+num3+num4).longValue()+"";
	}
	public String getBestModuleID() {
		return bestModuleID;
	}
	public void setBestModuleID(String bestModuleID) {
		this.bestModuleID = bestModuleID;
	}
	public String getBestModulePostsNum() {
		return bestModulePostsNum;
	}
	public void setBestModulePostsNum(String bestModulePostsNum) {
		this.bestModulePostsNum = bestModulePostsNum;
	}
	public String getBestModuleThreadNum() {
		return bestModuleThreadNum;
	}
	public void setBestModuleThreadNum(String bestModuleThreadNum) {
		this.bestModuleThreadNum = bestModuleThreadNum;
	}
	public String getFormsCount() {
		return formsCount;
	}
	public void setFormsCount(String formsCount) {
		this.formsCount = formsCount;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getMemberOfAccess() {
		return memberOfAccess;
	}
	public void setMemberOfAccess(String memberOfAccess) {
		this.memberOfAccess = memberOfAccess;
	}
	public String getMemberOfManageNum() {
		return memberOfManageNum;
	}
	public void setMemberOfManageNum(String memberOfManageNum) {
		this.memberOfManageNum = memberOfManageNum;
	}
	public String getMembersNum() {
		return membersNum;
	}
	public void setMembersNum(String membersNum) {
		this.membersNum = membersNum;
	}
	public String getMembersOfNoPostsNum() {
		return membersOfNoPostsNum;
	}
	public void setMembersOfNoPostsNum(String membersOfNoPostsNum) {
		this.membersOfNoPostsNum = membersOfNoPostsNum;
	}
	public String getMembersOfPostsNum() {
		return (Integer.parseInt(membersNum)
				-Integer.parseInt(membersOfNoPostsNum))+"";
	}
	public String getNewMemberName() {
		return newMemberName;
	}
	public void setNewMemberName(String newMemberName) {
		this.newMemberName = newMemberName;
	}
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	public String getPostsNum() {
		return postsNum;
	}
	public void setPostsNum(String postsNum) {
		this.postsNum = postsNum;
	}
	public String getPostsNum_allNum() {
		if(postsNum_allNum==null){
			return postsNum_allNum = get2Point((Float.parseFloat(getMembersOfPostsNum())/Float.parseFloat(membersNum)*100)+"");
		}else{
			return postsNum_allNum;
		}
	}
	public boolean getShowFluxSurvey() {
		return showFluxSurvey;
	}
	public void setShowFluxSurvey(boolean showFluxSurvey) {
		this.showFluxSurvey = showFluxSurvey;
	}
	public String getThreadNum() {
		return threadNum;
	}
	public void setThreadNum(String threadNum) {
		this.threadNum = threadNum;
	}
	public String getVisitorOfAccess() {
		return visitorOfAccess;
	}
	public void setVisitorOfAccess(String visitorOfAccess) {
		this.visitorOfAccess = visitorOfAccess;
	}
	private String get2Point(String targetString){
		int pointIndex = targetString.indexOf(".");
		if(pointIndex<0){
			return targetString;
		}else{
			if(pointIndex+3<=targetString.length()){
				return targetString.substring(0,pointIndex+3);
			}else{
				return targetString.substring(0,targetString.length());
			}
		}
	}
	public void setRuntime(String runtime) {
		this.runtime = Double.valueOf(runtime);
	}
	public String getAccessTimeAllFlux() {
		return accessTimeAllFlux;
	}
	public void setAccessTimeAllFlux(String accessTimeAllFlux) {
		this.accessTimeAllFlux = accessTimeAllFlux;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
	public SubPostlog getSubPostLog() {
		return subPostLog;
	}
	public List<PageInfo> getMonthFlux() {
		return monthFlux;
	}
}
