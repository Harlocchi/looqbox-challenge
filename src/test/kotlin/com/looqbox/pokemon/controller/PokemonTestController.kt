package com.looqbox.pokemon.Controller

import com.looqbox.pokemon.controller.PokemonController
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.model.PokemonHighlight
import com.looqbox.pokemon.service.PokemonService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.Test

class PokemonControllerTest {

    private val pokemonService: PokemonService = mock(PokemonService::class.java)
    private val pokemonController = PokemonController(pokemonService)

    @Test
    fun `getPokemons should return a list of pokemons`() {
        val query = "pi"
        val sort = PokemonSortEnum.ALPHABETICAL
        val mockPokemons = listOf("Pikachu")

        `when`(pokemonService.getPokemon(query, sort)).thenReturn(mockPokemons)

        val response: ResponseEntity<Map<String, List<String>>> = pokemonController.getPokemons(query, sort)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(mockPokemons, response.body?.get("result"))
    }

    @Test
    fun `getPokemons should return 500 when an exception occurs`() {
        val query = "pikachu"
        val sort = PokemonSortEnum.ALPHABETICAL

        `when`(pokemonService.getPokemon(query, sort)).thenThrow(RuntimeException("Service failure"))

        // Act
        val response: ResponseEntity<Map<String, List<String>>> = pokemonController.getPokemons(query, sort)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }

    @Test
    fun `getPokemonsHighlight should return a list of highlighted pokemons`() {
        // Arrange
        val query = "pi"
        val sort = PokemonSortEnum.ALPHABETICAL
        val mockPokemonHighlights = listOf(
            PokemonHighlight("Pikachu", "<pre>Pi</pre>kachu"),
            PokemonHighlight("Pidgey","<pre>Pi</pre>dgey" )
        )

        `when`(pokemonService.getPokemonHighlight(query, sort)).thenReturn(mockPokemonHighlights)


        val response: ResponseEntity<Map<String, List<PokemonHighlight>>> = pokemonController.getPokemonsHighlight(query, sort)


        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(mockPokemonHighlights, response.body?.get("result"))
    }

    @Test
    fun `getPokemonsHighlight should return 500 when an exception occurs`() {
        val query = "pikachu"
        val sort = PokemonSortEnum.ALPHABETICAL

        `when`(pokemonService.getPokemonHighlight(query, sort)).thenThrow(RuntimeException("Service failure"))

        val response: ResponseEntity<Map<String, List<PokemonHighlight>>> = pokemonController.getPokemonsHighlight(query, sort)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
    }
}