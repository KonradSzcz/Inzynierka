<?php
$host = "localhost";
$user = "root";
$password = "";
$database = "cryptodb";

$conn = new mysqli($host, $user, $password, $database);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (isset($_POST['username'])) {
    $username = $_POST['username'];

    // Sprawdź, czy użytkownik już istnieje
    $sql_check = "SELECT * FROM username WHERE Username = ?";
    $stmt = $conn->prepare($sql_check);
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows == 0) {
        // Jeśli użytkownik nie istnieje, dodaj go
        $sql_insert = "INSERT INTO username (Username) VALUES (?)";
        $stmt_insert = $conn->prepare($sql_insert);
        $stmt_insert->bind_param("s", $username);

        if ($stmt_insert->execute()) {
            echo "User created successfully.";
        } else {
            echo "Error creating user.";
        }
    } else {
        echo "User already exists.";
    }

    $stmt->close();
} else {
    echo "No username provided.";
}

$conn->close();
?>
