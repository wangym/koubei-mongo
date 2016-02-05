/**
 * 
 */
package me.yumin.mongo.client.query.impl;

import me.yumin.mongo.client.query.MongoAdvancedQuery;

/**
 * @author yumin
 * 
 */
public class MongoEqualQuery extends MongoAdvancedQuery {

	/**
	 * 
	 */
	private Object equalto;

	@Override
	public Object getQueryObject() {
		return equalto;
	}

	/**
	 * 
	 */
	public Object getEqualto() {
		return equalto;
	}

	public void setEqualto(Object equalto) {
		this.equalto = equalto;
	}
}
