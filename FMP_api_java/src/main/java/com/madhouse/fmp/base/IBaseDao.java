package com.madhouse.fmp.base;

import org.springframework.dao.DataAccessException;

/**
 * 数据库公共类Dao类接口
 */
public interface IBaseDao<T,E> {
	
	/**
	 * 保存对象到数据库表中
	 * @param t 要保存的对象
	 * @throws DataAccessException DataAccessException
	 */
	T add(T entity) throws DataAccessException;
	
	/**
	 * 更新对象到数据库表中
	 * @param t 要保存的对象
	 * @throws DataAccessException DataAccessException
	 */
	T update(T entity) throws DataAccessException;
	
	/**
	 * 根据对象主键删除对应的对象
	 * @param t 对象的
	 * @throws DataAccessException DataAccessException
	 */
	T delete(T entity) throws DataAccessException;
}
