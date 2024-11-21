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
        List.of(new BigDecimal("2"), new BigDecimal("1")),
        List.of(new BigDecimal("4"), new BigDecimal("3")),
        List.of(new BigDecimal("5"), new BigDecimal("4"))
    );
    Integer k = Integer.valueOf("2");
    Pair<List<List<BigDecimal>>, List<Integer>> calculate = this.kmeansService.calculate(dataSet, k);
    assertThat(calculate).isNotNull();

    // Print results
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