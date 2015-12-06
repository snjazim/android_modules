//db get get and send data beautiful way

<?php

$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

// check for get data
if (isset($_POST["login_idlogin"])) 
{
    $idValue = $_POST['login_idlogin'];
    if(!empty($idValue))
	{	
		$check = "SELECT * FROM service WHERE login_idlogin='$idValue';";
		if($output = mysql_query($check))
		{
			$bigArray = array();
			while($row = mysql_fetch_assoc($output))
			{
			    $product = array();
			    $product["sgroup"] = $row["service_group"];
			    $product["service"] = $row["service"];
			    $product["code"] = $row["code"];
			    $product["value"] = $row["amount"];
			    $product["updated_at"] = $row["updated_at"];
			   
			    array_push($bigArray, $product);
			}
			echo json_encode($bigArray);
		}
		else
		{
		   // no product found
		    $response["success"] = 0;
		    $response["message"] = "No product found";
	 
		    // echo no users JSON
		    echo json_encode($response);
		}
	}
	else
	{
	    $response["success"] = -2;
	    $response["message"] = "Required field(s) is missing";
	 // echoing JSON response
	    echo json_encode($response);
	}
}
else 
{
    $response["success"] = -2;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

?>



