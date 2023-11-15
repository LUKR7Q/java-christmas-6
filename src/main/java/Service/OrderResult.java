package Service;

import java.util.Map;

public class OrderResult {
    private final Map<String, Integer> orderMenu;
    private final int totalBeforeDiscount;
    private final int totalBenefits;
    private final int totalAfterDiscount;
    private final Map<String, Integer> discountDetails;  // 할인에 대한 세부 정보
    private final String eventBadge;
    private final Map<String, Integer> freeItems;

    public OrderResult(Map<String, Integer> orderMenu, int totalBeforeDiscount, int totalBenefits,
                       int totalAfterDiscount, Map<String, Integer> discountDetails, String eventBadge,
                       Map<String, Integer> freeItems) {
        // 다른 생성자 부분...
        this.orderMenu = orderMenu;
        this.totalBeforeDiscount = totalBeforeDiscount;
        this.totalBenefits = totalBenefits;
        this.totalAfterDiscount = totalAfterDiscount;
        this.discountDetails = discountDetails;
        this.eventBadge = eventBadge;
        this.freeItems = freeItems;
    }

    // Getter 메서드들 추가

    public Map<String, Integer> getOrderMenu() {
        return orderMenu;
    }

    public int getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public int getTotalBenefits() {
        return totalBenefits;
    }

    public int getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public Map<String, Integer> getDiscountDetails() {
        return discountDetails;
    }

    public String getEventBadge() {
        return eventBadge;
    }

    public Map<String, Integer> getFreeItems() {
        return freeItems;
    }
}
