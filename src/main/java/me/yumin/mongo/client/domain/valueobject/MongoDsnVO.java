/**
 * 
 */
package me.yumin.mongo.client.domain.valueobject;

import java.io.Serializable;
import java.util.List;

/**
 * @author yumin
 * 
 */
public class MongoDsnVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -550214474836202611L;

	/**
	 * servers(e.g:127.0.0.1:27017)
	 */
	private List<String> servers;

	/**
	 * this controls whether or not on a connect, the system retries
	 * automatically
	 */
	private Boolean autoConnectRetry;

	/**
	 * number of connections allowed per host will block if run out
	 */
	private Integer connectionsPerHost;

	/**
	 * connect timeout in milliseconds
	 */
	private Integer connectTimeout;

	/**
	 * max wait time of a blocking thread for a connection
	 */
	private Integer maxWaitTime;

	/**
	 * socket timeout
	 */
	private Integer socketTimeout;

	/**
	 * multiplier for connectionsPerHost for # of threads that can block if
	 * connectionsPerHost is 10, and
	 * threadsAllowedToBlockForConnectionMultiplier is 5, then 50 threads can
	 * block more than that and an exception will be throw
	 */
	private Integer threadsAllowedToBlockForConnectionMultiplier;

	/**
	 * 
	 */
	public List<String> getServers() {
		return servers;
	}

	public Boolean getAutoConnectRetry() {
		return autoConnectRetry;
	}

	public Integer getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public Integer getMaxWaitTime() {
		return maxWaitTime;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public Integer getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}

	public void setAutoConnectRetry(Boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}

	public void setConnectionsPerHost(Integer connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setMaxWaitTime(Integer maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(Integer threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}
}
