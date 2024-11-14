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
                $COL2_USERNAME VARCHAR(255) NOT NULL
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

            // Chapter 2
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Którą realizację technologii blockchain stanowił Bitcoin?', 'chapter21')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaki problem rozwiązał blockchain Bitcoina?', 'chapter21')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego Bitcoin początkowo budził sceptycyzm?', 'chapter21')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest blockchain?', 'chapter22')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie cechy odróżniają blockchain od tradycyjnych systemów?', 'chapter22')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego blockchain jest efektywny kosztowo?', 'chapter22')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W jaki sposób blockchain zapewnia bezpieczeństwo transakcji?', 'chapter23')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest decentralizacja w kontekście blockchaina?', 'chapter23') ")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie są kluczowe elementy prywatności w blockchainie?', 'chapter23')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to jest sieć P2P?', 'chapter24')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jak węzły w sieci P2P przyczyniają się do działania blockchaina?', 'chapter24')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co to znaczy, że węzły w P2P mają „równy status”?', 'chapter24')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest konsensus w blockchainie?', 'chapter25')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W jaki sposób protokół konsensusu działa w blockchainie?', 'chapter25')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Dlaczego protokół konsensusu jest ważny?', 'chapter25')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie jest główne zadanie kryptografii w blockchainie?', 'chapter26')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaką funkcję pełni hashowanie w blockchainie?', 'chapter26')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Który algorytm jest powszechnie stosowany do hashowania w blockchainie?', 'chapter26')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('W czym pomaga podpis cyfrowy w blockchainie?', 'chapter26')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest transakcja w blockchainie?', 'chapter27')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym są niewydane transakcje wyjściowe (UTXO)?', 'chapter27')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jakie znaczenie mają UTXO w blockchainie?', 'chapter27')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Co dzieje się z UTXO po ich wydaniu?', 'chapter27')")

            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym jest Proof-of-Work (PoW)?', 'chapter28')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Jaką rolę pełni wartość nonce w PoW?', 'chapter28')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Czym różni się Proof-of-Stake (PoS) od Proof-of-Work?', 'chapter28')")
            db?.execSQL("INSERT INTO $TABLE_NAME_QUESTIONS ($COL2_QUESTIONS, $COL3_QUESTIONS) VALUES ('Kiedy użytkownik w PoS ma większą szansę na utworzenie bloku?', 'chapter28')")

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

            //CHAPTER 2
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pierwszą', 18, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Drugą', 18, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Trzecią', 18, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Czwartą', 18, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Podwójne wydawanie', 19, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wysokie koszty przelewów', 19, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Niską prędkość transakcji', 19, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralizację banków', 19, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ze względu na brak kontroli rządowej', 20, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Z powodu wysokiej inflacji', 20, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Z powodu małej liczby użytkowników', 20, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Z powodu braku szyfrowania', 20, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rozproszoną księgą rachunkową', 21, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tradycyjną bazą danych', 21, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralnym bankiem danych', 21, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zbiorem protokołów sieciowych', 21, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przejrzystość, niezmienność, decentralizacja', 22, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wysoka scentralizowana kontrola', 22, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Brak dostępu dla użytkowników', 22, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Możliwość edycji zapisanych danych', 22, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ma niskie, ustalane przez nadawcę opłaty', 23, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymaga drogich serwerów', 23, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zależy od rządowych dotacji', 23, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymaga udziału pośredników', 23, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Poprzez użycie kryptografii', 24, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przez scentralizowaną kontrolę', 24, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Dzięki ochronie hasłem', 24, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przez autoryzację banków', 24, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Równym rozłożeniem kontroli między użytkowników', 25, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Centralizacją danych w jednym punkcie', 25, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kontrolą jednej instytucji', 25, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczeniem dostępu do danych', 25, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Publiczne klucze i podpisy cyfrowe', 26, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Anonimowość IP', 26, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Weryfikacja użytkownika w banku', 26, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ograniczenie dostępu do węzłów', 26, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sieć komputerów działających na równych zasadach', 27, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sieć scentralizowana', 27, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Hierarchiczna sieć dostawców', 27, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zbiór serwerów zarządzanych przez jedną firmę', 27, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowują pełne kopie blockchaina', 28, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tylko wysyłają transakcje', 28, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Nie mają wpływu na działanie sieci', 28, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Działają jako serwery centralne ', 28, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Żaden węzeł nie jest nadrzędny', 29, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wszyscy są administratorami', 29, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Każdy węzeł ma pełną kopię łańcucha', 29, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Tylko wybrane węzły mogą zarządzać transakcjami', 29, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ogólną zgodą węzłów na treść łańcucha ', 30, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jednomyślną decyzją użytkowników', 30, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Decyzją administratora', 30, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wymogiem autoryzacji przez banki', 30, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Węzły wspólnie zatwierdzają transakcje', 31, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Każdy węzeł decyduje indywidualnie', 31, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Administracja decyduje o aktualizacji', 31, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Rząd ma ostateczną kontrolę nad blockchainem', 31, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapobiega centralizacji', 32, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zwiększa liczbę użytkowników', 32, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwia szybsze transakcje', 32, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Wspiera anonimowość', 32, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapewnia automatyczną synchronizację portfeli', 33, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zabezpiecza prywatność i integralność danych oraz weryfikuje własność aktywów', 33, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ułatwia dostęp do sieci P2P', 33, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwia szybkie kopiowanie transakcji', 33, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Kompresuje dane w celu zmniejszenia ich rozmiaru', 34, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Sprawdza, czy użytkownik ma wystarczające środki', 34, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapewnia integralność danych poprzez generowanie unikalnego hashu dla każdego zestawu danych', 34, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Szyfruje wiadomości między użytkownikami', 34, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('RSA', 35, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('DES', 35, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('SHA256', 35, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('AES', 35, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W przesyłaniu dużych plików w sieci P2P', 36, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W podpisywaniu transakcji, potwierdzając ich autentyczność i własność zasobów', 36, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W szyfrowaniu danych w bazie blockchain', 36, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W tworzeniu kopii zapasowych danych', 36, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Procesem tworzenia nowego bloku w sieci', 37, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapytaniem o historię salda', 37, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przekazaniem informacji o transferze kryptowalut między użytkownikami', 37, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zatwierdzaniem nowej wersji blockchaina', 37, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Transakcjami oczekującymi na zatwierdzenie', 38, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zasobami, które zostały przekazane do innych użytkowników', 38, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Aktywnymi monetami, które mogą zostać wydane przez właściciela', 38, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zapisem wszystkich transakcji w blockchainie', 38, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Umożliwiają sprawdzenie całej historii transakcji', 39, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Pozwalają użytkownikowi na wydanie kryptowaluty poprzez potwierdzenie własności', 39, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Przechowują informacje o saldzie kont użytkowników', 39, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zabezpieczają transakcje przed modyfikacją', 39, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Stają się STXO i nie są już aktywne', 40, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zostają podzielone na mniejsze UTXO', 40, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Ulegają usunięciu z blockchaina', 40, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Zostają zakodowane w formie nowego hashu', 40, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Algorytmem konsensusu wykorzystującym staking monet', 41, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Procesem losowego wybierania węzła do utworzenia nowego bloku', 41, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Algorytmem konsensusu polegającym na rozwiązywaniu złożonych zagadek kryptograficznych', 41, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Mechanizmem pozwalającym górnikom na przesyłanie monet', 41, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest kluczem prywatnym użytkownika', 42, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Służy do szyfrowania danych w bloku', 42, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest losową wartością, którą górnicy muszą znaleźć, aby spełnić wymagania trudności', 42, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Jest adresem do wysyłania transakcji', 42, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('PoS nie wymaga zużycia dużej ilości energii do utworzenia bloku', 43, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('PoS wymaga większej mocy obliczeniowej niż PoW', 43, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W PoS bloki tworzą jedynie nowe węzły', 43, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('W PoS bloki tworzone są tylko w centralnych punktach sieci', 43, 0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy rozwiąże zagadkę kryptograficzną', 44, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy stawia większą liczbę monet w sieci', 44, 1)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy znajdzie nowy hash', 44, 0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_ANSWERS ($COL2_ANSWERS, $COL3_ANSWERS, $COL4_ANSWERS) VALUES ('Gdy posiada więcej węzłów w sieci', 44, 0)")


            //FLASHCARDS
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Zdecentralizowanie', 'Brak kontroli przez jedną instytucję czy osobę', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Anonimowość', 'Tożsamość uczestników transakcji może pozostać nieujawniona', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Bezpośrednie transakcje', 'Transakcje bez pośrednictwa instytucji finansowych', 'chapter1',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ograniczona podaż', 'Istnieje ściśle określona ilość jednostek waluty, która może zostać wytworzona', 'chapter1',0)")

            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Finanse', 'Zdecentralizowane finanse (brak pośredników przy korzystaniu z różnych produktów finansowych takich jak np. giełdy)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Dostawy', 'Monitorowanie produktów (szybkie reagowanie w przypadku problemów, przeciwdziała fałszerstwu)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Ochrona zdrowia', 'bezpieczne przechowywanie danych medycznych (pacjenci mają pełną kontrolę nad swoją historią zdrowia)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Zarządzanie torzsamością', 'cyfrowe niezmienne dokumenty (bezpieczne przechowywanie dokumentów, eliminacja potrzeby wielokrotnej weryfikacji tożsamości)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Tokenizacja', 'Pozwoli ułatwić nabywanie fragmentów nieruchomości oraz umożliwia tworzenie NFT (unikalnych reprezentacji dzieł sztuki)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Edukacja', 'Zwiększenie wiarygodności systemu certyfikacji (łatwiejsza weryfikacja kwalifikacja)', 'chapter2',0)")
            db?.execSQL("INSERT INTO $TABLE_NAME_FLASHCARDS ($COL2_FLASHCARDS, $COL3_FLASHCARDS, $COL4_FLASHCARDS, $COL5_FLASHCARDS) VALUES ('Głosowanie', 'Umożliwia sprzedaż muzyki, filmów i innych dzieł jako NFT', 'chapter2',0)")
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
        onCreate(db)
    }
}
