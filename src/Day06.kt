fun main() {

    /*
     given a table of numbers separated by whitespace and the mathematical operators + - * in the last line
     split the table vertically to get the columns of numbers and apply the operators to the respective columns
     return the sum of the results
     */
    fun part1(input: List<String>): Long {
        val mappedNums = input.map { it.trim().split(Regex("\\s+")) }
        var result = 0L

        for (i in mappedNums[0].indices) {
            // get each first entry of each line as number except the last line
            val numbers = mappedNums.dropLast(1).map { it[i].toLong() }
            // get the operator from the last line for the respective column
            val operator = mappedNums.last()[i]

            when (operator) {
                "+" -> result += numbers.sum()
                "*" -> result += numbers.reduce { acc, j ->
                    acc * j
                }
            }
        }

        return result
    }

    /*
        now math is written right-to-left in columns.
        Each number is given in its own column, with the most significant digit at the top
         and the least significant digit at the bottom

         so we need to reverse all the lists inside the mappedNums and then reverse mappedNums itself to get the correct order
         then we can apply the same logic as in part 1
     */


    fun part2(input: List<String>): Long {
        val mappedNums = input.map { it.reversed() }.reversed().drop(1)
        val operators = input.map { it.trim() }.last().reversed()

        // get the first character of each line join the characters from last to first and then apply the operator
        // last sum all the intermediate results

        var intermediate = mutableListOf<Long>()
        var result = 0L
        val numOperations = operators.length
        var digitBuilder = ""
        for (i in 0 until numOperations) {
            mappedNums.forEachIndexed { lineId, line ->
                for (numId in mappedNums.size - 1 downTo 0) {
                    println("$i for operation $numId at lineId $lineId")
                    if(mappedNums[numId][lineId+numId] == ' ') continue
                    digitBuilder += mappedNums[numId][lineId+i]
                    //intermediate.add(mappedNums[numId][lineId+i].digitToInt().toLong())
                }
                intermediate.add(
                    if(digitBuilder.isEmpty() && operators[i] == '+') 0
                    else if(digitBuilder.isEmpty() && operators[i] == '*') 1
                    else if(digitBuilder.isBlank()) 0
                    else digitBuilder.trim().toLong())
                digitBuilder = ""
            }
            when (operators[i]) {
                '+' -> {
                    println("sum: $intermediate :: ${intermediate.sum()}")
                    result += intermediate.sum()
                }
                '*' -> {
                    println("times: $intermediate :: ${intermediate.reduce { acc, j ->
                        acc * j
                    }}")
                    result += intermediate.reduce { acc, j ->
                        acc * j
                    }
                }
            }
            intermediate.clear()
            println(result)
        }
        println(result)
        return result
    }


    // test input per description from `input/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)
// aoc-personal-data from `input/Day06.txt` file:
    val input = readInput("Day06")
    check(part1(input) == 5784380717354)
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
