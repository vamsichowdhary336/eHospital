window.onload = () => {
    fetch("http://localhost:9090/api/doctor/reports", {
        credentials: "include"
    })
    .then(res => res.json())
    .then(data => {
        const tbody = document.getElementById("reportTableBody");
        tbody.innerHTML = "";

        data.forEach(r => {
            const row = `
                <tr>
                    <td>${r.patientName}</td>
                    <td>${r.doctorName}</td>
                    <td>${r.reportName}</td>
                    <td>${r.fileName}</td>
                    <td>
                        <button class="view-btn" onclick="viewReport(${r.id})">
                            View Report
                        </button>
                    </td>
                </tr>
            `;
            tbody.innerHTML += row;
        });
    })
    .catch(err => console.error("Failed to load reports", err));
};

function viewReport(id) {
    window.open(`http://localhost:9090/api/doctor/report/${id}`, "_blank");
}
