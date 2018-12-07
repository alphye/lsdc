package com.lishengzn.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface CacheManager {
	static final Map<String,Object> cache=new ConcurrentHashMap<String,Object>();
	static final String clientPoolKey="clientPool";
	static final String configBundleKey="configBundle";
	static final String transportOrderQueueKey="transportOrderQueue";
}
