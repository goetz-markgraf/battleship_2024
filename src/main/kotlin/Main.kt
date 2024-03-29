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

data class Hit(
    var hits: List<Position>
)

typealias Field = Array<Array<Int>>

data class GameState(
    val field1: Field,
    val field2: Field,
    val ships1: List<Ship>,
    val ships2: List<Ship>,
    val hits1: List<Hit>,
    val hits2: List<Hit>
)

fun main() {
    var gameState = createGameState()
    val shipsLeftPlayer1 = mutableListOf(4, 3, 2, 1)
    val shipsLeftPlayer2 = mutableListOf(4, 3, 2, 1)

    showField(gameState, Player.PLAYER1)
    gameState = check(gameState, Player.PLAYER1)

    while (shipsLeftPlayer1.sum() > 0) {
        gameState = place(gameState, Player.PLAYER1, shipsLeftPlayer1)
        showField(gameState, Player.PLAYER1)
    }

    showField(gameState, Player.PLAYER2)
    while (shipsLeftPlayer2.sum() > 0) {
        gameState = place(gameState, Player.PLAYER2, shipsLeftPlayer2)
        showField(gameState, Player.PLAYER2)
    }

}

fun createGameState(): GameState {

    val field1 = Array(10) { Array(10) { 0 } }
    val field2 = Array(10) { Array(10) { 0 } }

    return GameState(field1, field2, emptyList(), emptyList(), emptyList(), emptyList())
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

fun showHits(gameState: GameState, player: Player) {
    for (row in '0'..'9') {
        print("\t  $row")
    }
    println()

    val tabVertLine = "\t${9474.toChar()}"

    val hits = if (player == Player.PLAYER1) gameState.hits1 else gameState.hits2

    for (col in 0 until 10) {
        print(" " + (col + 65).toChar() + tabVertLine)
        for (row in 0 until 10) {
            var shipFound = false
            hits.forEach {
                it.hits.forEach {
                    if (it.row == row && it.col == col) {
                        shipFound = true
                    }
                }
            }
            if (shipFound) {
                print(" X")
            } else {
                print(" O")
            }
            print(tabVertLine)
        }
        println()
    }
}

fun place(gameState: GameState, player: Player, shipsLeft: MutableList<Int>): GameState {
    print("$player, Enter ship coordinates: ")
    val location = readln().lowercase()
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

        val posList = mutableListOf<Position>()

        for (row in firstPos.col..lastPos.col) {
            for (col in firstPos.row..lastPos.row) {
                if (isCellOccupied(gameState, player, Position(col, row))) {
                    println("This cell is occupied")
                    return gameState
                } else {
                    posList.add(Position(col, row))
                    length++
                }
            }
        }
        ships.add(Ship(posList))
        changeShipsLeft(gameState, shipsLeft, length)
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
    return Pair(convert(input.substring(0, 2)), convert(input.substring(2, 4)))
}

fun convert(input: String): Position {
    val rowChar = input[0]
    val colChar = input[1]

    val row = rowChar - 'a'
    val col = colChar.toString().toInt()

    return Position(row, col)
}

fun check(gameState: GameState, player: Player): GameState {
    println("$player, Enter ship coordinates:")
    val pos = readln().lowercase()
    val hits = (if (player == Player.PLAYER1) gameState.hits1 else gameState.hits2).toMutableList()

    if (inputIsValid(Function.CHECK, pos)) {
        val coordinates = convertPair(pos)

    }
    return gameState
}

fun changeShipsLeft(gameState: GameState, shipsLeft: MutableList<Int>, length: Int): GameState {
    if (shipsLeft[length - 1] != 0) {
        shipsLeft[length - 1]--
    } else {
        println("You dont have ships of this size left")
    }
    println("single: ${shipsLeft[0]}, double: ${shipsLeft[1]}, triple: ${shipsLeft[2]}, quadruple: ${shipsLeft[3]}")
    return gameState
}

fun inputIsValid(function: Function, input: String): Boolean {
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