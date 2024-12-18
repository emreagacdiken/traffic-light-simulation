package model; // Kodun bulunduğu paket veya klasörü tanımlar, bu sınıf "model" paketinin bir parçasıdır

import java.awt.Color; // Renk özelliklerini tanımlamak için Color sınıfı
import java.util.Random; // Rastgele sayı üretmek için Random sınıfı

import common.Util; // Ortak özellikler ve metotlar için "common" paket veya klasöründeki "Util" sınıfı

public class Car implements Runnable { 

/* Araç sınıfı, her bir aracın davranış ve özelliklerini tanımlar ve Runnable arayüzünü uygular 
Çünkü araçlar Thread olarak çalışır ve hareket eder     
Thread sınıfını extend ederek kullanma yöntemi yerine Runnable arayüzünü implement ederek kullanmayı tercih etmemizin sebebi
Java’da çoklu kalıtım desteklenmediği için bir sınıfın sadece bir sınıfı extend eder yani genişletebiliriz.
Runnable arayüzü ise bir arayüz olduğu için sınıfımızın başka bir sınıfı extend etmesine engel olmaz */

    private final Color color; // Araç rengi
    private int x; // Araç x koordinatı
    private int y; // Araç y koordinatı
    private final int width; // Araç genişliği
    private final int height; // Araç yüksekliği
    private final int speed; // Araç hızı
    private final int lane; // Araç şeridi
    private Direction yon; // Araç yönü
    private int decision; // Karar değeri
    private int indeks; // Trafik ışığı indeksi
    private boolean proximity = false; // Yakınlık kontrolü
    private boolean passed = false; // Geçiş kontrolü

    // Ekran boyutları
    private final int SCREEN_WIDTH = 950; 
    private final int SCREEN_HEIGHT = 850;

    // Araç yapıcı metot
    public Car(Color color, int x, int y, int width, int height, int speed, int lane) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.lane = lane;

        // Şeride göre yön ve trafik ışığı indeksi ayarlanıyor
        switch (lane) {
            case 0 -> {
                this.yon = Direction.N_W; // Kuzeyden batıya yön
                this.indeks = 2;
            }
            case 1 -> {
                this.yon = Direction.N_SE; // Kuzeyden güneydoğuya yön
                this.indeks = 3;
            }
            case 2 -> {
                this.yon = Direction.E_N; // Doğudan kuzeye yön
                this.indeks = 4;
            }
            case 3 -> {
                this.yon = Direction.E_W; // Doğudan batıya yön
                this.indeks = 5;
            }
            case 4 -> {
                this.yon = Direction.E_W; // Doğudan batıya yön
                this.indeks = 5;
            }
            case 5 -> {
                this.yon = Direction.S_E; // Güneyden doğuya yön
                this.indeks = 6;
            }
            case 6 -> {
                this.yon = Direction.S_NW; // Güneyden kuzeybatıya yön
                this.indeks = 7;
            }
            case 7 -> {
                this.yon = Direction.W_S; // Batıdan güneye yön
                this.indeks = 0;
            }
            case 8 -> {
                this.yon = Direction.W_E; // Batıdan doğuya yön
                this.indeks = 1;
            }
            case 9 -> {
                this.yon = Direction.W_E; // Batıdan doğuya yön
                this.indeks = 1;
            }
            default -> {
            }
        }

