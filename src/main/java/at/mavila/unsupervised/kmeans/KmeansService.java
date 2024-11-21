package at.mavila.unsupervised.kmeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class KmeansService {

  private final InitializeCentroidsService initializeCentroidsService;
  private final AssignPointsToClustersService assignPointsToClustersService;
  private final CentroidsUpdateService centroidsUpdateService;

  public Pair<List<List<BigDecimal>>, List<Integer>> calculate(List<List<BigDecimal>> points, Integer k) {

    final AtomicReference<List<List<BigDecimal>>> centroids = new AtomicReference<>(this.initializeCentroidsService.initialize(points, k));
    final AtomicReference<List<Integer>> assignments = new AtomicReference<>(new ArrayList<>(points.size()));

    while (true) {
      List<Integer> newAssignments = this.assignPointsToClustersService.assign(points, centroids.get());
      if (newAssignments.equals(assignments.get())) {
        break;
      }
      assignments.set(newAssignments);
      centroids.set(this.centroidsUpdateService.update(points, assignments.get(), k));
    }

    return Pair.of(centroids.get(), assignments.get());
  }

}
