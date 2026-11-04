# Marketing REST Client

Aplikasi Android berbasis Java untuk mengelola data marketing campaign melalui REST API. Aplikasi ini dibuat sebagai project Ujian Tengah Semester (UTS) Pemrograman Mobile Lanjutan.

## Fitur Aplikasi

- Login dan logout menggunakan autentikasi API.
- Menampilkan daftar marketing campaign.
- Menambahkan marketing campaign baru.
- Melihat detail marketing campaign.
- Mengambil dan mengirim data melalui REST API (GET dan POST).

## Teknologi yang Digunakan

- **Bahasa Pemrograman:** Java
- **Platform:** Android
- **Networking:** Retrofit
- **Arsitektur:** Model-View-ViewModel (MVVM)
- **Data Observation:** LiveData dan MutableLiveData
- **Design Pattern:** Repository Pattern
- **Backend:** Laravel REST API

## Struktur Arsitektur

Project menggunakan pola arsitektur MVVM dengan pembagian:

- **Model:** Menyimpan struktur data campaign dan respons login.
- **View:** Activity untuk menampilkan antarmuka dan menerima interaksi pengguna.
- **ViewModel:** Mengelola data dan menghubungkan View dengan Repository.
- **Repository:** Mengelola komunikasi data dengan REST API.
- **API Service:** Mendefinisikan endpoint API menggunakan Retrofit.

### 1. Halaman Login

<img width="350" height="1600" alt="Login" src="https://github.com/user-attachments/assets/a2bb8b55-55ba-423f-9c9b-5cf75dd88236" />


### 2. Halaman Daftar Campaign

<img width="350" height="1600" alt="Halaman Tampilan" src="https://github.com/user-attachments/assets/f4e9fe9a-8a11-4b64-971c-f53c3025f481" />


### 3. Halaman Tambah Campaign

<img width="350" height="1600" alt="Tambah" src="https://github.com/user-attachments/assets/33ae0bc2-59e4-43c9-879d-d714e0ceafb0" />


### 4. Halaman Detail Campaign

<img width="350" height="1600" alt="Halaman Detaill" src="https://github.com/user-attachments/assets/2d9820b2-e339-4cab-b324-bd92df171470" />

### 5. Halaman Logout

<img width="350" height="1600" alt="Logout" src="https://github.com/user-attachments/assets/67060e16-c625-48b8-bb26-9f5e59a84f0b" />


## Repository

**REST Client (Android):**

https://github.com/nelysalfiah-debug/marketing-rest-client

**REST Server (Laravel):**

https://github.com/nelysalfiah-debug/marketing-erp.git
