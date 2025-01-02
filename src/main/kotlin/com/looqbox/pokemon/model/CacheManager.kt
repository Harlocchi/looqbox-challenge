package com.looqbox.pokemon.model

import com.looqbox.pokemon.enums.CacheType
import com.looqbox.pokemon.enums.PokemonSortEnum
import java.time.Duration
import java.time.LocalDateTime

class CacheManager(){

    companion object{
        var cacheList : MutableList<CacheItem> = mutableListOf()

        fun addCache(item: CacheItem){
            cacheList.add(item)
        }

        fun removeCache(item: CacheItem){
            cacheList.remove(item)
        }

        fun isPresent(query: String, sort: PokemonSortEnum, cacheType: CacheType): CacheItem? {
            var objCache: CacheItem? = null

            cacheList.map {
                if(it.query.equals(query) and it.sort.equals(sort) and it.cacheType.equals(cacheType)){
                    objCache = it
                }
            }
            return objCache
        }
        fun isExpired(cacheItem: CacheItem) : Boolean{
            if(Duration.between(cacheItem.time, LocalDateTime.now()).toMinutes() >= 5){
                return true
            }
            return false
        }
    }




}