package ru.vlabum.threads;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Hello world!
 *
 */
public class App 
{

    public static final int CARS_COUNT = 4;

    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);

    public static final Semaphore semaphore = new Semaphore(CARS_COUNT/2);

    public static final CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);

    public static final CountDownLatch countDownLatchEnd = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        @NotNull final Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        @NotNull final Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            countDownLatch.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            for (final Car car : cars) {
                synchronized (car) {
                    car.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            countDownLatchEnd.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
