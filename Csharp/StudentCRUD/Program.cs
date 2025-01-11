
using StudentCRUD.AppDataContext;
using StudentCRUD.Models;

namespace StudentCRUD
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            builder.Services.Configure<DbSettings>(builder.Configuration.GetSection("DbSettings")); // Add this line
            builder.Services.AddSingleton<CRUDDbContext>(); // Add this line

            var app = builder.Build();

            {
                using var scope = app.Services.CreateScope(); // Add this line
                var context = scope.ServiceProvider; // Add this line
            }   

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseHttpsRedirection();

            app.UseAuthorization();


            app.MapControllers();

            app.Run();
        }
    }
}
