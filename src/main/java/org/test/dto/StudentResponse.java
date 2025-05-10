package org.test.dto;

import java.time.LocalDate;

import java.util.UUID;

public record StudentResponse(
                UUID idStudent,
                String studentNumber,
                String fullname,
                String major,
                Float gpa,
                LocalDate birth) {
}
