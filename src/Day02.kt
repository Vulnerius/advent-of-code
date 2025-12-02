fun main() {

    // given a list of strings, split each sequence by ',' and then map to data class(firstID: Int, secondID: Int) with separator '-'
    // check for invalid IDs where splitting the id in half there are duplicate sequences within the ID-range
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

    // given a list of strings, split each sequence by ',' and then map to data class(firstID: Int, secondID: Int) with separator '-'
    // check for invalid IDs where reoccurrences of a sequence of numbers indicates an invalid ID
    // get the length of the ID string, and check for all possible substring lengths from 1 to half the length of the ID
    // if an invalid id has been found, add it to a mutableList of invalid ID's and return the sum of all invalid ID's
    fun part2(input: List<String>): Long {
        var invalidIDs = 0L
        val idRanges = mutableListOf<IDRange>()

        // mapping input to List of IDRange
        input.forEach { line ->
            val parts = line.split(",")
            parts.map { part ->
                val ids = part.split("-")
                idRanges.add(IDRange(ids[0].toLong(), ids[1].toLong()))
            }
        }

        idRanges.forEach { idRange ->
            for (id in idRange.firstID..idRange.secondID) {
                val idStr = id.toString()
                val possibleLengths = 1..idStr.length / 2

                for (possibleLength in possibleLengths) {
                    val idSequence = idStr.substring(0, possibleLength)

                    idStr.chunked(possibleLength).let { chunks ->
                        if (chunks.size >= 2 && chunks.all { it == idSequence }) {
                            invalidIDs += id
                            break
                            //return@let invalidIDs
                        }
                    }
                }
            }
        }
        return invalidIDs
    }


// Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput).toInt() == 1227775554)
    check(part2(testInput).toLong() == 4174379265)
    // 33974249126
// Read the input from the `input/Day02.txt` file.
    val input = readInput("Day02")
    println("########\n part 1:")
    part1(input).println()
    println("########\n part 2:")
    part2(input).println()
}

data class IDRange(val firstID: Long, val secondID: Long)