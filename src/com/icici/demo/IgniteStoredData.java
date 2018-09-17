package com.icici.demo;

import java.util.Collection;
import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;

public class IgniteStoredData {
	private Ignite ignite = null;
	private static IgniteStoredData igniteStoredData;
	private IgniteCache<Integer, String> cache;
	private String messageCache = "nmessage";
	private String masterCache = "nmaster";

	private IgniteStoredData(Ignite ignite) {
		this.ignite = ignite;
//		init();
	}

	private void init() {
		try {
			System.out.println("ignite start==" + ignite);

//			IgniteCluster cluster = ignite.cluster();
			// ClusterGroup serverGroup = cluster.forAttribute("ROLE",
			// "server");
			CacheConfiguration cacheCfg = new CacheConfiguration();
			cacheCfg.setName(messageCache);
			cacheCfg.setCacheMode(CacheMode.REPLICATED);
			cacheCfg.setBackups(1);
			cacheCfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
			cacheCfg.setSqlSchema("PUBLIC");
			cacheCfg.setCopyOnRead(true);
			cache = ignite.getOrCreateCache(cacheCfg);
			Collection<String> cacheNames = ignite.cacheNames();
			System.out.println("cache size =" + cacheNames.size());
			for (String name : cacheNames) {
				System.out.println("cache name=" + name);
			}
			// create table
			FieldsQueryCursor<List<?>> query = cache
					.query(new SqlFieldsQuery(
							"CREATE TABLE message_queue (mobile_no LONG,gcm_id LONG,"
									+ "app_id LONG, dept_Id LONG, user_id varchar, msg varchar"
									+ ", PRIMARY KEY(mobile_no,user_id))"));

			System.out.println("create message table==" + query.getColumnsCount());
                        
                        //create master table 
                        CacheConfiguration masterConf = new CacheConfiguration();
			masterConf.setName(masterCache);
			masterConf.setCacheMode(CacheMode.REPLICATED);
			masterConf.setBackups(1);
			masterConf.setAtomicityMode(CacheAtomicityMode.ATOMIC);
			masterConf.setSqlSchema("PUBLIC");
			masterConf.setCopyOnRead(true);
			IgniteCache<Integer, String> cachemaster = ignite.getOrCreateCache(masterConf);
			Collection<String> mastercacheName = ignite.cacheNames();
			System.out.println("cache size =" + mastercacheName.size());
			for (String name : mastercacheName) {
				System.out.println("cache name=" + name);
			}
			// create table
			FieldsQueryCursor<List<?>> masterDataQuery = cachemaster
					.query(new SqlFieldsQuery(
							"CREATE TABLE master_data (mobile_no LONG,gcm_id LONG,"
									+ "user_id varchar"
									+ ", PRIMARY KEY(mobile_no,user_id))"));
                        System.out.println("create master table==" + masterDataQuery.getColumnsCount());
                        
		} catch (Exception e) {
			System.out.println("ecxetion =" + e);
		}
	}

	public static IgniteStoredData getInstance(Ignite ignite) {
		if (igniteStoredData == null) {
			igniteStoredData = new IgniteStoredData(ignite);
		}
		return igniteStoredData;
	}

        
	public void insertMasterData(long mobile_no, long gcm_id,String user_id) {
		System.out.println("inside master data ......");
		System.out.println("user id=" + user_id + " mobile_no=" + mobile_no+
                        " gcm id="+gcm_id);
		try {
			IgniteCache<Integer, String> cache = ignite
					.getOrCreateCache(messageCache);
			SqlFieldsQuery query = new SqlFieldsQuery(
					"INSERT INTO master_data (mobile_no,gcm_id,user_id) VALUES(?,?,?)");
			FieldsQueryCursor<List<?>> query2 = cache.query(query.setArgs(
					mobile_no, gcm_id, user_id));
			System.out.println("insert data=" + query2.getColumnsCount());

		} catch (Exception e) {
			System.out.println("insert error=" + e);
		} finally {
			//ignite.destroyCache(cacheName);
		}
	}
	public void insertMessageData(long mobile_no, long gcm_id, long app_id,
			long dept_id, String user_id, String message) {
		System.out.println("inside insert message ......");
		System.out.println("user id=" + user_id + " mobile_no=" + mobile_no
				+ " message=" + message);
		try {
			IgniteCache<Integer, String> cache = ignite
					.getOrCreateCache(messageCache);
			SqlFieldsQuery query = new SqlFieldsQuery(
					"INSERT INTO message_queue (mobile_no,gcm_id,app_id,dept_Id,user_id,msg) VALUES(?,?,?,?,?,?)");
			FieldsQueryCursor<List<?>> query2 = cache.query(query.setArgs(
					mobile_no, gcm_id, app_id, dept_id, user_id, message));
			System.out.println("insert data=" + query2.getColumnsCount());

		} catch (Exception e) {
			System.out.println("insert error=" + e);
		} finally {
			//ignite.destroyCache(cacheName);
		}
	}

}
