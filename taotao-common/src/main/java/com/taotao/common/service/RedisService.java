package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 通用的Redis工具类
 * Created by ning_ on 2020/7/7.
 */
@Service
public class RedisService {
    //注入分片连接池 设置required = false 这样就可以按需加载
    @Autowired(required = false)
    private ShardedJedisPool pool;

    /**
     * 执行set命令，添加字符串格式数据
     *
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value) {
        return excute(new Function<String>() {
            @Override
            public String commond(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value);
            }

        });
    }

    /**
     * 执行get命令，查询字符串格式数据
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return excute(new Function<String>() {
            @Override
            public String commond(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });
    }

    /**
     * 设置expire生存时间
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key, final Integer seconds) {
        return excute(new Function<Long>() {
            @Override
            public Long commond(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, seconds);
            }
        });
    }

    /**
     * 执行set命令，添加字符串格式数据，并且设置存活时间,单位是秒
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value, final Integer seconds) {
        return excute(new Function<String>() {
            @Override
            public String commond(ShardedJedis jedis) {
                String str = jedis.set(key, value);
                jedis.expire(key, seconds);
                return str;
            }
        });
    }

    /**
     * 根据key删除value
     * @param key
     * @return
     */
    public Long del(final String key){
        return excute(new Function<Long>() {
            @Override
            public Long commond(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

    /**
     * RedisService的具体的操作内容,逻辑上是中间环节,
     *
     * @param func
     * @param <R>
     * @return
     */
    public <R> R excute(Function<R> func) {
        ShardedJedis shardedJedis = null;
        try {
            // 从池中获取连接
            shardedJedis = pool.getResource();
            // 添加数据
            return func.commond(shardedJedis);

        } finally {
            //关闭连接
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }

    /**
     * 封装shardedJedis的具体操作,并且将返回值用泛型接收,因为未来不知道是用redis获取什么类型的数据
     *
     * @param <R>
     */
    private interface Function<R> {
        R commond(ShardedJedis shardedJedis);
    }
}
