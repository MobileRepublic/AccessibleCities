<?php /* @var $this Controller */ ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>Maruti Admin</title><meta charset="UTF-8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/bootstrap-responsive.min.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/colorpicker.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/datepicker.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/uniform.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/select2.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/maruti-style.css" />
		<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/maruti-media.css" class="skin-color" />
	</head>
	<body>
	
		<!--Header-part-->
		<div id="header">
			<h1>Smart Tracker Admin</a></h1>
		</div>
		<!--close-Header-part-->
	
		<!--top-Header-messaages-->
		<div class="btn-group rightzero">
			<a class="top_message tip-left" title="Manage Files"><i class="icon-file"></i></a>
			<a class="top_message tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="top_message tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="top_message tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
		<!--close-top-Header-messaages-->
	
		<!--top-Header-menu-->
		<div id="user-nav" class="navbar navbar-inverse">
			<ul class="nav">
				<li class=""><a title="" href="<?php echo Yii::app()->baseUrl; ?>/site/login"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
			</ul>
		</div>
		<!--close-top-Header-menu-->
	
		<!--left-menu-stats-sidebar-->    
		<div id="sidebar">
			<a href="#" class="visible-phone"><i class="icon icon-th-list"></i> Common Elements</a>
			<ul>
				<li class="active"><a href="<?php echo Yii::app()->baseUrl; ?>/site/home"><i class="icon icon-home"></i> <span>Dashboard</span></a></li>
				<li><a href="<?php echo Yii::app()->baseUrl; ?>/site/form"><i class="icon icon-th-list"></i> <span>Forms</span></a> </li>
				<li><a href="<?php echo Yii::app()->baseUrl; ?>/site/view"><i class="icon icon-th"></i> <span>Tables</span></a></li>
			</ul>
		</div>
		<!--close-left-menu-stats-sidebar-->
	
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<!--<a href="index.html" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
					<a href="#" class="tip-bottom">Form elements</a>
					<a href="#" class="current">Common elements</a>-->
				</div>
				<?php echo $content; ?>
			</div>
		</div>
		<div class="row-fluid">
			<div id="footer" class="span12">
				Copyright &copy; <?php echo date('Y'); ?> by My Company.<br/>All Rights Reserved.<br/>
				<?php echo Yii::powered(); ?>
			</div>
		</div>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/jquery.min.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/jquery.ui.custom.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/bootstrap.min.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/bootstrap-colorpicker.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/bootstrap-datepicker.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/jquery.uniform.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/select2.min.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/maruti.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/maruti.form_common.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/jquery.dataTables.min.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/maruti.tables.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/jquery.validate.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/jquery.wizard.js"></script>
		<script src="<?php echo Yii::app()->baseUrl; ?>/js/maruti.wizard.js"></script>
	</body>
</html>