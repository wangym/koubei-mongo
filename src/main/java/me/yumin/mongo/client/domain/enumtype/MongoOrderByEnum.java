/**
 * 
 */
package me.yumin.mongo.client.domain.enumtype;

/**
 * @author yumin
 * 
 */
public enum MongoOrderByEnum {

	DESC(-1), ASC(1);

	/**
	 * 
	 */
	private int value = 0;

	/**
	 * 
	 * @param value
	 */
	MongoOrderByEnum(int value) {
		this.value = value;
	}

	/**
	 * 
	 * @return
	 */
	public int getValue() {
		return this.value;
	}
}
