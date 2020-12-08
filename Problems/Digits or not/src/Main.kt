import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    fun checkDigit(char: Char): Boolean {
        return char in '\u0030'..'\u0039'
    }
    print("${checkDigit(scanner.next()[0])}\\${checkDigit(scanner.next()[0])}\\")
    print("${checkDigit(scanner.next()[0])}\\${checkDigit(scanner.next()[0])}")
//    for (char in chars) {
//        print(if (char.single().isDigit()) "true" else "false")
//        print(if (chars.lastIndex != chars.indexOf(char)) "\\" else "")
//    }
}