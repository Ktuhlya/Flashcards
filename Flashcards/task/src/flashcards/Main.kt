package flashcards

import java.io.File
import kotlin.system.exitProcess

val cardList = mutableListOf<Card>()




  fun action (str: String) {
     when(str){
         "add" ->  addFoo()
         "remove" -> removeFoo()
         "import" -> importFoo()
         "export" -> exportFoo()
         "ask" -> askFoo()
         "log" -> logFoo()
         "hardest card" -> shameYouFoo()
         "reset stats" -> resetStatsFoo()
         "exit" -> exitFoo()
         else -> println("zalupa")
     }
  }

fun resetStatsFoo() {
    TODO("Not yet implemented")
}

fun shameYouFoo() {
    TODO("Not yet implemented")
}

fun logFoo() {
    TODO("Not yet implemented")
}

fun exitFoo() {
        println("Bye bye!")
    exitProcess(0)
}

fun askFoo() {
    println("How many times to ask?")
    for (i in 0 until readln().toInt()) tiDebil(i, cardList)
    main()
}

fun exportFoo() {
    println("File name:")
    val exportFile = File(readln())
    exportFile.writeText("")
    for (i in cardList.indices) {
        exportFile.appendText("${cardList[i].term}, " +
                cardList[i].definition +
                ":e:${cardList[i].errors}\n")
    }
    println("${cardList.size} cards have been saved.")
    main()
}

fun importFoo() {
    println("File name:")
    val importFile = File(readln())
    if (!importFile.exists()) {
        println("File not found")
        main()
    } else {
       // var count = 0

        importFile.forEachLine {
            checkFile(it.substringBefore(", "))
            cardList.add(
                Card(
                    it.substringBefore(", "),
                    it.substringAfter(", "),
                    it.substringAfter(":e:")

                )
            )
        }
      println("${importFile.readLines().size} cards have been loaded.")

        main()
    }
}

fun checkFile(str: String) {
    for (i in cardList.indices) {
        if (cardList[i].term == str) cardList.removeAt(i)
    }

}

fun removeFoo() {
    println("Which card")
    val str = readln()
    if (checkRepeat(str)) {
        cardList.forEach { if (it.term == str) cardList.remove(it) }
        println("The card has been removed.")
        main()
    }else{
        println("Can't remove \"$str\": there is no such card.")
        main()
    }
}

fun addFoo() {
    cardList.add(Card().create())
    println("The pair " +
            "(\"${cardList.last().term}\":\"${cardList.last().definition}\") " +
            "has been added.")
    main()
}

fun main() {
  println("Input the action (add, remove, import, export, ask, exit):")
    action(readln())

}

fun tiDebil(i: Int, cardList: MutableList<Card>) {
    println("Print the definition of \"${cardList[i].term}\":")
    val str = readln()
    if (str == cardList[i].definition) println("Correct!")
    else {
        if (checkRepeat(str)) {
            println(
                "Wrong. The right answer is \"${cardList[i].definition}\", " +
                        "but your answer is " +
                        "correct for \"${checkRepeatAnswer(str)}\"."
            )
            cardList[i].errors = (cardList[i].errors.toInt() +1).toString()
            println(cardList[i].errors)
        } else {
            println("Wrong. The right answer is \"${cardList[i].definition}\".")
            cardList[i].errors = cardList[i].errors +1
            println(cardList[i].errors)
        }
    }
}

fun checkRepeatAnswer(str: String): String{
    cardList.forEach { if(it.definition == str) return it.term }
    return ""
}

fun  checkRepeat ( str: String): Boolean{
    cardList.forEach{if(it.term==str || it.definition==str) return true }
       return false
}


class Card(var term: String = "", var definition: String = "", var errors: String = "0") {
    fun create(): Card {
        print("The card:\n> ")
          term = readln()
      if (checkRepeat(term)) {
              println("The card \"$term\" already exists.")
              main()
          }
        print("The definition of the card:\n> ")
        definition = readln()
       if (checkRepeat(definition)) {
            println("The definition \"$definition\" already exists.")
            main()
            }
        return Card(term, definition)
    }
}

