<?php
require "checkToken.php";
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
					<a class="navbar-brand" href="index.php"><img style="width:35px;height:30px;margin-top: -5px;" src="./img/logo.jpg">
				</div>
				<b><font size="3">The Approval System</a><a href="login.php?out=1" style="right:50px; position:fixed;margin-top: 10px;">Sign out</a></font></b>
			</div>
		</nav>

		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3 col-md-2 sidebar">	
					<h3>Your Actions</h3>
					<ul class="nav nav-sidebar">
						<li><a href="submitProposal.php" class="active">Submit Proposal	</a></li>
						<li><a href="projectLink.php">Submit Project Link </a></li>
						<li><a href="approvedProjects.php">List of Approved project</a></li>
					</ul>
				</div>
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<div class="row">
					  <div class="col-sm-6">
					  <?php
					  	if(!$data->checkProposalExists($_SESSION['userId'])) : 
					  ?>
					  		<form role="form" action="submitProposal.php" method="post">
							  <div class="form-group">
							    <label for="project-topic">Enter topic for project:</label>
							    <br>
							    <input name="project-topic" type="text" class="form-control" id="project-topic" style="width: 100%">
                  <p id='topic_error' style='display: inline'></p>
							  </div>
							  <div class="form-group">
							    <label for="project-topic">Enter the link for the proposal:</label>
							    <br>
							    <input name="project-link" type="text" class="form-control" id="project-topic" style="width: 100%">
                  <P id='link_error' style='display: inline'></P>
							  </div>
							  <input type="submit" value="submit" name="form_submit"/>
							  <!--<button type="submit" class="btn btn-primary">Submit</button>-->
							</form>
                  <?php
              		endif;
                  	session_start();
                    if(isset($_POST['form_submit'])){
                      $topic = strip_tags(trim($_POST['project-topic']));
                      $link = strip_tags(trim($_POST['project-link']));
                      $id = $_SESSION['userId'];
  
                      if(!isset($topic) || $topic === ""){
                        echo "<p style='color:red'>* Please enter a topic</p>";
                      }
                      elseif(!isset($link) || $link === ""){
                        echo "<p style='color:red'>* Please enter a link</p>";
                      }
                      else{
                        $data = new DataLayer();
                        $data->submitProposal($id, $topic, $link);
                      }
                    }
                  ?>
					  </div>

					  	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					
					<div class="row placeholders">
						<div class="col-xs-7 col-sm-9 placeholder">
							<div class="panel panel-default">
								<div class="panel-body">
									List of Submitted/Approved projects

									  <table class="table table-bordered" style="text-align: left">
									    <thead>
                      <?="<caption>School: ".$_SESSION['school']." - session: ".$_SESSION['sec']."</caption>"?>
									      <tr>
									        <th><a href='submitProposal.php?name=1'>Student Name</a></th>
									        <th><a href='submitProposal.php?topic=1'>Topic</a></th>
                          <th><a href='submitProposal.php?time=1'>Submission Time</a></th>
									        <th><a href='submitProposal.php?status=1'>Status</a></th>
									        
									      </tr>
									    </thead>
									    <tbody>
                       <?php
                       $sort = "";
                        if(isset($_GET['name']))
                       	  $sort = 2;
                       	if(isset($_GET['time']))
                       		$sort = 6;
                       	if(isset($_GET['topic']))
                          $sort = 3;
                       	if(isset($_GET['status']))
                          $sort = 5;
                        $data = new DataLayer();
                        $res = $data->getAll($sort, $_SESSION['school'], $_SESSION['sec']);
                        foreach($res as $row){
                          echo "<tr>
                                  <td>$row[2]</td>
                                  <td>$row[3]</td>
                                  <td>$row[6]</td>
                                  <td>$row[5]</td>
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