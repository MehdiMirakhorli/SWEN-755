<?php
require "checkToken.php";

if(isset($_POST['submit-link'])){
	$link = strip_tags(trim($_POST['project-link']));

	if(!isset($link) || $link === ""){
		header("Location: projectLink.php?fail=1");
		die();
	}
	else{
		$data = new DataLayer();
		if($data->submitLink($_SESSION['userId'], $link)){
			$_POST['reg-success'] = true;
		}
		else
			$_POST['reg-fail'] = true;
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
		
</script> 
	</head>

	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header" >
					<a class="navbar-brand" href="student.php"><img style="width:35px;height:30px;margin-top: -5px;" src="./img/logo.jpg">
				</div>
				<b><font size="3">The Approval System</a><a href="login.php?out=1" class ="btn btn-info" style="right:50px; position:fixed;margin-top: 10px;">Sign out</a></font></b>
			</div>
		</nav>

		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">
					<h3>Your Actions</h3>
					<ul class="nav nav-sidebar">
						<li><a href="submitProposal.php">Submit Proposal</a></li>
						<li><a href="projectLink.php" class="active">Submit Project Link </a></li>
						<li><a href="approvedProjects.php">List of Approved project</a></li>
					</ul>
				</div>
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					
					<div class="row placeholders">
						<div class="col-xs-7 col-sm-9 placeholder">
							<div class="panel panel-default">
								<div class="panel-body">
									<form method="post" action="projectLink.php" role="form" data-toggle="validator">
										
										<div class="form-group">
											<label for="project-link" style="width:25%">Submit Project link</label>
											<input type="text" class="form-control" id="project-link" name="project-link" required/>
										</div>
										
										<input type="submit" class="btn btn-primary" name="submit-link" value="submit" />
									</form>
									<?php if(isset($_POST['reg-success'])) echo "<p style='color:green'>Congratulations, Your link has been submitted</p>";
									      if(isset($_POST['reg-fail'])) echo "<p style='color:red'>Sorry, your link was not accepted</p>";
									?>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>