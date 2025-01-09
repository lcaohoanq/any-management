package com.lcaohoanq.demo.controller;

import com.lcaohoanq.demo.api.ApiResponse;
import com.lcaohoanq.demo.dto.StudentPort;
import com.lcaohoanq.demo.model.Student;
import com.lcaohoanq.demo.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${API_PREFIX}/students")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Student APIs", description = "Operations related to Students")
@RequiredArgsConstructor
public class StudentController {

    IStudentService studentService;
    
    @GetMapping("/all")
    @Operation(
        summary = "Get all Student support pagination"
    )
    public ResponseEntity<ApiResponse<?>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ){
        return ResponseEntity.ok().body(
            ApiResponse.builder()
                .message("Get All Student successfully")
                .data(studentService.getAll(PageRequest.of(page, limit)))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    } 
    
    @GetMapping("/detail")
    @Operation(summary = "Get Student Detail")
    public ResponseEntity<ApiResponse<?>> getDetail(
        @RequestParam Long id
    ){
        return ResponseEntity.ok().body(
            ApiResponse.builder()
                .message("Get Student detail successfully")
                .data(studentService.findById(id))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
        @Valid @RequestBody StudentPort.StudentDTO studentDTO,
        BindingResult result) {
        try {

            if (result.hasErrors()) {
                List<FieldError> fieldErrorList = result.getFieldErrors();
                List<String> errorMessages = fieldErrorList
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Student student = new Student(1L, "Hoang", "123");
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
        @Valid @RequestParam Long id,
        BindingResult result) {
        try {

            if (result.hasErrors()) {
                List<FieldError> fieldErrorList = result.getFieldErrors();
                List<String> errorMessages = fieldErrorList
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            //call UserService to update user
            Student student = new Student(id, "Hoang", "123");
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
        @Valid @RequestParam Long id,
        BindingResult result) {
        try {

            if (result.hasErrors()) {
                List<FieldError> fieldErrorList = result.getFieldErrors();
                List<String> errorMessages = fieldErrorList
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            //call UserService to delete user
            Student student = new Student(id, "Hoang", "123");
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
