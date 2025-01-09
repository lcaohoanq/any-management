package com.lcaohoanq.demo.service;

import com.lcaohoanq.demo.dto.ClassroomPort.AddNewClassRequest;
import com.lcaohoanq.demo.model.Classroom;
import com.lcaohoanq.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClassroomService {

    Classroom findById(Long id);
    Page<Classroom> getAll(Pageable pageable);
    Classroom create(AddNewClassRequest classRequest);
}
