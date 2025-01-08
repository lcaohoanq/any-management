namespace StudentCRUD
{
    class Program
    {
        static void Main(string[] args)
        {
            var studentList = new List<Student>();
            int choice = -1;

            try
            {
                do
                {
                    Utils.PrintMenu();
                    choice = Utils.GetValidIntInput("Enter your choice: ", 0, 6); // Validates the choice input

                    int searchId = 0;
                    Student s = null;

                    switch (choice)
                    {
                        case 1:
                            Student student = new Student();
                            student.Id = Utils.GetValidIntInput("Enter student id: ");
                            student.FName = Utils.GetValidStringInput("Enter student first name: ");
                            student.LName = Utils.GetValidStringInput("Enter student last name: ");
                            student.Gpa = Utils.GetValidDoubleInput("Enter student GPA: ");

                            studentList.Add(student);
                            Console.WriteLine("Added a new student successfully.");
                            break;

                        case 2:
                            searchId = Utils.GetValidIntInput("Enter student id (Integer): ");
                            s = studentList.Find(st => st.Id == searchId);
                            if (s != null)
                            {
                                Console.WriteLine($"Found student with data: {s}");
                            }
                            else
                            {
                                Console.WriteLine("Student not found");
                            }
                            break;

                        case 3:
                            searchId = Utils.GetValidIntInput("Enter student id: ");
                            s = studentList.Find(st => st.Id == searchId);
                            if (s != null)
                            {
                                studentList.Remove(s);
                                Console.WriteLine($"Deleted student successfully: {s}");
                            }
                            else
                            {
                                Console.WriteLine("Student not found");
                            }
                            break;

                        case 4:
                            Console.WriteLine("Not implemented yet");
                            break;

                        case 5:
                            PrintStudentList(studentList);
                            break;

                        case 6:
                            PrintStudentList(studentList.OrderBy(s => s.FName).ToList());
                            break;

                        case 0:
                            Console.WriteLine("Exiting program...");
                            break;

                        default:
                            Console.WriteLine("Invalid choice");
                            break;
                    }
                } while (choice != 0);
            }
            catch (Exception e)
            {
                Console.WriteLine("Error: " + e.Message);
            }
        }

        // Method to print the student list
        static void PrintStudentList(List<Student> studentList)
        {
            if (studentList.Count == 0)
            {
                Console.WriteLine("No students in the list.");
            }
            else
            {
                foreach (var student in studentList)
                {
                    Console.WriteLine(student);
                }
            }
        }
    }
}
