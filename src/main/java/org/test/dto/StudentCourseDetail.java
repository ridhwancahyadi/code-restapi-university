package org.test.dto;

import java.time.LocalDate;

public record StudentCourseDetail(
                String fullName,
                String courseName,
                LocalDate enrolledOn) {
}
