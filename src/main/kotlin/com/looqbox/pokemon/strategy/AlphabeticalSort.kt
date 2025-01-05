package com.looqbox.pokemon.strategy

import java.time.Duration
import java.time.LocalDateTime

class AlphabeticalSort : ISort {
    override fun sort(list: MutableList<String>) : ArrayList<String> {
        var time = LocalDateTime.now()
        if (list.size > 1) {

            // find the middle of the list
            val mid = list.size / 2

            //create sublist based on middle
            val left = list.subList(0, mid).toMutableList()
            val right = list.subList(mid, list.size).toMutableList()

            //call recursively
            sort(left)
            sort(right)

            // Index for the left list
            var i = 0
            // Index for the right list
            var j = 0
            // Index for the main list
            var k = 0

            // While there are values in both lists
            while (i < left.size && j < right.size) {

                // Compare by lexicographic
                if (left[i] < right[j]) {
                    // Allocate the value in the correct index of the main list
                    list[k] = left[i]
                    i++
                } else {
                    list[k] = right[j]
                    j++
                }
                k++
            }

            // Place the remaining values in the main list,
            // if any of the lists still have values
            while (i < left.size) {
                list[k] = left[i]
                i++
                k++
            }

            while (j < right.size) {
                list[k] = right[j]
                j++
                k++
            }
        }
        return ArrayList(list)
    }
}