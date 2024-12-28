data class Student(val id: Long, val name: String, val age: Int)

fun printMenu() {
    //print a menu for crud student data
    println("1. View Student")
    println("2. Add Student")
    println("3. Remove Student")
    println("0. Exit")
}

fun main() {
    val studentList = mutableListOf<Student>()
    do {
        printMenu()
        //read user input
        println("Enter your choice:")
        val input = readInt()
        println("You entered: $input")
        when (input) {
            1 -> {
                if (studentList.isEmpty()) println("Empty List") else studentList.forEachIndexed { index, student ->
                    println("${index + 1} | (${student.id}) | ${student.name} | ${student.age} years old")
                }
            }

            2 -> {
                var id: Long
                while (true) {
                    println("Enter student id:")
                    id = readLong()
                    if (!studentList.any { it.id == id }) break else println("Student with id $id already exists")
                }

                println("Enter student name:")
                val name = readString()
                println("Enter student age:")
                val age = readInt()

                studentList.add(Student(id, name, age))
            }

            3 -> {
                println("Enter student id:")
                val id = readLong()
                val student = studentList.find { it.id == id }
                if (student == null) println("Student with id $id not found")
                else {
                    studentList.remove(student)
                    println("Student with id $id has been removed")
                }
            }

            0 -> return
            else -> println("Invalid input")
        }
    } while (input != 0)
}