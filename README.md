# Aplikacja Pogodowa
## Opis Projektu
Niniejszy projekt to aplikacja pogodowa, której celem jest dostarczanie informacji o bieżących i prognozowanych warunkach pogodowych dla lokalizacji wybranych przez użytkownika. Poniżej przedstawiono główne cechy projektu oraz wykorzystane umiejętności:

## Funkcje Projektu
### 1. Pobieranie Informacji o Pogodzie:
• Umożliwia pobieranie danych pogodowych korzystając z API udostępnianego przez serwis OpenWeatherMap.
• Obsługuje dane w formie plików JSON lub XML.

### 2. Zarządzanie Ulubionymi Lokalizacjami:
• Pozwala użytkownikowi zdefiniować listę ulubionych lokalizacji.
• Sprawdza poprawność danych lokalizacyjnych poprzez serwis Yahoo, pobiera identyfikator WOEID, a następnie zapisuje lokalizację w bazie danych.

### 3. Pobieranie Aktualnych Informacji o Pogodzie:
• Sprawdza dostępność połączenia internetowego podczas uruchomienia.
• Jeżeli dostępne, pobiera aktualne informacje o pogodzie i zapisuje je w pamięci telefonu (w prywatnym katalogu aplikacji).

### 4. Obsługa Braku Połączenia Internetowego:
• W przypadku braku połączenia internetowego, wczytuje informacje o pogodzie z ostatnio zapisanego pliku.
• Informuje użytkownika o możliwej nieaktualności danych i zachęca do aktualizacji przy użyciu połączenia internetowego.

## Wykorzystane Umiejętności
Projekt wykorzystuje szereg zaawansowanych umiejętności programistycznych, takich jak:

• Implementacja interfejsu użytkownika opartego na fragmentach.
• Skuteczna komunikacja między fragmentami a aktywnościami.
• Adaptacja interfejsu użytkownika do różnych ekranów.
• Integracja z usługami sieciowymi dostępnymi w systemie Android.
• Parsowanie plików XML/JSON dla przetwarzania danych pogodowych.
• Bezpieczne przechowywanie danych w pamięci urządzenia, w tym prywatnym katalogu aplikacji.
• Wykorzystanie bazy danych do składowania informacji o ulubionych lokalizacjach.

## Struktura Projektu
Projekt składa się z kilku kluczowych modułów:

• UI_Fragmenty: Zawiera implementację interfejsu użytkownika opartego na fragmentach.
• Komunikacja: Odpowiada za skuteczną komunikację między fragmentami a aktywnościami.
• Przetwarzanie_Danych: Realizuje funkcje związane z pobieraniem, parsowaniem i przechowywaniem danych pogodowych.
• Baza_Danych: Zapewnia mechanizmy przechowywania ulubionych lokalizacji w bazie danych.

## Wygląd aplikacji:
### Strona główna:
![Screenshot_2023-12-17-22-48-58-08_e2a8ad7d5d0bba5df8045270f51053db](https://github.com/KZielinskii/Pogoda/assets/58587948/74513b29-c541-488e-93e7-c4c9e9198f13)
### Dodawanie miejsco![Screenshot_2023-12-17-22-49-05-59_e2a8ad7d5d0bba5df8045270f51053db](https://github.com/KZielinskii/Pogoda/assets/58587948/fb85728f-a370-420e-b68f-1f490b9d6da7)
wści:
![Screenshot_2023-12-17-22-49-11-37_e2a8ad7d5d0bba5df8045270f51053db](https://github.com/KZielinskii/Pogoda/assets/58587948/75cc52f2-f94a-4e92-b36f-2d02c213cfed)
### Ekran pogody miejscowości:
![Screenshot_2023-12-17-22-49-18-84_e2a8ad7d5d0bba5df8045270f51053db](https://github.com/KZielinskii/Pogoda/assets/58587948/6fa9e573-b82a-40f7-831d-16a1524d9960)
![Screenshot_2023-12-17-22-49-25-46_e2a8ad7d5d0bba5df8045270f51053db](https://github.com/KZielinskii/Pogoda/assets/58587948/dd7b87a8-acdf-4136-8e35-29ee3c34ed0d)
![Screenshot_2023-12-17-22-49-47-83_e2a8ad7d5d0bba5df8045270f51053db](https://github.com/KZielinskii/Pogoda/assets/58587948/e2c1e961-2d91-47f9-8299-1518a6224fd0)
### Zmiana jednostek:
![Screenshot_2023-12-17-22-50-18-02_e2a8ad7d5d0bba5df8045270f51053db](https://github.com/KZielinskii/Pogoda/assets/58587948/f30edbde-eba0-4c79-b859-2843c6300aa8)

