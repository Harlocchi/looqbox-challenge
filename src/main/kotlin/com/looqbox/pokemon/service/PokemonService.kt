package com.looqbox.pokemon.service

import com.looqbox.pokemon.connection.PokeapiConnection
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.enviroment.Enviroment
import org.springframework.stereotype.Service

@Service
class PokemonService {

    val connection = PokeapiConnection.builder();


    fun getPokemonResult(query : String, pokemonSortEnum: PokemonSortEnum) : List<String>{

        var urlNext = Enviroment.pokeApiUrl

        do{
            val requestCode = connection
                .url(urlNext)
                .method("GET")
                .sendRequest();

            urlNext = requestCode
        }while ()


        println(requestCode)

        return ArrayList<String>(0)
    }



}