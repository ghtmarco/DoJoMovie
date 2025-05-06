# DoJo Movie - Project Documentation

## Project Overview
DoJo Movie adalah aplikasi mobile Android untuk sistem pembelian film yang dirancang sebagai solusi untuk memperluas basis pelanggan melalui platform mobile.

## Fitur Utama
1. **Authentication System**
   - Login dengan nomor telepon dan password
   - Registrasi pengguna baru
   - Verifikasi OTP melalui SMS
   
2. **Home Page**
   - Menampilkan peta lokasi toko DoJo Movie menggunakan Google Maps
   - Daftar film yang tersedia dari API JSON
   - Navigasi ke detail film
   
3. **Detail Film**
   - Menampilkan informasi lengkap film
   - Form pembelian dengan input quantity
   - Kalkulasi otomatis harga total
   
4. **Transaction History**
   - Melihat riwayat transaksi
   - Filter berdasarkan user yang login
   
5. **Profile**
   - Menampilkan nomor telepon user
   - Fungsi logout dengan konfirmasi

## Teknologi yang Digunakan

### Android Development
- **Bahasa**: Kotlin
- **Android Studio**: Jellyfish 2023.3.1.19
- **Target SDK**: API 34
- **Minimum SDK**: API 24

### Libraries
- **Google Maps SDK**: Integrasi peta
- **Volley**: Network requests untuk JSON API
- **Glide**: Image loading
- **Material Design**: UI components
- **RecyclerView**: Daftar film dan history
- **ViewBinding**: Type-safe view binding

### Database
- **SQLite**: Database lokal dengan helper class

## Struktur Proyek

```
DoJoMovie/
├── app/
│   ├── src/main/
│   │   ├── java/com/dojomovie/app/
│   │   │   ├── activities/
│   │   │   ├── fragments/
│   │   │   ├── adapters/
│   │   │   ├── models/
│   │   │   ├── database/
│   │   │   └── utils/
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── drawable/
│   │   │   ├── menu/
│   │   │   └── values/
│   │   └── AndroidManifest.xml
```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    phone_number TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    otp_code TEXT,
    is_verified INTEGER DEFAULT 0
);
```

### Films Table
```sql
CREATE TABLE films (
    _id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    price REAL NOT NULL,
    cover TEXT,
    description TEXT,
    genre TEXT,
    duration TEXT,
    rating REAL
);
```

### Transactions Table
```sql
CREATE TABLE transactions (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    film_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    total_price REAL NOT NULL,
    transaction_date TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(_id),
    FOREIGN KEY (film_id) REFERENCES films(_id)
);
```

## API Integration
- URL JSON API: `https://api.npoint.io/66cce8acb8f366d2a508`
- Data film diambil dari API dan disimpan ke database lokal
- Jika gagal fetch dari API, aplikasi akan memuat data dari database

## Validasi
1. **Phone Number**: Format nomor Indonesia
2. **Password**: Minimal 8 karakter
3. **OTP**: 6 digit angka
4. **Quantity**: Harus angka > 0

## Keamanan
- Password disimpan dalam bentuk plain text (dalam implementasi production sebaiknya di-hash)
- Session management menggunakan SharedPreferences
- Validasi input untuk mencegah injection

## Izin yang Diperlukan
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.SEND_SMS" />
```

## Cara Menjalankan Aplikasi
1. Clone repository
2. Buka dengan Android Studio Jellyfish 2023.3.1.19 atau newer
3. Tambahkan Google Maps API key di AndroidManifest.xml
4. Build dan run aplikasi

## Screenshots
*Screenshots akan ditambahkan setelah implementasi selesai*

## Future Improvements
1. Implementasi push notification
2. Payment gateway integration
3. User profile customization
4. Film rating dan review system
5. Firebase integration untuk analytics

## Contributors
- [Your Name] - Developer

## License
Proyek ini dibuat untuk keperluan tugas akademik.

---
*Dokumentasi ini dibuat untuk project MOBI6006 - Mobile Programming*