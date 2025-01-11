using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using StudentCRUD.Entities;
using StudentCRUD.Models;

namespace StudentCRUD.AppDataContext
{
    public class CRUDDbContext : DbContext
    {
        // DbSettings field to store the connection string
        private readonly DbSettings _dbsettings;

        // Constructor to inject the DbSettings model
        public CRUDDbContext(IOptions<DbSettings> dbSettings)
        {
            _dbsettings = dbSettings.Value;
        }


        // DbSet property to represent the Todo table
        public DbSet<Student> Todos { get; set; }

        // Configuring the database provider and connection string

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer(_dbsettings.ConnectionString);
        }

        // Configuring the model for the Todo entity
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Student>()
                .ToTable("TodoAPI")
                .HasKey(x => x.Id);
        }
    }
}