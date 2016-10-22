package com.myunihome.myxapp.sso.server.net.common.unicache.constants;

/**
 * 缓存统一定义常量类
 * 
 * @author kanghc
 * 
 */
public final class UniCacheConstants {
	
	private UniCacheConstants(){}

	/* PaaSsession缓存前缀定义 */
	public static final String _UniCache_PaaS_Session = "R_JSID_";

	/* sso缓存前缀定义 */
	public static final String _UniCache_Redis = "R_SSOID_";

	public static final class CacheNamespaces {
		
		private CacheNamespaces(){}
		
		/* 单点登录缓存命名空间 */
		public static final String SSOCache = "com.myunihome.myxapp.sso.cache.gnservicerouteconfig";
		
		/* session缓存命名空间 */
		public static final String sessionCache = "com.myunihome.myxapp.sso.session.SessionServer";
	}
}
