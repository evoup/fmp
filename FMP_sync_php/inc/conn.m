<?php
/*
  +----------------------------------------------------------------------+
  | Name:conn.php
  +----------------------------------------------------------------------+
  | Comment:简单的连接数据库调用
  +----------------------------------------------------------------------+
  | Author:Evoup     evoex@126.com                                                     
  +----------------------------------------------------------------------+
  | Create:
  +----------------------------------------------------------------------+
  | Last-Modified: 2015-03-04 11:14:48
  +----------------------------------------------------------------------+
 */
$link=null;
$link= mysqli_init();
$link->options(MYSQLI_OPT_CONNECT_TIMEOUT, 8);
$link->real_connect(__DB_MYSQL_HOST, __DB_MYSQL_USER, __DB_MYSQL_PASS, __DB_MYSQL_DB);
$link->query("SET NAMES utf8");
?>
