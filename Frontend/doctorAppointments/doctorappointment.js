window.onload = loadAppointments;

/* Load all appointments for logged-in doctor */
function loadAppointments() {
    fetch("http://localhost:9090/api/doctor/appointments", {
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => renderAppointments(data))
    .catch(() => alert("Failed to load appointments"));
}

/* Render table rows */
function renderAppointments(appointments) {
    const tbody = document.getElementById("appointmentsTable");
    tbody.innerHTML = "";

    appointments .filter(appt => appt.status !== "DENIED")
    .forEach(appt => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td class="patient-name">${appt.username}</td>
            <td class="appointment-date">${appt.appointmentDate}</td>
            <td class="appointment-time">${appt.appointmentTime}</td>
            <td class="appointment-status">${appt.status}</td>
            <td class="actions">
                <button class="action-btn primary upload-btn">
                    Upload Report
                </button>
                <button class="action-btn danger deny-btn">
                    Deny
                </button>
            </td>
        `;

        /* Upload Report click */
        row.querySelector(".upload-btn").onclick = () => {
            selectPatientAndUpload(appt.username);
        };

        /* Deny click */
        row.querySelector(".deny-btn").onclick = () => {
            denyAppointment(appt.id);
        };

        tbody.appendChild(row);
    });
}

/* Select patient & go to upload page */
function selectPatientAndUpload(patientUsername) {

    // store on frontend
    sessionStorage.setItem("patientname", patientUsername);

    // store on backend session
    fetch(
        `http://localhost:9090/api/doctor/check-patient?username=${patientUsername}`,
        { credentials: "include" }
    )
    .then(res => {
        if (!res.ok) throw new Error();
        window.location.href = "../docUploadReport/uploadreport.html";
    })
    .catch(() => alert("Patient not found"));
}

/* Deny appointment */
function denyAppointment(id) {
    if (!confirm("Are you sure you want to deny this appointment?")) return;

    fetch(`http://localhost:9090/api/doctor/deny/${id}`, {
        method: "POST",
        credentials: "include"
    })
    .then(() => loadAppointments())
    .catch(() => alert("Failed to deny appointment"));
}
