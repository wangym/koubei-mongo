/**
 * 
 */
package com.koubei.mongo.client;

import java.util.List;
import java.util.Map;
import com.koubei.mongo.client.domain.valueobject.MongoSortVO;
import com.koubei.mongo.client.query.MongoAdvancedQuery;

/**
 * @author yumin
 * 
 */
public interface IMongoDAO<T> {

	/**
	 * 写入一条新记录("_id"不允许重复)
	 * 
	 * @param object 任意pojo,若不传"_id"则自动生成
	 * @return 写入成功true|或失败false
	 */
	public boolean insert(T object);

	/**
	 * 批量写入新记录("_id"不允许重复)
	 * 
	 * @param objects 任意pojo列表,若不传"_id"则自动生成
	 * @return 写入成功true|或失败false
	 */
	public boolean insertList(List<T> objects);

	/**
	 * 保存一条新记录("_id"若存在则更新反之则写入)
	 * 
	 * @param object 任意pojo,若不传"_id"则自动生成
	 * @return 保存成功true|或失败false
	 */
	public boolean save(T object);

	/**
	 * 自定义条件修改(多条件版,将满足系列条件的记录全部修改)
	 * 类似:UPDATE tblname SET xx=xx... WHERE key1=value1 AND key2=value2...
	 * 注:此方法只会修改指定项,只改少数字段时推荐
	 * 
	 * @param condition key:查询的字段名,value:字段的匹配值
	 * @param target 新修改对象,只需传想修改的字段即可
	 * @return 修改成功true|或失败false
	 */
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target);

	/**
	 * 自定义条件修改(单条件版,将满足系列条件的记录全部修改)
	 * 类似:UPDATE tblname SET xx=xx... WHERE field=value
	 * 注:此方法只会修改指定项,只改少数字段时推荐
	 * 
	 * @param field 查询的字段名
	 * @param value 字段的匹配值
	 * @param target 新修改对象,只需传想修改的字段即可
	 * @return 修改成功true|或失败false
	 */
	public boolean updateMapByKV(String field, String value, Map<String, Object> target);

	/**
	 * 按"_id"条件修改(将满足字段"_id"等于"value"的记录全部修改)
	 * 类似:UPDATE tblname SET xx=xx... WHERE _id=value
	 * 注:此方法只会修改指定项,只改少数字段时推荐
	 * 
	 * @param value "_id"的匹配值
	 * @param target 新修改对象,只需传想修改的字段即可
	 * @return 修改成功true|或失败false
	 */
	public boolean updateMapByPrimaryId(String value, Map<String, Object> target);

	/**
	 * 自定义条件修改(多条件版,将满足系列条件的记录全部修改)
	 * 类似:UPDATE tblname SET xx=xx... WHERE key1=value1 AND key2=value2...
	 * 注:此方法会修改所有参数
	 * 
	 * @param condition key:查询的字段名,value:字段的匹配值
	 * @param object 新修改对象,任意pojo但需完整对象,而不只设想改的字段
	 * @return 修改成功true|或失败false
	 */
	public boolean updateObjectByCondition(Map<String, Object> condition, T target);

	/**
	 * 自定义条件修改(单条件版,将满足单个条件的记录全部修改)
	 * 类似:UPDATE tblname SET xx=xx... WHERE field=value
	 * 注:此方法会修改所有参数
	 * 
	 * @param field 查询的字段名
	 * @param value 字段的匹配值
	 * @param object 新修改对象,任意pojo但需完整对象,而不只设想改的字段
	 * @return 修改成功true|或失败false
	 */
	public boolean updateObjectByKV(String field, String value, T target);

	/**
	 * 按"_id"条件修改(将满足字段"_id"等于"value"的记录全部修改)
	 * 类似:UPDATE tblname SET xx=xx... WHERE _id=value
	 * 注:此方法会修改所有参数
	 * 
	 * @param value "_id"的匹配值
	 * @param object 新修改对象,任意pojo但需完整对象,而不只设想改的字段
	 * @return 修改成功true|或失败false
	 */
	public boolean updateObjectByPrimaryId(String value, T target);

	/**
	 * 按"_id"条件累加
	 * 类似:UPDATE tblname field=field+step SET WHERE _id=value
	 * 
	 * @param value "_id"的匹配值
	 * @param column 对该字段进行累加
	 * @param step 累加步长(应传正数)
	 * @return 累加成功true|或失败false
	 */
	public boolean incrByPrimaryId(String value, String column, int step);

	/**
	 * 按"_id"条件累减
	 * 类似:UPDATE tblname field=field-step SET WHERE _id=value
	 * 
	 * @param value "_id"的匹配值
	 * @param column 对该字段进行累减
	 * @param step 累减步长(应传负数)
	 * @return 累减成功true|或失败false
	 */
	public boolean decrByPrimaryId(String value, String column, int step);

	/**
	 * 自定义条件删除(多条件版,将满足系列条件的记录全部删除)
	 * 类似:DELETE FROM tblname WHERE key1=value1 AND key2=value2...
	 * 
	 * @param condition key:查询的字段名,value:字段的匹配值
	 * @return 删除成功true|或失败false
	 */
	public boolean removeByCondition(Map<String, Object> condition);

	/**
	 * 自定义条件删除(单条件版,将满足单个条件的记录全部删除)
	 * 类似:DELETE FROM tblname WHERE field=value
	 * 
	 * @param field 查询的字段名
	 * @param value 字段的匹配值
	 * @return 删除成功true|或失败false
	 */
	boolean removeByKV(String field, String value);

	/**
	 * 按"_id"条件删除(将满足字段"_id"等于"value"的记录全部删除)
	 * 类似:DELETE FROM tblname WHERE _id=value
	 * 
	 * @param value "_id"的匹配值
	 * @return 删除成功true|或失败false
	 */
	public boolean removeByPrimaryId(String value);

	/**
	 * 清空当前数据集的所有记录(请慎用)
	 * 
	 * @return
	 */
	@Deprecated
	public boolean removeAll();

	/**
	 * 自定义查询单行(多条件版,将满足系列条件的单行记录返回)
	 * 类似:SELECT * FROM tblname WHERE key1=value1 AND key2=value2... LIMIT 1
	 * 
	 * @param condition key:查询的字段名,value:字段的匹配值
	 * @return 业务对象
	 */
	public T findOneByCondition(Map<String, Object> condition);

	/**
	 * 自定义查询单行(单条件版,将满足单个条件的单行记录返回)
	 * 类似:SELECT * FROM tblname WHERE field=value LIMIT 1
	 * 
	 * @param field 查询的字段名
	 * @param value 字段的匹配值
	 * @return 业务对象
	 */
	public T findOneByKV(String field, String value);

	/**
	 * 按"_id"条件查询单行(将满足字段"_id"等于"value"的单行记录返回)
	 * 类似:SELECT * FROM tblname WHERE _id=value LIMIT 1
	 * 
	 * @param value "_id"的匹配值
	 * @return 业务对象
	 */
	public T findOneByPrimaryId(String value);

	/**
	 * 自定义查询全部(多条件版,将满足系列条件的多行记录返回,支持排序)
	 * 类似:SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key DESC|ASC
	 * 
	 * @param condition key:查询的字段名,value:字段的匹配值
	 * @param sort 排序对象
	 * @return 业务对象
	 */
	@Deprecated
	public List<T> findAllByCondition(Map<String, Object> condition, MongoSortVO sort);

	/**
	 * 自定义查询列表(多条件版,将满足系列条件的多行记录返回,支持排序及分页)
	 * 类似:SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition key:查询的字段名,value:字段的匹配值
	 * @param sort 排序对象
	 * @param page 当前页码
	 * @param size 每页数量
	 * @return 业务对象
	 */
	public List<T> findByCondition(Map<String, Object> condition, MongoSortVO sort, int page, int size);

	/**
	 * 统计满足条件的记录数(多条件版)
	 * 类似:SELECT COUNT(*) FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition key:查询的字段名,value:字段的匹配值
	 * @return 满条足件的记录数
	 */
	public long countByCondition(Map<String, Object> condition);

	/**
	 * 自定义查询列表(多条件高级版,将满足系列条件的多行记录返回,支持排序及分页)
	 * 高级版支持丰富查询条件
	 * 类似:SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition key:查询的字段名,value:AdvancedQuery
	 * @param sort 排序对象
	 * @param page 当前页码
	 * @param size 每页数量
	 * @return 业务对象
	 */
	public List<T> advancedFindByCondition(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page, int size);

	/**
	 * 统计满足条件的记录数(多条件高级版)
	 * 类似:SELECT COUNT(*) FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition key:查询的字段名,value:AdvancedQuery
	 * @return 满条足件的记录数
	 */
	public long advancedCountByCondition(Map<String, MongoAdvancedQuery> condition);

    /**
     * closes the underlying connector, which in turn closes all open connections.
     * Once called, this Mongo instance can no longer be used.
     */
	public void close();
}
