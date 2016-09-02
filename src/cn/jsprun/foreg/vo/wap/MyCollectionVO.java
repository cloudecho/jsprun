package cn.jsprun.foreg.vo.wap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class MyCollectionVO extends WithFooterAndHead {
	private List<Map<String,String>> mythreadList = null;
	private List<Map<String,String>> mypostList = null;
	private List<Map<String,String>> favthreadList = null;
	private List<Map<String,String>> favforumList = null;
	public List<Map<String, String>> getMythreadList() {
		return mythreadList;
	}
	public List<Map<String, String>> getMypostList() {
		return mypostList;
	}
	public List<Map<String, String>> getFavthreadList() {
		return favthreadList;
	}
	public List<Map<String, String>> getFavforumList() {
		return favforumList;
	}
	public void setMythreadList(List<Map<String, String>> mythreadList) {
		this.mythreadList = mythreadList;
	}
	public void setMypostList(List<Map<String, String>> mypostList) {
		this.mypostList = mypostList;
	}
	public void setFavthreadList(List<Map<String, String>> favthreadList) {
		this.favthreadList = favthreadList;
	}
	public void setFavforumList(List<Map<String, String>> favforumList) {
		this.favforumList = favforumList;
	}
}
