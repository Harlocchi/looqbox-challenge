package com.looqbox.pokemon.entity

import com.looqbox.pokemon.enums.CacheType
import com.looqbox.pokemon.enums.PokemonSortEnum
import java.time.LocalDateTime

class CacheItem(val query: String, val sort: PokemonSortEnum, val cacheType: CacheType, val value: List<Any>,val time: LocalDateTime) {

}