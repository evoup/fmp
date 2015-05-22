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
$publish_task_content="";
$fmp_uid=$_SESSION[__SESSION_FMP_UID];
$task_type=__TASK_PUBLISH;
$task_status=__TASKSTAT_READY;
$budget=$_SESSION[__SESSION_CAMP_EDIT]['step4']['budget']*100;
$schedule_start=$_SESSION[__SESSION_CAMP_EDIT]['step4']['schedule_start'];
$schedule_end=$_SESSION[__SESSION_CAMP_EDIT]['step4']['schedule_end'];
// promote_page 普通个人page没有意义,需要是business page，且发布广告的帐号要有管理该page的权限
//$pixel_id = "";
$promote_page=$_SESSION[__SESSION_CAMP_EDIT]['step5']['selected_page'];
$messages=$_SESSION[__SESSION_CAMP_EDIT]['step5']['messages'];
$link=$_SESSION[__SESSION_CAMP_EDIT]['step5']['link'];
foreach($_SESSION[__SESSION_CAMP_EDIT]['step5']['product_multi'] as $productInfo){
    unset($productInfo['product_pic_url']); // 图片url信息不用传递 
    $multiProductsInfo[]=$productInfo;
}

// TODO per cost
$task_content=json_encode(array(
    'objective'=>__OBJT_MULTI_PRODUCT,
    'fmp_uid'=>$fmp_uid,
    'budget'=>$budget,
    'bid_type'=>__BYT_CPC,
    'per_cost'=>2,
    'schedule_start'=>$schedule_start,
    'schedule_end'=>$schedule_end,
    'promote_page'=>$promote_page,
    'messages'=>$messages,
    'link'=>$link,
    'product_multi'=>$multiProductsInfo
));

// 检查是否有正在发布中的任务，如果有超过指定数目未发布(目前为3个)的任务，则无法再发布
$status_ready=__TASKSTAT_READY;
$check_task_query=<<<EOT
SELECT count(*) FROM `t_fmp_task`
    WHERE `fmp_user_id`={$fmp_uid} AND `status`={$status_ready};
EOT;
include(dirname(__FILE__).'/../inc/conn.php');
if ($result=$link->query($check_task_query)) {
    $row=mysqli_fetch_assoc($result);
    $max_ready_nums=$row['count(*)'];
    if($max_ready_nums>__FMP_MAX_READY_PUBLISH_TASKS) {
        echo "reach max ready publish task nums!\n";
    } 
}

$insert_task_query=<<<EOT
INSERT INTO `t_fmp_task`(fmp_user_id,type,status,content) 
    VALUES({$fmp_uid},{$task_type},{$task_status},'{$task_content}');
EOT;
echo $insert_task_query;
$task_id=null;
if ($link->query($insert_task_query)) {
    $task_id=$link->insert_id;
} else {
    addLog(__FMP_LOGTYPE_ERROR,array('run query error'=>$insert_task_query));
} 
@mysqli_close($link);
// 队列生产者，写一个任务
require __API_ROOT.'/GPLlib/predis-1.0/autoload.php';
$queue_server = array(
    'host'     => __REDIS_HOST,
    'port'     => __REDIS_PORT,
    'database' => __REDIS_DB_INDEX
);
$redis_client = new Predis\Client($queue_server + array('read_write_timeout' => 0));
$queue_content=json_encode(array(
    'task_id'=>$task_id,
    'task_type'=>$task_type,
    'task_content'=>json_decode($task_content)
));
$redis_client->lpush(__REDIS_QUEUE_NAME,$queue_content);
echo "done";
die;

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
sleep(5);


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
sleep(5);

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
    $targeting->{TargetingSpecsFields::AGE_MIN}=$cdInfo['age_from'];
    $targeting->{TargetingSpecsFields::AGE_MAX}=$cdInfo['age_to'];

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
    sleep(25);
}

