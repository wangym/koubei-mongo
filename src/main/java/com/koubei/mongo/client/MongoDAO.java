/**
 * 
 */
package com.koubei.mongo.client;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.koubei.mongo.client.domain.valueobject.MongoDsnVO;
import com.koubei.mongo.client.domain.valueobject.MongoSortVO;
import com.koubei.mongo.client.etc.MongoConnector;
import com.koubei.mongo.client.etc.MongoUtil;
import com.koubei.mongo.client.exception.MongoException;
import com.koubei.mongo.client.query.MongoAdvancedQuery;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author yumin
 * 
 */
public class MongoDAO<T> implements InitializingBean, IMongoDAO<T> {

	/**
	 * 
	 */
	private MongoDsnVO dsn;
	private String database, collection;
	private T pojo;
	private Mongo mongo;
	private DB db;
	private DBCollection coll;

	/**
	 * 
	 */
	private static final Log LOG = LogFactory.getLog(MongoDAO.class);

	// ====================
	// public and protected
	// ====================

	/**
	 * 初始化本抽像类 同时支持Spring注入和子类的手工初始化
	 * 子类手工初始化:
	 * 1.先设定属性:dsn,database,collection,pojo;
	 * 2.再调用本方法即可.
	 * 若使用Spring注入,则配置bean时注入以上三个属性也可.
	 * 
	 * @throws UnknownHostException 
	 */
	protected void initializingBean() throws UnknownHostException {

		if (null == dsn) {
			throw new MongoException("koubei-mongo: check the datasource file!");
		}
		if (null == database || null == collection || null == pojo) {
			throw new MongoException("koubei-mongo: property 'database', 'collection', 'pojo' required!");
		}

		mongo = MongoConnector.getMongo(dsn);
		if (null == mongo) {
			throw new MongoException("koubei-mongo: connection failed!");
		}

		db = mongo.getDB(database);
		coll = db.getCollection(collection);
		if (null == db || null == collection) {
			throw new MongoException("koubei-mongo: property 'db', 'coll' initializing failed!");
		}
	}

	/**
	 * Spring注入口
	 */
	public void afterPropertiesSet() throws Exception {
		initializingBean();
	}

	@Override
	public boolean insert(T object) {

		// 返回结果
		boolean result = false;

		try {
			// 写入对象
			DBObject dbObject = MongoUtil.getDBObjectByObject(object);
			// 调试信息
			LOG.debug("insert - " + dbObject.toString());
			// 执行写入
			coll.insert(dbObject);
			result = getResult();
		} catch (Exception e) {
			LOG.debug("insert", e);
		}

		return result;
	}

	@Override
	public boolean insertList(List<T> args) {

		// 返回结果
		boolean result = false;

		try {
			// 写入对象
			List<DBObject> dbObjects = new ArrayList<DBObject>();
			// 批量转换
			if (null != args && 0 < args.size()) {
				for (T object : args) {
					dbObjects.add(MongoUtil.getDBObjectByObject(object));
				}
			}
			// 调试信息
			LOG.debug("insertList - " + dbObjects.toString());
			// 执行写入
			coll.insert(dbObjects);
			result = getResult();
		} catch (Exception e) {
			LOG.debug("insertList", e);
		}

		return result;
	}

