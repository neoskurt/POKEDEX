package com.pokemon

import com.requests.Request

object Pokedex{

   def main(args: Array[String]): Unit = {

  val scanner = new java.util.Scanner(System.in)
    Console.println("Chosissiez une fonction:")
    val choose = "get"
    Console.println(choose)
    val first_step = scanner.nextLine()

    first_step match {
      case "get" =>
        Console.println("Choose a Pokemon Name")
        val line = scanner.nextLine()
          println(Request.get( line ))

    }
}
}
