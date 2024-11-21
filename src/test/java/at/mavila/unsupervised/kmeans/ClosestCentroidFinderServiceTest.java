package at.mavila.unsupervised.kmeans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClosestCentroidFinderServiceTest {

  @Autowired
  private ClosestCentroidFinderService closestCentroidFinderService;

  @Test
  void testFind_updatesMinDistance() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("20.8"), new BigDecimal("89.93"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1.912"), new BigDecimal("2.123")),
        Arrays.asList(new BigDecimal("4.83883"), new BigDecimal("5.838")),
        Arrays.asList(new BigDecimal("7.6263"), new BigDecimal("8.82383"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(2); // Ensure the closest centroid is found
  }

  @Test
  void testFind_withDifferentResult() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("4.0"), new BigDecimal("5.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("4.0"), new BigDecimal("5.0")),
        Arrays.asList(new BigDecimal("7.0"), new BigDecimal("8.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(1);
  }

  @Test
  void testFind_withSingleCentroid() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0);
  }

  @Test
  void testFind_withMultipleCentroids() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0")),
        Arrays.asList(new BigDecimal("5.0"), new BigDecimal("6.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0);
  }

  @Test
  void testFind_withEquidistantCentroids() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("0.0"), new BigDecimal("1.0")),
        Arrays.asList(new BigDecimal("2.0"), new BigDecimal("3.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0); // Assuming the first centroid is chosen in case of a tie
  }

  @Test
  void testFind_withNoCentroids() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"));
    List<List<BigDecimal>> centroids = List.of();

    assertThatThrownBy(() -> closestCentroidFinderService.find(point, centroids))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("centroids is empty");
  }

  @Test
  void testFind_withHighPrecision() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1.12345678901234567890"), new BigDecimal("2.12345678901234567890"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1.12345678901234567890"), new BigDecimal("2.12345678901234567890")),
        Arrays.asList(new BigDecimal("3.12345678901234567890"), new BigDecimal("4.12345678901234567890"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0);
  }

  @Test
  void testFind_withNegativeCoordinates() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("-1.0"), new BigDecimal("-2.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("-1.0"), new BigDecimal("-2.0")),
        Arrays.asList(new BigDecimal("-3.0"), new BigDecimal("-4.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0);
  }

  @Test
  void testFind_withLargeValues() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1000000.0"), new BigDecimal("2000000.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1000000.0"), new BigDecimal("2000000.0")),
        Arrays.asList(new BigDecimal("3000000.0"), new BigDecimal("4000000.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0);
  }

  @Test
  void testFind_withMixedValues() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1.0"), new BigDecimal("-2.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("-2.0")),
        Arrays.asList(new BigDecimal("-3.0"), new BigDecimal("4.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0);
  }

  @Test
  void testFind_withZeroValues() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("0.0"), new BigDecimal("0.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("0.0"), new BigDecimal("0.0")),
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("1.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0);
  }

  @Test
  void testFind_withSameCoordinates() {
    List<BigDecimal> point = Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"));
    List<List<BigDecimal>> centroids = List.of(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"))
    );

    Integer result = closestCentroidFinderService.find(point, centroids);

    assertThat(result).isEqualTo(0); // Assuming the first centroid is chosen in case of a tie
  }
}