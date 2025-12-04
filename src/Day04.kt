fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

// test input per description from `input/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    //check(part1(testInput) == 357)
    //check(part2(testInput) == 3121910778619L)

// aoc-personal-data from `input/Day03.txt` file:
    val input = readInput("Day03")
    //check(part1(input) == 17229)
    //check(part2(input) == 170520923035051L)


    println("########\n part 1:")
    println("P1 start: ${java.time.LocalDateTime.now()}")
    part1(input).println()
    println("P1 end: ${java.time.LocalDateTime.now()}")
    println("########\n part 2:")
    println("P2 start: ${java.time.LocalDateTime.now()}")
    part2(input).println()
    println("P2 end: ${java.time.LocalDateTime.now()}")
}