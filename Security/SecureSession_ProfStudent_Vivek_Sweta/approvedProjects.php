<?php session_start(); ?>
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
									List of Submitted/Approved projects
									<?php
									if(isset($_GET['all']))
										echo "<a href='approvedProjects.php'>View Your Session</a>";
									else
										echo "<a href='approvedProjects.php?all=1'>View All Submissions</a>";
									?>

									  <table class="table table-bordered" style="text-align: left">
									  <?="<caption>School: ".$_SESSION['school']." - session: ".$_SESSION['sec']."</caption>"?>
									    <thead>
									      <tr>
									        <th><a href='approvedProjects.php?name=1<?php if(isset($_GET['all'])) echo "&all=1";?>'>Student Name</a></th>
									        <th><a href='approvedProjects.php?topic=1<?php if(isset($_GET['all'])) echo "&all=1";?>'>Topic</a></th>
                          					<th><a href='approvedProjects.php?time=1<?php if(isset($_GET['all'])) echo "&all=1";?>'>Submission Time</a></th>
                          					<th><a href='approvedProjects.php?school=1<?php if(isset($_GET['all'])) echo "&all=1";?>'>School</a></th>
                          					<th><a href='approvedProjects.php?sec=1<?php if(isset($_GET['all'])) echo "&all=1";?><?php if(isset($_GET['all'])) echo "&all=1";?>'>Session</a></th>
									        <th><a href='approvedProjects.php?status=1<?php if(isset($_GET['all'])) echo "&all=1";?>'>Status</a></th>
									        <th>Link</th>
									      </tr>
									    </thead>
									    <tbody>
									      <?php
									      $sort = " ";
									        if(isset($_GET['name']))
									        	$sort = 2;
									        if(isset($_GET['topic']))
									        	$sort = 6;
									        if(isset($_GET['time']))
									        	$sort = 3;
									        if(isset($_GET['status']))
									        	$sort = 5;
									        if(isset($_GET['school']))
									        	$sort = 7;
                          					require "dataLayer.class.php";
                          					$data = new DataLayer();
                          					if(isset($_GET['all']))
                          						$res = $data->getAll($sort);
                          					else
                          						$res = $data->getAll($sort, $_SESSION['school'], $_SESSION['sec']);
                          					foreach($res as $row){
                                        echo "<tr>
                                                <td>$row[2]</td>
                                                <td>$row[3]</td>
                                                <td>$row[6]</td>
                                                <td>$row[7]</td>
                                                <td>$row[8]</td>
                                                <td>$row[5]</td>
                                                <td><a href='$row[4]'>$row[4]</a></td>
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