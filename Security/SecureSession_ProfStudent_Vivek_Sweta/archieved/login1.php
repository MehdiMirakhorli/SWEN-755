<?php
if($_GET['out'] == 1){
  session_start();
	session_unset();
  session_destroy();
  header("Location: login.php");
  die();
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
		<link href="./css/layout.css" rel="stylesheet">
		<script src="./js/ie-emulation-modes-warning.js"></script>
		<script src="./js/jquery-2.1.4.min.js"> </script>
		<style type="text/css"></style>
		<script src="./js/bootstrap.min.js"></script>
		<script src="./js/myJS.js"></script>
		<script src="./js/holder.js"></script>
		<script src="./js/ie10-viewport-bug-workaround.js"></script>
	</head>

	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top">
						<div class="container-fluid">
				<div class="navbar-header" >
					<a class="navbar-brand" href="index.php"><img style="width:35px;height:30px;margin-top: -5px;" src="./img/logo.jpg">
				</div>
				<b><font size="3">The Approval System</a></font></b>
			</div>
		</nav>

		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-9 col-sm-offset-3 col-md-6 col-md-offset-3 main">
					<h1 class="page-header">Login Here</h1>
					<div class="row placeholders">
						<div class="col-xs-6 col-sm-12 placeholder">
							<div class="panel panel-login">
								<div class="panel-heading">
									<div class="row">
										<div class="col-xs-6">
											<a href="#" class="active" id="login-form-link">Login</a>
										</div>
										<div class="col-xs-6">
											<a href="#" id="register-form-link">Register</a>
										</div>
									</div>
									<hr>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-lg-12">
											<form id="login-form" action="processLogin.php" onsubmit="return validateFields();" method="post" role="form" style="display: block;">										
												<div class="form-group">
												<?php
													if(isset($_GET['reg'])){
														echo "<p style='color:green'>Successfully Registered Your Account</p>";
													}
													if(isset($_GET['noclass'])){
														echo "<p style='color:red'>That session does not exist at that school</p>";
													}
												?>
													<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="">
												</div>
												<div class="form-group">
													<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
													<?php
														if(isset($_GET['fail'])){
															echo "<p style='color:red'>Incorrect username/password combination</p>";
														}
													?>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="col-sm-6 col-sm-offset-3">
															<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
														</div>
													</div>
												</div>
											</form>
											<form id="register-form" action="processLogin.php" onsubmit="return validateFields();" method="post" role="form" style="display: none;">
												<div class="form-group">
													<input type="text" name="student_name" id="student_name" tabindex="1" class="form-control" placeholder="Your Name" value="">
												</div>	
												<div class="form-group">
													<input type="text" name="username" id="username" tabindex="2" class="form-control" placeholder="Username" value="">
												</div>
												<div class="form-group">
													<input type="email" name="email" id="email" tabindex="3" class="form-control" placeholder="Email Address" value="">
												</div>
											<div class="form-group">
												<input type="radio" name="school" id="UofR" value="UofR" /><label for="UofR">U of R</label>&nbsp&nbsp
												<input type="radio" name="school" id="RIT" value="RIT" /><label for="RIT">RIT</label>
											</div>
											<div class="form-group">
											Session Number:
												<select name="session" id='session'>
												<?php
												echo "<option value=''>please select</option>";
												for($i=1;$i<11;$i++)
													echo "<option value='$i'>$i</option>";
												?>
												</select>
											</div>
												<div class="form-group">
													<input type="password" name="password" id="password" tabindex="4" class="form-control" placeholder="Password">
												</div>
												<div class="form-group">
													<input type="password" name="confirm-password" id="confirm-password" tabindex="5" class="form-control" placeholder="Confirm Password">
													<?php
														if(isset($_GET['regFail'])){
															echo "<p style='color:red'>* You must fill out all of the fields</p>";
														}
														if(isset($_GET['pass'])){
															echo "<p style='color:red' >* Your passwords do not match</p>";
														}
														if(isset($_GET['userFail'])){
															echo "<p style='color:red' >* That username is already taken</p>";
														}
													?>
												</div>
												<div class="form-group">
													<div class="row">
														<div class="col-sm-6 col-sm-offset-3">
															<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" style="width:120px" value="Register Now">
														</div>
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>