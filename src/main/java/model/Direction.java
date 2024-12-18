package model; // Kodun bulunduğu paket veya klasörü tanımlar, bu sınıf "model" paketinin bir parçasıdır

// Yönleri temsil eden bir enum sınıfı
// Bu enum, bir aracın trafik simülasyonu sırasında hangi yöne hareket ettiğini belirtmek içindir
public enum Direction {
    N_W,  // Kuzeyden batıya doğru hareket
    N_SE, // Kuzeyden güneydoğuya doğru hareket
    E_N,  // Doğudan kuzeye doğru hareket
    E_W,  // Doğudan batıya doğru hareket
    S_E,  // Güneyden doğuya doğru hareket
    S_NW, // Güneyden kuzeybatıya doğru hareket
    W_E,  // Batıdan doğuya doğru hareket
    W_S   // Batıdan güneye doğru hareket
}
