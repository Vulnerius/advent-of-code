package llmReworks

import println
import readInput
import java.time.LocalDateTime

/*
    after verifying my answers were correct, I asked ChatGPT to
    "make this kotlin code more clean ** my code here **"
 */

// Max 2-digit value per line
fun day03part1(input: List<String>): Int =
    input.sumOf { line ->
        val digits = line.map(Char::digitToInt)

        val tens = digits.dropLast(1).max()
        val tensIdx = digits.indexOfFirst { it == tens } + 1
        val ones = digits.drop(tensIdx).max()

        tens * 10 + ones
    }

// Max 12-digit value per line
fun day03part2(input: List<String>): Long =
    input.sumOf { line ->
        val digits = line.map(Char::digitToInt)
        val builder = StringBuilder()
        var start = 0

        repeat(12) { pos ->
            val remaining = 11 - pos
            val window = digits.drop(start).dropLast(remaining)
            val current = window.max()

            start += window.indexOfFirst { it == current } + 1
            builder.append(current)
        }

        builder.toString().toLong()
    }


fun main() {
    printd03()
}

fun printd03() {
// test input per description from `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(day03part1(testInput) == 357)
    check(day03part2(testInput) == 3121910778619L)

// aoc-personal-data from `input/Day02.txt` file:
    val input = readInput("Day03")
    check(day03part1(input) == 17229)
    check(day03part2(input) == 170520923035051L)

    println("########\n part 1:")
    println("P1 start: ${LocalDateTime.now()}")
    day03part1(input).println()
    println("P1 end: ${LocalDateTime.now()}")
    println("########\n part 2:")
    println("P2 start: ${LocalDateTime.now()}")
    day03part2(input).println()
    println("P2 end: ${LocalDateTime.now()}")
}