<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Allow-Headers: Content-Type");
header('Content-Type: application/json');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "cryptodb";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode(["error" => "Connection failed: " . $conn->connect_error]);
    exit();
}

$sql = "SELECT * FROM Notifications";
$result = $conn->query($sql);

$notifications = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $notifications[] = array(
            'ID' => $row['ID'],
            'Title' => $row['Title'],
            'Content' => $row['Content']
        );

        // Usunięcie powiadomienia po jego pobraniu
        $deleteSql = "DELETE FROM Notifications WHERE ID = " . $row['ID'];
        $conn->query($deleteSql);
    }
}

// Zwracanie danych w formacie JSON
echo json_encode($notifications);

$conn->close();
?>
