package christmas;

import Service.OrderCalculator;
import Service.OrderResult;
import View.InputView;
import View.OutputView;

import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final Map<String, Integer> menuPrices = new HashMap<>();
    private static final Map<String, Integer> eventBadges = new HashMap<>();

    static {
        menuPrices.put("양송이수프", 6000);
        menuPrices.put("타파스", 5500);
        menuPrices.put("시저샐러드", 8000);
        menuPrices.put("티본스테이크", 55000);
        menuPrices.put("바비큐립", 54000);
        menuPrices.put("해산물파스타", 35000);
        menuPrices.put("크리스마스파스타", 25000);
        menuPrices.put("초코케이크", 15000);
        menuPrices.put("아이스크림", 5000);
        menuPrices.put("제로콜라", 3000);
        menuPrices.put("레드와인", 60000);
        menuPrices.put("샴페인", 25000);

        eventBadges.put("크리스마스 디데이 할인", 5000);
        eventBadges.put("평일 및 주말 할인", 3000);
    }

    public static void main(String[] args) {
        OrderCalculator orderCalculator = new OrderCalculator(menuPrices, eventBadges);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        // 사용자로부터 날짜와 주문 메뉴 입력 받기
        int visitDate = inputView.readDate();
        Map<String, Integer> orderMenu = inputView.readOrderMenu();

        // 주문 계산
        OrderResult orderResult = orderCalculator.calculateOrder(orderMenu, visitDate);

        // 주문 결과 출력
        outputView.printOrderDetails(orderResult);
    }

}
