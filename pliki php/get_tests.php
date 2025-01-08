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

// Sprawdzenie, czy użytkownik został przekazany w zapytaniu
if (isset($_GET['username'])) {
    $username = $_GET['username'];

    // Zapytanie do tabeli Tests dla określonego użytkownika
    $sql = "SELECT Chapter, Test, Result FROM Tests WHERE Username = '$username'";
    $result = $conn->query($sql);

    // Sprawdzenie, czy istnieją wyniki
    if ($result->num_rows > 0) {
        $tests = [];

        // Pobranie wierszy jako tablicy asocjacyjnej
        while ($row = $result->fetch_assoc()) {
            // Usuwanie kolumny Username z wyników, ponieważ nie jest potrzebna w aplikacji Android
            unset($row['Username']);
            $tests[] = $row;
        }

        // Zwrócenie danych w formacie JSON
        echo json_encode($tests);
    } else {
        // Brak wyników
        echo json_encode([]);
    }
} else {
    // Brak parametru 'username'
    http_response_code(400);
    echo json_encode(["error" => "Username parameter is missing."]);
}

// Zamknięcie połączenia z bazą danych
$conn->close();
?>
