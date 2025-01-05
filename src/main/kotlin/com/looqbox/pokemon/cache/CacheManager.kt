package com.looqbox.pokemon.cache

import java.time.Duration
import java.time.LocalDateTime

class CacheManager(){

    companion object{
         var cache : CacheItem? = null

        fun updateCache(newCache : CacheItem){
            cache = newCache
        }


        //verify if cache is expired
        fun isExpired() : Boolean{
            //the cache is expired after 10 minutes
            if(Duration.between(this.cache?.time, LocalDateTime.now()).toMinutes() >= 10){
                return true
            }
            return false
        }
    }




}