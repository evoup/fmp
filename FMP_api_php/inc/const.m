<?php
/*
  +----------------------------------------------------------------------+
  | Name:inc/const.m                                                     |
  +----------------------------------------------------------------------+
  | Comment:MMS RESTful API const                                        |
  +----------------------------------------------------------------------+
  | Author:Evoup                                                         |
  +----------------------------------------------------------------------+
  | Created:2011-02-22 10:30:48                                          |
  +----------------------------------------------------------------------+
  | Last-Modified: 2015-05-21 18:09:59
  +----------------------------------------------------------------------+
 */

/* 版本号 */
define('__VERSION','1.0');        //主版本号',代表主要功能分支
define('__SUBVERSION','r1590');   //小版本号',即subversion版本号
/* {{{ db mysql setting*/
list($mysql_host,$mysql_port)=isset($conf['mysql_host'])?explode(':',$conf['mysql_host']):array('localhost',11211);
$mysql_user=isset($conf['mysql_user'])?$conf['mysql_user']:'madcore';
$mysql_pass=isset($conf['mysql_pass'])?$conf['mysql_pass']:'madcore';
$mysql_db=isset($conf['mysql_db'])?$conf['mysql_db']:'fmp';
define('__DB_MYSQL_HOST', $mysql_host);
define('__DB_MYSQL_PORT', $mysql_port);
define('__DB_MYSQL_USER', $mysql_user);
define('__DB_MYSQL_PASS', $mysql_pass);
define('__DB_MYSQL_DB',   $mysql_db);
unset($mysql_host,$mysql_port,$mysql_user,$mysql_pass,$mysql_db);
/* }}} */

/*{{{ facebook app设置*/
define('__FACEBOOK_APPID', $conf['app_id']);
define('__FACEBOOK_SECRET', $conf['app_secret']);
/*}}}*/
list($memcache_host,$memcache_port)=isset($conf['memcache_host'])?explode(':',$conf['memcache_host']):array('localhost',11211);
define('__MEMCACHE_HOST', $memcache_host);
define('__MEMCACHE_PORT', $memcache_port);
unset($memcache_host,$memcache_port);

list($redis_host,$redis_port)=isset($conf['redis_host'])?explode(':',$conf['redis_host']):array('localhost',6379);
define('__REDIS_HOST', $redis_host);
define('__REDIS_PORT', $redis_port);
unset($redis_host,$redis_port);

/* {{{ 物料地址 */
$material_url=isset($conf['material_url'])?$conf['material_url']:$_SERVER['HTTP_HOST'];
define('__MATERIAL_URL', "http://{$material_url}/fmpapi1.0/get/images");
unset($material_url);
/* }}} */

//webui的根目录，会在下面合并JS或CSS
define('__WEBUI_ROOT', '/usr/local/share/project/fmp/facebookprj_git/fmp/FMP_ui/');

/* {{{ mysql tables */
define('__TB_FMP_USER', 't_fmp_user');
/* }}} */
/* {{{ redis server
 */
define('__REDIS_HOST',      '127.0.0.1');
define('__REDIS_PORT',      '6379');
/* }}} */
/* {{{ services
 */
define('__SERVICE_JOIN',              'join');
define('__SERVICE_FMPUSER',           'fmpuser');
define('__SERVICE_LOGIN',             'login');
define('__SERVICE_FBLOGIN',           'fb_login');
define('__SERVICE_FBACCOUNT',         'fbaccount');
define('__SERVICE_SYNC',              'sync');
define('__SERVICE_CAMPAIGN',          'campaign');
define('__SERVICE_USER',              'user');
define('__SERVICE_FB_GRAPH',          'fb_graph');
define('__SERVICE_AJAX_UPLOAD',       'ajax_upload');
define('__SERVICE_IMAGES',            'images');
define('__SERVICE_SPCAMPAIGNS',       'sp_campaigns');
define('__SERVICE_PUBLISH',           'publish');
define('__SERVICE_GRAPH',             'graph');
define('__SERVICE_DISTRICT',          'district');
/* }}} */

/* {{{ services
 */
