package com.example.servlet;

// Φέρνουμε τις κλάσεις που χρειαζόμαστε
import com.example.model.Student;
import com.example.storage.DataStore;

import jakarta.ws.rs.*;                 // Για τις REST annotations (GET, POST, κλπ)
import jakarta.ws.rs.core.MediaType;  // Για να δηλώσουμε ότι δουλεύουμε με JSON
import jakarta.ws.rs.core.Response;   // Για να φτιάχνουμε απαντήσεις HTTP

import java.util.*;                   // Για τη χρήση λιστών και map

// Βασικό URL για αυτήν την κλάση: /api/students
@Path("/students")

// Όταν στέλνουμε απαντήσεις, θα είναι σε μορφή JSON
@Produces(MediaType.APPLICATION_JSON)

// Όταν λαμβάνουμε δεδομένα (π.χ. σε POST ή PUT), αναμένουμε JSON
@Consumes(MediaType.APPLICATION_JSON)

public class StudentServlet {

    // Μέθοδος για να πάρουμε όλους τους μαθητές
    // Αν κάποιος κάνει GET στο /api/students θα εκτελεστεί αυτή η μέθοδος
    @GET
    public List<Student> getAllStudents() {
        // Επιστρέφουμε όλα τα αντικείμενα Student από το DataStore σε λίστα
        return new ArrayList<>(DataStore.students.values());
    }

    // Μέθοδος για να πάρουμε έναν μαθητή με συγκεκριμένο ID
    // Παράδειγμα URL: /api/students/3
    @GET
    @Path("/{id}")  // Το {id} σημαίνει ότι παίρνουμε τον αριθμό από το URL
    public Response getStudentById(@PathParam("id") int id) {
        Student student = DataStore.students.get(id);  // Βρίσκουμε τον μαθητή με αυτό το ID

        if (student == null) {
            // Αν δεν υπάρχει μαθητής με αυτό το ID, στέλνουμε απάντηση 404 (Δεν βρέθηκε)
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Αν βρέθηκε, στέλνουμε τον μαθητή με κατάσταση 200 (OK)
        return Response.ok(student).build();
    }

    // Μέθοδος για να προσθέσουμε έναν νέο μαθητή
    // Κάποιος κάνει POST στο /api/students και στέλνει τα δεδομένα του μαθητή σε JSON
    @POST
    public Response addStudent(Student student) {
        // Δημιουργούμε μοναδικό ID για τον νέο μαθητή
        student.setId(DataStore.studentIdCounter++);

        // Αποθηκεύουμε τον μαθητή στο DataStore (στη μνήμη)
        DataStore.students.put(student.getId(), student);

        // Στέλνουμε απάντηση 201 (Created) μαζί με τα δεδομένα του μαθητή
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    // Μέθοδος για να ενημερώσουμε έναν μαθητή με συγκεκριμένο ID
    // Κάποιος κάνει PUT στο /api/students/3 και στέλνει τα νέα δεδομένα σε JSON
    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") int id, Student updatedStudent) {
        Student existing = DataStore.students.get(id);  // Βρίσκουμε τον μαθητή

        if (existing == null) {
            // Αν δεν υπάρχει μαθητής με αυτό το ID, στέλνουμε 404
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Ενημερώνουμε τα πεδία του μαθητή με τα νέα δεδομένα
        existing.setFullName(updatedStudent.getFullName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setRegisteredCourses(updatedStudent.getRegisteredCourses());

        // Στέλνουμε πίσω τον ενημερωμένο μαθητή με 200 (OK)
        return Response.ok(existing).build();
    }

    // Μέθοδος για να διαγράψουμε έναν μαθητή με συγκεκριμένο ID
    // Κάποιος κάνει DELETE στο /api/students/3
    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") int id) {
        Student removed = DataStore.students.remove(id);  // Αφαιρούμε τον μαθητή

        if (removed == null) {
            // Αν δεν υπήρχε μαθητής με αυτό το ID, στέλνουμε 404
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Αν διαγράφηκε επιτυχώς, στέλνουμε 204 No Content (επιτυχής διαγραφή χωρίς σώμα απάντησης)
        return Response.noContent().build();
    }
}
