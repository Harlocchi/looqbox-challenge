package com.looqbox.pokemon.service

import com.looqbox.pokemon.connection.PokeapiConnection
import com.looqbox.pokemon.cache.CacheItem
import com.looqbox.pokemon.cache.CacheManager
import com.looqbox.pokemon.model.PokemonHighlight
import com.looqbox.pokemon.enums.CacheType
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.environment.Enviroment
import com.looqbox.pokemon.strategy.AlphabeticalSort
import com.looqbox.pokemon.strategy.ISort
import com.looqbox.pokemon.strategy.LenghtSort
import com.looqbox.pokemon.strategy.SortContext
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.collections.ArrayList

@Service
class PokemonService {

    val connection = PokeapiConnection.builder();


    fun getPokemon(query : String, pokemonSortEnum: PokemonSortEnum) : List<String>{
        var cacheType = CacheType.POKEMONS
        var names: List<String>;

        if((CacheManager.cache == null) || (CacheManager.isExpired())){
            names = fetchPokemons()
            //save the new cache
            val cacheItem = CacheItem(names, LocalDateTime.now())
            CacheManager.updateCache(cacheItem)
        }else{
            names = CacheManager.cache?.value!!
        }


        if(names.isNotEmpty()){
           names = sortByType(names, pokemonSortEnum);
        }
        if(query.isNotEmpty()){
           names = filterByQuery(names, query)
        }


        return names
    }

    fun getPokemonHighlight(query: String ,pokemonSortEnum : PokemonSortEnum): List<PokemonHighlight>{
        var names: List<String> = emptyList();

        //if cache does not exist or is expired
        if((CacheManager.cache == null) || (CacheManager.isExpired())){
            names = fetchPokemons()
            //save the new cache
            val cacheItem = CacheItem(names, LocalDateTime.now())
            CacheManager.updateCache(cacheItem)
        }else{
            names = CacheManager.cache?.value!!
        }

        if(names.isNotEmpty()){
            names = sortByType(names, pokemonSortEnum);
        }
        if(query.isNotEmpty()){
            names = filterByQuery(names, query)
        }

        val pokemonHighlightList = makeHighlight(query, names)
        return pokemonHighlightList
    }


    fun makeHighlight(query: String, names : List<String>) : List<PokemonHighlight>{
        val highLighList : MutableList<PokemonHighlight> = mutableListOf()
        names.map {
            val objHighlight : PokemonHighlight;

            if(query.isEmpty()){
                objHighlight = PokemonHighlight(it, it)
            }else{
                objHighlight = PokemonHighlight(it, it.replace(query, "<pre>$query</pre>", ignoreCase = true))
            }

            highLighList.add(objHighlight)
        }
        return ArrayList(highLighList)
    }

    fun fetchPokemons(): List<String> {
        var urlNext: String? = Enviroment.pokeApiUrl
        val names = ArrayList<String>()

        try {
            while (urlNext != null) {
                val response = connection
                    .url(urlNext)
                    .method("GET")
                    .sendRequest()

                response.results.forEach { pokemon ->
                    names.add(pokemon.name)
                }
                urlNext = response.next
            }
        } catch (e: Exception) {
            println("Erro ao buscar pokémons: ${e.message}")
            e.printStackTrace()
        }

        return names
    }


    fun filterByQuery(list: List<String>, query: String) : List<String>{
        val filterList: MutableList<String> = mutableListOf()
        list.map {
            if(it.contains(query)) filterList.add(it)
        }
        return ArrayList(filterList)
    }

    fun sortByType(names: List<String>, pokemonSortEnum : PokemonSortEnum) : List<String>{
        var sortType : ISort = AlphabeticalSort();
        if(pokemonSortEnum.equals(PokemonSortEnum.LENGHT)){
            sortType = LenghtSort()
        }
        val sortContext  = SortContext(sortType)
        return sortContext.sort(names.toMutableList())
    }

}