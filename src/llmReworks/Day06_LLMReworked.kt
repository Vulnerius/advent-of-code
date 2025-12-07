import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = Path("input/Day06.txt").readText().trimIndent().lines()

    val rows = input.map { it.toList() }
    val numRows = rows.size
    val numCols = rows.maxOf { it.size }

    // Identify problem boundaries (columns of all spaces separate problems)
    val boundaries = mutableListOf<Pair<Int, Int>>()
    var start: Int? = null
    for (c in 0 until numCols) {
        val isEmpty = (0 until numRows).all { r -> rows[r][c] == ' ' }
        if (isEmpty) {
            if (start != null) {
                boundaries.add(start to c - 1)
                start = null
            }
        } else {
            if (start == null) start = c
        }
    }
    if (start != null) boundaries.add(start to numCols - 1)

    var grandTotal = 0L

    // Process each problem right-to-left
    for ((left, right) in boundaries.reversed()) {
        val problemCols = (left..right).map { c -> rows.map { it[c] } }

        // Bottom row contains operator
        val opChar = problemCols.map { it[numRows - 1] }.first { it == '+' || it == '*' }

        // Reconstruct numbers per column
        val numbers = mutableListOf<Long>()

        for (col in problemCols.reversed()) { // right-to-left
            val digits = col.take(numRows - 1) // exclude operator row
            val digitStr = digits.joinToString("").replace(" ", "")
            if (digitStr.isNotEmpty()) {
                numbers.add(digitStr.toLong())
            }
        }

        // Apply operator
        val result = when (opChar) {
            '+' -> numbers.sum()
            '*' -> numbers.fold(1L) { acc, n -> acc * n }
            else -> 0L
        }

        grandTotal += result
    }

    println("Grand total: $grandTotal")
}
