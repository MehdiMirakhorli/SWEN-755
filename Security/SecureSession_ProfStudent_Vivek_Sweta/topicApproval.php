<?php
$title = "prof";
require "checkToken.php";

if(isset($_POST['change_status'])){
  $id = strip_tags(trim($_POST['id']));
  $status = strip_tags(trim($_POST['status']));

  $data = new DataLayer();
  $data->updateStatus($id, $status);
  $student = $data->getStudent($id);
  $email = $student[7];
  $message = "Dear $student[2], your topic approval status has changed to: $status";
  mail($email, "Topic Submission Status Change", $message);
}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Project Approval System</title>
		<link href="./css/bootstrap.min.css" rel="stylesheet">
		<link href="./css/dashboard.css" rel="stylesheet">
		<script src="./js/ie-emulation-modes-warning.js"></script>
		<style type="text/css"></style>
		<script src="./js/jquery-2.1.4.min.js"></script>
		<script src="./js/bootstrap.min.js"></script>
		<script src="./js/holder.js"></script>
		<script src="./js/ie10-viewport-bug-workaround.js"></script>
	</head>

	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header" >
					<a class="navbar-brand" href="professor.php"><img style="width:35px;height:30px;margin-top: -5px;" src="./img/logo.jpg">
				</div>
				<b><font size="3">The Approval System</a><a href="login.php?out=1" class ="btn btn-info" style="right:50px; position:fixed;margin-top: 10px;">Sign out</a></font></b>
			</div>
		</nav>

		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<h3>Your Actions</h3>
					<ul class="nav nav-sidebar">
						<li><a href="createSessions.php" class="active">Create Sessions</a></li>
						<li><a href="createSubmissions.php">Create Submissions</a></li>
						<li><a href="topicApproval.php">Students Submissions</a></li>
					</ul>
				</div>
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">Approve Topics</h1>
					<div class="row placeholders">
						<div class="col-xs-7 col-sm-9 placeholder">
							<div class="panel panel-default">
								<div class="panel-body">
									<form id="login-form" action="topicApproval.php?" method="get" role="form" style="display: block;">
										<select name="school">
										  <option value="" selection="default">Select School</option>
										  <option value="RIT">RIT</option>
										  <option value="UofR">U of R</option>
										</select>
										<select name="sec">
										  <option value="" selection="default">Select Session</option>
										<?php
											for($i=1;$i<11;$i++)
												echo "<option value='$i'>$i</option>";
										?>
										</select>
										<input type="submit" name="select_ses" class="btn btn-primary" value="Select Session" />
									</form>
									<?php
									    if(isset($_GET['school']) && isset($_GET['sec'])) : 
									?>
									<table class="table table-hover" data-role="table" id="movie-table" data-filter="true" data-input="#filterTable-input" >
										<thead>
											<tr>
                        <th><a href='topicApproval.php?student=1<?="&select_ses=1&school=".htmlspecialchars($_GET['school'])."&sec=".htmlspecialchars($_GET['sec'])?>'>Student</a></th>
                        <th><a href='topicApproval.php?topic=1<?="&select_ses=1&school=".htmlspecialchars($_GET['school'])."&sec=".htmlspecialchars($_GET['sec'])?>'>Topic</a></th>
                        <th><a href='topicApproval.php?time=1<?="&select_ses=1&school=".htmlspecialchars($_GET['school'])."&sec=".htmlspecialchars($_GET['sec'])?>'>Submission Time</a></th>
                        <th><a href='topicApproval.php?status=1<?="&select_ses=1&school=".htmlspecialchars($_GET['school'])."&sec=".htmlspecialchars($_GET['sec'])?>'>Current Status</a></th>
                        <th colspan="2">Change Status</th>
                      </tr>
										<thead>
										<tbody>
									    <?php
						if(isset($_GET['student']))
							$sort = 2;
					    if(isset($_GET['topic']))
					    	$sort = 3;
					    if(isset($_GET['status']))
					    	$sort = 5;
					    if(isset($_GET['time']))
					    	$sort = 6;
                        if(isset($_GET['select_ses'])){
							$school = strip_tags(trim($_GET['school']));
							$sec = strip_tags(trim($_GET['sec']));

							if(isset($school) && isset($sec)){
								$res = $data->getAll($sort, $school, $sec);
							}
						}
						else
                        	$res = $data->getAll($sort);
                        foreach($res as $row){
                          echo "<tr>
                                  <form method='post' onsubmit='return validateFields();' action='topicApproval.php'>
                                  <td>$row[2]</td>
                                  <td>$row[3]</td>
                                  <td>$row[6]</td>
                                  <td>$row[5]</td>
                                  <td>
                                    <select name='status' id='status'>
                                      <option value='pending'>Pending</option>
                                      <option value='approved'>Approved</option>
                                      <option value='denied'>Denied</option>
                                    </select>
                                  </td>
                                  <td>
                                    <input type='hidden' name='id' id='id' value='$row[0]' />
                                    <input type='submit' name='change_status' value='update'/>
                                  </td>
                                  </form>
                                </tr>";
                        } endif;
									    ?>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>