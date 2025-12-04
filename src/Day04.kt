fun main() {

    fun canBeCleaned5(lineIdx: Int, line: CharArray, charIdx: Int, input: Array<CharArray>): Boolean {
        var adjacentCount = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                if (charIdx == 0 && j == -1) continue
                if (charIdx == line.size && j == 1) continue
                val newLineIdx = lineIdx + i
                val newCharIdx = charIdx + j
                if (newLineIdx in input.indices && newCharIdx in line.indices) {
                    if (input[newLineIdx][newCharIdx] == '@') {
                        adjacentCount++
                    }
                }
            }
        }
        return adjacentCount < 4
    }

    fun canBeCleaned8(lineIdx: Int, line: CharArray, charIdx: Int, input: Array<CharArray>): Boolean {
        var adjacentCount = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val newLineIdx = lineIdx + i
                val newCharIdx = charIdx + j
                if (newLineIdx in input.indices && newCharIdx in line.indices) {
                    if (input[newLineIdx][newCharIdx] == '@') {
                        adjacentCount++
                    }
                }
            }
        }
        return adjacentCount < 4
    }

    // given a grid of dots and @ symbols
    // the @ symbols shall be cleaned if there are less than 4 @ symbols in the eight adjacent cells
    // return the number of @ symbols that can be cleaned
    fun part1(input: List<String>): Int {
        var cleanedRolls = 0
        input.forEachIndexed lineRolls@{ lineIdx, line ->
            line.forEachIndexed paperRolls@{ charIdx, char ->
                if (char == '@') {
                    if ((lineIdx == 0 || lineIdx == input.lastIndex) && (charIdx == 0 || charIdx == line.lastIndex)) {
                        // corner case can always be cleaned
                        cleanedRolls++
                        return@paperRolls
                    } else if (charIdx == 0 || charIdx == line.lastIndex) {
                        // start and end of line case : check 5 adjacent cells
                        if (canBeCleaned5(
                                lineIdx,
                                line.toCharArray(),
                                charIdx,
                                input.map { it.toCharArray() }.toTypedArray()
                            )
                        ) {
                            cleanedRolls++
                        }
                        return@paperRolls
                    }
                    // general case: check all 8 adjacent cells
                    if (canBeCleaned8(
                            lineIdx,
                            line.toCharArray(),
                            charIdx,
                            input.map { it.toCharArray() }.toTypedArray()
                        )
                    ) {
                        cleanedRolls++
                    }
                }
            }
        }
        return cleanedRolls
    }

    // given a grid of dots and @ symbols
    // the @ symbols shall be cleaned if there are less than 4 @ symbols in the eight adjacent cells
    // repeat this cleaning process until no more @ symbols can be cleaned
    // return the number of @ symbols that were cleaned in total

    //had to ask copilot for help here:
    // issue was giving input to the canBeCleaned functions therefore no updates were happening
    // and I had some of by one mistakes using line.size instead of line.lastIndex
    fun part2(input: List<String>): Int {
        var cleanedRolls = 0

        var mappedArray = input.map { row -> row.toCharArray() }.toTypedArray()

        //while atsInArray is changing per iteration, keep going
        var atsInArray = mappedArray.sumOf { line -> line.count { char -> char == '@' } }
        do {
            val initialAtsInArray = atsInArray
            mappedArray.forEachIndexed lineRolls@{ lineIdx, line ->
                line.forEachIndexed paperRolls@{ charIdx, char ->
                    if (char == '@') {
                        if ((lineIdx == 0 || lineIdx == line.lastIndex) && (charIdx == 0 || charIdx == line.lastIndex)) {
                            // corner case can always be cleaned
                            println("found cleanable at $lineIdx , $charIdx")
                            cleanedRolls++
                            mappedArray[lineIdx][charIdx] = '.'
                            return@paperRolls
                        }
                        if (charIdx == 0 || charIdx == line.lastIndex) {
                            if (canBeCleaned5(lineIdx, line, charIdx, mappedArray)) {
                                cleanedRolls++
                                mappedArray[lineIdx][charIdx] = '.'
                            }
                            return@paperRolls
                        }
                        // general case: check all 8 adjacent cells
                        if (canBeCleaned8(lineIdx, line, charIdx, mappedArray)) {
                            println("found cleanable at $lineIdx , $charIdx")
                            cleanedRolls++
                            if (mappedArray[lineIdx][charIdx] == '.') error("math aint mathing")
                            mappedArray[lineIdx][charIdx] = '.'
                        }
                    }
                }
            }
            atsInArray = mappedArray.sumOf { line -> line.count { char -> char == '@' } }
        } while (atsInArray != initialAtsInArray)
        return cleanedRolls
    }

// test input per description from `input/Day03_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

// aoc-personal-data from `input/Day03.txt` file:
    val input = readInput("Day04")
    check(part1(input) == 1549)
    check(part2(input) == 8887)


    println("########\n part 1:")
    val startTimeP1 = System.currentTimeMillis()
    part1(input).println()
    println("ended in: ${System.currentTimeMillis() - startTimeP1} ms")
    println("########\n part 2:")
    val startTimeP2 = System.currentTimeMillis()
    part2(input).println()
    println("ended in: ${System.currentTimeMillis() - startTimeP2} ms ")
}
