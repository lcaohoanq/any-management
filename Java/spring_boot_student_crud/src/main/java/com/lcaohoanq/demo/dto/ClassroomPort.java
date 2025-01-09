package com.lcaohoanq.demo.dto;

import jakarta.validation.constraints.NotBlank;

public interface ClassroomPort {

    record AddNewClassRequest(
        @NotBlank(message = "Class name is required") String name,
        @NotBlank(message = "Class code is required") String classCode

    ) {

    }

}
