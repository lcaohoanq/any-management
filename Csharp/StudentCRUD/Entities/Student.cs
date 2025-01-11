using System.ComponentModel.DataAnnotations;

namespace StudentCRUD.Entities
{
    public class Student
    {
        [Key]
        public Guid Id { get; set; }
        public string FName { get; set; }
        public string LName { get; set; }
        public double gpa {  get; set; }
        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
    }
}