define('__PREFIX_JOIN',              'join');
define('__PREFIX_FMPUSER',           'fmpuser');
define('__PREFIX_USER',              'user');
define('__PREFIX_LOGIN',             'login');
define('__PREFIX_FBLOGIN',           'fb_login');
define('__PREFIX_FBACCOUNT',         'fbaccount');
define('__PREFIX_SYNC',              'sync');
define('__PREFIX_CAMPAIGN',          'campaign');
define('__PREFIX_CAMPAIGN_FB_GRAPH', 'fb_graph');
define('__PREFIX_AJAX_UPLOAD',       'ajax_upload');
define('__PREFIX_IMAGES',            'images');
define('__PREFIX_SPCAMPAIGNS',       'sp_campaigns');
define('__PREFIX_PUBLISH',           'publish');
define('__PREFIX_GRAPH',             'graph');
define('__PREFIX_DISTRICT',          'district');
/* }}} */

/* {{{ operations
 */
define('__OPERATION_CREATE', 'create');
define('__OPERATION_READ',   'get');
define('__OPERATION_UPDATE', 'update');
define('__OPERATION_DELETE', 'delete');
/* }}} */

/* {{{ http status define
 */
define('__HTTPSTATUS_OK',                    200);
define('__HTTPSTATUS_CREATED',               201);
define('__HTTPSTATUS_NO_CONTENT',            204);
define('__HTTPSTATUS_RESET_CONTENT',         205);
define('__HTTPSTATUS_BAD_REQUEST',           400);
define('__HTTPSTATUS_UNAUTHORIZED',          401);
define('__HTTPSTATUS_FORBIDDEN',             403);
define('__HTTPSTATUS_NOT_FOUND',             404);
define('__HTTPSTATUS_METHOD_NOT_ALLOWED',    405);
define('__HTTPSTATUS_METHOD_CONFILICT',      409);
define('__HTTPSTATUS_INTERNAL_SERVER_ERROR', 500);
/* }}} */

/* {{{ selector
 */
define('__SELECTOR_SINGLE',   '@self');
define('__SELECTOR_NEW',      '@new'); // 是否为新用户，就是没有任何广告活动的 
define('__SELECTOR_MASS',     '@all');
define('__SELECTOR_GROUP',    '@group');


define('__SELECTOR_STEP1',    '@step1'); // 广告第1步 
define('__SELECTOR_STEP2',    '@step2'); // 广告第2步
define('__SELECTOR_STEP3',    '@step3'); // 广告第3步
define('__SELECTOR_STEP4',    '@step4'); // 广告第4步
define('__SELECTOR_STEP5',    '@step5'); // 广告第5步
define('__SELECTOR_STEP6',    '@step6'); // 广告第6步

define('__SELECTOR_PRODUCT1', '@product1'); // 上传广告图片1
define('__SELECTOR_PRODUCT2', '@product2'); // 上传广告图片2
define('__SELECTOR_PRODUCT3', '@product3'); // 上传广告图片3
define('__SELECTOR_PRODUCT4', '@product4'); // 上传广告图片4
define('__SELECTOR_PRODUCT5', '@product5'); // 上传广告图片5


define('__SELECTOR_REPORT',  '@report');
define('__SELECTOR_ALLSITE', '@all'); // 全部站点 
define('__SELECTOR_SINGLE_SITE', '@self'); // 单个站点 
/* }}} */


/* {{{ syslog(fancility&level)
 */
if (!$conf['debug']) {
    define('__SYSLOG_FACILITY_API', 'LOG_LOCAL4');
} else {
    define('__SYSLOG_FACILITY_API', 'LOG_LOCAL5');
}
//level
define('__SYSLOG_LV_DEBUG',     'LOG_ERR');
/* }}} */

/* {{{ debug 
 */
if (!$conf['debug']) {
} else {
}
/* }}} */

// 选择的广告类型Objective
define('__FMP_ADTYPE_MUTIPRO',  1); //Multi-Product Ads(Website Clicks) 
define('__FMP_ADTYPE_NEWSFEED', 2); //News feed(Website Clicks) 
define('__FMP_ADTYPE_RIGHTCOL', 3); //Right-Hand Column(Website Clicks) 
$AD_TYPES=array(
    __FMP_ADTYPE_MUTIPRO=>"Multi-Product Ads(Website Clicks)",
    __FMP_ADTYPE_NEWSFEED=>"News feed(Website Clicks)",
    __FMP_ADTYPE_RIGHTCOL=>"Right-Hand Column(Website Clicks)"
);


// 性别
define('__FMP_GENDER_ALL',    0);
define('__FMP_GENDER_MALE',   1);
define('__FMP_GENDER_FEMALE', 2);


