import llmReworks.IDRange

fun main() {

    fun mapInputToIDRanges(input: List<String>): List<IDRange> {
        val idRanges = mutableListOf<IDRange>()
        input.forEach { line ->
            val idRange = line.split(",")
            idRange.map { idMinMax ->
                val ids = idMinMax.split("-")
                idRanges.add(IDRange(ids[0].toLong(), ids[1].toLong()))
            }
        }
        return idRanges
    }

    // given a list of strings, split each sequence by ',' and then map to data class(firstID: Int, secondID: Int) with separator '-'
    // check for invalid IDs where splitting the id in half there are duplicate sequences within the ID-range
    // if an invalid id has been found, add it to a mutableList of invalid ID's and return the sum of all invalid ID's
    fun part1(input: List<String>): Long {
        val invalidIDs = mutableListOf<Long>()
        val idRanges = mapInputToIDRanges(input)

        idRanges.flatMap { range -> (range.firstID..range.secondID) }.forEach { id ->
            val idStr = id.toString()
            val length = idStr.length
            if (length % 2 == 0) {
                val halfLength = length / 2
                if (idStr.substring(0, halfLength) == idStr.substring(halfLength)) {
                    invalidIDs.add(id)
                }
            }
        }

        return invalidIDs.sum()
    }

    // given a list of strings, split each sequence by ',' and then map to data class(firstID: Int, secondID: Int) with separator '-'
    // check for invalid IDs where reoccurrences of a sequence of numbers indicates an invalid ID
    // get the length of the ID string, and check for all possible substring lengths from 1 to half the length of the ID
    // if an invalid id has been found, add it to a mutableList of invalid ID's and return the sum of all invalid ID's
    // runtime over 3 tries: 1 second +|-
    fun part2(input: List<String>): Long {
        var invalidIDs = 0L
        val idRanges = mapInputToIDRanges(input)

        idRanges.flatMap { range -> range.firstID..range.secondID }.forEach { id ->
            val idStr = id.toString()
            val chunkSizes = 1..idStr.length / 2

            for (chunkSize in chunkSizes) {
                if (idStr.chunked(chunkSize).all { it == idStr.substring(0, chunkSize) }) {
                    invalidIDs += id
                    break
                }
            }
        }

        return invalidIDs
    }

// test input per description from `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput).toInt() == 1227775554)
    check(part2(testInput).toLong() == 4174379265)

// aoc-personal-data from `input/Day02.txt` file:
    val input = readInput("Day02")
    check(part1(input) == 44487518055)
    check(part2(input) == 53481866137)

    println("########\n part 1:")
    println("P1 start: ${java.time.LocalDateTime.now()}")
    part1(input).println()
    println("P1 end: ${java.time.LocalDateTime.now()}")
    println("########\n part 2:")
    println("P2 start: ${java.time.LocalDateTime.now()}")
    part2(input).println()
    println("P2 end: ${java.time.LocalDateTime.now()}")
}