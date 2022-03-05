package flashcards

import java.io.File
import kotlin.system.exitProcess

val cardList = mutableListOf<Card>()
val log = mutableListOf<String>()
var export = false
var  exportFileArg = ""


    fun printlnMy(str: String) {
        log.add(str)
        println(str)
    }
    fun readlnMy(): String {
        val str = readln()
        log.add(str)
        return str
    }
    fun printMy(str: String) {
        log.add(str)
        print(str)
    }

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
         else -> choNado()
     }
  }

fun resetStatsFoo() {
    cardList.forEach { it.errors = "0" }
    println("Card statistics have been reset.")
    choNado()
}

fun shameYouFoo() {
    var max = 0
    val tempList = mutableListOf<Pair<Int,Int>>()
    cardList.forEach { tempList.add(Pair(it.errors.toInt(), cardList.indexOf(it))) }
    tempList.sortBy { it.first }
    for (i in tempList.indices){
        if ((tempList.size>1) && (tempList[i].first < tempList.last().first))  tempList.removeAt(i)
    }
    if (tempList.isNotEmpty()){
    if (tempList.last().first ==0){
        printlnMy("There are no cards with errors.")
        choNado()
    }else{
    val str1 = if (tempList.size > 1) "cards are" else "card is"
    val str2 = if (tempList.size > 1) "them" else "it"
    var str3 = mutableListOf<String>()
    for (i in tempList.indices) str3.add("\"${cardList[tempList[i].second].term}\"")
        printlnMy("The hardest $str1 ${str3.joinToString(", ")}. " +
            "You have ${tempList[0].first} errors answering $str2.")
        choNado()
}
    } else{
        printlnMy("There are no cards with errors.")
        choNado()
    }
}

fun logFoo() {
    printlnMy("File name:")
    val logFile = File(readlnMy())
    logFile.writeText("")
    printlnMy("The log has been saved.")
    for (i in log.indices) logFile.appendText("${log[i]}\n")
    choNado()
}

fun exitFoo() {
    if (!export) {
        printlnMy("Bye bye!")
        exitProcess(0)
    }else{
        val exportFile = File(exportFileArg)
        exportFile.writeText("")
        for (i in cardList.indices) {
            exportFile.appendText("${cardList[i].term}, " +
                    cardList[i].definition +
                    ":e:${cardList[i].errors}\n")
        }
        printlnMy("${cardList.size} cards have been saved.")
        printMy("Bye bye!")
        exitProcess(0)
    }
}

fun askFoo() {
    printlnMy("How many times to ask?")
    for (i in 0 until readlnMy().toInt()) tiDebil(i, cardList)
    choNado()
}

fun exportFoo() {
    printlnMy("File name:")
    val exportFile = File(readlnMy())
    exportFile.writeText("")
    for (i in cardList.indices) {
        exportFile.appendText("${cardList[i].term}, " +
                cardList[i].definition +
                ":e:${cardList[i].errors}\n")
    }
    printlnMy("${cardList.size} cards have been saved.")
    choNado()
}

fun importFoo() {
    printlnMy("File name:")
    val importFile = File(readlnMy())
    if (!importFile.exists()) {
        printlnMy("File not found")
        choNado()
    } else {
        importFile.forEachLine {
            checkFile(it.substringBefore(", "))
            cardList.add(
                Card(
                    it.substringBefore(", "),
                    it.substring(it.indexOf(" ")+1,it.indexOf(":")),
                    it.substringAfter(":e:")

                )
            )
        }
      printlnMy("${importFile.readLines().size} cards have been loaded.")

        choNado()
    }
}

fun checkFile(str: String) {
    for (i in cardList.indices) {
        if (cardList[i].term == str) cardList.removeAt(i)

    }
}

fun removeFoo() {

    printlnMy("Which card")
    val str = readlnMy()
    try {
        if (checkRepeat(str)) {
            cardList.forEach { if (it.term == str) cardList.remove(it) }
            println("The card has been removed.")
            choNado()
        } else {
            printlnMy("Can't remove \"$str\": there is no such card.")
            choNado()
        }
    } catch ( e: ConcurrentModificationException ){
        println("The card has been removed.")
    }
}

fun addFoo() {
    cardList.add(Card().create())
    printlnMy("The pair " +
            "(\"${cardList.last().term}\":\"${cardList.last().definition}\") " +
            "has been added.")
    choNado()
}

fun tiDebil(i: Int, cardList: MutableList<Card>) {
    printlnMy("Print the definition of \"${cardList[i].term}\":")
    val str = readlnMy()
    if (str == cardList[i].definition) printlnMy("Correct!")
    else {
        if (checkRepeat(str)) {
            printlnMy(
                "Wrong. The right answer is \"${cardList[i].definition}\", " +
                        "but your answer is " +
                        "correct for \"${checkRepeatAnswer(str)}\"."
            )
            cardList[i].errors = (cardList[i].errors.toInt() +1).toString()
           // printlnMy(cardList[i].errors)
        } else {
            printlnMy("Wrong. The right answer is \"${cardList[i].definition}\".")
            cardList[i].errors = (cardList[i].errors.toInt() +1).toString()
           // printlnMy(cardList[i].errors)
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
        printlnMy("The card:")
          term = readlnMy()
      if (checkRepeat(term)) {
              printlnMy("The card \"$term\" already exists.")
          choNado()
          }
        printlnMy("The definition of the card:")
        definition = readlnMy()
       if (checkRepeat(definition)) {
            printlnMy("The definition \"$definition\" already exists.")
           choNado()
            }
        return Card(term, definition)
    }
}
fun choNado (){

      printlnMy("Input the action (add, remove, import, export, ask, exit):")
      action(readlnMy())
}
fun main(args: Array<String>) {
        if (args.isNotEmpty()) {
            if (args.contains("-export")) {
              //  println("zalupa")
                exportFileArg = args[args.indexOf("-export")+1]
                export = true
            }
            if(args.contains("-import")){
                val importFile = File(args[args.indexOf("-import")+1])
                if (!importFile.exists()) {
                    choNado()
                } else {
                    importFile.forEachLine {
                        cardList.add(
                            Card(
                                it.substringBefore(", "),
                                it.substring(it.indexOf(" ")+1,it.indexOf(":")),
                                it.substringAfter(":e:")
                            )
                        )
                    }
                    printlnMy("${importFile.readLines().size} cards have been loaded.")
                    choNado()
                }
            }else{
                choNado()
            }
        }else{
            choNado()
        }
}


/*


 */