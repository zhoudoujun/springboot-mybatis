package com.adou.springboot.mybatis.common;


/**
 * @author zhoudoujun01
 * @date 2019年8月24日20:24:52
 */
public final class SentinelNo {

    private static SentinelNo instance = null;

    /**
     * 关键词键值
     */
    private static String COMPLATEKEY = null;
    
    private static String KEY = null;

    private static String PRONAME = null;
    
    /**
     * 取得此类单例对象
     * 
     * @param proName 自定义配置文件名称
     * @param key 关键词键值
     * @return RedisSentinelOrderNo实例
     */
    public static SentinelNo getInstance(String proName, String key) {

        synchronized (SentinelNo.class) {
            instance = new SentinelNo();
            SentinelNo.PRONAME = proName;
            SentinelNo.KEY = key;
        }
        return instance;
    }

    /**
     * 私有化无参构造函数
     */
    private SentinelNo() {
    }

    
    public long nextId() {
    	return System.currentTimeMillis();
    }
    
//    /**
//     * 产生下一个ID
//     *
//     * @return 流水号
//     */
//    public long nextId() {
//        Jedis jedis = null;
//        try {
//            jedis = CacheCloudClientUtil.getSentinelPool(PRONAME).getResource();
//            if (RedisSentinelOrderNo.COMPLATEKEY == null) {
//                RedisSentinelOrderNo.COMPLATEKEY = JedisConfig.getPreWordMap().get(PRONAME) + RedisSentinelOrderNo.KEY;
//            }
//            return jedis.incr(COMPLATEKEY);
//        }
//        catch (Exception e) {
//            throw new RuntimeException("Redis config error.  Refusing to generate id.");
//        } finally {
//            CacheCloudClientUtil.closeJedis(jedis);
//        }
//    }

}
