package at.mavila.unsupervised.kmeans;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ClosestCentroidFinderService {

  private final EuclideanDistanceBetweenPointsService euclideanDistanceBetweenPointsService;

  public Integer find(List<BigDecimal> point, List<List<BigDecimal>> centroids) {

    if(CollectionUtils.isEmpty(centroids)) {
      throw new IllegalArgumentException("centroids is empty");
    }

    final AtomicInteger closest = new AtomicInteger(0);
    final AtomicReference<BigDecimal> minDistance =
        new AtomicReference<>(this.euclideanDistanceBetweenPointsService.calculate(List.of(point), List.of(centroids.getFirst())));

    for (int i = 1; i < centroids.size(); i++) {
      BigDecimal dist = this.euclideanDistanceBetweenPointsService.calculate(List.of(point), List.of(centroids.get(i)));
      if (dist.compareTo(minDistance.get()) < 0) {
        minDistance.set(dist);
        closest.set(i);
      }
    }

    return closest.get();
  }

}
