namespace StudentCRUD;

public class Utils
{
    public static int GetValidIntInput(string prompt, int min = int.MinValue,
        int max = int.MaxValue)
    {
        int input;
        while (true)
        {
            Console.Write(prompt);
            if (int.TryParse(Console.ReadLine(), out input) && input >= min && input <= max)
            {
                return input;
            }

            {
                Console.WriteLine($"Invalid input. Please enter a number between {min} and {max}.");
            }
        }
    }

    // Method to handle string input validation
    public static string GetValidStringInput(string prompt)
    {
        string input;
        while (true)
        {
            Console.Write(prompt);
            input = Console.ReadLine();
            if (!string.IsNullOrWhiteSpace(input))
            {
                return input;
            }

            {
                Console.WriteLine("Input cannot be empty. Please try again.");
            }
        }
    }

    // Method to handle double input validation for GPA
    public static double GetValidDoubleInput(string prompt)
    {
        double input;
        while (true)
        {
            Console.Write(prompt);
            if (double.TryParse(Console.ReadLine(), out input) && input >= 0 && input <= 4.0)
            {
                return input;
            }
            
            {
                Console.WriteLine("Invalid GPA. Please enter a value between 0 and 4.");
            }
        }
    }

    public static void PrintMenu()
    {
        Console.WriteLine("1. Add a New Student");
        Console.WriteLine("2. Search student by ID");
        Console.WriteLine("3. Delete student by ID");
        Console.WriteLine("4. Export highest GPA list");
        Console.WriteLine("5. Print student list");
        Console.WriteLine("6. Sort student list by first name ascending");
        Console.WriteLine("0. Exit");
    }
}