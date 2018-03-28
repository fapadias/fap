package com.adias.fap.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.adias.fap.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.adias.fap.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.Transporter.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.CostGride.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.Dimension.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.DimensionValue.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.Metric.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.Metric.class.getName() + ".dimensions", jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.MetricValue.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.MetricValue.class.getName() + ".dimensions", jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.EntryData.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.EntryData.class.getName() + ".lines", jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.InvoiceLine.class.getName(), jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.InvoiceLine.class.getName() + ".attributes", jcacheConfiguration);
            cm.createCache(com.adias.fap.domain.InvoiceKeyValue.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
