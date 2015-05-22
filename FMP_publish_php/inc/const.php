<?php
/*
  +----------------------------------------------------------------------+
  | Name:
  +----------------------------------------------------------------------+
  | Comment:
  +----------------------------------------------------------------------+
  | Author:Evoup     evoex@126.com                                                     
  +----------------------------------------------------------------------+
  | Create:
  +----------------------------------------------------------------------+
  | Last-Modified:
  +----------------------------------------------------------------------+
 */
/*{{{ redis设置*/
list($redis_host,$redis_port)=isset($conf['redis_host'])?explode(':',$conf['redis_host']):array('localhost',6379);
define('__REDIS_HOST', $redis_host);
define('__REDIS_PORT', $redis_port);
define('__REDIS_DB_INDEX', 1);
define('__REDIS_QUEUE_NAME', 'fmp_publish'); // 用来发送发布任务的redis队列的名字
unset($redis_host,$redis_port);
/*}}}*/

