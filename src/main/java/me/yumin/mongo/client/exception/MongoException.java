/**
 * 
 */
package me.yumin.mongo.client.exception;

/**
 * @author yumin
 * 
 */
public class MongoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5646510831551284097L;

	/**
	 * 
	 */
	public MongoException() {
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public MongoException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public MongoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param cause
	 */
	public MongoException(Throwable cause) {
		super(cause);
	}
}
