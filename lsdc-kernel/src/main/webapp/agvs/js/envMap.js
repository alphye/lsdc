	var realMapWidth=12000;
	var realMapHeight=12000;
	var x_lineNum=21;// 横线数量
	var y_lineNum=21;// 竖线数量
	var screenWidth = document.documentElement.clientWidth || document.body.clientWidth;
	var screenHeight = document.documentElement.clientHeight || document.body.clientHeight;
    
	var leftOffset=screenWidth*0.1;
	var topOffset=screenHeight*0.1;
	
	var mapHT=screenHeight*0.85;// 地图高
	var mapWD=mapHT*(realMapWidth/realMapHeight);// 地图宽
	
	var real_virtual_rate=mapWD/realMapWidth; // 真实地图与界面展示的地图的比例
		
	var p_left_top =	 {"X":leftOffset,"Y":topOffset};// 地图左上角坐标
	var p_left_bottom =  {"X":leftOffset,"Y":topOffset+mapHT};// 地图左下角坐标
	var p_right_top =	 {"X":leftOffset+mapWD,"Y":topOffset};// 地图右上角坐标
	var p_right_bottom = {"X":leftOffset+mapWD,"Y":topOffset+mapHT};// 地图右下角坐标
	
	var gridWD=mapWD/(y_lineNum-1);// 每个格子的宽度
	var gridHT=mapHT/(x_lineNum-1);// 每个格子的高度

	$(function(){
		initialize();
		$("rect").bind("contextmenu",onRectContextMenu);
        $(".cellIndex").bind("contextmenu",onRectContextMenu);
		  $(document).click(function(){
			$(".contextmenu").hide();
		  });
	});
    function initialize(){
        generateContextMenu2();
        buildNnaviTaskTemplate();
        // 真实地图的宽高比如果比屏幕的宽高比大，则要将<SVG>的width扩大。否则会显示不全
        var n=realMapWidth/realMapHeight-screenWidth/screenHeight;
        if(n>0){
            $('#mainSVG').css('width',(100+Math.ceil(n*100))+'%');
        }
        makeMapWithJSON_withTrack();
        makVehicle();
        resetVehicle();
    }
	function onRectContextMenu(e){
        showContextMenu("contextmenu1",e);
        hidenContextMenu2();
        return false;
    }

    function showContextMenu(menuID,e){
        var winWidth = $(document).width();
        var winHeight = $(document).height();
        var posX = e.pageX;
        var posY = e.pageY;
        var menuWidth = $("#"+menuID).width();
        var menuHeight = $("#"+menuID).height();
        var secMargin = 10;
        if(posX + menuWidth + secMargin >= winWidth
            && posY + menuHeight + secMargin >= winHeight){
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY - menuHeight - secMargin + "px";
        }
        else if(posX + menuWidth + secMargin >= winWidth){
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY + secMargin + "px";
        }
        else if(posY + menuHeight + secMargin >= winHeight){
            posLeft = posX + secMargin + "px";
            posTop = posY - menuHeight - secMargin + "px";
        }
        else {
            posLeft = posX + secMargin + "px";
            posTop = posY + secMargin + "px";
        };
        $("#"+menuID).css({
            "left": posLeft,
            "top": posTop
        }).show();
        return false;
    }
    function generateContextMenu2(){
    	var template = $("#contextmenu2Template").val();
        for(var i=0;i<actualTracks.length;i++){
    		var index=actualTracks[i].index;
    		var param=('0'+index).substr(-2);
    		var html=template.replace("#param#",param).replace('#index#',index);
    		$("#contextmenu2").append(html);
		}
	}

	function buildNnaviTaskTemplate(){
    	var naviTaskTemplate = $("#naviTaskTemplate").html();
    	var options = "";
        for(var i=0;i<actualTracks.length;i++){
            var index=actualTracks[i].index;
            var param='13'+('0'+index).substr(-2);
            options=options+" <option value='"+param+"'>"+index+"号位置</option>";
        }
        naviTaskTemplate=naviTaskTemplate.replace("#options#",options);
        $("#naviTaskTemplate").html(naviTaskTemplate);
	}
	function addNaviTask(){
        var nextNaviTaskIndex = $("#nextNaviTaskIndex").val();
        var naviTaskTemplate = $("#naviTaskTemplate").html();
        var plussignShow = nextNaviTaskIndex == 0 ? "table-cell":"none";
        naviTaskTemplate=naviTaskTemplate.replace("#plussignShow#",plussignShow);
        naviTaskTemplate=naviTaskTemplate.replace(/#index#/g,nextNaviTaskIndex);
        naviTaskTemplate=naviTaskTemplate.replace("#vehicleIp#",window.parent.vehicleIp)
        $("#naviTaskDataArea").append(naviTaskTemplate);

        $("#nextNaviTaskIndex").val(++nextNaviTaskIndex);
	}
    function selectNaviPoints(e,th){
        $("#contextmenu3").hide();
        var winWidth = $(document).width();
        var winHeight = $(document).height();
        var posX=$("#contextmenu1").offset().left+$("#contextmenu1").width()-5;
        var posY=$("#contextmenu1").offset().top-10;
        var menuWidth = $("#contextmenu2").width();
        var menuHeight = $("#contextmenu2").height();
        var secMargin = 10;
        if(posX + menuWidth + secMargin >= winWidth
            && posY + menuHeight + secMargin >= winHeight){
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY - menuHeight + ($(th).height())+18 + "px";
        }
        else if(posX + menuWidth + secMargin >= winWidth){
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY + secMargin + "px";
        }
        else if(posY + menuHeight + secMargin >= winHeight){
            posLeft = posX + secMargin + "px";
            posTop = posY - menuHeight  + ($(th).height())+18  + "px";
        }
        else {
            posLeft = posX + secMargin + "px";
            posTop = posY + secMargin + "px";
        };
        $("#contextmenu2").css({
			"height":$("#contextmenu1").height(),
			"overflow":"auto",
            "left": posLeft,
            "top": posTop
        }).show();
        return false;
    }
    function selectPackageDirection(e,th){
        $("#contextmenu2").hide();
        var winWidth = $(document).width();
        var winHeight = $(document).height();
        var posX=$("#contextmenu1").offset().left+$("#contextmenu1").width()-5;
        var posY=$("#contextmenu1").offset().top-10;
        var menuWidth = $("#contextmenu3").width();
        var menuHeight = $("#contextmenu3").height();
        var secMargin = 10;
        if(posX + menuWidth + secMargin >= winWidth
            && posY + menuHeight + secMargin >= winHeight){
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY - menuHeight + ($(th).height())+18 + "px";
        }
        else if(posX + menuWidth + secMargin >= winWidth){
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY + secMargin + "px";
        }
        else if(posY + menuHeight + secMargin >= winHeight){
            posLeft = posX + secMargin + "px";
            posTop = posY - menuHeight  + ($(th).height())+18  + "px";
        }
        else {
            posLeft = posX + secMargin + "px";
            posTop = posY + secMargin + "px";
        };
        $("#contextmenu3").css({
            "left": posLeft,
            "top": posTop
        }).show();
        return false;
    }

    function hidenContextMenu2(){
        $("#contextmenu2").hide();
        $("#contextmenu3").hide();
    }

	function makeSVG(tag, attrs) {
		var el= document.createElementNS('http://www.w3.org/2000/svg', tag);
		for (var k in attrs)
			el.setAttribute(k, attrs[k]);
		return el;
	}

	/**
	 *地图坐标系的坐标值 转换为 浏览器坐标系的坐标值
	 * @description 因浏览器的坐标系是左上角为原点，而地图坐标系的原点在左下角，所以要将小车的位置坐标转换一下
	 * p_left_bottom 地图原点在浏览器坐标系中的坐标值
	 * @param coor 地图坐标系的坐标值
	 * @return 浏览器坐标系中的坐标值
	*/
	function transformToBrowserCoor(coor){
		var transformedCoor={};
		transformedCoor.X=p_left_bottom.X+coor.X;
		transformedCoor.Y=p_left_bottom.Y-coor.Y;
		return transformedCoor;	
	}
	/**
	 *浏览器坐标系的坐标值 转换为 地图坐标系的坐标值
	 * @param coor 浏览器坐标系中的坐标值
     * @return 地图坐标系的坐标值
	*/
	function transformToMapCoor (coor){
		var transformedCoor={};
		transformedCoor.X=coor.X;-p_left_bottom.X
		transformedCoor.Y=p_left_bottom.Y-coor.Y;
		return transformedCoor;	
	}
	
	/**
	 *将小车传输来的真实坐标按比例转换为虚拟地图上的坐标，并转换坐标系
	 */
	function tranformRealCoorToBrowserCoor(coor){
		var transformedCoor={};
		transformedCoor.X=real_virtual_rate*coor.X;
		transformedCoor.Y=real_virtual_rate*coor.Y;
		return transformToBrowserCoor(transformedCoor);	
	}
	function makVehicle(vehicleIp){
		var vihecleOffset=3;
		var vihecleLength=gridWD-vihecleOffset;
		var vehicle=makeSVG('rect',{x:0,y:0,width:vihecleLength,height:vihecleLength,id:'vehicle-01',style:"stroke: blue; fill: red;",transform:"translate("+(-vihecleLength/2)+","+(-vihecleLength/2)+")"});
		$("#g1").append(vehicle);
	}

	function resetVehicle(){
		vehicleMoveTo("vehicle-01",tranformRealCoorToBrowserCoor({'X':0,'Y':0}));
	}
	function updateVehiclePosition(vehicleId,coor){
		vehicleMoveTo("vehicle-01",tranformRealCoorToBrowserCoor(coor));
	}
	function vehicleMoveTo(vehicleId,coor){
		$("#"+vehicleId).attr("x",coor.X).attr("y",coor.Y);
	}


	function onStopCharge(){
		window.parent.stopCharge();
	}
	function onStartCharge(){
		window.parent.startCharge();
	}
	
	function onQueryNaviTrails(){
		window.parent.queryNaviTrails();
	}

	function vehicleOnline(){
	    window.top.vehicleOnline();
    }

    function donone(){
        cancelBubble();
	    return false;
    }

    function cancelBubble(e) {
        var evt = e ? e : window.event;
        if (evt.stopPropagation) {        //W3C
            evt.stopPropagation();
        }else {       //IE
            evt.cancelBubble = true;
        }
    }

    function sendSimProCmd(param) {
        window.parent.sendSimProCmd(param);
    }

    function createNaviTask(){
        $("#createNaviTaskBox").fadeIn("slow");
        addNaviTask();
	}
	function cancleDialog() {
		$("#naviTaskDataArea").html("");
		$("#nextNaviTaskIndex").val("0");
		$("#createNaviTaskBox").fadeOut("fast");
		// $("#mask").css({ display: 'none' });
	}
	
	function submitDialog() {
        var t = $('form').serializeArray();
        var cyclicExecute = $("#cyclicExecute").prop("checked");
        var url="/lsdc/simpPro/sendContinuousSimpProCmd";
        if(cyclicExecute){
            url="/lsdc/simpPro/sendCyclicContinuousSimpProCmd";
        }
		$.ajax({
            url:url,
            type:"post",
            data:t,
            success:function(result){
                if(result.exception){
                    alert("新建导航任务异常！");
                }
                cancleDialog();
            }
        });
    }

    function cancleCyclicTask(){
        var url="/lsdc/simpPro/cancleCyclicTask";
        var data={"vehicleIp":window.parent.vehicleIp};
        $.ajax({
            url:url,
            type:"post",
            contentType:"application/json;charset=UTF-8",
            data:JSON.stringify(data),
            success:function(result){
                if(result.exception){
                    alert("取消循环任务异常！");
                }
                if(result=="success"){
                    alert("取消循环任务成功！");
                }
            }
        });
    }

    $.fn.AFserializeObject = function() {
        var obj = {};
        var array = $("form").serializeArray();
        $.each(array, function () {
            var match = this.name.match(/^([^\s]+)\[(\d+)\]([^\s]*)$/);
            if(match){// 有数组表达式[number]
                var arrayName=RegExp.$1;
                if(!obj[arrayName]){
                    obj[arrayName]=[];
                }
                if(RegExp.$3){ // 如果数组后边有属性
                    var arrName = ""+RegExp.$3.replace(".","");
                    if(!obj[arrayName][RegExp.$2]){
                        obj[arrayName][RegExp.$2]={};
                    }
                    obj[arrayName][RegExp.$2][arrName]=this.value;
                }else{
                    obj[arrayName][RegExp.$2]=this.value;
                }
            }else{
                if (obj[this.name]) {
                    if (!obj[this.name].push) {
                        obj[this.name] = [ obj[this.name] ];
                    }
                    obj[this.name].push(this.value || '');
                } else {
                    obj[this.name] = this.value || '';
                }
            }
        });
        return obj;
    };
