package org.test.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentRequest(
        String studentNumber,
        String fullname,
        String major,
        BigDecimal gpa,
        LocalDate birth) {
}
