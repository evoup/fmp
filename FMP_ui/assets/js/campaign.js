var cache = {
    '': $('.bbq-default')
};
$(document).ready(function() {
        loading()
        $.ajax({
            url: baseConf.api_prefix + "/get/login/@self",
            method: "GET",
            async: false,
            success: function(data) {
                if (data.status == "false") { // 没有登录退出
                    location.href=baseConf.redirect_url+"not_login.html"
                }
                var user = [{
                    UserName: data.username
                }]
                $("#headerTemplate").tmpl(user).appendTo('.header #user_profile_a');
            }
        })
        bind_logout_btn()
    })
    /** 根据url参数判断是第几步，下载模板和ajax数据动态构造编辑页面
     *  新的方法一次性下载全部模板
     *  gced:global campaign edit data
     */
var gced = {
    adaccounts: "",
    audience: "",
    spending: "",
    design: "",
    tpl_sidebar: "",
    tpl_step1: "",
    tpl_step2: "",
    tpl_step3: "",
    tpl_step4: "",
    tpl_step5: "",
    tpl_step6: ""
};
$.when(
    // 获取全部页面模板数据 
    $.get(baseConf.api_prefix + "/get/campaign/@step1", function(response) {
        gced.adaccounts = response.data
    }),
    $.get(baseConf.api_prefix + "/get/campaign/@step3", function(response) {
        gced.audience = response.data
    }),
    $.get(baseConf.api_prefix + "/get/campaign/@step4", function(response) {
        gced.spending = response.data
    }),
    $.get(baseConf.api_prefix + "/get/campaign/@step5", function(response) {
        gced.design = response.data
    }),
    $.get(baseConf.domain + "/templates/camp.sidebar.htm?t=20150406005706", function(data) {
        gced.tpl_sidebar = data
    }),
    $.get(baseConf.domain + "/templates/camp_step1.htm?t=20150406005706", function(data) {
        gced.tpl_step1 = data
    }),
    $.get(baseConf.domain + "/templates/camp_step2.htm?t=20150406005706", function(data) {
        gced.tpl_step2 = data
    }),
    $.get(baseConf.domain + "/templates/camp_step3.htm?t=20150406005706", function(data) {
        gced.tpl_step3 = data
    }),
    $.get(baseConf.domain + "/templates/camp_step4.htm?t=20150406005706", function(data) {
        gced.tpl_step4 = data
    }),
    $.get(baseConf.domain + "/templates/camp_step5.htm?t=20150406005706", function(data) {
        gced.tpl_step5 = data
    }),
    $.get(baseConf.domain + "/templates/camp_step6.htm?t=20150406005706", function(data) {
        gced.tpl_step6 = data
    })
).then(function() {
    $("#main-loading").remove()
    $.tmpl(gced.tpl_sidebar, null).appendTo('#sidebar-area');
    $(window).bind('hashchange', function(e) {
        var url = $.param.fragment();
        $('.bbq-content').children(':visible').hide();
        arr = url.split("/")
        stp = (arr.length < 2) ? 1 : arr[1]
        changeNav(stp)
        if (cache[url]) {
            // 已经缓存
            cache[url].show()
            switch (stp) {
              case("6"):
              // 每次进入需要计算切分
              window.publish_obj=new PublishProcess().init()
              break;
            }
        } else {
            // 首次加载
            switch (stp) {
                case ("1"):
                    cache[url] = $.tmpl(gced["tpl_step" + stp], gced.adaccounts).appendTo('#ad_edit_area');
                    break;
                case ("2"):
                    cache[url] = $.tmpl(gced["tpl_step" + stp], gced.adaccounts).appendTo('#ad_edit_area');
                    trackingProcess()
                    break;
                case ("3"):
                    cache[url] = $.tmpl(gced["tpl_step" + stp], gced.audience).appendTo('#ad_edit_area');
                    AudienceProcess()
                    break;
                case ("4"):
                    cache[url] = $.tmpl(gced["tpl_step" + stp], gced.spending).appendTo('#ad_edit_area');
                    spendingProcess()
                    break;
                case ("5"):
                    cache[url] = $.tmpl(gced["tpl_step" + stp], gced.design).appendTo('#ad_edit_area');
                    new DesignProcess().init()
                    break;
                    case("6"):
                    cache[url] = $.tmpl(gced["tpl_step" + stp], gced.publish).appendTo('#ad_edit_area');
                    window.publish_obj=new PublishProcess().init()
                    break;
                default:
                    cache[url] = $.tmpl(gced["tpl_step" + stp], gced.adaccounts).appendTo('#ad_edit_area');
                    break;
            }
        }
        $("#fmp_leftmenu").height($("#ad_edit_area").height() < 470 ? 470 : $("#ad_edit_area").height())
    })
    $(window).trigger('hashchange');
    if ((($.param.fragment()).split("/")).length < 2) {
        cache[""] = $.tmpl(gced["tpl_step1"], gced.adaccounts).appendTo('#ad_edit_area');
    }
});

