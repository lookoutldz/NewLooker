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

// ------------------------------profile.html-----------------------------------------

$(function () {

    //登出清除cookie
    $('.my_logout').click(function () {
        if ($.cookie("input_text") != null){
            $.removeCookie("input_text");
        }
        if ($.cookie("login_type") != null){
            $.removeCookie("login_type");
        }
    })
})

$(document).ready(function () {

    $('#upd_friend_game').click(function () {
        $(this).attr("disabled","true");
        var steamid = $('#hidden_steamid').val();
        $('#upd_friend_game span').text("更新中...");
        $.ajax({
            type : "POST",
            url : "/games/updFriendsGame",
            data : {steamid : steamid},
            success : function (data) {
                console.log(data);
                setTimeout($('#upd_friend_game span').text("更新完成"),10000);
                setTimeout($(this).removeAttr("disabled"),60000)
            },
            error : function () {
                setTimeout($(this).removeAttr("disabled"),60000)
            }
        })
    })
})