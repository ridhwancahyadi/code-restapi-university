package org.test.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "student_course")
public class StudentCourse extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_student_course", nullable = false)
    private UUID idStudentCourse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_student", referencedColumnName = "id_student")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_course", referencedColumnName = "id_course")
    private Course course;

    @Column(name = "enrolled_on")
    private LocalDate enrolledOn = LocalDate.now();

    // Getters & Setters

    public UUID getIdStudentCourse() {
        return idStudentCourse;
    }

    public void setIdStudentCourse(UUID idStudentCourse) {
        this.idStudentCourse = idStudentCourse;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrolledOn() {
        return enrolledOn;
    }

    public void setEnrolledOn(LocalDate enrolledOn) {
        this.enrolledOn = enrolledOn;
    }
}
