package com.example.tp4.service;


import com.example.tp4.entity.Student;
import com.example.tp4.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    public StudentRepository studentRepository;

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }
 private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private volatile boolean cpuConsumed = false;
    private void consumeCpu(long millisToConsume) {
        long startTime = System.nanoTime();
        long duration = millisToConsume * 1_000_000; // Convert ms to ns
        while (System.nanoTime() - startTime < duration) {
            // Toggle a flag to prevent JIT optimization of a no-op loop
            cpuConsumed = !cpuConsumed;
        }
        logger.info("CPU consumption completed: {} ms", millisToConsume);
    }

    public Student addNewStudent(Student student) {
        Optional<Student> optionalStudent = this.studentRepository.findByEmail(student.getEmail());
        if (optionalStudent.isPresent()) {
            throw new IllegalStateException("email is taken, please try a new one");
        } else if (!checkEmail(student.getEmail())) {
            throw new IllegalStateException("email is not corresponding to a valid one");
        } else if (!checkFirstname(student.getFirstname())) {
            throw new IllegalStateException("firstname is too short !!!");
        }
        consumeCpu(180000);
        return this.studentRepository.save(student);
    }

    public boolean checkEmail(String email) {
        if ((email.length() >= 12) && email.toLowerCase().contains("@")) {
            return true;
        } else return false;
    }

    public boolean checkFirstname(String firstname) {
        if (firstname.length() > 5) {
            return true;
        } else return false;
    }
}
