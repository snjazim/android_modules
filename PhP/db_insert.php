<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['service_group']) && isset($_POST['service']) 
    && isset($_POST['login_idlogin']) && isset($_POST['code'])) 
{
    $name = $_POST['service_group'];
    $id = $_POST['service'];
    $due = $_POST['login_idlogin'];
    $value = $_POST['code'];
    $amount_value = $_POST['amount_var'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
// ,'$amount_value' >> , amount
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO service(service_group, service, login_idlogin, code, amount) VALUES('$name', '$id', '$due', '$value', '$amount_value')");
    echo "Monkey D Luffy";
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>
    
