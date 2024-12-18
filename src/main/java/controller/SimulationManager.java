package controller; // Kodun bulunduğu paket veya klasörü tanımlar, bu sınıf "controller" paketinin bir parçasıdır

import java.awt.event.ActionEvent; // Kullanıcı etkileşimlerini temsil eden sınıf
import java.awt.event.ActionListener; // Kullanıcı etkileşimlerini dinleyen sınıf
import java.util.ArrayList; // Dinamik dizi yapısı için ArrayList sınıfı

import javax.swing.ButtonGroup; //Swing kütüphanesinden buton gruplarını temsil eden sınıf
import javax.swing.JButton; //Swing kütüphanesinden butonları temsil eden sınıf
import javax.swing.JFrame; //Swing kütüphanesinden pencere oluşturmayı sağlayan sınıf
import javax.swing.JLabel; //Swing kütüphanesinden etiketleri temsil eden sınıf
import javax.swing.JRadioButton; //Swing kütüphanesinden radyo butonları temsil eden sınıf

import common.CarsGenerator; 
import common.TrafficLight;
import common.Util;
import view.Road;

public class SimulationManager implements Runnable, ActionListener { // Simülasyon yöneticisi sınıfı, Runnable ve ActionListener arayüzlerini uygular 
    //(ActionListener butonlara tıklanma olaylarını işler ve Runnable simülasyonun ana döngüsünü çalıştırır)

    private final Road road; // Yolu temsil eder
    private final CarsGenerator carsGenerator; // Araç oluşturucuyu temsil eder
    private ArrayList<Thread> carsThreads; // Araç Thread'larını temsil eder

    private final JFrame frame; // Ana pencere.

    private final ButtonGroup trafficType; // Trafik türü seçimi
    private final JRadioButton heavy = new JRadioButton("Yoğun"); // Yoğun trafik seçeneği
    private final JRadioButton medium = new JRadioButton("Orta"); // Orta trafik seçeneği
    private final JRadioButton light = new JRadioButton("Hafif"); // Hafif trafik seçeneği
    private final JButton startSimulation = new JButton("Başlat"); // Simülasyonu başlatan buton
    private final JButton stopSimulation = new JButton("Durdur"); // Simülasyonu durduran buton

    boolean running; // Simülasyonun çalışıp çalışmadığını belirtir

    public SimulationManager() { // Simülasyon yöneticisi yapıcı metodu

        this.road = new Road(); // Yeni bir yol nesnesi oluşturur
        this.carsGenerator = new CarsGenerator(); // Yeni bir araç oluşturucu nesnesi oluşturur
        this.carsThreads = new ArrayList<>(); // Araç Thread'larını tutacak dinamik dizi oluşturur

        this.frame = new JFrame("Trafik Lambası Simülasyonu"); // Pencere başlığını ayarlar
        this.frame.setLayout(null); // Pencere düzenini sıfırlar
        this.frame.setSize(this.road.getWidth() + 120, 900); // Pencere boyutlarını ayarlar

        this.frame.add(road); // Yolu pencereye ekler.
        this.trafficType = new ButtonGroup(); // Trafik türü seçim grubu oluşturur
        this.trafficType.add(heavy); // Yoğun trafik seçeneğini gruba ekler
        this.trafficType.add(medium); // Orta trafik seçeneğini gruba ekler
        this.trafficType.add(light); // Hafif trafik seçeneğini gruba ekler
        this.light.setSelected(true); // Hafif trafik seçeneğini varsayılan olarak seçer
        heavy.setBounds(950, 150, 100, 20); // Yoğun trafik seçeneği için konum ve boyut ayarları
        medium.setBounds(950, 170, 100, 20); // Orta trafik seçeneği için konum ve boyut ayarları
        light.setBounds(950, 190, 100, 20); // Hafif trafik seçeneği için konum ve boyut ayarları        
        startSimulation.setBounds(960, 700, 80, 30); // Başlat butonunun konum ve boyut ayarları
        stopSimulation.setBounds(960, 750, 80, 30); // Durdur butonunun konum ve boyut ayarları
        JLabel trafficJLabel = new JLabel("Trafik Türü"); // Trafik türü seçimi için etiket oluşturur
        trafficJLabel.setBounds(960, 110, 80, 30); // Etiketin konum ve boyut ayarları

        this.frame.add(trafficJLabel); // Etiketi pencereye ekler
        this.frame.add(heavy); // Yoğun trafik seçeneğini pencereye ekler
        this.frame.add(medium); // Orta trafik seçeneğini pencereye ekler
        this.frame.add(light); // Hafif trafik seçeneğini pencereye ekler
        this.frame.add(startSimulation); // Başlat butonunu pencereye ekler
        this.frame.add(stopSimulation); // Durdur butonunu pencereye ekler

        initializeActionListeners(); //  Butonlar için action listener'ları başlatır.

        this.running = false; // Simülasyon başlangıçta çalışmıyor olarak ayarlı

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Pencerenin kapanışında programın sonlanmasını sağlar
        this.frame.setResizable(false); // Pencerenin yeniden boyutlandırılmasını engeller
        this.frame.setVisible(true); // Pencereyi görünür yapar.
        initializeActionListeners(); //  Butonlar için action listener'ları başlatır.
    }

