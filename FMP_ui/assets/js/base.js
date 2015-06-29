var baseConf = {
  "domain": "http://"+document.domain,
  "api_prefix": "http://"+document.domain+"/fmpapi1.0",
  "client_id": "798558363555177",
  "logged_gourl": "http://"+document.domain+"/settings",
  "redirect_url": "http://"+document.domain+"/redirect/",
  "fb_login_url": "https://www.facebook.com/v2.2/dialog/oauth?client_id=798558363555177&redirect_uri=http://"+document.domain+"/fb_login/index.html&scope=manage_pages,ads_management,read_insights,publish_actions",
  "product_multi_max": 5
};
$(document).ajaxComplete(function(event, xhr, settings) {
    /** 全局ajax完成触发检查超时登出
     * TODO如果是本方API调用才会检查返回状态
     */
    //console.log(settings.type)
    //console.log(settings.url)
    try {
        login_status=(xhr.responseJSON).status
        // 请求API出现400或者明文出现status为false，则为没有登录，跳转到未登录页
        if (xhr.status===400 || login_status==="false" || login_status===false) {
            //console.log("redirect")
            if (settings.url===baseConf.api_prefix+"/get/login/@self"){
              // 登录页不跳转
            } else if(settings.url===baseConf.api_prefix+"/get/fb_graph/@self") {
              // facebook的请求api接口出问题不跳转
            } else {
              // fmp的其他接口都跳转
                if (settings.type=="GET") {
                    window.location.href=baseConf.redirect_url+"not_login.html"
                }
            }
        }
    } catch(e) {}
});
