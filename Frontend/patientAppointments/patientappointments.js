document.addEventListener("DOMContentLoaded", loadAppointments);

function loadAppointments() {
    fetch("http://localhost:9090/api/patient/appointments", {
        method: "GET",
        credentials: "include"   // session required
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Failed to load appointments");
        }
        return res.json();
    })
    .then(data => renderAppointments(data))
    .catch(err => {
        console.error(err);
        alert("Please login again");
        window.location.href = "../login/login.html";
    });
}

function renderAppointments(appointments) {
    const tbody = document.getElementById("appointmentsBody");
    tbody.innerHTML = "";

    if (appointments.length === 0) {
        tbody.innerHTML = `
           <tr>
            <td colspan="7" class="no-data">No appointments found</td>
        </tr>`;
        return;
    }

    appointments.forEach(app => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${app.id}</td>
            <td>${app.appointmentDate}</td>
            <td>${formatTime(app.appointmentTime)}</td>
            <td>${app.doctor}</td>
            <td>${app.department}</td>
            <td>${app.status}</td>
            <td>
                <button class="action-btn reschedule" onclick="reschedule(${app.id})">Reschedule</button>
                <button class="action-btn cancel" onclick="cancelAppointment(${app.id})">Cancel</button>
            </td>
        `;

        tbody.appendChild(row);
    });
}

/* CANCEL APPOINTMENT */
function cancelAppointment(id) {
    if (!confirm("Are you sure you want to cancel this appointment?")) return;

    fetch(`http://localhost:9090/api/patient/appointments/cancel/${id}`, {
        method: "POST",
        credentials: "include"
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Cancel failed");
        }
        return res.text();
    })
    .then(msg => {
        alert(msg);
        loadAppointments(); // refresh table
    })
    .catch(err => {
        alert("Unable to cancel appointment");
        console.error(err);
    });
}

/*  RESCHEDULE REDIRECT */
function reschedule(id) {
    window.location.href = `../rescheduleAppointment/reschedule.html?id=${id}`;
}

function formatTime(time) {
    // "11:00:00" → "11:00"
    return time ? time.substring(0, 5) : "";
}
