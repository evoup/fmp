<?php
/*
  +----------------------------------------------------------------------+
  | Name: publish.php
  +----------------------------------------------------------------------+
  | Comment: 发布活动
  +----------------------------------------------------------------------+
  | Author:Evoup     evoex@126.com                                                     
  +----------------------------------------------------------------------+
  | Create: 2015-05-22 16:38:34
  +----------------------------------------------------------------------+
  | Last-Modified: 2015-05-22 16:38:41
  +----------------------------------------------------------------------+
*/
ini_set('default_socket_timeout', 120);
define(__APP_ROOT, dirname(__FILE__).'/');
$conf = parse_ini_file(__APP_ROOT.'conf/publish.conf'); //配置文件
include(__APP_ROOT.'inc/const.php');
require __APP_ROOT.'GPLlib/predis-1.0/autoload.php';

$queue_server = array(
    'host'     => __REDIS_HOST,
    'port'     => __REDIS_PORT,
    'database' => __REDIS_DB_INDEX
);
print_r($queue_server);
die;
class PublishWorker extends Worker {

    public function __construct(SafeLog $logger) {
        $this->logger = $logger;
    }
    
    protected $logger;  
}

/* the collectable class implements machinery for Pool::collect */
class PublishWork extends Collectable {
    public function run() {
        $this->worker
            ->logger
            ->log("%s executing in Thread #%lu",
                  __CLASS__, $this->worker->getThreadId());
        echo time();
        $this->setGarbage();
    }
}

class SafeLog extends Stackable {
    
    protected function log($message, $args = []) {
        $args = func_get_args();    
        
        if (($message = array_shift($args))) {
            echo vsprintf(
                "{$message}\n", $args);
        }
    }
}

$pool = new Pool(8, 'PublishWorker', [new SafeLog()]);

while(1) {
    sleep(5);
    // 获取从前端保存到redis队列中的数据，往线程池提交work
    print_r($pool);
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
    //$pool->submit(new PublishWork());
}
$pool->shutdown();

$pool->collect(function($work){
    return $work->isGarbage();
});
//var_dump($pool);
?>
