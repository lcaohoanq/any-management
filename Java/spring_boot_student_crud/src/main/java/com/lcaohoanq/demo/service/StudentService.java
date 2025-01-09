package com.lcaohoanq.demo.service;

import com.lcaohoanq.demo.api.PageResponse;
import com.lcaohoanq.demo.exception.DataNotFoundException;
import com.lcaohoanq.demo.model.Student;
import com.lcaohoanq.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student isExist(Long userId) {
        return studentRepository.findById(userId).orElseThrow();
    }

    @Override
    public Page<Student> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Not found"));
    }
}
