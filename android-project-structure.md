# Android Project Structure

Ini adalah struktur proyek Android untuk aplikasi DoJo Movie:

```
DoJoMovie/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/dojomovie/app/
│   │   │   │   ├── activities/
│   │   │   │   │   ├── LoginActivity.kt
│   │   │   │   │   ├── RegisterActivity.kt
│   │   │   │   │   ├── OTPActivity.kt
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── DetailFilmActivity.kt
│   │   │   │   │   └── HistoryActivity.kt
│   │   │   │   │
│   │   │   │   ├── fragments/
│   │   │   │   │   ├── HomeFragment.kt
│   │   │   │   │   ├── HistoryFragment.kt
│   │   │   │   │   └── ProfileFragment.kt
│   │   │   │   │
│   │   │   │   ├── adapters/
│   │   │   │   │   ├── FilmAdapter.kt
│   │   │   │   │   └── TransactionAdapter.kt
│   │   │   │   │
│   │   │   │   ├── models/
│   │   │   │   │   ├── User.kt
│   │   │   │   │   ├── Film.kt
│   │   │   │   │   └── Transaction.kt
│   │   │   │   │\n│   │   │   │   ├── database/\n│   │   │   │   │   ├── DatabaseHelper.kt\n│   │   │   │   │   └── DatabaseContract.kt\n│   │   │   │   │\n│   │   │   │   └── utils/\n│   │   │   │       ├── SessionManager.kt\n│   │   │   │       ├── NetworkUtils.kt\n│   │   │   │       └── ValidationUtils.kt\n│   │   │   │\n│   │   │   ├── res/\n│   │   │   │   ├── layout/\n│   │   │   │   │   ├── activity_login.xml\n│   │   │   │   │   ├── activity_register.xml\n│   │   │   │   │   ├── activity_otp.xml\n│   │   │   │   │   ├── activity_main.xml\n│   │   │   │   │   ├── activity_detail_film.xml\n│   │   │   │   │   ├── activity_history.xml\n│   │   │   │   │   ├── fragment_home.xml\n│   │   │   │   │   ├── fragment_history.xml\n│   │   │   │   │   ├── fragment_profile.xml\n│   │   │   │   │   ├── item_film.xml\n│   │   │   │   │   └── item_transaction.xml\n│   │   │   │   │\n│   │   │   │   ├── drawable/\n│   │   │   │   ├── mipmap/\n│   │   │   │   └── values/\n│   │   │   │       ├── strings.xml\n│   │   │   │       ├── colors.xml\n│   │   │   │       ├── styles.xml\n│   │   │   │       └── dimens.xml\n│   │   │   │\n│   │   │   └── AndroidManifest.xml\n│   │   │\n│   │   └── test/\n│   │\n│   ├── build.gradle.kts\n│   └── proguard-rules.pro\n│\n├── build.gradle.kts\n├── settings.gradle.kts\nREADME.md\n```

## File dan Direktori Yang Sudah Dibuat

1. **README.md** - Deskripsi proyek utama
2. **android-project-structure.md** - Struktur proyek ini

## Langkah Selanjutnya

1. Membuat file build configuration (build.gradle.kts)
2. Membuat AndroidManifest.xml
3. Membuat activities dan fragments
4. Mengimplementasikan database helper
5. Membuat models dan adapters
6. Mengintegrasikan Google Maps dan Volley\n"