<?php

require "dataLayer.class.php";
$data = new DataLayer();

session_start();
if(!isset($_SESSION['userId']) || !$data->checkToken($_SESSION['userId'], session_id())){
	header("Location: login.php");
	die();
}
if($title === "prof"){
	$res = $data->getStudent($_SESSION['userId']);
  if($res[6] != 1){
    header("Location: login.php");
  }
}


?>