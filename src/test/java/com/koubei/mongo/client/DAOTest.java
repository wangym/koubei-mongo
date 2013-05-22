/**
 * 
 */
package com.koubei.mongo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.koubei.mongo.client.domain.enumtype.MongoOrderByEnum;
import com.koubei.mongo.client.domain.valueobject.MongoSortVO;

/**
 * @author xuanyin
 * 
 */
public class DAOTest {

	/**
	 * 
	 */
	private static DAO dao;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test.application.xml");
		dao = (DAO) context.getBean("dao");
		if (null != dao) {
			dao.removeAll();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
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

	@Test
	public void testInsert() {

		System.out.println("=== testInsert ===");

		String _id = getRandom();

		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(27);
		pojo.setNick("insert");
		boolean result = dao.insert(pojo);

		assertEquals(true, result);
	}

	@Test
	public void testInsertList() {

		System.out.println("=== testInsertList ===");

		List<POJO> list = new ArrayList<POJO>();

		for (int i = 1; i <= 5; i++) {

			String _id = getRandom();

			POJO pojo = new POJO();
			pojo.set_id(_id);
			pojo.setAge(i);
			pojo.setNick("insertList");

			list.add(pojo);
		}

		boolean result = dao.insertList(list);

		assertEquals(true, result);
	}

	@Test
	public void testSave() {

		System.out.println("=== testSave ===");

		String _id = getRandom();

		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(27);
		pojo.setNick("save");
		boolean result = dao.save(pojo);

		assertEquals(true, result);
	}

	@Test
	public void testUpdateMapByCondition() {

		System.out.println("=== testUpdateMapByCondition ===");

		String _id = getRandom();
		int age = 27;
		String nick = "temp";
		String newNick = "updateMapByCondition";

		// 自行写入供随后修改
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", _id);
			condition.put("age", age);
			condition.put("nick", nick);

			Map<String, Object> target = new HashMap<String, Object>();
			target.put("nick", newNick);

			result = dao.updateMapByCondition(condition, target);
		}

		assertEquals(true, result);
	}

	@Test
	public void testUpdateMapByKV() {

		System.out.println("=== testUpdateMapByKV ===");

		String _id = getRandom();
		int age = 27;
		String nick = "temp";
		String newNick = "updateMapByKV";

		// 自行写入供随后修改
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			Map<String, Object> target = new HashMap<String, Object>();
			target.put("nick", newNick);

			result = dao.updateMapByKV("_id", _id, target);
		}

