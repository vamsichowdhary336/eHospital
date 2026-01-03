function toggleStaffFields() {
    const staffFields = document.getElementById("staffFields");
    const isStaff = document.getElementById("staffCheckbox").checked;
    staffFields.style.display = isStaff ? "block" : "none";
}

function register() {

    const username = document.getElementById("username").value.trim();
    const firstname = document.getElementById("firstname").value.trim();
    const lastname = document.getElementById("lastname").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const isStaff = document.getElementById("staffCheckbox").checked;
    const message = document.getElementById("message");

    // Basic validation
    if (!username || !firstname || !lastname || !email || !password) {
        message.style.color = "red";
        message.innerText = "Please fill all required fields";
        return;
    }

    //  Password validation (same as image)
    const passwordRegex =
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;

    if (!passwordRegex.test(password)) {
        message.style.color = "red";
        message.innerText =
            "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character";
        return;
    }

    const data = {
        username,
        firstname,
        lastname,
        email,
        password,
        staffMember: isStaff
    };

    //  Staff validation
    if (isStaff) {
        const department = document.getElementById("department").value;
        const jobTitle = document.getElementById("jobTitle").value;
        const dateOfJoining = document.getElementById("dateOfJoining").value;
        const manager = document.getElementById("manager").value;

        if (!department || !jobTitle || !dateOfJoining || !manager) {
            message.style.color = "red";
            message.innerText = "Please fill all staff details";
            return;
        }

        data.department = department;
        data.jobTitle = jobTitle;
        data.dateOfJoining = dateOfJoining;
        data.manager = manager;
    }

    fetch("http://localhost:9090/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(res => res.text())
    .then(msg => {
        message.style.color = "green";
        message.innerText = msg;

        // Redirect to login page
        setTimeout(() => {
            window.location.href = "../login/login.html";
        }, 1500);
    })
    .catch(() => {
        message.style.color = "red";
        message.innerText = "Registration failed";
    });
}

function togglePassword() {
    const passwordField = document.getElementById("password");
    passwordField.type =
        passwordField.type === "password" ? "text" : "password";
}
