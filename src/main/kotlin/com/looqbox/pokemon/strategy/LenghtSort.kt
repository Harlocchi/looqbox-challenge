package com.looqbox.pokemon.strategy

class LenghtSort : ISort {
    override fun sort(list : MutableList<String>) : ArrayList<String>{

        if (list.size > 1) {
            val mid = list.size / 2
            val left = list.subList(0, mid).toMutableList()
            val right = list.subList(mid, list.size).toMutableList()

            sort(left)
            sort(right)

            var i = 0
            var j = 0
            var k = 0


            while (i < left.size && j < right.size) {
                if (left[i].length < right[j].length) {
                    list[k] = left[i]
                    i++
                } else {
                    list[k] = right[j]
                    j++
                }
                k++
            }

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