function loading() {
    $("#main-loading").remove()
    $('#main-area').append("<div id=\"main-loading\"><i class=\"fa fa-cog fa-spin fa-3x fa-fw margin-bottom\"></i>loading...</div>")
}

function bind_logout_btn() {
    $("#logout_btn").bind("click", function() {
        $.ajax({
            url: baseConf.api_prefix + "/delete/login/@self",
            type: "GET",
            success: function(data) {
                if (data.status == "true") {
                    location.href = "http://" + document.domain;
                }
            }
        })
    })
}

// 修改左侧菜单样式
function changeNav(step) {
    for (i = 0; i < 7; i++) {
        $("#fmp_leftmenu > ul > li:nth-child(" + i + ")").attr('class', '')
    }
    $("#fmp_leftmenu > ul > li:nth-child(" + step + ")").attr('class', 'active')
    location.href = baseConf.domain + "/campaign/new/#step/" + step
}

/** 失去焦点后的验证
 * @param {int} current_step - 要验证哪一步
 */
window.blurValidate =function(current_step){
    var d = $("#form_camp_step" + current_step).serialize()
    var ret = false
    $.ajax({
        url: baseConf.api_prefix + "/update/campaign/@step" + current_step,
        type: "POST",
        data: d,
        async: false,
        success: function(data) {
            $("code").html("")
            // 成功展现下一页
            if (data.status == "true") {
                $("form").find('label>code').removeClass('error')
                ret = true
            } else {
                // 失败在code标签上显示错误
                err_msg = data.err_msg
                $("form").find('label>code').removeClass('error');
                for (i = 0; i < err_msg.length; i++) {
                    for (id in err_msg[i]) {
                        alert_dom_id = "label[for=" + id + "] code"
                        var emsg_str=err_msg[i][id]
                        // 首字大写
                        emsg_str=emsg_str.charAt(0).toUpperCase() + emsg_str.slice(1)
                        $(alert_dom_id).text(emsg_str).addClass("error")
                    }
                }
            }
        }
    })
    return ret
}

// 下一步
function goStep(step) {
    switch (step) {
        case (step):
            if (true==window.blurValidate(step-1)){
                // 移动滚动条到最上
                var body = $("html, body")
                body.animate({scrollTop:0}, '1000', 'swing', function() { 
                })
                location.href = baseConf.domain + "/campaign/new/#step/" + step
                changeNav(step)
            }
            break;
        default:
            changeNav(step)
            $("#ad_edit_area").html("")
            $.tmpl(gced["tpl_step" + step], null).appendTo('#ad_edit_area')
            break;
    }
}

function goStepPram(obj) {
    step = obj.step
    pram = obj.pram
    changeNav(step)
    $("#ad_edit_area").html("");
    $.tmpl(gced["tpl_step" + step], pram).appendTo('#ad_edit_area');
}

function routeCampUrlAct() {
    url = window.location.href
    n = url.indexOf("#step")
    if (n >= 0) { //执行指定的路由
        url_sub = url.substr(n)
        sp_arr = url_sub.split("#step/")
            //会调用第"+sp_arr[1]+"页的方法
        return parseInt(sp_arr[1])
    } else { //不然就是第一页
        return 1
    }
}

function trackingProcess() {
    function trackingInit() {
        //google analytics
        if ($("input[name='ga_enable']").is(":checked")) {
            for (i = 2; i < 5; i++) {
                $("#form_camp_step2 > div:nth-child(" + i + ")").css("display", "block")
            }
        } else {
            for (i = 2; i < 5; i++) {
                $("#form_camp_step2 > div:nth-child(" + i + ")").css("display", "none")
            }
        }
        //sigmad tracking code
        if ($("input[name='sm_enable']").is(":checked")) {
            $("#form_camp_step2 > div:nth-child(7)").css("display", "block")
        } else {
            $("#form_camp_step2 > div:nth-child(7)").css("display", "none")
        }
        //facebook convert pixel
        if ($("input[name='fb_enable']").is(":checked")) {
            $("#form_camp_step2 > div:nth-child(10)").css("display", "block")
        } else {
            $("#form_camp_step2 > div:nth-child(10)").css("display", "none")
        }
    }

    function bindTrackingCheckBox() {
        $("input[name='ga_enable'],input[name='sm_enable'],input[name='fb_enable']").on('click', function() {
            trackingInit()
        })
    }
    trackingInit()
    bindTrackingCheckBox()
}

