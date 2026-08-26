package com.launchcode.practice_github_pilot_group.repository;

import com.launchcode.practice_github_pilot_group.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
