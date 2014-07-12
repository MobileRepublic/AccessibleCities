<?php

/**
 * This is the model class for table "current_offers".
 *
 * The followings are the available columns in table 'current_offers':
 * @property string $external_id
 * @property string $item_name
 * @property string $item_description
 * @property string $release_date
 * @property string $expiry_date
 * @property string $weight
 * @property string $template_id
 * @property string $enabled
 * @property string $entityId
 * @property string $classifications
 * @property string $address
 * @property string $n_a
 * @property string $city
 * @property string $state
 * @property string $zipcode
 * @property string $country_of_domain_purchase_is_made_from
 * @property string $promotional_headline
 * @property string $call_to_action
 * @property string $promotional_image
 */
class PublicForm extends CActiveRecord
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
			array('file', 'file', 
					'types'=>'csv',
					'maxSize'=>1024 * 1024 * 10, // 10MB
					'tooLarge'=>'The file was larger than 10MB. Please upload a smaller file.',
					'allowEmpty' => false
				  ),
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
            'file' => 'Select file',
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
	
	
	
	
	
	
	/**
	 * Returns the static model of the specified AR class.
	 * Please note that you should have this exact method in all your CActiveRecord descendants!
	 * @param string $className active record class name.
	 * @return CurrentOffers the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}