function AudienceProcess() {
    function bindCheck() {
        $("#form_camp_step3 > div > div > div:nth-child(1) > div:nth-child(6)").css(
            "display",
            $("input[name='age_split']").is(":checked") ? "block" : "none"
        )
        $("#form_camp_step3 > div > div > div:nth-child(1) > div:nth-child(12)").css(
            "display",
            $("input[name='save_template']").is(":checked") ? "block" : "none"
        )
    }

    function audienceInit() {
        bindCheck()
        $("#sel_fmptemplate").change(function() {
            reloadAudienceByTmpl($("#sel_fmptemplate").val())
        })
    }

    function bindLocAutoComplete() {
        // 预先载入一个假的自动完成
        savedCountries = [];
        $("#fmplocation_autocomplete").wrap("<div class=\"ui-autocomplete-multiselect ui-state-default ui-widget\" id=\"fmp_loc_autocomplete_dummy\"></div>")
        $("#fmp_loc_autocomplete_dummy").bind('click', function() {
            $("#fmplocation_autocomplete").click()
        })
        $.each(gced.audience.fmplocation, function(k, v) {
            (v != null) && savedCountries.push(v)
        })
        before_savedCounties = "";
        $.each(savedCountries, function(k, v) {
            before_savedCounties += "<div class=\"ui-autocomplete-multiselect-item\">" + v + "<span class=\"ui-icon ui-icon-close\"></span></div>"
        })
        $("#fmplocation_autocomplete").before(before_savedCounties)
        $(".ui-icon,.ui-icon-close").bind('click', function() {
            $(this).parent().remove()
            del_country = $(this).parent().text()
            savedCountries = $.grep(savedCountries, function(n, i) {
                return n != del_country
            })
            $("input[name=fmplocation]").val(($.unique(savedCountries)).join('|'))
        })
        var contrys = []
        $.each(fmp_loc_dic, function(k, v) {
            contrys.push(v)
        })
        $("#fmplocation_autocomplete").on('click', function() {
            if ($("#fmplocation_autocomplete").parent().attr("id") == "fmp_loc_autocomplete_dummy") {
                $(".ui-autocomplete-multiselect-item").remove()
                $("#fmplocation_autocomplete").unwrap()
            }
            $("#fmplocation_autocomplete").autocomplete({
                source: contrys,
                multiselect: true
            });
            $("#fmplocation_autocomplete").parent().attr("id", "fmp_loc_autocomplete")
            before_savedCounties = "";
            $.each(savedCountries, function(k, v) {
                before_savedCounties += "<div class=\"ui-autocomplete-multiselect-item\">" + v + "<span class=\"ui-icon ui-icon-close\"></span></div>"
            })
            $("#fmplocation_autocomplete").before(before_savedCounties)
            savedCountries = []
            $(".ui-icon,.ui-icon-close").bind('click', function() {
                $(this).parent().remove()
            })
            $("#fmplocation_autocomplete").focus()
            $("#form_camp_step3 input").on('blur', function(event) {
                arr = [];
                event.stopPropagation();
                $.each($(".ui-autocomplete-multiselect-item"), function(k, v) {
                    arr.push($(this).text())
                })
                $("input[name=fmplocation]").val(($.unique(arr)).join('|'))
            })
        })
    }
    audienceInit()
    bindLocAutoComplete()
    $("input[name='age_split'],input[name='save_template']").on('click', function() {
        bindCheck()
    })
    $("#fmp_loc_autocomplete_dummy").trigger("click")
    $("#fmplocation_autocomplete").trigger("blur")
    $("#fmp_loc_autocomplete").removeClass("ui-state-active")
    generateAtLeast()
    generateDetail(true)
    $("#form_camp_step3 select").on('change', function() {
        setTimeout(function(){
            window.blurValidate(3);
            generateAtLeast();
            generateDetail(true);
        },500)
    })
    $("#form_camp_step3 input[type='checkbox']").on('click', function() {
        setTimeout(function(){
            window.blurValidate(3);
            generateAtLeast();
            generateDetail(true);
        },500)
    })
    $("#fmp_loc_autocomplete").on('DOMSubtreeModified', function() {
        setTimeout(function(){
            window.blurValidate(3);
            generateAtLeast();
            generateDetail(true);
        },500)
    })
    becameSplitter($('#mainSplitter_step3'),660)
}

// 根据指定的template重载Audience
function reloadAudienceByTmpl(template_id) {
    $("#ad_edit_area").find("h2:contains('Audience')").remove()
    $("#form_camp_step3").remove()
    loading()
    $.get(baseConf.api_prefix + "/get/campaign/@step3?template_id=" + template_id, function(data) {
        $("#main-loading").remove()
        $("#ad_edit_area").css("display", "none")
        gced.audience = data.data
        cache["step/3"] = $.tmpl(gced["tpl_step3"], gced.audience).appendTo('#ad_edit_area');
        AudienceProcess()
        $("#ad_edit_area").css("display", "block")
    })
}



function spendingProcess() {
    becameSplitter($('#mainSplitter_step4'),400)
    generateAtLeast()
    generateDetail()
}

/**
 * Design的类
 */
