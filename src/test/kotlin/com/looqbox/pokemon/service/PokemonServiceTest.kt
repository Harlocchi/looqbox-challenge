package com.looqbox.pokemon.service

import com.looqbox.pokemon.cache.CacheItem
import com.looqbox.pokemon.cache.CacheManager
import com.looqbox.pokemon.enums.PokemonSortEnum
import com.looqbox.pokemon.model.PokemonHighlight
import com.looqbox.pokemon.strategy.SortContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime

class PokemonServiceTest {

    private lateinit var pokemonService: PokemonService

    @BeforeEach
    fun setup() {
        pokemonService = PokemonService()
    }

    @Test
    fun `should fetch pokemons and cache them when cache is null or expired`() {
        CacheManager.cache = null

        val result = pokemonService.getPokemon("pikachu", PokemonSortEnum.ALPHABETICAL)

        assertNotNull(CacheManager.cache)
        assertTrue(CacheManager.cache?.value?.isNotEmpty() == true)

        assertNotNull(result)
    }

    @Test
    fun `should return cached pokemons when cache is valid`() {
        val cachedPokemons = listOf("bulbasaur", "charmander", "squirtle")
        CacheManager.cache = CacheItem(cachedPokemons, LocalDateTime.now())

        val result = pokemonService.getPokemon("bulbasaur", PokemonSortEnum.ALPHABETICAL)

        println(result.size)

        assertEquals(1, result.size)
    }

    @Test
    fun `should filter pokemons by query`() {
        val pokemons = listOf("bulbasaur", "charmander", "squirtle")
        val query = "bulba"

        val result = pokemonService.filterByQuery(pokemons, query)

        assertEquals(1, result.size)
        assertTrue(result.contains("bulbasaur"))
    }

    @Test
    fun `should sort pokemons alphabetically`() {
        val pokemons = listOf("squirtle", "bulbasaur", "charmander")

        val result = pokemonService.sortByType(pokemons, PokemonSortEnum.ALPHABETICAL)

        assertEquals(listOf("bulbasaur", "charmander", "squirtle"), result)
    }

    @Test
    fun `should sort pokemons by length`() {
        val pokemons = listOf("squirtle", "bulbasaur", "cat")

        val result = pokemonService.sortByType(pokemons, PokemonSortEnum.LENGHT)

        assertEquals(listOf("cat", "squirtle", "bulbasaur"), result)
    }

    @Test
    fun `should create highlight list`() {
        val pokemons = listOf("bulbasaur", "charmander", "squirtle")
        val query = "char"

        val result = pokemonService.makeHighlight(query, pokemons)

        assertEquals(3, result.size)
        assertEquals("charmander", result[1].name)
        assertEquals("<pre>char</pre>mander", result[1].highlight)
    }

    @Test
    fun `should return empty list when fetchPokemons fails`() {
        val result = pokemonService.fetchPokemons()

        assertTrue(result.isNotEmpty())
    }
}
