/**
 * Created by hanqi on 2017/9/5.
 */



!function(window, document, $, undefined){

    var host = location.host;//获取ip和端口

    var init = function(){
        getdata();
        xmgjExtend.navigationTitle('小马学院');

    };

//考核
    assessONClick = function(name){
        //alert(222222)
        // location.href ='assess.html'
        xmgjExtend.nextViewControllerUrl('http://'+ host +'/html/XM-academy/assess.html?name='+name,name)
    };

//更多
    moreData = function(name){
        //alert(3333333)
        // location.href ='Q&A.html'
        xmgjExtend.nextViewControllerUrl('http://'+ host +'/html/XM-academy/Q&A.html?name='+name,name)
    }

    var getdata = function (){

        var lis = [];
        var lis2 = [];
        var ul1 = $('.ul1');

        $.ajax({
            url:'../../common/tools',
            dataType: 'json',
            processData: true,
            data:{"host":"college/getChannel.do"},
            type: 'get',
            success: function (response) {
                if (response.code == 100) {
                    var severList = response.value;
                    var severList1, severList2;

                    if (severList.length > 8) {
                        severList1 = severList.slice(0, 8);
                        severList2 = severList.slice(8, (severList.length - 1));
                        // $('#scroller').append('<ul class="xm-div-ul ul2"></ul>');
                        var ul2 = $('#scroller .ul2');
                        $.each(severList1, function (index, val) {

                            lis += '<li><a onclick="liClick(\'' + val.id + '\',\'' + val.url + '\',\'' + val.name + '\')">' +
                                '<dl>' +
                                '<dt><img src="' + val.icon + '" alt=""></dt>' +
                                '<dd>' + val.name + '</dd>' +
                                '</dl>' +
                                '</a></li>'

                        });
                        ul1.empty();
                        ul1.html(lis)
                        $.each(severList2, function (index, val) {
                            // console.log(severList);

                            lis2 += '<li><a onclick="liClick(\'' + val.id + '\',\'' + val.url + '\',\'' + val.name + '\')">' +
                                '<dl>' +
                                '<dt><img src="' + val.icon + '" alt=""></dt>' +
                                '<dd>' + val.name + '</dd>' +
                                '</dl>' +
                                '</a></li>'

                        });
                        ul2.empty();
                        ul2.html(lis2);

                    } else {
                        $.each(severList, function (index, val) {
                            // console.log(severList);

                            lis += '<li><a onclick="liClick(\'' + val.id + '\',\'' + val.url + '\',\'' + val.name + '\')">' +
                                '<dl>' +
                                '<dt><img src="' + val.icon + '" alt=""></dt>' +
                                '<dd>' + val.name + '</dd>' +
                                '</dl>' +
                                '</a></li>'

                        });
                        ul1.empty();
                        ul1.append(lis);
                    }

                } else {
                    console.log(response.message)
                }
            },
        error:function(error){
            console.log(error.message)
        }
    })
};

liClick = function(item,url,name){
    var send_url = 'http://'+url;
    if(url){
        //alert(11111)
        xmgjExtend.nextViewControllerUrl(send_url,name);
        // location.href = url
    }else{
        xmgjExtend.nextViewControllerUrl('http://'+ host +'/html/XM-academy/sever/sever-introdoce.html?id='+ item+'&name='+name,name);
        // location.href = 'sever/sever-introdoce.html?id='+ item+'&name='+name
    }
};

// ////////////////////下拉刷新/////////////////////////////////

var loading = function() {

    require(['listloading'], function () {
        var Listloading = require('listloading');

        var listloading = new Listloading('#listloading', {
            disableTime: true, // 是否需要显示时间
            pullDownAction: function (cb, flg) { // 下拉刷新

                getdata();

                cb();
            },
            Realtimetxt: '释放刷新数据！',
            loaderendtxt: '没有数据了',
            // iscroll的API
            iscrollOptions: {
                scrollbars: false // 显示iscroll滚动条
            }
        });
    });
    require.config({
        paths: {
            "listloading": "../../js/iscroll/jslib/listloading"
        }
    });


};

loading();

$(document).ready(init);



 }(window, document, jQuery);