var DesignProcess = function() {}
DesignProcess.prototype = {
    // 可以被加入的tab索引
    tabIndexs: _.range(1,baseConf.product_multi_max+1),
    // 最大的tab数目
    maxTabNums: baseConf.product_multi_max,
    // 初始化生成splitter和右侧明细,创建tabs，动态填充tabs
    init: function() {
        window.becameSplitter($('#mainSplitter_step5'),720)
        window.generateAtLeast()
        window.generateDetail()
        this.loadDropDown()
        this.bindDropDown()
        this.createNewTabs($('#multi_product_jqxtabs'))
        this.bindTabsCloseBtn($('#multi_product_jqxtabs'))
        this.bindDbClkChTabTitle($('#multi_product_jqxtabs'))
        return this
    },
    // 预先载入下拉page的选定项
    loadDropDown: function() {
        var content
        var $target=$('#form_camp_step5 .dropdown-menu li')
        var pages=gced.design.pages
        //console.log(pages)
        if (pages==null || pages.length==0) {
            return
        }
        $.each(pages,function(k,v){
            if(v.selected=='true') {
                content='<span class="pic"><img src="'+v.imgbase64+'"></span>'+v.name+'<input type="hidden" value="'+v.id+'">'
                $target
                    .closest('.btn-group')
                    .find('[data-bind="label"]').html(content)
                $("input[name='selected_page']").val(v.id)
            }
        })
    },
    // 绑定下拉page的选择变换
    bindDropDown: function() {
        $(document.body).on('click', '#form_camp_step5 .dropdown-menu li', function(event) {
            var $target = $(event.currentTarget)
            var $content = $($target.html())
            //console.log($content)
            var my_selected_page_id=$target.find("input[type='hidden']").val()
            $target
                .closest('.btn-group')
                .find('[data-bind="label"]').html($content.html())
                .end()
                .children('.dropdown-toggle').dropdown('toggle')
            //传递到实际代表page的id的控件上
            if ($($("span:nth-child(1)")[0]).text()=="Please select a Page") {
                $("input[name='selected_page']").val("")
            } else {
                $("input[name='selected_page']").val(my_selected_page_id)
            }
            return false
        })
        return this
    },
    /** 创建tabs
     * @param {obj} obj_tabs - 创建的tabs的id
     */
    createNewTabs: function(obj_tabs) {
        //var index = 3;
        var $this=this
        obj_tabs
            .append("<ul id=\"unorderedList\"></ul>")
            .find('#unorderedList').append("<li canselect='false' hasclosebutton='false' name='li_add_new_product'>Add new Product</li>")
            .after("<div></div>")
            .parent()
            .jqxTabs({
                height: 470,
                width: '100%',
                showCloseButtons: true,
                scrollPosition: 'both'
            })
            .on('tabclick', function(event) {
                
                if (event.args.item == $('#unorderedList').find('li').length - 1) {
                    var length = $('#unorderedList').find('li').length
                    //obj_tabs.jqxTabs('addAt', event.args.item, 'Product ' + index, 'Sample content number: ' +
                    //    index)
                    length<=$this.maxTabNums && $this.createTabContent(obj_tabs,length-1)
                    //index++
                }
            })
        var product_mul=gced.design.product_multi
        var $this=this
        if (product_mul==null || product_mul.length==0){
            // 默认填充三枚tab pane
            $this.createTabContent(obj_tabs, 0)
            $this.createTabContent(obj_tabs, 1)
            $this.createTabContent(obj_tabs, 2)
            obj_tabs.jqxTabs('select', 0)
        } else {
            var ci=0;
            $.each(product_mul,function(k,v){
                console.log(v)
                $this.createTabContent(obj_tabs, ci++, v.product_name, v.product_link, v.product_desc, v.product_pic, v.product_pic_url)
            })
            obj_tabs.jqxTabs('select', 0)
        }
        return this
    },
    /** 创建tabs的内容
     * @param {obj} obj - jqxtabs的id(必须提供)
     * @param {int} idx - 从idx位置开始创建新的tab内容(必须提供)
     * @param {string} pd_name 产品名字(可选参数，如提供以下三个都需要提供)
     * @param {string} pd_link 产品链接(可选参数)
     * @param {string} pd_desc 产品描述(可选参数)
     * @param {string} pd_hash 产品图片的hash(可选参数)
     */
    createTabContent: function() {
        var obj=arguments[0]
        var idx=arguments[1]
        console.log(arguments)
        // 剩下还可以创建的索引
        //alert("本次创建前剩下还可以创建的索引:"+this.tabIndexs)
        // 用剩余最小的id作为默认产品名字
        var $this=this
        var newTabId=Math.min.apply(Math,this.tabIndexs)
        this.tabIndexs = $.grep(this.tabIndexs, function(value) {
            return value != newTabId;
        })
        var pd_name=(arguments[2]!=null) ? arguments[2] : 'Product ' + newTabId
        var pd_link=(arguments[3]!=null) ? arguments[3] : ''
        var pd_desc=(arguments[4]!=null) ? arguments[4] : ''
        var pd_hash=(arguments[5]!=null) ? arguments[5] : ''
        var pd_pic_url=(arguments[6]!=null) ? arguments[6] : ''
        var hasData=(arguments.length>2) ? true : false
        //alert("本次创建完剩下还可以创建的索引:"+this.tabIndexs)

        // pane的主要部分
        var pane_content = '<div class="form-group">'
        pane_content += '<input type="hidden" id="hid_product_name'+newTabId+'" name="productName['+newTabId+']" value="product '+newTabId+'"><label for="productLink['+newTabId+']">Product Link<code></code></label><input type="text" class="form-control" id="productLink'+newTabId+'" name="productLink['+newTabId+']" placeholder="Enter name" value="'+pd_link+'"></div>'
        pane_content += '<div class="form-group"><label for="productDescription['+newTabId+']">Product Description<code></code></label><input type="text" class="form-control" id="productDescription'+newTabId+'" name="productDescription['+newTabId+']" placeholder="Enter product description" value="'+pd_desc+'"></div>'
        pane_content += '<div class="form-group" id="fg'+newTabId+'"><label for="productHash['+newTabId+']">Picture<code></code></label><form><input id="file_upload'+newTabId+'" name="file_upload['+newTabId+']" type="file" multiple="true" style="display:none"><div id="btn-group'+newTabId+'"><button class="btn btn-default btn-sm btn-upload">Upload new images <span class="glyphicon glyphicon-plus"></span></button>or<button class="btn btn-default btn-sm btn-upload">Select from your galley <span class="glyphicon glyphicon-plus"></span></button></div></form></div>'
        pane_content += '</div>'

        obj.jqxTabs('addAt', idx, pd_name, pane_content)

        // 上传控件初始化
        $('#file_upload'+newTabId).uploadify({
            'multi': false,
            'queueSizeLimit': 1,
            'fileTypeExts': '*.gif; *.jpg; *.jpeg; *.png',
            'buttonImage': '../../assets/img/transparent.gif',
            'buttonClass': 'uploadify_no_display',
            'wmode': 'transparent',
            'width': $("#btn-group"+newTabId+" > button:nth-child(1)").width() + 20,
            'formData': {},
            'swf': '../../assets/widgets/uploadify/uploadify.swf',
            'uploader': baseConf.api_prefix + "/create/ajax_upload/@product1",
            'onUploadSuccess': function(file, data, response) {
                var responseObj = JSON.parse(data);
                if (responseObj.err_msg) {
                    $("#" + file.id).find('.data').css('color', 'red').html(' - ' + responseObj.err_msg);
                    return;
                } else {
                    // 绑定当前上传图片的行为，展示和关闭
                    $('#adimage'+newTabId).remove()
                    $('#multi_product_jqxtabs #fg'+newTabId).after('<div id="adimage'+newTabId+'" class="modal-header" style="width:200px;border:none;"></div>')
                    $('#adimage'+newTabId).empty().append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="../../assets/img/modal_close.png"/></button><div class="img-thumbnail imgPreview"><img src="' + responseObj.url + '"><input type="hidden" name="productHash['+newTabId+']" value="'+responseObj.hash+'"></div>').css('display', 'none').fadeIn();
                    $("#adimage"+newTabId+" > button").on('click', function() {
                        $("#adimage"+newTabId).empty()
                    });
                    setTimeout(function() {
                        var prev_offset=$("#adimage"+newTabId).offset()
                        $("#adimage"+newTabId).attr('position','absolute')
                        $("#adimage"+newTabId).offset({
                            top: prev_offset.top-20,
                            left: prev_offset.left
                        })
                        $("#adimage"+newTabId).animate({ "top": "-=30px" }, "slow" );
                    },3500)
                }
            }
        });

        // 对齐上传按钮到swf上传控件的位置
        $("#btn-group"+newTabId).offset({
                top: $("#file_upload"+newTabId).offset().top,
                left: $("#file_upload"+newTabId).offset().left
            })

        // 绑定物料库的操作
        $(function() {
            var $modal = $('#galley_modal');
            $('#btn-group'+newTabId+' > button:nth-child(2)').on('click', function() {
                $('body').modalmanager('loading');
                setTimeout(function() {
                    $ul = $("#galley_modal > div.modal-body > div.files > ul");
                    $ul.empty();
                    $.get(baseConf.api_prefix + "/get/ajax_upload/@all", function(data) {
                        $.each(data, function(k, v) {
                            v != null && $ul.append('<li><a href=\"' + v.url + '\" class=\"imgPreview\"><img src=\"' +
                                v.url + '\"></a></li>')
                        });
                        $modal.modal();

                        $.each(
                            $("#galley_modal ul").find('.imgPreview'),
                            function(k, v) {
                                $(v).bind('click', function() {
                                    $('#adimage'+newTabId).remove()
                                    $('#multi_product_jqxtabs #fg'+newTabId).after('<div id="adimage'+newTabId+'" class="modal-header" style="width:200px;border:none;"></div>')
                                    $("#adimage"+newTabId).attr('position','absolute')
                                    var prev_ofs=$("#adimage"+newTabId).offset()
                                    var the_product_url=$(this).attr('href')
                                    var the_product_hash=the_product_url.substr(the_product_url.lastIndexOf("/") + 1).split('.')[0]
                                    // 选择完毕呈现在下方的展示区域
                                    $("#adimage"+newTabId)
                                        .offset({
                                            top: prev_ofs.top-20,
                                            left: prev_ofs.left
                                        })
                                        .animate({ "top": "-=30px" }, "slow" )
                                        .empty()
                                        .append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="../../assets/img/modal_close.png"/></button><div class="img-thumbnail imgPreview"><img src="' + the_product_url + '"><input type="hidden" name="productHash['+newTabId+']" value="'+the_product_hash+'"></div>').css('display', 'none').fadeIn();

                                    $("#adimage"+newTabId+" > button").on('click', function() {
                                        $("#adimage"+newTabId).empty()
                                    });
                                    $('#galley_modal').modal('hide');
                                    return false;
                                })
                            });
                    });
                }, 1000);
                return false;
            });
            $this.bindDbClkChTabTitle($('#multi_product_jqxtabs'))
        })

        // 呈现已经选择的展示区域的图片
        $(function() {
            if (true==hasData) {
                console.log("load uploaded img")
                $('#adimage'+newTabId).remove()
                $('#multi_product_jqxtabs #fg'+newTabId).after('<div id="adimage'+newTabId+'" class="modal-header" style="width:200px;border:none;"></div>')
                  $("#adimage"+newTabId).attr('position','absolute')
                  prev_ofs=$("#adimage"+newTabId).offset()
                  console.log(newTabId)
                  the_product_url=pd_pic_url
                  $("#adimage"+newTabId)
                      .offset({
                          top: prev_ofs.top-20,
                          left: prev_ofs.left
                      })
                      .animate({ "top": "-=30px" }, "slow" )
                      .empty()
                      .append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><img src="../../assets/img/modal_close.png"/></button><div class="img-thumbnail imgPreview"><img src="' + the_product_url + '"><input type="hidden" name="productHash['+newTabId+']" value="'+pd_hash+'"></div>').css('display', 'none').fadeIn();

                  $("#adimage"+newTabId+" > button").on('click', function() {
                      $("#adimage"+newTabId).empty()
                  });
            }
        })
        //
    },

    /** 更新剩余可用的tab索引
     * @param {obj} obj - jqxtabs的id
     */
    updateTabIndexs: function(obj) {
        var $this=this
        //alert("本次删除前还剩下的索引"+$this.tabIndexs)
        var alreadyHasId=[]
        var oldInx=$this.tabIndexs=_.range(1,baseConf.product_multi_max+1)

        try{
            for(var i=0;i<$(obj).jqxTabs('length');i++){  
                var inp=$(obj)
                    .jqxTabs('getContentAt',i)
                    .find(">.form-group>input:first")
                alreadyHasId.push(parseInt(inp.attr("id").match(/\d+/)[0]))
            }
        } catch(e){}
        //alert("本次预先重置的索引"+$this.tabIndexs)
        //alert("本次已经占用的索引"+alreadyHasId)
        $.each(alreadyHasId,function(k,v){
            //从总的索引中排除这些索引
            oldInx.splice( $.inArray(v,oldInx) ,1 );
        })
        //alert("排除这些后剩下的索引"+oldInx)
    },

   /** 绑定jqxtabs上每个小tab的关闭按钮
    * @param {obj} obj - jqxtabs的id
    */
    bindTabsCloseBtn: function(obj) {
        var $this=this
         obj.on('removed', function (event) {
             $this.updateTabIndexs(obj)
         })
    },

    /** 绑定tab标题双击修改产品名字的功能
     * @param {obj} obj - jqxtabs的id
     */
     bindDbClkChTabTitle: function(obj) {
        var me = this;
        // disable keyboard navigation. The keyboard navigation handles the arrow keys and selects the next or previous tab depending on the // pressed arrow key.
        obj.jqxTabs({ keyboardNavigation: false });
        // find the tab we will show an editor for.
        var tabs = obj.find("li");
        // bind to the double-click event.
        tabs.bind('dblclick', function (event) {
            // find the double-clicked tab title.
            var target = event.target;
            var tab = target.tagName == "LI" ? $(target) : $(target).parents('li:first');
            // create a text input or get the already created.
            var input = me.input || $("<div style='position: absolute; z-index: 9999; background: white'><input/></div>");
            var textinput = input.find('input');
            if (!me.input) {
                // add the text input to the document's body.
                $(document.body).append(input);
                // update the tab's text on blur.
                textinput.bind('blur', function () {
                    // 最多35个字符
                    var newtext = $.trim(textinput.val()).substr(0,35);
                    if (newtext==""){
                        newtext=me.edittab.text()
                    }

                    me.edittab.text(newtext);
                    input.css('display', 'none');
                    obj.jqxTabs('_performHeaderLayout');

                    // 把值传递给实际控件
                    var sele_itm=obj.jqxTabs('selectedItem')
                    var current_pane=obj.jqxTabs('getContentAt', sele_itm)
                    var $product_name=current_pane.find("input:first")            
                    $product_name.val(newtext)
                    
                    //需要再绑定一次否则关闭按钮会消失
                    me.bindDbClkChTabTitle(obj);
                });
            }
            me.input = input;
            // show the input.
            input.css('display', 'block');
            // position the input over the tab and set its size.
            var taboffset = tab.offset();
            me.input.css({ left: taboffset.left, top: taboffset.top });
            me.input.height(tab.outerHeight());
            me.input.width(tab.outerWidth());
            var sizeoffset = 6;
            textinput.width(me.input.width() - sizeoffset);
            textinput.height(me.input.height() - sizeoffset);
            // set the initial text of the input.
            textinput.val(tab.text());
            me.edittab = tab;
        });
        // 使add new product按钮不能被双击修改
        $("#unorderedList > li[name='li_add_new_product']").unbind("dblclick");
    }
}

