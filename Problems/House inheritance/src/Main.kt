import java.lang.Exception

fun main() {
    val rooms = readLine()!!
    val price = readLine()!!
    val house = House(rooms.toInt(), price.toInt())
    println(totalPrice(house.chooseHouse(house)))
}

open class House(val rooms: Int, var price: Int, val coefficient: Double = 0.0) {
    fun chooseHouse(house: House): House {
        return when {
            house.rooms < 1 -> {
                Cabin(rooms, price)
            }
            house.rooms in 2..3 -> {
                Bungalow(rooms, price)
            }
            house.rooms == 4 -> {
                Cottage(rooms, price)
            }
            house.rooms in 5..7 -> {
                Mansion(rooms, price)
            }
            house.rooms > 7 -> {
                Palace(rooms, price)
            }
            else -> throw Exception("Incorrect information")
        }
    }
}

class Cabin(rooms: Int, price: Int, coefficient: Double = 1.0) : House(rooms, price, coefficient)
class Bungalow(rooms: Int, price: Int, coefficient: Double = 1.2) : House(rooms, price, coefficient)
class Cottage(rooms: Int, price: Int, coefficient: Double = 1.25) : House(rooms, price, coefficient)
class Mansion(rooms: Int, price: Int, coefficient: Double = 1.4) : House(rooms, price, coefficient)
class Palace(rooms: Int, price: Int, coefficient: Double = 1.6) : House(rooms, price, coefficient)

fun totalPrice(house: House): Int {
    if (house.price < 0) house.price = 0
    if (house.price > 1_000_000) house.price = 1_000_000
    return (house.price.toDouble() * house.coefficient).toInt()
}