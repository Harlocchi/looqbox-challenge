package com.looqbox.pokemon.service

import com.looqbox.pokemon.connection.PokeapiConnection
import com.looqbox.pokemon.model.CacheItem
import com.looqbox.pokemon.model.CacheManager
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
        var getOnCache = CacheManager.isPresent(query, pokemonSortEnum, cacheType)

        if (getOnCache != null){
            if(CacheManager.isExpired(getOnCache)){
                CacheManager.removeCache(getOnCache)
            }else{
                return getOnCache.value as List<String>
            }
        }


        try {
           names = fetchPokemons();
        }catch (exc : Exception){
            throw Exception("could not perform request")
        }

        if(names.isNotEmpty()){
           names = sortByType(names, pokemonSortEnum);
        }
        if(query.isNotEmpty()){
           names = filterByQuery(names, query)
        }

        val cacheToSave = CacheItem(query, pokemonSortEnum, cacheType, names, LocalDateTime.now())
        CacheManager.addCache(cacheToSave)

        return names
    }



    fun getPokemonHighlight(query: String ,pokemonSortEnum : PokemonSortEnum): List<PokemonHighlight>{
        var cacheType = CacheType.HIGHLIGHT
        var names: List<String>;

        var getOnCache = CacheManager.isPresent(query, pokemonSortEnum, cacheType)

        if (getOnCache != null){
            if(CacheManager.isExpired(getOnCache)){
                CacheManager.removeCache(getOnCache)
            }else{
                return getOnCache.value as List<PokemonHighlight>
            }
        }

        try {
            names = fetchPokemons();
        }catch (exc : Exception){
            throw Exception("could not perform request")
        }

        if(names.isNotEmpty()){
            names = sortByType(names, pokemonSortEnum);
        }
        if(query.isNotEmpty()){
            names = filterByQuery(names, query)
        }

        var pokemonHighlightList = makeHighlight(query, names)
        val cacheToSave = CacheItem(query, pokemonSortEnum, cacheType, pokemonHighlightList, LocalDateTime.now())
        CacheManager.addCache(cacheToSave)

        return pokemonHighlightList
    }

    fun makeHighlight(query: String, names : List<String>) : List<PokemonHighlight>{
        val highLighList : MutableList<PokemonHighlight> = mutableListOf()
        names.map {
            val objHighlight : PokemonHighlight;

            if(query.isEmpty()){
                objHighlight = PokemonHighlight(it, "<pre>$it</pre>")
            }else{
                objHighlight = PokemonHighlight(it, it.replace(query, "<pre>$query</pre>"))
            }

            highLighList.add(objHighlight)
        }
        return ArrayList(highLighList)
    }

    fun fetchPokemons() : List<String>{

        var urlNext: String? = Enviroment.pokeApiUrl;
        val names = ArrayList<String>()

        while (urlNext != null) {
            val response = connection
                .url(urlNext)
                .method("GET")
                .sendRequest();

            response.results.forEach{ pokemon ->
                names.add(pokemon.name)
            }

            urlNext = response.next
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
        var auxList = ArrayList<String>()

        var time = LocalDateTime.now()
        return sortContext.sort(names.toMutableList())
    }

}