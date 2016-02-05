/**
 * 
 */
package me.yumin.mongo.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import me.yumin.mongo.client.query.MongoAdvancedQuery;
import me.yumin.mongo.client.query.impl.MongoBetweenQuery;
import me.yumin.mongo.client.query.impl.MongoEqualQuery;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author xuanyin
 * 
 */
public class AdvancedQueryTest {

	/**
	 * 
	 */
	private static UserDAO dao;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test.application.xml");
		dao = (UserDAO) context.getBean("dao");
		if (null != dao) {
			// dao.removeAll();
		}
	}

	/**
	 * 
	 * @return
	 */
	private String getRandom() {

		long time = new Date().getTime() / 1000;
		long random = (long) (Math.random() * (1 - 99999999)) + 99999999;
		long result = time + random;

		return result + "";
	}

	// @Test
	public void testSimpleInit() {

		System.out.println("=== testSimpleInit ===");

		String random = getRandom();
		Date now = new Date();

		POJO pojo = new POJO();
		pojo.set_id(random);
		pojo.setAge(27);
		pojo.setCreateDate(now);
		pojo.setModifyDate(now);
		pojo.setNick("insert");
		boolean result = dao.insert(pojo);

		assertEquals(true, result);
	}

	@Test
	public void testAdvancedQuery() {

		System.out.println("=== testAdvancedQuery ===");

		// between
		MongoBetweenQuery between = new MongoBetweenQuery();
		between.setGreaterThan(1);
		between.setLessThanEqualTo(27);

		// equal
		MongoEqualQuery equal = new MongoEqualQuery();
		equal.setEqualto("1333715943");

		// condition
		Map<String, MongoAdvancedQuery> condition = new HashMap<String, MongoAdvancedQuery>();
		condition.put("age", between);
		condition.put("_id", equal);

		List<POJO> list = dao.advancedFindByCondition(condition, null, 1, 10);
		if (null != list && 0 < list.size()) {
			for (POJO pojo : list) {
				System.out.println(pojo.get_id() + "-" + pojo.getAge());
			}
		}

		assertEquals(true, true);
	}
}
