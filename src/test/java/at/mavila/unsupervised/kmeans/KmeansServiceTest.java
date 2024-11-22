package at.mavila.unsupervised.kmeans;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class KmeansServiceTest {

  @Autowired
  private KmeansService kmeansService;

  @Test
  void testKmeans_withValidPoints() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("2"), new BigDecimal("2")),
        List.of(new BigDecimal("4"), new BigDecimal("3")),
        List.of(new BigDecimal("5"), new BigDecimal("4"))
    );
    Integer k = Integer.valueOf("2");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();
    List<List<BigDecimal>> left = calculate.getLeft();
    assertThat(left).isNotNull();
    assertThat(left.size()).isEqualTo(k);

    // Check centroids
    // Sometimes the centroids are swapped, so we need to check both possibilities
    assertThat(left.getFirst()).containsAnyOf(new BigDecimal("1.5"), new BigDecimal("4.5"));
    assertThat(left.getLast()).containsAnyOf(new BigDecimal("1.5"), new BigDecimal("4.5"));

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withValidPointsExtended() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("0.5")),
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("1.5"), new BigDecimal("1")),
        List.of(new BigDecimal("1.5"), new BigDecimal("0.5")),
        List.of(new BigDecimal("2.5"), new BigDecimal("1.5")),
        List.of(new BigDecimal("1.5"), new BigDecimal("3")),
        List.of(new BigDecimal("4"), new BigDecimal("3")),
        List.of(new BigDecimal("2.5"), new BigDecimal("5.5")),
        List.of(new BigDecimal("8"), new BigDecimal("1.5")),
        List.of(new BigDecimal("5.5"), new BigDecimal("4")),
        List.of(new BigDecimal("5.5"), new BigDecimal("5.5")),
        List.of(new BigDecimal("6"), new BigDecimal("3.5")),
        List.of(new BigDecimal("6.5"), new BigDecimal("4.5")),
        List.of(new BigDecimal("6.5"), new BigDecimal("5.5")),
        List.of(new BigDecimal("7.5"), new BigDecimal("5.5"))
    );
    Integer k = Integer.valueOf("2");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withValidPointsReduced() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("1"), new BigDecimal("3.5")),
        List.of(new BigDecimal("1.5"), new BigDecimal("4")),
        List.of(new BigDecimal("3.5"), new BigDecimal("1")),
        List.of(new BigDecimal("4"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("2.5")),
        List.of(new BigDecimal("4"), new BigDecimal("4"))

    );
    Integer k = Integer.valueOf("2");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withValidPointsReducedWithThreeClusters() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("1"), new BigDecimal("3.5")),
        List.of(new BigDecimal("1.5"), new BigDecimal("4")),
        List.of(new BigDecimal("3.5"), new BigDecimal("1")),
        List.of(new BigDecimal("4"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("2.5")),
        List.of(new BigDecimal("4"), new BigDecimal("4"))

    );
    Integer k = Integer.valueOf("3");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withValidPointsReducedWithThreeClustersWithAPointFarAway() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("-10"), new BigDecimal("3.5")),
        List.of(new BigDecimal("1.5"), new BigDecimal("4")),
        List.of(new BigDecimal("3.5"), new BigDecimal("1")),
        List.of(new BigDecimal("4"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("2.5")),
        List.of(new BigDecimal("4"), new BigDecimal("4"))

    );
    Integer k = Integer.valueOf("3");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withValidPointsReducedWithThreeClustersWithAPointNear() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("1"), new BigDecimal("1.2")),
        List.of(new BigDecimal("1.5"), new BigDecimal("4")),
        List.of(new BigDecimal("3.5"), new BigDecimal("1")),
        List.of(new BigDecimal("4"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("1.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("2.5")),
        List.of(new BigDecimal("4"), new BigDecimal("4"))

    );
    Integer k = Integer.valueOf("3");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withValidPointsReducedWithTwoClustersWithAPointNear() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("0.5")),
        List.of(new BigDecimal("1"), new BigDecimal("1.5")),
        List.of(new BigDecimal("2.5"), new BigDecimal("1.5")),
        List.of(new BigDecimal("2.5"), new BigDecimal("4")),
        List.of(new BigDecimal("4.5"), new BigDecimal("1")),
        List.of(new BigDecimal("3.5"), new BigDecimal("3.5")),
        List.of(new BigDecimal("3.5"), new BigDecimal("4.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("4.5")),
        List.of(new BigDecimal("4.5"), new BigDecimal("5"))

    );
    Integer k = Integer.valueOf("2");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  private void reportDataSet(List<List<BigDecimal>> dataSet) {
    log.info("Data Set:");
    for (List<BigDecimal> point : dataSet) {
      log.info(Arrays.toString(point.toArray()));
    }
  }

  @Test
  void testKmeans_withLargeDataSet() {
    List<List<BigDecimal>> dataSet = List.of(
        // Cluster 1
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("1.1"), new BigDecimal("1.1")),
        List.of(new BigDecimal("0.9"), new BigDecimal("0.9")),
        List.of(new BigDecimal("1.2"), new BigDecimal("1.2")),
        List.of(new BigDecimal("0.8"), new BigDecimal("0.8")),
        // Cluster 2
        List.of(new BigDecimal("5"), new BigDecimal("5")),
        List.of(new BigDecimal("5.1"), new BigDecimal("5.1")),
        List.of(new BigDecimal("4.9"), new BigDecimal("4.9")),
        List.of(new BigDecimal("5.2"), new BigDecimal("5.2")),
        List.of(new BigDecimal("4.8"), new BigDecimal("4.8")),
        // Cluster 3
        List.of(new BigDecimal("9"), new BigDecimal("9")),
        List.of(new BigDecimal("9.1"), new BigDecimal("9.1")),
        List.of(new BigDecimal("8.9"), new BigDecimal("8.9")),
        List.of(new BigDecimal("9.2"), new BigDecimal("9.2")),
        List.of(new BigDecimal("8.8"), new BigDecimal("8.8"))
    );
    Integer k = Integer.valueOf("3");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withDispersedPoints() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("1"), new BigDecimal("1")),
        List.of(new BigDecimal("10"), new BigDecimal("10")),
        List.of(new BigDecimal("20"), new BigDecimal("20")),
        List.of(new BigDecimal("30"), new BigDecimal("30")),
        List.of(new BigDecimal("40"), new BigDecimal("40")),
        List.of(new BigDecimal("50"), new BigDecimal("50")),
        List.of(new BigDecimal("60"), new BigDecimal("60")),
        List.of(new BigDecimal("70"), new BigDecimal("70")),
        List.of(new BigDecimal("80"), new BigDecimal("80")),
        List.of(new BigDecimal("90"), new BigDecimal("90"))
    );
    Integer k = Integer.valueOf("3");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }

  @Test
  void testKmeans_withStarrySkyPoints() {
    List<List<BigDecimal>> dataSet = List.of(
        List.of(new BigDecimal("3.2"), new BigDecimal("8.7")),
        List.of(new BigDecimal("15.1"), new BigDecimal("20.3")),
        List.of(new BigDecimal("24.4"), new BigDecimal("5.6")),
        List.of(new BigDecimal("35.7"), new BigDecimal("32.8")),
        List.of(new BigDecimal("42.9"), new BigDecimal("19.7")),
        List.of(new BigDecimal("55.5"), new BigDecimal("43.2")),
        List.of(new BigDecimal("67.8"), new BigDecimal("60.1")),
        List.of(new BigDecimal("71.4"), new BigDecimal("15.9")),
        List.of(new BigDecimal("80.3"), new BigDecimal("88.8")),
        List.of(new BigDecimal("92.1"), new BigDecimal("70.4")),
        List.of(new BigDecimal("5.4"), new BigDecimal("25.8")),
        List.of(new BigDecimal("18.3"), new BigDecimal("12.4")),
        List.of(new BigDecimal("23.8"), new BigDecimal("41.9")),
        List.of(new BigDecimal("31.1"), new BigDecimal("7.6")),
        List.of(new BigDecimal("45.2"), new BigDecimal("36.5")),
        List.of(new BigDecimal("58.9"), new BigDecimal("22.4")),
        List.of(new BigDecimal("63.4"), new BigDecimal("50.2")),
        List.of(new BigDecimal("72.3"), new BigDecimal("68.7")),
        List.of(new BigDecimal("85.7"), new BigDecimal("10.3")),
        List.of(new BigDecimal("96.4"), new BigDecimal("95.2"))
    );
    Integer k = Integer.valueOf("5");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
    reportDataSet(dataSet);
    reportCentroids(calculate);
    reportClusterAssignment(dataSet, calculate);
  }


  private static void reportClusterAssignment(List<List<BigDecimal>> dataSet,
                                              Pair<List<List<BigDecimal>>, List<Integer>> calculate) {
    log.info("Cluster Assignments:");
    for (int i = 0; i < dataSet.size(); i++) {
      log.info("Point " + dataSet.get(i) + " belongs to cluster " + calculate.getRight().get(i));
    }
  }

  private static void reportCentroids(Pair<List<List<BigDecimal>>, List<Integer>> calculate) {
    log.info("Final Centroids:");
    for (List<BigDecimal> centroid : calculate.getLeft()) {
      log.info(Arrays.toString(centroid.toArray()));
    }
  }


}