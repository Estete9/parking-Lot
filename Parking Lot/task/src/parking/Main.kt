package parking

import java.io.InputStream
import java.util.*


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
    var spaces: Array<Pair<String?, Car?>> = Array(numOfSpaces) { Pair(null, null) }

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

            return "invalid"
    }


    fun createParkingLot(order: Order) {
        spaces = Array(order.initSpaces) { Pair(null, null) }
        isParkingLotCreated = true
        isParkingLotEmpty = true
        println("Created a parking lot with ${order.initSpaces} spots.")
    }

    fun parkingLotStatus() {
        for (item in spaces) {
            if (item.first == occupied) {
                isParkingLotEmpty = false
            }
        }
        if (isParkingLotEmpty) println("Parking lot is empty.") else {
            val parkedCarsList = mutableListOf<Pair<String?, Car?>>()
            for ((index, item) in spaces.withIndex()) {
                if (item.first == occupied) {
                    parkedCarsList.add(item)
                    println("${index + 1} ${item.second?.regNumber} ${item.second?.color}")
                }
            }
        }
    }

    fun park(car: Car) {
        for ((index, item) in spaces.withIndex()) {
            if (item.first == null) {
                spaces[index] = Pair(occupied, car)
                println("${car.color} car parked in spot ${index + 1}.")
                return
            }
        }
        println("Sorry, the parking lot is full.")
    }

    fun leave(order: Order) {
        if (spaces[order.spot.toInt() - 1].first == occupied) {
            spaces[order.spot.toInt() - 1] = Pair(null, null)
            println("Spot ${order.spot} is free.")
            isParkingLotEmpty = true
            for (item in spaces) {
                if (item.first == occupied) {
                    isParkingLotEmpty = false
                }
            }
        } else {
            println("There is no car in spot ${order.spot}.")
        }
    }
// TODO implement functions
//    reg_by_color / spot_by_color / spot_by_reg
}

fun main() {
    ParkingLot.start()
}
