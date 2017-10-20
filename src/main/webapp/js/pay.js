var dataAry = new Array();
var revenAry = new Array();
var myMoney,firstMoney,moneyOld;
var months,keeperId
var map

var w = $(window).width();
var wx;
if(w > 320){ wx = 200}else{wx = 180}

var host = location.host;//获取ip和端口

function orderList(obj){

    if(obj == 1){
        localStorage.removeItem("keepId")
    }else if(obj == 2){
        //alert(keeperId)
        localStorage.setItem("keepId",keeperId)
    }
    //location.href = 'orderList.html';
    xmgjExtend.nextViewControllerUrl('http://'+host+'/html/pay/orderList.html','订单营收')

}
function cardList(obj){

    if(obj == 1){
        localStorage.removeItem("keepId")
    }else if(obj == 2){
        //alert(keeperId)
        localStorage.setItem("keepId",keeperId)
    }
    //location.href = 'cardList.html';
    xmgjExtend.nextViewControllerUrl('http://'+host+'/html/pay/cardList.html','办卡营收')
}

function getTime(month){
    map={revenue_month:month}
    getData()
    localStorage.setItem("month",month)
    months = map.revenue_month
}

var m1=0,m2=0,m3=0;
var a1 = []
var a2 = []

//刷新数据
require(['iscroll', 'listloading'], function() {
    var Listloading = require('listloading');
    // demo
    var listloading = new Listloading('#listloading', {
        disableTime: true, // 是否需要显示时间
        pullDownAction: function(cb,flg) { // 下拉刷新
            map={revenue_month:months}
            if(flg){

            }else{
                getData()
            }

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
        "iscroll": "../../js/iscroll/jslib/iscroll",
        "listloading": "../../js/iscroll/jslib/listloading"
    }
});

function getData(){

    $.ajax({
        url:'../../common/tools',
        dataType: 'json',
        processData: true,
        data:{host:"revenue/wages/manage",map:map},
        type: 'get',
        success: function (data) {
            if(data.code == 100) {
                dataAry = []
                revenAry = []
                var obj = data.value
                keeperId = obj.keeperId
                //alert(obj)
                //if(obj) {
                $('#title').html('<span>'+months.substring(0,4)+'年'+months.substring(5,7)+'月营收</span>')
                $('#num1').text(obj.orderCount)
                $('#num3').text(obj.ranking)

                var len = obj.mySalaryVos.length
                $.each(obj.mySalaryVos, function (key, val) {
                    //当前月份工资
                    if (key == len - 1) {
                        //console.log(key,val)
                        m1 = val.reven;
                        m2 = val.card;
                        m3 = val.basicSalary;
                        //console.log(m1,m2,m3)
                        $('#m1').text(m1)
                        $('#m2').text(m2)
                        $('#m3').text(m3)
                        //总营收
                        if (m1 + m2 > m3) {
                            $('#num2').text(m1 + m2)
                            myMoney = m1 + m2
                        } else {
                            $('#num2').text(m3)
                            myMoney = m3
                        }
                        console.log(myMoney)
                        $("#can").empty();
                        if(m1+m2+m3 == 0){
                            $('#can').html('<div class="none" style="background:#ccc;border-radius:50%;color:#fff;text-align:center;line-height:130px">0%</div>')
                        }else if(m1+m2>m3){
                            c1(m1,m2)
                        }else if(m1+m2<m3){
                            $('#can').html('<div class="none" style="background:#FF6D00;border-radius:50%;color:#fff;text-align:center;line-height:130px">100%</div>')
                        }
                    }
                    //上一月份工资
                    if (key == len - 2) {
                        //console.log(key,val)
                        var m1Old, m2Old, m3Old;
                        m1Old = val.reven;
                        m2Old = val.card;
                        m3Old = val.basicSalary;
                        if (m1Old + m2Old > m3Old) {
                            moneyOld = m1Old + m2Old
                        } else {
                            moneyOld = m3Old
                        }
                        $('#m1Old').text(m1Old)
                        $('#m2Old').text(m2Old)
                    }
                    //薪资走势
                    var time = val.timeStamp;
                    var reven = val.reven;
                    var card = val.card;
                    var basicSalary = val.basicSalary;
                    time = new Date(time);
                    var month = time.getMonth() + 1 + '月';
                    if (reven + card > basicSalary) {
                        revenAry.push(reven + card)
                    } else {
                        revenAry.push(basicSalary)
                    }

                    dataAry.push(month)

                    c2()
                })
                //同城薪资对比
                var n1, n2, n3;
                n1 = obj.firstRevenCount
                n2 = obj.firstCardCount
                n3 = obj.firstProtectCount
                if (n1 + n2 > n3) {
                    firstMoney = n1 + n2
                } else {
                    firstMoney = n3
                }
                if (myMoney == firstMoney) {
                    $("#desc").html('恭喜您成为同城最高收入者，感恩小马和客户，感谢自己。再接再厉吧，加油')
                } else {
                    if (myMoney - moneyOld >= 1) {
                        var num = ((myMoney - moneyOld) / myMoney) * 100
                        //console.log(num)
                        var n = Math.round(num).toFixed(1) + '%'
                        //$('#s1').html('增加了<font class="fc_o">'+n+'</font>')
                        $("#desc").html('您的薪资比上月增加了<font class="fc_o">' + n + '</font>，有进步哦！只差<font class="fc_o">' + (firstMoney - myMoney) + '</font>元，您就可以追上同城最高i薪资啦。多多努力，相信你可以的!')
                    } else if(myMoney - moneyOld <= -1) {
                        var num = ((moneyOld - myMoney) / moneyOld) * 100
                        console.log(num)
                        var n = Math.round(num).toFixed(1) + '%'
                        //$('#s1').html('减少了<font class="fc_o">'+n+'</font>')
                        $("#desc").html('您的薪资比上月减少了<font class="fc_o">' + n + '</font>，有点点下降哦。不过没关系，多多努力，追上同城最高薪资没那么难，相信你可以的！')
                    }else if(myMoney - moneyOld == 0){
                        $("#desc").html('当前还没有您的营收信息，可能你上月未接单或者新入职，若上月出勤，没有营收信息，请尽快向片区反馈，谢谢！')
                    }
                }

                $('#n1').text(n1)
                $('#n2').text(n2)
                $('#n3').text(n3)
                //console.log(ary)
                c3()
                //技能对比
                //var mySkill=[{"skill_name":"吃饭"},{"skill_name":"睡觉"},{"skill_name":"上班"}]
                //var firstSkill=[{"skill_name":"吃饭"},{"skill_name":"睡觉"},{"skill_name":"上班"},{"skill_name":"下班"}]
                var html = ''
                a1 = []
                a2 = []
                $.each(obj.mySkill, function (key, val) {

                    html += '<span>' + val.skill_name + '</span>'
                    a1.push(val.skill_name)

                })

                $('.tbox4 dl').eq(0).find('dd').html(html)

                var html2 = ''
                var html3 = ''

                $.each(obj.firstSkill, function (key, val) {
                    a2.push(val.skill_name)
                })

                $.each(a2, function (key, val) {
                    $.each(a1, function (key2,val2) {
                        if(val === val2){
                            html2 += '<span>' + val + '</span>'
                        }
                    })
                    if (a1.indexOf(val) < 0) {
                        html3 += '<span style="color:#FF6D00">' + val + '</span>'
                    }
                });
                $('.tbox4 dl').eq(1).find('dd').html(html2+html3)

            }else{
                alert(data.message)
            }
        },
        error: function(data) {
            alert("Connection error");
        }
    })
}

function decimal(num,v){
    var vv = Math.pow(10,v);
    return Math.round(num*vv)/vv;
}
//饼状图
function c1(m1,m2){
    var v1,v2;
    var width = wx-60;
    var height = wx-60;

    var num=[m1,m2];
    var sun=0;
    for(var i=0;i<num.length;i++){
        sun=sun+num[i];
    }
    v1 = decimal((num[0]/sun),2)
    v2 = decimal((num[1]/sun),2)


    var dataset = [ parseInt(v1*100) , parseInt(v2*100)];
    //alert(dataset[1])
    var svg = d3.select("#can")
        .append("svg")
        .attr("width", width)
        .attr("height", height);

    var pie = d3.layout.pie();

    var piedata = pie(dataset);

    var outerRadius = width/2;	//外半径
    var innerRadius = 0;	//内半径，为0则中间没有空白

    var arc = d3.svg.arc()	//弧生成器
        .innerRadius(innerRadius)	//设置内半径
        .outerRadius(outerRadius);	//设置外半径

    var col=["#FFD400","#FF9B27"]


    var arcs = svg.selectAll("g")
        .data(piedata)
        .enter()
        .append("g")
        .attr("transform","translate("+ (width/2) +","+ (width/2) +")");

    arcs.append("path")
        .attr("fill",function(d,i){

            if(i == 0){
                return col[0];
            }else if(i == 1){
                return col[1];
            }

        })
        .attr("d",function(d){
            return arc(d);
        });

    arcs.append("text")
        .attr("transform",function(d){
            if( v1 == 1){
                return "translate(-1.3399990446244323e-14,10)";
            }else if(v2 == 1){
                return "translate(-1.3399990446244323e-14,10)";
            }else{
                return "translate(" + arc.centroid(d) + ")";
            }
        })
        .attr("fill","#fff")
        .attr("text-anchor","middle")
        .text(function(d){
            if(d.data > 0){
                return d.data+'%';
            }

        });
}

//折线图
function c2(){
    var myChart = echarts.init(document.getElementById('canvasDivs'));

    option = {

        grid: {
            top:'10%',
            left: '0',
            right: '10%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            boundaryGap: true,
            splitLine: { //网格线
                show: false,
                lineStyle: {
                    color: ['#f5f5f5']
                }
            },
            axisLine: {
                lineStyle: {
                    color: '#f5f5f5'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    fontSize: 10,
                    color: '#485465'
                }
            },

            data:dataAry,
        }],
        yAxis: [{
            type: 'value',
            axisLine: {
                show: false
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: ['#f5f5f5'],
                    type: 'solid',
                    fontsize: '8px'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    fontSize: 10,
                    color: '#485465'
                }
            },
        }],
        series: [{
            name: '我的薪资',
            type: 'line',
            stack: '我的薪资',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            itemStyle: {
                normal: {
                    color: 'rgba(255,109,0,0.8)'
                }
            },
            areaStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: 'rgba(255,109,0,0.80)'
                    }, {
                        offset: 1,
                        color: 'rgba(255,255,255,0.20)'
                    }])
                }
            },
            data: revenAry
        }]
    };
    myChart.setOption(option);
}

