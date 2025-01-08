<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// Dane połączenia z bazą danych
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "cryptodb";

// Połączenie z bazą danych
$conn = new mysqli($servername, $username, $password, $dbname);

// Sprawdzenie połączenia
if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

// Zapytanie do tabeli Users
$sql = "SELECT * FROM Username";
$result = $conn->query($sql);

// Sprawdzenie, czy istnieją wyniki
if ($result->num_rows > 0) {
    $users = [];

    // Pobranie wierszy jako tablicy asocjacyjnej
    while ($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    // Zwrócenie danych w formacie JSON
    echo json_encode($users);
} else {
    // Gdy brak wyników
    echo json_encode([]);
}

// Zamknięcie połączenia z bazą danych
$conn->close();
?>
