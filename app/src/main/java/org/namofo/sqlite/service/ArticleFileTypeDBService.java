package org.namofo.sqlite.service;

import org.namofo.bean.ArticleFileType;

import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * 
 * @author zhengjiong
 * 2014-7-5 下午5:55:34
 */
public class ArticleFileTypeDBService extends AbstractDbService<ArticleFileType>{

	public ArticleFileTypeDBService(AbstractDao<ArticleFileType, Long> dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArticleFileType load(Long id) {
		return dao.load(id);
	}

	@Override
	public List<ArticleFileType> loadAll() {
		return dao.loadAll();
	}

}
