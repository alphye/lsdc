/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package com.lishengzn.lsdc.strategies.router;

import com.lishengzn.common.entity.map.Edge;
import org.jgrapht.graph.DefaultWeightedEdge;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Stefan Walter (Fraunhofer IML)
 */
public class ModelEdge
    extends DefaultWeightedEdge {

  /* 引用的边 */
  private final Edge edge;

  /* 是否从起点移动到终点 */
  private boolean moveToEnd;
  public ModelEdge(Edge edge,boolean moveToEnd) {
    this.edge = requireNonNull(edge, "edge");
    this.moveToEnd = requireNonNull(moveToEnd);
  }

  public Edge getEdge() {
    return edge;
  }

  public boolean isMoveToEnd() {
    return moveToEnd;
  }
}
