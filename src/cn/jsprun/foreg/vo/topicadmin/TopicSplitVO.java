package cn.jsprun.foreg.vo.topicadmin;
import java.util.ArrayList;
import java.util.List;
public class TopicSplitVO extends TopicPublicVO {
	private List<PostsInfo> postsInfoList = new ArrayList<PostsInfo>();
	public static class PostsInfo{
		private String pid;
		private String author;
		private String content;
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
	}
	public List<PostsInfo> getPostsInfoList() {
		return postsInfoList;
	}
	public void setPostsInfoList(List<PostsInfo> postsInfoList) {
		this.postsInfoList = postsInfoList;
	}
	public PostsInfo getPostsInfo(){
		return new PostsInfo();
	}
}
