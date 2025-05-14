package org.test.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.UUID;

public record StudentResponse(
        UUID idStudent,
        String studentNumber,
        String fullname,
        String major,
        BigDecimal gpa,
        LocalDate birth) {
}
