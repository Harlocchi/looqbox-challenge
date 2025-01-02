package com.looqbox.pokemon.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.looqbox.pokemon.connection.PokeapiConnection
import com.looqbox.pokemon.entity.Pokemon
import com.looqbox.pokemon.entity.PokemonHighlight
import com.looqbox.pokemon.enums.CacheType
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.enviroment.Enviroment
import com.looqbox.pokemon.strategy.AlphabeticalSort
import com.looqbox.pokemon.strategy.ISort
import com.looqbox.pokemon.strategy.LenghtSort
import com.looqbox.pokemon.strategy.SortContext
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Service
class PokemonService {

    val connection = PokeapiConnection.builder();


    fun getPokemon(query : String, pokemonSortEnum: PokemonSortEnum) : List<String>{
        var cacheType = CacheType.POKEMONS

       var names: List<String>;

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

       return names
    }

    fun getPokemonHighlight(query: String ,pokemonSortEnum : PokemonSortEnum): List<PokemonHighlight>{
        var cacheType = CacheType.HIGHLIGHT
        var names: List<String>;

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

       return makeHighlight(query, names)
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




    fun cacheExist(){

    }

}