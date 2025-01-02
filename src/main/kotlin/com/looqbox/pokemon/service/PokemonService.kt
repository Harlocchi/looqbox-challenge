package com.looqbox.pokemon.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.looqbox.pokemon.connection.PokeapiConnection
import com.looqbox.pokemon.entity.Pokemon
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


    fun getPokemonResult(query : String, pokemonSortEnum: PokemonSortEnum) : List<String>{

        var urlNext: String? = Enviroment.pokeApiUrl;
        var names = ArrayList<String>()

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

        var sortType : ISort = AlphabeticalSort();
        if(pokemonSortEnum.equals(PokemonSortEnum.LENGHT)){
            sortType = LenghtSort()
        }

        var sortContext : SortContext = SortContext(sortType)
        var auxList = ArrayList<String>()

        var time = LocalDateTime.now()
        println(sortContext.sort(names.toMutableList()))
        println(Duration.between(time, LocalDateTime.now()).toMillis())

        return sortContext.sort(names.toMutableList())
    }



}