// Load logged-in doctor name (session or localStorage fallback)
window.onload = () => {
    fetch("http://localhost:9090/api/auth/me", {
        credentials: "include"
    })
    .then(res => {
        if (!res.ok) throw new Error("Not logged in");
        return res.json();
    })
    .then(data => {
        document.getElementById("username").innerText = data.username;
    })
    .catch(() => {
        // fallback to localStorage if session expired
        const username = localStorage.getItem("username");
        if (username) {
            document.getElementById("username").innerText = username;
        } else {
            window.location.href = "../login/login.html";
        }
    });
};

// Button functions (called from HTML)
function viewAppointments() {
    window.location.href = "../doctorAppointments/doctorappointment.html";
}

function uploadReport() {
    window.location.href = "../docUploadReport/uploadreport.html";
}

function viewReports() {
    window.location.href = "../doctorReports/allDocReports.html";
}

function logout() {
    fetch("http://localhost:9090/api/auth/logout", {
        method: "POST",
        credentials: "include"
    }).finally(() => {
        window.location.href = "../login/login.html";
    });
}
