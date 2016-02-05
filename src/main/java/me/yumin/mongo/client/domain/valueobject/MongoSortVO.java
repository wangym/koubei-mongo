/**
 * 
 */
package me.yumin.mongo.client.domain.valueobject;

import java.io.Serializable;
import me.yumin.mongo.client.domain.enumtype.MongoOrderByEnum;

/**
 * @author yumin
 * 
 */
public class MongoSortVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1017613392822734690L;

	/**
	 * 
	 */
	private String primaryKey;
	private MongoOrderByEnum primaryOrderBy;

	/**
	 * 
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public MongoOrderByEnum getPrimaryOrderBy() {
		return primaryOrderBy;
	}

	public void setPrimaryOrderBy(MongoOrderByEnum primaryOrderBy) {
		this.primaryOrderBy = primaryOrderBy;
	}
}
