import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val size = scanner.nextLine()
    val string = scanner.nextLine()
    val array = mutableListOf<Int>()
    var numOfTriples = 0

    string.split(" ").forEach {
        array.add(it.toInt())
    }
    for ((index, item) in array.withIndex()) {
        if (array.getOrNull(index + 2) == null) break
        if (item + 1 == array[index + 1] && item + 2 == array[index + 2]) numOfTriples++
    }
    println(numOfTriples)
}