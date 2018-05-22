//jquery扩展
$.extend({
    cookie : function(name, val) {
        if (!val) {
            var a = document.cookie.match(RegExp("(^|\s*)" + name + "=([^;]*)(;|$)"));
            return a ? decodeURIComponent(a[2]) : null;
        } else {
            //document.cookie = name + "=" + escape(val);
            document.cookie = name + "=" + encodeURI(val);
        }
    },
    removeCookie : function(name) {
        var expires = new Date();
        expires.setTime(expires.getTime() - 1);
        document.cookie = name + "=;expires=" + expires.toGMTString();
    }
});

// ------------------------------login.html-----------------------------------------

//有cookie信息直接登录
$(function () {

    var input_text = $.cookie("input_text");
    var login_type = $.cookie("login_type");
    if (input_text != null && login_type != null){
        $('#login_btn').attr("disabled","true");
        $.ajax({
            type : "POST",
            url : "/loginCheck",
            data : {input_text : input_text, login_type : login_type},
            success : function (data) {
                checkout(data);
            },
            error : function () {
                msg_bar("服务器瑟瑟发抖...Σ(°Д°;", "danger");
                $(this).removeAttr("disabled");
            }
        })
    }
})

//无cookie则输入信息登录
$(document).ready(function () {

    $('#login_btn').click(function () {
        $(this).attr("disabled","true");
        var input_text = $('#input_text').val();
        if (input_text.trim()==""){
            msg_bar("账户不能为空~", "danger");
            $(this).removeAttr("disabled");
        }
        else {
            var login_type = $('input[name="logintype"]:checked').val();
            var regex = /\d{17}/;
            if (login_type=="steamid" && !regex.test(input_text)) {
                msg_bar("Steamid64输入有误~", "danger");
                $(this).removeAttr("disabled");
            }
            else {
                $.ajax({
                    type : "POST",
                    url : "/loginCheck",
                    data : {input_text : input_text, login_type : login_type},
                    success : function (data) {
                        checkout(data, this);
                    },
                    error : function () {
                        msg_bar("服务器瑟瑟发抖...Σ(°Д°;", "danger");
                    }
                })
            }
        }
    })
})

function checkout(data, obj) {
    var code = data.resultCode;
    if (code == null){
        msg_bar("网络君消失了？Σ(°Д°;", "danger");
        $(obj).removeAttr("disabled");
    }
    else if (code == -1){
        msg_bar("输入错了？这个用户并没有户籍...Σヽ(ﾟД ﾟ; )ﾉ", "danger");
        $(obj).removeAttr("disabled");
    }
    else if (code == 1){
        msg_bar("用户steam资料私密~⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄", "danger");
        $(obj).removeAttr("disabled");
    }
    else if (code == 2){
        msg_bar("用户steam资料仅好友可见~⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄", "danger");
        $(obj).removeAttr("disabled");
    }
    else {
        //验证通过，处理cookie，提交表单。cookie登录默认用steamid（ajax验证成功后的返回结果）
        msg_bar("验证成功,拉取信息中~(≖ᴗ≖๑)", "success");
        if ($('#remember').prop('checked')){
            $.cookie("input_text",data.player_data);
            $.cookie("login_type","steamid");
        }
        $('#hidden_steamid').attr("value",data.player_data);
        $('#loginform').submit();
    }
}

function msg_bar(msg, alarmlevel) {
    //alarmlevel = danger / warning / info / success
    var msg = $('<div class="alert alert-'+alarmlevel+' alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'+msg+'</div>');
    $('#info_box').empty().append(msg);
}
