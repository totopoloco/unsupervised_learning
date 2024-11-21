package at.mavila.unsupervised.kmeans;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CentroidsUpdateServiceTest {

  @Autowired
  private CentroidsUpdateService centroidsUpdateService;

  @Test
  void testUpdate_withValidPoints() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0")),
        Arrays.asList(new BigDecimal("5.0"), new BigDecimal("6.0"))
    );
    List<Integer> assignments = Arrays.asList(0, 1, 0);
    int k = 2;

    List<List<BigDecimal>> result = this.centroidsUpdateService.update(points, assignments, k);

    List<List<BigDecimal>> expected = Arrays.asList(
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0"))
    );

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void testUpdate_withEmptyPoints() {
    List<List<BigDecimal>> points = List.of();
    List<Integer> assignments = List.of();
    int k = 2;

    List<List<BigDecimal>> result = this.centroidsUpdateService.update(points, assignments, k);

    List<List<BigDecimal>> expected = Arrays.asList(
        Arrays.asList(BigDecimal.ZERO, BigDecimal.ZERO),
        Arrays.asList(BigDecimal.ZERO, BigDecimal.ZERO)
    );

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void testUpdate_withSingleCluster() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0")),
        Arrays.asList(new BigDecimal("5.0"), new BigDecimal("6.0"))
    );
    List<Integer> assignments = Arrays.asList(0, 0, 0);
    int k = 1;

    List<List<BigDecimal>> result = this.centroidsUpdateService.update(points, assignments, k);

    List<List<BigDecimal>> expected = List.of(
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0"))
    );

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void testUpdate_withMultipleClusters() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0")),
        Arrays.asList(new BigDecimal("5.0"), new BigDecimal("6.0")),
        Arrays.asList(new BigDecimal("7.0"), new BigDecimal("8.0"))
    );
    List<Integer> assignments = Arrays.asList(0, 1, 0, 1);
    int k = 2;

    List<List<BigDecimal>> result = this.centroidsUpdateService.update(points, assignments, k);

    List<List<BigDecimal>> expected = Arrays.asList(
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0")),
        Arrays.asList(new BigDecimal("5.0"), new BigDecimal("6.0"))
    );

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void testUpdate_withNoPointsInCluster() {
    List<List<BigDecimal>> points = Arrays.asList(
        Arrays.asList(new BigDecimal("1.0"), new BigDecimal("2.0")),
        Arrays.asList(new BigDecimal("3.0"), new BigDecimal("4.0"))
    );
    List<Integer> assignments = Arrays.asList(0, 0);
    int k = 2;

    List<List<BigDecimal>> result = this.centroidsUpdateService.update(points, assignments, k);

    List<List<BigDecimal>> expected = Arrays.asList(
        Arrays.asList(new BigDecimal("2.0"), new BigDecimal("3.0")),
        Arrays.asList(BigDecimal.ZERO, BigDecimal.ZERO)
    );

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }
}