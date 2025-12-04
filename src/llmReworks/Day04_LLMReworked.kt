package llmReworks

import readInput

/*
    after verifying my answers were correct, I asked ChatGPT to
    "make this code more clean and readable ** my code here **"
 */

fun main() {
    printd04()
}
fun countAdjacent(grid: Array<CharArray>, r: Int, c: Int, limitToFive: Boolean): Int {
    var count = 0
    for (dr in -1..1) {
        for (dc in -1..1) {
            if (dr == 0 && dc == 0) continue

            // exclude left/right if using 5-adjacent rule
            if (limitToFive && c == 0 && dc == -1) continue
            if (limitToFive && c == grid[0].lastIndex && dc == 1) continue

            val nr = r + dr
            val nc = c + dc
            if (nr in grid.indices && nc in grid[0].indices) {
                if (grid[nr][nc] == '@') count++
            }
        }
    }
    return count
}

fun cleanable(grid: Array<CharArray>, r: Int, c: Int): Boolean {
    val edge = (c == 0 || c == grid[0].lastIndex)
    val adj = countAdjacent(grid, r, c, limitToFive = edge)
    return adj < 4
}

fun part1(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }.toTypedArray()
    var cleaned = 0

    for (r in grid.indices) {
        for (c in grid[0].indices) {
            if (grid[r][c] == '@') {
                val corner = (r == 0 || r == grid.lastIndex) && (c == 0 || c == grid[0].lastIndex)
                if (corner || cleanable(grid, r, c)) cleaned++
            }
        }
    }

    return cleaned
}

fun part2(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }.toTypedArray()
    var cleaned = 0

    fun countAts() = grid.sumOf { row -> row.count { it == '@' } }

    var prev = countAts()
    do {
        val before = prev
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == '@') {
                    val corner = (r == 0 || r == grid.lastIndex) && (c == 0 || c == grid[0].lastIndex)
                    if (corner || cleanable(grid, r, c)) {
                        cleaned++
                        grid[r][c] = '.'
                    }
                }
            }
        }
        prev = countAts()
    } while (prev != before)

    return cleaned
}


fun printd04() {
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    check(part1(input) == 1549)
    check(part2(input) == 8887)

    println("########\n part 1:")
    val startTimeP1 = System.currentTimeMillis()
    println(part1(input))
    println("ended in: ${System.currentTimeMillis() - startTimeP1} ms")

    println("########\n part 2:")
    val startTimeP2 = System.currentTimeMillis()
    println(part2(input))
    println("ended in: ${System.currentTimeMillis() - startTimeP2} ms")
}
