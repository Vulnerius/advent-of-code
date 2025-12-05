package llmReworks

import kotlin.io.path.Path
import kotlin.io.path.readLines

/*
    as every day after confirming my solution i asked ChatGPT to
    "make this kotlin code more clean, readable and faster ** my code here **"
 */

fun parse(input: String): List<String> =
    Path("input/$input.txt").readLines()

private data class Range(val start: Long, val end: Long) {
    operator fun contains(value: Long): Boolean = value in start..end
}

private fun mergeRanges(ranges: List<Range>): List<Range> {
    if (ranges.isEmpty()) return emptyList()

    val sorted = ranges.sortedBy { it.start }
    val merged = ArrayList<Range>()

    var current = sorted[0]
    for (next in sorted.drop(1)) {
        if (next.start <= current.end + 1) {
            // extend current
            current = Range(current.start, maxOf(current.end, next.end))
        } else {
            merged += current
            current = next
        }
    }
    merged += current
    return merged
}

fun main() {

    fun parseSections(input: List<String>): Pair<List<Range>, List<Long>> {
        val splitIdx = input.indexOfFirst { it.isBlank() }
        val rangeLines = input.subList(0, splitIdx)
        val idLines = input.subList(splitIdx + 1, input.size)

        val ranges = rangeLines
            .map {
                val (a, b) = it.split("-")
                Range(a.toLong(), b.toLong())
            }

        val ids = idLines.map { it.toLong() }
        return ranges to ids
    }

    // Count how many id numbers fall into any given range
    fun part1(input: List<String>): Int {
        val (ranges, eatableIds) = parseSections(input)

        return eatableIds.count { id ->
            ranges.any { id in it }
        }
    }

    // Count the total number of values covered by merged ranges
    fun part2(input: List<String>): Long {
        val (ranges, _) = parseSections(input)
        val merged = mergeRanges(ranges)

        return merged.sumOf { it.end - it.start + 1 }
    }

    // test input
    val testInput = parse("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    // real input
    val input = parse("Day05")
    check(part1(input) == 821)
    // check(part2(input) == 8887)

    println("########\npart 1:")
    val start1 = System.currentTimeMillis()
    println(part1(input))
    println("ended in: ${System.currentTimeMillis() - start1} ms")

    println("########\npart 2:")
    val start2 = System.currentTimeMillis()
    println(part2(input))
    println("ended in: ${System.currentTimeMillis() - start2} ms")
}
