import kotlin.math.abs

enum class Function {
    CHECK, PLACE
}

fun main() {
    val fields = createFields()
    showField(fields.first)
    place(fields.first)
    while (true) {

    }

}

fun createFields(): Pair<Array<Array<Int>>, Array<Array<Int>>> {

    val field1 = Array(10) {Array(10) {0} }
    val field2 = Array(10) {Array(10) {0} }

    for (i in 0 ..< 10) {
        for (j in 0 ..< 10) {
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

    for (i in 0 ..< 10) {
        print(" " + (i + 65).toChar() + tabVertLine)
        for (j in 0 ..< 10) {
            print(tabVertLine)
        }
        println()
    }
}

fun place(field: Array<Array<Int>>): Array<Array<Int>> {
    val location = readln().lowercase()
    if (inputIsValid(Function.PLACE, location)) {


    }
    return field
}
fun convert(input: String): String {
    return input.map{
        ('a'..'j').indexOf(it).let{
            index->
        if (index !=-1) index.toString()
        else it.toString()
        }
    }.joinToString("")
}

fun check() {
    val pos = readln().lowercase()
    if (inputIsValid(Function.CHECK, pos)) {

    }
}

fun inputIsValid(function: Function, input: String): Boolean {
    when (function) {
        Function.CHECK ->
            if (input[0] in 'a'..'j' && input[1] in '0'..'9') {
                return true
            } else {
                println("Enter the cell index in format \"letternumber\", for example \"a0\"")
                return false
            }

        Function.PLACE ->
            if (input[0] in 'a'..'j' && input[1] in '0'..'9' &&
                input[2] in 'a'..'j' && input[3] in '0'..'9' &&
                abs(input[0] - input[2]) <= 3 &&
                abs(input[1] - input[3]) <= 3) {
                return true
            } else {
                println("Enter the cell indexes in format \"letternumberletternumber\" with the ship not expanding in both directions, for example \"a0d0\", \"b3b5\"")
                return false
            }
    }
}


fun gameState(): Boolean? {
    return null
}