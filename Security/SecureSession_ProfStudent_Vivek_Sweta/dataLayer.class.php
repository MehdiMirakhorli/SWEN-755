<?php
class DataLayer {
  
  private $mysqli;

  public function __construct(){
    //require "/home/npcomplete/dbCon.php";
    $this->mysqli = new mysqli('localhost', 'sj2416', 'Darker@123', 'sj2416');
    if ($mysqli->connect_error){
      echo "Connect failed: " . $mysqli->connect_error;
      exit();
    }
    else
      return $mysqli;
  }

  /*
  * returns 2D associative array with each array representing a row in the student table
  * the key to each row is the student's ID
  ^ @param int query sort order
  * @return array(array())
  */
  public function getAll($sort = 0, $school = "", $sec = 0){
      $order = "id";

      if($sort == 2)
        $order = "fName";
      if($sort == 3)
        $order = "topic";
      if($sort == 5)
        $order = "status";
      if($sort == 6)
        $order = "time";
      if($sort == 7)
        $order = "school, section";
      
      $query = "SELECT id, lName, fName, topic, link, status, time, school, section
                FROM student
                WHERE role = 2
                ORDER BY $order";

      if($school != "" && $sec != 0){
        $query = "SELECT id, lName, fName, topic, link, status, time, school, section
                  FROM student
                  WHERE role = 2 AND school = ? AND section = ?
                  ORDER BY $order";
      }
    

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    if($school != "" && $sec != 0)
      $stmt->bind_param("si", $school, $sec);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($id, $last, $first, $topic, $link, $status, $time, $school, $section);
    $students = array();
    while($stmt->fetch()){
      $student = array($id, $last, $first, $topic, $link, $status, $time, $school, $section);
      $students[$id] = $student;
    }

    return $students;
  }
  
   /*
  * returns 2D associative array with each array representing a row in the student table
  * the key to each row is the student's ID
  ^ @param int query sort order
  * @return array(array())
  */
  public function getAllPending($sort = 0, $school = "", $sec = 0){
      $order = "id";

      if($sort == 2)
        $order = "fName";
      if($sort == 3)
        $order = "topic";
      if($sort == 5)
        $order = "status";
      if($sort == 6)
        $order = "time";
      if($sort == 7)
        $order = "school, section";
      
      $query = "SELECT id, lName, fName, topic, link, status, time, school, section
                FROM student
                WHERE role = 2 AND status = 'pending'
                ORDER BY $order";

      if($school != "" && $sec != 0){
        $query = "SELECT id, lName, fName, topic, link, status, time, school, section
                  FROM student
                  WHERE role = 2 AND school = ? AND section = ? AND status= 'pending'
                  ORDER BY $order";
      }
    

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    if($school != "" && $sec != 0)
      $stmt->bind_param("si", $school, $sec);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($id, $last, $first, $topic, $link, $status, $time, $school, $section);
    $students = array();
    while($stmt->fetch()){
      $student = array($id, $last, $first, $topic, $link, $status, $time, $school, $section);
      $students[$id] = $student;
    }

    return $students;
  }
  
  /*
  * Returns the row for a specified student ID
  * @param int $id
  * @return array 
  */
  public function getStudent($id){
    $query = "SELECT id, lName, fName, topic, link, status, role, email, time, school, section
              FROM student
              WHERE id = ?";

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($id, $last, $first, $topic, $link, $status, $role, $email, $time, $school, $sec);
    $stmt->fetch();

    return array($id, $last, $first, $topic, $link, $status, $role, $email, $time, $school, $sec);//11 items
  }
  
  /*
  * retrieves id and role for given username
  * @param string username
  * @return array contains user id and role
  */
  public function getStudentByUser($user){
    $query = "SELECT id, role, school, section, fName
              FROM student
              WHERE userName = ?";

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param("s", $user);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($id, $role, $school, $sec, $first);
    $stmt->fetch();

    return array($id, $role, $school, $sec, $first);
  }
  
  /*
  * Compares the user entered password to the one stored in the DB
  * @param string userName
  * @param string password
  * @return boolean
  */
  public function testPass($user, $pw){
  	$query = "SELECT password
  	          FROM student
  	          WHERE userName = ?";

  	$mysqli = $this->mysqli;
  	$stmt = $mysqli->prepare($query);
  	$stmt->bind_param("s", $user);
  	$stmt->execute();
  	$stmt->store_result();
  	$stmt->bind_result($pw2);
  	$stmt->fetch();

  	if($pw === $pw2)
  		return true;
  	else
  		return false;
  }

  /*
  * stores the users token after successful login
  * @param int user ID
  * @param string session ID token
  * @return boolean
  */
  public function storeToken($id, $token){
    
  	$query = "UPDATE student
  	          SET session = ?
  	          WHERE id = ?";

  	$mysqli = $this->mysqli;
  	if($stmt = $mysqli->prepare($query)){
      $stmt->bind_param("si", $token, $id);
  	  $stmt->execute();
  	  return true;
  	}
  	else{
      return false;
    }
  }
  
  /*
  * compares the session id of current session with the session id stored in DB
  * @param int user ID
  * @param string session ID token
  * @return boolean
  */
  public function checkToken($id, $token){
    $query = "SELECT session
              FROM student 
              WHERE id = ?";

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($session);
    $stmt->fetch();

    if($session === $token){
      return true;
    }
    else{
      return false;
    }
  }
  
