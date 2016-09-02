package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Stats_forumsrankVO{
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private List<Map<String, ForumCompositor>> forumCompositorMapList = new ArrayList<Map<String,ForumCompositor>>();
	private String lastTime;
	private String nextTime;
	public static class ForumCompositor{
		private String fid;
		private String name;
		private String num;
		private ForumCompositor(){
		}
		public String getFid() {
			return fid;
		}
		public void setFid(String fid) {
			this.fid = fid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
	}
	public List<Map<String, ForumCompositor>> getForumCompositorMapList() {
		return forumCompositorMapList;
	}
	public void setForumCompositorMapList(Map<Integer,Map<String,String>> statvars_threadMap,
										  Map<Integer,Map<String,String>> statvars_postMap,
										  Map<Integer,Map<String,String>> statvars_thisMonthMap,
										  Map<Integer,Map<String,String>> statvars_thisDayMap) {
		Map<String,ForumCompositor> forumCompisitorMap = null;
		Map<String,String> mapTemp_post = null;
		Map<String,String> mapTmep_thisMonthMap = null;
		Map<String,String> mapTmep_thisDayMap = null;
		for(int i = 0;i<statvars_threadMap.size();i++){
			forumCompisitorMap = new HashMap<String, ForumCompositor>();
			Map<String,String> mapTemp_thread = statvars_threadMap.get(i);
			ForumCompositor forumCompositor_thread = new ForumCompositor();
			forumCompositor_thread.setFid(mapTemp_thread.get("fid"));
			forumCompositor_thread.setName(mapTemp_thread.get("name"));
			forumCompositor_thread.setNum(mapTemp_thread.get("threads"));
			forumCompisitorMap.put("thread", forumCompositor_thread);
			mapTemp_post = statvars_postMap.get(i);
			if(mapTemp_post!=null){
				ForumCompositor forumCompositor_post = new ForumCompositor();
				forumCompositor_post.setFid(mapTemp_post.get("fid"));
				forumCompositor_post.setName(mapTemp_post.get("name"));
				forumCompositor_post.setNum(mapTemp_post.get("posts"));
				forumCompisitorMap.put("post", forumCompositor_post);
			}
			mapTmep_thisMonthMap = statvars_thisMonthMap.get(i);
			if(mapTmep_thisMonthMap!=null){
				ForumCompositor forumCompositor_thisMonthMap = new ForumCompositor();
				forumCompositor_thisMonthMap.setFid(mapTmep_thisMonthMap.get("fid"));
				forumCompositor_thisMonthMap.setName(mapTmep_thisMonthMap.get("name"));
				forumCompositor_thisMonthMap.setNum(mapTmep_thisMonthMap.get("posts"));
				forumCompisitorMap.put("thread_thisMonth", forumCompositor_thisMonthMap);
			}
			mapTmep_thisDayMap = statvars_thisDayMap.get(i);
			if(mapTmep_thisDayMap!=null){
				ForumCompositor forumCompositor_thisDayMap = new ForumCompositor();
				forumCompositor_thisDayMap.setFid(mapTmep_thisDayMap.get("fid"));
				forumCompositor_thisDayMap.setName(mapTmep_thisDayMap.get("name"));
				forumCompositor_thisDayMap.setNum(mapTmep_thisDayMap.get("posts"));
				forumCompisitorMap.put("thread_thisDay", forumCompositor_thisDayMap);
			}
			forumCompositorMapList.add(forumCompisitorMap);
			forumCompisitorMap = null;
			mapTemp_post = null;
			mapTmep_thisMonthMap = null;
			mapTmep_thisDayMap = null;
		}
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
}
