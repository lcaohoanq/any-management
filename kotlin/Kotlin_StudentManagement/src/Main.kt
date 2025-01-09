data class Student(val id: Long, val name: String, val age: Int) {
    override fun toString() = "ID: $id | Name: $name | Age: $age years old"
}

class StudentManager {
    private val students = mutableListOf<Student>()

    fun addStudent() {
        val id = getUniqueId()
        println("Enter student name:")
        val name = readString()
        println("Enter student age:")
        val age = readInt()

        students.add(Student(id, name, age))
        println("Student added successfully!")
    }

    fun searchStudent() {
        println("Enter the student id: ")
        val id = readLong()
        students.find { it.id == id }
            ?.let { println(it) }
            ?: println("Student not found")
    }

    fun deleteStudent() {
        println("Enter student id:")
        val id = readLong()
        if (students.removeIf { it.id == id }) {
            println("Student with id $id has been removed")
        } else {
            println("Student with id $id not found")
        }
    }

    fun printStudents(sorted: Boolean = false) {
        if (students.isEmpty()) {
            println("Empty List")
            return
        }

        val studentsToPrint = if (sorted) {
            students.sortedBy { it.name }
        } else {
            students
        }

        studentsToPrint.forEachIndexed { index, student ->
            println("${index + 1}. $student")
        }
    }

    private fun getUniqueId(): Long {
        while (true) {
            println("Enter student id:")
            val id = readLong()
            if (!students.any { it.id == id }) return id
            println("Student with id $id already exists")
        }
    }
}

enum class MenuOption(val option: Int, val description: String) {
    ADD(1, "Add a new student"),
    SEARCH(2, "Search student by id"),
    DELETE(3, "Delete student by id"),
    EXPORT(4, "Export highest gpa list"),
    PRINT(5, "Print student list"),
    SORT(6, "Sort student list by name ascending"),
    EXIT(0, "Exit");

    companion object {
        fun fromInt(value: Int) = values().find { it.option == value }
    }
}

fun printMenu() {
    println("\n=== Student Management System ===")
    MenuOption.values().forEach {
        println("${it.option}. ${it.description}")
    }
    println("Enter your choice:")
}

fun main() {
    val manager = StudentManager()

    while (true) {
        printMenu()
        when (MenuOption.fromInt(readInt())) {
            MenuOption.ADD -> manager.addStudent()
            MenuOption.SEARCH -> manager.searchStudent()
            MenuOption.DELETE -> manager.deleteStudent()
            MenuOption.PRINT -> manager.printStudents()
            MenuOption.SORT -> manager.printStudents(sorted = true)
            MenuOption.EXIT -> {
                println("Goodbye!")
                return
            }
            else -> println("Invalid option. Please try again.")
        }
    }
}