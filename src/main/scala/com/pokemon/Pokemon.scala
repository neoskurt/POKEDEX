package com.pokemon

import scala.collection.mutable.ArrayBuffer
import spray.json.*
import DefaultJsonProtocol.*
import com.pokemon.Stat
import com.pokemon.Type
import com.env.Env.*

class Pokemon(name: String, jsonSource: String):
  var type1: String = _
  var type2: String = _
  var hp: String = _
  var attack: String = _
  var defense: String = _
  var specialAttack: String = _
  var specialDefense: String = _
  var speed: String = _
  var legendary: Boolean = false
  var generation: Int = 0
  var moveset: ArrayBuffer[String] = new ArrayBuffer[String]()
  var evolutionPath: String = _

  def get(): Pokemon =
    setGeneration()
    setMoves()
    setStats()
    setTypes()
    this

  def setGeneration(): Unit =
    val versions = jsonSource.parseJson.asJsObject.getFields("sprites").head.asJsObject.getFields("versions").head
    var currentNumber : Int = 0
    while(this.generation == 0 && currentNumber < romanNumerals.size - 1)
      try
        val gen : String = versions.asJsObject.getFields("generation-"+romanNumerals(currentNumber)).head.toString
        if(gen != "" && gen != null)
          this.generation = currentNumber + 1
        currentNumber += 1

  def setMoves():Unit =
    val movesJson = jsonSource.parseJson.asJsObject.getFields("moves").head.convertTo[JsArray]
    for(move <- movesJson.elements)
      this.moveset.append(move.asJsObject.getFields("move").head.asJsObject.getFields("name").head.toString)

  def setStats(): Unit =
    implicit val statFormat: JsonFormat[Stat] = jsonFormat2(Stat)
    val statsJson = jsonSource.parseJson.asJsObject.getFields("stats").head.convertTo[JsArray]
    var stats : Array[Stat] = Array()
    for(stat <- statsJson.elements)
      val name : String = stat.asJsObject.getFields("stat").head.convertTo[Stat].name
      if(name == "hp")
        this.hp = stat.asJsObject.getFields("base_stat").head.toString
      else if (name == "attack")
        this.attack = stat.asJsObject.getFields("base_stat").head.toString
      else if (name == "defense")
        this.defense = stat.asJsObject.getFields("base_stat").head.toString
      else if (name == "special-attack")
        this.specialAttack = stat.asJsObject.getFields("base_stat").head.toString
      else if (name == "special-defense")
        this.specialDefense = stat.asJsObject.getFields("base_stat").head.toString
      else if (name == "speed")
        this.speed = stat.asJsObject.getFields("base_stat").head.toString


  def setTypes(): Unit =
    implicit val typeFormat: JsonFormat[Type] = jsonFormat2(Type)
    val typesJson = jsonSource.parseJson.asJsObject.getFields("types").head.convertTo[JsArray]
    for(typ <- typesJson.elements)
      if(typ.asJsObject.getFields("slot").head.toString.toInt == 1)
        this.type1 = typ.asJsObject.getFields("type").head.convertTo[Type].name
      else if(typ.asJsObject.getFields("slot").head.toString.toInt == 2)
        this.type2 = typ.asJsObject.getFields("type").head.convertTo[Type].name

  override def toString: String =
    s"""
      |name : ${this.name}
      |type 1 : ${this.type1}
      |type 2 : ${this.type2}
      |hp : ${this.hp}
      |attack : ${this.attack}
      |def : ${this.defense}
      |SP attack : ${this.specialAttack}
      |SP defense : ${this.specialDefense}
      |speed : ${this.speed}
      |legendary : ${this.legendary}
      |moveset : ${this.moveset}
      |generation : ${this.generation}
      |evolution path : ${this.evolutionPath}
      |""".stripMargin