/**
 * Publish的类
 */
var PublishProcess = function() {}
// 静态方法提交发布信息
PublishProcess.commit=function() {
    var t=_.first(window.publish_obj.total_rows)
    var ret=false;
    // 提交的待发布数据准备完毕
    $.ajax({
        url: baseConf.api_prefix + "/get/campaign/@step6?pagesize="+t,
        method: "GET",
        async: false,
        success: function(data) {
            console.log(data)
            var i=0;
            var sri=$("#jqxgrid").jqxGrid('selectedrowindexes');
            var commitArr=[];
            $.each(data[0].Rows,function(k,v){
                if (-1!=_.indexOf(sri,i)) {
                    commitArr.push(v)
                }
                i++;
            })
            console.log(commitArr)
            if (commitArr.length==0) {
                alert("you check nothing!")
                ret=false
            } else {
                $.ajax({
                    url: baseConf.api_prefix + "/get/publish/@self",
                    method: "POST",
                    data: {"commit_data":commitArr},
                    async: false,
                    success: function(data){
                        console.log(data)
                    }
                })
                ret=true
            }
        }
    })
    return ret
}
// 实例化的方法
PublishProcess.prototype = {
    total_rows: 0,
    // 选择了的行
    selected_rows: [],
    // 初始化splitter和右侧明细，创建确定table，并生成确认的
    init: function() {
        window.becameSplitter($('#mainSplitter_step6'),700)
        window.generateAtLeast()
        window.generateDetail()
        this.createVerifyTbl($('#jqxgrid'))
        return this
    },
    /** 创建确认表格
     *  @param {obj} obj - 创建的jqxgrid的对象名 
     */
    createVerifyTbl: function(obj) {
        $this=this;
        $(document).ready(function () {
            var theme = 'classic';
            var source =
            {
                datatype: "json",
                datafields: [
                    { name: 'campaign_name', type: 'string' },
                    { name: 'delivery', type: 'int' },
                    { name: 'ad_set_name', type: 'string' },
                    { name: 'start', type: 'string' },
                    { name: 'end', type: 'string' },
                    { name: 'objective', type: 'string' },
                    { name: 'location', type: 'string' },
                    { name: 'age_from', type: 'int' },
                    { name: 'age_to', type: 'int' },
                    { name: 'gender', type: 'string' }
                ],
                cache: false,
                url: baseConf.api_prefix+'/get/campaign/@step6',
                root: 'Rows',
                beforeprocessing: function (data) {
                    $this.total_rows = source.totalrecords = data[0].TotalRows;
                }
            };
            var dataadapter = new $.jqx.dataAdapter(source);
            obj.jqxGrid(
            {
                width: "100%",
                source: dataadapter,
                theme: theme,
                autoheight: true,
                pageable: true,
                virtualmode: true,
                sortable: true,
                altrows: true,
                enabletooltips: true,
                editable: true,
                selectionmode: 'checkbox',
                rendergridrows: function (params) {
                    $.each(params.data,function(k,v){
                        // 收集当前打勾的行等待发布时传递
                        $this.selected_rows.push(v)
                    })
                    console.log($this.selected_rows)
                    return params.data;
                },
                columns:
                [
                    { text: 'Campaign Name', datafield: 'campaign_name', width: 150 },
                    { text: 'Delivery', datafield: 'delivery', width: 70 },
                    { text: 'Ad set Name', datafield: 'ad_set_name', width: 120 },
                    { text: 'Start', datafield: 'start', width: 160 },
                    { text: 'End', datafield: 'end', width: 160 },
                    { text: 'Objective', datafield: 'objective', width: 120 },
                    { text: 'Location', datafield: 'location', width: 140 },
                    { text: 'Age From', datafield: 'age_from', width: 80 },
                    { text: 'Age To', datafield: 'age_to', width: 80 },
                    { text: 'Gender', datafield: 'gender', width: 80 }
                ]
            });
            // 绑定发布按钮的点击事件 
            $("#form_camp_step6 #jsProceed").on("click",function(){
                $(this).html("Publishing Campaign...")
                if (false==PublishProcess.commit()){
                    $(this).html("Campaign Published Failed!")
                } else {
                    $(this).html("Campaign Successlly Published!").prop("disabled",true)
                }
            })
        });
    }
}

