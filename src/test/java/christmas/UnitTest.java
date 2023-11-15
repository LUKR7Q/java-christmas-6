package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

class UnitTest extends NsTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();


    @Test
    void 평일할인_테스트() {
        assertSimpleTest(() -> {
            runException("3", "티본스테이크-2,바비큐립-2,초코케이크-2,아이스크림-1,제로콜라-1");
        });
    }

    @Test
    void 주말할인_테스트() {
        assertSimpleTest(() -> {
            runException("7", "티본스테이크-2,바비큐립-2,초코케이크-2,아이스크림-1,제로콜라-1");
        });
    }

    @Test
    void 이벤트배지_없음_테스트() {
        assertSimpleTest(() -> {
            runException("3", "아이스크림-1,제로콜라-1");
        });
    }

    @Test
    void 이벤트배지_별_테스트() {
        assertSimpleTest(() -> {
            runException("3", "초코케이크-1,아이스크림-1,제로콜라-1");
        });
    }

    @Test
    void 이벤트배지_트리_테스트() {
        assertSimpleTest(() -> {
            runException("3", "아이스크림-4,제로콜라-1");
        });
    }

    @Test
    void 이벤트배지_산타_테스트() {
        assertSimpleTest(() -> {
            runException("3", "티본스테이크-2,바비큐립-2,초코케이크-2,아이스크림-1,제로콜라-1");
        });
    }

    @Test
    void 주문_수량_초과_테스트() {
        assertSimpleTest(() -> {
            runException("7", "티본스테이크-30,바비큐립-30,초코케이크-2,아이스크림-1,제로콜라-1");
            assertThat(output()).contains("[ERROR] 한 번에 최대 20개까지만 주문할 수 있습니다. 초과된 주문은 무시됩니다.");
        });
    }

    @Test
    void 음료만_주문_예외_테스트() {
        assertSimpleTest(() -> {
            runException("7", "제로콜라-1, 레드와인-1, 샴페인-1");
            assertThat(output()).contains("음료만 주문 시, 주문할 수 없습니다.");
        });
    }

    @Test
    void 이벤트_발생_날짜_범위_테스트() {
        assertSimpleTest(() -> {
            runException("32", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}