/* *
//li 的内容数据。li长度，ul，长度判断
* */
var severName =decodeURI(getParam('name'));

!function(window, document, $, undefined){

    var severId = getParam('id');

    var init = function(){
        getdata();
        initevent();
        // xmgjExtend.navigationTitle(severName);
    };

    var initevent = function(){
        var $close = $('.close');
        var $down = $('.down');
        var $xmMue = $('#xm-mue');
        var $allLIist = $('#all-list');

        $xmMue.find('ul').on('click', 'li', function(){
            var $this = $(this);
            $this.addClass('active').siblings('li').removeClass('active')
        });

        $down.on('click',function(){
            $allLIist.css('display','block');
            $xmMue.css('display','none')
        });

        $close.on('click',function(){
            $allLIist.css('display','none');
            $xmMue.css('display','block')
        });

	};

	var getdata = function() {
        var lis = [];
        var lis2 = [];
        var $allLIist = $('#all-list');
        var $ul1 = $('#ul1');
        var $innerhtml = $('#innerhtml');
        $.ajax({
            url:'../../../common/tools',
            dataType: 'json',
            processData: true,
            data:{"host":"college/getChannelInfo.do",map:{id:severId}},
            type: 'get',
            success: function (response) {
                if(response.code ==100) {
                    var severList = response.value;
                    if(severList.length<5){
                        $('.down').css('display','none');
                    }else{
                        $('.down').css('display','block');
                    }

                    $.each(severList, function (index, val) {
                        // console.log(val)
                        if (index == 0) {
                            lis.push(
                                '<li class="active" onclick="litabonClick(', val.id, ')">', val.name, '</li>'
                            );

                            $innerhtml.html(val.html)
                            getUL();
                        } else {
                            lis.push(
                                '<li onclick="litabonClick(', val.id, ')">', val.name, '</li>'
                            )
                        }
                    });

                    litabonClick = function (id) {
                        $.each(severList, function (index, val) {
                            if (id == val.id) {
                                $innerhtml.html(val.html);
                                // console.log(val.html)
                            }
                        });
                    }
                    $ul1.html(lis.join(''))
                    $allLIist.html(lis.join(''))

                    var liWidth = $('#all-list li').width();
                    $('#all-list ul').width(liWidth*severList.length);

                    getUL();
                }else {
                    console.log(response.message)
                }
            },
            error:function(error){
                console.log(error.message)
            }

        })
	};


	var getUL =  function(){
        var $close = $('.close');
        var $down = $('.down');
        var $xmMue = $('#xm-mue');
        var $allLIist = $('#all-list');

        $('#xm-mue ul li').on("click",function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $down.on('click',function(){
            $allLIist.css('display','block')
            $xmMue.css('display','none')
        });

        $close.on('click',function(){
            $allLIist.css('display','none')
            $xmMue.css('display','block')
        });

        var screenWidth = $(window).width(); //屏幕宽度
        var liWidth=70;
        console.log(screenWidth);

        var numMin = parseInt ( screenWidth/liWidth/2 );//3
        console.log(numMin)

        var menuWidth = $('#xm-mue ul').outerWidth(); //tab ul 宽度 800
        var liNum = $('#xm-mue ul li').length ; //tab li个数 11


        // console.log(menuWidth)//
        // console.log(liNum)//
        // console.log( $('#xm-mue ul li'))

        var allLi = 0;
        for(var i = 0; i < liNum; i++){
            allLi += $('#xm-mue ul li').eq(i).outerWidth();
        }
        // console.log(allLi)//850
        for (var i = 0; i < numMin; i++) {
            $('#xm-mue ul li').eq(i).click(function(){
                $('#xm-mue').scrollLeft( 0 );
            });
        }
        // console.log(liNum-numMin)
        for (var i = numMin; i < (liNum-numMin); i++) {
            $('#xm-mue ul li').eq(i).click(function(){
                var index = $(this).index();
                var len=0;
                for (var i = numMin; i < index; i++) {
                    var len1 =  $('#xm-mue ul li').eq(i).outerWidth();
                    len = len + len1;
//                    console.log(index) //76
//                    console.log(len1) //76
//                    console.log((liWidth+10)*(index-numMin)+len- liWidth*(index-numMin))
//                    console.log(liWidth)//
//                    console.log(index)//
//                    console.log(index-numMin,'333333333333')
//                    console.log(len,'22222222222')
//                    console.log(liWidth*(index-numMin))
//                    console.log((liWidth+4)*(index-numMin)+len- liWidth*(index-numMin),'5555555555555')
//                    console.log(liNum,'777777777')
//                    console.log(numMin,'44444444')
                }
                $('#xm-mue').scrollLeft( (liWidth+10)*(index-numMin)+len- liWidth*(index-numMin) );

            });

        }
        for (var i = (liNum-numMin) ; i < liNum ; i++) { //8
            $('#xm-mue ul li').eq(i).click(function(){
                $('#xm-mue').scrollLeft(menuWidth-allLi+ liWidth+screenWidth +20);
//                console.log($('#xm-mue').scrollLeft())
//                console.log(i,'1111')
//                console.log((liWidth+10)*(i-numMin)+i- liWidth*(i-numMin) )
//                console.log(allLi - menuWidth + liWidth+screenWidth)
//                console.log(allLi)//850
//                console.log( menuWidth )//900
//                console.log( liWidth )//70
            });

        }

        $("#all-list li").on("click",function(){
            var $this = $(this);
            $this.addClass('active').siblings('li').removeClass('active')
            var currIndex=$("#all-list li").index(this);
//            console.log(currIndex)
            $allLIist.css('display','none')
            $xmMue.css('display','block')
            $("#xm-mue ul li").eq(currIndex).addClass("active").siblings().removeClass("active");
            $('#xm-mue ul li').eq(currIndex).trigger("click");
        })
    }



    $(document).ready(init);
}(window, document, jQuery);





