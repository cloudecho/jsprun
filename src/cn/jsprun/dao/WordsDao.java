package cn.jsprun.dao;
import java.util.List;
import java.util.Map;
import cn.jsprun.domain.Words;
public interface WordsDao {
	public Map getAllWords(int page);
	public Integer updateWordsList(List<Words> wordsList,
			List<Words> updateReplaceList);
	public Integer delteCollection(String[] ids);
	public Integer deleteAll() throws Exception;
	public boolean save(Words words);
	public String downWordsAll();
	public boolean findByFindPorperty(Words words);
	public Integer updateWords(Words words);
}
