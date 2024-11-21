package at.mavila.unsupervised.kmeans;

import at.mavila.unsupervised.Utils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class CentroidsUpdateService {

  public List<List<BigDecimal>> update(List<List<BigDecimal>> points, List<Integer> assignments, Integer k) {

    if (CollectionUtils.isEmpty(points)) {
      List<List<BigDecimal>> emptyCentroids = new ArrayList<>();
      for (int i = 0; i < k; i++) {
        emptyCentroids.add(List.of(BigDecimal.ZERO, BigDecimal.ZERO));
      }
      return emptyCentroids;
    }

    List<List<BigDecimal>> centroids = Utils.initArray(points, k);


    //Initialize a list to count the number of points in each cluster
    List<Integer> counts = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      counts.add(Integer.valueOf("0"));
    }

    // Sum up the coordinates of points assigned to each cluster
    for (int i = 0; i < points.size(); i++) {
      int cluster = assignments.get(i);
      counts.set(cluster, counts.get(cluster) + 1);

      List<BigDecimal> point = points.get(i);
      List<BigDecimal> centroid = centroids.get(cluster);
      for (int j = 0; j < point.size(); j++) {
        List<BigDecimal> bigDecimals = centroids.get(cluster);
        bigDecimals.set(j, centroid.get(j).add(point.get(j)));
      }

    }

    // Calculate the mean for each cluster
    for (int i = 0; i < k; i++) {
      List<BigDecimal> centroid = centroids.get(i);
      int clusterSize = counts.get(i);

      if (clusterSize > 0) {
        for (int j = 0; j < centroid.size(); j++) {
          centroids.get(i).set(j, centroid.get(j).divide(BigDecimal.valueOf(clusterSize), RoundingMode.HALF_UP));
        }
      }

    }


    return centroids;
  }

}
