window.onload = loadReports;

function loadReports() {
    fetch("http://localhost:9090/api/patient/reports", {
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        const tbody = document.querySelector("#reportsTable tbody");
        tbody.innerHTML = "";

        data.forEach(report => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${report.patientName}</td>
                <td>${report.doctorName}</td>
                <td>${report.reportName}</td>
                <td>${report.fileName}</td>
                <td>
                    <button class="download-btn" onclick="downloadReport(${report.id})">
                        Download
                    </button>
                </td>
            `;

            tbody.appendChild(row);
        });
    })
    .catch(err => console.error("Failed to load reports", err));
}

function downloadReport(id) {
    window.location.href = `http://localhost:9090/api/patient/reports/download/${id}`;
}
