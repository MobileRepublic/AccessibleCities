<?php
error_reporting(0);
require_once ("Rest.inc.php");
require 'image.compare.class.php';
class API extends REST {
	public $data = "";
	const DB_SERVER = "localhost";
	const DB_USER = "root";
	const DB_PASSWORD = "@ccend0Admin";
	const DB = "melbourne";
	private $db = NULL;
	public function __construct() {
		parent::__construct();
		// Init parent contructor
		$this -> dbConnect();
		// Initiate Database connection
	}
	/*
	 *  Database connection
	 */
	private function dbConnect() {
		$this -> db = mysql_connect(self::DB_SERVER, self::DB_USER, self::DB_PASSWORD);
		if ($this -> db)
			mysql_select_db(self::DB, $this -> db);
	}
	public function processApi() {
		$func = strtolower(trim(str_replace("/", "", $_REQUEST['rquest'])));
		if ((int) method_exists($this, $func) > 0)
			$this -> $func();
		else {
			$this -> response('', 404);
		}
	}
	private function testpurchase() {
		if ($this -> get_request_method() != "POST") {
			$this -> response('', 406);
		}
		$logintype = $this -> _request['message'];
		$error = array('status' => "success", "msg" => $logintype);
		$this -> response($this -> json($error), 200);
	}
	
	//Added by Samrat M for testing API timeouts
	private function imagescan(){
	//try{
			if($this->get_request_method() != "POST"){
				$this->response('',406);
			}
			 $sql = mysql_query("SELECT * from images_info", $this->db);
			 $tmp_name = $_FILES["image_name"]["tmp_name"];
			 $name = $_FILES["image_name"]["name"];
			// echo $name; echo "<br />";
			// echo $tmp_name;
			 
			 $target_path1 = "/var/www/html/melbourne/temp/";

            $target_path = $target_path1.$name;
			 
			$move =  move_uploaded_file($_FILES["image_name"]["tmp_name"], $target_path);
			
			$class = new compareImages;
			$a = '';
			$d ='';
			while($rlt = mysql_fetch_array($sql,MYSQL_ASSOC)){

			
				 $test =  $class->compare("http://ec2-54-79-7-64.ap-southeast-2.compute.amazonaws.com/melbourne/imagescompare/".$rlt['image_name'],"http://ec2-54-79-7-64.ap-southeast-2.compute.amazonaws.com/melbourne/temp/".$name);


                                 
                                    $ufile ="http://ec2-54-79-7-64.ap-southeast-2.compute.amazonaws.com/melbourne/temp/".$name;

                                    chmod($ufile, 0777);

				 //echo $test;
				  $a .= $test.',';
				  $d.=$rlt['id'].',';
				}//exit;
				//print_r($a);exit;
				$b = rtrim($a, ",");
				$c = explode(",",$b);
				$e = rtrim($d,",");
				$f = explode(",",$e);
				$g = array_combine($f,$c);
				$val = min($c);
				//echo $val;exit;
				//print_r($g);exit;
				if($val == '0'){
					//echo "Ok";
					$res = array_search($val,$g);
					//echo $res;
					$sql2 = mysql_query("SELECT * from images_info where id='".$res."'", $this->db);
					$result = mysql_fetch_array($sql2,MYSQL_ASSOC);
					array_push($result['status'], "Success");
					//$result .= array('status' => "");
					unlink($target_path);
					$this->response($this->json($result), 200);
				}elseif($val != '0' || $val == ' '){
				  
				   
				    $l = explode(",",rtrim($a,','));
					
					
					$lmin = min($l);
					
					// echo $lmin;  exit;
					
					if ($lmin <= '20'){ 
					$res = array_search($val,$g);
					$sql2 = mysql_query("SELECT * from images_info where id='".$res."'", $this->db);
					$result = mysql_fetch_array($sql2,MYSQL_ASSOC);
					array_push($result['status'], "Success");
					}else{
				    
					//echo "Images are not matched"; exit;
					$result = array('status' => "Fail", 'result' => 'Images are not matched');
					}
					$this->response($this->json($result), 200);
					unlink($target_path);
				}
				$this->response('',204); // If no records "No Content" status}*/
		//}catch(Exception $e){
		//		$this->response('Try Again',204);
		//		throw new Exception('Select query failed', 0, $e);
		//}	
	}
        private function json($data) {
		if (is_array($data)) {
			return json_encode($data);
		}
	}
}
// Initiiate Library
$api = new API;
$api -> processApi();
?>
