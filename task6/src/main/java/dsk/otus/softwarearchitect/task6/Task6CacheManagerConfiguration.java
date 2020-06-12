package dsk.otus.softwarearchitect.task6;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Task6CacheManagerConfiguration {
    @Bean
    public CacheManager getCacheManager() {
        boolean caching = false;
        String cache_enable = System.getenv("CACHE_ENABLED");
        if (cache_enable != null) {
            if (cache_enable.equals("1") || cache_enable.toLowerCase().equals("true"))
                caching = true;
        }
        if (caching) {
            System.out.println("Cache : ON");
            // SimpleCacheManager, ConcurrentMapCacheManager
            // JCacheCacheManager, EhCacheCacheManager, CaffeineCacheManager
            return new EhCacheCacheManager();
//            return new CaffeineCacheManager();
//            return new ConcurrentMapCacheManager() {
//                @Override
//                protected Cache createConcurrentMapCache(final String name) {
//                    return new ConcurrentMapCache(name,
//                            CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(100).build().asMap(), false);
//                }
//            };
        }

        System.out.println("Cache : OFF");
        return new NoOpCacheManager();
    }
}
