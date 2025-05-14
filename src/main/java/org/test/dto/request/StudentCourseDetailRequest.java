package org.test.dto.request;

import java.time.LocalDate;

public record StudentCourseDetailRequest(
        String fullName,
        String courseName,
        LocalDate enrolledOn) {
}
