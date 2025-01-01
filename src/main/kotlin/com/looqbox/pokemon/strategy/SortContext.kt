package com.looqbox.pokemon.strategy

class SortContext(private var strategy: ISort) {


    fun setStrategy(strategy: ISort) {
        this.strategy = strategy
    }

    fun sort(data: List<String>): List<String> {
        return strategy.sort(data)
    }
}