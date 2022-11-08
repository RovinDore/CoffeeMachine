package javaMain;
import java.util.HashMap;
import java.util.Scanner;

public class CoffeeMachine {
    private int money;
    private int hasWater;
    private int hasMilk;
    private int hasBeans;
    private int hasCups;
    private boolean running;
    private final Scanner scan = new Scanner(System.in);

    CoffeeMachine(){
        this.money = 550;
        this.hasWater = 400;
        this.hasMilk = 540;
        this.hasBeans = 120;
        this.hasCups = 9;
        this.running = true;
    }

    public HashMap<String, Integer> getIngredients(String drinkType){
        HashMap<String, Integer> drinkIngredients = new HashMap<>();
        int water = 0, milk = 0, beans = 0, cost = 0, error = 0;
        switch (drinkType) {
            case "1" -> {
                water = 250;
                beans = 16;
                cost = 4;
            }
            case "2" -> {
                water = 350;
                milk = 75;
                beans = 20;
                cost = 7;
            }
            case "3" -> {
                water = 200;
                milk = 100;
                beans = 12;
                cost = 6;
            }
            default -> error = 1;
        }
        drinkIngredients.put("water", water);
        drinkIngredients.put("milk", milk);
        drinkIngredients.put("beans", beans);
        drinkIngredients.put("cost", cost);
        drinkIngredients.put("error", error);
        return drinkIngredients;
    }

    public void showInventory(){
        System.out.println("The coffee machine has:");
        System.out.println(this.hasWater + " ml of water");
        System.out.println(this.hasMilk + " ml of milk");
        System.out.println(this.hasBeans + " g of coffee beans");
        System.out.println(this.hasCups + " disposable cups");
        System.out.println("$" + this.money + " of money \n");
    }

    public void determineAction(){
        System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
        String action = scan.nextLine();
        switch (action) {
            case "buy" -> buyAction();
            case "fill" -> fillAction();
            case "take" -> takeAction();
            case "remaining" -> showInventory();
            case "exit" -> setRunning(false);
        }
    }

    public void buyAction(){
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String drinkType = scan.nextLine();
        if(drinkType.equals("back")){
            determineAction();
            return;
        }

        HashMap<String, Integer> ingredients = getIngredients(drinkType);
        if(ingredients.get("error") == 1){
            buyAction();
            return;
        }

        int milk = ingredients.get("milk");
        int water = ingredients.get("water");
        int beans = ingredients.get("beans");
        int cost = ingredients.get("cost");
        if(howManyCups(water, milk, beans) > 0){
            System.out.println("I have enough resources, making you a coffee!");
            setHasWater(getHasWater() - water);
            setHasBeans(getHasBeans() - beans);
            setHasMilk(getHasMilk() - milk);
            setHasCups(getHasCups() - 1);
            setMoney(getMoney() + cost);
        } else{
            System.out.println("Sorry, not enough!");
        }

    }

    public void takeAction(){
        System.out.println("I gave you $" + this.money + "\n");
        setMoney(0);
    }

    public static void main(String[] args) {
        CoffeeMachine main = new CoffeeMachine();
        while (main.isRunning()){
            main.determineAction();
        }
    }

    public int howManyCups(int water, int milk, int beans){
        if(getHasCups() == 0) return 0;

        int milkCups = milk > 0 ? getHasMilk() / milk : getHasMilk();
        int waterCups = water > 0 ? getHasWater() / water : getHasWater();
        int beansCups = beans > 0 ? getHasBeans() / beans : getHasBeans();
        int[] ingredients = {milkCups, waterCups, beansCups };

        if(beansCups < 0 || waterCups < 0 || milkCups < 0) {
            if(beansCups < 0) System.out.println("Sorry, not enough beans!");
            if(waterCups < 0) System.out.println("Sorry, not enough water!");
            if(milkCups < 0) System.out.println("Sorry, not enough milk!");
            return 0;
        }

        int lowest = 999999999;
        for(int ing: ingredients){
            if(ing < lowest) lowest = ing;
        }

        return lowest;
    }

    public void fillAction(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add: ");
        int inputWater = scan.nextInt();
        setHasWater(getHasWater() + inputWater);

        System.out.println("Write how many ml of milk you want to add: ");
        int inputMilk = scan.nextInt();
        setHasMilk(getHasMilk() + inputMilk);

        System.out.println("Write how many grams of coffee beans you want to add:");
        int inputBeans = scan.nextInt();
        setHasBeans(getHasBeans() + inputBeans);

        System.out.println("Write how many disposable cups of coffee you want to add: ");
        int inputCups = scan.nextInt();
        setHasCups(getHasCups() + inputCups);

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHasWater() {
        return hasWater;
    }

    public void setHasWater(int hasWater) {
        this.hasWater = hasWater;
    }

    public int getHasMilk() {
        return hasMilk;
    }

    public void setHasMilk(int hasMilk) {
        this.hasMilk = hasMilk;
    }

    public int getHasBeans() {
        return hasBeans;
    }

    public void setHasBeans(int hasBeans) {
        this.hasBeans = hasBeans;
    }

    public int getHasCups() {
        return hasCups;
    }

    public void setHasCups(int hasCups) {
        this.hasCups = hasCups;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
