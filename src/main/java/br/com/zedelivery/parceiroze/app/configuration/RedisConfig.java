package br.com.zedelivery.parceiroze.app.configuration;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.repository.entity.ParceiroZeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {
    private static final String PARCEIRO_ZE = "parceiroze";
    @Value("${redis.hostname}")
    private String redisHostname;
    @Value("${redis.port}")
    private Integer redisPort;
    @Value("${redis.ssl}")
    private Boolean useSSL;

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Primary
    @Bean(name = "cacheManagerZeDelivery")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration(PARCEIRO_ZE, buildCacheConfiguration(Duration.ofMinutes(5), ParceiroZeEntity.class))
                .build();
    }

    private RedisCacheConfiguration buildCacheConfiguration(Duration duration, Class clazz) {
        RedisSerializationContext.SerializationPair jsonSerializeValues = RedisSerializationContext
                .SerializationPair
                .fromSerializer(new Jackson2JsonRedisSerializer<Object>(clazz));
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(duration)
                .serializeValuesWith(jsonSerializeValues);
    }

    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(redisHostname);
        return new LettuceConnectionFactory(redisConfiguration, buildLettuceConnectionFactory());
    }

    private LettuceClientConfiguration buildLettuceConnectionFactory() {
        LettuceClientConfiguration clientConfiguration;
        if(useSSL) {
            return LettuceClientConfiguration.builder().useSsl().build();
        }
        return LettuceClientConfiguration.builder().build();
    }

}
