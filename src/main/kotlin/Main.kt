fun main() {
    val rows = 10
    val cols = 10
    val matrix = Array(rows) { Array(cols) {0} }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            matrix[i][j] = (j * cols) + i + 1
        }
    }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            print("${matrix[i][j]}\t")
        }
        println()
    }
}
