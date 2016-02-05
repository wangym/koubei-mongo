/**
 * 
 */
package me.yumin.mongo.client.etc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import me.yumin.mongo.client.domain.enumtype.MongoOrderByEnum;
import me.yumin.mongo.client.domain.valueobject.MongoSortVO;
import me.yumin.mongo.client.query.MongoAdvancedQuery;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author yumin
 * 
 */
@SuppressWarnings("rawtypes")
public class MongoUtil {

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static DBObject getDBObjectByAdvancedQuery(Map<String, MongoAdvancedQuery> map) {

		DBObject result = null;

		if (null != map && 0 < map.size()) {
			Iterator it = map.entrySet().iterator();
			if (null != it) {
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					MongoAdvancedQuery value = (MongoAdvancedQuery) entry.getValue();
					if (null != value) {
						result = new BasicDBObject();
						result.put(key, value.getQueryObject());
					}
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @param sort
	 * @return
	 */
	public static DBObject getDBObjectByMongoSort(MongoSortVO sort) {

		DBObject result = null;

		if (null != sort) {
			String primaryKey = sort.getPrimaryKey();
			MongoOrderByEnum primaryOrderBy = sort.getPrimaryOrderBy();
			if (null != primaryKey && 0 < primaryKey.length() && null != primaryOrderBy) {
				result = new BasicDBObject();
				result.put(primaryKey, primaryOrderBy.getValue());
			}
		}

		return result;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static DBObject getDBObjectByObject(Map<String, Object> map) {

		DBObject result = null;

		if (null != map && 0 < map.size()) {
			Iterator it = map.entrySet().iterator();
			if (null != it) {
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					Object value = entry.getValue();
					result = new BasicDBObject();
					result.put(key, value);
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static DBObject getDBObjectByObject(Object object) throws IOException {

		DBObject result = null;

		String json = objectToJson(object);
		result = (DBObject) jsonToObject(json, BasicDBObject.class);

		return result;
	}

	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public static Object getObjectByDBObject(DBObject source, Object target) throws IOException {

		Object result = null;

		String json = objectToJson(source);
		Class beanClass = target.getClass();
		result = jsonToObject(json, beanClass);

		return result;
	}

	/**
	 * 
	 * @param json
	 * @param beanClass
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@SuppressWarnings("unchecked")
	public static Object jsonToObject(String json, Class beanClass) throws JsonParseException, JsonMappingException, IOException {

		Object object = null;

		ObjectMapper mapper = MongoMapper.getInstance();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		object = mapper.readValue(json, beanClass);

		return object;
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static String objectToJson(Object object) throws IOException {

		String json = null;

		ObjectMapper mapper = MongoMapper.getInstance();
		StringWriter writer = new StringWriter();
		JsonGenerator generator = new JsonFactory().createJsonGenerator(writer);
		mapper.writeValue(generator, object);
		generator.close();
		json = writer.toString();
		writer.close();

		return json;
	}
}