  /*
  * updates the status for a specified student
  * @param int user ID
  * @param string status
  */ 
  public function updateStatus($id, $status){
    $query = "UPDATE student
              SET status = ?
              WHERE id = ?";

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param("si", $status, $id);
    $stmt->execute();
  }

  /*
  * submits topic proposal to DB
  * @param int ID
  * @param string topic
  * @param string link to supporting evidence
  * @return boolean 
  */
  public function submitProposal($id, $topic, $link){
   $time = date("Y-m-d H:i:s");

    $query = "UPDATE student
              SET topic = ?, support = ?, status = 'pending', time = ?
              WHERE id = ?";

    $mysqli = $this->mysqli;
    if($stmt = $mysqli->prepare($query)){
      $stmt->bind_param("sssi", $topic, $link, $time, $id);
      $stmt->execute();
      return true;
    }
    else
      return false;
  }

  /*
  * checks if the student has submitted a proposal
  * @param int student id
  * @return boolean True if proposal has been submitted
  */
  public function checkProposalExists($id){
    $query = "SELECT topic
              FROM student
              WHERE id = ?";

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($topic);
    $stmt->fetch();

    if($topic === "Not Submitted"){
      return false;
    }
    else
      return true;
  }
  
    /*
  * checks the proposal status
  * @param int student id
  * @return boolean True if proposal has been submitted
  */
  public function checkProposalStatus($id){
    $query = "SELECT status
              FROM student
              WHERE id = ?";

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($status);
    $stmt->fetch();

     return $status;
  }

  /*
  * checks if a username exists in the db
  * @param string username
  * @return boolean true if username exists in db
  */
  public function checkUser($user){
    $query = "SELECT userName
              FROM student
              WHERE userName = ?";

    $mysqli = $this->mysqli;
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param("s", $user);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($user2);
    $stmt->fetch();
    if($user == $user2)
      return true;
    else
      return false;
  }

  /*
  * adds a user to the db
  * @param string last name
  * @param string first name
  * @param string username
  * @param string email
  * @param string school
  * @param string password
  * @return boolean true if successful update
  */
  public function addUser($lName, $fName, $username, $email, $school, $pw, $sec){
    $query = "INSERT INTO student
              SET lName = ?, fName = ?, userName = ?, email = ?, school = ?, section = ?, password = ?, role = 2";

    $mysqli = $this->mysqli;
    if($stmt = $mysqli->prepare($query)){
      $stmt->bind_param("sssssis", $lName, $fName, $username, $email, $school, $sec, $pw);
      $stmt->execute();
      return true;
    }
    else
      return false;
  }

  /*
  * submits a link to the student's project
  * @param int student id
  * @param string link
  * @return boolean true if succesful update
  */
  public function submitLink($id, $link){
    $query = "UPDATE student
              SET link = ?
              WHERE id = ?";

    $mysqli = $this->mysqli;
    if($stmt = $mysqli->prepare($query)){
      $stmt->bind_param("si", $link, $id);
      $stmt->execute();
      return true;
    }
    else
      return false;
  }

  public function setSession($school, $sec){
  	$query = "INSERT INTO sections
  			  SET school = ?, section = ?";

  	$mysqli = $this->mysqli;
  	$stmt = $mysqli->prepare($query);
  	$stmt->bind_param("si", $school, $sec);
  	$stmt->execute();
  }

  public function getSessions(){
  	$query = "SELECT school, section
  	          FROM sections
  	          ORDER BY school";

  	$mysqli = $this->mysqli;
  	$stmt = $mysqli->prepare($query);
  	$stmt->execute(); 	
  	$stmt->store_result();
  	$stmt->bind_result($school, $section);
  	$sessions = array();
  	while($stmt->fetch()){
  		$session = array("school" => $school, "session" => $section);
  		array_push($sessions, $session);
  	}
  	return $sessions;
  }

  public function createSubmission($school, $sec, $name, $type, $due, $ins){
  	$query = "INSERT INTO submissions
  	          SET school = ?, section = ?, name = ?, type = ?, due = ?, ins = ?";

  	$mysqli = $this->mysqli;
  	$stmt = $mysqli->prepare($query);
  	$stmt->bind_param("sissss", $school, $sec, $name, $type, $due, $ins);
  	$stmt->execute();
  }

  public function checkClass($school, $sec){
  	$query = "SELECT id
  	          FROM sections
  	          WHERE school = ? AND section = ?";

  	$stmt = $this->mysqli->prepare($query);
  	$stmt->bind_param("si", $school, $sec);
  	$stmt->execute();
  	$stmt->store_result();
  	$rows = $stmt->num_rows;
  	if($rows == 0)
  		return false;
  	else
  		return true;
  }

  public function getSubmissions($school, $sec){
  	$query = "SELECT name, type, due, ins
  	          FROM submissions
  	          WHERE school = ? AND section = ?";

  	$stmt = $this->mysqli->prepare($query);
  	$stmt->bind_param("si", $school, $sec);
  	$stmt->execute();
  	$stmt->store_result();
  	$stmt->bind_result($name, $type, $due, $ins);
  	$subs = array();
  	while($stmt->fetch()){
  		$sub = array($name, $type, $due, $ins);
  		array_push($subs, $sub);
  	}
  	return $subs;
  }

}//end class
?>