<?php

/**
 * This is the model class for table "ets_empmaster".
 *
 * The followings are the available columns in table 'ets_empmaster':
 * @property integer $empmaster_id
 * @property string $employee_code
 * @property string $emp_fname
 * @property string $emp_lname
 * @property string $emp_mname
 * @property integer $emp_type
 * @property integer $emp_role
 * @property string $emp_profileimage
 * @property string $emp_weekday
 * @property integer $emp_activestatus
 * @property integer $emp_createdby
 * @property string $emp_createddate
 * @property integer $emp_updatedby
 * @property string $emp_updateddate
 */
class EtsEmpmaster extends CActiveRecord
{
	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'ets_empmaster';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('employee_code, emp_fname, emp_lname, emp_type, emp_role, emp_weekday, emp_createdby, emp_createddate, emp_updatedby, emp_updateddate', 'required'),
			array('emp_type, emp_role, emp_activestatus, emp_createdby, emp_updatedby', 'numerical', 'integerOnly'=>true),
			array('employee_code, emp_weekday', 'length', 'max'=>10),
			array('emp_fname, emp_lname', 'length', 'max'=>150),
			array('emp_mname', 'length', 'max'=>45),
			array('emp_profileimage', 'length', 'max'=>250),
			// The following rule is used by search().
			// @todo Please remove those attributes that should not be searched.
			array('empmaster_id, employee_code, emp_fname, emp_lname, emp_mname, emp_type, emp_role, emp_profileimage, emp_weekday, emp_activestatus, emp_createdby, emp_createddate, emp_updatedby, emp_updateddate', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'empmaster_id' => 'Empmaster',
			'employee_code' => 'Employee Code',
			'emp_fname' => 'Emp Fname',
			'emp_lname' => 'Emp Lname',
			'emp_mname' => 'Emp Mname',
			'emp_type' => 'Emp Type',
			'emp_role' => 'Emp Role',
			'emp_profileimage' => 'Emp Profileimage',
			'emp_weekday' => 'Emp Weekday',
			'emp_activestatus' => 'Emp Activestatus',
			'emp_createdby' => 'Emp Createdby',
			'emp_createddate' => 'Emp Createddate',
			'emp_updatedby' => 'Emp Updatedby',
			'emp_updateddate' => 'Emp Updateddate',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 *
	 * Typical usecase:
	 * - Initialize the model fields with values from filter form.
	 * - Execute this method to get CActiveDataProvider instance which will filter
	 * models according to data in model fields.
	 * - Pass data provider to CGridView, CListView or any similar widget.
	 *
	 * @return CActiveDataProvider the data provider that can return the models
	 * based on the search/filter conditions.
	 */
	public function search()
	{
		// @todo Please modify the following code to remove attributes that should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('empmaster_id',$this->empmaster_id);
		$criteria->compare('employee_code',$this->employee_code,true);
		$criteria->compare('emp_fname',$this->emp_fname,true);
		$criteria->compare('emp_lname',$this->emp_lname,true);
		$criteria->compare('emp_mname',$this->emp_mname,true);
		$criteria->compare('emp_type',$this->emp_type);
		$criteria->compare('emp_role',$this->emp_role);
		$criteria->compare('emp_profileimage',$this->emp_profileimage,true);
		$criteria->compare('emp_weekday',$this->emp_weekday,true);
		$criteria->compare('emp_activestatus',$this->emp_activestatus);
		$criteria->compare('emp_createdby',$this->emp_createdby);
		$criteria->compare('emp_createddate',$this->emp_createddate,true);
		$criteria->compare('emp_updatedby',$this->emp_updatedby);
		$criteria->compare('emp_updateddate',$this->emp_updateddate,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}

	/**
	 * Returns the static model of the specified AR class.
	 * Please note that you should have this exact method in all your CActiveRecord descendants!
	 * @param string $className active record class name.
	 * @return EtsEmpmaster the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}
