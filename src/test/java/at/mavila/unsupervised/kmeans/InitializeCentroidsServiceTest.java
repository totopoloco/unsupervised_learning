package at.mavila.unsupervised.kmeans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InitializeCentroidsServiceTest {

  @Autowired
  private InitializeCentroidsService initializeCentroidsService;

  @Test
  void testInitialize_withEmptyPoints() {
    List<List<BigDecimal>> points = Collections.emptyList();
    Integer k = 3;

    List<List<BigDecimal>> centroids = initializeCentroidsService.initialize(points, k);

    assertThat(centroids).isEmpty();
  }

  @Test
  void testInitialize_withNullK() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0"))
    );
    Integer k = null;

    List<List<BigDecimal>> centroids = initializeCentroidsService.initialize(points, k);

    assertThat(centroids).isEmpty();
  }

  @Test
  void testInitialize_withValidPointsAndK() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0")),
        Arrays.asList(new BigDecimal("5.0"), new BigDecimal("6.0"))
    );
    Integer k = 2;

    List<List<BigDecimal>> centroids = initializeCentroidsService.initialize(points, k);

    assertThat(centroids).hasSize(2);
    assertThat(centroids.get(0)).containsExactly(new BigDecimal("1.0"), new BigDecimal("2.0"));
    assertThat(centroids.get(1)).containsExactly(new BigDecimal("3.0"), new BigDecimal("4.0"));
  }

  @Test
  void testInitialize_withKGreaterThanPointsSize() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0"))
    );
    Integer k = 3;



    assertThatThrownBy(() -> initializeCentroidsService.initialize(points, k))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Number of clusters (k) cannot exceed the number of points.");

  }

  @Test
  void testInitialize_withKEqualToPointsSize() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0"))
    );
    Integer k = 2;

    List<List<BigDecimal>> centroids = initializeCentroidsService.initialize(points, k);

    assertThat(centroids).hasSize(2);
    assertThat(centroids.get(0)).containsExactly(new BigDecimal("1.0"), new BigDecimal("2.0"));
    assertThat(centroids.get(1)).containsExactly(new BigDecimal("3.0"), new BigDecimal("4.0"));
  }
}