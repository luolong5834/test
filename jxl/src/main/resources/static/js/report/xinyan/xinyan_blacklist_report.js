$(function() {
   $.ajaxSetup({
     beforeSend:function(){
        $('#result').text('正在查询，请稍等！');
     }
     });
    $('#queryBtn').click(function() {
        query_fun();
    })
});

query_fun = function() {
    var name = $('#name')
      , idcard = $('#idcard')
      , phone = $('#phone');

    if (!$(name).val()) {
        alert('请输入姓名！')
        return false;
    }
    if (!$(idcard).val()) {
        alert('请输入身份证！');
        return false;
    }

    var callback = function(msg) {
        if (msg.code == 'success') {
            if (msg.isBlacklistCode == "0") {
                $('#result').text('黑名单');
            } else if(msg.isBlacklistCode == "1") {
                $('#result').text('无法确认');
            }else if(msg.isBlacklistCode == "2") {
                $('#result').text('空值未知');
            }else if(msg.isBlacklistCode == "9") {
                $('#result').text('其他异常');
            }else{
                $('#result').text('系统异常');
            }
            $('#reportData').text(JSON.stringify(JSON.parse(msg.reportData), null, 4));
        } else {
            alert('系统繁忙，请稍后重试!');
        }
    }

    var callback2 = function(token) {

        var url = '/report/xinyan/blacklist/search';
        var data = {
            "token": token,
            "name": $(name).val(),
            "idcard": $(idcard).val(),
            "phone": $(phone).val()
        };
        $.post(url, data, callback);
    }
    getXinyanToken(callback2);
}
//获取新颜token
function getXinyanToken(callback) {
    console.log("开始获取token...");
    function dfCallBack(token, sign) {
        callback(token);
        console.log("token:" + token);
    }
    (function() {
        _saber = {
            merchantNo: '8150725275',
            token: '111111',
            appName: '咔咔钱包',
            tokenMode: 'backend',
            callback: dfCallBack
        };
        var aa = document.createElement('script');
        //aa.async = true;
        aa.async = false;
        aa.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'res.xinyan.com/static-dfp/pc/fingerprint.js?t=' + (new Date().getTime() / 3600000).toFixed(0);
        var bb = document.getElementsByTagName('script')[0];
        bb.parentNode.insertBefore(aa, bb);
    }
    )();
}
