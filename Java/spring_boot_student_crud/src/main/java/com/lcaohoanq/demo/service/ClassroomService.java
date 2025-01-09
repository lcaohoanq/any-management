package com.lcaohoanq.demo.service;

import com.lcaohoanq.demo.dto.ClassroomPort.AddNewClassRequest;
import com.lcaohoanq.demo.exception.DataNotFoundException;
import com.lcaohoanq.demo.model.Classroom;
import com.lcaohoanq.demo.repository.ClassroomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService implements IClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Classroom findById(Long id) {
        return classroomRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Not found class"));
    }

    @Override
    public Page<Classroom> getAll(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    @Override
    public Classroom create(AddNewClassRequest classRequest) {
        return classroomRepository.save(
            Classroom.builder()
                .name(classRequest.name())
                .classCode(classRequest.classCode())
                .build()
        );
    }
}
