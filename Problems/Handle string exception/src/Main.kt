fun main() {
    val index = readLine()!!.toInt()
    val word = readLine()!!
    if (word.getOrNull(index) == null) println("There isn't such an element " + 
    "in the given string, please fix the index!") else println(word[index])    
}
