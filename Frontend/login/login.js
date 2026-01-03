function togglePassword() {
    const passwordInput = document.getElementById("password");
    passwordInput.type =
        passwordInput.type === "password" ? "text" : "password";
}

function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const message = document.getElementById("message");

    fetch("http://localhost:9090/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ username, password })
    })
    .then(async res => {
        let data = {};
        try {
            data = await res.json();
        } catch (e) {}

        console.log("Response status:", res.status); // Debug
        console.log("Response data:", data); // Debug
        console.log("Cookies:", document.cookie); // Debug - check if cookie is set

        if (res.status === 200) {
            message.style.color = "green";
            message.innerText = data.message;

            localStorage.setItem("role", data.role);
            localStorage.setItem("name", data.name);
            localStorage.setItem("username", username);

            setTimeout(() => {
                if (data.role === "DOCTOR") {
                    window.location.href = "../doctorDashboard/doctordashboard.html";
                } else {
                    window.location.href = "../patientDashboard/patientdashboard.html";
                }
            }, 1000);
        }
        else if (res.status === 401 || res.status === 404) {
            message.style.color = "red";
            message.innerText = data.message || "Invalid credentials";
        }
        else {
            throw new Error();
        }
    })
    .catch((error) => {
        console.error("Login error:", error); // Debug
        message.style.color = "red";
        message.innerText = "Server error";
    });
}