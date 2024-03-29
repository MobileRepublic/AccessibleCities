<?php

//echo "test"; exit;
/**
 * ApiController class file
 * @author Joachim Werner <joachim.werner@diggin-data.de>  
 */
/**
 * ApiController 
 * 
 * @uses Controller
 * @author Joachim Werner <joachim.werner@diggin-data.de>
 * @author 
 * @see http://www.gen-x-design.com/archives/making-restful-requests-in-php/
 * @license (tbd)
 */
 require_once(Yii::app()->basePath . '/extensions/image.compare.class.php');
class ApiController extends Controller 
{
    // Members
    /**
     * Key which has to be in HTTP USERNAME and PASSWORD headers 
     */
    Const APPLICATION_ID = 'ASCCPE';
 
    /**
     * Default response format
     * either 'json' or 'xml'
     */
    private $format = 'json';
    /**
     * @return array action filters
     */
    public function filters()
    {
            return array();
    }
	
	
    // Actions
    public function actionList()
    {
      $model = Users::model()->findAll();
      $data=CJSON::encode($model);
      var_dump($data);exit;
    switch($_GET['Users'])
    {
        case 'posts':
            $models = Users::model()->findAll();
            break;
        default:
            // Model not implemented error
            $this->_sendResponse(501, sprintf(
                'Error: Mode <b>list</b> is not implemented for model <b>%s</b>',
                $_GET['model']) );
            Yii::app()->end();
    }
    // Did we get some results?
    if(empty($models)) {
        // No
        $this->_sendResponse(200, 
                sprintf('No items where found for model <b>%s</b>', $_GET['model']) );
    } else {
        // Prepare response
        $rows = array();
        foreach($models as $model)
            $rows[] = $model->attributes;
        // Send the response
        $this->_sendResponse(200, CJSON::encode($rows));
    }
    }
   
	
	
	
	
	/*************************************************************
                          View Public Toilets
    **************************************************************/
	
