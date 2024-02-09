enum class Function {
    CHECK, PLACE
}

enum class Player {
    PLAYER1, PLAYER2
}

data class Position(val col: Int, val row: Int)

data class Ship(val parts: ArrayList<Position>)

typealias Field = Array<Array<Int>>

data class GameState(val field1: Field, val field2: Field, val ships1: List<Ship>, val ships2: List<Ship>)

fun main() {
    var gameState = createGameState()
    showField(gameState, Player.PLAYER1)
    while (true) {
        println("Fill")
        gameState = place(gameState, Player.PLAYER1)
        showField(gameState, Player.PLAYER1)
    }
}

fun createGameState(): GameState {
    val field1 = Array(10) { Array(10) { 0 } }
    val field2 = Array(10) { Array(10) { 0 } }

    return GameState(field1, field2, emptyList(), emptyList())
}

fun showField(gameState: GameState, player: Player) {
    for (i in '0'..'9') {
        print("\t  $i")
    }
    println()

    val tabVertLine = "\t${9474.toChar()}"

    val ships = if (player == Player.PLAYER1) gameState.ships1 else gameState.ships2

    for (row in 0 until 10) {
        print(" " + (row + 65).toChar() + tabVertLine)
        for (col in 0 until 10) {
            var shipFound = false
            ships.forEach {
                it.parts.forEach {
                    if (it.row == row && it.col == col) {
                        shipFound = true
                    }
                }
            }
            if (shipFound) {
                print("▉▉")
            }
            print(tabVertLine)
        }
        println()
    }
}

fun place(gameState: GameState, player: Player): GameState {
    val location = readLine()?.lowercase() ?: ""
    println("inside place")

    val ships = (if (player == Player.PLAYER1) gameState.ships1 else gameState.ships2).toMutableList()

    if (inputIsValid(Function.PLACE, location)) {
        val coordinates = convertPair(location)
        //if (coordinates != null) {
        val firstPos = coordinates.first
        val lastPos = coordinates.second


        println(coordinates)
        val n: ArrayList<Pair<Int, Int>> = ArrayList()

        for (i in firstPos.first..lastPos.first) {
            for (j in firstPos.second..lastPos.second) {
                println("set at $i,$j")
                //field[i][j] = 2
                n.add(Position(i, j))
            }
        }
        ships.addLast(Ship(n))
        //}
    }
    return if (player == Player.PLAYER1) {
        gameState.copy(ships1 = ships)
    } else {
        gameState.copy(ships2 = ships)
    }
}


fun convertPair(input: String): Pair<Position, Position> {
    println("in convertPair $input")
    return Pair(convert(input.substring(0, 2)), convert(input.substring(2, 4)))
}

fun convert(input: String): Position {
    println("in convert $input")
    val rowChar = input[0]
    val columnChar = input[1]
    /*if (rowChar !in 'a'..'j' || columnChar !in '0'..'9') {
        return null
    }*/

    val row = rowChar - 'a'
    val column = columnChar.toString().toInt()

    return Position(row, column)
}

fun check() {
    val pos = readLine()?.lowercase() ?: ""
    if (inputIsValid(Function.CHECK, pos)) {
    }
}

fun inputIsValid(function: Function, input: String): Boolean {
    println(function)
    when (function) {
        Function.CHECK -> {
            if (input.length == 2 && input[0] in 'a'..'j' && input[1] in '0'..'9') {
                return true
            } else {
                println("Enter the cell index in format \"letternumber\", for example \"a0a3\"")
                return false
            }
        }

        Function.PLACE -> {
            if (input.length == 4 &&
                input[0] in 'a'..'j' && input[1] in '0'..'9' &&
                input[2] in 'a'..'j' && input[3] in '0'..'9' &&
                (input[0] == input[2] || input[1] == input[3])
            ) {
                return true
            } else {
                println("Enter the cell indexes in format \"letternumber\" for placing ship, for example \"a0\"")
                return false
            }
        }
    }
}
