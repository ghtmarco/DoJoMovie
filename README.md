# DoJo Movie - Android Application

## 📱 Overview
DoJo Movie adalah aplikasi mobile Android untuk sistem pembelian film yang dirancang untuk memperluas basis pelanggan melalui platform mobile. Aplikasi ini menawarkan fitur autentikasi, katalog film, dan sistem pembelian yang terintegrasi dengan Google Maps untuk menunjukkan lokasi toko.

<div align="center">
  <img src="docs/screenshots/splash.png" width="200" alt="Splash Screen"/>
  <img src="docs/screenshots/login.png" width="200" alt="Login Screen"/> 
  <img src="docs/screenshots/home.png" width="200" alt="Home Screen"/>
</div>

## ✨ Features

- **Authentication System**
  - Login dengan nomor telepon dan password
  - Registrasi pengguna baru
  - Verifikasi OTP melalui SMS

- **Home Screen**
  - Peta lokasi toko dengan Google Maps
  - Daftar film dari JSON API
  - Navigasi ke detail film

- **Film Details**
  - Informasi lengkap film
  - Form pembelian dengan input quantity
  - Kalkulasi otomatis harga total

- **Transaction History**
  - Riwayat transaksi user
  - Filter berdasarkan user login

- **User Profile**
  - Display nomor telepon
  - Fungsi logout

## 🛠 Tech Stack

- **Language**: Kotlin
- **IDE**: Android Studio Jellyfish 2023.3.1.19
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: SQLite
- **Networking**: Volley
- **UI/UX**: Material Design
- **Maps**: Google Maps SDK
- **Image Loading**: Glide

## 📋 Requirements

- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)
- Android Studio Jellyfish 2023.3.1.19 or newer
- Google Maps API Key

## 🚀 Getting Started

1. **Clone repository**
   ```bash
   git clone https://github.com/ghtmarco/DoJoMovie.git
   cd DoJoMovie
   ```

2. **Setup Google Maps API**
   - Dapatkan API key dari [Google Cloud Console](https://console.cloud.google.com/)
   - Tambahkan API key di `app/src/main/AndroidManifest.xml`:
     ```xml
     <meta-data
         android:name="com.google.android.geo.API_KEY"
         android:value="YOUR_GOOGLE_MAPS_API_KEY" />
     ```

3. **Build dan Run**
   - Buka project di Android Studio
   - Sync project dengan Gradle
   - Build dan run aplikasi

## 📁 Project Structure

```
app/
├── src/main/
│   ├── java/com/dojomovie/app/
│   │   ├── activities/        # Activity classes
│   │   ├── fragments/         # Fragment classes
│   │   ├── adapters/          # RecyclerView adapters
│   │   ├── models/            # Data models
│   │   ├── database/          # Database helper and contract
│   │   ├── utils/             # Utility classes
│   │   └── DoJoMovieApplication.kt
│   ├── res/
│   │   ├── layout/            # XML layouts
│   │   ├── drawable/          # Image resources
│   │   ├── menu/              # Menu resources
│   │   └── values/            # Colors, strings, styles
│   └── AndroidManifest.xml
```

## 🎯 Key Components

### Activities
- `SplashActivity` - Splash screen dengan session check
- `LoginActivity` - Login dengan validasi
- `RegisterActivity` - Registrasi user dengan OTP
- `OTPActivity` - Verifikasi OTP
- `MainActivity` - Main container dengan bottom navigation
- `DetailFilmActivity` - Detail dan pembelian film
- `HistoryActivity` - Daftar transaksi

### Database Schema

#### Users Table
```sql
CREATE TABLE users (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    phone_number TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    otp_code TEXT,
    is_verified INTEGER DEFAULT 0
);
```

#### Films Table
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

#### Transactions Table
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

## 📡 API Integration

Film data diambil dari API JSON:
```
https://api.npoint.io/66cce8acb8f366d2a508
```

Format data:
```json
[
  {
    "id": 1,
    "title": "Film Title",
    "price": 50000,
    "cover": "https://example.com/cover.jpg",
    "description": "Film description",
    "genre": "Action",
    "duration": "120 min",
    "rating": 8.5
  }
]
```

## 🔐 Security

- Session management menggunakan SharedPreferences
- Input validation untuk semua form
- OTP verification untuk registrasi
- SQL injection prevention

## 📱 Screenshots

| Login | Home | Film Detail | History |
|-------|------|-------------|---------|
| <img src="docs/screenshots/login.png" width="200"/> | <img src="docs/screenshots/home.png" width="200"/> | <img src="docs/screenshots/detail.png" width="200"/> | <img src="docs/screenshots/history.png" width="200"/> |

## 🤝 Contributing

Proyek ini dibuat untuk keperluan tugas akademik. Pull requests dan suggestions welcome!

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

- **Your Name** - [GitHub Profile](https://github.com/ghtmarco)

## 📖 Documentation

Untuk dokumentasi lengkap, silakan baca [PROJECT_DOCUMENTATION.md](PROJECT_DOCUMENTATION.md)

---
*Dibuat dengan ❤️ untuk MOBI6006 - Mobile Programming*