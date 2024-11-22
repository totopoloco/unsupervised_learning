package at.mavila.unsupervised.kmeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class InitializeCentroidsService {

  private final EuclideanDistanceBetweenPointsService euclideanDistanceBetweenPointsService;

  public List<List<BigDecimal>> initialize(List<List<BigDecimal>> points, Integer k) {
    List<List<BigDecimal>> centroids = new ArrayList<>();
    Random random = new Random();

    // Choose the first centroid randomly
    centroids.add(points.get(random.nextInt(points.size())));

    // Choose the remaining k-1 centroids
    for (int i = 1; i < k; i++) {
      List<BigDecimal> nextCentroid = chooseNextCentroid(points, centroids);
      centroids.add(nextCentroid);
    }

    return centroids;
  }

  private List<BigDecimal> chooseNextCentroid(List<List<BigDecimal>> points, List<List<BigDecimal>> centroids) {
    double[] distances = new double[points.size()];

    // Choose a random point weighted by the distance
    double randomValue = Math.random() * getTotalDistance(points, centroids, distances);
    for (int i = 0; i < points.size(); i++) {
      randomValue -= distances[i];
      if (randomValue <= 0) {
        return points.get(i);
      }
    }

    return points.getLast();
  }

  private double getTotalDistance(List<List<BigDecimal>> points, List<List<BigDecimal>> centroids, double[] distances) {
    double totalDistance = 0;

    // Calculate the distance of each point to the nearest centroid
    for (int i = 0; i < points.size(); i++) {
      distances[i] = getMinDistance(points.get(i), centroids);
      totalDistance += distances[i];
    }
    return totalDistance;
  }

  private double getMinDistance(List<BigDecimal> point, List<List<BigDecimal>> centroids) {
    double minDistance = Double.MAX_VALUE;
    for (List<BigDecimal> centroid : centroids) {
      double distance = this.euclideanDistanceBetweenPointsService.calculate(List.of(point), List.of(centroid)).doubleValue();
      if (distance < minDistance) {
        minDistance = distance;
      }
    }
    return minDistance;
  }
}