const username = localStorage.getItem("username");

if (!username) {
    window.location.href = "../login/login.html";
}

document.getElementById("welcomeText").innerText =
    `Welcome, ${username}`;

function goToAppointments() {
    window.location.href = "../patientAppointments/patientappointments.html";
}

function goToBookAppointment() {
    window.location.href = "../bookAppointment/bookappointment.html";
}

function viewReports() {
    window.location.href = "../reports/patientreport.html"; 
}

function logout() {
    localStorage.clear();
    window.location.href = "../login/login.html";
}
