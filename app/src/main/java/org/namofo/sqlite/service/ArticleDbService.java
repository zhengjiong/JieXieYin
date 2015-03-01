package org.namofo.sqlite.service;

import org.namofo.bean.Article;
import org.namofo.sqlite.dao.ArticleDao.Properties;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 
 * @author zhengjiong
 * 2014-7-5 下午5:17:40
 */
public class ArticleDbService extends AbstractDbService<Article>{

	public ArticleDbService(AbstractDao<Article, Long> dao){
		super(dao);
		
	}
	
	@Override
	public Article load(Long id) {
		return dao.load(id);
	}

	@Override
	public List<Article> loadAll() {
		return dao.loadAll();
	}

	public List<Article> loadAllByCategory(Long id){
		
		QueryBuilder<Article> builder = dao.queryBuilder();
		builder.where(Properties.Category.eq(id)).orderDesc(Properties.Id);
		return builder.list();
	}
}
