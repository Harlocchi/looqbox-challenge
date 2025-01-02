package com.looqbox.pokemon.entity

import com.looqbox.pokemon.enums.CacheType
import com.looqbox.pokemon.enums.PokemonSortEnum

class CacheManager(){

    companion object{
        var cacheList : MutableList<CacheItem> = mutableListOf()
    }

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
}