package ru.vlabum.threads;

import org.jetbrains.annotations.NotNull;

public class Car implements Runnable {

    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private final Race race;

    private final int speed;

    private final String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(@NotNull final Race race, @NotNull final int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            App.cyclicBarrier.await(); // ждет остальные потоки, когда все будут готовы запустится
            App.countDownLatch.countDown(); // когда все будут готовы, основной поток продолжится
            synchronized (this) {
                wait(); // подождем главный поток
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        App.countDownLatchEnd.countDown(); // когда все закончат, продолжим главный поток
    }

}