/**
 * Created by hanqi on 2017/9/8.
 */

var severName =decodeURI(getParam('name'));

!function(window, document, $, undefined){

    var init = function(){
        initevent()
        getdata();
        xmgjExtend.navigationTitle(severName);
    };

    var initevent = function(){

    };

    var getdata = function (){
        var lis = [];
        var ul1 = $('#ul1');

        $.ajax({
            url:'../../common/tools',
            dataType: 'json',
            processData: true,
            data:{"host":"college/getExam.do"},
            type: 'get',
            success: function (response) {
                if(response.code ==100){
                    console.log(response.value);
                    var severList = response.value;
                    $.each(severList, function(index,val){
                        console.log(val);
                        lis.push(
                            '<li class="xm-list" onclick="onAssessData(','\''+(val.name)+'\''+','+'\''+(val.url)+'\'',')">',
                                '<img src="'+val.icon+'" alt="">',
                                '<span>',val.name,'</span>',
                                '<i class="arrow"></i>',
                             '</li>'
                        )
                    });
                    ul1.html(lis.join(''))
                }else {
                    console.log(response.message)
                }
            },
            error:function(error){
                console.log(error.message)
            }

        })

        onAssessData = function(name, url){
            var send_url = 'http://'+url;
            xmgjExtend.nextViewControllerUrl(send_url,name);
        }


    };



    $(document).ready(init);


}(window, document, jQuery);
