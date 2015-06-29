package com.madhouse.fmp.base;

import java.io.Serializable;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;


public class BaseDao<T, E extends Serializable> implements IBaseDao<T, E> {
	
	public static Logger logger = LoggerFactory.getLogger(BaseDao.class);
	@Resource(name="sqlSession")
	protected SqlSessionTemplate session;
	public T add(T entity) throws DataAccessException {
		try {
			session.insert(entity.getClass().getName() + ".save",
					entity);
			return entity;
		} catch (RuntimeException re) {
			logger.error("save " + entity.getClass().getName() + " failed :{}",
					re);
			throw re;
		}
	}

	public T update(T entity) throws DataAccessException {
		try {
			this.session.update(
					entity.getClass().getName() + ".update", entity);
			return entity;
		} catch (RuntimeException re) {
			logger.error("update " + entity.getClass().getName()
					+ " failed :{}", re);
			throw re;
		}
	}

	public T delete(T entity) throws DataAccessException {
		try {
			this.session.delete(
					entity.getClass().getName() + ".delete", entity);
			return entity;
		} catch (RuntimeException re) {
			logger.error("delete " + entity.getClass().getName()
					+ " failed :{}", re);
			throw re;
		}
	}
}
