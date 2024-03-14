
const loginForm = document.getElementById("login-form");
const resultsDiv = document.getElementById("results");

loginForm.addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent default form submission

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // **Important security measures:**
    // - **Never send plain text passwords:** Use robust password hashing on the server-side.
    // - **Validate user input:** Sanitize user input to prevent potential security vulnerabilities.

    // Simulate successful login (replace with actual authentication logic)
    const isLoggedIn = true; // Replace with your authentication logic

    if (isLoggedIn) {
        // Send POST request with secure communication protocols
        fetch("/send-data", {
            method: "POST",
            headers: {
                "Content-Type": "application/json", // Indicate JSON data
            },
            body: JSON.stringify({
                // Example data to send (replace with your data)
                data1: "value1",
                data2: "value2",
            }),
        })
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse the JSON response
            } else {
                throw new Error("Failed to send POST request");
            }
        })
        .then(data => {
            resultsDiv.textContent = "POST request successful! Response:";
            resultsDiv.appendChild(document.createElement("pre")).textContent = JSON.stringify(data, null, 2); // Pretty-print JSON for readability
        })
        .catch(error => {
            resultsDiv.textContent = "Error sending POST request: " + error.message;
        });
    } else {
        resultsDiv.textContent = "Login failed."; // Replace with appropriate error message
    }
});