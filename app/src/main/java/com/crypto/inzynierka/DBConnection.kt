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

        const val TABLE_NAME_TESTS = "Tests"
        const val COL1_TESTS= "ID"
        const val COL2_TESTS = "Chapter"
        const val COL3_TESTS = "Test"
        const val COL4_TESTS = "Result"

        const val TABLE_NAME_LOGIN = "Login"
        const val COL1_LOGIN = "ID"
        const val COL2_LOGIN = "LoginDate"

        const val TABLE_NAME_NOTIFICATIONS = "Notifications"
        const val COL1_NOTIFICATIONS = "ID"
        const val COL2_NOTIFICATIONS = "Title"
        const val COL3_NOTIFICATIONS = "Content"

        const val TABLE_NAME_USERNAME = "Username"
        const val COL1_USERNAME = "ID"
        const val COL2_USERNAME = "Username"
        const val COL3_USERNAME = "Flag"

        const val TABLE_NAME_WALLETS = "Wallets"
        const val COL1_WALLETS = "ID"
        const val COL2_WALLETS = "Chapter"
        const val COL3_WALLETS = "isChecked"

        const val TABLE_NAME_DANGERS = "Dangers"
        const val COL1_DANGERS = "ID"
        const val COL2_DANGERS = "Chapter"
        const val COL3_DANGERS = "isChecked"



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

        const val SQL_CREATE_TESTS_TABLE = """
            CREATE TABLE $TABLE_NAME_TESTS (
                $COL1_TESTS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_TESTS VARCHAR(255) NOT NULL,
                $COL3_TESTS INTEGR NOT NULL,
                $COL4_TESTS INTEGR NOT NULL
            )
        """

        const val SQL_CREATE_LOGIN_TABLE = """
            CREATE TABLE $TABLE_NAME_LOGIN (
                $COL1_LOGIN INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_LOGIN VARCHAR(255) NOT NULL
            )
        """

        const val SQL_CREATE_NOTIFICATIONS_TABLE = """
            CREATE TABLE $TABLE_NAME_NOTIFICATIONS (
                $COL1_NOTIFICATIONS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_NOTIFICATIONS VARCHAR(255) NOT NULL,
                $COL3_NOTIFICATIONS VARCHAR(255) NOT NULL
            )
        """


        const val SQL_CREATE_USERNAME_TABLE = """
            CREATE TABLE $TABLE_NAME_USERNAME (
                $COL1_USERNAME INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_USERNAME VARCHAR(255) NOT NULL,
                $COL3_USERNAME INTEGR NOT NULL
            )
        """


        const val SQL_CREATE_WALLETS_TABLE = """
            CREATE TABLE $TABLE_NAME_WALLETS (
                $COL1_WALLETS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_WALLETS VARCHAR(255) NOT NULL,
                $COL3_WALLETS INTEGR NOT NULL
            )
        """


        const val SQL_CREATE_DANGERS_TABLE = """
            CREATE TABLE $TABLE_NAME_DANGERS (
                $COL1_DANGERS INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL2_DANGERS VARCHAR(255) NOT NULL,
                $COL3_DANGERS INTEGR NOT NULL
            )
        """




        const val SQL_DELETE_QUESTIONS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_QUESTIONS"
        const val SQL_DELETE_ANSWERS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_ANSWERS"
        const val SQL_DELETE_FLASHCARDS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_FLASHCARDS"
        const val SQL_DELETE_RESULTS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_RESULTS"
        const val SQL_DELETE_TESTS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_TESTS"
        const val SQL_DELETE_LOGIN_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_LOGIN"
        const val SQL_DELETE_NOTIFICATIONS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_NOTIFICATIONS"
        const val SQL_DELETE_USERNAME_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_USERNAME"
        const val SQL_DELETE_WALLETS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_WALLETS"
        const val SQL_DELETE_DANGERS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_DANGERS"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_QUESTIONS_TABLE)
        db?.execSQL(SQL_CREATE_ANSWERS_TABLE)
        db?.execSQL(SQL_CREATE_FLASHCARD_TABLE)
        db?.execSQL(SQL_CREATE_RESULTS_TABLE)
        db?.execSQL(SQL_CREATE_TESTS_TABLE)
        db?.execSQL(SQL_CREATE_LOGIN_TABLE)
        db?.execSQL(SQL_CREATE_NOTIFICATIONS_TABLE)
        db?.execSQL(SQL_CREATE_USERNAME_TABLE)
        db?.execSQL(SQL_CREATE_WALLETS_TABLE)
        db?.execSQL(SQL_CREATE_DANGERS_TABLE)

        // Check if data already exists
        val cursor: Cursor? = db?.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME_QUESTIONS", null)
        cursor?.moveToFirst()
        val count = cursor?.getInt(0)
        cursor?.close()

        if (count == 0) {
            // Insert data into Questions
            // Chapter 1_1
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Do czego kryptowaluty wykorzystują technologię kryptograficzną?', 'chapter11')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do zabezpieczenia transakcji', 1, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do przechowywania dużej ilości danych w chmurze', 1, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do szybszego przesyłania danych między serwerami', 1, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do tworzenia grafik komputerowych', 1, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kto kontroluje i emituje kryptowaluty?', 'chapter11')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Twórca', 2, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rząd', 2, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Banki', 2, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nikt', 2, 1)")

            // Chapter 1_2

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaka była pierwsza kryptowaluta?', 'chapter12')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Etherum', 3, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Litecoin', 3, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dogecoin', 3, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bitcoin', 3, 1)")


            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kto stworzył pierwszą kryptowalute?', 'chapter12')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Satoshi Nakamoto', 4, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Vitalik Buterin', 4, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Elon Musk', 4, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Charlie Lee', 4, 0)")

            // Chapter 1_3

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaka jest główna cecha kryptowalut?', 'chapter13')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bezpieczeństwo', 5, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralizacja', 5, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Elastyczność', 5, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zmienność', 5, 1)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co oznacza zdecentralizowanie kryptowalut?', 'chapter13')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one kontrolowane przez rząd', 6, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one kontrolowane przez banki', 6, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one kontrolowane przez twórców', 6, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Że są one rozproszone na wiele komputerów', 6, 1)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czy kryptowaluty mają ograniczoną podaż?', 'chapter13')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tak', 7, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie', 7, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie wiem', 7, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Może', 7, 0)")

            // Chapter 1_4

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Do czego można porównać blockchain?', 'chapter14')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Księgi rachunkowej', 8, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rejestru katastralnego', 8, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Notatnika', 8, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tablicy wyników', 8, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co zawiera każdy blok?', 'chapter14')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Adresy', 9, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcje', 9, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nazwiska', 9, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Numer telefonu', 9, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W co są połączone bloki?', 'chapter14')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci bloków', 10, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci komputerów', 10, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci neuronowe', 10, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W sieci peer-to-peer', 10, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaki jesst jeden z podstaowych komponentów blockchainu?', 'chapter14')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Hash poprzedniego bloku', 11, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Blok', 11, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Łańcuch', 11, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcja', 11, 0)")

            // Chapter 1_5

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co oznacza decentralizacja w kontekście kryptowalut?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kryptowaluty są kontrolowane przez jedną instytucję', 12, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kryptowaluty działają bez centralnego organu kontrolującego', 12, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kryptowaluty wymagają rządowego nadzoru', 12, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kryptowaluty są zależne od banków', 12, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są zalety niskich kosztów transakcji w kryptowalutach?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększenie opłat za przelewy międzynarodowe', 13, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Niższe koszty transakcji', 13, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wysokie prowizje w przypadku transakcji krajowych', 13, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększenie kosztów dostępu do rynku', 13, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego szybkość transakcji jest korzystna w przypadku kryptowalut?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pozwala na długotrwałe przechowywanie wartości', 14, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwia szybkie przekazywanie wartości na skalę globalną', 14, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sprawia, że transakcje są bardziej kosztowne', 14, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwia przeprowadzanie transakcji tylko w obrębie jednego kraju', 14, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie mechanizmy zapewniają bezpieczeństwo i prywatność w kryptowalutach?', 'chapter15')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Użycie tradycyjnych systemów bankowych', 15, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Mechanizmy kryptograficzne', 15, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowywanie danych w centralnych bazach', 15, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zgody rządów na publikację danych', 15, 0)")


            // Chapter 1_6

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie ryzyko wiąże się z wahaniami cen kryptowalut?', 'chapter16')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększenie stabilności rynków finansowych', 16, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wahania cen', 16, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczenie możliwości inwestycyjnych', 16, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększenie inwestycji w tradycyjne aktywa', 16, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie wyzwania prawne napotykają kryptowaluty?', 'chapter16')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kryptowaluty są powszechnie akceptowane na całym świecie', 17, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak jakichkolwiek regulacji prawnych', 17, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Różne wyzwania prawne i regulacyjne na różnych rynkach', 17, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększenie liczby rządowych regulacji na całym świecie', 17, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie zagrożenia związane z bezpieczeństwem istnieją w świecie kryptowalut?', 'chapter16')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Głównie tradycyjne problemy bankowe', 18, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ryzyko związane z cyberatakami, kradzieżami i oszustwami', 18, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak ryzyk związanych z bezpieczeństwem', 18, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczenie możliwości transakcji online', 18, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jak procesy związane z kryptowalutami wpływają na środowisko?', 'chapter16')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększają oszczędności energetyczne', 19, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Są neutralne dla środowiska', 19, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kopanie kryptowalut jest energochłonne i może negatywnie wpływać na środowisko naturalne', 19, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Poprawiają efektywność energetyczną sektora', 19, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie trudności napotykają kryptowaluty w codziennym życiu i w świecie biznesu?', 'chapter16')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększona akceptacja i szybka adopcja', 20, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Trudności związane z adopcją, zaufaniem i świadomością technologii', 20, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Powszechna znajomość kryptowalut w społeczeństwie', 20, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Szerokie wdrożenie technologii w biznesie', 20, 0)")


            // Chapter 1_7

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym charakteryzuje się aplikacja zdecentralizowana (DAPP)?', 'chapter17')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Działa na jednym centralnym serwerze', 21, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Działa na rozproszonej sieci komputerowej', 21, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jej kod i dane są przechowywane na jednym serwerze', 21, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest zależna od jednego centralnego podmiotu', 21, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie korzyści wynikają z używania aplikacji zdecentralizowanej (DAPP)?', 'chapter17')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Większa zależność od jednego serwera', 22, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak możliwości awarii sieci', 22, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Większa odporność na awarie i brak zależności od jednego centralnego podmiotu', 22, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczony dostęp do danych użytkowników', 22, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co zapewnia działanie DAPP, gdy jeden węzeł w sieci przestanie działać?', 'chapter17')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Aplikacja przestaje działać', 23, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Inne węzły przejmują zadania przestającego działać węzła', 23, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Cała sieć zostaje wyłączona', 23, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Aplikacja kontynuuje działanie tylko w obrębie lokalnego węzła', 23, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym różni się DAPP od scentralizowanej aplikacji?', 'chapter17')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('DAPP wymaga centralnego serwera do działania', 24, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Scentralizowane aplikacje działają na wielu węzłach w sieci', 24, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('DAPP działa na rozproszonej sieci komputerowej a nie zależy od serwera', 24, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Scentralizowane aplikacje są bardziej odporne na awarie', 24, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są zalety DAPP w porównaniu do scentralizowanych aplikacji?', 'chapter17')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zdecentralizowane aplikacje oferują mniejszą niezależność', 25, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('DAPP nie wymagają interwencji ze strony twórców czy właścicieli', 25, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('DAPP są bardziej podatne na ataki z zewnątrz', 25, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('DAPP są mniej odporne na awarie', 25, 0)")

            // Chapter 18

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest DAO (Zdecentralizowana Autonomiczna Organizacja)?', 'chapter18')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tradycyjnym modelem zarządzania organizacją', 26, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Koncepcją opartą na zaprogramowanych regułach, działającą bez centralnego zarządzania', 26, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Organizacją, która zależy od centralnego zarządzania', 26, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Firmą zarządzaną przez jednoosobowy zespół', 26, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie technologie umożliwiły rozwój DAO?', 'chapter18')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Systemy bankowe', 27, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Technologia blockchain i Bitcoin', 27, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wysokowydajne procesory', 27, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tradycyjne systemy zarządzania przedsiębiorstwem', 27, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co umożliwia DAO w kontekście zarządzania finansami i operacjami?', 'chapter18')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymaga pośredników do wykonania transakcji', 28, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Eliminuje pośredników w procesach finansowych i operacyjnych', 28, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Działa wyłącznie w ramach tradycyjnych struktur organizacyjnych', 28, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymaga centralnego organu do podejmowania decyzji', 28, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie było pierwsze podejście do DAO i co się stało z tym projektem?', 'chapter18')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pierwszy projekt DAO odniósł duży sukces', 29, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pierwszy projekt DAO z 2016 roku nie odniósł sukcesu', 29, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pierwszy projekt DAO działał na centralnym serwerze', 29, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pierwszy projekt DAO został całkowicie zablokowany przez rządy', 29, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W jakich aplikacjach technologia DAO znalazła swoje zastosowanie?', 'chapter18')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W tradycyjnych aplikacjach scentralizowanych', 30, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W zdecentralizowanych aplikacjach (DAPP) działających w sieci P2P', 30, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W aplikacjach bankowych', 30, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W aplikacjach do zarządzania firmami', 30, 0)")


            // Chapter 2
            // Chapter 2_1
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Którą realizację technologii blockchain stanowił Bitcoin?', 'chapter21')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pierwszą', 31, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Drugą', 31, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Trzecią', 31, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Czwartą', 31, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaki problem rozwiązał blockchain Bitcoina?', 'chapter21')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Podwójne wydawanie', 32, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wysokie koszty przelewów', 32, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Niską prędkość transakcji', 32, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralizację banków', 32, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego Bitcoin początkowo budził sceptycyzm?', 'chapter21')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ze względu na brak kontroli rządowej', 33, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Z powodu wysokiej inflacji', 33, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Z powodu małej liczby użytkowników', 33, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Z powodu braku szyfrowania', 33, 0)")

            // Chapter 2_2

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest blockchain?', 'chapter22')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rozproszoną księgą rachunkową', 34, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tradycyjną bazą danych', 34, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralnym bankiem danych', 34, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zbiorem protokołów sieciowych', 34, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie cechy odróżniają blockchain od tradycyjnych systemów?', 'chapter22')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przejrzystość, niezmienność, decentralizacja', 35, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wysoka scentralizowana kontrola', 35, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak dostępu dla użytkowników', 35, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Możliwość edycji zapisanych danych', 35, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego blockchain jest efektywny kosztowo?', 'chapter22')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ma niskie, ustalane przez nadawcę opłaty', 36, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymaga drogich serwerów', 36, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zależy od rządowych dotacji', 36, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymaga udziału pośredników', 36, 0)")

            // Chapter 2_3

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W jaki sposób blockchain zapewnia bezpieczeństwo transakcji?', 'chapter23')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Poprzez użycie kryptografii', 37, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przez scentralizowaną kontrolę', 37, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dzięki ochronie hasłem', 37, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przez autoryzację banków', 37, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest decentralizacja w kontekście blockchaina?', 'chapter23') ")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Równym rozłożeniem kontroli między użytkowników', 38, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralizacją danych w jednym punkcie', 38, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kontrolą jednej instytucji', 38, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczeniem dostępu do danych', 38, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są kluczowe elementy prywatności w blockchainie?', 'chapter23')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Publiczne klucze i podpisy cyfrowe', 39, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Anonimowość IP', 39, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Weryfikacja użytkownika w banku', 39, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczenie dostępu do węzłów', 39, 0)")

            // Chapter 2_4

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to jest sieć P2P?', 'chapter24')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sieć komputerów działających na równych zasadach', 40, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sieć scentralizowana', 40, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Hierarchiczna sieć dostawców', 40, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zbiór serwerów zarządzanych przez jedną firmę', 40, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jak węzły w sieci P2P przyczyniają się do działania blockchaina?', 'chapter24')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowują pełne kopie blockchaina', 41, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tylko wysyłają transakcje', 41, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie mają wpływu na działanie sieci', 41, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Działają jako serwery centralne ', 41, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to znaczy, że węzły w P2P mają „równy status”?', 'chapter24')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Żaden węzeł nie jest nadrzędny', 42, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wszyscy są administratorami', 42, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Każdy węzeł ma pełną kopię łańcucha', 42, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tylko wybrane węzły mogą zarządzać transakcjami', 42, 0)")

            // Chapter 2_5

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest konsensus w blockchainie?', 'chapter25')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ogólną zgodą węzłów na treść łańcucha ', 43, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jednomyślną decyzją użytkowników', 43, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Decyzją administratora', 43, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymogiem autoryzacji przez banki', 43, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W jaki sposób protokół konsensusu działa w blockchainie?', 'chapter25')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Węzły wspólnie zatwierdzają transakcje', 44, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Każdy węzeł decyduje indywidualnie', 44, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Administracja decyduje o aktualizacji', 44, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rząd ma ostateczną kontrolę nad blockchainem', 44, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego protokół konsensusu jest ważny?', 'chapter25')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapobiega centralizacji', 45, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększa liczbę użytkowników', 45, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwia szybsze transakcje', 45, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wspiera anonimowość', 45, 0)")

            // Chapter 2_6

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie jest główne zadanie kryptografii w blockchainie?', 'chapter26')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapewnia automatyczną synchronizację portfeli', 46, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zabezpiecza prywatność i integralność danych', 46, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ułatwia dostęp do sieci P2P', 46, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwia szybkie kopiowanie transakcji', 46, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaką funkcję pełni hashowanie w blockchainie?', 'chapter26')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kompresuje dane w celu zmniejszenia ich rozmiaru', 47, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sprawdza, czy użytkownik ma wystarczające środki', 47, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapewnia integralność danych', 47, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Szyfruje wiadomości między użytkownikami', 47, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Który algorytm jest powszechnie stosowany do hashowania w blockchainie?', 'chapter26')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('RSA', 48, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('DES', 48, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('SHA256', 48, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('AES', 48, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W czym pomaga podpis cyfrowy w blockchainie?', 'chapter26')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W przesyłaniu dużych plików w sieci P2P', 49, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W podpisywaniu transakcji, potwierdzając ich autentyczność i własność zasobów', 49, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W szyfrowaniu danych w bazie blockchain', 49, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W tworzeniu kopii zapasowych danych', 49, 0)")

            // Chapter 2_7

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest transakcja w blockchainie?', 'chapter27')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Procesem tworzenia nowego bloku w sieci', 50, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapytaniem o historię salda', 50, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przekazaniem informacji o transferze kryptowalut między użytkownikami', 50, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zatwierdzaniem nowej wersji blockchaina', 50, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym są niewydane transakcje wyjściowe (UTXO)?', 'chapter27')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcjami oczekującymi na zatwierdzenie', 51, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zasobami, które zostały przekazane do innych użytkowników', 51, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Aktywnymi monetami, które mogą zostać wydane przez właściciela', 51, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapisem wszystkich transakcji w blockchainie', 51, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie znaczenie mają UTXO w blockchainie?', 'chapter27')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwiają sprawdzenie całej historii transakcji', 52, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pozwalają użytkownikowi na wydanie kryptowaluty poprzez potwierdzenie własności', 52, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowują informacje o saldzie kont użytkowników', 52, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zabezpieczają transakcje przed modyfikacją', 52, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co dzieje się z UTXO po ich wydaniu?', 'chapter27')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Stają się STXO i nie są już aktywne', 53, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zostają podzielone na mniejsze UTXO', 53, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ulegają usunięciu z blockchaina', 53, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zostają zakodowane w formie nowego hashu', 53, 0)")

            // Chapter 2_8

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest Proof-of-Work (PoW)?', 'chapter28')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Algorytmem konsensusu wykorzystującym staking monet', 54, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Procesem losowego wybierania węzła do utworzenia nowego bloku', 54, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Algorytmem konsensusu polegającym na rozwiązywaniu złożonych zagadek kryptograficznych', 54, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Mechanizmem pozwalającym górnikom na przesyłanie monet', 54, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaką rolę pełni wartość nonce w PoW?', 'chapter28')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest kluczem prywatnym użytkownika', 55, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Służy do szyfrowania danych w bloku', 55, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest losową wartością, którą górnicy muszą znaleźć, aby spełnić wymagania trudności', 55, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest adresem do wysyłania transakcji', 55, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym różni się Proof-of-Stake (PoS) od Proof-of-Work?', 'chapter28')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('PoS nie wymaga zużycia dużej ilości energii do utworzenia bloku', 56, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('PoS wymaga większej mocy obliczeniowej niż PoW', 56, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W PoS bloki tworzą jedynie nowe węzły', 56, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W PoS bloki tworzone są tylko w centralnych punktach sieci', 56, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kiedy użytkownik w PoS ma większą szansę na utworzenie bloku?', 'chapter28')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy rozwiąże zagadkę kryptograficzną', 57, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy stawia większą liczbę monet w sieci', 57, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy znajdzie nowy hash', 57, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy posiada więcej węzłów w sieci', 57, 0)")


            // Chapter 3
            // Chapter 3_1
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kiedy wprowadzono Bitcoina?', 'chapter31')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 2009 roku', 58, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 2005 roku', 58, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 2012 roku', 58, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 2015 roku', 58, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Na czym opiera się działanie sieci Bitcoina?', 'chapter31')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Na zdecentralizowanej sieci peer-to-peer', 59, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Na centralnym systemie bankowym', 59, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Na prywatnych serwerach twórcy', 59, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Na transakcjach gotówkowych', 59, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego Bitcoin nazywany jest cyfrowym złotem?', 'chapter31')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ma ograniczoną podaż 21 milionów monet', 60, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest wymieniany na złoto w bankach', 60, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jego wartość jest ustalana przez rządy', 60, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest dostępny tylko dla inwestorów', 60, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co gwarantuje przejrzystość transakcji w sieci Bitcoina?', 'chapter31')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Blockchain', 61, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Banki centralne', 61, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Satoshi Nakamoto', 61, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Algorytm szyfrowania', 61, 0)")

            // Chapter 3_2
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kto zainspirował powstanie Bitcoina w latach 90.?', 'chapter32')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wei Dai z projektem „b-money” i Hal Finney', 62, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Elon Musk i jego innowacje technologiczne', 62, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Międzynarodowy Fundusz Walutowy', 62, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bank Światowy', 62, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kiedy opublikowano manifest Bitcoina?', 'chapter32')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 2008 roku', 63, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 2009 roku', 63, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 2010 roku', 63, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W 1998 roku', 63, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co przedstawiał dokument Bitcoin: A Peer-to-Peer Electronic Cash System?', 'chapter32')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Koncepcję kryptowaluty opartej na blockchainie i mechanizmie Proof of Work', 64, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Podręcznik obsługi giełdy kryptowalut', 64, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Regulacje prawne dotyczące kryptowalut', 64, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Protokół do przesyłania danych w sieci', 64, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co wydarzyło się podczas pierwszej transakcji Bitcoina w 2010 roku?', 'chapter32')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zakupiono dwie pizze za 10 000 bitcoinów', 65, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bitcoin został wymieniony na złoto', 65, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sprzedano akcje za kryptowalutę', 65, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Uruchomiono pierwszą giełdę kryptowalut', 65, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym stał się Bitcoin w dzisiejszych czasach?', 'chapter32')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Najpopularniejszą i najbardziej rozpoznawalną kryptowalutą na świecie', 66, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wyłącznie narzędziem dla przestępców', 66, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jedyną używaną kryptowalutą na giełdach', 66, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Systemem całkowicie kontrolowanym przez rządy', 66, 0)")

            // Chapter3_3
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kim jest Satoshi Nakamoto?', 'chapter33')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Twórcą Bitcoina, którego prawdziwa tożsamość jest nieznana', 67, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Japońskim politykiem odpowiedzialnym za waluty cyfrowe', 67, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Międzynarodowym zespołem programistów', 67, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Firmą zajmującą się kryptowalutami', 67, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest biała księga (white paper) podpisana przez Nakamoto?', 'chapter33')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dokumentem opisującym teoretyczne podstawy Bitcoina', 68, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Aktem prawnym dotyczącym kryptowalut', 68, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Księgą zawierającą wszystkie transakcje Bitcoina', 68, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Oficjalnym raportem banku centralnego', 68, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego tożsamość Satoshi Nakamoto jest tajemnicą?', 'chapter33')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie wiadomo, czy to jedna osoba czy zespół ludzi', 69, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jego dane zostały opublikowane w blockchainie', 69, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wszystkie informacje o nim są w publicznych rejestrach', 69, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nakamoto ujawnił swoją tożsamość, ale nie została zaakceptowana', 69, 0)")

            // Chapter3_4
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie jest główne założenie Bitcoina w kontekście systemu finansowego?', 'chapter34')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Działa bez pośredników, takich jak banki czy rządy', 70, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest kontrolowany przez rządy', 70, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Opiera się na tradycyjnych systemach płatności', 70, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest używany wyłącznie w e-commerce', 70, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego Bitcoin jest szczególnie popularny w krajach z wysoką inflacją?', '4')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Chroni majątek przed utratą wartości', 71, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest łatwiejszy do zdobycia niż gotówka', 71, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest wspierany przez lokalne banki', 71, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gwarantuje stałą wartość w dolarach', 71, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Do czego porównywana jest ograniczona podaż Bitcoina?', 'chapter34')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do „cyfrowego złota”', 72, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do tradycyjnych obligacji', 72, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do systemu bankowego', 72, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do papierowych pieniędzy', 72, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaką rolę pełni Bitcoin jako aktywo inwestycyjne?', 'chapter34')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Element dywersyfikacji portfela', 73, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gwarant stabilnej ceny', 73, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Waluta wyłącznie do codziennych transakcji', 73, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Produkt oferowany jedynie przez banki', 73, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jak Bitcoin wpływa na rozwój innowacyjnych usług finansowych?', 'chapter34')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Napędza rozwój aplikacji kryptowalutowych i platform płatniczych', 74, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zastępuje tradycyjne banki', 74, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest używany głównie w dużych korporacjach', 74, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Został zakazany w globalnym systemie finansowym', 74, 0)")

            // Chapter 3_5
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co jest pierwszym krokiem w procesie zakupu Bitcoina?', 'chapter35')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przeprowadzanie procesu KYC', 75, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wybranie giełdy kryptowalutowej', 75, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zasilenie konta bankowego', 75, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transfer Bitcoinów do prywatnego portfela', 75, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Który sposób zakupu Bitcoina pozwala na określenie preferowanej ceny zakupu?', 'chapter35')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zlecenie rynkowe', 76, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zlecenie limitowane', 76, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przelew bankowy', 76, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kupno za pomocą karty kredytowej', 76, 0)")

            // Chapter 3_6
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to jest blockchain i jaką pełni rolę w wydobywaniu bitcoinów?', 'chapter36')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest to baza danych przechowująca wszystkie transakcje w sieci', 77, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('To sprzęt używany do wydobywania kryptowalut', 77, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Mechanizm generujący koszty dla górników', 77, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Oprogramowanie służące do przechowywania Bitcoinów', 77, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co zawiera każdy blok w blockchainie?', 'chapter36')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcje dokonane w sieci', 78, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Informacje o sprzęcie górników', 78, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kalkulacje opłacalności wydobycia', 78, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dane osobowe użytkowników', 78, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaką funkcję pełnią górnicy w sieci Bitcoina?', 'chapter36')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapewniają bezpieczeństwo blockchaina i synchronizację sieci', 79, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowują prywatne klucze użytkowników', 79, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wykonują transakcje bankowe', 79, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Decydują o cenie Bitcoina', 79, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są główne koszty związane z wydobywaniem bitcoinów?', 'chapter36')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zakup sprzętu i zużycie energii elektrycznej', 80, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Podatki nakładane przez rząd', 80, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wynajem przestrzeni w blockchainie', 80, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zakup gotowych bloków transakcji', 80, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego warto używać kalkulatora opłacalności wydobycia?', 'chapter36')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Aby ocenić, czy koszty wydobycia są niższe niż wartość wydobytych monet', 81, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do obliczenia zużycia pamięci na komputerze', 81, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Aby sprawdzić kompatybilność sprzętu z blockchainem', 81, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Do szacowania ilości wydobywanych bloków', 81, 0)")

            // Chapter3_7
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest wydobywanie w chmurze?', 'chapter3_7')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Procesem wydobywania kryptowalut przy użyciu wynajmowanej mocy obliczeniowej', 82, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Instalowaniem oprogramowania górniczego na komputerze domowym', 82, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Metodą przechowywania Bitcoinów w portfelu online', 82, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Technologią umożliwiającą wydobywanie wyłącznie na komputerach stacjonarnych', 82, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są zalety wydobywania w chmurze?', 'chapter3_7')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak konieczności inwestowania w sprzęt i niskie koszty energii', 83, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Stałe zarobki niezależnie od opłacalności wydobycia', 83, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gwarantowana wygrana w sieci Bitcoin', 83, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Niezależność od kursu kryptowalut', 83, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest pula wydobywcza?', 'chapter3_7')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wspólnym łączeniem zasobów przez górników dla zwiększenia mocy obliczeniowej', 84, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Metodą przechowywania Bitcoinów w jednej lokalizacji', 84, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Siecią serwerów służących do obsługi kryptowalut', 84, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Algorytmem służącym do szyfrowania transakcji', 84, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jak dzielone są nagrody w puli wydobywczej?', 'chapter3_7')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Proporcjonalnie do wkładu górników w proces wydobycia', 85, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Równo między wszystkich uczestników, niezależnie od ich wkładu', 85, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zależnie od kursu Bitcoina w danym momencie', 85, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W zależności od lokalizacji użytkowników', 85, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie jest główne ograniczenie puli wydobywczej?', 'chapter3_7')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nagrody są dzielone między większą liczbę uczestników', 86, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymaga wysokich kosztów początkowych na sprzęt', 86, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak gwarancji bezpieczeństwa blockchaina', 86, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Utrata pełnej kontroli nad procesem wydobywania', 86, 0)")

            // Chapter3_8
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaki typ portfela umożliwia przechowywanie kluczy offline na fizycznym urządzeniu?', 'chapter38')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel sprzętowy', 87, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel mobilny', 87, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel online', 87, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel programowy', 87, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Który portfel umożliwia przechowywanie kluczy na papierze?', 'chapter38')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel papierowy', 88, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel sprzętowy', 88, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel mobilny', 88, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel online', 88, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Który portfel jest aplikacją na smartfony, umożliwiającą wygodne płatności?', 'chapter38')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel mobilny', 89, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel papierowy', 89, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel sprzętowy', 89, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel desktopowy', 89, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Który portfel jest instalowany na komputerze, zapewniając pełną kontrolę nad kluczami?', 'chapter38')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel desktopowy', 90, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel online', 90, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel sprzętowy', 90, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Portfel mobilny', 90, 0)")

            // Chapter3_9
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie jest główne zadanie węzłów w sieci Bitcoin?', 'chapter39')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przetwarzanie, szyfrowanie i potwierdzanie transakcji', 91, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowywanie wszystkich kryptowalut', 91, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zatwierdzanie tożsamości nadawcy', 91, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ustalanie kursów wymiany Bitcoinów', 91, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co oznacza „zerowe potwierdzenie” transakcji bitcoinowej?', 'chapter39')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Odbiorca akceptuje płatność przed jej oficjalnym zatwierdzeniem na blockchainie', 92, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcja jest natychmiastowo zatwierdzana bez żadnych potwierdzeń', 92, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcja jest przetwarzana przez bank', 92, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Została zakończona w ciągu kilku minut', 92, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co jest główną różnicą między transakcją bitcoinową a przelewem bankowym?', 'chapter39')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcje bitcoinowe są szybsze i tańsze', 93, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bitcoin wymaga zatwierdzenia przez bank centralny', 93, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przelew bankowy trwa 10 minut, a bitcoin kilka dni', 93, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bitcoin wymaga weryfikacji tożsamości nadawcy', 93, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są opłaty transakcyjne w sieci Bitcoin w porównaniu do banków?', 'chapter39')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Opłaty bitcoinowe są zazwyczaj niższe lub zerowe', 94, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Banki nie pobierają żadnych opłat', 94, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Opłaty bitcoinowe są wyższe od bankowych', 94, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Opłaty bankowe są stałe i wynoszą 1%', 94, 0)")


            // CHAPTER 4
            // Chapter 4_1
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest główny cel Ethereum?', 'chapter41')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwienie przesyłania środków i zawierania umów przez inteligentne kontrakty', 95, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tylko umożliwienie przesyłania środków', 95, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwienie zakupu nieruchomości', 95, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwienie handlu na giełdach papierów wartościowych', 95, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to jest inteligentny kontrakt w sieci Ethereum?', 'chapter41')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Cyfrowa umowa, która automatycznie realizuje się, gdy spełnione są określone warunki', 96, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umowa zawierana między dwoma stronami w tradycyjny sposób', 96, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Program, który zajmuje się monitorowaniem cen kryptowalut', 96, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zabezpieczenie dla użytkowników przed kradzieżą', 96, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co jest główną jednostką Ethereum?', 'chapter41')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ether', 97, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Bitcoin', 97, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Litecoin', 97, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dogecoin', 97, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kto jest twórcą Ethereum?', 'chapter41')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Vitalik Buterin', 98, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Satoshi Nakamoto', 98, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Charlie Lee', 98, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Mark Zuckerberg', 98, 0)")

            // Chapter 4_2
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to są inteligentne kontrakty w Ethereum?', 'chapter42')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Fragmenty kodu, które automatycznie realizują określone działania', 99, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zabezpieczenia dla użytkowników', 99, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zewnętrzne programy, które służą do przechowywania tokenów', 99, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcje między użytkownikami', 99, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaka funkcja pełnią tokeny w Ethereum?', 'chapter42')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwiają przechowywanie danych użytkowników', 100, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pełnią rolę wirtualnych aktywów, podobnie jak papiery wartościowe', 100, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Są używane do zapewnienia bezpieczeństwa sieci', 100, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowują historię transakcji', 100, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie zadanie wykonują górnicy w sieci Ethereum?', 'chapter42')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tworzą nowe aplikacje', 101, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rozwiązują problemy matematyczne i tworzą nowe bloki w blockchainie', 101, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowują saldo użytkowników', 101, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tworzą tokeny Ether', 101, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co otrzymują górnicy w zamian za swoją pracę w Ethereum?', 'chapter42')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Udział w zdecentralizowanych aplikacjach', 102, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nagrody w postaci Etheru', 102, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Prowizję za transakcje', 102, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dostęp do większej ilości tokenów', 102, 0)")

            // Chapter 4_3
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to jest wydobywanie Ethereum?', 'chapter43')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Proces przetwarzania transakcji', 103, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rozwiązywanie problemów matematycznych i tworzenie nowych bloków', 103, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Proces sprzedaży kryptowalut na giełdach', 103, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Proces przechowywania danych w blockchainie', 103, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaki sprzęt jest najczęściej wykorzystywany do wydobywania Ethereum?', 'chapter43')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wydajne procesory CPU', 104, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Karty graficzne (GPU) o wysokiej wydajności', 104, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dyski twarde', 104, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Urządzenia mobilne', 104, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to jest pula wydobywcza?', 'chapter43')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Grupa użytkowników łączących swoje zasoby', 105, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Usługa w chmurze umożliwiająca wydobywanie Ethereum', 105, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rodzaj specjalnych kart graficznych używanych do wydobywania', 105, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Strona internetowa, na której wykonuje się zadania w zamian za kryptowaluty', 105, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to jest kopanie w chmurze?', 'chapter43')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wydobywanie Ethereum za pomocą lokalnych komputerów', 106, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wynajmujmowanie mocy obliczeniowej od dostawców usług w chmurze', 106, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wydobywanie Ethereum przy użyciu aplikacji mobilnych', 106, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wydobywanie Ethereum za pomocą kart graficznych w specjalnych ośrodkach', 106, 0)")

            // Chapter4_4
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie jest główne wyzwanie dotyczące skalowalności Ethereum?', 'chapter4_4')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zbyt mała liczba użytkowników', 107, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zbyt wysokie opłaty transakcyjne', 107, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczona liczba transakcji na sekundę (około 15)', 107, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak wsparcia dla inteligentnych kontraktów', 107, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co ma na celu aktualizacja Ethereum 2.0?', 'chapter4_4')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zmniejszenie liczby użytkowników', 108, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wprowadzenie mechanizmu proof-of-stake zamiast proof-of-work', 108, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększenie kosztów transakcji', 108, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczenie użycia inteligentnych kontraktów', 108, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest sharding w kontekście Ethereum?', 'chapter4_4')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zmiana algorytmu konsensusu', 109, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dzielnie blockchaina na mniejsze części w celu', 109, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zmiana technologii inteligentnych kontraktów', 109, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wprowadzenie nowych kryptowalut w ekosystemie Ethereum', 109, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie inne rozwiązanie może poprawić skalowalność Ethereum?', 'chapter4_4')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zmniejszenie liczby transakcji w sieci', 110, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wykorzystanie dodatkowych blockchainów, aby odciążyć główną sieć', 110, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczenie liczby inteligentnych kontraktów', 110, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przekształcenie Ethereum w system zamknięty', 110, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie rozwiązania mają na celu poprawę szybkości transakcji i bezpieczeństwa w Ethereum?', 'chapter4_4')")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zmniejszenie liczby użytkowników', 111, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rozwój nowych rozwiązań w zakresie inteligentnych kontraktów', 111, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczenie liczby aktywów w sieci', 111, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększenie kosztów transakcji', 111, 0)")


            //FLASHCARDS
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Zdecentralizowanie', 'Brak kontroli przez jedną instytucję czy osobę', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Anonimowość', 'Tożsamość uczestników transakcji może pozostać nieujawniona', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Bezpośrednie transakcje', 'Transakcje bez pośrednictwa instytucji finansowych', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ograniczona podaż', 'Istnieje ściśle określona ilość jednostek waluty, która może zostać wytworzona', 'chapter1',0)")

            // Chapter 2

            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Finanse', 'Zdecentralizowane finanse (brak pośredników przy korzystaniu z różnych produktów finansowych takich jak np. giełdy)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Dostawy', 'Monitorowanie produktów (szybkie reagowanie w przypadku problemów, przeciwdziała fałszerstwu)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ochrona zdrowia', 'bezpieczne przechowywanie danych medycznych (pacjenci mają pełną kontrolę nad swoją historią zdrowia)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Zarządzanie torzsamością', 'cyfrowe niezmienne dokumenty (bezpieczne przechowywanie dokumentów, eliminacja potrzeby wielokrotnej weryfikacji tożsamości)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Tokenizacja', 'Pozwoli ułatwić nabywanie fragmentów nieruchomości oraz umożliwia tworzenie NFT (unikalnych reprezentacji dzieł sztuki)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Edukacja', 'Zwiększenie wiarygodności systemu certyfikacji (łatwiejsza weryfikacja kwalifikacja)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Głosowanie', 'Umożliwia sprzedaż muzyki, filmów i innych dzieł jako NFT', 'chapter2',0)")

            // Chapter 3

            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Satoshi Nakamoto', 'Anonimowy twórca (lub grupa twórców) Bitcoina', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ograniczona podaż', 'Bitcoin ma maksymalną podaż wynoszącą 21 milionów monet, co czyni go zasobem rzadkim i odpornym na manipulacje.', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Kopanie Bitcoina', 'Proces tworzenia nowych bloków w blockchainie za pomocą mocy obliczeniowej komputerów', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Wydobywanie w chmurze', 'Usługa umożliwiająca wydobywanie Bitcoinów bez zakupu sprzętu', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Portfele kryptowalutowe', 'Narzędzia umożliwiające przechowywanie, wysyłanie i odbieranie kryptowalut', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Portfele sprzętowe', 'Fizyczne urządzenia przechowujące klucze prywatne offline', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Portfele mobilne', 'Aplikacje na smartfony, umożliwiające dostęp do kryptowalut z dowolnego miejsca', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Portfele online (gorące portfele)', 'Przechowują kryptowaluty w chmurze', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Portfele desktopowe', 'Programy instalowane na komputerze, które umożliwiają dostęp do kryptowalut', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Portfele papierowe', 'Fizyczna forma zapisu kluczy prywatnych, często w formie kodów QR', 'chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Klucz prywatny', 'Sekretny ciąg znaków pozwalający na zarządzanie kryptowalutami', 'chapter3', 0)")

            // Chapter 4

            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ethereum', 'Platforma blockchain umożliwiająca przesyłanie środków i tworzenie inteligentnych kontraktów.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ether', 'Główna jednostka rozliczeniowa w sieci Ethereum.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Inteligentny kontrakt', 'Cyfrowa umowa realizująca się automatycznie po spełnieniu określonych warunków.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Vitalik Buterin', 'Twórca Ethereum, jednej z najpopularniejszych kryptowalut.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('DAPP', 'Zdecentralizowana aplikacja działająca na blockchainie Ethereum.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Proof-of-stake', 'Mechanizm, zwiększający wydajność sieci.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Sharding', 'Technologia dzieląca blockchain na mniejsze części w celu zwiększenia liczby przetwarzanych transakcji.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Pula wydobywcza', 'Grupa górników łączących moc obliczeniową, aby szybciej wydobywać Ether.', 'chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ethereum 2.0', 'Aktualizacja sieci Ethereum mająca na celu poprawę skalowalności i bezpieczeństwa.', 'chapter4', 0)")




            // WALLETS
            db?.execSQL("INSERT INTO $TABLE_NAME_WALLETS ($COL2_WALLETS, $COL3_WALLETS) VALUES ('chapter1', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_WALLETS ($COL2_WALLETS, $COL3_WALLETS) VALUES ('chapter2', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_WALLETS ($COL2_WALLETS, $COL3_WALLETS) VALUES ('chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_WALLETS ($COL2_WALLETS, $COL3_WALLETS) VALUES ('chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_WALLETS ($COL2_WALLETS, $COL3_WALLETS) VALUES ('chapter5', 0)")

            // DANGERS
            db?.execSQL("INSERT INTO $TABLE_NAME_DANGERS ($COL2_DANGERS, $COL3_DANGERS) VALUES ('chapter1', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_DANGERS ($COL2_DANGERS, $COL3_DANGERS) VALUES ('chapter2', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_DANGERS ($COL2_DANGERS, $COL3_DANGERS) VALUES ('chapter3', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_DANGERS ($COL2_DANGERS, $COL3_DANGERS) VALUES ('chapter4', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_DANGERS ($COL2_DANGERS, $COL3_DANGERS) VALUES ('chapter5', 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_DANGERS ($COL2_DANGERS, $COL3_DANGERS) VALUES ('chapter6', 0)")

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_FLASHCARDS_TABLE)
        db?.execSQL(SQL_DELETE_ANSWERS_TABLE)
        db?.execSQL(SQL_DELETE_QUESTIONS_TABLE)
        db?.execSQL(SQL_DELETE_RESULTS_TABLE)
        db?.execSQL(SQL_DELETE_TESTS_TABLE)
        db?.execSQL(SQL_DELETE_LOGIN_TABLE)
        db?.execSQL(SQL_DELETE_NOTIFICATIONS_TABLE)
        db?.execSQL(SQL_DELETE_USERNAME_TABLE)
        db?.execSQL(SQL_DELETE_WALLETS_TABLE)
        db?.execSQL(SQL_DELETE_DANGERS_TABLE)
        onCreate(db)
    }
}
