package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const MAX = 100

type Student struct {
	id    string
	fName string
	lName string
	yob   int
	gpa   float32
}

var students []Student

func showMenu() {
	fmt.Println("\n==============================")
	fmt.Println("Student Management Application")
	fmt.Println("1. Add a new student")
	fmt.Println("2. Search student by id")
	fmt.Println("3. Delete student by id")
	fmt.Println("4. Export highest gpa list")
	fmt.Println("5. Print student list")
	fmt.Println("6. Sort student list by fName ascending")
	fmt.Println("7. Quit")
}

func addStudent() {
	reader := bufio.NewReader(os.Stdin)
	var s Student

	fmt.Print("Id   : ")
	s.id = readInput(reader)

	fmt.Print("fName: ")
	s.fName = readInput(reader)

	fmt.Print("lName: ")
	s.lName = readInput(reader)

	fmt.Print("Yob  : ")
	yobStr := readInput(reader)
	s.yob, _ = strconv.Atoi(yobStr)

	fmt.Print("gpa  : ")
	gpaStr := readInput(reader)
	gpa, _ := strconv.ParseFloat(gpaStr, 32)
	s.gpa = float32(gpa)

	students = append(students, s)
	fmt.Println("Student added successfully!")
}

func readInput(reader *bufio.Reader) string {
	input, _ := reader.ReadString('\n')
	return strings.TrimSpace(input)
}

func printStudentList() {
	if len(students) == 0 {
		fmt.Println("No students to display.")
		return
	}

	fmt.Println("\nID         | First Name | Last Name | YOB  | GPA ")
	for _, s := range students {
		fmt.Printf("%-10s | %-10s | %-10s | %4d | %5.2f\n", s.id, s.fName, s.lName, s.yob, s.gpa)
	}
}

func findIndexById(id string) int {
	for i, s := range students {
		if s.id == id {
			return i
		}
	}
	return -1
}

func printStudent(s Student) {
	fmt.Printf("\n%-10s | %-10s | %-10s | %4d | %5.2f\n", s.id, s.fName, s.lName, s.yob, s.gpa)
}

func removeStudentByPos(pos int) {
	students = append(students[:pos], students[pos+1:]...)
	fmt.Println("Student removed successfully!")
}

func main() {
	reader := bufio.NewReader(os.Stdin)
	var choice int

	for {
		showMenu()
		fmt.Print("\nPlease input your choice: ")
		choiceStr := readInput(reader)
		choice, _ = strconv.Atoi(choiceStr)

		switch choice {
		case 1:
			fmt.Println("\nAdd New Student")
			addStudent()
		case 2:
			fmt.Println("\nSearch student by id")
			fmt.Print("Input Id: ")
			id := readInput(reader)
			pos := findIndexById(id)
			if pos == -1 {
				fmt.Println("Student doesn't exist.")
			} else {
				printStudent(students[pos])
			}
		case 3:
			fmt.Println("\nDelete student by id")
			fmt.Print("Input Id: ")
			id := readInput(reader)
			pos := findIndexById(id)
			if pos == -1 {
				fmt.Println("Student doesn't exist.")
			} else {
				printStudent(students[pos])
				removeStudentByPos(pos)
			}
		case 5:
			fmt.Println("\nPrint Student List")
			printStudentList()
		case 7:
			fmt.Println("Goodbye!")
			return
		default:
			fmt.Println("Your choice must be between 1 and 7.")
		}
	}
}
