<?php

/**
 * This is the model class for table "ets_emplogin".
 *
 * The followings are the available columns in table 'ets_emplogin':
 * @property integer $emplogin_id
 * @property integer $empmaster_id
 * @property string $employee_email
 * @property string $employee_pwd
 * @property integer $createdby
 * @property string $createddate
 * @property integer $updatedby
 * @property string $updateddate
 */
class EtsEmplogin extends CActiveRecord
{
	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'ets_emplogin';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('empmaster_id, employee_email, employee_pwd, createdby, createddate, updatedby', 'required'),
			array('empmaster_id, createdby, updatedby', 'numerical', 'integerOnly'=>true),
			array('employee_email, employee_pwd', 'length', 'max'=>255),
			array('updateddate', 'safe'),
			// The following rule is used by search().
			// @todo Please remove those attributes that should not be searched.
			array('emplogin_id, empmaster_id, employee_email, employee_pwd, createdby, createddate, updatedby, updateddate', 'safe', 'on'=>'search'),
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
			'emplogin_id' => 'Emplogin',
			'empmaster_id' => 'Empmaster',
			'employee_email' => 'Employee Email',
			'employee_pwd' => 'Employee Pwd',
			'createdby' => 'Createdby',
			'createddate' => 'Createddate',
			'updatedby' => 'Updatedby',
			'updateddate' => 'Updateddate',
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

		$criteria->compare('emplogin_id',$this->emplogin_id);
		$criteria->compare('empmaster_id',$this->empmaster_id);
		$criteria->compare('employee_email',$this->employee_email,true);
		$criteria->compare('employee_pwd',$this->employee_pwd,true);
		$criteria->compare('createdby',$this->createdby);
		$criteria->compare('createddate',$this->createddate,true);
		$criteria->compare('updatedby',$this->updatedby);
		$criteria->compare('updateddate',$this->updateddate,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}

	/**
	 * Returns the static model of the specified AR class.
	 * Please note that you should have this exact method in all your CActiveRecord descendants!
	 * @param string $className active record class name.
	 * @return EtsEmplogin the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}
