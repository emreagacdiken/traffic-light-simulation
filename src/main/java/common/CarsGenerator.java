package common; // Kodun bulunduğu paket veya klasörü tanımlar, bu sınıf "common" paketinin bir parçasıdır

import java.awt.Color; // Araçların renk özelliklerini tanımlamak için Color sınıfı
import java.awt.Point; // Koordinatları temsil etmek için Point sınıfı
import java.util.ArrayList; // Dinamik dizi yapısı için ArrayList sınıfı
import java.util.Arrays; // Dizi işlemleri için Arrays sınıfı
import java.util.Random; // Rastgele sayı üretmek için Random sınıfı

import model.Car; // "model" paket veya klasöründeki "Car" sınıfı, her bir aracın davranış ve özelliklerini tanımlar
import view.Road; // "view" paket veya klasöründeki "Road" sınıfı, araçların hareket ettiği yolu temsil eder

public class CarsGenerator { // Araçların oluşturulmasından sorumlu sınıf

    private final int HEAVY_TRAFFIC = 3; // Yoğun trafik seviyesi 
    private final int MEDIUM_TRAFFIC = 2; // Orta trafik seviyesi 
    private final int LIGHT_TRAFFIC = 1; // Hafif trafik seviyesi

    // Araçların başlayacağı koordinatları tanımlayan sabit dizi.
    private final ArrayList<Point> STARTING_POINTS = new ArrayList<>(
            Arrays.asList(/*Kuzey*/ new Point(410, -30), new Point(460, -30), // Kuzey yönünden gelen araçlar 2 şerit
                    /*Doğu*/ new Point(950, 310), new Point(950, 360), new Point(950, 410), // Doğu yönünden gelen araçlar 3 şerit
                    /*Güney*/ new Point(510, 850), new Point(460, 850), // Güney yönünden gelen araçlar 2 şerit
                    /*Batı*/ new Point(-30, 510), new Point(-30, 460), new Point(-30, 410))); // Batı yönünden gelen araçlar 3 şerit
                    // Case'ler 0-1: Kuzey, 2-4: Doğu, 5-6: Güney, 7-9: Batı
    public CarsGenerator() {} // Boş yapıcı metot olup şeritlerin koordinatlarını tanımlar

    public ArrayList<Thread> genCars(int traffic, Road road) {
        // Belirtilen trafik yoğunluğuna göre araçlar oluşturur ve döndürür

        int n; // Üretilecek araç sayısı
        n = switch (traffic) {
            case HEAVY_TRAFFIC -> 30;
            case MEDIUM_TRAFFIC -> 20;
            case LIGHT_TRAFFIC -> 10;
            default -> 5;
        }; // Yoğun trafik için 30 araç
        // Orta trafik için 20 araç
        // Hafif trafik için 10 araç
        // Hiçbiri ise 5 araç.

        ArrayList<Thread> threads = new ArrayList<>(); // Her bir aracın Thread'ını tutacak dinamik dizi

        for (int i = 0; i < n; i++) {
            Random rand = new Random(); // Rastgele sayı
            int index = rand.nextInt(10); // 0 ile 9 arasında rastgele bir başlangıç noktası seçilir
            Point p = STARTING_POINTS.get(index); // Seçilen başlangıç noktasının koordinatları alınır

            switch (index) { // Seçilen başlangıç noktasına göre koordinatlar güncellenir
                case 0, 1 -> {
                    p.y = p.y - 50; // Kuzey yönünden gelen araçlar için y koordinatı azaltılır
                    STARTING_POINTS.remove(index); // Başlangıç noktası listesinden o nokta çıkarılır
                    STARTING_POINTS.add(index, p); // Güncellenmiş başlangıç noktası listeye eklenir
                }
                case 2, 3, 4 -> {
                    p.x = p.x + 50; // Doğu yönünden gelen araçlar için x koordinatı artırılır
                    STARTING_POINTS.remove(index); // Başlangıç noktası listesinden o nokta çıkarılır
                    STARTING_POINTS.add(index, p); // Güncellenmiş başlangıç noktası listeye eklenir
                }
                case 5, 6 -> {
                    p.y = p.y + 50; // Güney yönünden gelen araçlar için y koordinatı artırılır
                    STARTING_POINTS.remove(index); // Başlangıç noktası listesinden o nokta çıkarılır
                    STARTING_POINTS.add(index, p); // Güncellenmiş başlangıç noktası listeye eklenir
                }
                case 7, 8, 9 -> {
                    p.x = p.x - 0; // Batı yönünden gelen araçlar için x koordinatı azaltılır
                    STARTING_POINTS.remove(index); // Başlangıç noktası listesinden o nokta çıkarılır
                    STARTING_POINTS.add(index, p); // Güncellenmiş başlangıç noktası listeye eklenir
                }
                default -> {
                }
            }
            // Seçilen başlangıç noktasına göre koordinatlar güncellenir
            // Diğer durumlar için işlem yapılmaz.

            Car car = new Car(Color.WHITE, p.x, p.y, 40, 30, 15, index); // Yeni bir araç oluştur
            road.addCar(car); // Araç yola eklenir.

            Thread t = new Thread(car); // Her araç bir Thread'a atanır
            threads.add(t); // Thread listesine eklenir
        }
        
        return threads; // Oluşturulan Thread'lar döndürülür
    }
}
