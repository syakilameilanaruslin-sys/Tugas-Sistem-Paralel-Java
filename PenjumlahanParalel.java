import java.util.Random;

public class PenjumlahanParalel {

    public static void main(String[] args) throws InterruptedException {

        // Tentukan berapa banyak angka yang mau dijumlahkan
        int ukuran = 10_000_000; // 10 juta angka

        // Siapkan tempat untuk menyimpan angka-angka tersebut
        int[] angka = new int[ukuran];

        // Isi setiap slot dengan angka acak antara 0 sampai 99
        Random acak = new Random();
        for (int i = 0; i < ukuran; i++) {
            angka[i] = acak.nextInt(100);
        }

        // Cek berapa banyak "jalur kerja" (inti CPU) yang tersedia di komputer ini
        // Ibarat kasir supermarket - semakin banyak kasir, semakin cepat antrian selesai
        int jumlahThread = Runtime.getRuntime().availableProcessors();
        System.out.println("Jumlah inti CPU (jalur kerja): " + jumlahThread);

        // Bagi data menjadi beberapa potongan, masing-masing untuk satu jalur kerja
        int potongan = ukuran / jumlahThread;

        // Siapkan wadah untuk menyimpan hasil dari tiap jalur kerja
        long[] hasilBagian = new long[jumlahThread];

        // Buat dan jalankan semua jalur kerja (thread) secara bersamaan
        Thread[] threads = new Thread[jumlahThread];

        for (int i = 0; i < jumlahThread; i++) {
            // Tentukan bagian mana yang dikerjakan thread ini
            int awal  = i * potongan;
            int akhir = (i == jumlahThread - 1) ? ukuran : awal + potongan;
            int bagian = i; // simpan indeks supaya bisa dipakai di dalam lambda

            // Buat thread — isinya adalah instruksi pekerjaan yang akan dijalankan
            threads[i] = new Thread(() -> {
                long jumlah = 0;
                for (int j = awal; j < akhir; j++) {
                    jumlah += angka[j]; // jumlahkan bagian yang jadi tugas thread ini
                }
                hasilBagian[bagian] = jumlah; // simpan hasil bagian ini
            });

            threads[i].start(); // mulai jalankan thread (bekerja secara bersamaan)
        }

        // Catat waktu sebelum mengumpulkan hasil
        long mulai = System.nanoTime();

        // Tunggu semua thread selesai bekerja sebelum lanjut
        for (int i = 0; i < jumlahThread; i++) {
            threads[i].join(); // tunggu thread ke-i sampai selesai
        }

        // Gabungkan semua hasil bagian menjadi satu total akhir
        long total = 0;
        for (int i = 0; i < jumlahThread; i++) {
            total += hasilBagian[i]; // kumpulkan hasil dari tiap jalur kerja
        }

        // Catat waktu setelah semua selesai
        long selesai = System.nanoTime();

        // Hitung durasi dalam milidetik
        double durasi = (selesai - mulai) / 1_000_000.0;

        // Tampilkan hasilnya
        System.out.println("=== PENJUMLAHAN PARALEL ===");
        System.out.println("Jumlah data   : " + ukuran);
        System.out.println("Jumlah thread : " + jumlahThread);
        System.out.println("Total         : " + total);
        System.out.printf ("Waktu eksekusi: %.3f ms%n", durasi);
    }
}