package com.example.educational_system.service;

import com.example.educational_system.entity.Grade;
import com.example.educational_system.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public double calculateGPA(Long studentId) {
        List<Grade> grades = getGradesByStudentId(studentId);
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        double totalCredit = 0;
        double totalGPA = 0;
        for (Grade grade : grades) {
            int credit = grade.getCourse().getCredit();
            double score = grade.getScore();
            double gpa;
            if (score >= 90) {
                gpa = 4.0;
            } else if (score >= 80) {
                gpa = 3.0;
            } else if (score >= 70) {
                gpa = 2.0;
            } else if (score >= 60) {
                gpa = 1.0;
            } else {
                gpa = 0.0;
            }
            totalCredit += credit;
            totalGPA += (gpa * credit);
        }
        if (totalCredit == 0) {
            return 0.0;
        }
        return totalGPA / totalCredit;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }
}