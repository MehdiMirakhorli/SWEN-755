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
		<script src="./js/jquery-2.1.4.min.js"> </script>
		<script src="./js/bootstrap.min.js"></script>
		<script src="./js/holder.js"></script>
		<script src="./js/ie10-viewport-bug-workaround.js"></script>
	</head>

	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="index.php"><img style="width:35px;height:30px;margin-top: -5px;" src="./img/logo.jpg">
				</div>
				<b><font size="3">The Approval System</a><a href="login.php?out=1" class ="btn btn-info" style="right:50px; position:fixed;margin-top: 10px;">Sign in</a></font></b>
			</div>
		</nav>

		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-1 main"">
					<h1 class="page-header" align="center">The Approval System</h1>
					<div class="row placeholders">
						<div class="col-xs-6 col-sm-12 placeholder">
							<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
							  <!-- Indicators -->
							  <ol class="carousel-indicators">
								<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
								<li data-target="#carousel-example-generic" data-slide-to="1"></li>
								<li data-target="#carousel-example-generic" data-slide-to="2"></li>
							  </ol>

							  <!-- Wrapper for slides -->
							  <div class="carousel-inner" role="listbox">
								<div class="item active">
								  <img src="./img/carousel1.jpg" alt="rate">
								  <div class="carousel-caption">
									Rating
								  </div>
								</div>
								<div class="item">
								  <img src="./img/carousel2.jpg" alt="prof">
								  <div class="carousel-caption">
									Professor
								  </div>
								</div>
								<div class="item">
								  <img src="./img/carousel3.jpg" alt="stud">
								  <div class="carousel-caption">
									Students
								  </div>
								</div>
							  </div>

							  <!-- Controls -->
							  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
								<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							  </a>
							  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
								<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							  </a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>