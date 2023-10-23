import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// 메뉴
class Menu {
    public String name;
    public String description;

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

// 상품
class Product extends Menu {
    public int price;

    public Product(String name, String description, int price) {
        super(name, description);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}

// 주문
class Order {
    public ArrayList<Product> cart = new ArrayList<>();

    public void addToCart(Product product) {
        cart.add(product);
    }

    public void clearCart() {
        cart.clear();
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public int calculateTotal() {
        int total = 0;
        for (Product product : cart) {
            total += product.getPrice();
        }
        return total;
    }
}

public class Main {

    static List<Menu> menuList = new ArrayList<>();
    public static void main(String[] args) {
        Map<String, Product> menuMap = new HashMap<>();
        System.out.println("\"농심가락에 오신 것을 환영합니다.\"");
        System.out.println("아래에서 상품을 골라 주문해주세요.");
        menuList.add(new Menu("면류", "| 직접 뽑은 농심가락으로 만든 면요리"));
        menuList.add(new Menu("분식", "| 통가래떡이 들어간 떡볶이, 시원한 국물의 어묵"));
        menuList.add(new Menu("음료", "| 전통식혜와 탄산음료"));
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        Order order = new Order();
        
        ArrayList<Product> noodles = new ArrayList<>();
        noodles.add(new Product("농심떡라면", "\t| 쫄깃한 가래떡이 들어간 신라면", 5000));
        noodles.add(new Product("농심가락자장", "| 농심가락 생우동면으로 만든 자장면", 6000));
        noodles.add(new Product("농심가락어묵우동", "| 농심가락 생우동면과 어묵을 곁들인 우동", 7000));

        ArrayList<Product> snacks = new ArrayList<>();
        snacks.add(new Product("어묵", "\t\t\t| 시원한 국물이 일품인 어묵탕", 5000));
        snacks.add(new Product("떡볶이", "\t\t| 직접 뽑은 통가래떡으로 만든 매콤한 떡볶이", 6000));

        ArrayList<Product> drinks = new ArrayList<>();
        drinks.add(new Product("식혜", "\t\t\t| 전통방식으로 만든 달콤한 식혜", 3000));
        drinks.add(new Product("탄산", "\t\t\t| 콜라, 사이다, 환타 중 택 1", 2000));

        while (true) {
            System.out.println("메뉴");
            int menuNumber = 1;
            for (String menuName : menuMap.keySet()) {
                Product product = menuMap.get(menuName);
                System.out.println(menuNumber + ". " + product.getName() + " - " + product.getPrice() + "원");
                System.out.println("   " + product.getDescription());
                menuNumber++;
            }
            System.out.println();
            System.out.println(menuNumber + ". 주문하기");
            System.out.println((menuNumber + 1) + ". 취소하기");

            int choice = scanner.nextInt();
            if (choice >= 1 && choice <= menuMap.size()) {
                int itemNumber = 1;
                for (String itemName : menuMap.keySet()) {
                    if (itemNumber == choice) {
                        Product selectedProduct = menuMap.get(itemName);
                        System.out.println(selectedProduct.getName() + "을/를 장바구니에 추가하시겠습니까? (1. 네 / 2. 아니오)");
                        int addToCartChoice = scanner.nextInt();
                        if (addToCartChoice == 1) {
                            order.addToCart(selectedProduct);
                            System.out.println(selectedProduct.getName() + "이/가 장바구니에 추가되었습니다.");
                        }
                        break;
                    }
                    itemNumber++;
                }
            } else if (choice == menuNumber) {
                // 주문
                orderScreen(order);
            } else if (choice == menuNumber + 1) {
                // 취소
                System.out.println("주문을 취소하시겠습니까? (1. 네 / 2. 아니오)");
                int cancelChoice = scanner.nextInt();
                if (cancelChoice == 1) {
                    order.clearCart();
                    System.out.println("주문이 취소되었습니다.");
                }
                break;
            }
        }
    }

    public static void orderScreen(Order order) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("주문하기:");
            int itemNumber = 1;
            for (Product product : order.getCart()) {
                System.out.println(itemNumber + ". " + product.getName() + " - " + product.getPrice());
                itemNumber++;
            }
            int total = order.calculateTotal();
            System.out.println("합계: " + total + "원");

            System.out.println("1. 주문하기");
            System.out.println("2. 취소하기");
            int orderChoice = scanner.nextInt();

            if (orderChoice == 1) {
                System.out.println("주문이 완료되었습니다.");
                order.clearCart();
                try {
                    Thread.sleep(3000); // 3초 후 홈으로 이동
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            } else if (orderChoice == 2) {
                System.out.println("메뉴 화면으로 돌아갑니다.");
                return;
            }
        }
    }
}