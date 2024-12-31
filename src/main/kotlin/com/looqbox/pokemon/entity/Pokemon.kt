package com.looqbox.pokemon.entity

class Pokemon(
    var name : String,
    var url : String
) {


    public fun getName() : String{
        return this.name;
    }

    public fun getUrl() : String{
        return this.url;
    }

    public fun setName(name : String) {
        this.name = name;
    }

    public fun setUrl(url : String) {
        this.url = url;
    }

}