	public function actionViewpublictoilets(){
	
	
        $request = $_SERVER['REQUEST_METHOD'];
     
        if($request == "GET"){
		try{
             
			 if($_GET['type'] == '1'){
			 $latitude              = $_GET['latitude'];
             $longitude             = $_GET['longitude'];
             $radius                = 1;
			 
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 $model = PublicToilets::model()->findAll($criteria);
			$rows = array();
			
			$i=0;
            foreach($model as $model1){
			
			// $rows['results'][$i]['id'] = $model1->attributes['id'];
			 $rows['results'][$i]['feature_name'] = $model1->attributes['feature_name'];
			 $rows['results'][$i]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$i]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$i]['elevation'] = round($model1->attributes['elevation']);
            //$rows[] = $model1->attributes;
			
			$i++;
			
			}
			
			}elseif($_GET['type'] == '2'){
			
			$latitude              = $_GET['latitude'];
            $longitude             = $_GET['longitude'];
            $radius                = 1;
			 
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 $criteria->limit = 30;	
			 $model = BuildingAcc::model()->findAll($criteria);
			$rows = array();
            $i=0;
            foreach($model as $model1){
			 $rows['results'][$i]['feature_name'] = $model1->attributes['building_name'].' '.$model1->attributes['street_address'];
			 $rows['results'][$i]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$i]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$i]['elevation'] = round($model1->attributes['elevation']);
             $i++;
			
			}
			}elseif($_GET['type'] == '3'){
			 
			 $latitude              = $_GET['latitude'];
             $longitude             = $_GET['longitude'];
             $radius                = 1;
			 
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 $model = PublicToilets::model()->findAll($criteria);
			$rows = array();
			
			$i=0;
            foreach($model as $model1){
			
			// $rows['results'][$i]['id'] = $model1->attributes['id'];
			 $rows['results'][$i]['feature_name'] = $model1->attributes['feature_name'];
			 $rows['results'][$i]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$i]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$i]['elevation'] = round($model1->attributes['elevation']);
            //$rows[] = $model1->attributes;
			
			$i++;
			
			}
			
			
			$latitude              = $_GET['latitude'];
            $longitude             = $_GET['longitude'];
            $radius                = 1;
			 
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 $criteria->limit = 30;	
			 $model = BuildingAcc::model()->findAll($criteria);
			//$rows = array();
            $j=$i+1;
            foreach($model as $model1){
			 $rows['results'][$j]['feature_name'] = $model1->attributes['building_name'].' '.$model1->attributes['street_address'];
			 $rows['results'][$j]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$j]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$j]['elevation'] = round($model1->attributes['elevation']);
             $j++;
			
			}
			
			}
      
          $this->_sendResponse(200, CJSON::encode($rows));
           
			}catch (Exception $e) {
				$this->_sendResponse(400,'[{"msg":"Try Again"}]');	
                throw new Exception('Select query failed', 0, $e);
        	}
        }
    }
		/*************************************************************
                          View Public Toilets
    **************************************************************/
	
	public function actionViewpublictoiletsall(){
	
	
        $request = $_SERVER['REQUEST_METHOD'];
     
        if($request == "GET"){
		try{
             $i=0;
			 $rows = array();
			 $a = $_GET['type'];
			 $b = explode(',',$a);
			 
			 $latitude              = $_GET['latitude'];
             $longitude             = $_GET['longitude'];
             $radius                = 1;
			 
			 
			 if(in_array("1", $b)){
						 
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 $model = PublicToilets::model()->findAll($criteria);
			//$rows = array();
			
			
            foreach($model as $model1){
			
			// $rows['results'][$i]['id'] = $model1->attributes['id'];
			 $rows['results'][$i]['feature_name'] = $model1->attributes['feature_name'];
			 $rows['results'][$i]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$i]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$i]['elevation'] = round($model1->attributes['elevation']);
            //$rows[] = $model1->attributes;
			
			$i++;
			
			}
			
			}
			if(in_array("2", $b)){
			
			
			 
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 $criteria->limit = 30;	
			 $model = BuildingAcc::model()->findAll($criteria);
			//$rows = array();
            
            foreach($model as $model1){
			 $rows['results'][$i]['feature_name'] = $model1->attributes['building_name'].' '.$model1->attributes['street_address'];
			 $rows['results'][$i]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$i]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$i]['elevation'] = round($model1->attributes['elevation']);
             $i++;
			
			}
			}
			if(in_array("3", $b)){
			
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 
			 $model = Superstops::model()->findAll($criteria);
			//$rows = array();
            
            foreach($model as $model1){
			 $rows['results'][$i]['feature_name'] = $model1->attributes['title'];
			 $rows['results'][$i]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$i]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$i]['elevation'] = round($model1->attributes['elevation']);
             $i++;
			
			}
			}
		
      
          $this->_sendResponse(200, CJSON::encode($rows));
           
			}catch (Exception $e) {
				$this->_sendResponse(400,'[{"msg":"Try Again"}]');	
                throw new Exception('Select query failed', 0, $e);
        	}
        }
    }
	/*************************************************************
                          View Building Accessories
    **************************************************************/
	
	public function actionViewbuildingaccessories(){
        $request = $_SERVER['REQUEST_METHOD'];
     
        if($request == "GET"){
		try{
             
			 
			 $latitude              = $_GET['latitude'];
             $longitude             = $_GET['longitude'];
             $radius                = 1;
			 
			 $criteria = new CDbCriteria();
             $criteria->select      = "*,( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) AS distance";
				
                $criteria->condition   = "latitude!='' and longitude!='' and ( 3959 * acos( cos( radians('".$latitude."') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('".$longitude."') ) + sin( radians('".$latitude."') ) * sin( radians( latitude ) ) ) ) < '".$radius."'";
			 $criteria->limit = 30;	
			 $model = BuildingAcc::model()->findAll($criteria);
			$rows = array();
            $i=0;
            foreach($model as $model1){
			
			// $rows['results'][$i]['id'] = $model1->attributes['id'];
			 $rows['results'][$i]['feature_name'] = $model1->attributes['building_name'].' '.$model1->attributes['street_address'];
			 $rows['results'][$i]['latitude'] = $model1->attributes['latitude'];
			 $rows['results'][$i]['longitude'] = $model1->attributes['longitude'];
			 $rows['results'][$i]['elevation'] = round($model1->attributes['elevation']);
            //$rows[] = $model1->attributes;
			
			$i++;
			
			}
      
          $this->_sendResponse(200, CJSON::encode($rows));
           
			}catch (Exception $e) {
				$this->_sendResponse(400,'[{"msg":"Try Again"}]');	
                throw new Exception('Select query failed', 0, $e);
        	}
        }
    }
	
	
	/*************************************************************
                          View Public Toilets
    **************************************************************/
	/*
public function actionImagesinfo(){
        $request = $_SERVER['REQUEST_METHOD'];
     
        if($request == "POST"){
		echo "testr";exit;
		 $model = new ImagesInfo;
		//print_r($_FILES['image_name']['name']);
		//$uploadedFile = CUploadedFile::getInstance($model,'image_name');
			$class = new compareImages; 
			echo Yii::app()->basePath;
			var_dump( Yii::app()->basePath . '/../img/Chrysanthemum.jpg');exit;
		echo 	$test =  $class->compare(Yii::app()->basePath . '/../img/Chrysanthemum.jpg',Yii::app()->basePath . '/../img/Chrysanthemum.jpg');
			print_r($uploadedFile);exit;
		//include( 'http://apps.accendotechnologies.com/melbourne/protected/extensions/image.compare.class.php');
		//Yii::import("application.image.compare.class.php");
		try{  
		     $model = new ImagesInfo;
            // $uploadedFile = CUploadedFile::getInstance($model,'image_name');
			  $tmp_name = $_FILES["image_name"]["tmp_name"];
			 print_r($tmp_name);exit; 
 $name = $_FILES["image_name"]["name"];

 move_uploaded_file($tmp_name, Yii::app()->basePath . '/../img/temp/'.$name)
			// print_r($uploadedFile);exit;
			 $model2 = ImagesInfo::model()->findAll();
			//if (!empty($uploadedFile)) {
                //$filename = $uploadedFile->saveAs(Yii::app()->basePath . '/../img/'.$uploadedFile);
              //}
			  $class = new compareImages;
				$a = '';
				$d ='';
			 foreach($model2 as $model3) {
	  $test =  $class->compare(Yii::app()->basePath . '/../img/'.$model3->image_name,Yii::app()->basePath . '/../img/temp/'.$name);
	  }
	  $a .= $test.',';
	  $d.=$model3->id.',';
	  $b = rtrim($a, ",");
	$c = explode(",",$b);
	$e = rtrim($d,",");
	$f = explode(",",$e);
	echo "<br />";print_r(min($c));
	$g = array_combine($f,$c);
	echo "<pre>"; print_r(min(array_keys($g)));
	
			 
			 
			 $criteria = new CDbCriteria();
             $model1 = ImagesInfo::model()->findAll($criteria);
			$rows = array();
            foreach($model1 as $model11)
            $rows[] = $model11->attributes;
      
          $this->_sendResponse(200, CJSON::encode($rows));
           
			}catch (Exception $e) {
				$this->_sendResponse(400,'[{"msg":"Try Again"}]');	
                throw new Exception('Select query failed', 0, $e);
        	}
        }
   } 
	
	
	
	

	
	
	
	
	*/
	
	
	
	
	
	
	
	
	
	 /**
     * Sends the API response 
     * 
     * @param int $status 
     * @param string $body 
     * @param string $content_type 
     * @access private
     * @return void
     */
    private function _sendResponse($status = 200, $body = '', $content_type = 'text/json')
    {
        $status_header = 'HTTP/1.1 ' . $status . ' ' . $this->_getStatusCodeMessage($status);
        // set the status
        header($status_header);
        // set the content type
        header('Content-type: ' . $content_type);

        // pages with body are easy
        if($body != '')
        {
            // send the body
            echo $body;
            exit;
        }
        // we need to create the body if none is passed
        else
        {
            // create some body messages
            $message = '';

            // this is purely optional, but makes the pages a little nicer to read
            // for your users.  Since you won't likely send a lot of different status codes,
            // this also shouldn't be too ponderous to maintain
            switch($status)
            {
                case 401:
                    $message = 'You must be authorized to view this page.';
                    break;
                case 404:
                    $message = 'The requested URL ' . $_SERVER['REQUEST_URI'] . ' was not found.';
                    break;
                case 500:
                    $message = 'The server encountered an error processing your request.';
                    break;
                case 501:
                    $message = 'The requested method is not implemented.';
                    break;
            }

            // servers don't always have a signature turned on (this is an apache directive "ServerSignature On")
            $signature = ($_SERVER['SERVER_SIGNATURE'] == '') ? $_SERVER['SERVER_SOFTWARE'] . ' Server at ' . $_SERVER['SERVER_NAME'] . ' Port ' . $_SERVER['SERVER_PORT'] : $_SERVER['SERVER_SIGNATURE'];

            // this should be templatized in a real-world solution
            $body = '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
                        <html>
                            <head>
                                <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
                                <title>' . $status . ' ' . $this->_getStatusCodeMessage($status) . '</title>
                            </head>
                            <body>
                                <h1>' . $this->_getStatusCodeMessage($status) . '</h1>
                                <p>' . $message . '</p>
                                <hr />
                                <address>' . $signature . '</address>
                            </body>
                        </html>';

            echo $body;
            exit;
        }
    } // }}}            
    // {{{ _getStatusCodeMessage
    /**
     * Gets the message for a status code
     * 
     * @param mixed $status 
     * @access private
     * @return string
     */
    private function _getStatusCodeMessage($status)
    {
        // these could be stored in a .ini file and loaded
        // via parse_ini_file()... however, this will suffice
        // for an example
        $codes = Array(
            100 => 'Continue',
            101 => 'Switching Protocols',
            200 => 'OK',
            201 => 'Created',
            202 => 'Accepted',
            203 => 'Non-Authoritative Information',
            204 => 'No Content',
            205 => 'Reset Content',
            206 => 'Partial Content',
            300 => 'Multiple Choices',
            301 => 'Moved Permanently',
            302 => 'Found',
            303 => 'See Other',
            304 => 'Not Modified',
            305 => 'Use Proxy',
            306 => '(Unused)',
            307 => 'Temporary Redirect',
            400 => 'Bad Request',
            401 => 'Unauthorized',
            402 => 'Payment Required',
            403 => 'Forbidden',
            404 => 'Not Found',
            405 => 'Method Not Allowed',
            406 => 'Not Acceptable',
            407 => 'Proxy Authentication Required',
            408 => 'Request Timeout',
            409 => 'Conflict',
            410 => 'Gone',
            411 => 'Length Required',
            412 => 'Precondition Failed',
            413 => 'Request Entity Too Large',
            414 => 'Request-URI Too Long',
            415 => 'Unsupported Media Type',
            416 => 'Requested Range Not Satisfiable',
            417 => 'Expectation Failed',
            500 => 'Internal Server Error',
            501 => 'Not Implemented',
            502 => 'Bad Gateway',
            503 => 'Service Unavailable',
            504 => 'Gateway Timeout',
            505 => 'HTTP Version Not Supported'
        );

        return (isset($codes[$status])) ? $codes[$status] : '';
    } // }}} 
    // {{{ _checkAuth
    /**
     * Checks if a request is authorized
     * 
     * @access private
     * @return void
     */
    private function _checkAuth()
    {
        // Check if we have the USERNAME and PASSWORD HTTP headers set?
        if(!(isset($_SERVER['HTTP_X_'.self::APPLICATION_ID.'_USERNAME']) and isset($_SERVER['HTTP_X_'.self::APPLICATION_ID.'_PASSWORD']))) {
            // Error: Unauthorized
            $this->_sendResponse(401);
        }
        $username = $_SERVER['HTTP_X_'.self::APPLICATION_ID.'_USERNAME'];
        $password = $_SERVER['HTTP_X_'.self::APPLICATION_ID.'_PASSWORD'];
        // Find the user
        $user=User::model()->find('LOWER(username)=?',array(strtolower($username)));
        if($user===null) {
            // Error: Unauthorized
            $this->_sendResponse(401, 'Error: User Name is invalid');
        } else if(!$user->validatePassword($password)) {
            // Error: Unauthorized
            $this->_sendResponse(401, 'Error: User Password is invalid');
        }
    } // }}} 
    // {{{ _getObjectEncoded
    /**
     * Returns the json or xml encoded array
     * 
     * @param mixed $model 
     * @param mixed $array Data to be encoded
     * @access private
     * @return void
     */
    private function _getObjectEncoded($model, $array)
    {
        if(isset($_GET['format']))
            $this->format = $_GET['format'];

        if($this->format=='json')
        {
            return CJSON::encode($array);
        }
        elseif($this->format=='xml')
        {
            $result = '<?xml version="1.0">';
            $result .= "\n<$model>\n";
            foreach($array as $key=>$value)
                $result .= "    <$key>".utf8_encode($value)."</$key>\n"; 
            $result .= '</'.$model.'>';
            return $result;
        }
        else
        {
            return;
        }
    } // }}} 
    // }}} End Other Methods
}

/* vim:set ai sw=4 sts=4 et fdm=marker fdc=4: */
?>
