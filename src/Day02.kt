fun main() {

    // given a list of strings, split each sequence by ',' and then map to data class(firstID: Int, secondID: Int) with separator '-'
    // check for invalid ID's where splitting the id in half there are duplicate sequences within the ID-range
    // if an invalid id has been found, add it to a mutableList of invalid ID's and return the sum of all invalid ID's
    fun part1(input: List<String>): Long {
        val invalidIDs = mutableListOf<Long>()
        val idRanges = mutableListOf<IDRange>()

        // mapping input to List of IDRange
        input.forEach { line ->
            val parts = line.split(",")
            parts.map { part ->
                val ids = part.split("-")
                idRanges.add(IDRange(ids[0].toLong(), ids[1].toLong()))
            }
        }

        // iterating IDRanges to find invalid ID's
        idRanges.forEach { idRange ->
            for (id in idRange.firstID..idRange.secondID) {
                if (id.toString().length % 2 == 0) {
                    val idStr = id.toString()
                    val halfLength = idStr.length / 2
                    if (idStr.substring(0, halfLength) == idStr.substring(halfLength)) {
                        invalidIDs.add(id)
                    }
                }
            }
        }

        return invalidIDs.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput).toInt() == 1227775554)

    // Read the input from the `input/Day02.txt` file.
    val input = readInput("Day02")
    println("########\n part 1:")
    part1(input).println()
    println("########\n part 2:")
    part2(input).println()
}

data class IDRange(val firstID: Long, val secondID: Long)