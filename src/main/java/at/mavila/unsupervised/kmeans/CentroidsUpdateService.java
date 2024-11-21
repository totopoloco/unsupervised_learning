package at.mavila.unsupervised.kmeans;

import at.mavila.unsupervised.Utils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CentroidsUpdateService {

  public List<List<BigDecimal>> update(List<List<BigDecimal>> points, List<Integer> assignments, Integer k) {
    List<List<BigDecimal>> newCentroids = new ArrayList<>();
    List<Integer> counts = new ArrayList<>(Collections.nCopies(k, 0));

    for (int i = 0; i < k; i++) {
      newCentroids.add(new ArrayList<>(Collections.nCopies(points.getFirst().size(), BigDecimal.ZERO)));
    }

    for (int i = 0; i < points.size(); i++) {
      List<BigDecimal> point = points.get(i);
      int cluster = assignments.get(i);
      counts.set(cluster, counts.get(cluster) + 1);

      for (int j = 0; j < point.size(); j++) {
        newCentroids.get(cluster).set(j, newCentroids.get(cluster).get(j).add(point.get(j)));
      }
    }

    for (int i = 0; i < k; i++) {
      for (int j = 0; j < newCentroids.get(i).size(); j++) {
        if (counts.get(i) == 0) {
          continue; // Skip division if no points are assigned to this cluster
        }
        newCentroids.get(i).set(j,
            newCentroids.get(i).get(j).divide(BigDecimal.valueOf(counts.get(i)), new MathContext(Utils.PRECISION))
                .stripTrailingZeros());
      }
    }

    return newCentroids;
  }
}