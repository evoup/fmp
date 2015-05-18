<?php
/*
  +----------------------------------------------------------------------+
  | Name: publish.m
  +----------------------------------------------------------------------+
  | Comment: 发布
  +----------------------------------------------------------------------+
  | Author:Evoup     evoex@126.com                                                     
  +----------------------------------------------------------------------+
  | Create: 2015-05-11 18:40:23
  +----------------------------------------------------------------------+
  | Last-Modified: 2015-05-15 17:56:07
  +----------------------------------------------------------------------+
 */
use FacebookAds\Api;
use FacebookAds\Object\AdImage;
use FacebookAds\Object\Fields\AdImageFields;
use FacebookAds\Object\AdCreative;
use FacebookAds\Object\Fields\AdCreativeFields;
use FacebookAds\Object\Fields\ObjectStorySpecFields;
use FacebookAds\Object\Fields\ObjectStory\LinkDataFields;
use FacebookAds\Object\Fields\ObjectStory\AttachmentDataFields;
use FacebookAds\Object\CustomAudience;
use FacebookAds\Object\Fields\CustomAudienceFields;
use FacebookAds\Object\Values\CustomAudienceTypes;
use FacebookAds\Object\AdCampaign;
use FacebookAds\Object\Fields\AdCampaignFields;
use FacebookAds\Object\Values\AdObjectives;
use FacebookAds\Object\TargetingSpecs;
use FacebookAds\Object\Fields\TargetingSpecsFields;
use FacebookAds\Object\AdSet;
use FacebookAds\Object\Fields\AdSetFields;
use FacebookAds\Object\Fields\AdGroupBidInfoFields;
use FacebookAds\Object\Values\BidTypes;
use FacebookAds\Object\AdGroup;
use FacebookAds\Object\Fields\AdGroupFields;

Api::init($app_id, $app_secret, $access_token);
echo $account_id;

print_r($_SESSION);
print_r($_POST['commit_data']);
//$page_id = "1568648156711755"; // 普通个人page没有意义
// business page
$page_id=@$_SESSION[__SESSION_CAMP_EDIT]['step5']['selected_page'];
//$pixel_id = "";

$child_attachments=null;
switch($_SESSION[__SESSION_CAMP_EDIT]['step1']['objective']) {
case (__OBJT_MULTI_PRODUCT):
    $child_attachments=array();
    $multiProductsInfo=$_SESSION[__SESSION_CAMP_EDIT]['step5']['product_multi'];
    include(dirname(__FILE__).'/../inc/conn.php');
    for ($i=0;$i<sizeof($multiProductsInfo);$i++) {
        $image[$i]=new AdImage(null, $account_id);
        $tmpFile=tempnam("/tmp","material_");
        // 出图
        $hash=$multiProductsInfo[$i]['product_pic'];
        $query="select content,mime from t_fmp_material where fmp_hash='{$hash}' limit 1;";
        if ($result=$link->query($query)) {
            while ($row=mysqli_fetch_assoc($result)) {
                $tmpFile=$tmpFile.'.'.getExtFromMime($row['mime']);
                file_put_contents($tmpFile,$row['content']);
            }
        }
        // Upload Images
        $image[$i]->{AdImageFields::FILENAME} = $tmpFile;
        $image[$i]->create();
        echo $image[$i]->hash;
        unlink($tmpFile);
        // Create Multi Product Ad Creative
        $child_attachments[$i][AttachmentDataFields::NAME]=$multiProductsInfo[$i]['product_name'];
        $child_attachments[$i][AttachmentDataFields::DESCRIPTION]=$multiProductsInfo[$i]['product_desc'];
        $child_attachments[$i][AttachmentDataFields::LINK]=$multiProductsInfo[$i]['product_link'];
        $child_attachments[$i][AttachmentDataFields::IMAGE_HASH]=$image[$i]->hash;
    }
    break;
}

// The ObjectStorySpec helps bring some order to a complex
// API spec that assists with creating page posts inline.

@$object_story_spec = array(
  ObjectStorySpecFields::PAGE_ID => $page_id,
  ObjectStorySpecFields::LINK_DATA => array(
    LinkDataFields::MESSAGE => $_SESSION[__SESSION_CAMP_EDIT]['step5']['message'],
    LinkDataFields::LINK => $_SESSION[__SESSION_CAMP_EDIT]['step5']['link'],
    LinkDataFields::CAPTION => $_SESSION[__SESSION_CAMP_EDIT]['step5']['link'],
    LinkDataFields::CHILD_ATTACHMENTS => $child_attachments
));


$creative = new AdCreative(null, $account_id);

$creative->setData(array(
  AdCreativeFields::NAME => 'Africa Creative 1',
  AdCreativeFields::OBJECT_STORY_SPEC => $object_story_spec
));

$creative->create();
$creative_id = $creative->id;
echo 'Creative ID: '.$creative_id ."\n";


// Create a campaign
// A campaign can have one or more adsets

$campaign  = new AdCampaign(null, $account_id);
@$campaign->setData(array(
  AdCampaignFields::NAME => $_SESSION[__SESSION_CAMP_EDIT] ['step1']['campaignName'],
  AdCampaignFields::OBJECTIVE => AdObjectives::WEBSITE_CLICKS,
  AdCampaignFields::STATUS => AdCampaign::STATUS_PAUSED
));

$campaign->validate()->create();
$campaign_id = $campaign->id;
echo "Campaign ID: " . $campaign_id . "\n";

echo "----------------------------\n";
foreach($_POST['commit_data'] as $cdInfo) {
    print_r($cdInfo);
    /*{{{*/
    // Create targeting
    $targeting = new TargetingSpecs();
    $targeting->{TargetingSpecsFields::GEO_LOCATIONS}
    = array(
        'countries' => explode('|', $cdInfo['location'])
    );

    // Create an ad set
    // An adset can have one or more ad units
    $adset = new AdSet(null, $account_id);
    @$adset->setData(array(
        AdSetFields::NAME => $cdInfo['ad_set_name'],
        AdSetFields::CAMPAIGN_GROUP_ID => $campaign_id,
        AdSetFields::CAMPAIGN_STATUS => AdSet::STATUS_ACTIVE,
        AdSetFields::DAILY_BUDGET => $_SESSION[__SESSION_CAMP_EDIT]['step4']['budget']*100,
        AdSetFields::TARGETING => $targeting,
        AdSetFields::BID_TYPE => BidTypes::BID_TYPE_CPM,
        AdSetFields::BID_INFO => array(AdGroupBidInfoFields::IMPRESSIONS => 2),
        AdSetFields::PROMOTED_OBJECT => array('page_id' => $page_id),
    ));

    $adset->validate()->create();
    $adset_id = $adset->id;
    echo 'AdSet ID: '. $adset_id . "\n";

    // Create an ad
    // Each ad can only have one creative
    echo "account_id:{$account_id}";
    echo "adset_id:{$adset_id}";
    echo "creative_id:{$creative_id}";
    $adgroup = new AdGroup(null, $account_id);
    $adgroup->{AdGroupFields::ADGROUP_STATUS} = AdGroup::STATUS_ACTIVE;
    //$adgroup->{AdGroupFields::NAME} = 'test adgroup';
    $adgroup->{AdGroupFields::NAME} = $cdInfo['ad_set_name'];
    $adgroup->{AdGroupFields::CAMPAIGN_ID}=$adset_id;
    $adgroup->{AdGroupFields::CREATIVE} = array('creative_id' => $creative_id);

    $adgroup->create();
    $adgroup_id=$adgroup->id;
    echo 'AdGroup ID: ' . $adgroup_id . "\n";
    /*}}}*/
}

