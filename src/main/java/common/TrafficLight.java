package common; // Kodun bulunduğu paket veya klasörü tanımlar, bu sınıf "common" paketinin bir parçasıdır

import java.awt.Color; // Trafik ışıklarının renklerini tanımlamak için Color sınıfı

public class TrafficLight implements Runnable { // Trafik ışıklarının çalışmasından sorumlu sınıf, Runnable arayüzünü uygular

    private final int id; // Trafik ışığının id'sini tutan değişken

    public TrafficLight(int id) { // Trafik ışığı oluşturucu metot, id parametresi alır
        this.id = id; // Trafik ışığının id'sini belirler
    }

    @Override 
    public void run() { // Trafik ışıklarının çalışmasını sağlayan metot (Thread'ın run metodu) 
        try {
            while (Util.goThreads) { // Trafik ışıklarının çalışmasını kontrol eden döngü
                if (id == 0) { // Eğer trafik ışığı id'si 0 ise, aşağıdaki sıralamayı uygular
                    Util.roadLight[id] = Color.GREEN; // Işık yeşil olur
                    Thread.sleep(6000); // Yeşil ışık 6 saniye açık kalır

                    Util.roadLight[id] = Color.YELLOW; // Işık sarı olur
                    Thread.sleep(2000); // Sarı ışık 2 saniye açık kalır

                    Util.roadLight[id] = Color.RED; // Işık kırmızı olur
                    Thread.sleep(8000); // Kırmızı ışık 8 saniye açık kalır

                    Util.roadLight[id] = Color.GREEN; // Işık tekrar yeşil olur
                    Thread.sleep(8000); // Yeşil ışık 8 saniye açık kalır
                }
                if (id == 1 || id == 5) { // Eğer trafik ışığı id'si 1 veya 5 ise, aşağıdaki sıralamayı uygular
                    Util.roadLight[id] = Color.GREEN; // Işık yeşil olur
                    Thread.sleep(6000); // Yeşil ışık 6 saniye açık kalır

                    Util.roadLight[id] = Color.YELLOW; // Işık sarı olur
                    Thread.sleep(2000); // Sarı ışık 2 saniye açık kalır

                    Util.roadLight[id] = Color.RED; // Işık kırmızı olur
                    Thread.sleep(16000); // Kırmızı ışık 16 saniye açık kalır
                }
                if (id == 2 || id == 3) { // Eğer trafik ışığı id'si 2 veya 3 ise, aşağıdaki sıralamayı uygular
                    Util.roadLight[id] = Color.RED; // Işık kırmızı olur
                    Thread.sleep(8000); // Kırmızı ışık 8 saniye açık kalır

                    Util.roadLight[id] = Color.GREEN; // Işık yeşil olur
                    Thread.sleep(6000); // Yeşil ışık 6 saniye açık kalır

                    Util.roadLight[id] = Color.YELLOW; // Işık sarı olur
                    Thread.sleep(2000); // Sarı ışık 2 saniye açık kalır

                    Util.roadLight[id] = Color.RED; // Işık tekrar kırmızı olur
                    Thread.sleep(8000); // Kırmızı ışık 8 saniye açık kalır
                }
                if (id == 4) { // Eğer trafik ışığı id'si 4 ise, aşağıdaki sıralamayı uygular
                    Util.roadLight[id] = Color.GREEN; // Işık yeşil olur
                    Thread.sleep(14000); // Yeşil ışık 14 saniye açık kalır

                    Util.roadLight[id] = Color.YELLOW; // Işık sarı olur
                    Thread.sleep(2000); // Sarı ışık 2 saniye açık kalır

                    Util.roadLight[id] = Color.RED; // Işık kırmızı olur
                    Thread.sleep(8000); // Kırmızı ışık 8 saniye açık kalır
                }
                if (id == 6 || id == 7) { // Eğer trafik ışığı id'si 6 veya 7 ise, aşağıdaki sıralamayı uygular
                    Util.roadLight[id] = Color.RED; // Işık kırmızı olur
                    Thread.sleep(16000); // Kırmızı ışık 16 saniye açık kalır

                    Util.roadLight[id] = Color.GREEN; // Işık yeşil olur
                    Thread.sleep(6000); // Yeşil ışık 6 saniye açık kalır

                    Util.roadLight[id] = Color.YELLOW; // Işık sarı olur
                    Thread.sleep(2000); // Sarı ışık 2 saniye açık kalır
                }
            }
        } catch (InterruptedException e) { // Thread'ın kesilmesi durumunda çalışacak kod bloğu
            e.printStackTrace(); // Hata mesajını yazdırır
        }
    }
}
