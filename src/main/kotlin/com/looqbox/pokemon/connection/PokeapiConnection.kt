package com.looqbox.pokemon.connection

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.looqbox.pokemon.model.PokemonResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PokeapiConnection private constructor(){

    private var method : String? = null;
    private var url : String? = null;

    companion object{
        fun builder(): PokeapiConnection{
            return PokeapiConnection();
        }
    }



    fun method(method : String): PokeapiConnection{
        this.method = method;
        return  this
    }

    fun url(url : String): PokeapiConnection{
        this.url = url;
        return this
    }


    //send request to pokeApi
    fun sendRequest() : PokemonResponse{

            val urlReady = this.url;
            val url = URL(urlReady)
            val connection: HttpURLConnection = (url.openConnection() as HttpURLConnection);
            connection.requestMethod = this.method;

            //buffer to read a body response
            val inputStream = connection.inputStream;
            val reader = BufferedReader(InputStreamReader(inputStream));
            val response = StringBuilder();


            var line: String?
            while (true) {
                line = reader.readLine();
                if (line == null) break;

                response.append(line)
            }


            //convert String to Pokemon Response
            val objectMapper = jacksonObjectMapper()
            val pokemonResponse: PokemonResponse = objectMapper.readValue(response.toString())

            reader.close()
            inputStream.close()
            return pokemonResponse

    }



}