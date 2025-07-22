package com.example.servlet;

import com.example.model.AttendanceRecord;
import com.example.storage.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AttendanceServlet - REST endpoints για τη διαχείριση παρουσιών
 */
@Path("/attendance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AttendanceServlet {

    // GET /attendance?studentId=1&courseId=2
    // Επιστρέφει λίστα από attendance records που ταιριάζουν με τα φίλτρα (αν υπάρχουν)
    @GET
    public List<AttendanceRecord> getAttendance(
            @QueryParam("studentId") Integer studentId,
            @QueryParam("courseId") Integer courseId) {

        // Παίρνουμε όλα τα attendance records από τη "βάση"
        Collection<AttendanceRecord> allRecords = DataStore.attendanceRecords.values();

        // Φιλτράρουμε τα αποτελέσματα ανάλογα με τα query params
        return allRecords.stream()
                .filter(ar -> studentId == null || ar.getStudentId() == studentId)
                .filter(ar -> courseId == null || ar.getCourseId() == courseId)
                .collect(Collectors.toList());
    }

    // POST /attendance
    // Προσθέτει νέο attendance record
    @POST
    public Response addAttendance(AttendanceRecord attendanceRecord) {
        // Ορίζουμε μοναδικό ID
        attendanceRecord.setId(DataStore.attendanceIdCounter++);

        // Το αποθηκεύουμε στον in-memory "πίνακα"
        DataStore.attendanceRecords.put(attendanceRecord.getId(), attendanceRecord);

        // Επιστρέφουμε επιτυχία (201 Created)
        return Response.status(Response.Status.CREATED).entity(attendanceRecord).build();
    }

    // PUT /attendance/{id}
    // Ενημερώνει υπάρχον attendance record με νέο περιεχόμενο
    @PUT
    @Path("/{id}")
    public Response updateAttendance(@PathParam("id") int id, AttendanceRecord updatedRecord) {
        // Παίρνουμε το παλιό object (αν υπάρχει)
        AttendanceRecord existing = DataStore.attendanceRecords.get(id);

        // Αν δεν υπάρχει, επιστρέφουμε 404 Not Found
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Ενημερώνουμε τα πεδία του
        existing.setStudentId(updatedRecord.getStudentId());
        existing.setCourseId(updatedRecord.getCourseId());
        existing.setDate(updatedRecord.getDate());
        existing.setPresent(updatedRecord.isPresent());

        // Επιστρέφουμε το ενημερωμένο object
        return Response.ok(existing).build();
    }

    // DELETE /attendance/{id}
    // Διαγράφει attendance record με το συγκεκριμένο ID
    @DELETE
    @Path("/{id}")
    public Response deleteAttendance(@PathParam("id") int id) {
        // Αφαιρούμε το record από το map
        AttendanceRecord removed = DataStore.attendanceRecords.remove(id);

        // Αν δεν υπήρχε, 404 Not Found
        if (removed == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Επιστρέφουμε 204 No Content (επιτυχία χωρίς περιεχόμενο)
        return Response.noContent().build();
    }
}
