package org.namofo.sqlite.service;

import java.util.List;

import org.namofo.bean.ArticleCategory;

import de.greenrobot.dao.AbstractDao;

/**
 * 
 * @author zhengjiong
 * 2014-7-5 下午5:55:34
 */
public class ArticleCategoryDBService extends AbstractDbService<ArticleCategory>{

	public ArticleCategoryDBService(AbstractDao<ArticleCategory, Long> dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArticleCategory load(Long id) {
		// TODO Auto-generated method stub
		return dao.load(id);
	}

	@Override
	public List<ArticleCategory> loadAll() {
		return dao.loadAll();
	}

}
