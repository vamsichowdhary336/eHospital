// Load patient name from session (set during check-patient)
window.onload = () => {
    fetch("http://localhost:9090/api/auth/me", {
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        // patientname stored in session earlier
        document.getElementById("patientName").value =
            sessionStorage.getItem("patientname") || "Selected Patient";
    })
    .catch(() => {
        document.getElementById("patientName").value = "Selected Patient";
    });
};

// Upload report
document.getElementById("reportForm").addEventListener("submit", e => {
    e.preventDefault();

    const reportName = document.getElementById("reportName").value;
    const file = document.getElementById("file").files[0];
    const message = document.getElementById("message");

    if (!reportName || !file) {
        message.style.color = "red";
        message.innerText = "All fields are required";
        return;
    }

    const formData = new FormData();
    formData.append("reportName", reportName);
    formData.append("file", file);

    fetch("http://localhost:9090/api/doctor/upload-report", {
        method: "POST",
        credentials: "include",
        body: formData
    })
    .then(res => res.text())
    .then(data => {
        message.style.color = "green";
        message.innerText = data;
        document.getElementById("reportForm").reset();
    })
    .catch(() => {
        message.style.color = "red";
        message.innerText = "Upload failed";
    });
});
