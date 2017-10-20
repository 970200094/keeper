/**
 * Created by hanqi on 2017/9/5.
 */


// !function(window, document, $, undefined){

    var init = function(){
        getdata();
        xmgjExtend.navigationTitle('小马学院');

    };

    //考核
    assessONClick = function(name){
        // alert(222222)
        // location.href ='assess.html'
        xmgjExtend.nextViewControllerUrl('http://182.92.187.39:9491/html/XM-academy/assess.html?name='+name,name)
    };

    //更多
    moreData = function(name){
        // alert(3333333)
        // location.href ='Q&A.html'
        xmgjExtend.nextViewControllerUrl('http://182.92.187.39:9491/html/XM-academy/Q&A.html?name='+name,name)
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
                if (response.code ==100) {
                    var severList = response.value;
                    var severList1,severList2;

                    if(severList.length>8){
                        severList1= severList.slice(0,5);
                        severList2 = severList.slice(8,(severList.length-1));
                        // $('#scroller').append('<ul class="xm-div-ul ul2"></ul>');
                        var ul2 = $('#scroller .ul2');
                        $.each(severList1, function (index, val) {

                            lis += '<li onclick="liClick(\''+ val.id +'\',\''+val.url+'\',\''+ val.name +'\')">'+
                                '<dl>'+
                                '<dt><img src="'+val.icon+'" alt=""></dt>'+
                                '<dd>'+val.name+ '</dd>'+
                                '</dl>'+
                                '</li>'

                        });
                        ul1.empty();
                        ul1.html(lis)
                        $.each(severList2, function (index, val) {
                            // console.log(severList);

                            lis2 += '<li onclick="liClick(\''+ val.id +'\',\''+val.url+'\',\''+ val.name +'\')">'+
                                '<dl>'+
                                '<dt><img src="'+val.icon+'" alt=""></dt>'+
                                '<dd>'+val.name+ '</dd>'+
                                '</dl>'+
                                '</li>'

                        });
                        ul2.empty();
                        ul2.html(lis2);

                    }else {
                        $.each(severList, function (index, val) {
                            // console.log(severList);

                            lis += '<li onclick="liClick(\''+ val.id +'\',\''+val.url+'\',\''+ val.name +'\')">'+
                                '<dl>'+
                                '<dt><img src="'+val.icon+'" alt=""></dt>'+
                                '<dd>'+val.name+ '</dd>'+
                                '</dl>'+
                                '</li>'

                        });
                        ul1.empty();
                        ul1.append(lis);
                    }

                }else {
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
            // alert(11111)
            xmgjExtend.nextViewControllerUrl(send_url,name);
            // location.href = url
        }else{
            xmgjExtend.nextViewControllerUrl('http://182.92.187.39:9491/html/XM-academy/sever/sever-introdoce.html?id='+ item+'&name='+name,name);
                // location.href = 'sever/sever-introdoce.html?id='+ item+'&name='+name
        }
    };

    // ////////////////////下拉刷新/////////////////////////////////

    var loading = function() {

        require(['listloading'], function () {
            var Listloading = require('listloading');
            var m = 3;
            //var hei = $(window).height() -53 -40;

            // 创建iscroll之前必须要先设置父元素的高度，否则无法拖动iscroll
            // 模板
            // demo
            var listloading = new Listloading('#listloading', {
                disableTime: true, // 是否需要显示时间
                pullDownAction: function (cb, flg) { // 下拉刷新
                    // true则为默认加载 false为下拉刷新
                    m = 3;
                    if (flg) {
                        console.log('默认加载');
                    }else{
                        init();
                    }
                    // var __html = createHtml();
                    // $('#order-list').html(__html);
                    // 执行完执行方法之后必须执行回调 回调的作用是通知默认加载已经全部执行完毕，程序需要去创建iscroll或者做下拉刷新动作

                    cb();
                },
                Realtimetxt: '释放刷新数据！',
                loaderendtxt: '没有数据了',
                // iscroll的API
                iscrollOptions: {
                    scrollbars: false // 显示iscroll滚动条
                }
            });
            // 点击事件
            // listloading.evt('section', 'click', function (dom) {
            //     listloading.refresh();
            // });
        });
        require.config({
            paths: {
                "listloading": "../../js/iscroll/jslib/listloading"
            }
        });


    };

    loading();

    $(document).ready(init);


// }(window, document, jQuery);
