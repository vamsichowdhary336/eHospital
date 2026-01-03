const appointmentId = new URLSearchParams(window.location.search).get("id");

if (!appointmentId) {
    alert("Invalid appointment");
    window.location.href = "../patientAppointments/patientappointments.html";
}

/* Load appointment details */
window.onload = () => {
    fetch(`http://localhost:9090/api/patient/appointments/${appointmentId}`, {
        credentials: "include"
    })
    .then(res => {
        if (!res.ok) throw new Error("Unauthorized");
        return res.json();
    })
    .then(app => {
        document.getElementById("department").value = app.department;
        document.getElementById("doctor").value = app.doctor;
        document.getElementById("appointmentDate").value = app.appointmentDate;
        document.getElementById("patientName").value = app.patientName;
        document.getElementById("patientEmail").value = app.patientEmail;

        loadTimeSlots(app.appointmentTime);
    })
    .catch(() => {
        alert("Session expired. Please login again.");
        window.location.href = "../login/login.html";
    });
};

/* Reload time slots when date changes */
document.getElementById("appointmentDate").addEventListener("change", () => {
    loadTimeSlots(null);
});

/* Time-slot logic */
function loadTimeSlots(selectedTime) {
    const timeSelect = document.getElementById("appointmentTime");
    timeSelect.innerHTML = '<option value="">Select Time</option>';

    let start = 10 * 60 + 30; // 10:30
    let end = 17 * 60 + 30;   // 5:30

    const selected = selectedTime ? selectedTime.substring(0, 5) : null;

    for (let mins = start; mins <= end; mins += 30) {
        const h = String(Math.floor(mins / 60)).padStart(2, "0");
        const m = String(mins % 60).padStart(2, "0");
        const value = `${h}:${m}`;

        const option = document.createElement("option");
        option.value = value;
        option.textContent = value;

        if (value === selected) {
            option.selected = true;
        }

        timeSelect.appendChild(option);
    }
}

/* Reschedule appointment */
function rescheduleAppointment() {
    const appointmentDate = document.getElementById("appointmentDate").value;
    const appointmentTime = document.getElementById("appointmentTime").value;
    const message = document.getElementById("message");

    if (!appointmentDate || !appointmentTime) {
        message.style.color = "red";
        message.innerText = "Please select date and time";
        return;
    }

    fetch("http://localhost:9090/api/patient/appointments/reschedule", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({
            id: appointmentId,
            appointmentDate,
            appointmentTime
        })
    })
    .then(res => {
        if (!res.ok) throw new Error();
        return res.text();
    })
    .then(msg => {
        message.style.color = "green";
        message.innerText = msg;

        setTimeout(() => {
            window.location.href =
                "../patientAppointments/patientappointments.html";
        }, 1200);
    })
    .catch(() => {
        message.style.color = "red";
        message.innerText = "Failed to reschedule appointment";
    });
}
