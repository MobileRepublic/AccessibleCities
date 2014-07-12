<?php

/**
 * This is the model class for table "public_toilets".
 *
 * The followings are the available columns in table 'public_toilets':
 * @property integer $id
 * @property string $feature_id
 * @property string $comp_type
 * @property string $sub_theme
 * @property string $feature_name
 * @property string $male
 * @property string $female
 * @property string $disabled
 * @property string $unisex
 * @property string $babychange
 * @property string $latitude
 * @property string $longitude
 * @property string $elevation
 */
class PublicToilets extends CActiveRecord
{
	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'public_toilets';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('feature_id, comp_type, sub_theme, feature_name, male, female, disabled, unisex, babychange, latitude, longitude, elevation', 'required'),
			array('feature_id', 'length', 'max'=>50),
			array('comp_type, sub_theme', 'length', 'max'=>100),
			array('male, female, disabled, unisex, babychange', 'length', 'max'=>10),
			array('latitude, longitude, elevation', 'length', 'max'=>25),
			// The following rule is used by search().
			// @todo Please remove those attributes that should not be searched.
			array('id, feature_id, comp_type, sub_theme, feature_name, male, female, disabled, unisex, babychange, latitude, longitude, elevation', 'safe', 'on'=>'search'),
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
			'feature_id' => 'Feature',
			'comp_type' => 'Comp Type',
			'sub_theme' => 'Sub Theme',
			'feature_name' => 'Feature Name',
			'male' => 'Male',
			'female' => 'Female',
			'disabled' => 'Disabled',
			'unisex' => 'Unisex',
			'babychange' => 'Babychange',
			'latitude' => 'Latitude',
			'longitude' => 'Longitude',
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
		$criteria->compare('feature_id',$this->feature_id,true);
		$criteria->compare('comp_type',$this->comp_type,true);
		$criteria->compare('sub_theme',$this->sub_theme,true);
		$criteria->compare('feature_name',$this->feature_name,true);
		$criteria->compare('male',$this->male,true);
		$criteria->compare('female',$this->female,true);
		$criteria->compare('disabled',$this->disabled,true);
		$criteria->compare('unisex',$this->unisex,true);
		$criteria->compare('babychange',$this->babychange,true);
		$criteria->compare('latitude',$this->latitude,true);
		$criteria->compare('longitude',$this->longitude,true);
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
	 * @return PublicToilets the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}
