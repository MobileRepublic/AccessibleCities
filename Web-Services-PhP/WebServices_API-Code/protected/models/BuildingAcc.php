<?php

/**
 * This is the model class for table "building_acc".
 *
 * The followings are the available columns in table 'building_acc':
 * @property integer $id
 * @property string $building_name
 * @property string $street_address
 * @property string $suburb
 * @property integer $postcode
 * @property string $accessibility_type
 * @property string $accessibility_type_description
 * @property integer $accessibility_rating
 * @property integer $building_height
 * @property integer $construction_year
 * @property integer $refurbished_year
 * @property string $latitude
 * @property string $longitude
 * @property string $location
 * @property string $elevation
 */
class BuildingAcc extends CActiveRecord
{
	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'building_acc';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('building_name, street_address, suburb, postcode, accessibility_type, accessibility_type_description, accessibility_rating, building_height, construction_year, refurbished_year, latitude, longitude, location, elevation', 'required'),
			array('postcode, accessibility_rating, building_height, construction_year, refurbished_year', 'numerical', 'integerOnly'=>true),
			array('building_name, street_address, location', 'length', 'max'=>100),
			array('suburb, accessibility_type', 'length', 'max'=>50),
			array('latitude, longitude, elevation', 'length', 'max'=>25),
			// The following rule is used by search().
			// @todo Please remove those attributes that should not be searched.
			array('id, building_name, street_address, suburb, postcode, accessibility_type, accessibility_type_description, accessibility_rating, building_height, construction_year, refurbished_year, latitude, longitude, location, elevation', 'safe', 'on'=>'search'),
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
			'id' => 'ID',
			'building_name' => 'Building Name',
			'street_address' => 'Street Address',
			'suburb' => 'Suburb',
			'postcode' => 'Postcode',
			'accessibility_type' => 'Accessibility Type',
			'accessibility_type_description' => 'Accessibility Type Description',
			'accessibility_rating' => 'Accessibility Rating',
			'building_height' => 'Building Height',
			'construction_year' => 'Construction Year',
			'refurbished_year' => 'Refurbished Year',
			'latitude' => 'Latitude',
			'longitude' => 'Longitude',
			'location' => 'Location',
			'elevation' => 'Elevation',
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

		$criteria->compare('id',$this->id);
		$criteria->compare('building_name',$this->building_name,true);
		$criteria->compare('street_address',$this->street_address,true);
		$criteria->compare('suburb',$this->suburb,true);
		$criteria->compare('postcode',$this->postcode);
		$criteria->compare('accessibility_type',$this->accessibility_type,true);
		$criteria->compare('accessibility_type_description',$this->accessibility_type_description,true);
		$criteria->compare('accessibility_rating',$this->accessibility_rating);
		$criteria->compare('building_height',$this->building_height);
		$criteria->compare('construction_year',$this->construction_year);
		$criteria->compare('refurbished_year',$this->refurbished_year);
		$criteria->compare('latitude',$this->latitude,true);
		$criteria->compare('longitude',$this->longitude,true);
		$criteria->compare('location',$this->location,true);
		$criteria->compare('elevation',$this->elevation,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
	
	/**
	* Truncate table.
	*/
	public function truncateTable()
    {
        $this->getDbConnection()->createCommand()->truncateTable($this->tableName());
    }

	/**
	 * Returns the static model of the specified AR class.
	 * Please note that you should have this exact method in all your CActiveRecord descendants!
	 * @param string $className active record class name.
	 * @return BuildingAcc the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}
