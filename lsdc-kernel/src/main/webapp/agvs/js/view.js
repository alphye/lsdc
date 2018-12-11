    var subscripeTopic = function() {
        console.log("========subscripeTopic");
		window.top.stomp.subscribe(constantsJSON.rtopicAddressConstants.setVehicleInIp,function(msg){
            var data =  JSON.parse(msg.body);
            $("#vehicleIp").html(data.message);
            alert("车辆上线："+data.message);
		});
		window.top.stomp.subscribe(constantsJSON.rtopicAddressConstants.setVehicleInfo,function(msg){
            var data =  JSON.parse(msg.body);
            var message = data.message;
            $("#vehicleIp").html(message.vehicleIp);
            $("#vehicle_position").html(message.position.position_x + ","+ message.position.position_y);
            if(message.targetPosition){
                $("#vehicle_targetPosition").html(message.targetPosition.position_x + ","+ message.targetPosition.position_y);
            }
            // 更新小车位置
            SVGFrame.updateVehiclePosition("vehicle-01",{'X':message.position.position_x,'Y':message.position.position_y});
            $("#vehicle_vx").html(message.velocity_x);
            $("#vehicle_vy").html(message.velocity_y);
            $("#vehicle_batteryCapacity").html(message.batteryCapacity);
            /*$("#vehicle_naviState").html(message.naviStateName);
            var millisecond= new Date().getTime();

            var t=(millisecond/10000.0 - Math.floor(millisecond/10000));
            if(Math.round(t*10000)%500<=40){
                // 这里是为了视觉效果，将“总里程”的更新频率降低到每1秒更新一次
                $("#vehicle_mileage").html(message.mileage);
            }*/
		});

		/*window.top.stomp.subscribe(constantsJSON.rtopicAddressConstants.setVehicleNaviState,function(msg){
			var data =  JSON.parse(msg.body);
			var message = data.message;
			// 更新小车导航状态
			$("#vehicle_naviState").html(message.naviStateName);
		});

		window.top.stomp.subscribe(constantsJSON.rtopicAddressConstants.setVehicleOperationState,function(msg){
			var data =  JSON.parse(msg.body);
			var message = data.message;
            // 更新小车操作状态
            $("#vehicle_operationState").html(message.operationStateName);
		});

		window.top.stomp.subscribe(constantsJSON.rtopicAddressConstants.printNaviTrails,function(msg){
			var data =  JSON.parse(msg.body);
			var message = data.message;
            // 打印导航轨迹
            console.log("本次导航轨迹：  "+message);
		});*/

		window.top.stomp.subscribe(constantsJSON.rtopicAddressConstants.error,function(msg){
			var data =  JSON.parse(msg.body);
            alert(data.message);
		});

	};

	function uppstn(){
		SVGFrame.updateVehiclePosition("vehicle-01",{'X':$("#p_x").val(),'Y':$("#p_y").val()});
	}


	//发送消息
	function send(address,message) {
		window.top.sendMessage(address,message);
	}
	function move(op){
		if(!/^[1-9][0-9]*$/.test($("#moveCells").val())){
			alert("请输入正整数！");
			return;
		}
		var command={"commandType":"vehicleMove","message":{"vehicleIp":$("#vehicleIp").html(),"direct":op,"cellNum":$("#moveCells").val()}};
		send(constantsJSON.stopicAddressConstants.vehicleMove,JSON.stringify(command));
	}
	
	function moveToPoint(coor){
		if(isNaN(coor.X) || isNaN(coor.Y)){
			alert("坐标值请输入数字！");
			return;
		}
		var command={"commandType":"sendToNode","message":{"vehicleIp":$("#vehicleIp").html(),"destCoor":{"position_x":coor.X,"position_y":coor.Y}}};
		send(constantsJSON.stopicAddressConstants.sendToNode,JSON.stringify(command));
	}
	function appendTrail(op){
		if(!/^[1-9][0-9]*$/.test($("#appendCells").val())){
			alert("请输入正整数！");
			return;
		}
		var command={"commandType":"appendNaviTaskWidthCellNum","message":{"vehicleIp":$("#vehicleIp").html(),"direct":op,"cellNum":$("#appendCells").val()}};
		send(constantsJSON.stopicAddressConstants.appendNaviTaskWidthCellNum,JSON.stringify(command));
	}
	
	function modifyNaviTask(op){
		var command={"commandType":op,"message":{"vehicleIp":$("#vehicleIp").html()}};
		var stopic="";
		if(op=="cancleNaviTask"){
			stopic=constantsJSON.stopicAddressConstants.cancleNaviTask;
		}
		else if(op=="pauseNaviTask"){
            stopic=constantsJSON.stopicAddressConstants.pauseNaviTask;
        }
		else if(op=="recoverNaviTask"){
            stopic=constantsJSON.stopicAddressConstants.recoverNaviTask;
        }
		send(stopic,JSON.stringify(command));
	}
	
	function beltRotationOn(){
		var command={"commandType":"sendOperationTask","message":{"vehicleIp":$("#vehicleIp").html(),"operationCode":0x021,"operationParams":[1,2]}};
		send(constantsJSON.stopicAddressConstants.sendOperationTask,JSON.stringify(command));
	}
	function beltRotationStop(){
		var command={"commandType":"sendOperationTask","message":{"vehicleIp":$("#vehicleIp").html(),"operationCode":0x022,"operationParams":[1]}};
		send(constantsJSON.stopicAddressConstants.sendOperationTask,JSON.stringify(command));
	}
	function beltRotationOnAndStop(){
		var command={"commandType":"sendOperationTask","message":{"vehicleIp":$("#vehicleIp").html(),"operationCode":0x023,"operationParams":[1,2,3000]}};
		send(constantsJSON.stopicAddressConstants.sendOperationTask,JSON.stringify(command));
	}
	
	function modifyOperationTask(op){
		var command={"commandType":op,"message":{"vehicleIp":$("#vehicleIp").html()}};
        var stopic="";
        if(op=="cancleOperationTask"){
            stopic=constantsJSON.stopicAddressConstants.cancleOperationTask;
        }
        else if(op=="pauseOperationTask"){
            stopic=constantsJSON.stopicAddressConstants.pauseOperationTask;
        }
        else if(op=="recoverOperationTask"){
            stopic=constantsJSON.stopicAddressConstants.recoverOperationTask;
        }
		send(stopic,JSON.stringify(command));
	}
	function clearError(){
		var command={"commandType":"clearError","message":{"vehicleIp":$("#vehicleIp").html()}};
		send(constantsJSON.stopicAddressConstants.clearError,JSON.stringify(command));
	}
	
	function queryNaviTrails(){
		var command={"commandType":"queryNaviTrails","message":{"vehicleIp":$("#vehicleIp").html()}};
		send(constantsJSON.stopicAddressConstants.queryNaviTrails,JSON.stringify(command));
	}
	function changeContainerDirection(direction){
		var command={"commandType":"sendOperationTask","message":{"vehicleIp":$("#vehicleIp").html(),"operationCode":0x031,"operationParams":[direction]}};
		send(constantsJSON.stopicAddressConstants.sendOperationTask,JSON.stringify(command));
	}
	function stopCharge(){
		var command={"commandType":"stopCharge","message":{"vehicleIp":$("#vehicleIp").html()}};
		send(constantsJSON.stopicAddressConstants.stopCharge,JSON.stringify(command));
	}
	function startCharge(){
		var command={"commandType":"startCharge","message":{"vehicleIp":$("#vehicleIp").html()}};
		send(constantsJSON.stopicAddressConstants.startCharge,JSON.stringify(command));
	}
	function writePackageSize(packageSize){
		if(isNaN(packageSize.packageLength) || isNaN(packageSize.packageWidth)){
			alert("包裹尺寸请输入数字！");
			return;
		}
		var command={"commandType":"packageSize","message":{"vehicleIp":$("#vehicleIp").html(),"packageWidth":packageSize.packageWidth,"packageLength":packageSize.packageLength}};
		send(constantsJSON.stopicAddressConstants.packageSize,JSON.stringify(command));
	}


	function sendSimProCmd(param){
        // var command={"vehicleIp":$("#vehicleIp").html(),"param":param};
        var command={"commandType":"sendSimProCmd","message":{"vehicleIp":$("#vehicleIp").html(),"param":param}};
        send("/wbskt/sendSimpProCmd",JSON.stringify(command));
	}
    function vehicleOnline(){
        window.top.vehicleOnline();
    }