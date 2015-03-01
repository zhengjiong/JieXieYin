package org.namofo.sqlite.service;

import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * 
 * @author zhengjiong
 * 2014-7-5 下午5:19:08
 */
public abstract class AbstractDbService<T> {
	protected AbstractDao<T, Long> dao;
	
	protected AbstractDbService(AbstractDao<T, Long> dao){
		this.dao = dao;
	}
	
	protected void delete(Long id){
		dao.deleteByKey(id);
	}
	
	protected void delete(T t){
		dao.delete(t);
	}

	protected abstract T load(Long id);
	protected abstract List<T> loadAll();
}
