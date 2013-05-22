/**
 * 
 */
package com.koubei.mongo.client.etc;

import java.net.UnknownHostException;
import java.util.List;
import com.koubei.mongo.client.domain.valueobject.MongoDsnVO;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

/**
 * @author yumin
 * 
 */
@SuppressWarnings("deprecation")
public class MongoConnector {

	/**
	 * 
	 * @param dsn
	 * @param database
	 * @return
	 * @throws UnknownHostException 
	 */
	public static Mongo getMongo(MongoDsnVO dsn, String database) throws UnknownHostException {

		Mongo mongo = null;

		if (null != dsn && null != database && 0 < database.length()) {
			List<String> servers = dsn.getServers();
			if (null != servers && 0 < servers.size()) {
				// 载入选项
				Boolean autoConnectRetry = dsn.getAutoConnectRetry();
				Integer connectionsPerHost = dsn.getConnectionsPerHost();
				Integer connectTimeout = dsn.getConnectTimeout();
				Integer maxWaitTime = dsn.getMaxWaitTime();
				Integer socketTimeout = dsn.getSocketTimeout();
				Integer threadsAllowedToBlockForConnectionMultiplier = dsn.getThreadsAllowedToBlockForConnectionMultiplier();
				// 设定选项
				MongoOptions options = new MongoOptions();
				options.autoConnectRetry = autoConnectRetry;
				options.connectionsPerHost = connectionsPerHost;
				options.connectTimeout = connectTimeout;
				options.maxWaitTime = maxWaitTime;
				options.socketTimeout = socketTimeout;
				options.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
				// 联机模式
				DBAddress left = new DBAddress(servers.get(0) + "/" + database);
				if (1 == servers.size()) {
					// Single Mode
					mongo = new Mongo(left, options);
				} else if (2 == servers.size()) {
					// Replica Sets
					DBAddress right = new DBAddress(servers.get(1) + "/" + database);
					mongo = new Mongo(left, right, options);
				}
			}
		}

		return mongo;
	}
}
