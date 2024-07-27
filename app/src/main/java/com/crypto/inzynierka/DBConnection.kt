package com.crypto.inzynierka

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBConnection(context: Context, dbname: String, dbversion: Int) : SQLiteOpenHelper(context, dbname, null, dbversion) {

    companion object {
        const val TABLE_NAME_QUESTIONS = "Questions"
        const val COL1_QUESTIONS = "ID"
        const val COL2_QUESTIONS = "Question"
        const val COL3_QUESTIONS = "Chapter"

        const val TABLE_NAME_ANSWERS = "Answers"
        const val COL1_ANSWERS = "ID"
        const val COL2_ANSWERS = "Answer"
        const val COL3_ANSWERS = "Question_ID"
        const val COL4_ANSWERS = "IsCorrect"

        const val TABLE_NAME_FLASHCARDS = "Flashcards"
        const val COL1_FLASHCARDS= "ID"
        const val COL2_FLASHCARDS = "Concept"
        const val COL3_FLASHCARDS = "Definition"
        const val COL4_FLASHCARDS = "Chapter"
        const val COL5_FLASHCARDS = "Line"

        const val TABLE_NAME_RESULTS = "Results"
        const val COL1_RESULTS= "ID"
        const val COL2_RESULTS = "Chapter"
        const val COL3_RESULTS = "Result"

        const val SQL_CREATE_QUESTIONS_TABLE = """
            CREATE TABLE $TABLE_NAME_QUESTIONS (
                $COL1_QUESTIONS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_QUESTIONS VARCHAR(255) NOT NULL,
                $COL3_QUESTIONS TEXT NOT NULL
            )
        """

        const val SQL_CREATE_ANSWERS_TABLE = """
            CREATE TABLE $TABLE_NAME_ANSWERS (
                $COL1_ANSWERS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_ANSWERS VARCHAR(255) NOT NULL,
                $COL3_ANSWERS INTEGER NOT NULL,
                $COL4_ANSWERS INTEGER NOT NULL,
                FOREIGN KEY ($COL3_ANSWERS) REFERENCES $TABLE_NAME_QUESTIONS($COL1_QUESTIONS)
            )
        """

        const val SQL_CREATE_FLASHCARD_TABLE = """
            CREATE TABLE $TABLE_NAME_FLASHCARDS (
                $COL1_FLASHCARDS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_FLASHCARDS VARCHAR(255) NOT NULL,
                $COL3_FLASHCARDS VARCHAR(255) NOT NULL,
                $COL4_FLASHCARDS VARCHAR(255) NOT NULL,
                $COL5_FLASHCARDS INTEGER NOT NULL
            )
        """

        const val SQL_CREATE_RESULTS_TABLE = """
            CREATE TABLE $TABLE_NAME_RESULTS (
                $COL1_RESULTS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_RESULTS VARCHAR(255) NOT NULL,
                $COL3_RESULTS INTEGR NOT NULL
            )
        """

        const val SQL_DELETE_QUESTIONS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_QUESTIONS"
        const val SQL_DELETE_ANSWERS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_ANSWERS"
        const val SQL_DELETE_FLASHCARDS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_FLASHCARDS"
        const val SQL_DELETE_RESULTS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_RESULTS"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_QUESTIONS_TABLE)
        db?.execSQL(SQL_CREATE_ANSWERS_TABLE)
        db?.execSQL(SQL_CREATE_FLASHCARD_TABLE)
        db?.execSQL(SQL_CREATE_RESULTS_TABLE)

        // Check if data already exists
        val cursor: Cursor? = db?.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME_QUESTIONS", null)
        cursor?.moveToFirst()
        val count = cursor?.getInt(0)
        cursor?.close()

        if (count == 0) {
            // Insert data into Questions
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Do czego kryptowaluty wykorzystują technologię kryptograficzną?', 'chapter11')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kto kontroluje i emituje kryptowaluty?', 'chapter11')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaka była pierwsza kryptowaluta?', 'chapter12')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kto ją stworzył?', 'chapter12')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są główne cechy kryptowalut?', 'chapter13')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co oznacza zdecentralizowanie kryptowalut?', 'chapter13')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czy kryptowaluty mają ograniczoną podaż?', 'chapter13')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Do czego można porównać blockchain?', 'chapter14')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co zawiera każdy blok?', 'chapter14')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W co są połączone bloki?', 'chapter14')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaki jesst jeden z podstaowych komponentów blockchainu?', 'chapter14')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co zapewnia decentralizacja?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Transakcje kryptowalutowe są zazwyczaj?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Na jaką skalę kryptowaluty umożliwiają szybkie przekazywanie wartości?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Które działania związane z kryptowalutami mogą mieć szczególnie niekorzystny wpływ na środowisko?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Z czym może być związana truność z adopcją kryptowalut?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czy wahania cen i regulacje prawne mogą odstraszać potencjalnych nowych pasjonatów kryptowalut?', 'chapter15')")


            // Insert data into Answers
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do zabezpieczenia transakcji', 1, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do przechowywania dużej ilości danych w chmurze', 1, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do szybszego przesyłania danych między serwerami', 1, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do tworzenia grafik komputerowych', 1, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Twórca', 2, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rząd', 2, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Banki', 2, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nikt', 2, 1)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Etherum', 3, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Litecoin', 3, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dogecoin', 3, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bitcoin', 3, 1)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Satoshi Nakamoto', 4, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Vitalik Buterin', 4, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Elon Musk', 4, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Charlie Lee', 4, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bezpieczeństwo', 5, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralizacja', 5, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Elastyczność', 5, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zmienność', 5, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one kontrolowane przez rząd', 6, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one kontrolowane przez banki', 6, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one kontrolowane przez twórców', 6, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one rozproszone na wiele komputerów', 6, 1)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tak', 7, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie', 7, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie wiem', 7, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Może', 7, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Księgi rachunkowej', 8, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rejestru katastralnego', 8, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Notatnika', 8, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tablicy wyników', 8, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Adresy', 9, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcje', 9, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nazwiska', 9, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Numer telefonu', 9, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci bloków', 10, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci komputerów', 10, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci neuronowe', 10, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci peer-to-peer', 10, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Hash poprzedniego bloku', 11, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Blok', 11, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Łańcuch', 11, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcja', 11, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Większą autonomię i niezależność', 12, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Większe koszty transakcji', 12, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Mniejszą autonomię i niezależność', 12, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak możliwości kupna kryptowalut', 12, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Niebezpieczne', 13, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Droższe niż tradycyjne metody płatności', 13, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tańsze niż tradycyjne metody płatności', 13, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bardzo niekorzystne w przypadku międzonarodowych przelewów', 13, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Lokalną', 14, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Globalną', 14, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Miejską', 14, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Krajową', 14, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Płatności', 15, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymiana', 15, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Trading ', 15, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kopanie', 15, 1)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ze zbyt dużą kontrolą płatności za ich pomocą', 16, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Z brakiem świadomości, złożoność technicza oraz niepeność rynku', 16, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ze zbyt małą wiedzą potrzeba na start', 16, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ze zbyt małym możliwym zyskiem', 16, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie, tylko wahania cen mogą być straszne', 17, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie, tylko regulacje prawne w określonych państwach mogą powodować brak dostępu', 17, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tak, obydwie cechy mogą odstraszać nowe osoby', 17, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie, żadna z tych cech nie może tego powodować', 17, 0)")

            //FLASHCARDS
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Krypto', 'Definicja', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Conceptaaaaaaaaaaaaaaaaaaaaaaa', 'Definicjaasdasd', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('cabiuuvqwev', 'D11111111111111111111111111111', 'chapter1',0)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_FLASHCARDS_TABLE)
        db?.execSQL(SQL_DELETE_ANSWERS_TABLE)
        db?.execSQL(SQL_DELETE_QUESTIONS_TABLE)
        db?.execSQL(SQL_DELETE_RESULTS_TABLE)
        onCreate(db)
    }
}
