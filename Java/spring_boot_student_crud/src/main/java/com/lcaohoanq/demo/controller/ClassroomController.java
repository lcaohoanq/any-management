package com.lcaohoanq.demo.controller;

import com.lcaohoanq.demo.api.ApiResponse;
import com.lcaohoanq.demo.dto.ClassroomPort.AddNewClassRequest;
import com.lcaohoanq.demo.model.Classroom;
import com.lcaohoanq.demo.service.IClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${API_PREFIX}/classes")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Class APIs", description = "Operations related to Classes")
@RequiredArgsConstructor
public class ClassroomController {
    
    IClassroomService classroomService;
    
    @GetMapping("/all")
    @Operation(summary = "Get All Class, suport pagination")
    public ResponseEntity<ApiResponse<?>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ){
        return ResponseEntity.ok().body(
            ApiResponse.builder()
                .message("Get All Student successfully")
                .data(classroomService.getAll(PageRequest.of(page, limit)))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
    
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<Classroom>> getDetail(
        @RequestParam Long id
    ){
        return ResponseEntity.ok().body(
            ApiResponse.<Classroom>builder()
                .message("Get class detail successfully")
                .data(classroomService.findById(id))      
                .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Classroom>> create(
        @Valid @RequestBody AddNewClassRequest classRequest
        ){
        return ResponseEntity.ok().body(
            ApiResponse.<Classroom>builder()
                .message("Get class detail successfully")
                .data(classroomService.create(classRequest))
                .build()
        );
    }
}
