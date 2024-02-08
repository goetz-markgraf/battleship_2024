import kotlin.math.abs

enum class Function {
    CHECK, PLACE
}

fun main() {
    val fields = createFields()
    showField(fields.first)
    while (true) {
        println("Fill")
        place(fields.first)
    }
}

fun createFields(): Pair<Array<Array<Int>>, Array<Array<Int>>> {
    val field1 = Array(10) { Array(10) { 0 } }
    val field2 = Array(10) { Array(10) { 0 } }

    for (i in 0 until 10) {
        for (j in 0 until 10) {
            field1[i][j] += j + i * 10
            field2[i][j] += j + i * 10
        }
    }

    return Pair(field1, field2)
}

fun showField(field: Array<Array<Int>>) {
    for (i in '0'..'9') {
        print("\t  $i")
    }
    println()

    val tabVertLine = "\t${9474.toChar()}"

    for (i in 0 until 10) {
        print(" " + (i + 65).toChar() + tabVertLine)
        for (j in 0 until 10) {
            print(tabVertLine)
        }
        println()
    }
}

fun place(field: Array<Array<Int>>): Array<Array<Int>> {
    val location = readLine()?.lowercase() ?: ""
    if (inputIsValid(Function.PLACE, location)) {
        val coordinates = convert(location)
        if (coordinates != null) {
            val row = coordinates.first
            val column = coordinates.second

            field[row][column] = 1

            val shipLength = 3
            if (row + shipLength <= 10) {
                for (i in row until row + shipLength) {
                    field[i][column] = 2
                }
            } else {
                println("Very long")
            }
        }
    }
    return field
}

fun convert(input: String): Pair<Int, Int>? {
    if (input.length != 2) {
        return null
    }
    val rowChar = input[0]
    val columnChar = input[1]
    if (rowChar !in 'a'..'j' || columnChar !in '0'..'9') {
        return null
    }

    val row = rowChar - 'a'
    val column = columnChar.toString().toInt()

    return Pair(row, column)
}

fun check() {
    val pos = readLine()?.lowercase() ?: ""
    if (inputIsValid(Function.CHECK, pos)) {
    }
}

fun inputIsValid(function: Function, input: String): Boolean {
    when (function) {
        Function.CHECK -> {
            if (input.length == 2 && input[0] in 'a'..'j' && input[1] in '0'..'9') {
                return true
            } else {
                println("Enter the cell index in format \"letternumber\", for example \"a0\"")
                return false
            }
        }

        Function.PLACE -> {
            if (input.length == 2 &&
                input[0] in 'a'..'j' && input[1] in '0'..'9') {
                return true
            } else {
                println("Enter the cell indexes in format \"letternumber\" for placing ship, for example \"a0\"")
                return false
            }
        }
    }
}