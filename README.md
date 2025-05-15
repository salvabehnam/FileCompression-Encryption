# 🔐 Secure Text Compression System

A desktop Java application that securely compresses text files using **Huffman coding** and encrypts them using **AES encryption**. It provides a simple GUI to encode `.txt` files into compressed `.8z` files and decode them back using a password.

## 📌 Features

- **Lossless Compression** using custom Huffman coding  
- **Secure Encryption** using AES (Java Cryptography Extension)  
- **JavaFX GUI** for file selection and password input  
- **Compression Metrics**: Shows original size, compressed size, and compression rate  
- **Password-based Decryption** with validation and error handling  

## 📂 Project Structure

```
SecureTextCompression/
├── Main.java                  # JavaFX application entry point
├── Controller.java            # Handles user actions from the GUI
├── sample.fxml                # GUI layout (FXML format)
├── Crypto.java                # AES encryption/decryption logic
├── CryptoException.java       # Custom exception for encryption module
├── HuffmanEncode.java         # Encodes text using Huffman coding
├── HuffmanDecode.java         # Decodes Huffman encoded files
└── README.md                  # This file
```

## 🚀 How to Run

1. **Clone the Repository**  
   `git clone https://github.com/YOUR_USERNAME/SecureTextCompression.git`  
   `cd SecureTextCompression`

2. **Compile the Java Files**  
   `javac *.java`

3. **Run the Application**  
   `java Main`  
   _Note: You need to have JavaFX installed or configured if running outside of an IDE._

## 🎮 Usage Instructions

- **To Encode a File**:  
  - Select a `.txt` file using the "Encode" button.  
  - Enter a password (max 16 characters).  
  - The system compresses the text with Huffman coding and encrypts it using AES, saving as `.8z`.  

- **To Decode a File**:  
  - Select a `.8z` file using the "Decode" button.  
  - Enter the correct password used during encoding.  
  - If successful, the original `.txt` file is restored.  

## 🔐 Security Notes

- Uses **AES (Advanced Encryption Standard)** for symmetric encryption.  
- Key is generated via `keyGen()` to ensure it's padded to 16 bytes (128-bit key).  
- Decryption fails gracefully on incorrect password.  

## 📊 Compression Example

```
Original Size:      103200 bits  
Compressed Size:     54800 bits  
Compression Rate:      1.88x  
```

## ✨ Technologies Used

- **Java**
- **JavaFX**
- **AES Encryption (JCE)**
- **Custom Huffman Coding**
- **File I/O & Exception Handling**

## 🔧 Requirements

- Java 8 or higher  
- JavaFX installed (if running outside IDE)  

---

This project demonstrates real-world software development with encryption, compression, Java OOP, and user interface design. Ideal for secure text transmission or storage.
