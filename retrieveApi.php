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

// Directory where the uploaded photos will be saved
$uploadDir = 'post-images/';

// Route for posting a new post with a comment
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['file']) && isset($_POST['title']) && isset($_POST['content']) && isset($_POST['comment'])) {
    
    // Get the uploaded file information
    $file = $_FILES['file'];
    
    $title = $_POST['title'];
    $content = $_POST['content'];
    $comment = $_POST['comment'];

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
            // Ensure directory exists
            if (!file_exists($uploadDir)) {
                mkdir($uploadDir, 0777, true);
            }

            // Move the uploaded file to the directory
            if (move_uploaded_file($fileTmpName, $uploadDir . $uniqueFileName)) {
                // Insert details into the database
                $fileUrl = $uploadDir . $uniqueFileName;
                $stmt = $conn->prepare("INSERT INTO `addPost` (title, content, comment, file_name, file_type, file_size, file_url) VALUES (?, ?, ?, ?, ?, ?, ?)");
                $stmt->bind_param("ssssiss", $title, $content, $comment, $fileName, $fileType, $fileSize, $fileUrl);
                
                if ($stmt->execute()) {
                    echo json_encode([
                        'status' => 'success',
                        'message' => 'File uploaded and saved to database successfully.',
                        'file_url' => $fileUrl,
                        'title' => $title,
                        'content' => $content,
                        'comment' => $comment
                    ]);
                } else {
                    echo json_encode([
                        'status' => 'error',
                        'message' => 'Failed to save file and post details to the database.'
                    ]);
                }
                $stmt->close();
            } else {
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
        echo json_encode([
            'status' => 'error',
            'message' => 'Invalid file type. Only JPEG, PNG, and GIF files are allowed.'
        ]);
    }
}

// Routing comments 
elseif ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['id'])) { //check if request meth is get and id is naa sa query string
    $id = $_GET['id'];

    // Retrieve comments 
    $stmt = $conn->prepare("SELECT * FROM `addPost` WHERE `id` = ?");
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $result = $stmt->get_result();

    $comments = [];
    while ($row = $result->fetch_assoc()) {
        $comments[] = $row;
    }

    if (count($comments) > 0) {
        echo json_encode([
            'status' => 'success',
            'comments' => $comments
        ]);
    } else {
        echo json_encode([
            'status' => 'error',
            'message' => 'No comments found for this post.'
        ]);
    }

    $stmt->close();
}

// Route for posting a new comment
// elseif ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['id']) && isset($_POST['author']) && isset($_POST['comment'])) {
//     $id = $_POST['id'];
//     $author = $_POST['author'];
//     $comment_content = $_POST['comment'];

//     // Insert the comment into the database
//     $stmt = $conn->prepare("INSERT INTO `comments` (id, author, comment) VALUES (?, ?, ?)");
//     $stmt->bind_param("iss", $id, $author, $comment_content);
    
//     if ($stmt->execute()) {
//         echo json_encode([
//             'status' => 'success',
//             'message' => 'Comment added successfully.'
//         ]);
//     } else {
//         echo json_encode([
//             'status' => 'error',
//             'message' => 'Failed to add comment.'
//         ]);
//     }
//     $stmt->close();
// } else {
//     echo json_encode([
//         'status' => 'error',
//         'message' => 'Invalid request method or missing parameters.'
//     ]);
// }

$conn->close();
?>
