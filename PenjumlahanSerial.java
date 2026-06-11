import java.util.Random;

public class PenjumlahanSerial {

    public static void main(String[] args) {

        // Tentukan berapa banyak angka yang mau dijumlahkan
        int ukuran = 10_000_000; // 10 juta angka

        // Siapkan tempat untuk menyimpan angka-angka tersebut (seperti laci)
        int[] angka = new int[ukuran];

        // Isi setiap laci dengan angka acak antara 0 sampai 99
        Random acak = new Random();
        for (int i = 0; i < ukuran; i++) {
            angka[i] = acak.nextInt(100);
        }

        // Catat waktu sebelum mulai menjumlahkan
        long mulai = System.nanoTime();

        // Jumlahkan semua angka satu per satu dari awal sampai akhir
        long total = 0;
        for (int i = 0; i < ukuran; i++) {
            total += angka[i]; // tambahkan angka ke-i ke total
        }

        // Catat waktu setelah selesai menjumlahkan
        long selesai = System.nanoTime();

        // Hitung berapa lama prosesnya (dalam milidetik)
        double durasi = (selesai - mulai) / 1_000_000.0;

        // Tampilkan hasilnya
        System.out.println("=== PENJUMLAHAN SERIAL ===");
        System.out.println("Jumlah data   : " + ukuran);
        System.out.println("Total         : " + total);
        System.out.printf ("Waktu eksekusi: %.3f ms%n", durasi);
    }
}