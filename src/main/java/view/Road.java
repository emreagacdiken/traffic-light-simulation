package view; // Kodun bulunduğu paket veya klasörü tanımlar, bu sınıf "view" paketinin bir parçasıdır

import java.awt.Color; // Renkler için gerekli sınıf
import java.awt.Graphics; // Grafik çizimleri için gerekli sınıf
import java.awt.image.BufferedImage; // Resim işlemleri için gerekli sınıf
import java.io.File; // Dosya işlemleri için gerekli sınıf
import java.io.IOException; // Hata işlemleri için gerekli sınıf
import java.util.ArrayList; // ArrayList sınıfı için gerekli sınıf

import javax.imageio.ImageIO;
import javax.swing.JPanel; // swing kütüphanesinden JPanel sınıfı

import common.Util;
import model.Car; 

public class Road extends JPanel { // Road sınıfı JPanel sınıfından türetilmiştir.

    private final int LANE_WIDTH = 50; // Şerit genişliği
    private final int LANE_LENGTH_EW = 400; // Doğu-Batı yönündeki şerit uzunluğu
    private final int LANE_LENGTH_NS = 300; // Kuzey-Güney yönündeki şerit uzunluğu

    
    private final ArrayList<Car> cars = new ArrayList<>(); // Araçları tutan liste

    
    public Road() { // Yapıcı metot, yol bileşeninin başlangıç ayarlarını yapar
        super(); // Üst sınıfın yapıcı metotunu çağırır
        this.setVisible(true); // Yolu görünür yapar
        this.setSize(950, 850); // Yolun boyutlarını ayarlar
    }