function c3(){

    var myChart = echarts.init(document.getElementById('canvasDiv'));

    option = {
        grid: {
            top:'10%',
            left: '0',
            right: '12%',
            bottom: '3%',
            containLabel: true
        },
        calculable : true,
        xAxis: [{
            type: 'category',
            boundaryGap: true,
            splitLine: { //网格线
                show: false,
                lineStyle: {
                    color: ['#f5f5f5']
                }
            },
            axisLine: {
                lineStyle: {
                    color: '#f5f5f5',
                    fontsize: '10px'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    fontSize: 10,
                    color: '#485465'
                }
            },

            data:['我的薪资','同城最高']
        }],
        yAxis: [{
            type: 'value',
            axisLine: {
                show: false
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: ['#f5f5f5'],
                    type: 'solid',
                    fontsize: '10px'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    fontSize: 10,
                    color: '#485465'
                }
            },
        }],
        series : [
            {
                name:'薪资对比',
                type:'bar',
                data:[myMoney, firstMoney],
                barWidth: '20%',
                itemStyle: {
                    normal: {
                        color: function(params) {
                            // build a color map as your need.
                            var colorList = [
                                '#FFD400','#FF6D00'
                            ];
                            return colorList[params.dataIndex]
                        },label: {
                            show: true,
                            position: 'top',
                            formatter: '{c}'
                        }
                    }
                }
            }
        ]
    };

    myChart.setOption(option);

}




