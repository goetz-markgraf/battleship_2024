fun main() {
    val rows = 10
    val cols = 10
    val matrix = Array(rows) { Array(cols) { " " } }


    for (i in 0 until rows) {
        for (j in 0 until cols) {
            matrix[i][j] = " "
        }
    }


    print("   ")
    for (i in 1..cols) {
        print("$i ")
    }
    println()
    val letters= ('A'..'J').toList()
    for(i in 0 until rows){
        print("${letters[i]}|")
        for(j in 0 until cols){
            print("${matrix[i][j]}|")
        }
        println()
    }

}