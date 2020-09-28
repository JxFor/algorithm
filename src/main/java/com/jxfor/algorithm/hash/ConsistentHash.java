package com.jxfor.algorithm.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性 hash
 *
 * @author jxfor
 * @date 2020/9/27 10:20
 */
public class ConsistentHash {

    private static SortedMap<Integer,String> sortedMap = new TreeMap<>();

    private static int addServer( String server,int virtualNodeCount) {
        if( virtualNodeCount < 0 ){
            return 0;
        }

        int successCount = 0 ,hash = 0;
        String virtualServer;
        for( int i = 0 ; i <= virtualNodeCount  ;i++){
            virtualServer = server+"#"+i;
            hash = getHash(virtualServer);
            //可能会有hash冲突，忽略它
            // 应该有更优雅的实现方式
            sortedMap.put(hash,virtualServer);
        }

        return successCount;
    }

    private static String getServer( int hash ){
        SortedMap<Integer, String> resultMap = sortedMap.tailMap(hash);

        hash = resultMap.isEmpty() ? sortedMap.firstKey() : resultMap.firstKey();

        String server = sortedMap.get(hash);
        //去除 #
        int pos = server.lastIndexOf("#");
        return pos == -1 ? server : server.substring(0,pos);
    }

    private static int getHash( String str ){
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    public static void main(String[] args) {
        addServer("127.0.0.1:8080",2);
        addServer("127.0.0.1:8081",2);
        addServer("127.0.0.1:8082",2);

        //查询节点
        String[] arr =  new String[]{"he#2","he#1","x1"};
        for( String str : arr ){
            int hash = getHash(str);
            System.out.println( getServer(hash));
        }

    }

}
