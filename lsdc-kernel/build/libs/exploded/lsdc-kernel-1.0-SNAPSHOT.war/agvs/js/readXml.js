var minCoorXY=0;// 坐标的最小值
function makeMapWithJSON(){
	var edges =mapJSON.edges;
	for(var i=0;i<edges.length;i++){
		var edge = edges[i];
		//console.log(edge);
		var startNodeCoor =edge.startNodeCoor;
		var endNodeCoor =edge.endNodeCoor;
		var p_start=tranformReal_virtualXY({"X":startNodeCoor.position_x,"Y":startNodeCoor.position_y});
		var p_end=tranformReal_virtualXY({"X":endNodeCoor.position_x,"Y":endNodeCoor.position_y});
		
		var line=makeSVG("line",{x1:p_start.X,y1:p_start.Y,x2:p_end.X,y2:p_end.Y,id:edge.id,class:"edge"});
		
		$("#mainSVG").append(line);
		if(startNodeCoor.position_y==0.0){
			var text1 =makeSVG("text",{x:p_start.X-10,y:p_start.Y+10});
			$(text1).text(Math.round(startNodeCoor.position_x));
			$("#mainSVG").append(text1);
		}
		else if(startNodeCoor.position_x==0.0){
			var text1 =makeSVG("text",{x:p_start.X-30,y:p_start.Y+5});
			$(text1).text(Math.round(startNodeCoor.position_y));
			$("#mainSVG").append(text1);
		}
	}
}
function makeMapWithJSON_withTrack(){
	var edges =mapJSON.edges;
	for(var i=0;i<edges.length;i++){
		var edge = edges[i];
		//console.log(edge);
		var startNodeCoor =edge.startNodeCoor;
		var endNodeCoor =edge.endNodeCoor;
		var p_start=tranformReal_virtualXY({"X":startNodeCoor.position_x,"Y":startNodeCoor.position_y});
		var p_end=tranformReal_virtualXY({"X":endNodeCoor.position_x,"Y":endNodeCoor.position_y});
		
		var line=makeSVG("line",{x1:p_start.X,y1:p_start.Y,x2:p_end.X,y2:p_end.Y,id:edge.id,class:"edge"});
		
		$("#mainSVG").append(line);
		if(startNodeCoor.position_y==minCoorXY){
			var text1 =makeSVG("text",{x:p_start.X-10,y:p_start.Y+10+gridWD/2});
			$(text1).text(Math.round(startNodeCoor.position_x));
			$("#mainSVG").append(text1);
		}
		if(startNodeCoor.position_x==minCoorXY){
			var text1 =makeSVG("text",{x:p_start.X-30-gridWD/2,y:p_start.Y+5});
			$(text1).text(Math.round(startNodeCoor.position_y));
			$("#mainSVG").append(text1);
		}
		
		// 画轨道
		if(p_start.Y==p_end.Y){
			// 针对每一条横边，以它的终点为中心，画一个轨道格子
			var trackCell=makeSVG('rect',{x:p_end.X,y:p_end.Y,width:gridWD,height:gridWD,class:'trackCell',transform:"translate("+(-gridWD/2)+","+(-gridWD/2)+")"});
			$("#mainSVG").append(trackCell);
			if(startNodeCoor.position_x==minCoorXY){
				// 如果是最左侧的横边，则要再以它的起点为中心，画一个轨道格子
				trackCell=makeSVG('rect',{x:p_start.X,y:p_start.Y,width:gridWD,height:gridWD,class:'trackCell',transform:"translate("+(-gridWD/2)+","+(-gridWD/2)+")"});
				$("#mainSVG").append(trackCell);
			}
		}
	}
}
