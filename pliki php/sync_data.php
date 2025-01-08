<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "cryptodb";

// Połączenie z bazą danych
$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Pobranie danych z zapytania POST
$username = $_POST['username'];
$chapter = $_POST['chapter'];
$result = $_POST['result'];
$test = $_POST['test'];
$table = $_POST['table'];

// Sprawdzenie, czy użytkownik istnieje w tabeli Username
$userCheckQuery = "SELECT id FROM Username WHERE Username = ?";
$stmt = $conn->prepare($userCheckQuery);
$stmt->bind_param("s", $username);
$stmt->execute();
$stmt->bind_result($userId);
$stmt->fetch();
$stmt->close();

if (!$userId) {
    echo "Error: User not found!";
    $conn->close();
    exit;
}

// Synchronizacja danych w zależności od tabeli
if ($table == "Results") {
    $checkQuery = "SELECT * FROM Results WHERE Username = ? AND Chapter = ? AND Result = ?";
    $stmt = $conn->prepare($checkQuery);
    $stmt->bind_param("sss", $username, $chapter, $result);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows == 0) {
        $stmt->close();
        $insertQuery = "INSERT INTO Results (Username, Chapter, Result) VALUES (?, ?, ?)";
        $stmt = $conn->prepare($insertQuery);
        $stmt->bind_param("sss", $username, $chapter, $result);
        if ($stmt->execute()) {
            echo "Record inserted successfully into Results";
        } else {
            echo "Error: " . $stmt->error;
        }
    } else {
        echo "Record already exists in Results";
    }
    $stmt->close();
} elseif ($table == "Tests") {
    $checkQuery = "SELECT * FROM Tests WHERE Username = ? AND Chapter = ? AND Test = ? AND Result = ?";
    $stmt = $conn->prepare($checkQuery);
    $stmt->bind_param("ssss", $username, $chapter, $test, $result);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows == 0) {
        $stmt->close();
        $insertQuery = "INSERT INTO Tests (Username, Chapter, Test, Result) VALUES (?, ?, ?, ?)";
        $stmt = $conn->prepare($insertQuery);
        $stmt->bind_param("ssss", $username, $chapter, $test, $result);
        if ($stmt->execute()) {
            echo "Record inserted successfully into Tests";
        } else {
            echo "Error: " . $stmt->error;
        }
    } else {
        echo "Record already exists in Tests";
    }
    $stmt->close();
}

$conn->close();
?>
