/**
 * 
 */
package me.yumin.mongo.client.query.impl;

import me.yumin.mongo.client.query.MongoAdvancedQuery;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author yumin
 * 
 */
public class MongoBetweenQuery extends MongoAdvancedQuery {

	/**
	 * 
	 */
	private Object greaterThan;
	private Object lessThan;
	private Object greaterThanEqualTo;
	private Object lessThanEqualTo;

	@Override
	public DBObject getQueryObject() {

		DBObject result = new BasicDBObject();

		if (null != greaterThan) {
			result.put("$gt", greaterThan);
		}
		if (null != lessThan) {
			result.put("$lt", lessThan);
		}
		if (null != greaterThanEqualTo) {
			result.put("$gte", greaterThanEqualTo);
		}
		if (null != lessThanEqualTo) {
			result.put("$lte", lessThanEqualTo);
		}

		return result;
	}

	/**
	 * 
	 */
	public Object getGreaterThan() {
		return greaterThan;
	}

	public void setGreaterThan(Object greaterThan) {
		this.greaterThan = greaterThan;
	}

	public Object getLessThan() {
		return lessThan;
	}

	public void setLessThan(Object lessThan) {
		this.lessThan = lessThan;
	}

	public Object getGreaterThanEqualTo() {
		return greaterThanEqualTo;
	}

	public void setGreaterThanEqualTo(Object greaterThanEqualTo) {
		this.greaterThanEqualTo = greaterThanEqualTo;
	}

	public Object getLessThanEqualTo() {
		return lessThanEqualTo;
	}

	public void setLessThanEqualTo(Object lessThanEqualTo) {
		this.lessThanEqualTo = lessThanEqualTo;
	}
}
