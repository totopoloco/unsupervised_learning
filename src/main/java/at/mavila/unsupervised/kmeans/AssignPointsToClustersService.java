package at.mavila.unsupervised.kmeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AssignPointsToClustersService {

  private final ClosestCentroidFinderService closestCentroidFinderService;

  public List<Integer> assign(List<List<BigDecimal>> points, List<List<BigDecimal>> centroids) {
    if (CollectionUtils.isEmpty(points) || CollectionUtils.isEmpty(centroids)) {
      return Collections.emptyList();
    }

    List<Integer> assignments = new ArrayList<>(points.size());
    for(int i = 0; i < points.size(); i++) {
      assignments.add(0);
    }

    for (int i = 0; i < points.size(); i++) {
      List<BigDecimal> point = points.get(i);
      Integer element = this.closestCentroidFinderService.find(point, centroids);
      assignments.set(i, element);
    }

    return assignments;
  }

}