	@Override
	public boolean save(T object) {

		// 返回结果
		boolean result = false;

		try {
			// 保存对象
			DBObject dbObject = MongoUtil.getDBObjectByObject(object);
			// 调试信息
			LOG.debug("save - " + dbObject.toString());
			// 执行保存
			coll.save(dbObject);
			result = getResult();
		} catch (Exception e) {
			LOG.debug("save", e);
		}

		return result;
	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {

		// 返回结果
		boolean result = false;

		try {
			// 修改条件
			DBObject query = MongoUtil.getDBObjectByObject(condition);
			// 新改对象
			DBObject dbObject = MongoUtil.getDBObjectByObject(target);
			dbObject.removeField("_id"); // 经实际测试,不允许改_id
			// 支持批量
			DBObject objNew = new BasicDBObject("$set", dbObject);
			// 调试信息
			LOG.debug("updateMapByCondition - query:" + query.toString() + " | objNew:" + objNew.toString());
			// 执行修改
			coll.update(query, objNew, false, true);
			result = getResult();
		} catch (Exception e) {
			LOG.debug("updateMapByCondition", e);
		}

		return result;
	}

	@Override
	public boolean updateMapByKV(String field, String value, Map<String, Object> target) {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put(field, value);

		return updateMapByCondition(condition, target);
	}

	@Override
	public boolean updateMapByPrimaryId(String value, Map<String, Object> target) {

		return updateMapByKV("_id", value, target);
	}

	@Override
	public boolean updateObjectByCondition(Map<String, Object> condition, T target) {

		// 返回结果
		boolean result = false;

		try {
			// 修改条件
			DBObject query = MongoUtil.getDBObjectByObject(condition);
			// 修改对象
			DBObject dbObject = MongoUtil.getDBObjectByObject(target);
			dbObject.removeField("_id"); // 经实际测试,不允许改_id
			// 支持批量
			DBObject objNew = new BasicDBObject("$set", dbObject);
			// 调试信息
			LOG.debug("updateObjectByCondition - query:" + query.toString() + " | objNew:" + objNew.toString());
			// 执行修改
			coll.update(query, objNew, false, true);
			result = getResult();
		} catch (Exception e) {
			LOG.debug("updateObjectByCondition", e);
		}

		return result;
	}

	@Override
	public boolean updateObjectByKV(String field, String value, T target) {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put(field, value);

		return updateObjectByCondition(condition, target);
	}

	@Override
	public boolean updateObjectByPrimaryId(String value, T target) {

		return updateObjectByKV("_id", value, target);
	}

	@Override
	public boolean incrByPrimaryId(String value, String column, int step) {

		// 参数判断
		if (0 > step) {
			return false;
		}

		// 执行累加
		return incOperateByKV("_id", value, column, step);
	}

	@Override
	public boolean decrByPrimaryId(String value, String column, int step) {

		// 参数判断
		if (0 < step) {
			return false;
		}

		// 执行累减
		return incOperateByKV("_id", value, column, step);
	}

	@Override
	public boolean removeByCondition(Map<String, Object> condition) {

		// 返回结果
		boolean result = false;

		try {
			// 删除条件
			DBObject query = MongoUtil.getDBObjectByObject(condition);
			// 调试信息
			LOG.debug("removeByCondition - " + query.toString());
			// 执行删除
			coll.remove(query);
			result = getResult();
		} catch (Exception e) {
			LOG.debug("removeByCondition", e);
		}

		return result;
	}

	@Override
	public boolean removeByKV(String field, String value) {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put(field, value);

		return removeByCondition(condition);
	}

	@Override
	public boolean removeByPrimaryId(String value) {

		return removeByKV("_id", value);
	}

	@Deprecated
	@Override
	public boolean removeAll() {

		// 返回结果
		boolean result = false;

		try {
			// 执行删除
			coll.remove(new BasicDBObject());
			result = getResult();
		} catch (Exception e) {
			LOG.debug("removeAll", e);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCondition(Map<String, Object> condition) {

		// 返回结果
		T object = null;

		try {
			// 查询条件
			DBObject query = MongoUtil.getDBObjectByObject(condition);
			// 调试信息
			LOG.debug("findOneByCondition - " + query.toString());
			// 执行查询
			DBObject dbObject = coll.findOne(query);
			if (null != dbObject) {
				// 转为原型
				object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
			}
		} catch (Exception e) {
			LOG.debug("findOneByCondition", e);
		}

		return object;
	}

	@Override
	public T findOneByKV(String field, String value) {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put(field, value);

		return findOneByCondition(condition);
	}

	@Override
	public T findOneByPrimaryId(String value) {

		return findOneByKV("_id", value);
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	@Override
	public List<T> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) {

		// 返回结果
		List<T> resultList = new ArrayList<T>();

		try {
			// 查询条件
			DBObject query = MongoUtil.getDBObjectByObject(condition);
			// 排序条件
			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);
			// 调试信息
			LOG.debug("findAllByCondition - query:" + query.toString() + " | order:" + order);
			// 执行查询
			DBCursor cursor = coll.find(query).sort(order);
			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {
					T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
					if (null != object) {
						resultList.add(object);
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("findAllByCondition", e);
		}

		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCondition(Map<String, Object> condition, MongoSortVO sort, int page, int size) {

		// 返回结果
		List<T> resultList = new ArrayList<T>();

		try {
			// 查询条件
			DBObject query = MongoUtil.getDBObjectByObject(condition);
			// 分页参数
			page = (0 < page ? page : 1);
			size = (0 < size ? size : 1);
			int skip = (page - 1) * size;
			// 排序条件
			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);
			// 调试信息
			LOG.debug("findByCondition - query:" + query.toString() + " | order:" + order);
			// 执行查询
			DBCursor cursor = coll.find(query).skip(skip).limit(size).sort(order);
			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {
					T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
					if (null != object) {
						resultList.add(object);
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("findByCondition", e);
		}

		return resultList;
	}

	@Override
	public long countByCondition(Map<String, Object> condition) {

		// 返回结果
		long count = -1;

		try {
			// 统计条件
			DBObject query = MongoUtil.getDBObjectByObject(condition);
			// 调试信息
			LOG.debug("countByCondition - " + query.toString());
			// 执行统计
			count = coll.count(query);
		} catch (Exception e) {
			LOG.debug("countByCondition", e);
		}

		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> advancedFindByCondition(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page, int size) {

		// 返回结果
		List<T> resultList = new ArrayList<T>();

		if (null == condition) {
			return resultList;
		}

		try {
			// 查询条件
			DBObject query = MongoUtil.getDBObjectByAdvancedQuery(condition);
			// 分页参数
			page = (0 < page ? page : 1);
			size = (0 < size ? size : 1);
			int skip = (page - 1) * size;
			// 排序条件
			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);
			// 调试信息
			LOG.debug("advancedFindByCondition - query:" + query.toString() + " | order:" + order);
			// 执行查询
			DBCursor cursor = coll.find(query).skip(skip).limit(size).sort(order);
			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {
					T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
					if (null != object) {
						resultList.add(object);
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("advancedFindByCondition", e);
		}

		return resultList;
	}

	@Override
	public long advancedCountByCondition(Map<String, MongoAdvancedQuery> condition) {

		// 返回结果
		long count = -1;

		if (null == condition) {
			return count;
		}

		try {
			// 统计条件
			DBObject query = MongoUtil.getDBObjectByAdvancedQuery(condition);
			// 调试信息
			LOG.debug("advancedCountByCondition - " + query.toString());
			// 执行统计
			count = coll.count(query);
		} catch (Exception e) {
			LOG.debug("advancedCountByCondition", e);
		}

		return count;
	}

	@Override
	public void close() {

		mongo.close();
	}

	// ====================
	// private methods
	// ====================

	/**
	 * 获取操作返回值 适用增删改操作
	 * 
	 * @return true|false
	 */
	private boolean getResult() {

		// 返回结果
		boolean result = false;

		DBObject msg = db.getLastError();
		if (null != msg) {
			String err = (String) msg.get("err");
			if (null == err || 0 == err.length()) {
				result = true;
			} else {
			}
		}

		return result;
	}

	/**
	 * $inc操作,累加或累减(单条件版)
	 * 
	 * @param field 查询字段名
	 * @param value 查询字段值
	 * @param column 被更改字段名
	 * @param step 被更改步长值(若为正数则累加反之累减)
	 * @return true|false
	 */
	private boolean incOperateByKV(String field, String value, String column, int step) {

		// 返回结果
		boolean result = false;

		try {
			// 修改条件
			DBObject query = new BasicDBObject();
			query.put(field, value);
			// 修改对象
			DBObject dbObject = new BasicDBObject();
			dbObject.put(column, step);
			DBObject objNew = new BasicDBObject("$inc", dbObject);
			// 调试信息
			LOG.debug("incOperateByKV - query:" + query.toString() + " | objNew:" + objNew.toString());
			// 执行修改
			coll.update(query, objNew, false, true);
			result = getResult();
		} catch (Exception e) {
			LOG.debug("incOperateByKV", e);
		}

		return result;
	}

	// ====================
	// getters and setters
	// ====================

	/**
	 * 
	 */
	public void setDsn(MongoDsnVO dsn) {
		this.dsn = dsn;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void setPojo(T pojo) {
		this.pojo = pojo;
	}
}
