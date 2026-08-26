package com.launchcode.practice_github_pilot_group.controller;

import com.launchcode.practice_github_pilot_group.model.Student;
import com.launchcode.practice_github_pilot_group.model.Teacher;
import com.launchcode.practice_github_pilot_group.repository.StudentRepository;
import com.launchcode.practice_github_pilot_group.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping
    public List<Student> list() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        // if a teacher id is provided on the student.teacher.id, attempt to set teacher
        if (student.getTeacher() != null && student.getTeacher().getId() != null) {
            Long teacherId = student.getTeacher().getId();
            Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
            student.setTeacher(teacher);
        }
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student updated) {
        return studentRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            if (updated.getTeacher() != null && updated.getTeacher().getId() != null) {
                Teacher teacher = teacherRepository.findById(updated.getTeacher().getId()).orElse(null);
                existing.setTeacher(teacher);
            }
            studentRepository.save(existing);
            return ResponseEntity.ok(existing);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return studentRepository.findById(id).map(s -> {
            studentRepository.delete(s);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
