package org.test.resource;

import org.test.domain.Student;
import org.test.dto.ApiResponse;
import org.test.dto.ApiResponseWithData;
import org.test.dto.StudentRequest;
import org.test.dto.StudentResponse;
import org.test.services.StudentService;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService;

    @Inject
    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @Path("all")
    public Response getStudents() {
        List<StudentResponse> getstudents = studentService.getStudents();
        ApiResponseWithData<List<StudentResponse>> response = new ApiResponseWithData<>(
                true,
                "Student list fetched succesfully", getstudents);
        return Response.ok(response).build();

    }

    @POST
    @Path("/procedure")
    public Response addStudentWithProcedure(StudentRequest studentRequest) {

        // Validasi awal
        if (studentRequest.fullname() == null || studentRequest.fullname().isBlank()) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Fullname is required")).build();
        }

        if (studentRequest.studentNumber() == null || studentRequest.studentNumber().isBlank()) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Student number is required")).build();
        }

        if (studentRequest.gpa() == null || studentRequest.gpa().compareTo(BigDecimal.ZERO) < 0 ||
                studentRequest.gpa().compareTo(BigDecimal.valueOf(4)) > 0) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "GPA must be between 0.0 and 4.0")).build();
        }

        if (studentRequest.birth() == null || studentRequest.birth().isAfter(LocalDate.now())) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Birth date must be a past date")).build();
        }

        // Menambahkan student dengan procedure
        studentService.addStudentWithProcedure(studentRequest);

        // Menyiapkan response dengan data yang baru
        ApiResponse response = new ApiResponse(
                true,
                "Student created with procedure");

        return Response.ok(response).build();
    }

    @POST
    @Path("/function")
    public Response addStudentWithFunction(StudentRequest studentRequest) {

        // Validasi awal
        if (studentRequest.fullname() == null || studentRequest.fullname().isBlank()) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Fullname is required")).build();
        }

        if (studentRequest.studentNumber() == null || studentRequest.studentNumber().isBlank()) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Student number is required")).build();
        }

        if (studentRequest.gpa() == null || studentRequest.gpa().compareTo(BigDecimal.ZERO) < 0 ||
                studentRequest.gpa().compareTo(BigDecimal.valueOf(4)) > 0) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "GPA must be between 0.0 and 4.0")).build();
        }

        if (studentRequest.birth() == null || studentRequest.birth().isAfter(LocalDate.now())) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, "Birth date must be a past date")).build();
        }

        // Menambahkan student dengan procedure
        UUID newStudentId = studentService.addStudentWithFunction(studentRequest);

        if (newStudentId == null) {
            ApiResponse response = new ApiResponse(
                    false,
                    "Failed to create student");
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();

        } else {
            StudentRequest newStudent = new StudentRequest(
                    // student.id(),
                    studentRequest.studentNumber(),
                    studentRequest.fullname(),
                    studentRequest.major(),
                    studentRequest.gpa(),
                    studentRequest.birth());
            ApiResponseWithData<StudentRequest> response = new ApiResponseWithData<>(
                    true,
                    "Student created successfully",
                    newStudent);
            return Response.ok(response).build();

        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") UUID id) {
        boolean deleted = studentService.deleteStudent(id);

        if (deleted) {
            ApiResponse response = new ApiResponse(true, "Student deleted successfully");
            return Response.ok(response).build();
        } else {
            ApiResponse response = new ApiResponse(false, "Student with ID " + id + " not found");
            return Response.status(Status.NOT_FOUND).entity(response).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") UUID id, StudentRequest studentRequest) {
        boolean updated = studentService.updateStudent(id, studentRequest);

        if (updated) {
            ApiResponseWithData<StudentRequest> response = new ApiResponseWithData<>(
                    true,
                    "Student updated successfully",
                    studentRequest);
            return Response.ok(response).build();
        } else {
            ApiResponse response = new ApiResponse(
                    false,
                    "Student with ID " + id + " not found");
            return Response.status(Status.NOT_FOUND).entity(response).build();
        }
    }

}
