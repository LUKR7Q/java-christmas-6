package Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderCalculator {
    private final Map<String, Integer> menuPrices;
    private final Map<String, Integer> eventBadges;

    public OrderCalculator(Map<String, Integer> menuPrices, Map<String, Integer> eventBadges) {
        this.menuPrices = menuPrices;
        this.eventBadges = eventBadges;
    }

    public OrderResult calculateOrder(Map<String, Integer> orderMenu, int visitDate) {
        // 주문 메뉴 계산
        int totalBeforeDiscount = calculateTotalBeforeDiscount(orderMenu);

        // 주문 메뉴 총 금액이 10000원 미만인 경우 할인 적용하지 않음
        if (totalBeforeDiscount < 10000) {
            return new OrderResult(orderMenu, totalBeforeDiscount, 0, totalBeforeDiscount, new HashMap<>(), "없음", new HashMap<>());
        }

        // 평일 및 주말 할인 계산
        int weekdayWeekendDiscount = calculateWeekdayWeekendDiscount(orderMenu, visitDate);
        Map<String, Integer> discountDetails = new HashMap<>();
        String discountType = calculateWeekdayWeekendDiscountMessage(visitDate);
        discountDetails.put(discountType, weekdayWeekendDiscount);

        // 각종 이벤트 적용 및 할인에 대한 세부 정보 계산
        discountDetails.putAll(calculateDiscountDetails(visitDate, totalBeforeDiscount));

        // 무료 아이템 계산
        Map<String, Integer> freeItems = calculateFreeItems(orderMenu, totalBeforeDiscount);

        // 총 할인 금액 계산
        int totalBenefits = calculateTotalBenefits(discountDetails);

        // 총 할인에 따른 이벤트 배지 계산
        String eventBadge = calculateEventBadge(totalBenefits);

        // 할인 후 예상 결제 금액 계산
        int totalAfterDiscount = totalBeforeDiscount - totalBenefits;

        // OrderResult 객체 생성
        return new OrderResult(orderMenu, totalBeforeDiscount, totalBenefits, totalAfterDiscount, discountDetails, eventBadge, freeItems);
    }

    private String calculateWeekdayWeekendDiscountMessage(int visitDate) {
        int dayOfWeek = visitDate % 7;

        if (dayOfWeek >= 1 && dayOfWeek <= 5) {
            return "평일 할인";
        }

        if (dayOfWeek == 6 || dayOfWeek == 0) {
            return "주말 할인";
        }

        return "할인 없음";
    }

    private Map<String, Integer> calculateFreeItems(Map<String, Integer> orderMenu, int totalBeforeDiscount) {
        Map<String, Integer> freeItems = new HashMap<>();

        // 할인 전 총 주문 금액이 120000원 이상인 경우 샴페인 1개를 무료로 제공
        if (totalBeforeDiscount >= 120000) {
            freeItems.put("샴페인", 1);
        }

        return freeItems;
    }

    private int calculateTotalBeforeDiscount(Map<String, Integer> orderMenu) {
        int total = 0;
        for (Map.Entry<String, Integer> entry : orderMenu.entrySet()) {
            String menu = entry.getKey();
            int quantity = entry.getValue();
            int price = menuPrices.getOrDefault(menu, 0);
            total += price * quantity;
        }
        return total;
    }

    private int calculateWeekdayWeekendDiscount(Map<String, Integer> orderMenu, int visitDate) {
        int dayOfWeek = visitDate % 7;

        // 주문한 메뉴 중 디저트와 메인의 개수를 계산
        int dessertCount = orderMenu.getOrDefault("초코케이크", 0) +
                orderMenu.getOrDefault("아이스크림", 0);

        int mainCount = orderMenu.getOrDefault("티본스테이크", 0) +
                orderMenu.getOrDefault("바비큐립", 0) +
                orderMenu.getOrDefault("해산물파스타", 0) +
                orderMenu.getOrDefault("크리스마스파스타", 0);

        // 평일은 0(일요일)부터 4(목요일), 주말은 5(금요일)부터 6(토요일)
        if (dayOfWeek >= 1 && dayOfWeek <= 5) {
            // 주문한 디저트 메뉴가 있을 경우 평일 할인 적용
            return (dessertCount > 0) ? dessertCount * 2023 : 0;
        }

        if (dayOfWeek == 6 || dayOfWeek == 0) {
            // 주문한 메인 메뉴가 있을 경우 주말 할인 적용
            return (mainCount > 0) ? mainCount * 2023 : 0;
        }

        // 그 외의 경우 할인 없음
        return 0;
    }

    private Map<String, Integer> calculateDiscountDetails(int visitDate, int totalBeforeDiscount) {
        Map<String, Integer> discountDetails = new HashMap<>();

        // 크리스마스 디데이 할인 계산
        int christmasDiscount = calculateChristmasDiscount(visitDate);
        discountDetails.put("크리스마스 디데이 할인", christmasDiscount);

        // 특별 할인 계산
        int specialDiscount = calculateSpecialDiscount(visitDate);
        discountDetails.put("특별 할인", specialDiscount);

        // 증정 이벤트 계산
        int freeItemEvent = calculateFreeItemEvent(totalBeforeDiscount);
        discountDetails.put("증정 이벤트", freeItemEvent);

        return discountDetails;
    }

    private int calculateChristmasDiscount(int visitDate) {
        return (visitDate >= 1 && visitDate <= 25) ? 1000 + (visitDate - 1) * 100 : 0;
    }

    private int calculateSpecialDiscount(int visitDate) {
        LocalDate localDate = LocalDate.of(2023, 12, visitDate);

        // 2023년 12월 25일에는 별이 있음
        if (localDate.getDayOfMonth() == 25) {
            return 1000;
        }

        // 2023년 12월의 매주 일요일에도 별이 있음
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return 1000;
        }

        return 0;
    }

    private int calculateFreeItemEvent(int totalBeforeDiscount) {
        int champagnePrice = menuPrices.getOrDefault("샴페인", 0);
        return (totalBeforeDiscount >= 120000) ? champagnePrice : 0;
    }

    private int calculateTotalBenefits(Map<String, Integer> discountDetails) {
        int total = 0;
        for (int discount : discountDetails.values()) {
            total += discount;
        }
        return Math.max(total, 0);
    }

    private String calculateEventBadge(int totalBenefits) {
        return (totalBenefits >= 20000) ? "산타" :
                (totalBenefits >= 10000) ? "트리" :
                        (totalBenefits >= 5000) ? "별" : "없음";
    }
}