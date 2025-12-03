package llmReworks

import println
import readInput

/*
    after verifying my answers were correct, I asked ChatGPT to
    "** my code here **
    make it clean and more readable as well as getting rid of quadruple for loops (if possible)"
 */

fun main() {
    print()
}

data class IDRange(val firstID: Long, val secondID: Long)

fun mapInputToIDRanges(input: List<String>): List<IDRange> =
    input.flatMap { line ->
        line.split(",").map { part ->
            val (start, end) = part.split("-")
            IDRange(start.toLong(), end.toLong())
        }
    }

private fun isInvalidPart1(id: Long): Boolean {
    val idStr = id.toString()
    if (idStr.length % 2 != 0) return false
    val half = idStr.length / 2
    return idStr.substring(0, half) == idStr.substring(half)
}

private fun isInvalidPart2(id: Long): Boolean {
    val idStr = id.toString()
    val maxLen = idStr.length / 2

    for (len in 1..maxLen) {
        if (idStr.length % len != 0) continue
        val chunk = idStr.substring(0, len)
        if (idStr.chunked(len).all { it == chunk }) return true
    }
    return false
}

fun day02part1(input: List<String>): Long =
    mapInputToIDRanges(input)
        .asSequence()
        .flatMap { range -> (range.firstID..range.secondID).asSequence() }
        .filter(::isInvalidPart1)
        .sum()

// runtime over 3 times: 550ms

fun day02part2(input: List<String>): Long =
    mapInputToIDRanges(input)
        .asSequence()
        .flatMap { range -> (range.firstID..range.secondID).asSequence() }
        .filter(::isInvalidPart2)
        .sum()

fun print() {
// test input per description from `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(day02part1(testInput).toInt() == 1227775554)
    check(day02part2(testInput).toLong() == 4174379265)

// aoc-personal-data from `input/Day02.txt` file:
    val input = readInput("Day02")
    check(day02part1(input) == 44487518055)
    check(day02part2(input) == 53481866137)

    println("########\n part 1:")
    println("P1 start: ${java.time.LocalDateTime.now()}")
    day02part1(input).println()
    println("P1 end: ${java.time.LocalDateTime.now()}")
    println("########\n part 2:")
    println("P2 start: ${java.time.LocalDateTime.now()}")
    day02part2(input).println()
    println("P2 end: ${java.time.LocalDateTime.now()}")
}