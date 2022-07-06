package br.com.zedelivery.parceiroze.app.configuration;

import br.com.zedelivery.parceiroze.app.adapter.dataprovider.dto.ParceiroZeDataproviderDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
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
@EnableCaching
public class RedisConfig {
    public static final String CACHE_MANAGER_ZE_DELIVERY = "cacheManagerZeDelivery";
    public static final String PARCEIRO_ZE = "parceiroze-por-area-de-cobertura";
    private static final Integer DURATION_IN_MINUTOS = 5;
    @Value("${redis.url}")
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
    @Bean(name = CACHE_MANAGER_ZE_DELIVERY)
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration(PARCEIRO_ZE, buildCacheConfiguration(Duration.ofMinutes(DURATION_IN_MINUTOS), ParceiroZeDataproviderDto.class))
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

    @Bean
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
