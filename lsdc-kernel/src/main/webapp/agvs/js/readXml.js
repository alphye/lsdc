var minCoorXY=0;// 坐标的最小值
function makeMapWithJSON(){
	var edges =mapJSON.edges;
	for(var i=0;i<edges.length;i++){
		var edge = edges[i];
		//console.log(edge);
		var startNodeCoor =edge.startNodeCoor;
		var endNodeCoor =edge.endNodeCoor;
		var p_start=tranformRealCoorToBrowserCoor({"X":startNodeCoor.position_x,"Y":startNodeCoor.position_y});
		var p_end=tranformRealCoorToBrowserCoor({"X":endNodeCoor.position_x,"Y":endNodeCoor.position_y});
		
		var line=makeSVG("line",{x1:p_start.X,y1:p_start.Y,x2:p_end.X,y2:p_end.Y,id:edge.id,class:"edge"});
		
		$("#g1").append(line);
		if(startNodeCoor.position_y==0.0){
			var text1 =makeSVG("text",{x:p_start.X-10,y:p_start.Y+10});
			$(text1).text(Math.round(startNodeCoor.position_x));
			$("#g1").append(text1);
		}
		else if(startNodeCoor.position_x==0.0){
			var text1 =makeSVG("text",{x:p_start.X-30,y:p_start.Y+5});
			$(text1).text(Math.round(startNodeCoor.position_y));
			$("#g1").append(text1);
		}
	}
}
function makeMapWithJSON_withTrack(){
	var edges =mapJSON.edges;
	var nodes = mapJSON.nodes;
	for(var i=0;i<edges.length;i++){
		var edge = edges[i];
		//console.log(edge);
		var startNodeCoor =edge.startNodeCoor;
		var endNodeCoor =edge.endNodeCoor;
		var p_start=tranformRealCoorToBrowserCoor({"X":startNodeCoor.position_x,"Y":startNodeCoor.position_y});
		var p_end=tranformRealCoorToBrowserCoor({"X":endNodeCoor.position_x,"Y":endNodeCoor.position_y});
		
		var line=makeSVG("line",{x1:p_start.X,y1:p_start.Y,x2:p_end.X,y2:p_end.Y,id:edge.id,class:"edge"});
		
		$("#g1").append(line);
		if(startNodeCoor.position_y==minCoorXY){
			var text1 =makeSVG("text",{x:p_start.X-10,y:p_start.Y+10+gridWD/2});
			$(text1).text(Math.round(startNodeCoor.position_x));
			$("#g1").append(text1);
		}
		if(startNodeCoor.position_x==minCoorXY){
			var text1 =makeSVG("text",{x:p_start.X-30-gridWD/2,y:p_start.Y+5});
			$(text1).text(Math.round(startNodeCoor.position_y));
			$("#g1").append(text1);
		}
	}
    // 画轨道
    var trackCellArr=[];
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i];
		var trackCoor=tranformRealCoorToBrowserCoor({"X":node.position.position_x,"Y":node.position.position_y});
		var clazz="trackCell";
        var trackCell;
        if(isActualTrack(node)){
            clazz="actualTrackCell";
            trackCell=makeSVG('rect',{index:node.index,x:trackCoor.X,y:trackCoor.Y,width:gridWD,height:gridWD,class:clazz,transform:"translate("+(-gridWD/2)+","+(-gridWD/2)+")"});
            trackCellArr.push(trackCell);
            continue;
        }
        trackCell=makeSVG('rect',{x:trackCoor.X,y:trackCoor.Y,width:gridWD,height:gridWD,class:clazz,transform:"translate("+(-gridWD/2)+","+(-gridWD/2)+")"});
        $("#g1").append(trackCell);
	}

	for(var i=0;i<trackCellArr.length;i++){
        $("#g1").append(trackCellArr[i]);
        var text2 =makeSVG("text",{x:$(trackCellArr[i]).attr("x")-5 ,y:$(trackCellArr[i]).attr("y"),class:"cellIndex"});
        $(text2).text($(trackCellArr[i]).attr("index"));
        // 轨道的index放在g2，就会显示是在上层，不会被车遮盖住
        $("#g2").append(text2);
    }
}

function isActualTrack(node){
    for(var i=0;i<actualTracks.length;i++){
        var actualTrack = actualTracks[i];
        if(Math.abs(actualTrack.position.X-node.position.position_x)<=250 && Math.abs(actualTrack.position.Y-node.position.position_y)<=250){
            console.log("actualTrack===="+node);
            node.index=actualTrack.index;
            return true;
        }
    }
    return false;
}
