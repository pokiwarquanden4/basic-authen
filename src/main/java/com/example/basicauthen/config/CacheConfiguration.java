package com.example.basicauthen.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Getter
@EnableScheduling
public class CacheConfiguration {
    private ConcurrentHashMap<String, Map<Object, Timestamp>> cacheMap = new ConcurrentHashMap<>();

    public Object getCache(String functionName, Object... params){
        String inputKey = functionName + Arrays.toString(params);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(cacheMap.containsKey(inputKey)){
            Map<Object, Timestamp> innerMap= cacheMap.get(inputKey);
            for (Map.Entry<Object, Timestamp> innerEntry : innerMap.entrySet()) {
                Object value = innerEntry.getKey();
                Timestamp innerValue = innerEntry.getValue();
                if(timestamp.getTime() > innerValue.getTime()){
                    return value;
                }else {
                    cacheMap.remove(inputKey);
                }
            }
        }
        return "Query Failed";
    }

    public void putCache(String functionName, Object result, Object... params){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String inputKey = functionName + Arrays.toString(params);
        Map<Object, Timestamp> map = new HashMap<>();
        map.put(result, timestamp);
        cacheMap.put(inputKey, map);
    }


    @Scheduled(fixedDelay = 100000l)
    void handleExpired(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ArrayList<String> newRemoveList = new ArrayList<>();
        for (Map.Entry<String, Map<Object, Timestamp>> entry : cacheMap.entrySet()) {
            String key = entry.getKey();
            Map<Object, Timestamp> innerMap = entry.getValue();
            for (Map.Entry<Object, Timestamp> innerEntry : innerMap.entrySet()) {
                Timestamp innerValue = innerEntry.getValue();
                if(checkDifferent(timestamp, innerValue)){
                    newRemoveList.add(key);
                }
            }
        }
        removeCache(newRemoveList);
    }

    public void removeCache(ArrayList<String> removeList){
        System.out.println("Clear: " + removeList);
        for (String removeKey: removeList){
            cacheMap.remove(removeKey);

        }
    }
    public boolean checkDifferent(Timestamp timestamp, Timestamp now){
        Long different = ((timestamp.getTime() - now.getTime()));
        if(different > 30000l){
            return true;
        }else {
            return false;
        }
    }
}
