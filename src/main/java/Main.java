import controller.SimulationManager; // "controller" paket veya klasöründeki "SimulationManager" sınıfı, simülasyonu yöneten ana sınıfı temsil eder

public class Main { 
    
    public static void main(String[] args) { //
        System.out.println("------------------------------------");
        System.out.println("Simülasyon başladı"); 
        System.out.println("------------------------------------");
        SimulationManager simulation = new SimulationManager();  // Yeni bir simülasyon yöneticisi oluşturur
        simulation.start(); // Simülasyonu başlatır
    }

}