		assertEquals(true, result);
	}

	@Test
	public void testUpdateMapByPrimaryId() {

		System.out.println("=== testUpdateMapByPrimaryId ===");

		String _id = getRandom();
		int age = 27;
		String nick = "temp";
		String newNick = "updateMapByPrimaryId";

		// 自行写入供随后修改
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			Map<String, Object> target = new HashMap<String, Object>();
			target.put("nick", newNick);

			result = dao.updateMapByPrimaryId(_id, target);
		}

		assertEquals(true, result);
	}

	@Test
	public void testUpdateObjectByCondition() {

		System.out.println("=== testUpdateObjectByCondition ===");

		String _id = getRandom();
		int age = 27;
		String nick = "temp";
		String newNick = "updateObjectByCondition";

		// 自行写入供随后修改
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", _id);
			condition.put("age", age);
			condition.put("nick", nick);

			pojo = new POJO();
			pojo.set_id(_id);
			pojo.setAge(age);
			pojo.setNick(newNick);

			result = dao.updateObjectByCondition(condition, pojo);
		}

		assertEquals(true, result);
	}

	@Test
	public void testUpdateObjectByKV() {

		System.out.println("=== testUpdateObjectByKV ===");

		String _id = getRandom();
		int age = 27;
		String nick = "temp";
		String newNick = "updateObjectByKV";

		// 自行写入供随后修改
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			pojo = new POJO();
			pojo.set_id(_id);
			pojo.setAge(age);
			pojo.setNick(newNick);

			result = dao.updateObjectByKV("_id", _id, pojo);
		}

		assertEquals(true, result);
	}

	@Test
	public void testUpdateObjectByPrimaryId() {

		System.out.println("=== testUpdateObjectByPrimaryId ===");

		String _id = getRandom();
		int age = 27;
		String nick = "temp";
		String newNick = "updateObjectByPrimaryId";

		// 自行写入供随后修改
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			pojo = new POJO();
			pojo.set_id(_id);
			pojo.setAge(age);
			pojo.setNick(newNick);

			result = dao.updateObjectByPrimaryId(_id, pojo);
		}

		assertEquals(true, result);
	}

	@Test
	public void testIncrByPrimaryId() {

		System.out.println("=== testIncrByPrimaryId ===");

		String _id = getRandom();
		int age = 27;
		String nick = "incrByPrimaryId";

		// 自行写入供随后累加
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			// 执行累加,累加27,应为54
			result = dao.incrByPrimaryId(_id, "age", 27);
			if (result) {

				// 执行查询,若为54,累加成功
				POJO findPojo = dao.findOneByPrimaryId(_id);
				if (null != findPojo) {

					result = (54 == findPojo.getAge() ? true : false);
				}
			}
		}

		assertEquals(true, result);
	}

	@Test
	public void testDecrByPrimaryId() {

		System.out.println("=== testDecrByPrimaryId ===");

		String _id = getRandom();
		int age = 27;
		String nick = "decrByPrimaryId";

		// 自行写入供随后累加
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			// 执行累加,累加27,应为0
			result = dao.decrByPrimaryId(_id, "age", -27);
			if (result) {

				// 执行查询,若为0,累减成功
				POJO findPojo = dao.findOneByPrimaryId(_id);
				if (null != findPojo) {

					result = (0 == findPojo.getAge() ? true : false);
				}
			}
		}

		assertEquals(true, result);
	}

	@Test
	public void testRemoveByCondition() {

		System.out.println("=== testRemoveByCondition ===");

		String _id = getRandom();
		int age = 27;
		String nick = "removeByCondition";

		// 自行写入供随后删除
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", _id);
			condition.put("age", age);
			condition.put("nick", nick);

			result = dao.removeByCondition(condition);
		}

		assertEquals(true, result);
	}

	@Test
	public void testRemoveByKV() {

		System.out.println("=== testRemoveByKV ===");

		String _id = getRandom();
		int age = 27;
		String nick = "removeByKV";

		// 自行写入供随后删除
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			result = dao.removeByKV("_id", _id);
		}

		assertEquals(true, result);
	}

	@Test
	public void testRemoveByPrimaryId() {

		System.out.println("=== testRemoveByPrimaryId ===");

		String _id = getRandom();
		int age = 27;
		String nick = "removeByPrimaryId";

		// 自行写入供随后删除
		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.insert(pojo);
		if (result) {

			result = dao.removeByPrimaryId(_id);
		}

		assertEquals(true, result);
	}

	@Test
	public void testFindOneByCondition() {

		System.out.println("=== testFindOneByCondition ===");

		String _id = "1111111111";
		int age = 27;
		String nick = "findOneByCondition";

		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.save(pojo);
		if (result) {

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", _id);
			condition.put("age", age);
			condition.put("nick", nick);
			POJO ret = dao.findOneByCondition(condition);

			if (null != ret && _id.equalsIgnoreCase(ret.get_id())) {
				result = true;
			}

		}

		assertEquals(true, result);
	}

	@Test
	public void testFindOneByKV() {

		System.out.println("=== testFindOneByKV ===");

		String _id = "2222222222";
		int age = 27;
		String nick = "findOneByKV";

		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.save(pojo);
		if (result) {

			POJO ret = dao.findOneByKV("nick", nick);

			if (null != ret && _id.equalsIgnoreCase(ret.get_id())) {
				result = true;
			}

		}

		assertEquals(true, result);
	}

	@Test
	public void testFindOneByPrimaryId() {

		System.out.println("=== testFindOneByPrimaryId ===");

		String _id = "3333333333";
		int age = 27;
		String nick = "findOneByPrimaryId";

		POJO pojo = new POJO();
		pojo.set_id(_id);
		pojo.setAge(age);
		pojo.setNick(nick);
		boolean result = dao.save(pojo);
		if (result) {

			POJO ret = dao.findOneByPrimaryId(_id);

			if (null != ret && _id.equalsIgnoreCase(ret.get_id())) {
				result = true;
			}

		}

		assertEquals(true, result);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testFindAllByCondition() {

		System.out.println("=== testFindAllByCondition ===");

		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		// 排序条件
		MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("age");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

		List<POJO> list = dao.findAllByCondition(condition, sort);
		if (null != list && 0 < list.size()) {
			for (POJO pojo : list) {
				System.out.println(pojo.get_id() + "-" + pojo.getAge());
			}
		}

		assertEquals(true, true);
	}

	@Test
	public void testFindByCondition() {

		System.out.println("=== testFindByCondition ===");

		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("nick", "insertList");
		// 排序条件
		MongoSortVO sort = new MongoSortVO();
		sort.setPrimaryKey("age");
		sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

		List<POJO> list = dao.findByCondition(condition, sort, 1, 10);
		if (null != list && 0 < list.size()) {
			for (POJO pojo : list) {
				System.out.println(pojo.get_id() + "-" + pojo.getAge());
			}
		}

		assertEquals(true, true);
	}

	@Test
	public void testCountByCondition() {

		System.out.println("=== testCountByCondition ===");

		// 查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("nick", "insertList");

		long count = dao.countByCondition(condition);
		System.out.println(count);

		assertEquals(true, true);
	}
}
