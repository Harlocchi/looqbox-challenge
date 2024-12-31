package com.looqbox.pokemon.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.looqbox.pokemon.connection.PokeapiConnection
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.enviroment.Enviroment
import org.springframework.stereotype.Service
import java.util.*

@Service
class PokemonService {

    val connection = PokeapiConnection.builder();


    fun getPokemonResult(query : String, pokemonSortEnum: PokemonSortEnum) : List<String>{

        var urlNext: String? = Enviroment.pokeApiUrl;

        while (urlNext != null) {
            val response = connection
                .url(urlNext)
                .method("GET")
                .sendRequest();

            val body = jsonToMap(response.toString());
            println(body)

            urlNext = body["next"] as? String;
        }

        return ArrayList<String>(0)
    }

    private fun jsonToMap(json : String) : Map<String, Any>{
        val mapper = jacksonObjectMapper()
        val jsonMap: Map<String, Any> = mapper.readValue(json)

        return jsonMap
    }



}