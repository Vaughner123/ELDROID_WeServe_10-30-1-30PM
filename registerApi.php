<?php
// Database connection
$host = 'localhost';
$user = 'root';
$password = '';
$database = 'eldroid-backend';

$conn = new mysqli($host, $user, $password, $database);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get input data
    $input = json_decode(file_get_contents('php://input'), true);

    // Assign input data to variables
    $name = $input['name'] ?? null;  // Nullable, defaults to null if not provided
    $lastname = $input['lastname'] ?? null;  // Nullable
    $email = $input['email'] ?? null;  // Nullable
    $password = $input['password'] ?? null;  // Nullable

    // Check if any of the fields are empty
    if (empty($name) || empty($lastname) || empty($email) || empty($password)) {
        echo json_encode(["error" => "All fields are required."]);
        return;
    }

    // Query to check if the email is already registered
    $sql = "SELECT * FROM users WHERE email = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        echo json_encode(["error" => "Email is already registered."]);
        return;
    }

    // Hash the password before storing it
    $hashed_password = password_hash($password, PASSWORD_DEFAULT);

    // Insert the user data into the database
    $sql = "INSERT INTO users (name, lastname, email, password) VALUES (?, ?, ?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssss", $name, $lastname, $email, $hashed_password);

    // Execute the query and check if the insertion was successful
    if ($stmt->execute()) {
        echo json_encode(["message" => "Registration successful."]);
    } else {
        echo json_encode(["error" => "Error: " . $stmt->error]);
    }
}

$conn->close();
?>
