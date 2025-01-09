package com.lcaohoanq.demo.service;

import com.lcaohoanq.demo.api.PageResponse;
import com.lcaohoanq.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService {

    void save(Student student);
    Student isExist(Long userId);
    Page<Student> getAll(Pageable pageable);
    Student findById(Long id);
}
