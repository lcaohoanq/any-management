package com.lcaohoanq.demo.repository;

import com.lcaohoanq.demo.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

}
