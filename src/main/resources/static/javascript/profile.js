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

    $('.my_logout').click(function () {
        alert("ok")
        if ($.cookie("input_text") != null){
            $.removeCookie("input_text");
        }
        if ($.cookie("login_type") != null){
            $.removeCookie("login_type");
        }
    })
})