import java.lang.Exception

fun main() {
    val productType = readLine()!!
    val price = readLine()!!
    val product = Product(price.toInt(), productType)

    println(totalPrice(product.chooseType()).toInt())
}

open class Product(val price: Int, val type: String, val tax: Double = 0.0) {
    fun chooseType(): Product {
        return when (type) {
            "headphones" -> {
                Headphones(type, price)
            }
            "smartphone" -> {
                Smartphone(type, price)
            }
            "tv" -> {
                Tv(type, price)
            }
            "laptop" -> {
                Laptop(type, price)
            }
            else -> throw Exception("Wrong type")
        }
    }
}

class Headphones(type: String, price: Int, tax: Double = 0.11) : Product(price, type, tax)
class Smartphone(type: String, price: Int, tax: Double = 0.15) : Product(price, type, tax)
class Tv(type: String, price: Int, tax: Double = 0.17) : Product(price, type, tax)
class Laptop(type: String, price: Int, tax: Double = 0.19) : Product(price, type, tax)

fun totalPrice(product: Product): Double {
    return product.price + product.price * product.tax
}