        // Kuzeyden güneydoğuya veya güneyden kuzeybatıya yönlerinde rastgele karar verir
        if (yon == Direction.S_NW || yon == Direction.N_SE) {
            Random rand = new Random();
            this.decision = rand.nextInt(2);
        }
    }

    // Thread'ın run metodu
    @Override
    public void run() {
        while (Util.goThreads) { // Thread'lar çalışırken
            try {
                this.move(speed); // Araç hareketini çağır
                Thread.sleep(100); // Thread'ı 100 milisaniye (0.1 sn) uyut
                
                while (Util.roadLight[indeks] == Color.RED && !passed && proximity) { // Trafik ışığı kırmızıysa ve araç geçmediyse ve yakınsa
                    Thread.sleep(100); 
                }
            } catch (InterruptedException e) { // Thread kesinti durumunda
                e.printStackTrace(); // Hata mesajını yazdır
            }
        }
    }

    // Araç hareketi
    public void move(int speed) { // Hareket hızı parametre olarak alınır
        System.out.println("Thread "+this.lane + " - Yön " + this.yon +" = Koordinat ("+ this.x + ", " + this.y+")"); 
        if (this.isOnScreen()) { // Araç ekran içindeyse
            if (null != this.yon) switch (this.yon) { // Araç yönüne göre hareket
                case W_E -> {
                    // Batıdan doğuya hareket
                    if (this.getX() >= Util.koordinat.get(lane).x) { // Araç ışıkları geçtiyse
                        passed = true;
                    }
                    if (Math.abs(this.getX() - Util.koordinat.get(lane).x) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveWestEast(speed); // Batıdan doğuya hareket fonksiyonunu çağır
                }
                case W_S -> {
                    // Batıdan güneye hareket
                    if (this.getX() >= Util.koordinat.get(lane).x) { // Araç ışıkları geçtiyse
                        passed = true; 
                    }
                    if (Math.abs(this.getX() - Util.koordinat.get(lane).x) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveWestSouth(speed); // Batıdan güneye hareket fonksiyonunu çağır
                }
                case S_NW -> {
                    // Güneyden kuzeybatıya hareket
                    if (this.getY() <= Util.koordinat.get(lane).y) { // Araç ışıkları geçtiyse
                        passed = true;
                    }
                    if (Math.abs(this.getY() - Util.koordinat.get(lane).y) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveSouthNorthWest(speed); // Güneyden kuzeybatıya hareket fonksiyonunu çağır
                }
                case S_E -> {
                    // Güneyden doğuya hareket
                    if (this.getY() <= Util.koordinat.get(lane).y) { // Araç ışıkları geçtiyse
                        passed = true;
                    }
                    if (Math.abs(this.getY() - Util.koordinat.get(lane).y) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveSouthEast(speed); // Güneyden doğuya hareket fonksiyonunu çağır
                }
                case E_W -> {
                    // Doğudan batıya hareket
                    if (this.getX() <= Util.koordinat.get(lane).x) { // Araç ışıkları geçtiyse
                        passed = true;
                    }
                    if (Math.abs(this.getX() - Util.koordinat.get(lane).x) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveEastWest(speed); // Doğudan batıya hareket fonksiyonunu çağır
                }
                case E_N -> {
                    // Doğudan kuzeye hareket
                    if (this.getX() <= Util.koordinat.get(lane).x) { // Araç ışıkları geçtiyse
                        passed = true;
                    }
                    if (Math.abs(this.getX() - Util.koordinat.get(lane).x) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveEastNorth(speed); // Doğudan kuzeye hareket fonksiyonunu çağır
                }
                case N_SE -> {
                    // Kuzeyden güneydoğuya hareket
                    if (this.getY() >= Util.koordinat.get(lane).y) { // Araç ışıkları geçtiyse
                        passed = true;
                    }
                    if (Math.abs(this.getY() - Util.koordinat.get(lane).y) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveNorthSouthEast(speed); // Kuzeyden güneydoğuya hareket fonksiyonunu çağır
                }
                case N_W -> {
                    // Kuzeyden batıya hareket
                    if (this.getY() >= Util.koordinat.get(lane).y) { // Araç ışıkları geçtiyse
                        passed = true;
                    }
                    if (Math.abs(this.getY() - Util.koordinat.get(lane).y) < 100) { // Işıklara yakınsa
                        proximity = true;
                    }
                    moveNorthWest(speed); // Kuzeyden batıya hareket fonksiyonunu çağır
                }
                default -> {
                }
            }
            // Araç yönüne göre hareket
            
        }
    }

    // Ekran içinde mi kontrolü
    private boolean isOnScreen() { 
        return switch (lane) {
            case 0, 1 -> this.x <= SCREEN_WIDTH && this.y <= SCREEN_HEIGHT && this.x + this.height >= 0;
            case 2, 3, 4 -> this.x + this.width >= 0 && this.y <= SCREEN_HEIGHT && this.x + this.height >= 0;
            case 5, 6 -> this.x <= SCREEN_WIDTH && this.x + this.width >= 0 && this.x + this.height >= 0;
            default -> this.x <= SCREEN_WIDTH && this.y <= SCREEN_HEIGHT && this.x + this.height >= 0;
        }; // Şeride göre kontrol yapılır
        // Şerit 0 ve 1 için kontrol
        // Ekranın içindeyse true döndür
        // Şerit 2, 3, 4 için kontrol
        // Ekranın içindeyse true döndür
        // Şerit 5 ve 6 için kontrol
        // Ekranın içindeyse true döndür
        // Şerit 7, 8, 9 için kontrol
        // Ekranın içindeyse true döndür
    }

    // Araç hareket fonksiyonları
    private void moveWestEast(int speed) { // Batıdan doğuya hareket
        this.incX(speed); // Araç doğuya hareket eder
        if (this.getLane() == 9 && this.getX() >= 400 && this.getY() < 460) { 
            this.incY(speed - 5); // Şerit 9'da araç yukarı hareket eder
        } else if (this.getLane() == 8 && this.getX() >= 400 && this.getY() < 510) { 
            this.incY(speed - 5); // Şerit 8'de araç yukarı hareket eder
        }
    }

    private void moveWestSouth(int speed) { // Batıdan güneye hareket
        if (this.getX() >= 405) {
            this.incY(speed); // Araç güneye hareket eder
        } else {
            this.incX(speed); // Araç doğuya hareket eder
        }
    }

    private void moveSouthNorthWest(int speed) { // Güneyden kuzeybatıya hareket
        if (this.decision == 0) { 
            
            decY(speed); // Araç kuzeye hareket eder
            if (this.getY() < 450 && this.getX() < 510) {
                incX(speed - 5); // Araç doğuya hareket eder
            }
        } else { 
            
            if (this.getY() > 360) {
                decY(speed); // Araç kuzeye hareket eder
            } else {
                decX(speed); // Araç batıya hareket eder
            }
        }

    }

    private void moveSouthEast(int speed) { // Güneyden doğuya hareket
        if (this.getY() < 510) {
            incX(speed); // Araç doğuya hareket eder
        } else {
            decY(speed); // Araç kuzeye hareket eder
        }
    }

    private void moveEastWest(int speed) { // Doğudan batıya hareket
        this.decX(speed);
        if (this.getLane() == 4 && this.getX() <= 500 && this.getY() > 360){
            this.decY(speed - 5); // Şerit 4'te araç yukarı hareket eder
        } else if (this.getLane() == 3 && this.getX() <= 550 && this.getY() > 310) {
            this.decY(speed - 5); // Şerit 3'te araç yukarı hareket eder
        }
    }

    private void moveEastNorth(int speed) { // Doğudan kuzeye hareket
        if (this.getX() < 520) {
            decY(speed); // Araç kuzeye hareket eder
        } else {
            decX(speed); // Araç batıya hareket eder
        }
    }

    private void moveNorthSouthEast(int speed) { // Kuzeyden güneydoğuya hareket
        if (this.decision == 0) {
            
            incY(speed); // Araç güneye hareket eder
            if (this.getY() > 400 && this.getX() > 410) {
                decX(speed - 5); // Araç batıya hareket eder
            }
        } else {
            
            if (this.getY() < 460) {
                incY(speed); // Araç güneye hareket eder
            } else {
                incX(speed); // Araç doğuya hareket eder
            }
        }
    }

    private void moveNorthWest(int speed) { // Kuzeyden batıya hareket
        if (this.getY() > 300) {
            decX(speed); // Araç batıya hareket eder
        } else {
            incY(speed); // Araç güneye hareket eder
        }
    }
// Get ve Set metotları
    public Color getColor() { 
        return this.color; 
    }

    public int getLane() {
        return this.lane;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
 // Koordinatları arttırma ve azaltma metotları
    public void incX(int speed) {
        this.x += speed;
    }

    public void incY(int speed) {
        this.y += speed;
    }

    public void decX(int speed) {
        this.x -= speed;
    }

    public void decY(int speed) {
        this.y -= speed;
    }

}
