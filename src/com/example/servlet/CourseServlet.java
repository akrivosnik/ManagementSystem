package com.example.servlet;

import com.example.model.Course;
import com.example.storage.DataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

/**
 * CourseServlet - REST endpoints για Course entities
 */
@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseServlet {

    // GET /courses
    // Επιστρέφει λίστα με όλα τα courses
    @GET
    public List<Course> getAllCourses() {
        return new ArrayList<>(DataStore.courses.values());
    }

    // POST /courses
    // Δημιουργεί νέο course και το προσθέτει στο in-memory DataStore
    @POST
    public Response addCourse(Course course) {
        // Ορίζουμε μοναδικό ID
        course.setId(DataStore.courseIdCounter++);

        // Το αποθηκεύουμε στο map
        DataStore.courses.put(course.getId(), course);

        // Επιστρέφουμε επιτυχία (201 Created) και το νέο αντικείμενο
        return Response.status(Response.Status.CREATED).entity(course).build();
    }
}
