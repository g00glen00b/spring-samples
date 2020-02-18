package be.g00glen00b.apps.springbootehcache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import java.util.List;

@Slf4j
public class TaskCacheListener implements CacheEventListener<String, List<TaskDTO>> {
    @Override
    public void onEvent(CacheEvent<? extends String, ? extends List<TaskDTO>> cacheEvent) {
        log.info("Event '{}' fired for key '{}' with value {}", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getNewValue());
    }
}
