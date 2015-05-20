<?php
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
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
    $pool->submit(new PublishWork());
}
$pool->shutdown();

$pool->collect(function($work){
    return $work->isGarbage();
});
//var_dump($pool);
?>
