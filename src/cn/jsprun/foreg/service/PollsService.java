package cn.jsprun.foreg.service;
import cn.jsprun.dao.PollsDao;
import cn.jsprun.domain.Polls;
import cn.jsprun.utils.BeanFactory;
public class PollsService {
	public boolean insertPolls(Polls polls){
		return ((PollsDao)BeanFactory.getBean("pollsDao")).insertPolls(polls);
	}
	public boolean updatePolls(Polls polls){
		return ((PollsDao)BeanFactory.getBean("pollsDao")).updatePolls(polls);
	}
	public boolean deletePolls(Polls polls){
		return ((PollsDao)BeanFactory.getBean("pollsDao")).deletePolls(polls);
	}
	public Polls findPollsBytid(int tid){
		return ((PollsDao)BeanFactory.getBean("pollsDao")).findPollsBytid(tid);
	}
}
