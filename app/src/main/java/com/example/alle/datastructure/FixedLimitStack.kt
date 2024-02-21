package com.example.alle.datastructure

import android.util.Log
import java.util.LinkedList
import java.util.Stack

class FixedLimitStack<K,V> (private val limit : Int, ) {
    private val map = mutableMapOf<K,V>()

    fun add(k:K, v: V){
        if(map.size <= 10){
            map[k] = v
        }else{
            val entry = map.entries.first()
            map.remove(entry.key)
            map[k] = v
        }
    }

    fun get(k:K): V? {
        return map[k]
    }

    fun containsKey(k:K): Boolean {
        return map.containsKey(k)
    }

    fun addIfNotContain(k:K, v:V){
        if(!map.containsKey(k)){
            add(k,v)
        }
    }
}