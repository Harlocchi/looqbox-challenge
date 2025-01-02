package com.looqbox.pokemon.controller

import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.service.PokemonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller("/pokemons")
class PokemonController(private val pokemonService : PokemonService) {


    @GetMapping("/pokemons")
    fun getPokemons(@RequestParam query: String? = "",
                   @RequestParam sort: PokemonSortEnum? = PokemonSortEnum.ALPHABETICAL): ResponseEntity<List<String>> {

        return try {
            var pokemons = pokemonService.getPokemonResult(query!!, sort!!)
            ResponseEntity.ok(pokemons)
        }catch (e : Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(listOf("Erro: ${e.message}"))
        }
    }

}