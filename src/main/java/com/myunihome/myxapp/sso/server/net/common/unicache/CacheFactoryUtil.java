package com.myunihome.myxapp.sso.server.net.common.unicache;

import com.myunihome.myxapp.paas.cache.CacheFactory;
import com.myunihome.myxapp.paas.cache.client.ICacheClient;

public class CacheFactoryUtil {
    public static ICacheClient getCacheClient(String namespace) {
        return CacheFactory.getCacheClient(namespace);
    }
    
}
