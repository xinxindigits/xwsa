package cn.com.xinxin.sass.session.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengyunhui
 * @created 2018/12/5 10:55
 * @updated
 * @description
 **/
@Configuration
public class SessionRedisConfig {

    @Value("${session.redis.cluster.nodes}")
    private String clusterNodes;

    @Value("${session.redis.cluster.timeout}")
    private Long timeout;

    @Value("${session.redis.cluster.max-redirects}")
    private Integer redirects;

    @Bean("redisClusterConfiguration")
    public RedisClusterConfiguration getClusterConfiguration() {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeout", timeout);
        source.put("spring.redis.cluster.max-redirects", redirects);
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }
}
