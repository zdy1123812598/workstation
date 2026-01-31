package cn.net.wanji.kafkamanager.cache;

import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TopicHistoryUpCache {
    public static final ExpiringMap<String, String> global_Map = ExpiringMap.builder()
            .expiration(20, TimeUnit.MINUTES)
            .maxSize(30000)
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .asyncExpirationListener((key, value) -> {
            })
            //.asyncExpirationListener(String::asynExpireHandle)
            .build();

    public static void put(String key, String value) {
        global_Map.put(key, value);
        //防止忘记实现equals 因此每次put后刷新下剩余时间
        global_Map.resetExpiration(key);
    }

    public static String get(String key, String value) {
        return global_Map.get(key);
    }

    public static void remove(String key) {
        global_Map.remove(key);
    }

    public static void removeAll() {
        global_Map.clear();
    }

    public static Collection<String> values() {
        return global_Map.values();
    }

}
