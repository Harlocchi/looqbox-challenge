package com.looqbox.pokemon.connection

import com.looqbox.pokemon.enviroment.Enviroment
import java.net.HttpURLConnection
import java.net.URL

class PokemonApi private constructor(){

    private var method : String? = null;
    private var url : String? = null;
    private var params : HashMap<String, String> ? = null;

    fun method(method : String): PokemonApi{
        this.method = method;
        return  this
    }

    fun url(url : String): PokemonApi{
        this.url = url;
        return this
    }

    fun params(params : HashMap<String, String>): PokemonApi{
        this.params = params;
        return this
    }


    fun sendRequest() : List<String>{
        var urlReady = this.url;
        val paramsObj = this.params;

        if(this.params != null) {
            urlReady += "?"
            val last = paramsObj?.size;
            var count = 1;

            for(param in paramsObj!!) {
                urlReady += param.key + "=" + param.value
                if(count != last){
                    urlReady += "&"
                }
                count+=1;
            }
        }


        val url = URL(urlReady)
        val connection : HttpURLConnection = (url.openConnection() as HttpURLConnection);
        connection.requestMethod = this.method;

        val responseCode = connection.responseCode
        println(responseCode)

        return emptyList()
    }



}