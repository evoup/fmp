���˵����fmp��restful API

����������
yum groupinstall development
libxml2-devel
openssl-devel
libcurl-devel
libmcrypt-devel

php5.4
�������
'./configure'  '--prefix=/usr/local/php5_facebook' '--with-layout=GNU'
'--with-config-file-scan-dir=/usr/local/php5_facebook/etc/php' '--enable-dom'
'--enable-filter' '--enable-hash' '--enable-json' '--with-mcrypt'
'--with-curl' '--with-pcre-regex' '--enable-mbstring' '--enable-ctype'
'--enable-session' '--with-libxml-dir' '--enable-libxml' '--enable-simplexml'
'--enable-pdo' '--with-pdo-mysql=mysqlnd' '--with-mysqli=mysqlnd'
'--with-mysql=mysqlnd' '--enable-sysvsem' '--enable-pcntl' '--enable-dba'
'--enable-sysvmsg' '--enable-sysvshm' '--enable-sockets' '--enable-ftp'
'--with-zlib' '--with-pear=/usr/local/pear' '--enable-xml' '--with-openssl'
'--enable-fpm' '--enable-exif'

memcache��gd��չ����ʱ��so��ʽ��װ


���÷�����

inc/const.m��
�޸����³���Ϊʵ�����л����Ĳ���

�޸����³���Ϊʵ�����л�����syslog����
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
