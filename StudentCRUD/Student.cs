namespace StudentCRUD;

public class Student
{
    public int Id { get; set; }
    public string FName { get; set; }
    public string LName { get; set; }
    public double Gpa { get; set; }

    public override string ToString() =>
        $"Student(Id={Id}, FName={FName}, LName={LName}, Gpa={Gpa})";
}