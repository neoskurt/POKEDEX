package com.requests

import com.pokemon.Pokemon
import com.env.Env


object Request {
  def get(name: String) : String =
    try
      val jsonSource : String = ClientApi.run(Env.pokemonUrl + name.toLowerCase())
      Pokemon(name, jsonSource).get().toString
    catch
      case r: Exception => s"Pokemon : $name Not found"
}
