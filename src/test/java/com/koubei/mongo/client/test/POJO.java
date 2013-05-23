/**
 * 
 */
package com.koubei.mongo.client.test;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuanyin
 * 
 */
public class POJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8181241456104322863L;

	/**
	 * 请注意,若在POJO中设有此属性, 
	 * 请自行确保唯一性,反之自动生成
	 */
	private String _id;

	/**
	 * 
	 */
	private String nick;

	/**
	 * 
	 */
	private int age;

	/**
	 * 
	 */
	private Date createDate;

	/**
	 * 
	 */
	private Date modifyDate;

	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param id the _id to set
	 */
	public void set_id(String id) {
		_id = id;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
