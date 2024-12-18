package common; // Kodun bulunduğu paket veya klasörü tanımlar, bu sınıf "common" paketinin bir parçasıdır

import java.awt.Color; // Grafik nesnelerini temsil eden sınıflar Color ve Point gibi
import java.awt.Point; // Dinamik dizi yapısı için ArrayList sınıfı
import java.util.ArrayList; // Dizi işlemleri için Arrays sınıfı
import java.util.Arrays;

public class Util { // Genel amaçlı yardımcı sınıf

    // Yaya geçitlerinin renklerini tutan dizi
    public static Color crosswalkColor[] = {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
    
    public static Color roadLight[] = {Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};
    // Trafik ışıklarının renklerini tutan dizi ve TrafficLight.java'daki id'lerle aynı sırayla

    public static boolean goThreads = false; // Thread'ların çalışıp çalışmadığını kontrol eden değişken ve false olarak başlar 
    //çünkü başlangıçta ışıkların yanmaması gerekiyor

    // Trafik ışıklarının koordinatlarını tutan liste
    public static ArrayList<Point> koordinat = new ArrayList<>( // Koordinat listesine elemanlar eklenir
            Arrays.asList(/*Kuzey*/ new Point(410, 300), new Point(460, 300), // Kuzeydeki ışıkların koordinatları
                    /*Doğu*/ new Point(520, 310), new Point(520, 360), new Point(520, 410), // Doğudaki ışıkların koordinatları
                    /*Güney*/ new Point(515, 515), new Point(460, 515), // Güneydeki ışıkların koordinatları
                    /*Batı*/ new Point(400, 510), new Point(400, 460), new Point(400, 410))); // Batıdaki ışıkların koordinatları

    // Trafik ışıklarının rengini değiştiren metot
    public static void changeRoadLight(int index) {
        if (roadLight[index] == Color.RED) roadLight[index] = Color.GREEN; // Eğer ışık kırmızıysa, yeşil olarak değiştir.
        
        else if (roadLight[index] == Color.GREEN) roadLight[index] = Color.YELLOW; // Eğer ışık yeşilse, sarı olarak değiştir.

        else if (roadLight[index] == Color.YELLOW) roadLight[index] = Color.RED; // Eğer ışık sarıysa, kırmızı olarak değiştir.
    }
}
