<?php

require "checkToken.php";

if(isset($_POST['submit_session'])){
	$school = strip_tags(trim($_POST['school']));
	$sec = strip_tags(trim($_POST['sec']));

	$sections = array(1,2,3,4,5,6,7,8,9,10);
	if($school != "RIT" && $school != "UofR"){
		header("Location: createSessions.php?fail=1");
		die();
	}
	elseif(!array_search($sec, $sections)){
		header("Location: createSessions.php?fail=1");
		die();		
	}
	else{
		$data->setSession($school, $sec);
		header("Location: createSessions.php");
		die();
	}
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
						<li><a href="topicApproval.php">Topic Approvals</a></li>
					</ul>
				</div>
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">Set Number of Sessions</h1>
					<div class="row placeholders">
						<div class="col-xs-7 col-sm-9 placeholder">
							<div class="panel panel-default">
								<div class="panel-body">
									<form id="login-form" action="createSessions.php" role="form" data-toggle="validator" method="post" style="display: block;">
										<div class="form-group" style="text-align: left;">
											<label for="university" style="width:25%">University</label>
											<select  name='school' class="form-control" required>
												<option selection="default">Please select a university</option>
												<option value='RIT'>RIT</option>
												<option value='UofR'>U of R</option>
											</select>
										</div>
										<div class="form-group" style="text-align: left;">
											<label for="numOfSession" style="width:25%;">Session Number</label>
											<select  name='sec' class="form-control" required>
												<option selection="default">Please select a number</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
												<option value="9">9</option>
												<option value="10">10</option>
											</select>
										</div>
										<input type="submit" name="submit_session" class="btn btn-primary" value="Set Sessions" />
									</form>
									<?php if(isset($_GET['fail'])) echo "<p style='color:red'>* Please select all fields</p>";?>
									<hr/>
									<table class="table table-hover" data-role="table" id="movie-table" data-filter="true" data-input="#filterTable-input">
										<thead>
											<tr>
												<th>School</th>
												<th>Session</th>
											</tr>
										</thead>
										<tbody>
										<?php
											$sessions = $data->getSessions();
											foreach($sessions as $ses){
												echo "<tr>
												        <td>".$ses['school']."</td>
												        <td>".$ses['session']."</td>
												      </tr>";
											}
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