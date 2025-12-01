fun main() {

    // start at dial 50
    // given ("L|RanyNumber"), L for decreasing the dial and R for increasing the dial
    // if the dial goes below 0, it wraps around to 99
    // if the dial goes above 99, it wraps around to 0
    // count all times the dial lands on zero and return that count
    fun part1(input: List<String>): Int {
        var dial = 50
        var password = 0
        for (instruction in input) {
            val direction = instruction[0].toString()
            val amountStr = instruction.drop(1).trim().toInt()
            when (direction) {
                "L" -> dial = (dial - amountStr).mod(100)
                "R" -> dial = (dial + amountStr).mod(100)
            }
            if (dial == 0)
                password++
        }
        return password
    }

    // start at dial 50
    // given ("L|RAnyNumber"), L for decreasing the dial and R for increasing the dial
    // if the dial goes below 0, it wraps around to 99
    // if the dial goes above 99, it wraps around to 0
    // count all times the dial passes zero (not only lands on it) and return that count
    fun part2(input: List<String>): Int {
        var dial = 50
        var password = 0

        for (instruction in input) {
            val direction = if (instruction[0] == 'L') -1 else 1

            repeat(instruction.drop(1).trim().toInt()) {
                dial = (dial + direction).mod(100)
                if (dial == 0)
                    password++
            }
        }
        return password
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    println("########\n part 1:")
    part1(input).println()
    println("########\n part 2:")
    part2(input).println()
}
