fun main() {


    // given multiple lines of strings, calculate the max 2-digit value per line
    // by keeping the order and using string concatenation of the two highest digits
    // return the sum of all max 2-digit values
    fun part1(input: List<String>): Int {
        val maxValues = input.map maxVals@ { line ->
            val digits = line.map { it.digitToInt() }
            val maxTen = digits.dropLast(1).max()
            val maxTenIdx = (digits.indexOfFirst { it == maxTen }) + 1
            val maxOne = digits.drop(maxTenIdx).max()
            return@maxVals maxTen * 10 + maxOne
        }
        return maxValues.sum()
    }

    // given multiple lines of strings, calculate the max 12-digit value per line
    // by keeping the order and using string concatenation of the twelve highest digits
    // return the sum of all max 12-digit values
    fun part2(input: List<String>): Long {
        val maxValues = input.map maxVals@ { line ->
            val digits = line.map { it.digitToInt() }
            var twelveBatteries = ""
            var currentIdx = 0
            for (i in 0..11) {
                val currentMax = digits.drop(currentIdx).dropLast(11-i).max()
                currentIdx = digits.drop(currentIdx).indexOfFirst { it == currentMax } + currentIdx +1
                twelveBatteries = twelveBatteries.plus(currentMax)
            }

            return@maxVals twelveBatteries.toLong()
        }

        return maxValues.sum().toLong()
    }

// test input per description from `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619L)

// aoc-personal-data from `input/Day03.txt` file:
    val input = readInput("Day03")
    check(part1(input) == 17229)
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