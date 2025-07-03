# BrailleAutocorrectSystem
Autocorrect system for the braille keyboard
# 🔠 Braille Autocorrect and Suggestion System

A JavaFX desktop application to decode Braille entered via QWERTY keyboard format and provide intelligent autocorrect suggestions using edit distance and dictionary-based frequency scoring. This tool simulates Braille typing without the need for specialized hardware.

---

## 🚀 How to Run the Application

### ✅ Prerequisites

- Java JDK 21 or 22
- JavaFX SDK 24.0.1
- IntelliJ IDEA (Community or Ultimate Edition)

### 📁 Project Structure
 ![image](https://github.com/user-attachments/assets/09f17819-20c2-4890-b6ac-3ce48a1b9126)


yaml
Copy code

---

### 🧩 JavaFX Setup in IntelliJ

1. **Download JavaFX SDK**:  
   [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)

2. **Configure VM Options in IntelliJ (Run → Edit Configurations)**:
--module-path "D:\path\to\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml

yaml
Copy code

3. Ensure `words.txt` exists at `src/main/resources/words.txt`.

4. Run the project by right-clicking `BrailleApp.java` and selecting `Run`.

---

## 🧠 Logic Used

### ✅ QWERTY Braille Mapping

This system maps standard Braille dots (1–6) to QWERTY keys:

| Braille Dot | QWERTY Key |
|-------------|-------------|
| Dot 1       | D           |
| Dot 2       | W           |
| Dot 3       | Q           |
| Dot 4       | K           |
| Dot 5       | O           |
| Dot 6       | P           |

Example:  
Typing `d` → dot 1 → character `a`  
Typing `d + k` → dots 1 & 4 → character `c`

---

### ✅ Decoding

- User presses **space** after every Braille character → instantly decoded.
- User types **period (`.`)** after each word → system decodes full word and suggests correction.

---

### ✅ Autocorrect System

Autocorrection uses:
- **Edit Distance** (Levenshtein logic using insert, delete, replace, swap)
- **Word Frequency** from `words.txt`
- **Weighted scoring** to prioritize:
- Exact-length matches
- Replacements over insertions/deletions
- Words with common prefixes

If a decoded word is not in the dictionary:
- It first looks for suggestions with the same length.
- If none are found, it provides closest matches based on edit distance and frequency.

---

## ✅ Sample Test Cases

| Input     | Decoded | Suggestion |
|-----------|---------|------------|
| hte.      | hte     | the        |
| caz.      | caz     | can        |
| thier.    | thier   | their      |
| thay.     | thay    | they       |
| dag.      | dag     | day        |
| bkow.     | bkow    | book       |
| teh.      | teh     | the        |
| knwo.     | knwo    | know       |

---

## 📂 Dictionary: `words.txt`

Contains over **10,000+** common English words (prepositions, verbs, nouns, etc.) compiled from public domain corpora.

Located at:
src/main/resources/words.txt


Output 
![Screenshot 2025-07-03 200305](https://github.com/user-attachments/assets/b95cc969-2430-4140-af27-b6ca5709bdd4)


---

## 🛠 Features & Improvements

- ✅ QWERTY Braille entry with live decoding
- ✅ Autocorrection with weighted suggestions
- ✅ Efficient lookup even for large dictionaries

### 🔜 Future Enhancements

- Add **learning mechanism** (smart suggestions over time)
- **Voice feedback** on decoded words
- **Multilingual support**

---

## 👩‍💻 Developed By

**Apoorwa**  

---
