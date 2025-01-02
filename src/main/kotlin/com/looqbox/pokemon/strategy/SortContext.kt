package com.looqbox.pokemon.strategy

class SortContext(private var strategy: ISort) {


    fun setStrategy(strategy: ISort) {
        this.strategy = strategy
    }

    fun sort(list : MutableList<String>): ArrayList<String> {
        return strategy.sort(list)
    }
}