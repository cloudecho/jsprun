package cn.jsprun.foreg.vo.statistic;
public class PageInfo {
		private String information;
		private String num;
		private String numPercent;
		private Integer maxLengh = 370;
		public PageInfo(){
		}
		public String getLineWidth() {
			return (maxLengh*(Double.valueOf(numPercent)/100))+"";
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getNumPercent() {
			return numPercent;
		}
		public void setNumPercent(String numPercent) {
			this.numPercent = numPercent;
		}
		public String getInformation() {
			return information;
		}
		public void setInformation(String information) {
			this.information = information;
		}
		public void setMaxLengh(Integer maxLengh) {
			this.maxLengh = maxLengh;
		}
}
