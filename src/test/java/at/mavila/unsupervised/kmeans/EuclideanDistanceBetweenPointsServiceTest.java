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
class EuclideanDistanceBetweenPointsServiceTest {


  @Autowired
  private EuclideanDistanceBetweenPointsService service;

  @Test
  void testCalculate_withValidPoints() {
    List<List<BigDecimal>> point1 = Arrays.asList(
        List.of(new BigDecimal("1.0")),
        List.of(new BigDecimal("2.0"))
    );
    List<List<BigDecimal>> point2 = Arrays.asList(
        List.of(new BigDecimal("4.0")),
        List.of(new BigDecimal("6.0"))
    );

    final BigDecimal result = this.service.calculate(point1, point2);
    final double expected = 5D;

    assertThat(result).isNotNull();
    assertThat(result.doubleValue()).isEqualTo(expected);
  }

  @Test
  void testCalculate_withNullPoint1() {
    List<List<BigDecimal>> point2 = Arrays.asList(
        List.of(new BigDecimal("4.0")),
        List.of(new BigDecimal("6.0"))
    );

    assertThatThrownBy(() -> service.calculate(null, point2))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("point1 is null");

  }

  @Test
  void testCalculate_withNullPoint2() {
    List<List<BigDecimal>> point1 = Arrays.asList(
        List.of(new BigDecimal("1.0")),
        List.of(new BigDecimal("2.0"))
    );

    assertThatThrownBy(() -> service.calculate(point1, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("point2 is null");

  }

  @Test
  void testCalculate_withDifferentDimensions() {
    List<List<BigDecimal>> point1 = List.of(
        List.of(new BigDecimal("1.0"))
    );
    List<List<BigDecimal>> point2 = Arrays.asList(
        List.of(new BigDecimal("4.0")),
        List.of(new BigDecimal("6.0"))
    );

    assertThatThrownBy(() -> service.calculate(point1, point2))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("point1 and point2 have different dimensions");

  }

  @Test
  void testCalculate_withHighPrecision() {
    List<List<BigDecimal>> point1 = Arrays.asList(
        List.of(new BigDecimal("1.35")),
        List.of(new BigDecimal("2.05"))
    );
    List<List<BigDecimal>> point2 = Arrays.asList(
        List.of(new BigDecimal("3.15")),
        List.of(new BigDecimal("4.68"))
    );

    BigDecimal result = this.service.calculate(point1, point2);
    BigDecimal expected = new BigDecimal("3.186989174754128342");

    assertThat(result).isNotNull();
    assertThat(result).isEqualByComparingTo(expected);
  }

  @Test
  void testCalculate_withTrailingZeros() {
    List<List<BigDecimal>> point1 = Arrays.asList(
        List.of(new BigDecimal("1.0")),
        List.of(new BigDecimal("2.0"))
    );
    List<List<BigDecimal>> point2 = Arrays.asList(
        List.of(new BigDecimal("1.0")),
        List.of(new BigDecimal("2.0"))
    );

    BigDecimal result = this.service.calculate(point1, point2);
    BigDecimal expected = BigDecimal.ZERO;

    assertThat(result).isNotNull();
    assertThat(result).isEqualByComparingTo(expected);
  }

}