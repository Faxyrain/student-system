package com.example.educational_system.repository;

import com.example.educational_system.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // 新增：根据学生 ID 查询成绩
    List<Grade> findByStudentId(Long studentId);
}