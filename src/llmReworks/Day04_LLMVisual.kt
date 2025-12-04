import java.lang.Thread.sleep

fun main() {
    val input = readInput("Day04_test")

    runCleaningVisualizer(input)
}

fun runCleaningVisualizer(source: List<String>) {
    val grid = source.map { it.toCharArray() }.toTypedArray()
    var iteration = 0
    var cleanedThisRound: Int

    fun countAdj(r: Int, c: Int, limitToFive: Boolean): Int {
        var count = 0
        for (dr in -1..1) {
            for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                if (limitToFive && c == 0 && dc == -1) continue
                if (limitToFive && c == grid[0].lastIndex && dc == 1) continue

                val nr = r + dr
                val nc = c + dc
                if (nr in grid.indices && nc in grid[0].indices && grid[nr][nc] == '@') count++
            }
        }
        return count
    }

    fun canClean(r: Int, c: Int): Boolean {
        val edge = (c == 0 || c == grid[0].lastIndex)
        return countAdj(r, c, edge) < 4
    }

    fun printGrid(g: Array<CharArray>) {
        for (row in g) {
            println(row.concatToString())
        }
    }

    println("INITIAL GRID")
    printGrid(grid)
    println()

    do {
        iteration++
        cleanedThisRound = 0
        val toClean = mutableListOf<Pair<Int, Int>>()

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == '@') {
                    val corner = (r == 0 || r == grid.lastIndex) &&
                            (c == 0 || c == grid[0].lastIndex)
                    if (corner || canClean(r, c)) {
                        toClean += r to c
                    }
                }
            }
        }

        for ((r, c) in toClean) {
            grid[r][c] = '.'
            cleanedThisRound++
        }
        sleep(1000)

        println("ITERATION $iteration – cleaned $cleanedThisRound")
        printGrid(grid)
        println()

    } while (cleanedThisRound > 0)

    println("DONE – no more '@' can be removed")
}
