<?php
require "dataLayer.class.php";

$data = new DataLayer();

if(isset($_POST['login-submit'])){
  $user = strip_tags(trim($_POST['username']));
  $pass = strip_tags(trim($_POST['password']));
  if($data->testPass($user, $pass)){
    $info = $data->getStudentByUser($user);
	 session_start();
    $token = session_id();
    $data->storeToken($info[0], $token);
    $_SESSION['userId'] = $info[0];
    $_SESSION['school'] = $info[2];
    $_SESSION['sec'] = $info[3];
	$_SESSION['student_name'] = $info[4];


    if($info[1] === 1)
     header("Location: professor.php");
    else
      header("Location: student.php");
  }
  else{
    header("Location: login.php?fail=1");
  }
}

if(isset($_POST['register-submit'])){
  $user = strip_tags(trim($_POST['username']));
  $email =strip_tags(trim($_POST['email']));
  $pass = strip_tags(trim($_POST['password']));
  $cPass = strip_tags(trim($_POST['confirm-password']));
  $school = strip_tags(trim($_POST['school']));
  $name = strip_tags(trim($_POST['student_name']));
  $sec = strip_tags(trim($_POST['session']));

  if($pass != $cPass){
    header("Location: login.php?pass=1");
    die();
  }

  $sesArr = array(1,2,3,4,5,6,7,8,9,10);
  if(!isset($user) || $user === "" || !isset($email) || $email === "" || !isset($pass) || $pass === "" 
    || !isset($cPass) || $cPass === "" || !isset($school) || $school === "" || !isset($name) || $name === ""
    || !in_array($sec, $sesArr))
  {
    header("Location: login.php?regFail=1");
    die();
  }
  else{
    if($data->checkUser($user)){
      header("Location: login.php?userFail=1");
      die();
    }
    else{
      if(!$data->checkClass($school, $sec)){
        header("Location: login.php?noclass=1");
        die();
      }
      
      $nameArr = explode(" ", $name);
      $fName = $nameArr[0];
      $lName = $nameArr[1];
      if($data->addUser($lName, $fName, $user, $email, $school, $pass, $sec)){
        header("Location: login.php?reg=1");
        die();
      }
    }
  }
}

?>