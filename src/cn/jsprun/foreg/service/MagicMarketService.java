package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.MagicMarketDao;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.utils.BeanFactory;
public class MagicMarketService {
	public List<Magicmarket> getAllMagicFormMarket(int firstNmu,int maxNum){
		return ((MagicMarketDao)BeanFactory.getBean("magicMarketDao")).getAllMagicFromMarket(firstNmu, maxNum);
	}
	public List<Magicmarket> getMagicFromMarketByUid(Integer uid,int firstNmu,int maxNum){
		return ((MagicMarketDao)BeanFactory.getBean("magicMarketDao")).getMagicFromMarketByUid(uid, firstNmu, maxNum);
	}
	public List<Magicmarket> getMagicFormMarketByMagicId(Short magicId,String orderby,String ascDesc,int firstNmu,int maxNum){
		return ((MagicMarketDao)BeanFactory.getBean("magicMarketDao")).getMagicFormMarketByMagicId(magicId, orderby, ascDesc, firstNmu, maxNum);
	}
	public Magicmarket getMagicmarketById(Short magicmarketId){
		return ((MagicMarketDao)BeanFactory.getBean("magicMarketDao")).getMagicmarketById(magicmarketId);
	}
}
