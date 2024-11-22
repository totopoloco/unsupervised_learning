package at.mavila.unsupervised;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

  /**
   * Precision for the calculations.
   */
  public static final int PRECISION = 20;


  public static List<List<BigDecimal>> initArray(List<List<BigDecimal>> points, Integer k) {
    List<List<BigDecimal>> centroids = new ArrayList<>();

    for (int i = 0; i < k; i++) {
      List<BigDecimal> centroid = new ArrayList<>();
      for (int j = 0; j < points.getFirst().size(); j++) {
        centroid.add(BigDecimal.ZERO);
      }
      centroids.add(centroid);
    }
    return centroids;
  }

}
