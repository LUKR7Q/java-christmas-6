package View;

import Service.OrderResult;

import java.util.Map;

public class OutputView {
    public void printOrderDetails(OrderResult orderResult) {
        // 주문 내역 출력
        System.out.println("<주문 메뉴>");
        printMenu(orderResult.getOrderMenu());

        // 할인 전 총 주문 금액 출력
        System.out.println("\n<할인 전 총주문 금액>");
        System.out.println(orderResult.getTotalBeforeDiscount() + "원");

        // 증정 메뉴 출력
        System.out.println("\n<증정 메뉴>");
        printFreeItem(orderResult.getFreeItems());

        // 혜택 내역 출력
        System.out.println("\n<혜택 내역>");
        printDiscountDetails(orderResult.getDiscountDetails());

        // 총 혜택 금액 출력
        System.out.println("\n<총혜택 금액>");
        System.out.println(orderResult.getTotalBenefits() + "원");

        // 할인 후 예상 결제 금액 출력
        System.out.println("\n<할인 후 예상 결제 금액>");
        System.out.println(orderResult.getTotalAfterDiscount() + "원");

        // 이벤트 배지 출력
        System.out.println("\n<12월 이벤트 배지>");
        System.out.println(orderResult.getEventBadge());
    }

    private void printMenu(Map<String, Integer> menu) {
        for (Map.Entry<String, Integer> entry : menu.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue() + "개");
        }
    }

    private void printFreeItem(Map<String, Integer> freeItems) {
        if (freeItems.isEmpty()) {
            System.out.println("없음");
            return;
        }

        for (Map.Entry<String, Integer> entry : freeItems.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue() + "개");
        }
    }

    private void printDiscountDetails(Map<String, Integer> discountDetails) {
        if (discountDetails.isEmpty()) {
            System.out.println("없음");
            return;
        }

        for (Map.Entry<String, Integer> entry : discountDetails.entrySet()) {
            System.out.println(entry.getKey() + ": -" + entry.getValue() + "원");
        }
    }
}
