package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.PolloptionsDao;
import cn.jsprun.domain.Polloptions;
import cn.jsprun.utils.BeanFactory;
public class PolloptionsService {
	public boolean insertPolloptions(Polloptions polloptions){
		return ((PolloptionsDao)BeanFactory.getBean("polloptionsDao")).insertPolloptions(polloptions);
	}
	public boolean deletePolloptions(Polloptions polloptions){
		return ((PolloptionsDao)BeanFactory.getBean("polloptionsDao")).deletePolloptions(polloptions);
	}
	public boolean updatePolloptions(Polloptions polloptions){
		return ((PolloptionsDao)BeanFactory.getBean("polloptionsDao")).updatePolloptions(polloptions);
	}
	public List<Polloptions> findPolloptionsBytid(int tid){
		return ((PolloptionsDao)BeanFactory.getBean("polloptionsDao")).findPolloptionsBytid(tid);
	}
	public Polloptions findPolloptionsById(int optionid){
		return ((PolloptionsDao)BeanFactory.getBean("polloptionsDao")).findPolloptionsById(optionid);
	}
}
