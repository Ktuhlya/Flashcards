package flashcards

val cardList = mutableListOf<Card>()

fun main() {
  print("Input the number of cards:\n> ")

  for (i in 1..readln().toInt()) {
    cardList.add(i-1, Card().create(i))
  }
    for (i in 1..cardList.size)
   tiDebil(i-1, cardList)
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
        } else {
            println("Wrong. The right answer is \"${cardList[i].definition}\".")
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

class Card(var term: String = "", var definition: String= "") {
    fun create(i: Int): Card {
        print("Card #$i:\n> ")
          term = readln()
          while (checkRepeat(term)) {
              println("The term \"$term\" already exists. Try again:")
              term = readln()
          }
        print("The definition for card #$i:\n> ")
        definition = readln()
        while (checkRepeat(definition)) {
            println("The definition \"$definition\" already exists. Try again:")
             definition = readln()
            }
        return Card(term, definition)
    }
}

