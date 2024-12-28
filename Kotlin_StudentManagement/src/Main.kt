data class Student(val id: Long, val name: String, val age: Int)

fun printMenu() {
    //print a menu for crud student data
    println("1. Add a new student")
    println("2. Search student by id")
    println("3. Delete student by id")
    println("4. Export highest gpa list")
    println("5. Print student list")
    println("6. Sort student list by fName ascending")
    println("0. Exit")
}

fun printList(isSort: Boolean = false, list: MutableList<Student>) {
    if (list.isEmpty()) println("Empty List") else {
        if (!isSort) {
            list.forEachIndexed { index, student ->
                println("${index + 1} | ${student.id} | ${student.name} | ${student.age} years old")
            }
        }
        list.sortByDescending { it.name }.run {
            list.forEachIndexed { index, student ->
                println("${index + 1} | ${student.id} | ${student.name} | ${student.age} years old")
            }
        }
    }
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

            2 -> {
                println("Enter the student id: ")
                val id = readLong()
                println(studentList.find { it.id == id } ?: "Not found")
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

            5 -> {
                printList(false, studentList)
            }

            6 -> {
                printList(true, studentList)
            }

            0 -> return
            else -> println("Invalid input")
        }
    } while (input != 0)
}