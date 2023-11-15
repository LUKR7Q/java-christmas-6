package View;

import camp.nextstep.edu.missionutils.Console;

import java.util.HashMap;
import java.util.Map;

public class InputView {
    private static final String INPUT_VISIT_DATE = "\"12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!): \"";
    private static final String INPUT_MENU_DISH = "\"주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1): \"";
    private static final int MAX_ORDER_ITEMS = 20;

    private boolean isBeverageOnlyOrder(Map<String, Integer> orderMenu) {
        for (String menu : orderMenu.keySet()) {
            if (!isBeverage(menu)) {
                return false;
            }
        }
        return true;
    }

    // 해당 메뉴가 음료인지 확인하는 메서드
    private boolean isBeverage(String menu) {
        // 실제 음료 메뉴 이름에 맞게 수정하세요
        return menu.equals("제로콜라") || menu.equals("레드와인") || menu.equals("샴페인");
    }
    public int readDate() {
        int visitDate;
        while (true) {
            System.out.println(INPUT_VISIT_DATE);
            try {
                visitDate = Integer.parseInt(Console.readLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
                continue;
            }

            if (visitDate >= 1 && visitDate <= 31) {
                break;
            }

            System.out.println("[ERROR] 1 이상 31 이하의 숫자를 입력해 주세요.");
        }
        return visitDate;
    }

    public Map<String, Integer> readOrderMenu() {
        Map<String, Integer> orderMenu = new HashMap<>();
        int orderCount = 0;

        while (true) {
            try {
                if (orderCount >= MAX_ORDER_ITEMS) {
                    System.out.println("[ERROR] 한 번에 최대 20개까지만 주문할 수 있습니다. 다시 입력해 주세요.");
                    break;
                }

                System.out.println(INPUT_MENU_DISH);
                String input = Console.readLine();
                String[] menuItems = input.split(",");

                boolean validOrder = true;

                for (String menuItem : menuItems) {
                    String[] parts = menuItem.trim().split("-");
                    String menuName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);

                    if (!isValidQuantity(quantity)) {
                        System.out.println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
                        validOrder = false;
                        break;
                    }

                    orderMenu.put(menuName, orderMenu.getOrDefault(menuName, 0) + quantity);
                    orderCount += quantity; // 주문이 유효한 경우 주문 개수 증가
                }

                if (isBeverageOnlyOrder(orderMenu)) {
                    System.out.println("[ERROR] 음료만 주문 시, 주문할 수 없습니다.");
                    orderMenu.clear(); // 주문 내역을 비워줌
                    continue;
                }

                if (validOrder) {
                    if (orderCount > MAX_ORDER_ITEMS) {
                        // 이미 주문을 받은 경우에만 에러 메시지 출력
                        System.out.println("[ERROR] 한 번에 최대 20개까지만 주문할 수 있습니다. 초과된 주문은 무시됩니다.");
                        orderMenu.clear(); // 초과된 주문 내역을 비워줌
                    }
                    break;
                }

                System.out.println("[ERROR] 유효한 주문을 입력해 주세요.");
            } catch (Exception e) {
                System.out.println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
                break;
            }
        }
        return orderMenu;
    }


    private boolean isValidQuantity(int quantity) {
        return quantity >= 1;
    }
}
