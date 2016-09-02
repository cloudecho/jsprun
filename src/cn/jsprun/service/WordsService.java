package cn.jsprun.service;
import java.util.List;
import java.util.Map;
import cn.jsprun.dao.WordsDao;
import cn.jsprun.domain.Words;
import cn.jsprun.utils.BeanFactory;
public class WordsService {
	public Map getAllWords(int page) {
		return ((WordsDao) BeanFactory.getBean("wordsDao")).getAllWords(page);
	}
	public Integer updateWordsList(List<Words> wordsList,
			List<Words> updateReplaceList) {
		return ((WordsDao) BeanFactory.getBean("wordsDao")).updateWordsList(wordsList, updateReplaceList);
	}
	public Integer delteCollection(String[] ids) {
		return ((WordsDao) BeanFactory.getBean("wordsDao")).delteCollection(ids);
	}
	public boolean saveWords(Words words) {
		if (words != null)
			return ((WordsDao) BeanFactory.getBean("wordsDao")).save(words);
		return true;
	}
	public Integer[] saveWordsList(List<Words> wordsList) {
		Integer[] countNumber = new Integer[] { 0, 0 };
		for (int i = 0; i < wordsList.size(); i++) {
			if (((WordsDao) BeanFactory.getBean("wordsDao")).findByFindPorperty(wordsList.get(i))) {
				countNumber[1]++;
			} else {
				((WordsDao) BeanFactory.getBean("wordsDao")).save(wordsList.get(i));
				countNumber[0]++;
			}
		}
		return countNumber;
	}
	public Integer deleteAndSave(List<Words> wordsList) {
		int saveNumbe = 0;
		if (wordsList != null && wordsList.size() > 0) {
			try {
				for (int i = 0; i < wordsList.size(); i++) {
					((WordsDao) BeanFactory.getBean("wordsDao")).save(wordsList.get(i));
					saveNumbe++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				saveNumbe = 0;
			}
		}
		return saveNumbe;
	}
	public String downWordsAll() {
		return ((WordsDao) BeanFactory.getBean("wordsDao")).downWordsAll();
	}
	public Integer[] updateAndSave(List<Words> wordsList) {
		Integer[] countNumber = new Integer[] { 0, 0 };
		for (int i = 0; i < wordsList.size(); i++) {
			if (((WordsDao) BeanFactory.getBean("wordsDao")).findByFindPorperty(wordsList.get(i))) {
				((WordsDao) BeanFactory.getBean("wordsDao")).updateWords(wordsList.get(i));
				countNumber[1]++;
			} else {
				((WordsDao) BeanFactory.getBean("wordsDao")).save(wordsList.get(i));
				countNumber[0]++;
			}
		}
		return countNumber;
	}
}
