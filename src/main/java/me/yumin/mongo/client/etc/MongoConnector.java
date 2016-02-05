/**
 * 
 */
package me.yumin.mongo.client.etc;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import me.yumin.mongo.client.domain.valueobject.MongoDsnVO;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

/**
 * @author yumin
 * 
 */
public class MongoConnector {

	/**
	 * 
	 * @param dsn
	 * @return
	 * @throws UnknownHostException
	 */
	public static Mongo getMongo(MongoDsnVO dsn) throws UnknownHostException {

		MongoClient mongo = null;

		if (null != dsn) {
			// 主机列表
			List<String> servers = dsn.getServers();
			if (null != servers && 0 < servers.size()) {
				List<ServerAddress> seeds = new ArrayList<ServerAddress>();
				for (String server : servers) {
					seeds.add(new ServerAddress(server));
				}
				// 设定选项
				MongoClientOptions options = new MongoClientOptions.Builder()
						.autoConnectRetry(dsn.getAutoConnectRetry())
						.connectionsPerHost(dsn.getConnectionsPerHost())
						.connectTimeout(dsn.getConnectTimeout())
						.maxWaitTime(dsn.getMaxWaitTime())
						.socketTimeout(dsn.getSocketTimeout())
						.threadsAllowedToBlockForConnectionMultiplier(dsn.getThreadsAllowedToBlockForConnectionMultiplier())
						.build();
				// 建立连接
				mongo = new MongoClient(seeds, options);
			}
		}

		return mongo;
	}
}
