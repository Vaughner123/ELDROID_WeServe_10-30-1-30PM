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

// Set the allowed file types
$allowedFileTypes = ['image/jpeg', 'image/png', 'image/gif'];

// Directory where the uploaded photos will go
$uploadDir = 'post-images/'; 

// Check if the 'file' input is present in the form submission
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file'])) {
    
    // Get the uploaded file information
    $file = $_FILES['file'];
    

    $fileName = $file['name'];
    $fileTmpName = $file['tmp_name'];
    $fileSize = $file['size'];
    $fileType = $file['type'];
    
    // Generate a unique name for the file to avoid conflicts
    $uniqueFileName = uniqid('', true) . '.' . pathinfo($fileName, PATHINFO_EXTENSION);

    // Check if the uploaded file type is allowed
    if (in_array($fileType, $allowedFileTypes)) {
        // Check if there was any error during the upload
        if ($file['error'] === UPLOAD_ERR_OK) {
            // Ensure the upload directory exists
            if (!file_exists($uploadDir)) {
                mkdir($uploadDir, 0777, true);
            }

            // Move the uploaded file to the desired directory
            if (move_uploaded_file($fileTmpName, $uploadDir . $uniqueFileName)) {
                // Insert file details into the database
                $fileUrl = $uploadDir . $uniqueFileName;
                $stmt = $conn->prepare("INSERT INTO `post-images` (file_name, file_type, file_size, file_url) VALUES (?, ?, ?, ?)");
                $stmt->bind_param("ssis", $fileName, $fileType, $fileSize, $fileUrl);
                
                if ($stmt->execute()) {
                    // Respond with success
                    echo json_encode([
                        'status' => 'success',
                        'message' => 'File uploaded and saved to database successfully.',
                        'file_url' => $fileUrl
                    ]);
                } else {
                    // If database insert fails
                    echo json_encode([
                        'status' => 'error',
                        'message' => 'Failed to save file details to the database.'
                    ]);
                }
                $stmt->close();
            } else {
                // If file move failed
                echo json_encode([
                    'status' => 'error',
                    'message' => 'Failed to move uploaded file.'
                ]);
            }
        } else {
            echo json_encode([
                'status' => 'error',
                'message' => 'Error during file upload.'
            ]);
        }
    } else {
        // If the file type is not allowed
        echo json_encode([
            'status' => 'error',
            'message' => 'Invalid file type. Only JPEG, PNG, and GIF files are allowed.'
        ]);
    }
} else {
    // If no file is uploaded or it's a wrong method
    echo json_encode([
        'status' => 'error',
        'message' => 'No file uploaded or wrong request method.'
    ]);
}

$conn->close();
?>
