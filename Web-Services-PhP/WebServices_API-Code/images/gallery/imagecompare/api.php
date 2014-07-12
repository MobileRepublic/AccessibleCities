<?php
error_reporting(0);
require_once ("Rest.inc.php");
require 'image.compare.class.php';
class API extends REST {
	public $data = "";
	const DB_SERVER = "localhost";
	const DB_USER = "root";
	const DB_PASSWORD = "RootAcc12";
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
			 move_uploaded_file($tmp_name, "temp/$name");
			$class = new compareImages;
			$a = '';
			$d ='';
			while($rlt = mysql_fetch_array($sql,MYSQL_ASSOC)){
			echo "http://apps.accendotechnologies.com/imagecompare/".$rlt['image_name'];echo "<br />";
				 $test =  $class->compare("http://apps.accendotechnologies.com/imagecompare/".$rlt['image_name'],"temp/$name");
				  $a .= $test.',';
				  $d.=$rlt['id'].',';
				}//exit;
				$b = rtrim($a, ",");
				$c = explode(",",$b);
				$e = rtrim($d,",");
				$f = explode(",",$e);
				$g = array_combine($f,$c);
				$val = min($c);
				print_r( $c);exit;
				if($val==0){
					echo "Ok";
					$res = array_search($val,$g);
					$sql2 = mysql_query("SELECT * from images_info where id='".$res."'", $this->db);
					$result = mysql_fetch_array($sql2,MYSQL_ASSOC);
					unlink("http://apps.accendotechnologies.com/imagecompare/temp/$name");
					$this->response($this->json($result), 200);
				}else{
					echo "Not Match";
					unlink("http://apps.accendotechnologies.com/imagecompare/temp/$name");
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
