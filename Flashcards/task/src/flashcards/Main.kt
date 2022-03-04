package flashcards

fun main() {
  print("Input the number of cards:\n> ")
  val cardList = MutableList<Card>(readln().toInt()) { Card() }
  for (i in 1..cardList.size) {
    cardList[i-1] = Card().create(i)
  }
    for (i in 1..cardList.size)
   tiDebil(i-1, cardList)
}

fun tiDebil(i: Int, cardList: MutableList<Card>) {
    println("Print the definition of \"${cardList[i].term}\":")
    val str = readln()
    if (str == cardList[i].definition) println("Correct!")
              else println("Wrong. The right answer is \"${cardList[i].definition}\".")
}

class Card(var term: String = "", var definition: String= ""){
  fun create(i: Int): Card {
      print("Card #$i:\n> ")
      term = readln()
      print("The definition for card #$i:\n> ")
       definition = readln()
    return Card(term, definition)
  }
}
