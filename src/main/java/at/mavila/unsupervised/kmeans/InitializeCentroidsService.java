package at.mavila.unsupervised.kmeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class InitializeCentroidsService {

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
    double totalDistance = 0;

    // Calculate the distance of each point to the nearest centroid
    for (int i = 0; i < points.size(); i++) {
      distances[i] = getMinDistance(points.get(i), centroids);
      totalDistance += distances[i];
    }

    // Choose a random point weighted by the distance
    double randomValue = Math.random() * totalDistance;
    for (int i = 0; i < points.size(); i++) {
      randomValue -= distances[i];
      if (randomValue <= 0) {
        return points.get(i);
      }
    }

    return points.getLast();
  }

  private double getMinDistance(List<BigDecimal> point, List<List<BigDecimal>> centroids) {
    double minDistance = Double.MAX_VALUE;
    for (List<BigDecimal> centroid : centroids) {
      double distance = calculateDistance(point, centroid);
      if (distance < minDistance) {
        minDistance = distance;
      }
    }
    return minDistance;
  }

  private double calculateDistance(List<BigDecimal> point1, List<BigDecimal> point2) {
    double sum = 0;
    for (int i = 0; i < point1.size(); i++) {
      double diff = point1.get(i).subtract(point2.get(i)).doubleValue();
      sum += diff * diff;
    }
    return sum;
  }
}