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

$(document).ready(function () {

    $('#login_btn').click(function () {
        var input_text = $('#input_text').val();
        if (input_text.trim()==""){
            msg_bar("账户不能为空~", "danger");
        }
        else {
            var type = $('input[name="logintype"]:checked').val();
            if (type == "steamid"){
                var regex = /\d{17}/;
                if (regex.test(input_text)){
                    $.ajax({
                        type : "POST",
                        url : "/loginBySID",
                        data : {steamid : input_text, type : type},
                        success : function (data) {
                            checkout(data);
                        },
                        error : function () {
                            msg_bar("服务器君瑟瑟发抖...Σ(°Д°;", "danger");
                        }
                    })
                }
                else{
                    msg_bar("Steamid64输入有误~", "danger");
                }
            }
            else {
                //type == vanityurl
            }
        }
    })


    function checkout(data) {
        var code = data.resultCode;
        if (code == null){
            msg_bar("网络君消失了？Σ(°Д°;", "danger");
        }
        else if (code == -1){
            msg_bar("神秘的错误出现了...Σヽ(ﾟД ﾟ; )ﾉ", "danger");
        }
        else if (code == 1){
            msg_bar("用户steam资料私密~⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄", "danger");
        }
        else if (code == 2){
            msg_bar("用户steam资料仅好友可见~⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄", "danger");
        }
        else {
            msg_bar("验证成功,拉取信息中~(≖ᴗ≖๑)", "success");
            $('#loginform').submit();
        }
    }

})

function msg_bar(msg, alarmlevel) {
    //alarmlevel = danger / warning / info / success
    var msg = $('<div class="alert alert-'+alarmlevel+' alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'+msg+'</div>');
    $('#info_box').empty().append(msg);
}