/**
 * 把内部具有splitter-panel类div的div变为jqxsplitter
 * @param {obj} obj - 外层div的对象名
 * @param {int} hgt - 高度
 */
window.becameSplitter = function(obj,hgt) {
    obj
        .jqxSplitter({
            width: 774,
            height: hgt,
            panels: [{
                size: 550,
                collapsible: false
            }]
        })
        .find(".form-group").css("padding-right", "20px")
    $(".jqx-widget-content").css("background", "transparent").css("border", "none")
    $("#multi_product_jqxtabs").css("background", "").css("border", "")
    $(".panel,.panel-default").css("margin-left", "20px")
}

// 生成至少有多少
window.generateAtLeast = function() {
    $.ajax({
        url: baseConf.api_prefix + "/get/sp_campaigns/@all",
        method: "GET",
        success: function(data) {
          $("div[id$='_panel_atleast']").find(".panel-body").html(data.sp_camps.length+" Ads")
        }
    })
}

// 生成右侧明细
window.generateDetail = function() {
    var age_min,age_max,gender,cccontent,forceGen
    var countryArr = []
    var $cc=$(cache["step/3"])
    var hasCache=$cc.length==0?false:true // 从第三步拿参数 
    // 如果没有cache用全局设置
    if (arguments.length==1) {
        forceGen=arguments[0]==true?true:false;
    }
    if (!hasCache || forceGen) {
        $.ajaxSetup({async:false})
        $.get(baseConf.api_prefix + "/get/campaign/@step3", function(response) {
            gced.audience = response.data
        })
        $.ajaxSetup({async:true})
        // 从全局广告活动编辑数据取参数
        $.each(gced.audience.age_from,function(k,v){
            if (_.propertyOf(v)("selected")=="selected") {
              age_min=v.name
            }
        })
        $.each(gced.audience.age_to,function(k,v){
            if (_.propertyOf(v)("selected")=="selected") {
              age_max=v.name
            }
        })
        $.each(gced.audience.gender,function(k,v){
            if (_.propertyOf(v)("selected")==1) {
                gender=k==0?null:k
            }
        })
        $.each(gced.audience.fmplocation,function(k,v){
            $.each(fmp_loc_dic, function(country_code, country) {
                v == country && countryArr.push(country_code)
            })
        })
        reachObj = {
            age_min: age_min,
            age_max: age_max,
            geo_locations: {
                countries: countryArr
            }
        }
        if (gender != null) {
            reachObj['genders'] = gender
        }
        reachestimateTs = JSON.stringify(reachObj)
        targetingsentencelinesTs = JSON.stringify({
            "interests": [],
            "user_adclusters": [],
            "custom_audiences": [],
            "excluded_custom_audiences": [],
            "locales": [],
            "connections": [],
            "friends_of_connections": [],
            "excluded_connections": [],
            "wireless_carrier": [],
            "education_schools": [],
            "education_majors": [],
            "college_years": [],
            "work_employers": [],
            "work_positions": [],
            "behaviors": [],
            "age_min": age_min,
            "age_max": age_max,
            "geo_locations": {
                "zips": [],
                "cities": [],
                "regions": [],
                "countries": countryArr
            },
            "flexible_spec": {}
        })
        $.ajax({
            url: baseConf.api_prefix + "/get/fb_graph/@self",
            method: "POST",
            data: {
                batch: [{
                    method: "GET",
                    url: "/act_" + gced.audience.billing_account + "/reachestimate?targeting_spec=" + reachestimateTs
                }, {
                    method: "GET",
                    url: "/act_" + gced.audience.billing_account + "/targetingsentencelines?targeting_spec=" + targetingsentencelinesTs
                }]
            },
            success: function(data) {
                if (data.status == "false") { // 没有登录退出
                    location.href=baseConf.redirect_url+"not_login.html"
                }
                item = ""
                estimate_audience = "n/a"
                if (data[0][0] == "200") {
                    estimate_audience = $.parseJSON(data[0][1]).users
                }
                if (data[1][0] == "200") {
                    arr = $.parseJSON(data[1][1]).targetingsentencelines
                    $.each(arr, function(k, v) {
                        item += "<li>" + v.content + v.children + "</li>"
                    })
                }
                detailContent = "<h4 class=\"tit\">Audience</h4><strong class=\"emphasize\">" + estimate_audience + "</strong>people<div class=\"summary\"><strong class=\"subtit\">Your ad targets people</strong><ul>" + item + "</ul></div>";
                $("#step3_panel_detail > div.panel-body").html(detailContent)
            }
        })
    } else {
        // 有cache用cache的
        var $ccform=$($cc[2])
        age_min = $ccform.find("#age_from").val() + ""
        age_max = $ccform.find("#age_to").val() + ""
        gender = $ccform.find("#gender").val() + "" == "0" ? null : [$ccform.find("#gender").val() + ""]
        $(".ui-autocomplete-multiselect-item").each(function(k, v) {
            $.each(fmp_loc_dic, function(country_code, country) {
                v.innerText == country && countryArr.push(country_code)
            })
        })
        try{
          // 找到已经cache过的明细
          cccontent=$(cache["step/3"][2])
            .find(".splitter-panel,col-md-4")
            .find("#step3_panel_detail .panel-body")
            .html()
        } catch(e) {}
        $("#step3_panel_detail .panel-body").html(cccontent)
    }

}
