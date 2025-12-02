package llmReworks

import println
import readInput

/*
    after verifying my answers were correct, I asked ChatGPT to
    "** my code here ** make it clean and more readable as well as getting rid of quadruple for loops (if possible)"
 */

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

fun part1(input: List<String>): Long =
    mapInputToIDRanges(input)
        .asSequence()
        .flatMap { range -> (range.firstID..range.secondID).asSequence() }
        .filter(::isInvalidPart1)
        .sum()

fun part2(input: List<String>): Long =
    mapInputToIDRanges(input)
        .asSequence()
        .flatMap { range -> (range.firstID..range.secondID).asSequence() }
        .filter(::isInvalidPart2)
        .sum()


fun main() {
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("Day02")
    println("########\n part 1:")
    part1(input).println()
    println("########\n part 2:")
    part2(input).println()
}