    public void printCars() { // Araçları ekrana yazdırmak için kullanılan metot
        for (int i = 1; i < cars.size(); i++) {
            System.out.println(i+". araç "+"( "+cars.get(i)+" )");
        }
    }

      
    public void addCar(Car car) { // Yola yeni bir araç ekler
        this.cars.add(car);
    }

    
    public boolean collision (Car vehicle) { // Araçların çarpışıp çarpışmadığını kontrol eden metot
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i); 
            if (!vehicle.equals(car)) { // Kendisiyle çarpışma kontrolü yapılmaz
                 
                if (vehicle.getY() == car.getY()) { // Aynı yatay eksendeki araçlar arasında çarpışma kontrolü
                    // Batı-Doğu veya Doğu-Batı Yönü
                    if (vehicle.getX() + vehicle.getWidth() >= car.getX() || car.getX() + car.getWidth() >= vehicle.getX()) {
                        return true;
                    }
                } 
                
                else if (vehicle.getX() == car.getX()) { // Aynı dikey eksendeki araçlar arasında çarpışma kontrolü
                    // Kuzey-Güney veya Güney-Kuzey Yönü
                    if (vehicle.getY() + vehicle.getHeight() >= car.getY() || car.getY() + car.getHeight() >= vehicle.getY()) {
                        return true;
                    }
                }
            }
        }
        return false; // Çarpışma yoksa false döner
    }

     
    private void paintCar(Graphics g, Car car) { // Araçları çizen metot
        g.setColor(car.getColor()); 
        g.drawRect(car.getX(), car.getY(), car.getWidth(), car.getHeight()); 
    }
    

    private void paintWest(Graphics g) { // Batı yönündeki yolu çizen metot

        g.setColor(Color.WHITE); // Yol rengini beyaz yapar
        for (int y = 300; y <= 550; y += LANE_WIDTH) { // Yolun şeritlerini çizer
            switch (y) { // Yolun farklı kısımlarına göre çizim yapar
                case 300, 550 -> g.fillRect(0, y, LANE_LENGTH_EW, 3); // Yolun kenar şeritlerini çizer
                case 400 -> g.fillRect(0, y, LANE_LENGTH_EW - 40, 3); // Yolun orta şeridini çizer
                default -> {
                    for (int x = 340; x >= 0; x -= 40) {
                        g.fillRect(x, y, 20, 3); // Yolun diğer şeritlerini çizer
                    }
                }
            }
            // Yolun farklı kısımlarına göre çizim yapar
                    }

        
        g.fillRect(360, 400, 7, 153); // Dur çizgisini çizer

         
        try { // Okları ekranda göstermek için resim dosyalarını okur
            BufferedImage img1 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\WtoE.png"));
            g.drawImage(img1, 310, 410, 40, 30, null);
            g.drawImage(img1, 310, 460, 40, 30, null);

            BufferedImage img2 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\WtoS.png"));
            g.drawImage(img2, 315, 510, 30, 30, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trafik ışıklarını çizer
        g.setColor(Util.roadLight[0]);
        g.fillOval(400, 510, 30, 30);

        g.setColor(Util.roadLight[1]);
        g.fillOval(400, 410, 30, 30);
        g.fillOval(400, 460, 30, 30);

    }

    private void paintEast(Graphics g) { // Doğu yönündeki yolu çizen metot

        g.setColor(Color.WHITE);
        for (int y = 300; y <= 550; y += LANE_WIDTH) {
            switch (y) {
                case 300, 550 -> g.fillRect(550, y, LANE_LENGTH_EW, 3);
                case 450 -> g.fillRect(590, y, LANE_LENGTH_EW - 40, 3);
                default -> {
                    for (int x = 590; x < 950; x += 40) {
                        g.fillRect(x, y, 20, 3);
                    }
                }
            }
        }

        
        g.fillRect(583, 300, 7, 153);

        
        try {
            BufferedImage img3 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\EtoW.png"));
            g.drawImage(img3, 600, 360, 40, 30, null);
            g.drawImage(img3, 600, 410, 40, 30, null);

            BufferedImage img4 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\EtoN.png"));
            g.drawImage(img4, 600, 310, 30, 30, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setColor(Util.roadLight[4]);
        g.fillOval(520, 310, 30, 30);

        g.setColor(Util.roadLight[5]);
        g.fillOval(520, 360, 30, 30);
        g.fillOval(520, 410, 30, 30);

    }

    private void paintNorth(Graphics g) { // Kuzey yönündeki yolu çizen metot

        g.setColor(Color.WHITE);
        for (int x = 400; x <= 550; x += LANE_WIDTH) {
            switch (x) {
                case 400, 550 -> g.fillRect(x, 0, 3, LANE_LENGTH_NS + 3);
                case 500 -> g.fillRect(x, 0, 3, LANE_LENGTH_NS - 40 + 3);
                default -> {
                    for (int y = 240; y >= 0; y -= 40) {
                        g.fillRect(x, y, 3, 20);
                    }
                }
            }
        }

        
        g.fillRect(400, 260, 103, 7);

        
        try {
            BufferedImage img7 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\NtoW.png"));
            g.drawImage(img7, 410, 220, 30, 30, null);

            BufferedImage img8 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\NtoSE.png"));
            g.drawImage(img8, 460, 220, 30, 30, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

       
        g.setColor(Util.roadLight[2]);
        g.fillOval(410, 300, 30, 30);

        g.setColor(Util.roadLight[3]);
        g.fillOval(460, 300, 30, 30);

    }

    private void paintSouth(Graphics g) { // Güney yönündeki yolu çizen metot

        g.setColor(Color.WHITE);
        for (int x = 400; x <= 550; x += LANE_WIDTH) {
            switch (x) {
                case 400, 550 -> g.fillRect(x, 550, 3, LANE_LENGTH_NS);
                case 450 -> g.fillRect(x, 590, 3, LANE_LENGTH_NS - 40);
                default -> {
                    for (int y = 590; y <= 850; y += 40) {
                        g.fillRect(x, y, 3, 20);
                    }
                }
            }
        }

        
        g.fillRect(450, 583, 100, 7);

        
        try {
            BufferedImage img5 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\StoE.png"));
            g.drawImage(img5, 515, 600, 30, 30, null);

            BufferedImage img6 = ImageIO.read(new File("C:\\Users\\Lenovo\\Desktop\\TrafficSimulation\\images\\StoNW.png"));
            g.drawImage(img6, 460, 600, 30, 30, null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        
        g.setColor(Util.roadLight[6]);
        g.fillOval(515, 515, 30, 30);

        g.setColor(Util.roadLight[7]);
        g.fillOval(460, 515, 30, 30);

    }

    
    private void paintCrosswalkWest(Graphics g) { // Batı yön için yaya geçidini çizen metot
        g.setColor(Util.crosswalkColor[0]); 
        for (int y = 310; y < 550; y += 20) { 
            g.fillRect(372, y, 23, 10);
        }
    }

    private void paintCrosswalkEast(Graphics g) { // Doğu yön için yaya geçidini çizen metot
        g.setColor(Util.crosswalkColor[1]);
        for (int y = 310; y < 550; y += 20) {
            g.fillRect(555, y, 23, 10);
        }
    }

    private void paintCrosswalkNorth(Graphics g) { // Kuzey yön için yaya geçidini çizen metot
        g.setColor(Util.crosswalkColor[2]);
        for (int x = 410; x < 550; x += 20) {
            g.fillRect(x, 272, 10, 23);
        }
    }

    private void paintCrosswalkSouth(Graphics g) { // Güney yön için yaya geçidini çizen metot
        g.setColor(Util.crosswalkColor[3]);
        for (int x = 410; x < 550; x += 20) {
            g.fillRect(x, 555, 10, 23);
        }
    }
     
    @Override // JPanel sınıfından gelen metot, yol bileşenini çizer
    public void paintComponent(Graphics g) { // Grafik çizimleri için kullanılan metot
        super.paintComponent(g); 

         // Arkaplanı siyah yapar.
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); 
        
        // Yollar ve yaya geçitleri çizilir.
        g.setColor(Color.WHITE);
        paintWest(g);
        paintEast(g);
        paintNorth(g);
        paintSouth(g);
        g.setColor(Color.WHITE);
        paintCrosswalkWest(g);
        paintCrosswalkEast(g);
        paintCrosswalkNorth(g);
        paintCrosswalkSouth(g);

        // Araçlar çizilir.
        for (Car c : cars) {
            paintCar(g, c);
        }
    }

}
