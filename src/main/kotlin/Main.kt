import kotlin.math.abs

enum class Function {
    CHECK, PLACE
}


data class Ship (val parts: ArrayList<Pair<Int,Int>>)

val ships: List<Ship> = mutableListOf()
fun main() {
    val fields = createFields()
    showField(fields.first)
    while (true) {
        println("Fill")
        val new = place(fields.first)
        showField(new)
    }
}

fun createFields(): Pair<Array<Array<Int>>, Array<Array<Int>>> {
    val field1 = Array(10) { Array(10) { 0 } }
    val field2 = Array(10) { Array(10) { 0 } }
/*
    for (i in 0 until 10) {
        for (j in 0 until 10) {
            field1[i][j] += j + i * 10
            field2[i][j] += j + i * 10
        }
    }
*/
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
            var shipFound = false
            ships.forEach {
                it.parts.forEach {
                    if (it.first == i && it.second == j) {
                        shipFound = true
                    }
                }
            }
            if (shipFound) {
                print("x")
            }
            print(tabVertLine)
        }
        println()
    }
}

fun place(field: Array<Array<Int>>): Array<Array<Int>> {
    val location = readLine()?.lowercase() ?: ""
    println("inside place")
    if (inputIsValid(Function.PLACE, location)) {
        val coordinates = convertPair(location)
        //if (coordinates != null) {
        val firstPos = coordinates.first
        val lastPos = coordinates.second


        println(coordinates)
        val n : ArrayList<Pair<Int,Int>> = ArrayList()

        for (i in firstPos.first .. lastPos.first) {
            for (j in firstPos.second ..  lastPos.second) {
                println("set at $i,$j")
                //field[i][j] = 2
                n.add(Pair(i,j))
            }
        }
        ships.addLast(Ship(n))
        //}
    }
    return field
}


fun convertPair(input: String): Pair<Pair<Int,Int>, Pair<Int,Int>> {
    println("in convertPair $input")
    return Pair(convert(input.substring(0,2)),  convert(input.substring(2,4)))
}
fun convert(input: String): Pair<Int, Int>{
    println("in convert $input")
    val rowChar = input[0]
    val columnChar = input[1]
    /*if (rowChar !in 'a'..'j' || columnChar !in '0'..'9') {
        return null
    }*/

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
                (input[0] == input[2] || input[1] == input[3])) {
                return true
            } else {
                println("Enter the cell indexes in format \"letternumber\" for placing ship, for example \"a0\"")
                return false
            }
        }
    }
}