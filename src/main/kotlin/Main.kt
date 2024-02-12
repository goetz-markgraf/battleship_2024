import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

enum class Function {
    CHECK, PLACE
}

enum class Player {
    PLAYER1, PLAYER2
}

data class Position(
    val col: Int,
    val row: Int
)

data class Ship(
    var parts: List<Position>
)

typealias Field = Array<Array<Int>>

data class GameState(
    val field1: Field,
    val field2: Field,
    val ships1: List<Ship>,
    val ships2: List<Ship>
)

fun main() {
    var gameState = createGameState()
    val shipsLeftPlayer1 = mutableListOf<Int>(4, 3, 2, 1)
    val shipsLeftPlayer2 = mutableListOf<Int>(4, 3, 2, 1)
    var activePlayer = Player.PLAYER1
    showField(gameState, Player.PLAYER1)

    while (shipsLeftPlayer1.sum() > 0) {
        gameState = place(gameState, Player.PLAYER1, shipsLeftPlayer1)
        showField(gameState, Player.PLAYER1)
    }
    gameState = resetGameState()
    showField(gameState, Player.PLAYER2)

    while (shipsLeftPlayer2.sum() > 0) {
        gameState = place(gameState, Player.PLAYER2, shipsLeftPlayer2)
        showField(gameState, Player.PLAYER2)
    }

}

fun createGameState(): GameState {

    val field1 = Array(10) { Array(10) { 0 } }
    val field2 = Array(10) { Array(10) { 0 } }

    return GameState(field1, field2, emptyList(), emptyList())
}

fun resetGameState(): GameState{
    return createGameState()
}

fun showField(gameState: GameState, player: Player) {
    for (row in '0'..'9') {
        print("\t  $row")
    }
    println()

    val tabVertLine = "\t${9474.toChar()}"

    val ships = if (player == Player.PLAYER1) gameState.ships1 else gameState.ships2

    for (col in 0 until 10) {
        print(" " + (col + 65).toChar() + tabVertLine)
        for (row in 0 until 10) {
            var shipFound = false
            ships.forEach {
                it.parts.forEach {
                    if (it.row == row && it.col == col) {
                        shipFound = true
                    }
                }
            }
            if (shipFound) {
                print("██")
            }
            print(tabVertLine)
        }
        println()
    }
}

fun place(gameState: GameState, player: Player, shipsLeft: MutableList<Int>): GameState {
    print("Enter ship coordinates: ")
    val location = readln().lowercase()
    println("inside place")

    val ships = (if (player == Player.PLAYER1) gameState.ships1 else gameState.ships2).toMutableList()

    if (inputIsValid(Function.PLACE, location)) {
        val coordinates = convertPair(location)
        val firstPos = Position(
            min(coordinates.first.row, coordinates.second.row),
            min(coordinates.first.col, coordinates.second.col)
        )
        val lastPos = Position(
            max(coordinates.first.row, coordinates.second.row),
            max(coordinates.first.col, coordinates.second.col)
        )
        var length = 0

        println(coordinates)

        val n = mutableListOf<Position>()

        for (row in firstPos.col..lastPos.col) {
            for (col in firstPos.row..lastPos.row) {
                if (isCellOccupied(gameState, player, Position(col, row))) {
                    println("This cell is occupied")
                    return gameState
                } else {
                    println("set at $row, $col")
                    n.add(Position(col, row))
                    length++
                }
            }
        }
        if (shipsLeft[length - 1] == 0 ) {
            println("You dont have ships of this size left")
            return gameState
        }
            ships.add(Ship(n))
            checkShipsLeft(gameState, shipsLeft, length)
    }

    return if (player == Player.PLAYER1) {
        gameState.copy(ships1 = ships)
    } else {
        gameState.copy(ships2 = ships)
    }
}

fun isCellOccupied(gameState: GameState, player: Player, position: Position): Boolean {
    val ships = if (player == Player.PLAYER1) gameState.ships1 else gameState.ships2

    return ships.any { ship -> ship.parts.any { it == position } }
}

fun convertPair(input: String): Pair<Position, Position> {
    println("in convertPair $input")
    return Pair(convert(input.substring(0, 2)), convert(input.substring(2, 4)))
}

fun convert(input: String): Position {
    println("in convert $input")
    val rowChar = input[0]
    val columnChar = input[1]

    val row = rowChar - 'a'
    val column = columnChar.toString().toInt()

    return Position(row, column)
}

fun check() {
    val pos = readln().lowercase()
    if (inputIsValid(Function.CHECK, pos)) {

    }
}

fun checkShipsLeft(gameState: GameState, shipsLeft: MutableList<Int>, length: Int): GameState {
    if (length != 0 && shipsLeft[length - 1] != 0) {
        shipsLeft[length - 1]--
    }
    println(shipsLeft)
    return gameState
}

fun swapPlayer() {

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
                (input[0] == input[2] || input[1] == input[3]) &&
                abs(input[0] - input[2]) <= 3 &&
                abs(input[1] - input[3]) <= 3
            ) {
                return true
            } else {
                println("Enter the cell indexes in format \"letternumberletternumber\" for placing ship, for example \"a0b0\"")
                return false
            }
        }
    }
}