// 业务错误代码
define('__FMP_ERR_UPDATE_ADACCOUNT',  10553);
define('__FMP_ERR_UPDATE_FMP_FB_REL', 10554);
define('__FMP_ERR_SELECT_ADACCOUNT',  10555);
define('__FMP_ERR_SELECT_ACCTOK',     10556);
define('__FMP_ERR_UPDATE_TEMPLATE',   10557);
define('__FMP_ERR_UPDATE_MATERIAL',   10558);
define('__FMP_ERR_UPDATE_USER_MATERIAL',     10559);
define('__FMP_ERR_COMMIT_MATERIAL_UPLOAD',   10560);
define('__FMP_ERR_CREATE_MUL_TEMP_TBL',      10561);


// 日志类型
define('__FMP_LOGTYPE_ERROR',      0); //系统错误日志 
define('__FMP_LOGTYPE_OPERACTION', 1); //操作日志 

// graph url前缀
define('__FB_GRAPH', 'https://graph.facebook.com/v2.2');

// session
define('__SESSION_FMP_UID', 'fmp_uid');
define('__SESSION_FMP_USERNAME', 'username');
define('__SESSION_FB_UID', 'fb_uid');
define('__SESSION_CAMP_EDIT', 'camp_edit');

// buyingType的种类
define('__BYT_CPC', 'cpc');
define('__BYT_CPM', 'cpm');
define('__BYT_OCPM','ocpm');
define('__BYT_CPA', 'cpa');
$BYT_ARR=array(
    __BYT_CPC=>'CPC(Pay for Clicks)',
    __BYT_CPM=>'CPM(Pay for impressions)',
    __BYT_OCPM=>'OCPM(Optimize for clicks)',
    __BYT_CPA=>'CPA(Pay for Action)'
);

// objective的种类
define('__OBJT_MULTI_PRODUCT', 1);
define('__OBJT_NEWSFEED',      2);
define('__OBJT_RIGHTCOL',      3);
$OBJECTIVE_ARR=array(
    __OBJT_MULTI_PRODUCT=>'Multi-Product Ads(Website Clicks)',
    __OBJT_NEWSFEED=>'News feed(Website Clicks)',
    __OBJT_RIGHTCOL=>'Right-Hand Column(Website Clicks)'
);


// 任务的种类
define('__TASK_PUBLISH', 1);  // 发布

// 任务的状态
define('__TASKSTAT_READY', 1); // 等待执行
define('__TASKSTAT_EXECUTING', 2); // 执行中
define('__TASKSTAT_FAIL', 3); // 失败
define('__TASKSTAT_SUCCESS', 4); // 成功

// 用户能创建最大的模板数目
define('__FMP_MAX_USER_TMPL', 20);


// 默认用户常量

// 权限常量
// 1=无权限 2=读取 3=读取创建 4=读取修改 5=读取创建修改 6=读取创建修改删除


// 分页的常量
define('__PAGE_PREV_YES', '1'); //有上一页 
define('__PAGE_PREV_NO',  '0'); //没有上一页 
define('__PAGE_NEXT_YES', '1'); //有下一页 
define('__PAGE_NEXT_NO',  '0'); //没有下一页 
define('__EVENTCODE_DOWN', '997w'); //宕机事件代码
define('__EVENT_CLASS_CAUTION', '2'); //注意事件 
define('__EVENT_CLASS_WARNING', '3'); //严重事件 
define('__EVENT_CLASS_NORMAL',  '1'); //普通事件 
define('__SUFFIX_EVENT_CAUTION', 'c'); //注意事件的后缀 
define('__SUFFIX_EVENT_WARNING', 'w'); //严重事件的后缀 
define('__SUFFIX_EVENT_NORMAL',  'n'); //普通事件的后缀 
define('__NUM_EVENTCODE', 3); //事件代码的前三位代表一个监控事件项 
define('__EVENT_ACTIVE', 1); //事件激活状态
define('__EVENT_FIXED', 0); //事件已解决
define('__HAS_THIS_PAGE', 1); //分页时候标记存在此页,而不获取数据，仅仅作为一个计数器 

// cookie
if (!$conf['debug']) {
    define('__CO_MMSUID', '__CO_MMSUID');
    define('__CO_MMSUNAME', '__CO_MMSUNAME');
} else {
    define('__CO_MMSUID', '__CO_MMSUID_BETA');
    define('__CO_MMSUNAME', '__CO_MMSUNAME_BETA');
}


// message queue

// memcache的key
define('__KEY_MEMCACHE_USER_PRODUCTX',"a|%s|%s"); // 第二位为uid，第三位为多产品的次序，如产品一为1，产品二2,依此类推
?>
