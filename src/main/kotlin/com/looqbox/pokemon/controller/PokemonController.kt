package com.looqbox.pokemon.controller

import com.looqbox.pokemon.entity.PokemonHighlight
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.service.PokemonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/pokemons")
class PokemonController(private val pokemonService : PokemonService) {


    @GetMapping()
    fun getPokemons(@RequestParam query: String? = "",
                   @RequestParam sort: PokemonSortEnum? = PokemonSortEnum.ALPHABETICAL): ResponseEntity<List<Any>> {

        return try {
            var pokemons = pokemonService.getPokemon(query!!, sort!!)
            ResponseEntity.ok(pokemons)
        }catch (e : Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }


    @GetMapping("/highlight")
    fun getPokemonsHighlight(@RequestParam query: String? = "",
                    @RequestParam sort: PokemonSortEnum? = PokemonSortEnum.ALPHABETICAL): ResponseEntity<List<PokemonHighlight>> {

        return try {
            val pokemons = pokemonService.getPokemonHighlight(query!!, sort!!)
            ResponseEntity.ok(pokemons)
        }catch (e : Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}