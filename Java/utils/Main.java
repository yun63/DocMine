

public class Main {
    public static void main(String[] args) {
        WeightedRandomSelector<String> itemDrops = new WeightedRandomSelector<>();
        itemDrops.addEntry("10 Gold", 5.0);
        itemDrops.addEntry("Sword", 20.0);
        itemDrops.addEntry("Shield", 45.0);

        for (int i = 0; i < 20; i++) {
            System.out.println(itemDrops.getRandom());
        }
    }
}
