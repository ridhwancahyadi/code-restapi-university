package org.test.services;

import java.math.BigDecimal;
import java.security.KeyStore.Entry;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

import org.test.domain.Student;
import org.test.dto.common.ApiResponse;
import org.test.dto.request.StudentRequest;
import org.test.dto.response.StudentResponse;
import org.test.resource.StudentResource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped // Menandakan bahwa class ini dikelola oleh container CDI
public class StudentService {

    private final EntityManager entityManager;
    // EntityManager digunakan untuk berinteraksi dengan database

    // Constructor untuk meng-inject EntityManager ke dalam service
    @Inject
    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Method untuk memetakan objek Student ke dalam format StudentResponse untuk
    // response API
    private StudentResponse mapToStudentResponse(Student student) {
        return new StudentResponse(
                student.getIdStudent(),
                student.getStudentNumber(),
                student.getFullname(),
                student.getMajor(),
                student.getGpa(),
                student.getBirth());
    }

    // Method untuk mengambil semua data mahasiswa dan mengembalikannya dalam bentuk
    // list StudentResponse
    public List<StudentResponse> getStudents() {
        // Mengambil semua mahasiswa dari database
        List<Student> students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        // Mengubah List<Student> menjadi List<StudentResponse> untuk response API
        return students.stream().map(this::mapToStudentResponse).collect(Collectors.toList());
    }

    // Method untuk menambahkan mahasiswa baru menggunakan procedure
    @Transactional
    public void addStudentWithProcedure(StudentRequest studentRequest) {
        // Membuat UUID baru untuk student
        UUID studentId = UUID.randomUUID();
        // Menulis query untuk memanggil stored procedure di database
        String sql = "CALL insert_student_procedure(:id_student, :student_number, :fullname, :major, :gpa, :birth)";

        // Menjalankan query native dengan parameter yang diberikan
        entityManager.createNativeQuery(sql).setParameter("id_student", studentId)
                .setParameter("student_number", studentRequest.studentNumber())
                .setParameter("fullname", studentRequest.fullname()).setParameter("major", studentRequest.major())
                .setParameter("gpa", studentRequest.gpa())
                .setParameter("birth", Date.valueOf(studentRequest.birth()))
                .executeUpdate(); // Menjalankan prosedur
    }

    // Method untuk menambahkan mahasiswa baru menggunakan function di database
    @Transactional
    public UUID addStudentWithFunction(StudentRequest studentRequest) {

        UUID studentId = UUID.randomUUID();

        String sql = "SELECT insert_student_function(:id_student, :student_number, :fullname, :major, :gpa, :birth)";

        // Query untuk memanggil function database dan mendapatkan hasil (misalnya, ID
        // student)
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("id_student", studentId)
                .setParameter("student_number", studentRequest.studentNumber())
                .setParameter("fullname", studentRequest.fullname()).setParameter("major", studentRequest.major())
                .setParameter("gpa", studentRequest.gpa())
                .setParameter("birth", Date.valueOf(studentRequest.birth()))
                .getSingleResult();

        return (UUID) result; // Mengembalikan hasil (ID mahasiswa) yang diterima dari function
    }

    public void updateStudentFields(Student student, StudentRequest studentRequest) {

        // Periksa dan perbarui studentNumber jika valid
        if (studentRequest.studentNumber() != null && !studentRequest.studentNumber().isBlank()) {
            student.setStudentNumber(studentRequest.studentNumber());
        }

        // Periksa dan perbarui fullname jika valid
        if (studentRequest.fullname() != null && !studentRequest.fullname().isBlank()) {
            student.setFullname(studentRequest.fullname());
        }

        // Periksa dan perbarui major jika valid
        if (studentRequest.major() != null && !studentRequest.major().isBlank()) {
            student.setMajor(studentRequest.major());
        }

        // Periksa dan perbarui GPA jika valid
        if (studentRequest.gpa() != null && studentRequest.gpa().compareTo(BigDecimal.ZERO) >= 0 &&
                studentRequest.gpa().compareTo(BigDecimal.valueOf(4)) <= 0) {
            student.setGpa(studentRequest.gpa());
        }

        // Periksa dan perbarui birth date jika valid
        if (studentRequest.birth() != null && !studentRequest.birth().isAfter(LocalDate.now())) {
            student.setBirth(studentRequest.birth());
        }
    }

    @Transactional
    public boolean updateStudent(UUID id, StudentRequest studentRequest) {
        // Cari student berdasarkan ID
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            // Perbarui student fields
            updateStudentFields(student, studentRequest);

            // Simpan perubahan pada database
            entityManager.merge(student);
            return true;
        }
        // Jika student tidak ditemukan, return false
        return false;
    }

    @Transactional
    public boolean deleteStudent(UUID id) {
        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            entityManager.remove(student);
            return true; // Mengembalikan true jika berhasil dihapus
        }
        // Jika mahasiswa tidak ditemukan, mengembalikan false
        return false;
    }
}
