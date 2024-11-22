package at.mavila.unsupervised.kmeans;

import at.mavila.unsupervised.Utils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;

@Service
public class EuclideanDistanceBetweenPointsService {

  public BigDecimal calculate(List<List<BigDecimal>> point1, List<List<BigDecimal>> point2) {

    List<List<BigDecimal>> point1Validated = Objects.requireNonNull(point1, "point1 is null");
    List<List<BigDecimal>> point2Validated = Objects.requireNonNull(point2, "point2 is null");
    checkSizes(point1Validated, point2Validated);

    AtomicReference<BigDecimal> distance = new AtomicReference<>(BigDecimal.ZERO);
    for (int i = 0; i < point1Validated.size(); i++) {
      final BigDecimal bigDecimal = distance.get();
      distance.set(bigDecimal.add(point1Validated.get(i).getFirst().subtract(point2Validated.get(i).getFirst()).pow(2)));
    }

    return distance.get().sqrt(new MathContext(Utils.PRECISION)).stripTrailingZeros();
  }

  private static void checkSizes(List<List<BigDecimal>> point1Validated, List<List<BigDecimal>> point2Validated) {
    if (point1Validated.size() != point2Validated.size()) {
      throw new IllegalArgumentException("point1 and point2 have different dimensions");
    }
  }

}
