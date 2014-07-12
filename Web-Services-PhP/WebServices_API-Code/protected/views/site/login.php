<!DOCTYPE html>
	<html lang="en">
		<head>
			<title>Maruti Admin</title><meta charset="UTF-8" />
			<meta name="viewport" content="width=device-width, initial-scale=1.0" />
			<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/bootstrap.min.css" />
			<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/bootstrap-responsive.min.css" />
			<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->baseUrl; ?>/css/maruti-login.css" />
		</head>
		<body>
			<div id="logo">
				<img src="<?php echo Yii::app()->baseUrl; ?>/img/login-logo.png" alt="" />
			</div>
			<div id="loginbox">
				<?php 
					$form=$this->beginWidget('CActiveForm', array(
						'id'=>'loginform',
						'htmlOptions' => array('class'=>'form-vertical',),
						'enableClientValidation'=>true,
						'clientOptions'=>array('validateOnSubmit'=>true,),
					)); 
				?>
				<div class="control-group normal_text"><h3>Maruti Admin Login</h3></div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on"><i class="icon-user"></i></span><!--<input type="text" placeholder="Username" />-->
							<?php echo $form->textField($model,'username', array('placeholder'=>'Username')); ?>
							<?php echo $form->error($model,'username', array('style' => 'color:red !important; font-size: 14px;')); ?>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on"><i class="icon-lock"></i></span><!--<input type="password" placeholder="Password" />-->
							<?php echo $form->passwordField($model,'password', array( 'placeholder'=>'Password')); ?>
							<?php echo $form->error($model,'password', array('style' => 'color:red !important; font-size: 14px;')); ?>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<span class="pull-left"><a href="#" class="flip-link btn btn-warning" id="to-recover">Lost password?</a></span>
					<span class="pull-right"><input type="submit" class="btn btn-success" value="Login" /></span>
				</div>
				<?php $this->endWidget(); ?>
			</div>
			<script src="<?php echo Yii::app()->baseUrl; ?>/js/jquery.min.js"></script>
			<script src="<?php echo Yii::app()->baseUrl; ?>/js/maruti.login.js"></script>
		</body>
	</html>