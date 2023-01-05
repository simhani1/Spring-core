package hello.core.singleton;

public class StatefulService {

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }


//    public void order(String name, int price) {
//        System.out.println("name = " + name + " price = " + price);
//        this.price = price;
//    }

//    public int getPrice() {
//        return price;
//    }
}
