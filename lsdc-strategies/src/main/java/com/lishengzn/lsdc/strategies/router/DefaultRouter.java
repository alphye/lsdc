package com.lishengzn.lsdc.strategies.router;

import com.lishengzn.lsdc.entity.Coordinate;
import com.lishengzn.common.entity.map.Edge;
import com.lishengzn.common.util.ReadMapUtil;
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
    private static Graph<Integer,ModelEdge> graph =new DirectedWeightedMultigraph<Integer,ModelEdge>(ModelEdge.class);
    private static ShortestPathAlgorithm<Integer,ModelEdge> algo;

    static{
        initializeGraph();
    }

    private static void initializeGraph() {
        Set<Integer> nodes = ReadMapUtil.getNodeIds();
        nodes.forEach((nodeId)->{
            graph.addVertex(nodeId);
        });
        Set<Integer> edgeIds =ReadMapUtil.getEdgeIds();
        edgeIds.forEach((edgeId)->{
            Edge edge = ReadMapUtil.getEdgeById(edgeId);
            // 沿边的起点到终点
            ModelEdge e1 = new ModelEdge(edge,true);
            if(!graph.addEdge(edge.getStartNodeId(), edge.getEndNodeId(),e1)){
                throw new RuntimeException("initializeGraph error edgeId: "+edgeId);
            }
            graph.setEdgeWeight(e1, 1);

            // 沿边的终点到起点
            ModelEdge e2 = new ModelEdge(edge,false);
            if(!graph.addEdge(edge.getEndNodeId(),edge.getStartNodeId(), e2)){
                throw new RuntimeException("initializeGraph error edgeId: "+edgeId);
            }
            graph.setEdgeWeight(e2, 1);
        });
        algo = new DijkstraShortestPath<Integer,ModelEdge>(graph);
    }

    public static List<Integer> getRoute(Integer startNodeId, Integer endNodeId){
        GraphPath<Integer, ModelEdge> graphPath =algo.getPath(startNodeId, endNodeId);
        List<ModelEdge> weightedEdges =graphPath.getEdgeList();
        List<Integer> nodeIds = weightedEdges.stream().map((weightedEdge)->{
            return weightedEdge.isMoveToEnd() ? weightedEdge.getEdge().getStartNodeId() : weightedEdge.getEdge().getEndNodeId();
        }).collect(Collectors.toList());
        ModelEdge lastModelEdge = (weightedEdges.get(weightedEdges.size()-1));
        nodeIds.add(lastModelEdge.isMoveToEnd() ? lastModelEdge.getEdge().getEndNodeId() : lastModelEdge.getEdge().getStartNodeId());
        return nodeIds;
    }

    public static List<Coordinate> getRoute2(Integer startNodeId, Integer endNodeId){
        GraphPath<Integer, ModelEdge> graphPath =algo.getPath(startNodeId, endNodeId);
        List<ModelEdge> weightedEdges =graphPath.getEdgeList();
        List<Coordinate> nodeIds = weightedEdges.stream().map((weightedEdge)->{
            return weightedEdge.isMoveToEnd() ? weightedEdge.getEdge().getStartNodeCoor() : weightedEdge.getEdge().getEndNodeCoor();
        }).collect(Collectors.toList());
        ModelEdge lastModelEdge = (weightedEdges.get(weightedEdges.size()-1));
        nodeIds.add(lastModelEdge.isMoveToEnd() ? lastModelEdge.getEdge().getEndNodeCoor() : lastModelEdge.getEdge().getStartNodeCoor());
        return nodeIds;
    }

    public static void main(String[] args){
        int startId =10001009;
        int endId =10001005;
        List<Integer> steps = getRoute(startId,endId);
        List<Coordinate> steps2 = getRoute2(startId,endId);
        System.out.println(Arrays.toString(steps.toArray(new Integer[]{})));
        System.out.println(Arrays.toString(steps2.toArray(new Coordinate[]{})));
    }
}
