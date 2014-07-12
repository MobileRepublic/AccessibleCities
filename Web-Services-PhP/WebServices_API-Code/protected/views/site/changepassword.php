<h1>Form wizard</h1>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon">
						<i class="icon-pencil"></i>									
					</span>
					<h5>Form wizard & validation</h5>
				</div>
				<div class="widget-content nopadding">
					<form id="form-wizard" class="form-horizontal" method="post">
						<div id="form-wizard-1" class="step">
							<div class="control-group">
								<label class="control-label">Username</label>
								<div class="controls">
									<input id="username" type="text" name="username" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Password</label>
								<div class="controls">
									<input id="password" type="password" name="password" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Confirm Password</label>
								<div class="controls">
									<input id="password2" type="password" name="password2" />
								</div>
							</div>
						</div>
						<div class="form-actions">
							<input id="back" class="btn btn-primary" type="reset" value="Back" />
							<input id="next" class="btn btn-primary" type="submit" value="Next" />
							<div id="status"></div>
						</div>
						<div id="submitted"></div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>