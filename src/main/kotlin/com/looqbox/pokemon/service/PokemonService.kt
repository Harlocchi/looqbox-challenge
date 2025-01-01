package com.looqbox.pokemon.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.looqbox.pokemon.connection.PokeapiConnection
import com.looqbox.pokemon.entity.Pokemon
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.enviroment.Enviroment
import org.springframework.stereotype.Service
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

        println(names.toString())
        return ArrayList<String>(0)
    }



}