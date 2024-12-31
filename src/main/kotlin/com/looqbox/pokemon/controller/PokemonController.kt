package com.looqbox.pokemon.controller

import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.service.PokemonService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PokemonController(private val pokemonService : PokemonService) {


    @GetMapping("/pokemons")
    fun getPokemons(@RequestParam query: String? = "",
                   @RequestParam sort: PokemonSortEnum? = PokemonSortEnum.ALPHABETICAL) : String{

        try {
            pokemonService.getPokemonResult(query!!, sort!!)
        }catch (e : Exception){
            e.printStackTrace()
        }


        return "aAAS"
    }

}