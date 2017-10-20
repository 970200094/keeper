/**
 * Created by hanqi on 2017/9/11.
 */
!function(window, document, $, undefined){

    var host = location.host;//获取ip和端口

    var init = function(){
        getQA();
    };
    var getQA = function(){

        var lis =[];
        var $uls = $('#qa-ul');
        $.ajax({
            url:'../../common/tools',
            dataType: 'json',
            processData: true,
            data:{"host":"college/getQuestionAnswer.do"},
            type: 'get',
            success: function (response) {
                if (response.code ==100) {
                    // console.log(response.value);
                    var severList = response.value;
                    if(severList.length>5){
                        $('.xm-more').css('display','block');
                        severList = severList.slice(0,5)
                    }else{
                        $('.xm-more').css('display','none');
                    }
                    // console.log(severList);
                    $.each(severList, function (index, val) {
                        var s= JSON.stringify(val)
                        var time = val.addTime;
                        var date = new Date(time);
                        var month = date.pattern("yyyy/MM/dd HH:mm")
                        // lis.push(
                        //
                        //     // '<li class="xm-qa" onclick="QAonclick(',s,')">',
                        //     '<li class="xm-qa" onclick="QAonclick(',(val.id),' , ',(val.url),')">',
                        //         '<dl>',
                        //         '<dt>',val.name,'</dt>',
                        //         '<dd>',
                        //         '<span>',val.addTime,'</span>',
                        //         '<span>','阅读' ,'</span> ' ,
                        //         '<span>',val.readCount+'</span>',
                        //         '</dd>',
                        //         '</dl>',
                        //     '</li>'
                        // );

                        //
                        lis +=
                            '<li class="xm-qa" onclick="QAonclick(\''+(val.id)+' \', \''+(val.url)+'\')">'+
                            '<dl>'+
                            '<dt>'+val.name+'</dt>'+
                            '<dd>'+
                            '<span>'+month+'</span>&nbsp;&nbsp;'+
                            '<span>'+'阅读' +'</span> ' +
                            '<span>'+val.readCount+'</span>'+
                            '</dd>'+
                            '</dl>'+
                            '</li>'

                    });
                    // $uls.html(lis.join(''))
                    $uls.append(lis)

                }else if (response.code ==105){
                    console.log(response.message)
                }else {
                    console.log(response.message)
                }
            },
            error:function(error){
                console.log(error.message)
            }
        });

        var Lisname = decodeURI('问题详情');
        //详情
        QAonclick = function(itemID,url){
            if(url){
                var send_url = 'http://'+url;
                console.log(send_url);
                xmgjExtend.nextViewControllerUrl(send_url,'问题详情');
                // location.href = 'https://'+url,+'&id='+itemID+'&name='+Lisname
            }else{
                xmgjExtend.nextViewControllerUrl('http://'+ host +'/html/XM-academy/details.html?id='+ itemID,'问题详情');
                // location.href = 'details.html?id='+ itemID
            }
        }
    };
    $(document).ready(init);


}(window, document, jQuery);
