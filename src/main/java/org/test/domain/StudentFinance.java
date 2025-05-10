package org.test.domain;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_finance")
public class StudentFinance extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // atau gunakan UUID.randomUUID() manual
    @Column(name = "id_student_finance")
    private UUID idStudentFinance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_student", referencedColumnName = "id_student")
    private Student student;

    @Column(name = "credit_hours", nullable = false)
    private int creditHours;

    @Column(name = "rate_per_credit", nullable = false)
    private float ratePerCredit;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    public UUID getIdStudentFinance() {
        return idStudentFinance;
    }

    public void setIdStudentFinance(UUID idStudentFinance) {
        this.idStudentFinance = idStudentFinance;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public float getRatePerCredit() {
        return ratePerCredit;
    }

    public void setRatePerCredit(float ratePerCredit) {
        this.ratePerCredit = ratePerCredit;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

}