   private void prepareLightThreads() { // Trafik ışıkları için Thread'ları hazırlar.
        for (int i = 0; i < Util.roadLight.length; i++) { // Tüm trafik ışıkları için Thread oluşturur
            TrafficLight tl = new TrafficLight(i); // Trafik ışığı oluşturur
            Thread t = new Thread(tl); // Trafik ışığı için bir Thread oluşturur
            t.start(); // Thread'ı başlatır
        }
    }
  
    private void initializeActionListeners() { // Butonlar için action listener'ları başlatır
        startSimulation.addActionListener(this); // Başlat butonu için action listener ekler
        stopSimulation.addActionListener(this); // Durdur butonu için action listener ekler
      }
 
    @Override
   public void run() { // Simülasyonun ana döngüsünü çalıştırır
        while (running) { // Simülasyon çalışıyorsa döngüyü çalıştırır
            this.road.repaint(); // Yolu yeniden çizer
            try {
                Thread.sleep(100); // döngüyü 100 milisaniye(0.1 sn) bekletir
            } catch (InterruptedException e) {
                e.printStackTrace(); // Hata durumunda hata mesajını yazdırır
            }
        }
    }

    @Override
   public void actionPerformed(ActionEvent actionEvent) { // Butonlara tıklanma olaylarını işler
        if (actionEvent.getSource().equals(startSimulation)) { // Başlat butonuna tıklanmışsa
            if (!running) { // Simülasyon çalışmıyorsa
                Util.goThreads = true; // Tüm Thread'lar aktif hale getirir
                prepareLightThreads(); // Trafik ışığı Thread'larını başlatır
                if (this.heavy.isSelected()) { // Yoğun trafik seçilmişse
                    this.carsThreads = this.carsGenerator.genCars(3, this.road); // Yoğun trafik için araçlar oluşturur
                } else if (this.medium.isSelected()) { // Orta trafik seçilmişse
                    this.carsThreads = this.carsGenerator.genCars(2, this.road); // Orta trafik için araçlar oluşturur
                } else if (this.light.isSelected()) { // Hafif trafik seçilmişse
                    this.carsThreads = this.carsGenerator.genCars(1, this.road); // Hafif trafik için araçlar oluşturur
                }
                running = true; // Simülasyonu başlatır
                road.printCars(); // Araçları ekrana yazdırır
                Thread t = new Thread(this); // Simülasyon için bir Thread oluşturur
                t.start(); // Thread'ı başlatır
                carsThreads.forEach(Thread::start); // Araç Thread'larını başlatır
            }
           // System.out.println("Start simulation");
        }  else if (actionEvent.getSource().equals(stopSimulation)) { // Durdur butonuna tıklanmışsa
            if (running) { // Simülasyon çalışıyorsa
                running = false; // Simülasyonu durdurur
                Util.goThreads = false; // Tüm Thread'ları durdurur
                System.out.println("------------------------------------");
                System.out.println("Simülasyon durdu");
                System.out.println("------------------------------------");
        }
        }
    }

   public void start() { // Simülasyonu başlatır
        this.frame.setVisible(true); // Pencereyi görünür yapar
    }

}
