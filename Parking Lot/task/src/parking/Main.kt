package parking

import java.io.InputStream
import java.util.*

//private const val TAG = "MainActivity"

class Car(val regNumber: String = "", val color: String = "")

class Order(val command: String) {
    var initSpaces: Int = 0
    lateinit var regNum: String
    lateinit var color: String
    lateinit var spot: String
    lateinit var requestStatus: String
}

object ParkingLot {
    var numOfSpaces = 0
    var isParkingLotCreated = false
    var isParkingLotEmpty = true
    const val create = "create"
    const val occupied = "occupied"
    const val park = "park"
    const val leave = "leave"
    const val exit = "exit"
    const val parkingLotStatus = "status"
    const val regByColor = "reg_by_color"
    const val spotByColor = "spot_by_color"
    const val spotByReg = "spot_by_reg"
    var parkingLotSpaces: Array<Pair<String?, Car?>> = Array(numOfSpaces) { Pair(null, null) }

    fun start() {
        do {
            val input = takeOrder(System.`in`)
        } while (interaction(createOrder(input)) != exit)
    }

    fun takeOrder(input: InputStream): String {
        return Scanner(input).nextLine().toString()
    }

    fun createOrder(order: String): Order {
        if (order.isEmpty()) println("order is empty")
        if (order.isBlank()) println("order is blank")
        if (order == "") println("order is \"\"")
        val splitInputs = order.trimEnd().split(" ")
        if (splitInputs[0] != create && !isParkingLotCreated) {
            val incorrectOrder = Order(splitInputs[0])
            incorrectOrder.requestStatus = "invalid"
            return incorrectOrder
        }
        when (splitInputs[0]) {
            create -> {
                val createOrder = Order(splitInputs[0])
                createOrder.initSpaces = splitInputs[1].toInt()
                createOrder.requestStatus = "valid"
                return createOrder
            }
            parkingLotStatus -> {
                val statusOrder = Order(splitInputs[0])
                statusOrder.requestStatus = "valid"
                return statusOrder
            }
            park -> {
                val parkOrder = Order(splitInputs[0])
                parkOrder.regNum = splitInputs[1]
                parkOrder.color = splitInputs[2]
                parkOrder.requestStatus = "valid"
                return parkOrder
            }
            leave -> {
                val retrieveOrder = Order(splitInputs[0])
                retrieveOrder.spot = splitInputs[1]
                retrieveOrder.requestStatus = "valid"
                return retrieveOrder
            }
            exit -> {
                val exitOrder = Order(splitInputs[0])
                exitOrder.requestStatus = "valid"
                return exitOrder
            }
            regByColor -> {
                val regByColorOrder = Order(splitInputs[0])
                regByColorOrder.color = splitInputs[1]
                regByColorOrder.requestStatus = "valid"
                return regByColorOrder
            }
            spotByColor -> {
                val spotByColorOrder = Order(splitInputs[0])
                spotByColorOrder.color = splitInputs[1]
                spotByColorOrder.requestStatus = "valid"
                return spotByColorOrder
            }
            spotByReg -> {
                val spotByRegOrder = Order(splitInputs[0])
                spotByRegOrder.regNum = splitInputs[1]
                spotByRegOrder.requestStatus = "valid"
                return spotByRegOrder

            }

            else -> {
                val incorrectOrder = Order(splitInputs[0])
                incorrectOrder.requestStatus = "invalid"
                return incorrectOrder
            }
        }
    }

    // takes the input order, compares it to the consts and calls the corresponding function
    fun interaction(order: Order): String {
        if (order.command == exit) return exit

        if (order.command != create && !isParkingLotCreated) {
            println("Sorry, a parking lot has not been created.")
            return "invalid"
        }
        if (order.command == create) {
            createParkingLot(order)
            return create
        }
        if (order.command == parkingLotStatus) {
            parkingLotStatus()
            return parkingLotStatus
        }
        if (order.command == park) {
            park(Car(order.regNum, order.color))
            return park
        }
        if (order.command == leave) {
            leave(order)
            return leave
        }
        if (order.command == regByColor) {
            regByColor(order)
            return regByColor
        }
        if (order.command == spotByColor) {
            spotByColor(order)
            return spotByColor

        }
        if (order.command == spotByReg) {
            spotByReg(order)
            return spotByReg

        }
        return "invalid"
    }

    // TODO implement functions reg_by_color / spot_by_color / spot_by_reg
    // returns the spot using the registration
    private fun spotByReg(order: Order) {
//        TODO check what happens when the registration doesn't exist in the parkingLotSpaces array
        for ((index, space) in parkingLotSpaces.withIndex()) {
            if (order.regNum == space.second?.regNumber) {
                println(index + 1)
                return
            }
            println("No cars with registration number ${order.regNum} were found")
        }
//        println("${order.command} called, with reg ${order.regNum}")
    }

    //returns a list of spots separated by a comma, using the color
    private fun spotByColor(order: Order) {
//        TODO("Not yet implemented")
//        val stringBuilder = StringBuilder()
        var spots = mutableListOf<Int>()
        for ((index, space) in parkingLotSpaces.withIndex()) {
            if (space.second?.color == order.color) {
                spots.add(index + 1)
            }
        }
        println(spots.toString())
//        println("${order.command} called with color ${order.color}")

    }

    //returns a list of registrations separated by a comma using the color
    private fun regByColor(order: Order) {
//        TODO("Not yet implemented")
        println("${order.command} called with color ${order.color}")
    }


    fun createParkingLot(order: Order) {
        parkingLotSpaces = Array(order.initSpaces) { Pair(null, null) }
        isParkingLotCreated = true
        isParkingLotEmpty = true
        println("Created a parking lot with ${order.initSpaces} spots.")
    }

    fun parkingLotStatus() {
        for (item in parkingLotSpaces) {
            if (item.first == occupied) {
                isParkingLotEmpty = false
            }
        }
        if (isParkingLotEmpty) println("Parking lot is empty.") else {
            val parkedCarsList = mutableListOf<Pair<String?, Car?>>()
            for ((index, item) in parkingLotSpaces.withIndex()) {
                if (item.first == occupied) {
                    parkedCarsList.add(item)
                    println("${index + 1} ${item.second?.regNumber} ${item.second?.color}")
                }
            }
        }
    }

    fun park(car: Car) {
        for ((index, space) in parkingLotSpaces.withIndex()) {
            if (space.first == null) {
                parkingLotSpaces[index] = Pair(occupied, car)
                println("${car.color} car parked in spot ${index + 1}.")
                return
            }
        }
        println("Sorry, the parking lot is full.")
    }

    fun leave(order: Order) {
        if (parkingLotSpaces[order.spot.toInt() - 1].first == occupied) {
            parkingLotSpaces[order.spot.toInt() - 1] = Pair(null, null)
            println("Spot ${order.spot} is free.")
            isParkingLotEmpty = true
            for (item in parkingLotSpaces) {
                if (item.first == occupied) {
                    isParkingLotEmpty = false
                }
            }
        } else {
            println("There is no car in spot ${order.spot}.")
        }
    }

}

fun main() {
    ParkingLot.start()
}
