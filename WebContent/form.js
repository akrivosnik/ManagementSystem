document.addEventListener("DOMContentLoaded", () => {
  const studentForm = document.getElementById("studentForm");
  const studentsList = document.getElementById("studentsList");
  const loadBtn = document.getElementById("loadStudentsBtn");
  const searchBtn = document.getElementById("searchStudentBtn");
  const searchResult = document.getElementById("searchResult");
  const updateForm = document.getElementById("updateForm");
  const deleteBtn = document.getElementById("deleteStudentBtn");

  // ➕ Προσθήκη φοιτητή
  studentForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;

    const student = {
      fullName,
      email,
      registeredCourses: []
    };

    try {
      const response = await fetch("/api/students", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(student)
      });

      if (response.ok) {
        alert("✅ Φοιτητής προστέθηκε!");
        studentForm.reset();
      } else {
        alert("❌ Σφάλμα κατά την προσθήκη.");
      }
    } catch (err) {
      alert("❌ Αποτυχία σύνδεσης.");
    }
  });

  // 👀 Εμφάνιση όλων των φοιτητών
  loadBtn.addEventListener("click", async () => {
    studentsList.innerHTML = "";

    try {
      const response = await fetch("/api/students");

      if (!response.ok) {
        alert("❌ Σφάλμα στην ανάκτηση.");
        return;
      }

      const students = await response.json();

      if (students.length === 0) {
        studentsList.innerHTML = "<li>🚫 Δεν υπάρχουν φοιτητές.</li>";
        return;
      }

      students.forEach((s) => {
        const li = document.createElement("li");
        li.textContent = `#${s.id} - ${s.fullName} (${s.email})`;
        studentsList.appendChild(li);
      });
    } catch (err) {
      alert("❌ Σφάλμα σύνδεσης.");
    }
  });

  // 🔍 Αναζήτηση φοιτητή
  searchBtn.addEventListener("click", async () => {
    const id = document.getElementById("searchStudentId").value;
    if (!id) return;

    try {
      const response = await fetch(`/api/students/${id}`);

      if (response.ok) {
        const student = await response.json();
        searchResult.textContent = `#${student.id} - ${student.fullName} (${student.email})`;
      } else {
        searchResult.textContent = "❌ Δεν βρέθηκε φοιτητής.";
      }
    } catch (err) {
      searchResult.textContent = "❌ Σφάλμα σύνδεσης.";
    }
  });

  // ✏️ Ενημέρωση φοιτητή
  updateForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = document.getElementById("updateId").value;
    const fullName = document.getElementById("updateFullName").value;
    const email = document.getElementById("updateEmail").value;

    const updatedStudent = {
      fullName,
      email
    };

    try {
      const response = await fetch(`/api/students/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedStudent)
      });

      if (response.ok) {
        alert("✅ Ενημερώθηκε ο φοιτητής.");
        updateForm.reset();
      } else {
        alert("❌ Δεν βρέθηκε ή απέτυχε η ενημέρωση.");
      }
    } catch (err) {
      alert("❌ Σφάλμα σύνδεσης.");
    }
  });

  // ❌ Διαγραφή φοιτητή
  deleteBtn.addEventListener("click", async () => {
    const id = document.getElementById("deleteStudentId").value;
    if (!id) return;

    if (!confirm("Είσαι σίγουρος ότι θέλεις να διαγράψεις τον φοιτητή;")) return;

    try {
      const response = await fetch(`/api/students/${id}`, {
        method: "DELETE"
      });

      if (response.ok) {
        alert("✅ Διαγράφηκε ο φοιτητής.");
      } else {
        alert("❌ Δεν βρέθηκε φοιτητής.");
      }
    } catch (err) {
      alert("❌ Σφάλμα σύνδεσης.");
    }
  });
});
