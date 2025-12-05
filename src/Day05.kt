import kotlin.io.path.Path
import kotlin.io.path.readLines

fun parse(input: String): List<String> {
    return Path("input/$input.txt").readLines()
}

fun eatableIdInFreshId(freshIds: Set<List<String>>, eatableId: Long): Boolean {
    for (id in freshIds) {
        val idNumbers = id.map { it.toLong() }
        if (eatableId in idNumbers.first()..idNumbers.last()) {
            return true
        }
    }
    return false
}

fun main() {

    // given some ranges num-num and a newline followed by some numbers
    // determine how many of the numbers fall into any of the ranges then count and return the sum
    fun part1(input: List<String>): Int {
        val splitIdx = input.map { it.split(" ") }.flatten().indexOfFirst { it.isEmpty() }
        val freshIds = input.subList(0, splitIdx).map { it.split("-") }.toSet()
        val eatableIds = input.subList(splitIdx + 1, input.size).map { it.toLong() }

        var eatableFood = 0

        eatableIds.forEach { food ->
            if (eatableIdInFreshId(freshIds, food)) {
                eatableFood++
            }
        }

        return eatableFood
    }

    // given some ranges num-num separated
    // determine how many numbers are covered by any of the ranges and return the sum

    // after running into java oom heap space i have to thank Sebi and Olaf for providing the idea of merging ranges
    // so shoutout to them
    fun part2(input: List<String>): Long {
        val splitIdx = input.map { it.split(" ") }.flatten().indexOfFirst { it.isEmpty() }
        val freshIds = input.subList(0, splitIdx).map { it.split("-") }
        val freshIdsLongRanges = freshIds.map { it[0].toLong()..it[1].toLong() }.toSet()

        val mergedRanges: List<LongRange> = freshIdsLongRanges.sortedBy { it.first() }.fold (emptyList<LongRange>()) { acc, next ->
            val prev = acc.lastOrNull() ?: return@fold listOf(next)

            when {
                next.first > prev.last +1 -> acc.plusElement(next)
                next.last > prev.last -> acc.dropLast(1).plusElement(prev.first..next.last)
                else -> acc
            }
        }

        return mergedRanges.sumOf { range -> range.last - range.first + 1 }
    }

    // test input per description from `input/Day03_test.txt` file:
    val testInput = parse("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

// aoc-personal-data from `input/Day03.txt` file:
    val input = readInput("Day05")
    check(part1(input) == 821)
    //check(part2(input) == 8887)


    println("########\n part 1:")
    val startTimeP1 = System.currentTimeMillis()
    part1(input).println()
    println("ended in: ${System.currentTimeMillis() - startTimeP1} ms")
    println("########\n part 2:")
    val startTimeP2 = System.currentTimeMillis()
    part2(input).println()
    println("ended in: ${System.currentTimeMillis() - startTimeP2} ms ")
}
