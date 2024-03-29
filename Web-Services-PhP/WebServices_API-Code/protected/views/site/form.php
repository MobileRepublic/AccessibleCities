<h1>Common Form Elements</h1>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon">
						<i class="icon-align-justify"></i>									
					</span>
					<h5>Personal-info</h5>
				</div>
				<div class="widget-content nopadding">
					<form action="#" method="get" class="form-horizontal">
						<div class="control-group">
							<label class="control-label">Basic info :</label>
							<div class="controls"><input type="text" class="span3" placeholder="First name (span3)" /> <input type="text" class="span3" placeholder="Middle name (span3)"/> <input type="text" class="span3" placeholder="Last name (span3)"/> <input type="text" class="span3" placeholder="Email ID (span3)"/></div>
						</div>
						<div class="control-group">
							<label class="control-label">Password input</label>
							<div class="controls">
								<input type="password"  class="span6" placeholder="Enter Password (span6)"  /> <input type="password"  class="span6" placeholder="Confirm Password (span6)"  />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">Description field:</label>
							<div class="controls">
								<input type="text" class="span20" placeholder="Enter Description (span20)"/>
								<span class="help-block">Description field</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">Normal textarea</label>
							<div class="controls">
								<textarea class="span20"  placeholder="textarea (span20)" ></textarea>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">Select input</label>
							<div class="controls ">
								<select>
									<option>First option</option>
									<option>Second option</option>
									<option>Third option</option>
									<option>Fourth option</option>
									<option>Fifth option</option>
									<option>Sixth option</option>
									<option>Seventh option</option>
									<option>Eighth option</option>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">Multiple Select input</label>
							<div class="controls">
								<select multiple>
									<option>First option</option>
									<option selected>Second option</option>
									<option>Third option</option>
									<option>Fourth option</option>
									<option>Fifth option</option>
									<option>Sixth option</option>
									<option>Seventh option</option>
									<option>Eighth option</option>
								</select>
								 
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">Radio inputs</label>
							<div class="controls">
								<label><input type="radio" name="radios" /> First One</label>
								<label><input type="radio" name="radios" /> Second One</label>
								<label><input type="radio" name="radios" /> Third One</label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">Checkboxes</label>
							<div class="controls">
								<label><input type="checkbox" name="radios" /> First One</label>
								<label><input type="checkbox" name="radios" /> Second One</label>
								<label><input type="checkbox" name="radios" /> Third One</label>
							</div>
						</div>	
					   <div class="control-group">
							<label class="control-label">File upload input</label>
							<div class="controls">
								<input type="file" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">Color picker (hex)</label>
							<div class="controls">
								<input type="text" data-color="#ffffff" value="#ffffff" class="colorpicker input-small" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">Color picker (rgba)</label>
							<div class="controls">
								<input type="text" data-color="rgba(344,232,53,0.5)" value="rgba(344,232,53,0.5)" data-color-format="rgba" class="colorpicker" />
							</div>
						</div>                                        
						<div class="control-group">
							<label class="control-label">Date picker</label>
							<div class="controls">
								<input type="text" data-date="01-02-2013" data-date-format="dd-mm-yyyy" value="01-02-2013" class="datepicker" />
							</div>
						</div>
						
						
						
						<div class="form-actions">
							<button type="submit" class="btn btn-success">Save</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>