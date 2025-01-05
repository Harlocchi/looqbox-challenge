package com.looqbox.pokemon.controller

import com.looqbox.pokemon.model.PokemonHighlight
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
                   @RequestParam sort: PokemonSortEnum? = PokemonSortEnum.ALPHABETICAL): ResponseEntity<Map<String, List<String>>> {

        return try {
            var pokemons = pokemonService.getPokemon(query!!, sort!!)
            val mapToReturn : HashMap<String, List<String>> = hashMapOf()

            //add pokemon on result
            mapToReturn.put("result", pokemons)
            ResponseEntity.ok(mapToReturn)
        }catch (e : Exception){
            println(e.printStackTrace())
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }


    @GetMapping("/highlight")
    fun getPokemonsHighlight(@RequestParam query: String? = "",
                    @RequestParam sort: PokemonSortEnum? = PokemonSortEnum.ALPHABETICAL): ResponseEntity<Map<String, List<PokemonHighlight>>> {

        return try {
            val pokemons = pokemonService.getPokemonHighlight(query!!, sort!!)
            val mapToReturn : HashMap<String, List<PokemonHighlight>> = hashMapOf()

            //add pokemon on results
            mapToReturn.put("result", pokemons)
            ResponseEntity.ok(mapToReturn)
        }catch (e : Exception){

            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}