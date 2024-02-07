fun main() {

    val fields = createFields()

    showField(fields.first)

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

    print("\t  ")
    for (i in '0'..'9') {
        print("$i   ")
    }
    println()

    for (i in 0 ..< 10) {
        print(" ${(i + 65).toChar()}  |")
        for (j in 0 ..< 10) {
            print(" ${69.toChar()} |")
        }
        println()
    }
}

fun place(field: Array<Array<Int>>): Array<Array<Int>> {
    return field
}

fun check(): Boolean? {
    return null
}