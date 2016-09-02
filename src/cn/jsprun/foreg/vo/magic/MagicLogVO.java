package cn.jsprun.foreg.vo.magic;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.MagiclogId;
import cn.jsprun.utils.Common;
public class MagicLogVO extends Magic_navbarVO{
	private String operation;
	private List<MagicUseLogVO> magicUseLogList = new ArrayList<MagicUseLogVO>();
	private List<MagicBuyLogVO> magicBuyLogList = new ArrayList<MagicBuyLogVO>();
	private List<MagicGiveOrReceiveLogVO> magicGiveOrReceiveLogList = new ArrayList<MagicGiveOrReceiveLogVO>();
	private List<MagicMarketLogVO> magicMarketLogList = new ArrayList<MagicMarketLogVO>();
	private String multipage = null;
	public MagicLogVO(){
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public List<MagicUseLogVO> getMagicUseLogList() {
		return magicUseLogList;
	}
	public List<MagicBuyLogVO> getMagicBuyLogList() {
		return magicBuyLogList;
	}
	public List<MagicMarketLogVO> getMagicMarketLogList() {
		return magicMarketLogList;
	}
	public List<MagicGiveOrReceiveLogVO> getMagicGiveOrReceiveLogList() {
		return magicGiveOrReceiveLogList;
	}
	public void setMagicUseLogList(List<Magiclog> magiclogList,Map<Short,String> magicId_NameMap,String contextPath,String timeoffset) {
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
		Magiclog magiclog = null;
		MagiclogId magiclogId = null;
		MagicUseLogVO magicUseLogVO = null;
		for(int i=0;i<magiclogList.size();i++){
			magiclog = magiclogList.get(i);
			magiclogId = magiclog.getId();
			magicUseLogVO = new MagicUseLogVO();
			magicUseLogVO.setMagicId(magiclogId.getMagicid().toString());
			magicUseLogVO.setMagicName(magicId_NameMap.get(magiclogId.getMagicid()));
			magicUseLogVO.setOperatingTime(Common.gmdate(dateFormat, magiclogId.getDateline()));
			magicUseLogVO.setTargetTid(magiclogId.getTargettid()==0?null:magiclogId.getTargettid().toString());
			magicUseLogVO.setTargetUid(magiclogId.getTargetuid()==0?null:magiclogId.getTargetuid().toString());
			magicUseLogVO.setContextPath(contextPath);
			if(magiclogId.getTargetpid()!=0){
				magicUseLogVO.setTargetTid("0");
			}
			magicUseLogList.add(magicUseLogVO);
		}
		dateFormat = null;
		magiclog = null;
		magiclogId = null;
		magicUseLogVO = null;
	}
	public boolean isShowUseLogList(){
		return magicUseLogList.size()>0;
	}
	public void setMagicBuyLogList(List<Magiclog> magiclogList,Map<Short,String> magicId_NameMap,String util,String timeoffset) {
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
		Magiclog magiclog = null;
		MagiclogId magiclogId = null;
		MagicBuyLogVO magicBuyLogVO = null;
		for(int i=0;i<magiclogList.size();i++){
			magiclog = magiclogList.get(i);
			magiclogId = magiclog.getId();
			magicBuyLogVO = new MagicBuyLogVO();
			magicBuyLogVO.setMagicId(magiclogId.getMagicid().toString());
			magicBuyLogVO.setMagicName(magicId_NameMap.get(magiclogId.getMagicid()));
			magicBuyLogVO.setMagicNum(magiclogId.getAmount().toString());
			magicBuyLogVO.setOperatingTime(Common.gmdate(dateFormat, magiclogId.getDateline()));
			magicBuyLogVO.setPrice(magiclogId.getPrice().toString());
			magicBuyLogVO.setUtil(util);
			magicBuyLogList.add(magicBuyLogVO);
		}
		dateFormat = null;
		magiclog = null;
		magiclogId = null;
		magicBuyLogVO = null;
	}
	public boolean isShowBuyLogList(){
		return magicBuyLogList.size()>0;
	}
	public void setMagicGiveOrReceiveLogList(List<Magiclog> magiclogList,Map<Short,String> magicId_NameMap,Map<Integer,String> memberId_NameMap,String contextPath,String timeoffset) {
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
		Magiclog magiclog = null;
		MagiclogId magiclogId = null;
		MagicGiveOrReceiveLogVO magicGiveOrReceiveLogVO = null;
		String targetUsername = null;
		String username = null;
		for(int i=0;i<magiclogList.size();i++){
			magiclog = magiclogList.get(i);
			magiclogId = magiclog.getId();
			magicGiveOrReceiveLogVO = new MagicGiveOrReceiveLogVO();
			magicGiveOrReceiveLogVO.setMagicId(magiclogId.getMagicid().toString());
			magicGiveOrReceiveLogVO.setMagicName(magicId_NameMap.get(magiclogId.getMagicid()));
			magicGiveOrReceiveLogVO.setMagicNum(magiclogId.getAmount().toString());
			magicGiveOrReceiveLogVO.setOperatingTime(Common.gmdate(dateFormat, magiclogId.getDateline()));
			magicGiveOrReceiveLogVO.setUserId(magiclogId.getTargetuid().toString());
			targetUsername = memberId_NameMap.get(magiclogId.getTargetuid());
			username = memberId_NameMap.get(magiclogId.getUid());
			magicGiveOrReceiveLogVO.setUsername(targetUsername!=null?targetUsername:username);
			magicGiveOrReceiveLogVO.setContextPath(contextPath);
			magicGiveOrReceiveLogList.add(magicGiveOrReceiveLogVO);
		}
		dateFormat = null;
		magiclog = null;
		magiclogId = null;
		magicGiveOrReceiveLogVO = null;
		targetUsername = null;
		username = null;
	}
	public boolean isShowGiveOrReceiveLogList(){
		return magicGiveOrReceiveLogList.size()>0;
	}
	public void setMagicMarketLogVOList(List<Magiclog> magiclogList,Map<Short,String> magicId_NameMap,String util,String timeoffset) {
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
		Magiclog magiclog = null;
		MagiclogId magiclogId = null;
		MagicMarketLogVO magicMarketLogVO = null;
		for(int i=0;i<magiclogList.size();i++){
			magiclog = magiclogList.get(i);
			magiclogId = magiclog.getId();
			magicMarketLogVO = new MagicMarketLogVO();
			magicMarketLogVO.setMagicId(magiclogId.getMagicid().toString());
			magicMarketLogVO.setMagicName(magicId_NameMap.get(magiclogId.getMagicid()));
			magicMarketLogVO.setMagicNum(magiclogId.getAmount().toString());
			magicMarketLogVO.setOperatingTime(Common.gmdate(dateFormat, magiclogId.getDateline()));
			magicMarketLogVO.setPrice(magiclogId.getPrice().toString());
			magicMarketLogVO.setOperationInfo(magiclogId.getAction().toString());
			magicMarketLogVO.setUtil(util);
			magicMarketLogList.add(magicMarketLogVO);
		}
		magiclog = null;
		magiclogId = null;
		magicMarketLogVO = null;
		dateFormat = null;
	}
	public boolean isShowMagicMarketLogList(){
		return magicMarketLogList.size()>0;
	}
	public String getMultipage() {
		return multipage;
	}
	public void setMultipage(String multipage) {
		this.multipage = multipage;
	}
}
