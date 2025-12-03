import kotlin.math.max

fun main() {


    // given multiple lines of strings, calculate the max 2-digit value per line
    // by keeping the order and using string concatenation of the two highest digits
    // return the sum of all max 2-digit values
    fun part1(input: List<String>): Int {
        val maxValues = input.map maxVals@ { line ->
            val digits = line.map { it.digitToInt() }
            val maxTen = digits.dropLast(1).max()
            val maxTenIdx = (digits.dropLast(1).indexOfFirst { it == maxTen }) + 1
            val maxOne = digits.drop(maxTenIdx).max()
            println("$maxTen$maxOne for line: $line")
            return@maxVals maxTen * 10 + maxOne
        }
        return maxValues.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

// test input per description from `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 357)
    //check(part2(testInput) == 1)

// aoc-personal-data from `input/Day03.txt` file:
    val input = readInput("Day03")
    //check(part1(input) == 44487518055)
    //check(part2(input) == 53481866137)


    println("########\n part 1:")
    println("P1 start: ${java.time.LocalDateTime.now()}")
    part1(input).println()
    println("P1 end: ${java.time.LocalDateTime.now()}")
    println("########\n part 2:")
    println("P2 start: ${java.time.LocalDateTime.now()}")
    part2(input).println()
    println("P2 end: ${java.time.LocalDateTime.now()}")
}