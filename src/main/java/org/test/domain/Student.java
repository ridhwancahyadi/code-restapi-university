package org.test.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_student")
    private UUID idStudent;

    @Column(name = "student_number", nullable = false, unique = true)
    private String studentNumber;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private Float gpa;

    @Column(nullable = false)
    private LocalDate birth;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentFinance> finances;

    public UUID getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(UUID idStudent) {
        this.idStudent = idStudent;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Float getGpa() {
        return gpa;
    }

    public void setGpa(Float gpa) {
        this.gpa = gpa;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public List<StudentFinance> getFinances() {
        return finances;
    }

    public void setFinances(List<StudentFinance> finances) {
        this.finances = finances;
    }

}
