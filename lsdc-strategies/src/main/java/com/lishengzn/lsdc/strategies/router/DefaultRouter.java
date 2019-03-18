package com.lishengzn.lsdc.strategies.router;

import com.lishengzn.lsdc.entity.Coordinate;
import com.lishengzn.common.entity.map.Edge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultRouter {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultRouter.class);
    private static Graph<String,ModelEdge> graph = new DirectedWeightedMultigraph<>(ModelEdge.class);
    private static ShortestPathAlgorithm<String,ModelEdge> algo;


    public static void initializeRouter() {
        Set<String> nodes = ReadMapUtil.getNodeIds();
        nodes.forEach((nodeId)->{
            graph.addVertex(nodeId);
        });
        Set<String> edgeIds =ReadMapUtil.getEdgeIds();
        edgeIds.forEach((edgeId)->{
            Edge edge = ReadMapUtil.getEdgeById(edgeId);
            // 沿边的起点到终点
            ModelEdge e1 = new ModelEdge(edge,true);
            if(!graph.addEdge(edge.getStartNodeId(), edge.getEndNodeId(),e1)){
                throw new RuntimeException("initializeGraph error edgeId: "+edgeId);
            }
            graph.setEdgeWeight(e1, 1);

           /* // 沿边的终点到起点
            ModelEdge e2 = new ModelEdge(edge,false);
            if(!graph.addEdge(edge.getEndNodeId(),edge.getStartNodeId(), e2)){
                throw new RuntimeException("initializeGraph error edgeId: "+edgeId);
            }
            graph.setEdgeWeight(e2, 1);*/
        });
        algo = new DijkstraShortestPath<>(graph);
    }

    /**
     * @param startNodeId 起始点
     * @param endNodeId 终点
     * @return 路径所经过的点的ID
     */
    public static List<String> getRoute(String startNodeId, String endNodeId){
        GraphPath<String, ModelEdge> graphPath =algo.getPath(startNodeId, endNodeId);
        List<ModelEdge> weightedEdges =graphPath.getEdgeList();
        List<String> nodeIds = weightedEdges.stream().map((weightedEdge)->{
            return weightedEdge.isMoveToEnd() ? weightedEdge.getEdge().getStartNodeId() : weightedEdge.getEdge().getEndNodeId();
        }).collect(Collectors.toList());
        ModelEdge lastModelEdge = (weightedEdges.get(weightedEdges.size()-1));
        nodeIds.add(lastModelEdge.isMoveToEnd() ? lastModelEdge.getEdge().getEndNodeId() : lastModelEdge.getEdge().getStartNodeId());
        return nodeIds;
    }

    /**
     * @param startNodeId 起始点
     * @param endNodeId 终点
     * @return 路径所经过的点的坐标
     */
    public static List<Coordinate> getRoute2(String startNodeId, String endNodeId){
        GraphPath<String, ModelEdge> graphPath =algo.getPath(startNodeId, endNodeId);
        List<ModelEdge> weightedEdges =graphPath.getEdgeList();
        List<Coordinate> nodes = weightedEdges.stream().map((weightedEdge)->{
            return weightedEdge.isMoveToEnd() ? weightedEdge.getEdge().getStartPos() : weightedEdge.getEdge().getEndPos();
        }).collect(Collectors.toList());
        ModelEdge lastModelEdge = (weightedEdges.get(weightedEdges.size()-1));
        nodes.add(lastModelEdge.isMoveToEnd() ? lastModelEdge.getEdge().getEndPos() : lastModelEdge.getEdge().getStartPos());
        return nodes;
    }

    public static void main(String[] args){
        String startId ="10001009";
        String endId ="10001005";
        List<String> steps = getRoute(startId,endId);
        List<Coordinate> steps2 = getRoute2(startId,endId);
        System.out.println(Arrays.toString(steps.toArray(new Integer[]{})));
        System.out.println(Arrays.toString(steps2.toArray(new Coordinate[]{})));
    }
}
