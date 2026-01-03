let allDoctors = [];

/* Load doctors on page load */
window.onload = () => {
    fetch("http://localhost:9090/api/appointments/doctors", {
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        allDoctors = data;
    })
    .catch(err => console.error("Failed to load doctors", err));
};

/* Filter doctors based on selected department */
function filterDoctors() {
    const dept = document.getElementById("department").value.trim().toLowerCase();
    const doctorSelect = document.getElementById("doctor");
    const timeSelect = document.getElementById("appointmentTime");

    doctorSelect.innerHTML = '<option value="">Select Doctor</option>';
    timeSelect.innerHTML = '<option value="">Select Time</option>';

    allDoctors
        .filter(d => d.department && d.department.toLowerCase() === dept)
        .forEach(d => {
            const option = document.createElement("option");
            option.value = d.username;
            option.textContent = d.username;
            doctorSelect.appendChild(option);
        });
}

/* Load time slots */
function loadTimeSlots() {
    const timeSelect = document.getElementById("appointmentTime");
    timeSelect.innerHTML = '<option value="">Select Time</option>';

    let start = 10 * 60 + 30;
    let end = 17 * 60 + 30;

    for (let mins = start; mins <= end; mins += 30) {
        const h = String(Math.floor(mins / 60)).padStart(2, "0");
        const m = String(mins % 60).padStart(2, "0");

        const option = document.createElement("option");
        option.value = `${h}:${m}`;
        option.textContent = `${h}:${m}`;
        timeSelect.appendChild(option);
    }
}

/* Book appointment */
function bookAppointment() {

    const department = document.getElementById("department").value;
    const doctor = document.getElementById("doctor").value;
    const appointmentDate = document.getElementById("appointmentDate").value;
    const appointmentTime = document.getElementById("appointmentTime").value;
    const patientName = document.getElementById("patientName").value.trim();
    const patientEmail = document.getElementById("patientEmail").value.trim();
    const message = document.getElementById("message");

    // ✅ frontend validation
    if (!department || !doctor || !appointmentDate || !appointmentTime || !patientName || !patientEmail) {
        message.style.color = "red";
        message.innerText = "Please fill all fields";
        return;
    }

    fetch("http://localhost:9090/api/appointments/booking", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({
            department,
            doctor,
            appointmentDate,
            appointmentTime,
            patientName,
            patientEmail
        })
    })
    .then(res => {
        if (res.status === 401) throw "LOGIN";
        return res.text();
    })
    .then(msg => {
        message.style.color = "green";
        message.innerText = msg;
    })
    .catch(err => {
        message.style.color = "red";
        message.innerText =
            err === "LOGIN" ? "Please login first" : "Error booking appointment";
    });
}
