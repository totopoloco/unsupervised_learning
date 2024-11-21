package at.mavila.unsupervised.kmeans;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class InitializeCentroidsService {

  public List<List<BigDecimal>> initialize(List<List<BigDecimal>> points, Integer k) {
    return getLists(points, k).orElseGet(() -> {
      validateLength(points, k);
      return points.subList(0, k);
    });
  }

  private static Optional<List<List<BigDecimal>>> getLists(List<List<BigDecimal>> points, Integer k) {
    if (CollectionUtils.isEmpty(points) || Objects.isNull(k) || k <= 0) {
      return Optional.of(Collections.emptyList());
    }
    return Optional.empty();
  }

  private static void validateLength(List<List<BigDecimal>> points, Integer k) {
    if (k > points.size()) {
      throw new IllegalArgumentException("Number of clusters (k) cannot exceed the number of points.");
    }
  }


}
